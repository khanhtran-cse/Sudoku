package android.trqukhanh0104.sudoku;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import android.content.*;
import android.view.*;

import static android.R.attr.keycode;
import static android.R.attr.width;

/**
 * Created by trqukhanh0104 on 15/03/2017.
 */

public class PuzzleView extends View {
    private static final String TAG ="Sudoku";
    private Game game;
    private float width;//the width of the tile
    private float height;//the height of the tile
    private int selX;//index of the selected tile
    private int selY;//index of the selected tile
    private final Rect selRect = new Rect();

    public PuzzleView(Context context){
        super(context);
        this.game = (Game) context;
        setFocusable(true);
        setFocusableInTouchMode(true);
    }

    @Override
    public void onSizeChanged(int w, int h, int oldW, int oldH){
        width = w/9;
        height = h/9;
        getRect(selX,selY,selRect);
        super.onSizeChanged(w,h,oldW,oldH);
    }

    private  void getRect(int x, int y, Rect rect){
        rect.set((int)(x*width),(int)(y*height),(int)(x*width + width),(int)(y*height + height));
    }

    @Override
    protected void onDraw(Canvas canvas){
        //Draw background
        Paint background= new Paint();
        background.setColor(getResources().getColor(R.color.puzzle_background));
        canvas.drawRect(0,0,getWidth(),getHeight(),background);

        //Draw line
        Paint dark = new Paint();
        Paint light = new Paint();
        light.setColor(getResources().getColor(R.color.puzzle_hilite));
        dark.setColor(getResources().getColor(R.color.puzzle_dark));
        float h = getHeight();
        float w = getWidth();
        for(int i = 0;i <=3;i++){
            canvas.drawLine(0,h/3*i,w,h/3*i,dark);
            canvas.drawLine(w/3*i,0,w/3*i,h,dark);
        }
        for(int i =1;i<9;i++){
            if(i%3==0){
                continue;
            }
            canvas.drawLine(0,h/9*i,w,h/9*i,light);
            canvas.drawLine(w/9*i,0,w/9*i,h,light);
        }

        // Draw the numbers...
        // Define color and style for numbers
        Paint foreground = new Paint(Paint.ANTI_ALIAS_FLAG);
        Paint const_tile_background = new Paint();
        const_tile_background.setColor(getResources().getColor(R.color.puzzle_hint_0));
        foreground.setColor(getResources().getColor(
                R.color.puzzle_foreground));
        foreground.setStyle(Paint.Style.FILL);
        foreground.setTextSize(height * 0.75f);
        foreground.setTextScaleX(width / height);
        foreground.setTextAlign(Paint.Align.CENTER);
        // Draw the number in the center of the tile
        Paint.FontMetrics fm = foreground.getFontMetrics();
        // Centering in X: use alignment (and X at midpoint)
        float x = width / 2;
        // Centering in Y: measure ascent/descent first
        float y = height/2 - (fm.ascent + fm.descent) / 2;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if(game.getTile(i,j) != 0){
                    if(game.isConstantTile(i,j)){
                        canvas.drawRect(i*width,j*height,i*width+width,j*height+height,const_tile_background);
                    }
                    canvas.drawText(String.valueOf(game.getTile(i,j)), i * width + x, j * height + y, foreground);
                }
            }
        }
        //Draw the selected tile
        Paint select = new Paint();
        select.setColor(getResources().getColor(R.color.puzzle_selected));
        canvas.drawRect(selRect,select);
    }

    private void selectTile(int x, int y){
        invalidate(selRect);
        selX = Math.min(Math.max(x,0),8);
        selY = Math.min(Math.max(y,0),8);
        getRect(selX,selY,selRect);
        invalidate(selRect);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        switch(keyCode){
            case KeyEvent.KEYCODE_DPAD_DOWN:
                selectTile(selX,selY -1);
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                selectTile(selX,selY + 1);
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                selectTile(selX + 1,selY);
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                selectTile(selX -1,selY);
                break;
            default:
                return super.onKeyDown(keyCode,event);
        }
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
         if(event.getAction() != MotionEvent.ACTION_DOWN){
             return super.onTouchEvent(event);
         }
         selX = (int) (event.getX()/width);
         selY = (int)  (event.getY()/height);
        selectTile(selX,selY);
        if(!game.isConstantTile(selX,selY)){
            Keypad keypad = new Keypad(game,this,game.getUsedAtTile(selX,selY));
            keypad.show();
        }

        return true;
    }

    public void setSelectedTileValue(int value){
        game.setTile(selX,selY,value);
        game.calculateUsed();
        invalidate(selRect);

        if(game.isFullTiles()){
            Result result = new Result(game,true);
            result.show();
        }
    }
}
