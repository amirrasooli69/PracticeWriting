package ir.papiloo.practicewriting;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ir.papiloo.practicewriting.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class Home extends AppCompatActivity {
    ReadSite.myDatabaseHelper mydb;
    TextView bestTxt;
    ListView mList;
    ArrayList<Item> arrayItem;
    BottomNavigationView bottomNav;
    Button play;
    Cursor cursor ;
    List<String> wordsArray;


    /* Variables */
    SharedPreferences prefs;
    MarshMallowPermission mmp = new MarshMallowPermission(this);
    @SuppressLint("SdCardPath")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                androidx.appcompat.app.AlertDialog.Builder dialog = new androidx.appcompat.app.AlertDialog.Builder(this);
                dialog.setCancelable(false);
                dialog.setTitle("دسترسی");
                dialog.setMessage(R.string.storage_permission);
                dialog.setNegativeButton("تایید", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(Home.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    }
                });
                dialog.show();

            }
        }

        // Hide ActionBar
        getSupportActionBar().hide();

        // Hide Status bar
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (!new File("/data/data/" + this.getPackageName() + "/databases/EnglishWords.sqlite").exists()) {
            mydb = new ReadSite.myDatabaseHelper(this);
            boolean a = mydb.insertData("Abandon", "ترک کردن،رها کردن");
             a = mydb.insertData("Keen", "تیز،زیرک");
            a = mydb.insertData("Jealous", "حسود");
            a = mydb.insertData("Tact", "تدبیر");
            a = mydb.insertData("Oath", "قسم،سوگند خوردن");
            a = mydb.insertData("Vacant", "خالی");
            a = mydb.insertData("Hardship", "سختی،دشواری");
            a = mydb.insertData("gallant", "شجاع،با شکوه");
            a = mydb.insertData("data", "اطلاعات،داده ها");
            a = mydb.insertData("unaccustomed", "نا آشنا(به)،خو نگرفته");
            a = mydb.insertData("bachelor", "مرد مجرد");
            a = mydb.insertData("qualify", "واجد شرایط بودن");
            a = mydb.insertData("corpse", "جسد");
            a = mydb.insertData("conceal", "مخفی کردن،کتمان کردن");
            a = mydb.insertData("dismal", "دلگیر،غم انگیز،گرفته");
            a = mydb.insertData("frigid", "بسیار سرد،منجمد");
            a = mydb.insertData("inhabit", "ساکن بودن،زندگی کردن");
            a = mydb.insertData("numb", "کرخت،بی حس");
            a = mydb.insertData("peril", "خطر،به خطر انداختن");
            a = mydb.insertData("recline", "تکیه دادن،لم دادن");
            a = mydb.insertData("shriek", "جیغ، جیغ کشیدن");
            a = mydb.insertData("sinister", "بدجنس،شرور،شیطان صفت");
            a = mydb.insertData("tempt", "وسوسه کردن");
            a = mydb.insertData("wager", "شرط بستن،شرط بندی");
            a = mydb.insertData("typical", "عادی،معمولی");
            a = mydb.insertData("minimum", "حداقل، کمترین");
            a = mydb.insertData("scarce", "کمیاب،نایاب");
            a = mydb.insertData("annual", "سالیانه،سالانه");
            a = mydb.insertData("persuade", "متقاعد کردن، مجاب کردن");
            a = mydb.insertData("essential", "لازم،ضروری،اساسی");
            a = mydb.insertData("blend", "مخلوط کردن،مخلوط");
            a = mydb.insertData("visible", "مرئی،قابل دیدن،آشکار");
            a = mydb.insertData("expensive", "گران،پربها");
            a = mydb.insertData("talent", "استعداد،قریحه");
            a = mydb.insertData("devise", "طرح کردن،ابداع کردن،تدبیر کردن");
            a = mydb.insertData("wholesale", "عمده فروشی،کلان فروشی،گسترده");
            a = mydb.insertData("vapor", "مه،بخار،دود و بخار");
            a = mydb.insertData("Eliminate", "حذف کردن");
            a = mydb.insertData("Villain", "آدم شرور،مجرم");
            a = mydb.insertData("Dense", "فشرده،انبوه");
            a = mydb.insertData("Utilize", "به کار بردن");
            a = mydb.insertData("Humid", "مرطوب");
            a = mydb.insertData("Theory", "تئوری،نظریه");
            a = mydb.insertData("Descend", "فرود آمدن");
            a = mydb.insertData("Circulate", "گشتن،دور زدن");
            a = mydb.insertData("Enormous", "عظیم،بزرگ");
            a = mydb.insertData("Predict", "پیش بینی کردن");
            a = mydb.insertData("Vanish", "ناپدید شدن");
            a = mydb.insertData("Tradition", "سنت");
            a = mydb.insertData("Rural", "روستایی");
            a = mydb.insertData("Burden", "مسئولیت سنگین");
            a = mydb.insertData("Campus", "محوطه دانشگاه یا مدرسه");
            a = mydb.insertData("Majority", "اکثریت");
            a = mydb.insertData("Assemble", "تجمع،مونتاژ کردن");
            a = mydb.insertData("Explore", "بررسی کردن");
            a = mydb.insertData("Topic", "موضوع");
            a = mydb.insertData("Evade", "شانه خالی کردن،فرار کردن");
            a = mydb.insertData("Probe", "جستجو");
            a = mydb.insertData("Reform", "اصلاح کردن");
            a = mydb.insertData("Approach", "نزدیک شدن");
            a = mydb.insertData("Detect", "متوجه شدن،کشف کردن");
            a = mydb.insertData("Defect", "نقص");
            a = mydb.insertData("Employee", "کارمند");
            a = mydb.insertData("Neglect", "غفلت کردن از");
            a = mydb.insertData("Deceive", "فریب دادن");
            a = mydb.insertData("Undoubtedly", "بی تردید");
            a = mydb.insertData("Popular", "عامه پسند");
            a = mydb.insertData("Thorough", "تمام عیار");
            a = mydb.insertData("Client", "موکل،مشتری");
            a = mydb.insertData("Comprehensive", "جامع،مفصل");
            a = mydb.insertData("Defraud", "پول گرفتن");
            a = mydb.insertData("Postpone", "به تعویق انداختن");
            a = mydb.insertData("Consent", "رضایت دادن");
            a = mydb.insertData("Massive", "حجیم");
            a = mydb.insertData("Capsule", "کپسول");
            a = mydb.insertData("Preserve", "محافظت کردن");
            a = mydb.insertData("Denounce", "محکوم کردن،انتقاد کردن");
            a = mydb.insertData("Unique", "منحصر به فرد");
            a = mydb.insertData("Torrent", "سیلاب");
            a = mydb.insertData("Resent", "رنجیدن از");
            a = mydb.insertData("Molest", "آسیب رساندن،حمله کردن");
            a = mydb.insertData("Gloomy", "تیره تار،تاریکی");
            a = mydb.insertData("Unforeseen", "غیر مترقبه");
            a = mydb.insertData("Exaggerate", "مبالغه کردن");
            a = mydb.insertData("Amateur", "آماتور،ناشی");
            a = mydb.insertData("Mediocre", "معمولی");
            a = mydb.insertData("Variety", "گوناگونی،تنوع");
            a = mydb.insertData("Valid", "معتبر،قانونی");
            a = mydb.insertData("Survive", "جان سالم به در بردن");
            a = mydb.insertData("Weird", "عجیب و غریب،مرموز");
            a = mydb.insertData("Prominent", "مشهور،برجسته");
            a = mydb.insertData("Security", "امنیت،تضمین");
            a = mydb.insertData("Bulky", "تنومند،چاق");
            a = mydb.insertData("Reluctant", "ناراضی");
            a = mydb.insertData("Obvious", "آشکار،واضح");
            a = mydb.insertData("Vicinity", "نزدیکی،محله");
            a = mydb.insertData("Century", "قرن");
            a = mydb.insertData("Rage", "خشم");
            a = mydb.insertData("Document", "سند");
            a = mydb.insertData("Conclude", "پایان دادن،به نتیجه رسیدن");
            a = mydb.insertData("Undeniable", "غیر قابل انکار");
            a = mydb.insertData("Resist", "مقاومت کردن در برابر");
            a = mydb.insertData("Lack", "نیاز،نداشتن");
            a = mydb.insertData("Ignore", "نادیده گرفتن");
            a = mydb.insertData("Challenge", "به مبارزه طلبیدن");
            a = mydb.insertData("Miniature", "مینیاتور،ریز");
            a = mydb.insertData("Source", "منشا،منبع");
            a = mydb.insertData("Excel", "بی نظیر بودن");
            a = mydb.insertData("Feminine", "زنانه");
            a = mydb.insertData("Mount", "سوار شدن،بالا رفتن");
            a = mydb.insertData("Compete", "رقابت کردن");
            a = mydb.insertData("Dread", "هراس،وحشت");
            a = mydb.insertData("Masculine", "مردانه");
            a = mydb.insertData("Menace", "تهدید،خطر");
            a = mydb.insertData("Tendency", "تمایل،گرایش");
            a = mydb.insertData("Underestimate", "کمتر  از حد برآورد کردن");
            a = mydb.insertData("Victorious", "فاتح،پیروزمندانه");
            a = mydb.insertData("Numerous", "متعدد");
            a = mydb.insertData("Flexible", "انعطاف پذیر");
            a = mydb.insertData("", "");
            a = mydb.insertData("", "");
            a = mydb.insertData("", "");
            a = mydb.insertData("", "");
            a = mydb.insertData("", "");
            a = mydb.insertData("", "");
            a = mydb.insertData("", "");
            a = mydb.insertData("", "");
            a = mydb.insertData("", "");
            a = mydb.insertData("", "");
            a = mydb.insertData("", "");
            a = mydb.insertData("", "");
            a = mydb.insertData("", "");
            a = mydb.insertData("", "");
            a = mydb.insertData("", "");
            a = mydb.insertData("", "");
            a = mydb.insertData("", "");
            a = mydb.insertData("", "");
            a = mydb.insertData("", "");
            a = mydb.insertData("", "");
            a = mydb.insertData("", "");
            a = mydb.insertData("", "");
            a = mydb.insertData("", "");
            a = mydb.insertData("", "");
            a = mydb.insertData("", "");
            a = mydb.insertData("", "");
            a = mydb.insertData("", "");
            a = mydb.insertData("", "");
            a = mydb.insertData("", "");
            a = mydb.insertData("", "");
            a = mydb.insertData("", "");
            a = mydb.insertData("", "");
            a = mydb.insertData("", "");
            a = mydb.insertData("", "");
            a = mydb.insertData("", "");
            a = mydb.insertData("", "");
            a = mydb.insertData("", "");
            a = mydb.insertData("", "");
            a = mydb.insertData("", "");
            a = mydb.insertData("", "");
            a = mydb.insertData("", "");
            a = mydb.insertData("", "");




        }




        //test send to site
        Button btntest=(Button) findViewById(R.id.addword);
        btntest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, ReadSite.class));

            }
        });
        // Init views
        bestTxt = (TextView)findViewById(R.id.hBestTxt);
        bestTxt.setTypeface(Configs.juneGull);

        //BottonNavigation
        bottomNav=findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem Item) {
                if(Item.getItemId()==R.id.nav_home)
                {
                    return true;
                }
                if(Item.getItemId()==R.id.nav_about)
                {
                    startActivity(new Intent(Home.this, About.class));
                    return true;
                }
                if (Item.getItemId()==R.id.nav_idea)
                {
                    startActivity(new Intent(Home.this, Idea.class));

                    return true;

                }
                return false;
            }
        });

        // Get Best Score
        prefs = PreferenceManager.getDefaultSharedPreferences(Home.this);
        Configs.bestScore = prefs.getInt("bestScore", Configs.bestScore);
        bestTxt.setText(String.valueOf(Configs.bestScore));

        //*******************************
        mList = findViewById(R.id.list_view);

        arrayItem = new ArrayList<>();

        CustomAdapter mAdapter = new CustomAdapter(this, arrayItem);

        itemDetails();

        mList.setAdapter(mAdapter);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override

            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(MainActivity.this, "Item Number " + i , Toast.LENGTH_SHORT).show();
                if(i==0) {
                    startActivity(new Intent(Home.this, GameBoard.class));
                }

            }
        });

    }

    public int buildListWords()
    {
        ArrayList<String> mylist = new ArrayList<String>();
        String DATABASE_NAME = "EnglishWords.sqlite";
        String TABLE_NAME = "practice";
        try {
            SQLiteDatabase mydb = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE,null);
            Cursor cursor  = mydb.rawQuery("SELECT * FROM "+  TABLE_NAME, null);
            if(cursor.moveToFirst()){
                do{
                    String ID = cursor.getString(0);
                    String word = cursor.getString(1);
                    String mean = cursor.getString(2);


                    mylist.add(word+"."+mean);

                    // Show values with Toast
//                    Toast.makeText(getApplicationContext(),NAME+"."+CITY , Toast.LENGTH_LONG).show();
                }
                while(cursor.moveToNext());
                wordsArray=mylist;
            }
            mydb.close();

        }catch(Exception e){
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
        }
        return wordsArray.size();

    }
    // create other language in list
    public void itemDetails() {

//        arrayItem.add(new Item("ico_fa", "فارسی", "----", "----"));
//        arrayItem.add(new Item("ico_sem", "سمنانی", "----", "----"));
//        arrayItem.add(new Item("ico_san", "سنگسری", "----", "----"));
//        arrayItem.add(new Item("ico_maz", "مازندرانی", "----", "----"));
        String [] wordsArrFa = getResources().getStringArray(R.array.english);
//        arrayItem.add(new Item("ico_english", Integer.toString(wordsArrFa.length)));
        arrayItem.add(new Item("ico_english", Integer.toString(buildListWords())));



    }


    // end onCreate()
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
        AlertDialog.Builder alertDialog1 = new AlertDialog.Builder(Home.this);
        alertDialog1.setTitle("نظر دادن");
        alertDialog1.setMessage("اگرخوشتان آمد نظر دهید؟");
        alertDialog1.setIcon(R.drawable.logo);
        alertDialog1.setPositiveButton("خروج",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        finish();
                    }
                });
                alertDialog1.setNegativeButton("نظر دادن",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        try
                        {
                            if (MarshMallowPermission.IS_INTERNET_AVAILABLE(Home.this)) {
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
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home.this);
                                alertDialog.setTitle("اینترنت");
                                alertDialog.setMessage("اینترنت وصل نیست");
                                alertDialog.setIcon(R.drawable.logo);
                                alertDialog.setPositiveButton("خروج از بازی",
                                        new DialogInterface.OnClickListener()
                                        {
                                            public void onClick(DialogInterface dialog, int which)
                                            {
                                                finish();
                                            }
                                        });
                                        alertDialog.setNegativeButton("درباره ما",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                startActivity(new Intent(Home.this, About.class));
                                            }
                                        });
                                alertDialog.show();
                            }
                        }
                        catch (Exception e)
                        {
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home.this);
                            alertDialog.setTitle("نظر");
                            alertDialog.setMessage("بزودی فعال میشود");
                            alertDialog.setIcon(R.drawable.logo);
                            alertDialog.setPositiveButton("خروج از بازی",
                                    new DialogInterface.OnClickListener()
                                    {
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                        }
                                    });
//                             alertDialog.setNegativeButton("درباره ما",
//                            new DialogInterface.OnClickListener()
//                            {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    startActivity(new Intent(Home.this, About.class));
//                                }
//                            });
                            alertDialog.show();
                        }
                    }
                });
        alertDialog1.show();
    }
}
// @end
