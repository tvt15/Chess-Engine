package com.chess.engine.board;

public class BoardUtils {
    public static final  boolean[] FirstColumn=innitColumn(0);//contains true on indexes for first column
    public static final boolean[] SecondColumn=innitColumn(1);
    public static final boolean[] SeventhColumn=innitColumn(6);
    public static final boolean[] EighthColumn =innitColumn(7);

    public static boolean[] SecondRow=innitrow(8);
    public static boolean[] SeventhRow=innitrow(48);

    public static final int Num_Tiles = 64;
    public static final int Num_Tiles_Per_Row=8;



    private BoardUtils(){
        throw new RuntimeException("You cant do me");
    }
    public static boolean[] innitColumn(int Col_num){
        final boolean[] col = new boolean[Num_Tiles];
        do {
            col[Col_num] = true;
            Col_num += Num_Tiles_Per_Row;
        }while(Col_num <Num_Tiles);
        return col;
    }
    public static boolean[] innitrow(int row_index){
        final boolean[] row= new boolean[Num_Tiles];
        do{
            row[row_index]=true;
            row_index++;
        }while(row_index%8==0);
        return row;
    }

    public static boolean isValidTileCoordinate(final int TileCoordinate) {
        return TileCoordinate>=0 && TileCoordinate<Num_Tiles;//if tile cordinate between [0 and 64) then valid cordinate
    }

}
