package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.normalMove;
import com.chess.engine.board.Move.captureMove;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Pawn extends Piece{
    private final static int[] Candidate_Move_Coordinate={7,8,9,16};
    public Pawn(final int piecePosition, final Alliance pieceAlliance) {
        super(PieceType.PAWN,piecePosition, pieceAlliance);
    }
    @Override
    public Pawn movePiece(final Move move) {
        return new Pawn(move.getDestinationCoordinate(),move.getPieceToMove().getPieceAlliance());
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
        final List<Move> LegalMoves= new ArrayList<>();
        for(int CandidateOffset:Candidate_Move_Coordinate){
            int CandidateDestinationCoordinate= (CandidateOffset *this.pieceAlliance.getDirection())+this.piecePosition;
            if(!BoardUtils.isValidTileCoordinate(CandidateDestinationCoordinate)){
                continue;
            }
            if(CandidateOffset==8&& !board.getTile(CandidateDestinationCoordinate).isTileOccupied()){
                LegalMoves.add(new normalMove(this,board,CandidateDestinationCoordinate));//1 step pawn
            }
            else if(CandidateOffset==16 && this.isFirstMove()&&
                    (BoardUtils.SecondRow[this.piecePosition]&&this.pieceAlliance.isBlack())||
                    (BoardUtils.SeventhRow[this.piecePosition]&& this.pieceAlliance.isWhite())){
                if(!board.getTile(CandidateDestinationCoordinate).isTileOccupied()&&
                        !board.getTile((8 *this.pieceAlliance.getDirection())+this.piecePosition).isTileOccupied()){
                    LegalMoves.add(new normalMove(this,board,CandidateDestinationCoordinate));//2 step pawn
                }
            }
            else if(CandidateOffset ==7&&!(this.pieceAlliance.isBlack()&&BoardUtils.FirstColumn[this.piecePosition])&&//edge case
                    !(this.pieceAlliance.isWhite()&&BoardUtils.EighthColumn[this.piecePosition])){
                if((board.getTile(CandidateDestinationCoordinate).isTileOccupied())&&
                        ((board.getTile(CandidateDestinationCoordinate).getPiece().pieceAlliance)!=this.pieceAlliance)) {
                    LegalMoves.add(new captureMove(this, board,
                            ((board.getTile(CandidateDestinationCoordinate)).getPiece()),
                            CandidateDestinationCoordinate));
                }

            }
            else if(CandidateOffset==9 &&!(BoardUtils.FirstColumn[this.piecePosition]&&this.pieceAlliance.isWhite())&&
                    !(BoardUtils.EighthColumn[this.piecePosition]&&this.pieceAlliance.isBlack())){//#2 edge case
                if((board.getTile(CandidateDestinationCoordinate).isTileOccupied())&&
                        ((board.getTile(CandidateDestinationCoordinate).getPiece().getPieceAlliance())!=this.getPieceAlliance())) {
                    LegalMoves.add(new captureMove(this, board,
                            ((board.getTile(CandidateDestinationCoordinate)).getPiece()),
                            CandidateDestinationCoordinate));
                }
            }
        }


        return ImmutableList.copyOf(LegalMoves);
    }
    @Override
    public String toString(){
        return PieceType.PAWN.toString();
    }
}
