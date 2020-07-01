package ir.papiloo.practicewriting;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.jar.Attributes;
import java.util.regex.Pattern;

import ir.papiloo.practicewriting.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class GameBoard extends AppCompatActivity implements View.OnClickListener {

    /* Views */
    TextView sTitleTxt, scoreTxt, txtanswer,txtWord;

    ProgressBar pb;
    Button letterButt1, letterButt2, letterButt3, letterButt4, letterButt5, letterButt6, letterButt7
            , letterButt8, letterButt9, letterButt10, letterButt11, letterButt12,btnHint;
    String beforeChar="";

    /* Variables */
    Timer gameTimer;
    float progress;
    List<String> wordsArray;
    List<String> charArray;
    String wordStr = "";
    int tapsCount = 0;
    String firstWord = "";
    String secondWord = "";
    String wordByCharacters = "";
    int randomCircle = 0;
    Button[] letterButtons;
    TextView[] letterTxts;
    MediaPlayer mp;
    Cursor Cursor;

    MarshMallowPermission mmp = new MarshMallowPermission(this);

    int lenght = 6;

    // ON START() ------------------------------------------------------------------------
    @Override
    protected void onStart() {
        super.onStart();

        // Reset score
        Configs.score = 0;
        scoreTxt.setText(String.valueOf(Configs.score));


        // Set progressBar and start the gameTimer
        pb = (ProgressBar) findViewById(R.id.gbProgressBar);
        progress = 0;
        pb.setProgress((int) progress);
        startGameTimer();


        // Get a random circle for letters
        Random r = new Random();
        randomCircle = r.nextInt(Configs.circlesArray.length);


        // Reset taps count
        tapsCount = -1;

        txtWord=findViewById(R.id.txtWord);
        // Get a random word from words string-array
        //lenght=
        getRandomWord();
    }

    // ON CREATE() ---------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_board);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

        // Hide ActionBar
        getSupportActionBar().hide();

        // Hide Status bar
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // mp = new MediaPlayer();

        // Get a List array of words

//        String[] wordsArr = getResources().getStringArray(R.array.english);
//        wordsArray = new ArrayList<String>(Arrays.asList(wordsArr));
//        Log.i("list wordsArray: ", String.valueOf(wordsArray));
//        Log.i("list size: ", String.valueOf(wordsArray.size()));



        ArrayList<String> mylist = new ArrayList<String>();
        String DATABASE_NAME = "EnglishWords.sqlite";
        String TABLE_NAME = "practice";
        btnHint=findViewById(R.id.btnHint);
        try{
            SQLiteDatabase mydb = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE,null);
            Cursor allrows  = mydb.rawQuery("SELECT * FROM "+  TABLE_NAME, null);
            if(allrows.moveToFirst()){
                do{
                    String ID = allrows.getString(0);
                    String word = allrows.getString(1);
                    String mean = allrows.getString(2);
                    mylist.add(word+"."+mean);
                    // Show values with Toast
//                    Toast.makeText(getApplicationContext(),NAME+"."+CITY , Toast.LENGTH_LONG).show();
                }
                while(allrows.moveToNext());
                wordsArray=mylist;
            }
            mydb.close();
        }catch(Exception e){
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
        }
        buildButton(lenght);
        // MARK: - RESET BUTTON ------------------------------------
        Button resetButt = (Button) findViewById(R.id.btnDelete);
        resetButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetWord();

                // Play a sound
                playSound("resetWord.mp3");
            }
        });

        // MARK: - BACK BUTTON ------------------------------------
        Button backButt = (Button) findViewById(R.id.gbBackButt);
        backButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameTimer.cancel();
                finish();
            }
        });

        btnHint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnHint.setText(wordByCharacters);
            }
        });

    }// end onCreate()

    //create count Button
    protected void buildButton(int lenght)
    {
        if (lenght == 3) {
            // Init Views
            sTitleTxt = (TextView) findViewById(R.id.gbScoreTxt);
            scoreTxt = (TextView) findViewById(R.id.gbPointsTxt);
            scoreTxt.setTypeface(Configs.juneGull);

            txtanswer = findViewById(R.id.txtanswer);
            //-----------------
            letterButt1 = (Button) findViewById(R.id.letterButt1);
            letterButt1.setTypeface(Configs.juneGull);
            letterButt1.setOnClickListener(this);
            letterButt1.setVisibility(View.VISIBLE);
            letterButt2 = (Button) findViewById(R.id.letterButt2);
            letterButt2.setTypeface(Configs.juneGull);
            letterButt2.setOnClickListener(this);
            letterButt2.setVisibility(View.VISIBLE);
            letterButt3 = (Button) findViewById(R.id.letterButt3);
            letterButt3.setTypeface(Configs.juneGull);
            letterButt3.setOnClickListener(this);
            letterButt3.setVisibility(View.VISIBLE);
            letterButt4 = (Button) findViewById(R.id.letterButt4);
            letterButt4.setTypeface(Configs.juneGull);
            letterButt4.setOnClickListener(this);
            letterButt4.setVisibility(View.INVISIBLE);
            letterButt5 = (Button) findViewById(R.id.letterButt5);
            letterButt5.setTypeface(Configs.juneGull);
            letterButt5.setOnClickListener(this);
            letterButt5.setVisibility(View.INVISIBLE);
            letterButt6 = (Button) findViewById(R.id.letterButt6);
            letterButt6.setTypeface(Configs.juneGull);
            letterButt6.setOnClickListener(this);
            letterButt6.setVisibility(View.INVISIBLE);
            letterButt7 = (Button) findViewById(R.id.letterButt7);
            letterButt7.setTypeface(Configs.juneGull);
            letterButt7.setOnClickListener(this);
            letterButt7.setVisibility(View.INVISIBLE);
            letterButt8 = (Button) findViewById(R.id.letterButt8);
            letterButt8.setTypeface(Configs.juneGull);
            letterButt8.setOnClickListener(this);
            letterButt8.setVisibility(View.INVISIBLE);
            letterButt9 = (Button) findViewById(R.id.letterButt9);
            letterButt9.setTypeface(Configs.juneGull);
            letterButt9.setOnClickListener(this);
            letterButt9.setVisibility(View.INVISIBLE);
            letterButt10 = (Button) findViewById(R.id.letterButt10);
            letterButt10.setTypeface(Configs.juneGull);
            letterButt10.setOnClickListener(this);
            letterButt10.setVisibility(View.INVISIBLE);
            letterButt11 = (Button) findViewById(R.id.letterButt11);
            letterButt11.setTypeface(Configs.juneGull);
            letterButt11.setOnClickListener(this);
            letterButt11.setVisibility(View.INVISIBLE);
            letterButt12 = (Button) findViewById(R.id.letterButt12);
            letterButt12.setTypeface(Configs.juneGull);
            letterButt12.setOnClickListener(this);
            letterButt12.setVisibility(View.INVISIBLE);

            //----------
            // Make an array of letter buttons
            letterButtons = new Button[3];
            letterButtons[0] = letterButt1;
            letterButtons[1] = letterButt2;
            letterButtons[2] = letterButt3;

        }
        if (lenght == 4) {
            // Init Views
            sTitleTxt = (TextView) findViewById(R.id.gbScoreTxt);
            scoreTxt = (TextView) findViewById(R.id.gbPointsTxt);
            scoreTxt.setTypeface(Configs.juneGull);

            txtanswer = findViewById(R.id.txtanswer);
            //-----------------
            letterButt1 = (Button) findViewById(R.id.letterButt1);
            letterButt1.setTypeface(Configs.juneGull);
            letterButt1.setOnClickListener(this);
            letterButt1.setVisibility(View.VISIBLE);
            letterButt2 = (Button) findViewById(R.id.letterButt2);
            letterButt2.setTypeface(Configs.juneGull);
            letterButt2.setOnClickListener(this);
            letterButt2.setVisibility(View.VISIBLE);
            letterButt3 = (Button) findViewById(R.id.letterButt3);
            letterButt3.setTypeface(Configs.juneGull);
            letterButt3.setOnClickListener(this);
            letterButt3.setVisibility(View.VISIBLE);
            letterButt4 = (Button) findViewById(R.id.letterButt4);
            letterButt4.setTypeface(Configs.juneGull);
            letterButt4.setOnClickListener(this);
            letterButt4.setVisibility(View.VISIBLE);
            letterButt5 = (Button) findViewById(R.id.letterButt5);
            letterButt5.setTypeface(Configs.juneGull);
            letterButt5.setOnClickListener(this);
            letterButt5.setVisibility(View.INVISIBLE);
            letterButt6 = (Button) findViewById(R.id.letterButt6);
            letterButt6.setTypeface(Configs.juneGull);
            letterButt6.setOnClickListener(this);
            letterButt6.setVisibility(View.INVISIBLE);
            letterButt7 = (Button) findViewById(R.id.letterButt7);
            letterButt7.setTypeface(Configs.juneGull);
            letterButt7.setOnClickListener(this);
            letterButt7.setVisibility(View.INVISIBLE);
            letterButt8 = (Button) findViewById(R.id.letterButt8);
            letterButt8.setTypeface(Configs.juneGull);
            letterButt8.setOnClickListener(this);
            letterButt8.setVisibility(View.INVISIBLE);
            letterButt9 = (Button) findViewById(R.id.letterButt9);
            letterButt9.setTypeface(Configs.juneGull);
            letterButt9.setOnClickListener(this);
            letterButt9.setVisibility(View.INVISIBLE);
            letterButt10 = (Button) findViewById(R.id.letterButt10);
            letterButt10.setTypeface(Configs.juneGull);
            letterButt10.setOnClickListener(this);
            letterButt10.setVisibility(View.INVISIBLE);
            letterButt11 = (Button) findViewById(R.id.letterButt11);
            letterButt11.setTypeface(Configs.juneGull);
            letterButt11.setOnClickListener(this);
            letterButt11.setVisibility(View.INVISIBLE);
            letterButt12 = (Button) findViewById(R.id.letterButt12);
            letterButt12.setTypeface(Configs.juneGull);
            letterButt12.setOnClickListener(this);
            letterButt12.setVisibility(View.INVISIBLE);

            //----------
            // Make an array of letter buttons
            letterButtons = new Button[4];
            letterButtons[0] = letterButt1;
            letterButtons[1] = letterButt2;
            letterButtons[2] = letterButt3;
            letterButtons[3] = letterButt4;


            // Make an array of letters on the top
//            letterTxts = new TextView[5];
//            letterTxts[0] = letter1;
//            letterTxts[1] = letter2;
//            letterTxts[2] = letter3;
//            letterTxts[3] = letter4;
//            letterTxts[4] = letter5;

        }
        if (lenght == 5) {
            // Init Views
            sTitleTxt = (TextView) findViewById(R.id.gbScoreTxt);
            scoreTxt = (TextView) findViewById(R.id.gbPointsTxt);
            scoreTxt.setTypeface(Configs.juneGull);

            txtanswer = findViewById(R.id.txtanswer);

            letterButt1 = (Button) findViewById(R.id.letterButt1);
            letterButt1.setTypeface(Configs.juneGull);
            letterButt1.setOnClickListener(this);
            letterButt1.setVisibility(View.VISIBLE);

            letterButt2 = (Button) findViewById(R.id.letterButt2);
            letterButt2.setTypeface(Configs.juneGull);
            letterButt2.setOnClickListener(this);
            letterButt2.setVisibility(View.VISIBLE);

            letterButt3 = (Button) findViewById(R.id.letterButt3);
            letterButt3.setTypeface(Configs.juneGull);
            letterButt3.setOnClickListener(this);
            letterButt3.setVisibility(View.VISIBLE);

            letterButt4 = (Button) findViewById(R.id.letterButt4);
            letterButt4.setTypeface(Configs.juneGull);
            letterButt4.setOnClickListener(this);
            letterButt4.setVisibility(View.VISIBLE);

            letterButt5 = (Button) findViewById(R.id.letterButt5);
            letterButt5.setTypeface(Configs.juneGull);
            letterButt5.setOnClickListener(this);
            letterButt5.setVisibility(View.VISIBLE);
            letterButt6 = (Button) findViewById(R.id.letterButt6);
            letterButt6.setTypeface(Configs.juneGull);
            letterButt6.setOnClickListener(this);
            letterButt6.setVisibility(View.INVISIBLE);
            letterButt7 = (Button) findViewById(R.id.letterButt7);
            letterButt7.setTypeface(Configs.juneGull);
            letterButt7.setOnClickListener(this);
            letterButt7.setVisibility(View.INVISIBLE);
            letterButt8 = (Button) findViewById(R.id.letterButt8);
            letterButt8.setTypeface(Configs.juneGull);
            letterButt8.setOnClickListener(this);
            letterButt8.setVisibility(View.INVISIBLE);
            letterButt9 = (Button) findViewById(R.id.letterButt9);
            letterButt9.setTypeface(Configs.juneGull);
            letterButt9.setOnClickListener(this);
            letterButt9.setVisibility(View.INVISIBLE);
            letterButt10 = (Button) findViewById(R.id.letterButt10);
            letterButt10.setTypeface(Configs.juneGull);
            letterButt10.setOnClickListener(this);
            letterButt10.setVisibility(View.INVISIBLE);
            letterButt11 = (Button) findViewById(R.id.letterButt11);
            letterButt11.setTypeface(Configs.juneGull);
            letterButt11.setOnClickListener(this);
            letterButt11.setVisibility(View.INVISIBLE);
            letterButt12 = (Button) findViewById(R.id.letterButt12);
            letterButt12.setTypeface(Configs.juneGull);
            letterButt12.setOnClickListener(this);
            letterButt12.setVisibility(View.INVISIBLE);

            //----------
            // Make an array of letter buttons
            letterButtons = new Button[5];
            letterButtons[0] = letterButt1;
            letterButtons[1] = letterButt2;
            letterButtons[2] = letterButt3;
            letterButtons[3] = letterButt4;
            letterButtons[4] = letterButt5;

        }
        if (lenght == 6) {
            // Init Views
            sTitleTxt = (TextView) findViewById(R.id.gbScoreTxt);
            scoreTxt = (TextView) findViewById(R.id.gbPointsTxt);
            scoreTxt.setTypeface(Configs.juneGull);

            txtanswer = findViewById(R.id.txtanswer);

            letterButt1 = (Button) findViewById(R.id.letterButt1);
            letterButt1.setTypeface(Configs.juneGull);
            letterButt1.setOnClickListener(this);
            letterButt1.setVisibility(View.VISIBLE);

            letterButt2 = (Button) findViewById(R.id.letterButt2);
            letterButt2.setTypeface(Configs.juneGull);
            letterButt2.setOnClickListener(this);
            letterButt2.setVisibility(View.VISIBLE);

            letterButt3 = (Button) findViewById(R.id.letterButt3);
            letterButt3.setTypeface(Configs.juneGull);
            letterButt3.setOnClickListener(this);
            letterButt3.setVisibility(View.VISIBLE);

            letterButt4 = (Button) findViewById(R.id.letterButt4);
            letterButt4.setTypeface(Configs.juneGull);
            letterButt4.setOnClickListener(this);
            letterButt4.setVisibility(View.VISIBLE);

            letterButt5 = (Button) findViewById(R.id.letterButt5);
            letterButt5.setTypeface(Configs.juneGull);
            letterButt5.setOnClickListener(this);
            letterButt5.setVisibility(View.VISIBLE);
            letterButt6 = (Button) findViewById(R.id.letterButt6);
            letterButt6.setTypeface(Configs.juneGull);
            letterButt6.setOnClickListener(this);
            letterButt6.setVisibility(View.VISIBLE);
            letterButt7 = (Button) findViewById(R.id.letterButt7);
            letterButt7.setTypeface(Configs.juneGull);
            letterButt7.setOnClickListener(this);
            letterButt7.setVisibility(View.INVISIBLE);
            letterButt8 = (Button) findViewById(R.id.letterButt8);
            letterButt8.setTypeface(Configs.juneGull);
            letterButt8.setOnClickListener(this);
            letterButt8.setVisibility(View.INVISIBLE);
            letterButt9 = (Button) findViewById(R.id.letterButt9);
            letterButt9.setTypeface(Configs.juneGull);
            letterButt9.setOnClickListener(this);
            letterButt9.setVisibility(View.INVISIBLE);
            letterButt10 = (Button) findViewById(R.id.letterButt10);
            letterButt10.setTypeface(Configs.juneGull);
            letterButt10.setOnClickListener(this);
            letterButt10.setVisibility(View.INVISIBLE);
            letterButt11 = (Button) findViewById(R.id.letterButt11);
            letterButt11.setTypeface(Configs.juneGull);
            letterButt11.setOnClickListener(this);
            letterButt11.setVisibility(View.INVISIBLE);
            letterButt12 = (Button) findViewById(R.id.letterButt12);
            letterButt12.setTypeface(Configs.juneGull);
            letterButt12.setOnClickListener(this);
            letterButt12.setVisibility(View.INVISIBLE);

            //----------
            // Make an array of letter buttons
            letterButtons = new Button[6];
            letterButtons[0] = letterButt1;
            letterButtons[1] = letterButt2;
            letterButtons[2] = letterButt3;
            letterButtons[3] = letterButt4;
            letterButtons[4] = letterButt5;
            letterButtons[5] = letterButt6;

        }
        if (lenght == 7) {
            // Init Views
            sTitleTxt = (TextView) findViewById(R.id.gbScoreTxt);
            scoreTxt = (TextView) findViewById(R.id.gbPointsTxt);
            scoreTxt.setTypeface(Configs.juneGull);

            txtanswer = findViewById(R.id.txtanswer);

            letterButt1 = (Button) findViewById(R.id.letterButt1);
            letterButt1.setTypeface(Configs.juneGull);
            letterButt1.setOnClickListener(this);
            letterButt1.setVisibility(View.VISIBLE);

            letterButt2 = (Button) findViewById(R.id.letterButt2);
            letterButt2.setTypeface(Configs.juneGull);
            letterButt2.setOnClickListener(this);
            letterButt2.setVisibility(View.VISIBLE);

            letterButt3 = (Button) findViewById(R.id.letterButt3);
            letterButt3.setTypeface(Configs.juneGull);
            letterButt3.setOnClickListener(this);
            letterButt3.setVisibility(View.VISIBLE);

            letterButt4 = (Button) findViewById(R.id.letterButt4);
            letterButt4.setTypeface(Configs.juneGull);
            letterButt4.setOnClickListener(this);
            letterButt4.setVisibility(View.VISIBLE);

            letterButt5 = (Button) findViewById(R.id.letterButt5);
            letterButt5.setTypeface(Configs.juneGull);
            letterButt5.setOnClickListener(this);
            letterButt5.setVisibility(View.VISIBLE);
            letterButt6 = (Button) findViewById(R.id.letterButt6);
            letterButt6.setTypeface(Configs.juneGull);
            letterButt6.setOnClickListener(this);
            letterButt6.setVisibility(View.VISIBLE);
            letterButt7 = (Button) findViewById(R.id.letterButt7);
            letterButt7.setTypeface(Configs.juneGull);
            letterButt7.setOnClickListener(this);
            letterButt7.setVisibility(View.VISIBLE);
            letterButt8 = (Button) findViewById(R.id.letterButt8);
            letterButt8.setTypeface(Configs.juneGull);
            letterButt8.setOnClickListener(this);
            letterButt8.setVisibility(View.INVISIBLE);
            letterButt9 = (Button) findViewById(R.id.letterButt9);
            letterButt9.setTypeface(Configs.juneGull);
            letterButt9.setOnClickListener(this);
            letterButt9.setVisibility(View.INVISIBLE);
            letterButt10 = (Button) findViewById(R.id.letterButt10);
            letterButt10.setTypeface(Configs.juneGull);
            letterButt10.setOnClickListener(this);
            letterButt10.setVisibility(View.INVISIBLE);
            letterButt11 = (Button) findViewById(R.id.letterButt11);
            letterButt11.setTypeface(Configs.juneGull);
            letterButt11.setOnClickListener(this);
            letterButt11.setVisibility(View.INVISIBLE);
            letterButt12 = (Button) findViewById(R.id.letterButt12);
            letterButt12.setTypeface(Configs.juneGull);
            letterButt12.setOnClickListener(this);
            letterButt12.setVisibility(View.INVISIBLE);

            //----------
            // Make an array of letter buttons
            letterButtons = new Button[7];
            letterButtons[0] = letterButt1;
            letterButtons[1] = letterButt2;
            letterButtons[2] = letterButt3;
            letterButtons[3] = letterButt4;
            letterButtons[4] = letterButt5;
            letterButtons[5] = letterButt6;
            letterButtons[6] = letterButt7;

        }
        if (lenght == 8) {
            // Init Views
            sTitleTxt = (TextView) findViewById(R.id.gbScoreTxt);
            scoreTxt = (TextView) findViewById(R.id.gbPointsTxt);
            scoreTxt.setTypeface(Configs.juneGull);

            txtanswer = findViewById(R.id.txtanswer);

            letterButt1 = (Button) findViewById(R.id.letterButt1);
            letterButt1.setTypeface(Configs.juneGull);
            letterButt1.setOnClickListener(this);
            letterButt1.setVisibility(View.VISIBLE);

            letterButt2 = (Button) findViewById(R.id.letterButt2);
            letterButt2.setTypeface(Configs.juneGull);
            letterButt2.setOnClickListener(this);
            letterButt2.setVisibility(View.VISIBLE);

            letterButt3 = (Button) findViewById(R.id.letterButt3);
            letterButt3.setTypeface(Configs.juneGull);
            letterButt3.setOnClickListener(this);
            letterButt3.setVisibility(View.VISIBLE);

            letterButt4 = (Button) findViewById(R.id.letterButt4);
            letterButt4.setTypeface(Configs.juneGull);
            letterButt4.setOnClickListener(this);
            letterButt4.setVisibility(View.VISIBLE);

            letterButt5 = (Button) findViewById(R.id.letterButt5);
            letterButt5.setTypeface(Configs.juneGull);
            letterButt5.setOnClickListener(this);
            letterButt5.setVisibility(View.VISIBLE);
            letterButt6 = (Button) findViewById(R.id.letterButt6);
            letterButt6.setTypeface(Configs.juneGull);
            letterButt6.setOnClickListener(this);
            letterButt6.setVisibility(View.VISIBLE);
            letterButt7 = (Button) findViewById(R.id.letterButt7);
            letterButt7.setTypeface(Configs.juneGull);
            letterButt7.setOnClickListener(this);
            letterButt7.setVisibility(View.VISIBLE);
            letterButt8 = (Button) findViewById(R.id.letterButt8);
            letterButt8.setTypeface(Configs.juneGull);
            letterButt8.setOnClickListener(this);
            letterButt8.setVisibility(View.VISIBLE);
            letterButt9 = (Button) findViewById(R.id.letterButt9);
            letterButt9.setTypeface(Configs.juneGull);
            letterButt9.setOnClickListener(this);
            letterButt9.setVisibility(View.INVISIBLE);
            letterButt10 = (Button) findViewById(R.id.letterButt10);
            letterButt10.setTypeface(Configs.juneGull);
            letterButt10.setOnClickListener(this);
            letterButt10.setVisibility(View.INVISIBLE);
            letterButt11 = (Button) findViewById(R.id.letterButt11);
            letterButt11.setTypeface(Configs.juneGull);
            letterButt11.setOnClickListener(this);
            letterButt11.setVisibility(View.INVISIBLE);
            letterButt12 = (Button) findViewById(R.id.letterButt12);
            letterButt12.setTypeface(Configs.juneGull);
            letterButt12.setOnClickListener(this);
            letterButt12.setVisibility(View.INVISIBLE);

            //----------
            // Make an array of letter buttons
            letterButtons = new Button[8];
            letterButtons[0] = letterButt1;
            letterButtons[1] = letterButt2;
            letterButtons[2] = letterButt3;
            letterButtons[3] = letterButt4;
            letterButtons[4] = letterButt5;
            letterButtons[5] = letterButt6;
            letterButtons[6] = letterButt7;
            letterButtons[7] = letterButt8;


        }
        if (lenght == 9) {
            // Init Views
            sTitleTxt = (TextView) findViewById(R.id.gbScoreTxt);
            scoreTxt = (TextView) findViewById(R.id.gbPointsTxt);
            scoreTxt.setTypeface(Configs.juneGull);

            txtanswer = findViewById(R.id.txtanswer);

            letterButt1 = (Button) findViewById(R.id.letterButt1);
            letterButt1.setTypeface(Configs.juneGull);
            letterButt1.setOnClickListener(this);
            letterButt1.setVisibility(View.VISIBLE);

            letterButt2 = (Button) findViewById(R.id.letterButt2);
            letterButt2.setTypeface(Configs.juneGull);
            letterButt2.setOnClickListener(this);
            letterButt2.setVisibility(View.VISIBLE);

            letterButt3 = (Button) findViewById(R.id.letterButt3);
            letterButt3.setTypeface(Configs.juneGull);
            letterButt3.setOnClickListener(this);
            letterButt3.setVisibility(View.VISIBLE);

            letterButt4 = (Button) findViewById(R.id.letterButt4);
            letterButt4.setTypeface(Configs.juneGull);
            letterButt4.setOnClickListener(this);
            letterButt4.setVisibility(View.VISIBLE);

            letterButt5 = (Button) findViewById(R.id.letterButt5);
            letterButt5.setTypeface(Configs.juneGull);
            letterButt5.setOnClickListener(this);
            letterButt5.setVisibility(View.VISIBLE);
            letterButt6 = (Button) findViewById(R.id.letterButt6);
            letterButt6.setTypeface(Configs.juneGull);
            letterButt6.setOnClickListener(this);
            letterButt6.setVisibility(View.VISIBLE);
            letterButt7 = (Button) findViewById(R.id.letterButt7);
            letterButt7.setTypeface(Configs.juneGull);
            letterButt7.setOnClickListener(this);
            letterButt7.setVisibility(View.VISIBLE);
            letterButt8 = (Button) findViewById(R.id.letterButt8);
            letterButt8.setTypeface(Configs.juneGull);
            letterButt8.setOnClickListener(this);
            letterButt8.setVisibility(View.VISIBLE);
            letterButt9 = (Button) findViewById(R.id.letterButt9);
            letterButt9.setTypeface(Configs.juneGull);
            letterButt9.setOnClickListener(this);
            letterButt9.setVisibility(View.VISIBLE);
            letterButt10 = (Button) findViewById(R.id.letterButt10);
            letterButt10.setTypeface(Configs.juneGull);
            letterButt10.setOnClickListener(this);
            letterButt10.setVisibility(View.INVISIBLE);
            letterButt11 = (Button) findViewById(R.id.letterButt11);
            letterButt11.setTypeface(Configs.juneGull);
            letterButt11.setOnClickListener(this);
            letterButt11.setVisibility(View.INVISIBLE);
            letterButt12 = (Button) findViewById(R.id.letterButt12);
            letterButt12.setTypeface(Configs.juneGull);
            letterButt12.setOnClickListener(this);
            letterButt12.setVisibility(View.INVISIBLE);

            //----------
            // Make an array of letter buttons
            letterButtons = new Button[9];
            letterButtons[0] = letterButt1;
            letterButtons[1] = letterButt2;
            letterButtons[2] = letterButt3;
            letterButtons[3] = letterButt4;
            letterButtons[4] = letterButt5;
            letterButtons[5] = letterButt6;
            letterButtons[6] = letterButt7;
            letterButtons[7] = letterButt8;
            letterButtons[8] = letterButt9;
        }
        if (lenght == 10) {
            // Init Views
            sTitleTxt = (TextView) findViewById(R.id.gbScoreTxt);
            scoreTxt = (TextView) findViewById(R.id.gbPointsTxt);
            scoreTxt.setTypeface(Configs.juneGull);

            txtanswer = findViewById(R.id.txtanswer);

            letterButt1 = (Button) findViewById(R.id.letterButt1);
            letterButt1.setTypeface(Configs.juneGull);
            letterButt1.setOnClickListener(this);
            letterButt1.setVisibility(View.VISIBLE);

            letterButt2 = (Button) findViewById(R.id.letterButt2);
            letterButt2.setTypeface(Configs.juneGull);
            letterButt2.setOnClickListener(this);
            letterButt2.setVisibility(View.VISIBLE);

            letterButt3 = (Button) findViewById(R.id.letterButt3);
            letterButt3.setTypeface(Configs.juneGull);
            letterButt3.setOnClickListener(this);
            letterButt3.setVisibility(View.VISIBLE);

            letterButt4 = (Button) findViewById(R.id.letterButt4);
            letterButt4.setTypeface(Configs.juneGull);
            letterButt4.setOnClickListener(this);
            letterButt4.setVisibility(View.VISIBLE);

            letterButt5 = (Button) findViewById(R.id.letterButt5);
            letterButt5.setTypeface(Configs.juneGull);
            letterButt5.setOnClickListener(this);
            letterButt5.setVisibility(View.VISIBLE);

            letterButt6 = (Button) findViewById(R.id.letterButt6);
            letterButt6.setTypeface(Configs.juneGull);
            letterButt6.setOnClickListener(this);
            letterButt6.setVisibility(View.VISIBLE);
            letterButt7 = (Button) findViewById(R.id.letterButt7);
            letterButt7.setTypeface(Configs.juneGull);
            letterButt7.setOnClickListener(this);
            letterButt7.setVisibility(View.VISIBLE);
            letterButt8 = (Button) findViewById(R.id.letterButt8);
            letterButt8.setTypeface(Configs.juneGull);
            letterButt8.setOnClickListener(this);
            letterButt8.setVisibility(View.VISIBLE);
            letterButt9 = (Button) findViewById(R.id.letterButt9);
            letterButt9.setTypeface(Configs.juneGull);
            letterButt9.setOnClickListener(this);
            letterButt9.setVisibility(View.VISIBLE);
            letterButt10 = (Button) findViewById(R.id.letterButt10);
            letterButt10.setTypeface(Configs.juneGull);
            letterButt10.setOnClickListener(this);
            letterButt10.setVisibility(View.VISIBLE);
            letterButt11 = (Button) findViewById(R.id.letterButt11);
            letterButt11.setTypeface(Configs.juneGull);
            letterButt11.setOnClickListener(this);
            letterButt11.setVisibility(View.INVISIBLE);
            letterButt12 = (Button) findViewById(R.id.letterButt12);
            letterButt12.setTypeface(Configs.juneGull);
            letterButt12.setOnClickListener(this);
            letterButt12.setVisibility(View.INVISIBLE);

            //----------
            // Make an array of letter buttons
            letterButtons = new Button[10];
            letterButtons[0] = letterButt1;
            letterButtons[1] = letterButt2;
            letterButtons[2] = letterButt3;
            letterButtons[3] = letterButt4;
            letterButtons[4] = letterButt5;
            letterButtons[5] = letterButt6;
            letterButtons[6] = letterButt7;
            letterButtons[7] = letterButt8;
            letterButtons[8] = letterButt9;
            letterButtons[9] = letterButt10;
        }
        if (lenght == 11) {
            // Init Views
            sTitleTxt = (TextView) findViewById(R.id.gbScoreTxt);
            scoreTxt = (TextView) findViewById(R.id.gbPointsTxt);
            scoreTxt.setTypeface(Configs.juneGull);

            txtanswer = findViewById(R.id.txtanswer);

            letterButt1 = (Button) findViewById(R.id.letterButt1);
            letterButt1.setTypeface(Configs.juneGull);
            letterButt1.setOnClickListener(this);
            letterButt1.setVisibility(View.VISIBLE);

            letterButt2 = (Button) findViewById(R.id.letterButt2);
            letterButt2.setTypeface(Configs.juneGull);
            letterButt2.setOnClickListener(this);
            letterButt2.setVisibility(View.VISIBLE);

            letterButt3 = (Button) findViewById(R.id.letterButt3);
            letterButt3.setTypeface(Configs.juneGull);
            letterButt3.setOnClickListener(this);
            letterButt3.setVisibility(View.VISIBLE);

            letterButt4 = (Button) findViewById(R.id.letterButt4);
            letterButt4.setTypeface(Configs.juneGull);
            letterButt4.setOnClickListener(this);
            letterButt4.setVisibility(View.VISIBLE);

            letterButt5 = (Button) findViewById(R.id.letterButt5);
            letterButt5.setTypeface(Configs.juneGull);
            letterButt5.setOnClickListener(this);
            letterButt5.setVisibility(View.VISIBLE);
            letterButt6 = (Button) findViewById(R.id.letterButt6);
            letterButt6.setTypeface(Configs.juneGull);
            letterButt6.setOnClickListener(this);
            letterButt6.setVisibility(View.VISIBLE);
            letterButt7 = (Button) findViewById(R.id.letterButt7);
            letterButt7.setTypeface(Configs.juneGull);
            letterButt7.setOnClickListener(this);
            letterButt7.setVisibility(View.VISIBLE);
            letterButt8 = (Button) findViewById(R.id.letterButt8);
            letterButt8.setTypeface(Configs.juneGull);
            letterButt8.setOnClickListener(this);
            letterButt8.setVisibility(View.VISIBLE);
            letterButt9 = (Button) findViewById(R.id.letterButt9);
            letterButt9.setTypeface(Configs.juneGull);
            letterButt9.setOnClickListener(this);
            letterButt9.setVisibility(View.VISIBLE);
            letterButt10 = (Button) findViewById(R.id.letterButt10);
            letterButt10.setTypeface(Configs.juneGull);
            letterButt10.setOnClickListener(this);
            letterButt10.setVisibility(View.VISIBLE);
            letterButt11 = (Button) findViewById(R.id.letterButt11);
            letterButt11.setTypeface(Configs.juneGull);
            letterButt11.setOnClickListener(this);
            letterButt11.setVisibility(View.VISIBLE);
            letterButt12 = (Button) findViewById(R.id.letterButt12);
            letterButt12.setTypeface(Configs.juneGull);
            letterButt12.setOnClickListener(this);
            letterButt12.setVisibility(View.INVISIBLE);

            //----------
            // Make an array of letter buttons
            letterButtons = new Button[11];
            letterButtons[0] = letterButt1;
            letterButtons[1] = letterButt2;
            letterButtons[2] = letterButt3;
            letterButtons[3] = letterButt4;
            letterButtons[4] = letterButt5;
            letterButtons[5] = letterButt6;
            letterButtons[6] = letterButt7;
            letterButtons[7] = letterButt8;
            letterButtons[8] = letterButt9;
            letterButtons[9] = letterButt10;
            letterButtons[10] = letterButt11;

        }
        if (lenght == 12) {
            // Init Views
            sTitleTxt = (TextView) findViewById(R.id.gbScoreTxt);
            scoreTxt = (TextView) findViewById(R.id.gbPointsTxt);
            scoreTxt.setTypeface(Configs.juneGull);

            txtanswer = findViewById(R.id.txtanswer);

            letterButt1 = (Button) findViewById(R.id.letterButt1);
            letterButt1.setTypeface(Configs.juneGull);
            letterButt1.setOnClickListener(this);
            letterButt1.setVisibility(View.VISIBLE);

            letterButt2 = (Button) findViewById(R.id.letterButt2);
            letterButt2.setTypeface(Configs.juneGull);
            letterButt2.setOnClickListener(this);
            letterButt2.setVisibility(View.VISIBLE);

            letterButt3 = (Button) findViewById(R.id.letterButt3);
            letterButt3.setTypeface(Configs.juneGull);
            letterButt3.setOnClickListener(this);
            letterButt3.setVisibility(View.VISIBLE);

            letterButt4 = (Button) findViewById(R.id.letterButt4);
            letterButt4.setTypeface(Configs.juneGull);
            letterButt4.setOnClickListener(this);
            letterButt4.setVisibility(View.VISIBLE);

            letterButt5 = (Button) findViewById(R.id.letterButt5);
            letterButt5.setTypeface(Configs.juneGull);
            letterButt5.setOnClickListener(this);
            letterButt5.setVisibility(View.VISIBLE);
            letterButt6 = (Button) findViewById(R.id.letterButt6);
            letterButt6.setTypeface(Configs.juneGull);
            letterButt6.setOnClickListener(this);
            letterButt6.setVisibility(View.VISIBLE);
            letterButt7 = (Button) findViewById(R.id.letterButt7);
            letterButt7.setTypeface(Configs.juneGull);
            letterButt7.setOnClickListener(this);
            letterButt7.setVisibility(View.VISIBLE);
            letterButt8 = (Button) findViewById(R.id.letterButt8);
            letterButt8.setTypeface(Configs.juneGull);
            letterButt8.setOnClickListener(this);
            letterButt8.setVisibility(View.VISIBLE);
            letterButt9 = (Button) findViewById(R.id.letterButt9);
            letterButt9.setTypeface(Configs.juneGull);
            letterButt9.setOnClickListener(this);
            letterButt9.setVisibility(View.VISIBLE);
            letterButt10 = (Button) findViewById(R.id.letterButt10);
            letterButt10.setTypeface(Configs.juneGull);
            letterButt10.setOnClickListener(this);
            letterButt10.setVisibility(View.VISIBLE);
            letterButt11 = (Button) findViewById(R.id.letterButt11);
            letterButt11.setTypeface(Configs.juneGull);
            letterButt11.setOnClickListener(this);
            letterButt11.setVisibility(View.VISIBLE);
            letterButt12 = (Button) findViewById(R.id.letterButt12);
            letterButt12.setTypeface(Configs.juneGull);
            letterButt12.setOnClickListener(this);
            letterButt12.setVisibility(View.VISIBLE);

            //----------
            // Make an array of letter buttons
            letterButtons = new Button[12];
            letterButtons[0] = letterButt1;
            letterButtons[1] = letterButt2;
            letterButtons[2] = letterButt3;
            letterButtons[3] = letterButt4;
            letterButtons[4] = letterButt5;
            letterButtons[5] = letterButt6;
            letterButtons[6] = letterButt7;
            letterButtons[7] = letterButt8;
            letterButtons[8] = letterButt9;
            letterButtons[9] = letterButt10;
            letterButtons[10] = letterButt11;
            letterButtons[11] = letterButt12;


        }
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    // MARK: - RESET LETTER BUTTONS ------------------------------------------------------
    void resetLetterButtons() {

        for (int i = 0; i<lenght; i++) {
            letterButtons[i].setEnabled(true);
            letterButtons[i].setBackgroundResource(Configs.circlesArray[randomCircle]);
            letterButtons[i].setTextColor(Color.parseColor("#ffffff"));
        }

        // Reset letters textViews on the top
        //resetLettersTxt();
    }

    // MARK: - RESET LETTERS ON THE TOP ------------------------------------------------------
//    void resetLettersTxt() {
//        for (int i = 0; i<5; i++) {
//            letterTxts[i].setText("");
//            letterTxts[i].setBackgroundResource(R.drawable.circle_corner_white);
//        }
//    }


    // MARK: - GET A RANDOM WORD ------------------------------------------------------------

    int getRandomWord() {

        // Get a random circle for letters
        Random r = new Random();
        randomCircle = r.nextInt(Configs.circlesArray.length);
        // Log.i("log-", "RAND CIRCLE: " + randomCircle);


        // Get a random word from the string-arrays
        String randomWord = wordsArray.get(new Random().nextInt(wordsArray.size()));
//        String randomWord = mylist.get(new Random().nextInt(mylist.size()));

        wordStr = randomWord;
        Log.i("log-", "RANDOM WORD: " + wordStr);


        // Get an array of words (if there are multiple words
        Configs.stringsArray = new ArrayList<String>();

        String w = "";
        if (wordStr.contains(".")) {
            String[] one = wordStr.split(Pattern.quote("."));
            for (String word : one) {
                Configs.stringsArray.add(word);
                w=one[0];
                //lenght=w.length();
                txtanswer.setText(one[1]);
            }
            btnHint.setText(w);
            Log.i("log-", "\n\nWORDS ARRAY: " + Configs.stringsArray);

        } else {
            //Log.i("log-", "SINGLE WORD: " + wordStr);
            Configs.stringsArray.add(wordStr);
            w=wordStr;
        }

        lenght=w.length();
        buildButton(lenght);
        // Get the complete word as a List of characters
        charArray = new ArrayList<String>();
        String[] chArr = w.split("");

        wordByCharacters=w;
        if(lenght == 3)
        {
            for(int i=0; i<4; i++) {
                String c = chArr[i];
                charArray.add(c);
            }
        }
        if(lenght == 4)
        {
            for(int i=0; i<5; i++) {
                String c = chArr[i];
                charArray.add(c);
            }
        }
        if(lenght == 5)
        {
            for(int i=0; i<6; i++) {
                String c = chArr[i];
                charArray.add(c);
            }
        }
        if(lenght == 6)
        {
            for(int i=0; i<7; i++) {
                String c = chArr[i];
                charArray.add(c);
            }
        }
        if(lenght == 7)
        {
            for(int i=0; i<8; i++) {
                String c = chArr[i];
                charArray.add(c);
            }
        }
        if(lenght == 8)
        {
            for(int i=0; i<9; i++) {
                String c = chArr[i];
                charArray.add(c);
            }
        }
        if(lenght == 9)
        {
            for(int i=0; i<10; i++) {
                String c = chArr[i];
                charArray.add(c);
            }
        }
        if(lenght == 10)
        {
            for(int i=0; i<11; i++) {
                String c = chArr[i];
                charArray.add(c);
            }
        }
        if(lenght == 11)
        {
            for(int i=0; i<12; i++) {
                String c = chArr[i];
                charArray.add(c);
            }
        }
        if(lenght == 12)
        {
            for(int i=0; i<13; i++) {
                String c = chArr[i];
                charArray.add(c);
            }
        }

        charArray.remove(0);
        Log.i("log-", "CHARS ARRAY: " + charArray);

        // Get Random characthers function
        getRandomChar();
        return lenght;
    }

    // MARK: - GET RANDOM CHARACTERS --------------------------------------------------------
    void getRandomChar() {
        // Get a random combination that displays characters on the Game Board

        if(lenght==3) {
            Random r = new Random();
            int randomCombination = r.nextInt(3);
            switch (randomCombination) {
                case 0:
                    letterButtons[1].setText(charArray.get(0));
                    letterButtons[0].setText(charArray.get(1));
                    letterButtons[2].setText(charArray.get(2));
                    break;
                case 1:
                    letterButtons[2].setText(charArray.get(0));
                    letterButtons[0].setText(charArray.get(1));
                    letterButtons[1].setText(charArray.get(2));
                    break;
                case 2:
                    letterButtons[0].setText(charArray.get(0));
                    letterButtons[2].setText(charArray.get(1));
                    letterButtons[1].setText(charArray.get(2));
                    break;
            }
        }
        if(lenght==4) {
            Random r = new Random();
            int randomCombination = r.nextInt(3);
            switch (randomCombination) {
                case 0:
                    letterButtons[1].setText(charArray.get(0));
                    letterButtons[0].setText(charArray.get(1));
                    letterButtons[3].setText(charArray.get(2));
                    letterButtons[2].setText(charArray.get(3));
                    break;
                case 1:
                    letterButtons[3].setText(charArray.get(0));
                    letterButtons[0].setText(charArray.get(1));
                    letterButtons[2].setText(charArray.get(2));
                    letterButtons[1].setText(charArray.get(3));
                    break;
                case 2:
                    letterButtons[2].setText(charArray.get(0));
                    letterButtons[1].setText(charArray.get(1));
                    letterButtons[0].setText(charArray.get(2));
                    letterButtons[3].setText(charArray.get(3));
                    break;
            }

        }
        if(lenght==5) {
            Random r = new Random();
            int randomCombination = r.nextInt(3);
            switch (randomCombination) {
                case 0:

                    letterButtons[1].setText(charArray.get(0));
                    letterButtons[0].setText(charArray.get(1));
                    letterButtons[4].setText(charArray.get(2));
                    letterButtons[2].setText(charArray.get(3));
                    letterButtons[3].setText(charArray.get(4));
                    break;
                case 1:

                    letterButtons[3].setText(charArray.get(0));
                    letterButtons[0].setText(charArray.get(1));
                    letterButtons[4].setText(charArray.get(2));
                    letterButtons[1].setText(charArray.get(3));
                    letterButtons[2].setText(charArray.get(4));
                    break;
                case 2:

                    letterButtons[4].setText(charArray.get(0));
                    letterButtons[2].setText(charArray.get(1));
                    letterButtons[0].setText(charArray.get(2));
                    letterButtons[3].setText(charArray.get(3));
                    letterButtons[1].setText(charArray.get(4));
                    break;
            }
        }
        if(lenght==6) {
            Random r = new Random();
            int randomCombination = r.nextInt(3);
            switch (randomCombination) {
                case 0:
                    letterButtons[1].setText(charArray.get(0));
                    letterButtons[0].setText(charArray.get(1));
                    letterButtons[4].setText(charArray.get(2));
                    letterButtons[2].setText(charArray.get(3));
                    letterButtons[3].setText(charArray.get(4));
                    letterButtons[5].setText(charArray.get(5));
                    break;
                case 1:
                    letterButtons[3].setText(charArray.get(0));
                    letterButtons[0].setText(charArray.get(1));
                    letterButtons[4].setText(charArray.get(2));
                    letterButtons[5].setText(charArray.get(3));
                    letterButtons[2].setText(charArray.get(4));
                    letterButtons[1].setText(charArray.get(5));
                    break;
                case 2:
                    letterButtons[4].setText(charArray.get(0));
                    letterButtons[1].setText(charArray.get(1));
                    letterButtons[0].setText(charArray.get(2));
                    letterButtons[3].setText(charArray.get(3));
                    letterButtons[5].setText(charArray.get(4));
                    letterButtons[2].setText(charArray.get(5));
                    break;
            }
        }
        if(lenght==7) {
            Random r = new Random();
            int randomCombination = r.nextInt(3);
            switch (randomCombination) {
                case 0:
                    letterButtons[1].setText(charArray.get(0));
                    letterButtons[0].setText(charArray.get(1));
                    letterButtons[4].setText(charArray.get(2));
                    letterButtons[2].setText(charArray.get(3));
                    letterButtons[3].setText(charArray.get(4));
                    letterButtons[5].setText(charArray.get(5));
                    letterButtons[6].setText(charArray.get(6));

                    break;
                case 1:
                    letterButtons[3].setText(charArray.get(0));
                    letterButtons[0].setText(charArray.get(1));
                    letterButtons[4].setText(charArray.get(2));
                    letterButtons[5].setText(charArray.get(3));
                    letterButtons[2].setText(charArray.get(4));
                    letterButtons[6].setText(charArray.get(5));
                    letterButtons[1].setText(charArray.get(6));

                    break;
                case 2:
                    letterButtons[4].setText(charArray.get(0));
                    letterButtons[1].setText(charArray.get(1));
                    letterButtons[0].setText(charArray.get(2));
                    letterButtons[3].setText(charArray.get(3));
                    letterButtons[6].setText(charArray.get(4));
                    letterButtons[2].setText(charArray.get(5));
                    letterButtons[5].setText(charArray.get(6));

                    break;
            }
        }
        if(lenght==8) {
            Random r = new Random();
            int randomCombination = r.nextInt(3);
            switch (randomCombination) {
                case 0:
                    letterButtons[1].setText(charArray.get(0));
                    letterButtons[0].setText(charArray.get(1));
                    letterButtons[4].setText(charArray.get(2));
                    letterButtons[2].setText(charArray.get(3));
                    letterButtons[3].setText(charArray.get(4));
                    letterButtons[5].setText(charArray.get(5));
                    letterButtons[7].setText(charArray.get(6));
                    letterButtons[6].setText(charArray.get(7));

                    break;
                case 1:
                    letterButtons[3].setText(charArray.get(0));
                    letterButtons[0].setText(charArray.get(1));
                    letterButtons[4].setText(charArray.get(2));
                    letterButtons[5].setText(charArray.get(3));
                    letterButtons[7].setText(charArray.get(4));
                    letterButtons[1].setText(charArray.get(5));
                    letterButtons[6].setText(charArray.get(6));
                    letterButtons[2].setText(charArray.get(7));

                    break;
                case 2:
                    letterButtons[4].setText(charArray.get(0));
                    letterButtons[1].setText(charArray.get(1));
                    letterButtons[6].setText(charArray.get(2));
                    letterButtons[3].setText(charArray.get(3));
                    letterButtons[5].setText(charArray.get(4));
                    letterButtons[2].setText(charArray.get(5));
                    letterButtons[0].setText(charArray.get(6));
                    letterButtons[7].setText(charArray.get(7));

                    break;
            }
        }
        if(lenght==9) {
            Random r = new Random();
            int randomCombination = r.nextInt(3);
            switch (randomCombination) {
                case 0:
                    letterButtons[1].setText(charArray.get(0));
                    letterButtons[0].setText(charArray.get(1));
                    letterButtons[4].setText(charArray.get(2));
                    letterButtons[2].setText(charArray.get(3));
                    letterButtons[3].setText(charArray.get(4));
                    letterButtons[5].setText(charArray.get(5));
                    letterButtons[8].setText(charArray.get(6));
                    letterButtons[6].setText(charArray.get(7));
                    letterButtons[7].setText(charArray.get(8));
                    break;
                case 1:
                    letterButtons[3].setText(charArray.get(0));
                    letterButtons[0].setText(charArray.get(1));
                    letterButtons[4].setText(charArray.get(2));
                    letterButtons[5].setText(charArray.get(3));
                    letterButtons[7].setText(charArray.get(4));
                    letterButtons[8].setText(charArray.get(5));
                    letterButtons[6].setText(charArray.get(6));
                    letterButtons[2].setText(charArray.get(7));
                    letterButtons[1].setText(charArray.get(8));
                    break;
                case 2:
                    letterButtons[4].setText(charArray.get(0));
                    letterButtons[1].setText(charArray.get(1));
                    letterButtons[6].setText(charArray.get(2));
                    letterButtons[3].setText(charArray.get(3));
                    letterButtons[5].setText(charArray.get(4));
                    letterButtons[2].setText(charArray.get(5));
                    letterButtons[0].setText(charArray.get(6));
                    letterButtons[7].setText(charArray.get(7));
                    letterButtons[8].setText(charArray.get(8));
                    break;
            }
        }
        if(lenght==10) {
            Random r = new Random();
            int randomCombination = r.nextInt(3);
            switch (randomCombination) {
                case 0:
                    letterButtons[1].setText(charArray.get(0));
                    letterButtons[0].setText(charArray.get(1));
                    letterButtons[4].setText(charArray.get(2));
                    letterButtons[2].setText(charArray.get(3));
                    letterButtons[3].setText(charArray.get(4));
                    letterButtons[5].setText(charArray.get(5));
                    letterButtons[8].setText(charArray.get(6));
                    letterButtons[6].setText(charArray.get(7));
                    letterButtons[7].setText(charArray.get(8));
                    letterButtons[9].setText(charArray.get(9));
                    break;
                case 1:
                    letterButtons[3].setText(charArray.get(0));
                    letterButtons[0].setText(charArray.get(1));
                    letterButtons[4].setText(charArray.get(2));
                    letterButtons[9].setText(charArray.get(3));
                    letterButtons[7].setText(charArray.get(4));
                    letterButtons[8].setText(charArray.get(5));
                    letterButtons[6].setText(charArray.get(6));
                    letterButtons[2].setText(charArray.get(7));
                    letterButtons[1].setText(charArray.get(8));
                    letterButtons[5].setText(charArray.get(9));
                    break;
                case 2:
                    letterButtons[4].setText(charArray.get(0));
                    letterButtons[1].setText(charArray.get(1));
                    letterButtons[6].setText(charArray.get(2));
                    letterButtons[3].setText(charArray.get(3));
                    letterButtons[5].setText(charArray.get(4));
                    letterButtons[2].setText(charArray.get(5));
                    letterButtons[9].setText(charArray.get(6));
                    letterButtons[7].setText(charArray.get(7));
                    letterButtons[8].setText(charArray.get(8));
                    letterButtons[0].setText(charArray.get(9));
                    break;
            }
        }
        if(lenght==11) {
            Random r = new Random();
            int randomCombination = r.nextInt(3);
            switch (randomCombination) {
                case 0:
                    letterButtons[1].setText(charArray.get(0));
                    letterButtons[0].setText(charArray.get(1));
                    letterButtons[4].setText(charArray.get(2));
                    letterButtons[2].setText(charArray.get(3));
                    letterButtons[3].setText(charArray.get(4));
                    letterButtons[5].setText(charArray.get(5));
                    letterButtons[8].setText(charArray.get(6));
                    letterButtons[6].setText(charArray.get(7));
                    letterButtons[7].setText(charArray.get(8));
                    letterButtons[9].setText(charArray.get(9));
                    letterButtons[10].setText(charArray.get(10));
                    break;
                case 1:
                    letterButtons[3].setText(charArray.get(0));
                    letterButtons[0].setText(charArray.get(1));
                    letterButtons[4].setText(charArray.get(2));
                    letterButtons[9].setText(charArray.get(3));
                    letterButtons[7].setText(charArray.get(4));
                    letterButtons[8].setText(charArray.get(5));
                    letterButtons[6].setText(charArray.get(6));
                    letterButtons[10].setText(charArray.get(7));
                    letterButtons[1].setText(charArray.get(8));
                    letterButtons[5].setText(charArray.get(9));
                    letterButtons[2].setText(charArray.get(10));
                    break;
                case 2:
                    letterButtons[4].setText(charArray.get(0));
                    letterButtons[1].setText(charArray.get(1));
                    letterButtons[10].setText(charArray.get(2));
                    letterButtons[3].setText(charArray.get(3));
                    letterButtons[5].setText(charArray.get(4));
                    letterButtons[2].setText(charArray.get(5));
                    letterButtons[9].setText(charArray.get(6));
                    letterButtons[7].setText(charArray.get(7));
                    letterButtons[8].setText(charArray.get(8));
                    letterButtons[0].setText(charArray.get(9));
                    letterButtons[6].setText(charArray.get(10));
                    break;
            }
        }
        if(lenght==12) {
            Random r = new Random();
            int randomCombination = r.nextInt(3);
            switch (randomCombination) {
                case 0:
                    letterButtons[1].setText(charArray.get(0));
                    letterButtons[0].setText(charArray.get(1));
                    letterButtons[4].setText(charArray.get(2));
                    letterButtons[2].setText(charArray.get(3));
                    letterButtons[3].setText(charArray.get(4));
                    letterButtons[5].setText(charArray.get(5));
                    letterButtons[8].setText(charArray.get(6));
                    letterButtons[6].setText(charArray.get(7));
                    letterButtons[7].setText(charArray.get(8));
                    letterButtons[9].setText(charArray.get(9));
                    letterButtons[10].setText(charArray.get(10));
                    letterButtons[11].setText(charArray.get(11));
                    break;
                case 1:
                    letterButtons[11].setText(charArray.get(0));
                    letterButtons[0].setText(charArray.get(1));
                    letterButtons[4].setText(charArray.get(2));
                    letterButtons[9].setText(charArray.get(3));
                    letterButtons[7].setText(charArray.get(4));
                    letterButtons[8].setText(charArray.get(5));
                    letterButtons[6].setText(charArray.get(6));
                    letterButtons[10].setText(charArray.get(7));
                    letterButtons[1].setText(charArray.get(8));
                    letterButtons[5].setText(charArray.get(9));
                    letterButtons[2].setText(charArray.get(10));
                    letterButtons[3].setText(charArray.get(11));
                    break;
                case 2:
                    letterButtons[4].setText(charArray.get(0));
                    letterButtons[1].setText(charArray.get(1));
                    letterButtons[10].setText(charArray.get(2));
                    letterButtons[3].setText(charArray.get(3));
                    letterButtons[5].setText(charArray.get(4));
                    letterButtons[2].setText(charArray.get(5));
                    letterButtons[11].setText(charArray.get(6));
                    letterButtons[7].setText(charArray.get(7));
                    letterButtons[8].setText(charArray.get(8));
                    letterButtons[0].setText(charArray.get(9));
                    letterButtons[6].setText(charArray.get(10));
                    letterButtons[9].setText(charArray.get(11));
                    break;
            }
        }
        // Call reset Word function
        resetWord();
    }

    // MARK: - RESET WORDS BUTTONS --------------------------------------------------------
    void resetWord() {
        // Reset tap Counts
        tapsCount = -1;
        txtWord.setText("");
        beforeChar="";
        btnHint.setText("کمک");

        // reset wordByCharacters
        //wordByCharacters = "";

        // Reset letter Buttons
        resetLetterButtons();

        // Reset top Letters
        //resetLettersTxt();
    }

    // MARK: - START GAME TIMER ---------------------------------------------------------------
    void startGameTimer() {
        float delay = 10*Configs.roundTime;
        gameTimer =  new Timer();
        gameTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progress = progress + 10/Configs.roundTime;
                        pb.setProgress((int) progress);
                        // Log.i("log-", "PROGRESS: " + progress);
                        //txtTest.setText((String)((int) progress));
                        Log.i("log-", "PROGRESS: " + progress);
                        // TIME ENDED, GAME OVER!
                        if (progress >= 100) {
                            gameTimer.cancel();
                            gameOver();
                        }
                    }});}
        }, (int)delay, (int)delay);

    }

    // UPDATE GAME TIMER ------------------------------------------------
    void updateTimer() {
        gameTimer.cancel();
        pb.setProgress((int) progress);
        startGameTimer();
    }

    // MARK: - LETTER BUTTON TAPPED ----------------------------------------------
    @Override
    public void onClick(View v) {
        Button lettButt = (Button)findViewById(v.getId());
            beforeChar= beforeChar + lettButt.getText();
            txtWord.setText(beforeChar);

        tapsCount = tapsCount+1;
        // Log.i("log-", "TAPS COUNT: " + tapsCount);

        // Set letter
//        letterTxts[tapsCount].setText(lettButt.getText().toString() );
//        letterTxts[tapsCount].setBackgroundResource(Configs.circlesArray[randomCircle]);

        // Change buttons status
        lettButt.setBackgroundResource(R.drawable.circle_corner_white);
        lettButt.setTextColor(Color.parseColor("#999999"));
        lettButt.setEnabled(false);

        // Create a string by shown characters
        //wordByCharacters = wordByCharacters + letterTxts[tapsCount].getText().toString();
        //Log.i("log-", "WORD BY CHARS: " + wordByCharacters);

        // You've tapped all buttons, so check your result

        if (tapsCount == lenght -1)
        {
            checkResult();
        }
        // Play a sound
        playSound("buttTapped.mp3");
    }

    // MARK: - CHECK RESULT ------------------------------------------------------------
    void checkResult() {
        Log.i("wordByCharacters","wordByCharacters:" + wordByCharacters);
        Log.i("TextWord","TxtWord:" + txtWord.getText());

        // YOU'VE GUESSED THE WORD!
        firstWord = Configs.stringsArray.get(0);

        if (Configs.stringsArray.size() == 2) {
            secondWord = Configs.stringsArray.get(1);
        }

        if (wordByCharacters.matches(txtWord.getText().toString()) ||
                wordByCharacters.matches(txtWord.getText().toString()))
        {

            // Play a sound
            playSound("rightWord.mp3");

            // Update game timer
            progress = progress - Configs.bonusProgress;
            updateTimer();

            // Update Score
            Configs.score = Configs.score + 10;
            scoreTxt.setText(String.valueOf(Configs.score));

            wordByCharacters="";
            // Get a new random word
            getRandomWord();

            // WORD IS WRONG
        } else {
            //wordByCharacters = "";
            getRandomChar();

            // Play a sound
            playSound("resetWord.mp3");
        }
    }

    // MARK: - GAME OVER ------------------------------------------------------------
    void gameOver() {
        // Get bestScore
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(GameBoard.this);
        Configs.bestScore = prefs.getInt("bestScore", Configs.bestScore);

        // Save Best Score
        if (Configs.bestScore <= Configs.score) {
            Configs.bestScore = Configs.score;
            prefs.edit().putInt("bestScore", Configs.bestScore).apply();
        }

        // Play a sound
        playSound("gameOver.mp3");

        // Go to Game Over Activity
        startActivity(new Intent(GameBoard.this, GameOver.class));
    }

    // MARK: - PLAY SOUND --------------------------------------------------------
    void playSound(String soundName) {
        try {

            MediaPlayer mp = new MediaPlayer();

            AssetFileDescriptor afd = getAssets().openFd("sounds/" + soundName);
            mp.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());

            mp.prepare();
            mp.setVolume(1f, 1f);
            mp.setLooping(false);
            mp.start();

            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }});

        } catch (Exception e) { e.printStackTrace(); }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        gameTimer.cancel();
    }

    public void btnHintClick(View view) {

    }
}// @end