package ir.papiloo.practicewriting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import ir.papiloo.words.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class About extends AppCompatActivity {
    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        getSupportActionBar().hide();
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //**************************
        bottomNav=findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem Item) {
                if(Item.getItemId()==R.id.nav_home)
                {
                    finish();
                    return true;
                }
                if(Item.getItemId()==R.id.nav_about)
                {
                    return true;
                }
                if (Item.getItemId()==R.id.nav_idea)
                {
                    startActivity(new Intent(About.this, Idea.class));
                    finish();
                    return true;
                }
                return false;
            }
        });

    };


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
