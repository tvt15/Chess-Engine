package com.chess.engine.Player;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Piece;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class Player {
    protected final Board board;
    protected final King playerKing;
    protected final Collection<Move> legalMoves;
    private final boolean isInCheck;
    //protected final Collection<Move> opponentMoves;

    Player(Board board,Collection<Move> legalMoves,Collection<Move> opponentMoves){
        this.board=board;
        this.legalMoves=legalMoves;
        this.playerKing=establishKing();
        //this.opponentMoves=opponentMoves;
        this.isInCheck=!Player.calculateAttackOnTile(playerKing.getPiecePosition(),opponentMoves).isEmpty();//if this
        // list is not empty then player's king is in check
    }
    public King getPlayerKing(){
        return this.playerKing;
    }
    public Collection<Move> getLegalMoves(){
        return this.legalMoves;
    }

    private static Collection<Move> calculateAttackOnTile(int piecePosition, Collection<Move> Moves) {
        final List<Move> AttackMoves=new ArrayList<>();
        for(Move move:Moves){
            if(move.getDestinationCoordinate()==piecePosition){
                AttackMoves.add(move);
            }
        }
        return ImmutableList.copyOf(AttackMoves);
    }

    private King establishKing() {
        for(final Piece piece:getActivePieces()){
            if(piece.getPieceType().isKing()){
                return (King) piece;
            }
        }
        throw new RuntimeException("lol,invalid Board config");
    }

    public boolean isMoveLegal(final Move move){
        return this.legalMoves.contains(move);
    }

    public boolean isInCheck(){

        return this.isInCheck;
    }
    public boolean isCheckmated(){
        return this.isInCheck&&!hasEscapeMoves();
    }
    public boolean isStaleMate(){
        return !this.isInCheck &&!hasEscapeMoves();
    }
    private boolean hasEscapeMoves() {
        for(Move move:this.legalMoves){
            final MoveTransition transition=makeMove(move);
            if(transition.getMoveStatus().isDone()){
                return true;
            }
        }
        return false;
    }


    public boolean isCastled(){
        return false;
    }
    public MoveTransition makeMove(final Move move){
        if(!isMoveLegal(move)){
            return new MoveTransition(this.board,MoveStatus.ILLEGAL_MOVE,move);
        }
        final Board transitionBoard=move.execute();
        final Collection<Move> kingAttacks=Player.calculateAttackOnTile(transitionBoard.currentPlayer().getOpponent().getPlayerKing().getPiecePosition(),
                transitionBoard.currentPlayer().getLegalMoves());//we use currentplayer.getopponent here as when we
        // transition thru the move we become the opponent.hence using current player.getlegalmoves to get opponents moves;
        if(!kingAttacks.isEmpty()){//checks if our king is in check after making the move
            return new MoveTransition(this.board,MoveStatus.LEAVES_KING_IN_CHECK,move);
        }
        return new MoveTransition(this.board,MoveStatus.DONE,move);
    }
    public abstract Collection<Piece> getActivePieces();
    public abstract Alliance getAlliance();
    public abstract Player getOpponent();


}
