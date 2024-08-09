package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Queen extends Piece{
    final static int[] Candidate_Move_Coordinates = {-9,-8,-7,-1,1,7,8,9};
    public Queen(int piecePosition, Alliance pieceAlliance) {
        super(PieceType.QUEEN,piecePosition, pieceAlliance);
    }

    @Override
    public Queen movePiece(final Move move) {
        return new Queen(move.getDestinationCoordinate(),move.getPieceToMove().getPieceAlliance());
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
        final List<Move> LegalMoves= new ArrayList<>();
        for(final int CandidateCoordinateOffset:Candidate_Move_Coordinates){
            int CandidateDestinationCoordinate= this.piecePosition;
            while(BoardUtils.isValidTileCoordinate(CandidateDestinationCoordinate)){
                if(IsFirstColumnExclusion(CandidateDestinationCoordinate,CandidateCoordinateOffset)||
                        IsEighthColumnExclusion(CandidateDestinationCoordinate,CandidateCoordinateOffset)){
                    break;
                }
                CandidateDestinationCoordinate+=CandidateCoordinateOffset;
                if(BoardUtils.isValidTileCoordinate(CandidateDestinationCoordinate)){
                    final Tile CandidateDestinationTile = board.getTile(CandidateDestinationCoordinate);
                    if(!CandidateDestinationTile.isTileOccupied()){
                        LegalMoves.add(new Move.normalMove(this,board,CandidateDestinationCoordinate));
                    }
                    else{
                        final Piece PieceAtDestination = CandidateDestinationTile.getPiece();
                        if(this.getPieceAlliance()!=PieceAtDestination.getPieceAlliance()){
                            LegalMoves.add(new Move.captureMove(this,board,PieceAtDestination,CandidateDestinationCoordinate));
                        }
                        break;  }
                }
            }
        }
        return ImmutableList.copyOf(LegalMoves);
    }
    @Override
    public String toString(){
        return PieceType.QUEEN.toString();
    }

    private boolean IsFirstColumnExclusion(int candidateDestinationCoordinate, int candidateCoordinateOffset) {
        return BoardUtils.FirstColumn[candidateDestinationCoordinate] &&(candidateCoordinateOffset==-1||
                candidateCoordinateOffset==-9 || candidateCoordinateOffset==7);
    }
    private boolean IsEighthColumnExclusion(int candidateDestinationCoordinate, int candidateCoordinateOffset) {
        return BoardUtils.EighthColumn[candidateDestinationCoordinate] &&(candidateCoordinateOffset==1||
               candidateCoordinateOffset==-7 || candidateDestinationCoordinate==9);
    }
}
