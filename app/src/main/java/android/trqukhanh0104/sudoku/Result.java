package android.trqukhanh0104.sudoku;

import android.os.Bundle;

import android.app.Dialog;
import android.content.*;
import android.widget.TextView;
import android.content.Intent;

import static android.trqukhanh0104.sudoku.R.id.tev_Result;

/**
 * Created by trqukhanh0104 on 19/03/2017.
 */

public class Result extends Dialog {
    private boolean win;
    public Result(Context context, boolean win){
        super(context);
        this.win = win;
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        TextView textView = (TextView)findViewById(R.id.tev_Result);
        if(win){
            textView.setText(R.string.Result_Win_Summary);
            setTitle(R.string.Result_Win);
            setContentView(R.layout.result);
        }
        else{
            textView.setText(R.string.Result_Lose_Summary);
            setTitle(R.string.Result_Lose);
            setContentView(R.layout.result);
        }
    }
}
