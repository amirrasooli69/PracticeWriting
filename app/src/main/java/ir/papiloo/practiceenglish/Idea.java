package ir.papiloo.practiceenglish;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import ir.papiloo.practiceenglish.R;

public class Idea extends AppCompatActivity {
    BottomNavigationView buttomNav;
    Button btnWhatsApp,btnInsta,btnTelegram,btnMyket,btnEmail;

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
        // Button Instagram
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
        // Button WhatsApp
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
        //Button Telegram
        btnTelegram=findViewById(R.id.btnTelegram);
        btnTelegram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tg://resolve?domain=@Papiloosoft"));
                    startActivity(intent);
                }
                catch (ActivityNotFoundException e){
                Toast.makeText(Idea.this, "شما برنامه Telegram را ندارید", Toast.LENGTH_SHORT).show();
            }
            }
        });
        btnMyket=findViewById(R.id.btnMyket);
        btnMyket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    if (MarshMallowPermission.IS_INTERNET_AVAILABLE(Idea.this)) {
                        // Do your stuff
                        // your codes
                        final String PACKAGE_NAME = getPackageName();
                        //String url="https://www.papiloo.ir/documentation/Games/Fives/Papiloo.apk";
                        String url = "myket://comment?id=" + getPackageName();
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        startActivity(intent);
                    }
                    else
                    {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Idea.this);
                        alertDialog.setTitle("اینترنت");
                        alertDialog.setMessage("اینترنت وصل نیست");
                        alertDialog.setIcon(R.drawable.logo);
                        alertDialog.setPositiveButton("بستن",
                                new DialogInterface.OnClickListener()
                                {
                                    public void onClick(DialogInterface dialog, int which)
                                    {
                                        finish();
                                    }
                                });
                        alertDialog.show();
                    }
                }
                catch (Exception e)
                {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(Idea.this);
                    alertDialog.setTitle("نظر");
                    alertDialog.setMessage("بزودی فعال میشود");
                    alertDialog.setIcon(R.drawable.logo);
                    alertDialog.setPositiveButton("بستن",
                            new DialogInterface.OnClickListener()
                            {
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            });
                    alertDialog.show();
                }
            }

        });
        btnEmail=findViewById(R.id.btnEmail);
        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    startActivity(new Intent(Idea.this, Email.class));
                    finish();
                }
                catch (Exception e)
                {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(Idea.this);
                    alertDialog.setTitle("امیل");
                    alertDialog.setMessage("بزودی فعال میشود");
                    alertDialog.setIcon(R.drawable.logo);
                    alertDialog.setPositiveButton("بستن",
                            new DialogInterface.OnClickListener()
                            {
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            });
                    alertDialog.show();
                }
            }
        });
    }
    public void onBackPressed() {
        finish();

    }


}


