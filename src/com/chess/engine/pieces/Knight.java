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

public class Knight extends Piece{
    private final static int[] Candidate_Move_Coordinates = {-17,-15,-10,-6,6,10,15,17};//the list of offsets for knights movement on a board

    public Knight(final int piecePosition, final Alliance pieceAlliance) {
        super(PieceType.KNIGHT,piecePosition, pieceAlliance);
    }
    @Override
    public Knight movePiece(final Move move) {
        return new Knight(move.getDestinationCoordinate(),move.getPieceToMove().getPieceAlliance());
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
        final List<Move> LegalMoves = new ArrayList<>();
        for(final int CurrentCandidateOffset : Candidate_Move_Coordinates){
           final int CandidateDestinationCoordinate=this.piecePosition + CurrentCandidateOffset;//adding piece position and the offset for knight movement
            if(BoardUtils.isValidTileCoordinate(CandidateDestinationCoordinate)){//checking if destination tile inside the board array
                if(IsFirstColumnExclusion(this.piecePosition,CurrentCandidateOffset)||//checking the exception of first column where certain cordinates produced by offset wld be wrong
                        IsSecondColumnExclusion(this.piecePosition,CurrentCandidateOffset)||//for 2nd column
                        IsSeventhColumnExclusion(this.piecePosition,CurrentCandidateOffset)||//for7th column
                        IsEighthColumnExclusion(this.piecePosition,CurrentCandidateOffset)){//for 8th column
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
        }
        return ImmutableList.copyOf(LegalMoves);
    }
    @Override
    public String toString(){
        return PieceType.KNIGHT.toString();
    }

    private static boolean IsFirstColumnExclusion(final int currentPosition,int CandidateOffset ){
        return BoardUtils.FirstColumn[currentPosition] &&(CandidateOffset==-17 ||CandidateOffset==-10||
                CandidateOffset==6||CandidateOffset==15);//if the current tile is in 1st column and the exception offset is being considered then return true
    }
    private static boolean IsSecondColumnExclusion(final int currentPosition,int CandidateOffset){
        return BoardUtils.SecondColumn[currentPosition] &&(CandidateOffset==-10||CandidateOffset==6);
    }
    private static boolean IsSeventhColumnExclusion(final int currentPosition,int CandidateOffset){
        return BoardUtils.SeventhColumn[currentPosition] && (CandidateOffset==-6||CandidateOffset==10);
    }
    private static boolean IsEighthColumnExclusion(final int currentPosition,int CandidateOffset){
        return BoardUtils.EighthColumn[currentPosition]&&(CandidateOffset==-15||CandidateOffset==-6||
                CandidateOffset==10||CandidateOffset==17);
    }

}
