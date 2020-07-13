package ir.papiloo.practiceenglish;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import ir.papiloo.practiceenglish.R;

public class Idea extends AppCompatActivity {
    BottomNavigationView buttomNav;
    Button btnWhatsApp,btnInsta,btnTelegram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.idea);
        getSupportActionBar().hide();
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        buttomNav = findViewById(R.id.bottom_navigation);
        buttomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem Item) {
                if (Item.getItemId() == R.id.nav_home) {
                    finish();
                    return true;
                }
                if (Item.getItemId() == R.id.nav_about) {
                    startActivity(new Intent(Idea.this, About.class));
                    finish();
                    return true;
                }
                if (Item.getItemId() == R.id.nav_idea) {
                    return true;
                }
                return false;
            }
        });
        btnInsta=findViewById(R.id.btnInstagram);
        btnInsta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.instagram.com/papiloosoft/");
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                likeIng.setPackage("com.instagram.android");
                Log.i("Instagram",likeIng.toString());

                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.instagram.com/papiloosoft/")));
                }
                Toast.makeText(Idea.this, "Instagram", Toast.LENGTH_SHORT).show();
            }
        });
        btnWhatsApp=findViewById(R.id.btnWhatsApp);
        btnWhatsApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.whatsapp");
                    startActivity(launchIntent);
                }
                catch (Exception  e){
                    Toast.makeText(Idea.this, "شما برنامه WhatsApp را ندارید", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnTelegram=findViewById(R.id.btnTelegram);
        btnTelegram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tg://resolve?domain=Papiloosoft"));
                    startActivity(intent);
                }
                catch (ActivityNotFoundException e){
                Toast.makeText(Idea.this, "شما برنامه Telegram را ندارید", Toast.LENGTH_SHORT).show();
            }
            }
        });
    }


}


