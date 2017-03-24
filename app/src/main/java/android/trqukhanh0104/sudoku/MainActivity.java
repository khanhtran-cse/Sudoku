package android.trqukhanh0104.sudoku;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import android.app.AlertDialog;
import android.content.DialogInterface;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private final String DIFF = "android.trqukhanh0104.sudoku.diff";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_NewGame = (Button)findViewById(R.id.btn_NewGame);
        btn_NewGame.setOnClickListener(this);
        Button btn_Continue = (Button) findViewById(R.id.btn_Continue);
        btn_Continue.setOnClickListener(this);
        Button btn_About = (Button) findViewById(R.id.btn_About);
        btn_About.setOnClickListener(this);
        Button btn_Settings = (Button) findViewById(R.id.btn_Settings);
        btn_Settings.setOnClickListener(this);
        Button btn_Quit = (Button) findViewById(R.id.btn_Quit);
        btn_Quit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.btn_NewGame:
               openDifficultChoiceTable();
                break;
            case R.id.btn_About:
                Intent intent = new Intent(this,About.class);
                startActivity(intent);
                break;
            case R.id.btn_Settings:
                Intent intentSetting = new Intent(this,Settings.class);
                startActivity(intentSetting);
                break;
            case R.id.btn_Quit:
                finish();
                break;
        }
    }

    private void openDifficultChoiceTable(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(R.string.Difficultly_title);
        dialog.setItems(R.array.difficultly, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startGame(which);
            }
        });
        dialog.show();
    }

    private void startGame(int diff){
        Intent i = new Intent(this,Game.class);
        i.putExtra(DIFF,diff);
        startActivity(i);
    }
}
