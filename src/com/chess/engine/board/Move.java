package com.chess.engine.board;

import com.chess.engine.Player.Player;
import com.chess.engine.pieces.Piece;

import static com.chess.engine.board.Board.*;

public abstract class Move {
    final Piece PieceToMove;
    final Board board;
    final int DestCoord;
    private Move(final Piece PieceToMove,
                 final Board board,
                 final int DestCoord){
        this.board=board;
        this.PieceToMove = PieceToMove;
        this.DestCoord=DestCoord;
    }
    public int getDestinationCoordinate(){
        return this.DestCoord;
    }
    public Piece getPieceToMove(){
        return this.PieceToMove;
    }

    public abstract Board execute();//when executing a move,  new board is created with the move executed using board
    // builder because the board class is immutable.

    public static final class normalMove extends Move {
        public normalMove(final Piece PieceToMove,
                          final Board board,
                          final  int DestCoord) {
            super(PieceToMove, board, DestCoord);
        }

        @Override
        public Board execute() {
            final Builder builder=new Builder();
            for(final Piece piece: this.board.currentPlayer().getActivePieces()){//setting the current players piece on
                // to the new board
                //TODO hash codes and equals for pieces
                if(this.PieceToMove.equals(piece)){
                    builder.setPiece(piece);
                }
            }
            for(final Piece piece:this.board.currentPlayer().getOpponent().getActivePieces()){//opponents pieces
                builder.setPiece(piece);
            }
            builder.setPiece(this.PieceToMove.movePiece(this));//setting the moved piece
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());//setting opponent as the move maker
            return builder.build();
        }

    }
    public static final class captureMove extends Move{
        final Piece PieceCaptured;
       public captureMove(final Piece PieceToMove,
                          final Board board,
                          final Piece PieceCaptured,
                          final int DestCoord){
            super(PieceToMove,board,DestCoord);
            this.PieceCaptured=PieceCaptured;

        }

        @Override
        public Board execute() {
            return null;
        }
    }
}

