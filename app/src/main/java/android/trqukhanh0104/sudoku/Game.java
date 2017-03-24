package android.trqukhanh0104.sudoku;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class Game extends AppCompatActivity {
    private final String DIFF = "android.trqukhanh0104.sudoku.diff";
    private final String easyPuzzle =
            "360000000004230800000004200" +
                    "070460003820000014500013020" +
                    "001900000007048300000000045" ;
    private final String mediumPuzzle =
            "650000070000506000014000005" +
                    "007009000002314700000700800" +
                    "500000630000201000030000097" ;
    private final String hardPuzzle =
            "009000000080605020501078000" +
                    "000000700706040102004000000" +
                    "000720903090301080000000600" ;
    private int[][] tile = new int[9][9];
    private boolean[][][] used = new boolean[9][9][9];
    private final int DIFFICULT_EASY = 0;
    private final int DIFFICULT_MEDIUM =1;
    private final int DIFFICULT_HARD =2;
    private int diff;
    private String selPuzz;
    private int usedTilesNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for(int i = 0;i<9;i++){
            for(int j = 0;j< 9;j++){
                tile[i][j] = 0;
            }
        }
        usedTilesNumber=0;
        this.diff = getIntent().getIntExtra(DIFF,0);
        initializeTiles(diff);
        setContentView(new PuzzleView(this));
        calculateUsed();
    }

    public int getTile(int x, int y){
        if( x >=0 && x <=8 && y >=0 && y <=8){
            return this.tile[x][y];
        }
        return 0;
    }

    public void setTile(int x, int y, int value){
        if( x >=0 && x <=8 && y >=0 && y <=8 && value >=0 && value <=9){
            this.tile[x][y] = value;
            if(this.tile[x][y] ==0 && value != 0){
                usedTilesNumber++;
            }
            else if(this.tile[x][y] != 0 && value == 0){
                usedTilesNumber--;
            }
        }
    }

    public boolean[] getUsedAtTile(int x, int y){
        return this.used[x][y];
    }

    public void calculateUsed(){
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                for(int x=0;x<9;x++){
                    used[i][j][x] = false;
                }
            }
        }
        for(int i = 0;i<9;i++){
            for(int j=0;j<9;j++){
                //calculate with row and column
                for(int x =0;x<9;x++){
                    if(tile[i][x] != 0 ){
                        used[i][j][tile[i][x] -1] = true;
                    }
                    if(tile[x][j] != 0){
                        used[i][j][tile[x][j] -1] = true;
                    }
                }
                //calculate with grid 3*3
                int row,column;
                row = i/3*3;
                column=j/3*3;
                for(int x=row;x<row+3;x++){
                    for(int y=column;y<column+3;y++){
                        if(tile[x][y] != 0){
                            used[i][j][tile[x][y] -1] = true;
                        }
                    }
                }
            }
        }
    }

    private void initializeTiles(int diff){
        usedTilesNumber=0;
        switch(diff){
            case DIFFICULT_EASY:
                selPuzz = easyPuzzle;
                break;
            case DIFFICULT_MEDIUM:
                selPuzz = mediumPuzzle;
                break;
            case DIFFICULT_HARD:
                selPuzz = hardPuzzle;
            break;
            default:
                selPuzz = easyPuzzle;
        }
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                tile[i][j] = selPuzz.charAt(i*9+j) - '0';
                if(tile[i][j] != 0){
                    usedTilesNumber++;
                }
            }
        }
    }

    public boolean isConstantTile(int x, int y){
        if(x>=0 && x<=8 && y>=0 && y<=8){
            if(selPuzz.charAt(x*9+y) - '0' != 0){
                return true;
            }
        }
        return false;
    }

    public boolean isFullTiles(){
        Log.i("Sudoku",String.valueOf(this.usedTilesNumber));
        if(usedTilesNumber >= 81){
            return true;
        }
        return false;
    }
}
