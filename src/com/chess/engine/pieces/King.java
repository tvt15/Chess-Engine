package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.captureMove;
import com.chess.engine.board.Move.normalMove;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class King extends Piece{
    private final static int[] Candidate_Move_Coordinates={-9,-8,-7,-1,1,7,8,9};
    public King(final int piecePosition,
                final Alliance pieceAlliance) {
        super(PieceType.KING,piecePosition, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {
        final List<Move> LegalMoves=new ArrayList<>();
        for(final int CandidateOffset:Candidate_Move_Coordinates){
            final int CandidateDestinationCoordinate=this.piecePosition+CandidateOffset;
            if(IsFirstColumnExclusion(this.piecePosition,CandidateOffset)||
                    IsEighthColumnExclusion(this.piecePosition,CandidateOffset)||
                    !BoardUtils.isValidTileCoordinate(CandidateDestinationCoordinate)){
                continue;
            }
            final Tile CandidateDestinationTile=board.getTile(CandidateDestinationCoordinate);
            if(!CandidateDestinationTile.isTileOccupied()){//if the tile is empty then mark move as available
                LegalMoves.add(new normalMove(this,board,CandidateDestinationCoordinate));
            }
            else{
                final Piece PieceAtDestination = CandidateDestinationTile.getPiece();//if the tile is occupied then get the piece on tile
                if(this.getPieceAlliance()!= PieceAtDestination.getPieceAlliance()) {//if its of opposite color then it can be captured
                    LegalMoves.add(new captureMove(this,board,PieceAtDestination,CandidateDestinationCoordinate));
                }
            }
        }

        return ImmutableList.copyOf(LegalMoves);
    }

    @Override
    public King movePiece(final Move move) {
        return new King(move.getDestinationCoordinate(),move.getPieceToMove().getPieceAlliance());
    }

    @Override
    public String toString(){
        return PieceType.KING.toString();
    }

    private boolean IsEighthColumnExclusion(int piecePosition, int candidateOffset) {
        return(BoardUtils.EighthColumn[piecePosition]&&(candidateOffset==-7||candidateOffset==1||candidateOffset==9));
    }

    private boolean IsFirstColumnExclusion(int piecePosition, int candidateOffset) {
        return(BoardUtils.FirstColumn[piecePosition]&&(candidateOffset==-9||candidateOffset==-1||candidateOffset==7));
    }
}
