package com.chess.engine.board;

import com.chess.engine.Alliance;
import com.chess.engine.Player.BlackPlayer;
import com.chess.engine.Player.Player;
import com.chess.engine.Player.WhitePlayer;
import com.chess.engine.pieces.*;
import com.google.common.collect.ImmutableList;

import java.util.*;
//Method Chaining: In java, Method Chaining is used to invoke multiple methods on the same object which occurs as a
// single statement. Method-chaining is implemented by a series of methods that return the this reference
// for a class instance.

public class Board {
    private final List<Tile> gameBoard;
    private final Collection<Piece> whitePieces;//tracks all available white pieces
    private final Collection<Piece> blackPieces;
    private final WhitePlayer whitePlayer;
    private final BlackPlayer blackPlayer;
    private final Player currentPlayer;

    private Board(final Builder builder){
        this.gameBoard=createGameBoard(builder);
        this.whitePieces = calculateActivePieces(this.gameBoard,Alliance.WHITE);
        this.blackPieces = calculateActivePieces(this.gameBoard,Alliance.BLACK);
        final Collection<Move> whiteLegalMoves = calculateLegalMoves(this.whitePieces);
        final Collection<Move> blackLegalMoves = calculateLegalMoves(this.blackPieces);
        this.whitePlayer= new WhitePlayer(this,whiteLegalMoves,blackLegalMoves);
        this.blackPlayer=new BlackPlayer(this,whiteLegalMoves,blackLegalMoves);
        this.currentPlayer=builder.nextMoveMaker.choosePlayer(this.whitePlayer,this.blackPlayer);

    }
    public Player currentPlayer(){
        return this.currentPlayer;
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        for(int i=0;i<BoardUtils.Num_Tiles;i++){
            final String tileText = this.gameBoard.get(i).toString();//getting the to string method of each tile
            builder.append(String.format("%3s",tileText));//adding each tile(piece and empty tile)to the output
            if((i+1)%BoardUtils.Num_Tiles_Per_Row==0){//at index 7 our row ends
                builder.append("\n");
            }
        }
        return builder.toString();
    }
    public Player whitePlayer(){
        return whitePlayer;
    }
    public Player BlackPlayer(){
        return blackPlayer;
    }

    private Collection<Move> calculateLegalMoves(Collection<Piece> pieces) {
        List<Move> LegalMoves = new ArrayList<>();
        for(final Piece piece:pieces){
            LegalMoves.addAll(piece.calculateLegalMoves(this));
        }
        return ImmutableList.copyOf(LegalMoves);
    }

    private static Collection<Piece> calculateActivePieces(List<Tile> gameBoard, Alliance alliance) {//stores all pieces of one alliance on board
        final List<Piece> activePieces = new ArrayList<>();
        for(final Tile tile: gameBoard){
            if(tile.isTileOccupied()){
                if(tile.getPiece().getPieceAlliance()==alliance){
                    activePieces.add(tile.getPiece());
                }
            }
        }
        return ImmutableList.copyOf(activePieces);
    }

    private static List<Tile> createGameBoard(final Builder builder) {
        final Tile[] tiles = new Tile[BoardUtils.Num_Tiles];
        for(int i=0;i<BoardUtils.Num_Tiles;i++){
            tiles[i]=Tile.createTile(i,builder.boardConfig.get(i));//creates new tiles and puts pieces from map
            // using key if any present on that tile
        }
        return ImmutableList.copyOf(tiles);
    }

    public Tile getTile(final int tileCoordinate) {

        return gameBoard.get(tileCoordinate);//as we have created a list of tiles named gameboard where the tile position
        // is its index in list hence returning the tile indexed with tile coordinate.
        // for eg tile 0 is indexed at 0th pos
    }

    public static Board createStandardBoard(){
        final Builder builder = new Builder();
        //Black Pieces
        builder.setPiece(new Rook(0,Alliance.BLACK));
        builder.setPiece(new Knight(1,Alliance.BLACK));
        builder.setPiece(new Bishop(2,Alliance.BLACK));
        builder.setPiece(new Queen(3,Alliance.BLACK));
        builder.setPiece(new King(4,Alliance.BLACK));
        builder.setPiece(new Bishop(5,Alliance.BLACK));
        builder.setPiece(new Knight(6,Alliance.BLACK));
        builder.setPiece(new Rook(7,Alliance.BLACK));
        builder.setPiece(new Pawn(8,Alliance.BLACK));
        builder.setPiece(new Pawn(9,Alliance.BLACK));
        builder.setPiece(new Pawn(10,Alliance.BLACK));
        builder.setPiece(new Pawn(11,Alliance.BLACK));
        builder.setPiece(new Pawn(12,Alliance.BLACK));
        builder.setPiece(new Pawn(13,Alliance.BLACK));
        builder.setPiece(new Pawn(14,Alliance.BLACK));
        builder.setPiece(new Pawn(15,Alliance.BLACK));

        //White Pieces
        builder.setPiece(new Rook(63,Alliance.WHITE));
        builder.setPiece(new Knight(62,Alliance.WHITE));
        builder.setPiece(new Bishop(61,Alliance.WHITE));
        builder.setPiece(new King(60,Alliance.WHITE));
        builder.setPiece(new Queen(59,Alliance.WHITE));
        builder.setPiece(new Bishop(58,Alliance.WHITE));
        builder.setPiece(new Knight(57,Alliance.WHITE));
        builder.setPiece(new Rook(56,Alliance.WHITE));
        builder.setPiece(new Pawn(55,Alliance.WHITE));
        builder.setPiece(new Pawn(54,Alliance.WHITE));
        builder.setPiece(new Pawn(53,Alliance.WHITE));
        builder.setPiece(new Pawn(52,Alliance.WHITE));
        builder.setPiece(new Pawn(51,Alliance.WHITE));
        builder.setPiece(new Pawn(50,Alliance.WHITE));
        builder.setPiece(new Pawn(49,Alliance.WHITE));
        builder.setPiece(new Pawn(48,Alliance.WHITE));

        builder.setMoveMaker(Alliance.WHITE);

        return builder.build();
    }

    public Collection<Piece> getWhitePieces() {
        return this.whitePieces;
    }
    public Collection<Piece> getBlackPieces(){
        return this.blackPieces;
    }



    public static class Builder{//builder class for thread safety and chaining of methods
        Map<Integer, Piece> boardConfig;
        Alliance nextMoveMaker;
        public Builder() {
            this.boardConfig= new HashMap<>();
        }
            public Builder setPiece(final Piece piece){
                this.boardConfig.put(piece.getPiecePosition(),piece);
                return this;
            }
            public Builder setMoveMaker(final Alliance nextMoveMaker){
                this.nextMoveMaker=nextMoveMaker;
                return this;
            }

        public Board build(){
            return new Board(this);
        }
    }
}
