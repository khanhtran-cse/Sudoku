package android.trqukhanh0104.sudoku;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.content.*;

/**
 * Created by trqukhanh0104 on 18/03/2017.
 */

public class Keypad extends Dialog{
    private final PuzzleView puzzleView;
    private final View key[];
    private View keypad;
    private final boolean[] used;

    public Keypad(Context context, PuzzleView puzzleView, boolean[] used){
        super(context);
        this.puzzleView = puzzleView;
        key = new View[10];
        this.used = used;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setTitle(R.string.Keypad_title);
        setContentView(R.layout.keypad);
        findView();
        setClickListener();
        setUsedButton();
    }

    public void findView(){
        key[0] = findViewById(R.id.keypad_0);
        key[1] = findViewById(R.id.keypad_1);
        key[2] = findViewById(R.id.keypad_2);
        key[3] = findViewById(R.id.keypad_3);
        key[4] = findViewById(R.id.keypad_4);
        key[5] = findViewById(R.id.keypad_5);
        key[6] = findViewById(R.id.keypad_6);
        key[7] = findViewById(R.id.keypad_7);
        key[8] = findViewById(R.id.keypad_8);
        key[9] = findViewById(R.id.keypad_9);
        keypad = findViewById(R.id.keypad);
    }

    public void setClickListener(){
        for(int i = 0;i<10;i++){
            final int t = i;
           key[i].setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view){
                   returnResult(t);
               }
           });
        }
        keypad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnResult(0);
            }
        });
    }

    private void returnResult(int v){
        puzzleView.setSelectedTileValue(v);
        this.dismiss();
    }

    private void setUsedButton(){
        for(int i=0;i<9;i++){
            if(used[i]){
                key[i+1].setBackgroundResource(R.color.keypad_invalid_button);
            }
        }
    }
}
