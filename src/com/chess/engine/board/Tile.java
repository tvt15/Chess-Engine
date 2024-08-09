package com.chess.engine.board;
import com.chess.engine.pieces.Piece;
import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

//immutability means that once constructed it will remain constant and its instance can be reused making its
// data footprint smaller and easier to for clients to use and less caching required
public abstract class Tile {//we want to make this class immutable;
    private static final Map<Integer,EmptyTile> EMPTY_TILES_CACHE = createEmptyTiles();

    private static Map<Integer, EmptyTile> createEmptyTiles() {
        Map<Integer,EmptyTile> emptyTileMap=new HashMap<>();
        for(int i=0;i<BoardUtils.Num_Tiles;i++){
            emptyTileMap.put(i,new EmptyTile(i));
        }
        return ImmutableMap.copyOf(emptyTileMap);
        // we have used guava(google java library) here
        //we can also use (inbuilt)
        // Collections.unmodifiableMap(emptyTileMap);
    }
    public static Tile createTile(final int tileCoordinate,final Piece piece){//the only publicly accessible constructor of the class tile;
        return piece!=null? new OccupiedTile(tileCoordinate,piece): EMPTY_TILES_CACHE.get(tileCoordinate);
        //if piece is not null then create a new occupied tile with tile cord and piece
        // else get an already cached empty tile from before

    }

    protected final int tileCoordinate; //protected final(immutability)
    private Tile(final int tileCoordinate){
        this.tileCoordinate=tileCoordinate;
    }
    public abstract boolean isTileOccupied();

    public abstract Piece getPiece();

    public static final class EmptyTile extends Tile{
        private EmptyTile(final int coordinate){//final(immutability)

            super(coordinate);
        }
        @Override
        public boolean isTileOccupied(){
            return false;
        }

        @Override
        public Piece getPiece(){
            return null;
        }
        @Override
        public String toString(){
            return "-";
        }

    }
    public static final class OccupiedTile extends Tile{
        private final Piece pieceOnTile;//private final immutability
        private OccupiedTile(final int tileCoordinate, final Piece pieceOnTile){
            super(tileCoordinate);
            this.pieceOnTile=pieceOnTile;
        }
        @Override
        public boolean isTileOccupied(){
            return true;
        }
        @Override
        public Piece getPiece(){
            return this.pieceOnTile;
        }
        @Override
        public String toString(){
            return this.pieceOnTile.getPieceAlliance().isBlack()?this.pieceOnTile.toString().toLowerCase():
                    this.pieceOnTile.toString();
        }

    }

}
