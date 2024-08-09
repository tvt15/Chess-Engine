package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.normalMove;
import com.chess.engine.board.Move.captureMove;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Bishop extends Piece {
    final static int[] Candidate_Move_Coordinates = {-9,-7,7,9};
    public Bishop(final int piecePosition, final Alliance pieceAlliance) {
        super(PieceType.BISHOP,piecePosition, pieceAlliance);
    }
    @Override
    public Bishop movePiece(final Move move) {
        return new Bishop(move.getDestinationCoordinate(),move.getPieceToMove().getPieceAlliance());
    }
    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
        final List<Move> LegalMoves = new ArrayList<>();
        for(final int CandidateCoordinateOffset : Candidate_Move_Coordinates){
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
                       LegalMoves.add(new normalMove(this,board,CandidateDestinationCoordinate));
                   }
                   else{
                      final Piece PieceAtDestination = CandidateDestinationTile.getPiece();
                       if(this.pieceAlliance!=PieceAtDestination.pieceAlliance){
                           LegalMoves.add(new captureMove(this,board,PieceAtDestination,CandidateDestinationCoordinate));
                       }
                   break;
                   }
                }
            }
        }
        return ImmutableList.copyOf(LegalMoves);
    }
    @Override
    public String toString(){
        return PieceType.BISHOP.toString();
    }
    private static boolean IsFirstColumnExclusion(final int CurrPos,final int CandidateOffset){
        return BoardUtils.FirstColumn[CurrPos] &&(CandidateOffset==-9 || CandidateOffset==7);
    }
    private static boolean IsEighthColumnExclusion(final int CurrPos,final int CandidateOffset){
        return BoardUtils.EighthColumn[CurrPos] && (CandidateOffset==-7 || CandidateOffset==9);
    }
}
