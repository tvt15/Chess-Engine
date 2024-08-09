package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

import java.util.Collection;

public abstract class Piece {
    protected final PieceType pieceType;
    protected final int piecePosition;
    protected final Alliance pieceAlliance;
    protected final boolean isFirstMove;
    private final int cachedHashCode;

    Piece(PieceType pieceType,int piecePosition, Alliance pieceAlliance){
        this.piecePosition=piecePosition;
        this.pieceAlliance=pieceAlliance;
        this.isFirstMove=false;
        this.pieceType=pieceType;
        this.cachedHashCode=calculateHashCode();
    }

    private int calculateHashCode() {
        int result= pieceType.hashCode();
        result=31 * result + pieceAlliance.hashCode();
        result=31 * result + piecePosition;
        result=31 * result + (isFirstMove?1:0);
        return result;
    }
    @Override
    public boolean equals(final Object other){
        if(other==this){
            return true;
        }
        if(!(other instanceof Piece)){
            return false;
        }
        final Piece otherPiece= (Piece) other;
        return piecePosition== otherPiece.getPiecePosition()&&pieceAlliance==otherPiece.getPieceAlliance()
                &&isFirstMove== otherPiece.isFirstMove()&& pieceType==otherPiece.getPieceType();

    }

    @Override
    public int hashCode(){
        return this.cachedHashCode;
    }

    public int getPiecePosition(){
        return this.piecePosition;
    }
    public boolean isFirstMove(){
        return this.isFirstMove;
    }
    public Alliance getPieceAlliance(){
        return this.pieceAlliance;
    }
    public abstract Collection<Move> calculateLegalMoves(final Board board) ;
    public abstract Piece movePiece(Move move);//returns a new piece that is same as the old piece just has
    // diff tile coordinates.we do this as a part of immutability.
    public enum PieceType{
        PAWN("P"){
            @Override
            public boolean isKing(){
                return false;
            }
        },
        ROOK("R") {
            @Override
            public boolean isKing() {
                return false;
            }
        },
        KNIGHT("N") {
            @Override
            public boolean isKing() {
                return false;
            }
        },
        BISHOP("B") {
            @Override
            public boolean isKing() {
                return false;
            }
        },
        QUEEN("Q") {
            @Override
            public boolean isKing() {
                return false;
            }
        },
        KING("K") {
            @Override
            public boolean isKing() {
                return true;
            }
        };

        private String pieceName;//code won't work if you put this at start of enum class....weird :/
        PieceType(final String pieceName) {//constructor for passing string in enum
            this.pieceName = pieceName;
        }

        @Override
        public String toString(){
            return this.pieceName;
        }
        public abstract boolean isKing();
    }
    public PieceType getPieceType(){
        return this.pieceType;
    }



}
