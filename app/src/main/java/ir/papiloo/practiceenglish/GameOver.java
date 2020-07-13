package ir.papiloo.practiceenglish;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;

import ir.papiloo.practiceenglish.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class GameOver extends AppCompatActivity {

    /* Views */
    TextView possibleWordTxt, scoreTxt;

    /* Variables */
    MarshMallowPermission mmp = new MarshMallowPermission(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

        // Hide ActionBar
        getSupportActionBar().hide();

        // Hide Status bar
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Init views
        possibleWordTxt = (TextView)findViewById(R.id.goPossibleWordTxt);
        possibleWordTxt.setTypeface(Configs.juneGull);
        scoreTxt = (TextView)findViewById(R.id.goScoreTxt);
        scoreTxt.setTypeface(Configs.juneGull);
        // Show score
        scoreTxt.setText(String.valueOf(Configs.score));
        // Get the word you could have made
        possibleWordTxt.setText(Configs.stringsArray.get(0));



        // MARK: - PLAY AGAIN BUTTON ------------------------------------
        Button paButt = (Button)findViewById(R.id.goPlayAgain);
        paButt.setTypeface(Configs.juneGull);
        paButt.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              startActivity(new Intent(GameOver.this, GameBoard.class));
              finish();
        }});



        // MARK: - HOME BUTTON ------------------------------------
        Button hButt = (Button)findViewById(R.id.goHomeButt);
        hButt.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
             startActivity(new Intent(GameOver.this, Home.class));
        }});



        // MARK: - SHARE CURRENT SCORE BUTTON ------------------------------------
        Button shareButt = (Button)findViewById(R.id.goShareButt);
        shareButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mmp.checkPermissionForWriteExternalStorage()) {
                    mmp.requestPermissionForWriteExternalStorage();
                } else {
                    Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
                    Uri uri = getImageUri(GameOver.this, bm);
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("image/jpeg");
                    intent.putExtra(Intent.EXTRA_STREAM, uri);

                    String playStoreLink = "https://www.papiloo.ir/documentation/Games/Fives/Papiloo.apk" + GameOver.this.getPackageName();

                    intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share1) + " " + String.valueOf(Configs.bestScore) + "\n" + getString(R.string.share2) + "\n" + playStoreLink);
                    startActivity(Intent.createChooser(intent, "اشتراک گذاری توسط..."));
                }
            }});

    }// end onCreate()

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    // Method to get URI of a stored image
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "image", null);
        return Uri.parse(path);
    }
    public void onBackPressed() {
        startActivity(new Intent(GameOver.this, Home.class));
        finish();

    }

}// @end
