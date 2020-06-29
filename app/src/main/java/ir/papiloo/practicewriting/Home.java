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
            a = mydb.insertData("Gallant", "شجاع،با شکوه");
            a = mydb.insertData("Data", "اطلاعات،داده ها");
            a = mydb.insertData("Unaccustomed", "نا آشنا(به)،خو نگرفته");
            a = mydb.insertData("Bachelor", "مرد مجرد");
            a = mydb.insertData("Qualify", "واجد شرایط بودن");
            a = mydb.insertData("Corpse", "جسد");
            a = mydb.insertData("Conceal", "مخفی کردن،کتمان کردن");
            a = mydb.insertData("Dismal", "دلگیر،غم انگیز،گرفته");
            a = mydb.insertData("Frigid", "بسیار سرد،منجمد");
            a = mydb.insertData("Inhabit", "ساکن بودن،زندگی کردن");
            a = mydb.insertData("Numb", "کرخت،بی حس");
            a = mydb.insertData("Peril", "خطر،به خطر انداختن");
            a = mydb.insertData("Recline", "تکیه دادن،لم دادن");
            a = mydb.insertData("Shriek", "جیغ، جیغ کشیدن");
            a = mydb.insertData("Sinister", "بدجنس،شرور،شیطان صفت");
            a = mydb.insertData("Tempt", "وسوسه کردن");
            a = mydb.insertData("Wager", "شرط بستن،شرط بندی");
            a = mydb.insertData("Typical", "عادی،معمولی");
            a = mydb.insertData("Minimum", "حداقل، کمترین");
            a = mydb.insertData("Scarce", "کمیاب،نایاب");
            a = mydb.insertData("Annual", "سالیانه،سالانه");
            a = mydb.insertData("Persuade", "متقاعد کردن، مجاب کردن");
            a = mydb.insertData("Essential", "لازم،ضروری،اساسی");
            a = mydb.insertData("Blend", "مخلوط کردن،مخلوط");
            a = mydb.insertData("Visible", "مرئی،قابل دیدن،آشکار");
            a = mydb.insertData("Expensive", "گران،پربها");
            a = mydb.insertData("Talent", "استعداد،قریحه");
            a = mydb.insertData("Devise", "طرح کردن،ابداع کردن،تدبیر کردن");
            a = mydb.insertData("Wholesale", "عمده فروشی،کلان فروشی،گسترده");
            a = mydb.insertData("Vapor", "مه،بخار،دود و بخار");
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
            a = mydb.insertData("Evidence", "شهادت،گواه");
            a = mydb.insertData("Solitary", "آدم گوشه گیر،تنها");
            a = mydb.insertData("Vision", "دید،خیال");
            a = mydb.insertData("Frequent", "مکرر");
            a = mydb.insertData("Glimpse", "نظر اجمالی");
            a = mydb.insertData("Recent", "اخیر");
            a = mydb.insertData("Decade", "دهه،دوره ده ساله");
            a = mydb.insertData("Hesitate", "مکث کردن،اکراه داشتن");
            a = mydb.insertData("Absurd", "پوچ");
            a = mydb.insertData("Conflict", "اختلاف");
            a = mydb.insertData("Minority", "اقلیت،گروه اقلیت");
            a = mydb.insertData("Fiction", "افسانه،خیال");
            a = mydb.insertData("Ignite", "آتش گرفتن");
            a = mydb.insertData("Abolish", "لغو کردن");
            a = mydb.insertData("Urban", "شهری");
            a = mydb.insertData("Population", "جمعیت");
            a = mydb.insertData("Frank", "رک و راست");
            a = mydb.insertData("Pollute", "آلوده کردن");
            a = mydb.insertData("Reveal", "آشکار کردن");
            a = mydb.insertData("Prohibit", "قدغن کردن");
            a = mydb.insertData("Urgent", "فوری");
            a = mydb.insertData("Adequate", "کافی");
            a = mydb.insertData("Decrease", "کاهش دادن");
            a = mydb.insertData("Audible", "قابل شنیدن،رسا");
            a = mydb.insertData("Journalist", "روزنامه نگار");
            a = mydb.insertData("Famine", "قحطی");
            a = mydb.insertData("Revive", "نیروی تازه گرفتن");
            a = mydb.insertData("Commence", "شروع کردن");
            a = mydb.insertData("Observant", "تیز بین");
            a = mydb.insertData("Identify", "نشان دادن هویت");
            a = mydb.insertData("Migrate", "مهاجرت کردن");
            a = mydb.insertData("Vessel", "کشتی،ظرف،آوند");
            a = mydb.insertData("Persist", "اصرار کردن");
            a = mydb.insertData("Hazy", "مه رقیق،مه آلود");
            a = mydb.insertData("Gleam", "نور ضعیف");
            a = mydb.insertData("Editor", "ویراستار");
            a = mydb.insertData("Unruly", "عنان گسیخته،سرکش");
            a = mydb.insertData("Rival", "رقیب");
            a = mydb.insertData("Violent", "خشن");
            a = mydb.insertData("Brutal", "وحشیانه");
            a = mydb.insertData("Opponent", "حریف،رقیب");
            a = mydb.insertData("Brawl", "کتک کاری");
            a = mydb.insertData("Duplicate", "کپی کردن");
            a = mydb.insertData("Vicious", "وحشی،وحشیانه");
            a = mydb.insertData("Whirling", "چرخش،چرخیدن");
            a = mydb.insertData("Underdog", "آدم بازنده،توسری خور");
            a = mydb.insertData("Thrust", "حمله کردن");
            a = mydb.insertData("Bewildered", "سردرگم کردن");
            a = mydb.insertData("Expand", "گسترش دادن");
            a = mydb.insertData("Alter", "اصلاح کردن");
            a = mydb.insertData("Mature", "بالغ");
            a = mydb.insertData("Sacred", "مقدس،مذهبی");
            a = mydb.insertData("Revise", "تجدید نظر کردن،اصلاح کردن");
            a = mydb.insertData("Pledge", "تعهد");
            a = mydb.insertData("Casual", "اتفاقی،بیخیال،عادی");
            a = mydb.insertData("Pursue", "تعقیب کردن");
            a = mydb.insertData("Unanimous", "هم عقیده");
            a = mydb.insertData("Fortunate", "خوش شانس");
            a = mydb.insertData("Pioneer", "پیشگام");
            a = mydb.insertData("Innovative", "ابتکاری");
            a = mydb.insertData("Slender", "لاغر،کم و اندک");
            a = mydb.insertData("Surpass", "سبقت گرفتن از");
            a = mydb.insertData("Vast", "وسیع");
            a = mydb.insertData("Doubt", "تردید کردن");
            a = mydb.insertData("Capacity", "گنجایش،ظرفیت");
            a = mydb.insertData("Penetrate", "نفوذ کردن");
            a = mydb.insertData("Pierce", "سوراخ کردن");
            a = mydb.insertData("Accurate", "صحیح،درست");
            a = mydb.insertData("Microscope", "میکروسکوپ");
            a = mydb.insertData("Grateful", "سپاسگزار");
            a = mydb.insertData("Cautious", "محتاط");
            a = mydb.insertData("Confident", "مطمئن");
            a = mydb.insertData("Appeal", "علاقه");
            a = mydb.insertData("Addict", "معتاد");
            a = mydb.insertData("Wary", "مراقب");
            a = mydb.insertData("Aware", "آگاه،دانا");
            a = mydb.insertData("Misfortune", "بدشانس");
            a = mydb.insertData("Avoid", "اجتناب کردن");
            a = mydb.insertData("Wretched", "فلاکت بار");
            a = mydb.insertData("Keg", "بشکه کوچک");
            a = mydb.insertData("Nourish", "تغذیه کردن");
            a = mydb.insertData("Harsh", "تند");
            a = mydb.insertData("Quantity", "مقدار");
            a = mydb.insertData("Opt", "انتخاب کردن");
            a = mydb.insertData("Tragedy", "تراژدی");
            a = mydb.insertData("Pedestrian", "عابر پیاده");
            a = mydb.insertData("Glance", "نگاه گذرا،نگاهی انداختن");
            a = mydb.insertData("Budget", "بودجه");
            a = mydb.insertData("Nimble", "چابک ، فرز");
            a = mydb.insertData("Manipulate", "دستکاری کردن");
            a = mydb.insertData("Reckless", "بی احتیاط");
            a = mydb.insertData("Horrid", "ترسناک،مهیب");
            a = mydb.insertData("Rave", "هذیان گفتن");
            a = mydb.insertData("Economical", "مقرون به صرفه");
            a = mydb.insertData("Lubricate", "روغن کاری کردن");
            a = mydb.insertData("Ingenious", "مبتکر");
            a = mydb.insertData("Harvest", "درو کردن");
            a = mydb.insertData("Abundant", "فراوان");
            a = mydb.insertData("Uneasy", "ناراحت");
            a = mydb.insertData("Calculate", "محاسبه کردن");
            a = mydb.insertData("Absorb", "جذب کردن رطوبت یا آب");
            a = mydb.insertData("Estimate", "محاسبه کردن،قضاوت");
            a = mydb.insertData("Morsel", "لقمه");
            a = mydb.insertData("Quota", "سهمیه");
            a = mydb.insertData("Threat", "تهدید،خطر");
            a = mydb.insertData("Ban", "منع کردن،ممنوع کردن");
            a = mydb.insertData("Panic", "سراسیمگی،هول");
            a = mydb.insertData("Appropriate", "مناسب،درست");
            a = mydb.insertData("Emerge", "بیرون آمدن");
            a = mydb.insertData("Jagged", "دندانه دار");
            a = mydb.insertData("Linger", "باقی ماندن،طول کشیدن");
            a = mydb.insertData("Ambush", "شبیخون زدن");
            a = mydb.insertData("Crafty", "ماهر،حرفه ای");
            a = mydb.insertData("Defiant", "نافرمان");
            a = mydb.insertData("Vigor", "قدرت،توان");
            a = mydb.insertData("Perish", "هلاک شدن");
            a = mydb.insertData("Fragile", "شکستنی");
            a = mydb.insertData("Captive", "اسیر جنگی،محبوس");
            a = mydb.insertData("Prosper", "رونق گرفتن");
            a = mydb.insertData("Devour", "بلعیدن،از چیزی مملو بودن");
            a = mydb.insertData("Plea", "تقاضا");
            a = mydb.insertData("Weary", "خسته");
            a = mydb.insertData("Collide", "تصادف کردن");
            a = mydb.insertData("Confirm", "تتایید کردن");
            a = mydb.insertData("Verify", "تأیید کنید");
            a = mydb.insertData("Anticipate", "انتظار داشتن");
            a = mydb.insertData("Dilemma", "دوراهی،تنگنا");
            a = mydb.insertData("Detour", "راه انحرافی");
            a = mydb.insertData("Merit", "شایستگی");
            a = mydb.insertData("Transmit", "انتقال دادن");
            a = mydb.insertData("Relieve", "تسکین دادن درد");
            a = mydb.insertData("Baffle", "سردرگم،گیج");
            a = mydb.insertData("Warden", "مسئول،نگهبان");
            a = mydb.insertData("Acknowledge", "پذیرفتن،به رسمیت شناختن");
            a = mydb.insertData("Justice", "عدالت");
            a = mydb.insertData("Delinquent", "بزهکار");
            a = mydb.insertData("Reject", "نپذیرفتن");
            a = mydb.insertData("Deprive", "محروم کردن از");
            a = mydb.insertData("Spouse", "همسر");
            a = mydb.insertData("Vocation", "حرفه");
            a = mydb.insertData("Unstable", "ناپایدار،متزلزل");
            a = mydb.insertData("Homicide", "قتل،قاتل");
            a = mydb.insertData("Penalize", "اجحاف کردن،ظلم کردن");
            a = mydb.insertData("Beneficiary", "ذینفع،بهره مند");
            a = mydb.insertData("Reptile", "جانور خزنده");
            a = mydb.insertData("Rarely", "به ندرت");
            a = mydb.insertData("Forbid", "ممنوع کردن");
            a = mydb.insertData("Logical", "منطقی");
            a = mydb.insertData("Exhibit", "به نمایش گذاشتن");
            a = mydb.insertData("Proceed", "پیش رفتن");
            a = mydb.insertData("Precaution", "احتیاط");
            a = mydb.insertData("Extract", "بیرون کشیدن");
            a = mydb.insertData("Prior", "قبلی،قبل از");
            a = mydb.insertData("Embrace", "آغوش،بغل");
            a = mydb.insertData("Valiant", "شجاع");
            a = mydb.insertData("Partial", "نسبی،نا تمام");
            a = mydb.insertData("Fierce", "خشن و وحشی");
            a = mydb.insertData("Detest", "متنفر بودن");
            a = mydb.insertData("Sneer", "ریشخند کردن");
            a = mydb.insertData("Scowl", "اخم کردن");
            a = mydb.insertData("Encourage", "تشویق کردن");
            a = mydb.insertData("Consider", "درباره چیزی فکر کردن");
            a = mydb.insertData("Vermin", "آفات جانوری");
            a = mydb.insertData("Wail", "شیون کردن");
            a = mydb.insertData("Symbol", "نماد");
            a = mydb.insertData("Authority", "قدرت،مقتدر");
            a = mydb.insertData("Neutral", "بی طرف");
            a = mydb.insertData("Trifle", "امر جزئی");
            a = mydb.insertData("Architect", "معمار");
            a = mydb.insertData("Matrimony", "ازدواج");
            a = mydb.insertData("Baggage", "اسباب سفر");
            a = mydb.insertData("Squander", "ضایع کردن");
            a = mydb.insertData("Abroad", "به کشور دیگر،خارج از کشور");
            a = mydb.insertData("Fugitive", "فراری");
            a = mydb.insertData("Calamity", "مصیبت");
            a = mydb.insertData("Pauper", "فقیر،مسکین");
            a = mydb.insertData("Envy", "حسادت،حسادت ورزیدن");
            a = mydb.insertData("Collapse", "فروپاشی،شکست");
            a = mydb.insertData("Prosecute", "پیگرد قانونی");
            a = mydb.insertData("Bigamy", "داشتن دو همسر همزمان");
            a = mydb.insertData("Possible", "احتمال،امکان");
            a = mydb.insertData("Compel", "مجبور کردن");
            a = mydb.insertData("Awkward", "دست و پا چلفتی،شرمسارانه");
            a = mydb.insertData("Venture", "به خطر انداختن،جرات کردن");
            a = mydb.insertData("Awesome", "عالی");
            a = mydb.insertData("Guide", "راهنما");
            a = mydb.insertData("Quench", "خاموش کردن،عطش را فرو نشاندن");
            a = mydb.insertData("Betray", "نارو زدن،لو دادن");
            a = mydb.insertData("Utter", "بر زبان آوردن");
            a = mydb.insertData("Pacify", "آرام کردن");
            a = mydb.insertData("Respond", "پاسخ دادن،واکنش نشان دادن");
            a = mydb.insertData("Beckon", "اشاره");
            a = mydb.insertData("Despite", "بر خلاف،با وجود اینکه");
            a = mydb.insertData("Disrupt", "متلاشی کردن");
            a = mydb.insertData("Rash", "جوش،عجولانه");
            a = mydb.insertData("Rapid", "سریع");
            a = mydb.insertData("Exhaust", "تا ته مصرف کردن،خسته کردن");
            a = mydb.insertData("Severity", "شدت");
            a = mydb.insertData("Feeble", "ضعیف");
            a = mydb.insertData("Unite", "متحد کردن");
            a = mydb.insertData("Cease", "متوقف کردن");
            a = mydb.insertData("Thrifty", "صرفه جو");
            a = mydb.insertData("Miserly", "خسیس");
            a = mydb.insertData("Monarch", "سلطنت");
            a = mydb.insertData("Outlaw", "قانون شکن");
            a = mydb.insertData("Promote", "ترویج");
            a = mydb.insertData("Underbrush", "فرومایه");
            a = mydb.insertData("Illustrate", "مصور");
            a = mydb.insertData("Disclose", "فاش کردن");
            a = mydb.insertData("Excessive", "بیش از حد");
            a = mydb.insertData("Disaster", "فاجعه");
            a = mydb.insertData("Censor", "سانسور کردن");
            a = mydb.insertData("Culprit", "مجرم");
            a = mydb.insertData("Juvenile", "نوجوان");
            a = mydb.insertData("Bait", "طعمه");
            a = mydb.insertData("Insist", "تاکید کردن");
            a = mydb.insertData("Toil", "زحمت");
            a = mydb.insertData("Blunder", "اشتباه بزرگ");
            a = mydb.insertData("Daze", "مبهوت کردن");
            a = mydb.insertData("Mourn", "سوگواری کردن");
            a = mydb.insertData("Subside", "فروکشی کردن");
            a = mydb.insertData("Maim", "علیل کردن");
            a = mydb.insertData("Comprehend", "فهمیدن،درک");
            a = mydb.insertData("Commend", "تحسین کردن");
            a = mydb.insertData("Final", "نهایی");
            a = mydb.insertData("Exempt", "معاف");
            a = mydb.insertData("Vain", "مغرور،بیهوده");
            a = mydb.insertData("Repetition", "تکرار");
            a = mydb.insertData("Depict", "تصور کن");
            a = mydb.insertData("Mortal", "فناپذیر،مهلک");
            a = mydb.insertData("Novel", "نوین،جدید،رمان");
            a = mydb.insertData("Occupant", "ساکن،مقیم");
            a = mydb.insertData("Appoint", "منصوب کردن،مقرر کردن");
            a = mydb.insertData("Quarter", "محل سکونت،اسکان دادن");
            a = mydb.insertData("Site", "محل");
            a = mydb.insertData("Quote", "نقل قول کردن");
            a = mydb.insertData("Verse", "آیه");
            a = mydb.insertData("Morality", "اخلاقیات");
            a = mydb.insertData("Roam", "پرسه زدن");
            a = mydb.insertData("Attract", "جذب کردن");
            a = mydb.insertData("Commuter", "مسافر هر روزه");
            a = mydb.insertData("Confine", "محبوس کردن");
            a = mydb.insertData("Idle", "بیکار،تنبل");
            a = mydb.insertData("Idol", "بت،محبوب");
            a = mydb.insertData("Jest", "شوخی کردن،شوخی");
            a = mydb.insertData("Patriotic", "میهنی،میهن پرستانه");
            a = mydb.insertData("Dispute", "مخالفت،جر و بحث");
            a = mydb.insertData("Valor", "شجاعت");
            a = mydb.insertData("Lunatic", "احمق");
            a = mydb.insertData("Vein", "سیاهرگ،ورید");
            a = mydb.insertData("Uneventful", "بی حادثه،بدون رویداد مهم");
            a = mydb.insertData("Fertile", "بارور شده،حاصلخیز");
            a = mydb.insertData("Refer", "اشاره کردن،ارجاع کردن");
            a = mydb.insertData("Distress", "درماندگی،مخمصه");
            a = mydb.insertData("Diminish", "کاهش دادن");
            a = mydb.insertData("Maximum", "حداکثر");
            a = mydb.insertData("Flee", "فرار کردن");
            a = mydb.insertData("Vulnerable", "آسیب پذیر،حساس");
            a = mydb.insertData("Signify", "معنی دادن،مهم بودن");
            a = mydb.insertData("Mythology", "اسطوره شناسی");
            a = mydb.insertData("Colleague", "همکار");
            a = mydb.insertData("Torment", "درد و رنج،عذاب دادن");
            a = mydb.insertData("Provide", "تامین کردن");
            a = mydb.insertData("Loyalty", "وفاداری");
            a = mydb.insertData("Volunteer", "داوطلب");
            a = mydb.insertData("Prejudice", "تعصب");
            a = mydb.insertData("Shrill", "گوش خراش");
            a = mydb.insertData("Jolly", "شاد،خوش");
            a = mydb.insertData("Witty", "شوخ");
            a = mydb.insertData("Hinder", "به تاخیر انداختن");
            a = mydb.insertData("Lecture", "تدریس،نصیحت");
            a = mydb.insertData("Abuse", "سو استفاده کردن");
            a = mydb.insertData("Mumble", "من من کردن");
            a = mydb.insertData("Mute", "خاموش،بی صدا");
            a = mydb.insertData("Wad", "گلوله پنبه،دسته");
            a = mydb.insertData("Retain", "حفظ کردن،وکیل گرفتن");
            a = mydb.insertData("Candidate", "کاندیدا،نامزد");
            a = mydb.insertData("Precede", "مقدم بودن بر");
            a = mydb.insertData("Adolescent", "نوجوان");
            a = mydb.insertData("Coeducational", "مشارکتی");
            a = mydb.insertData("Radical", "ریشه ای،افراطی،تندرو");
            a = mydb.insertData("Spontaneous", "خود جوش");
            a = mydb.insertData("Vaccinate", "واکسن زدن");
            a = mydb.insertData("Untidy", "نامرتب");
            a = mydb.insertData("Utensil", "وسایل آشپزی");
            a = mydb.insertData("Sensitive", "حساس،زود رنج");
            a = mydb.insertData("Temperate", "معتدل");
            a = mydb.insertData("Vague", "مبهم،دو پهلو");
            a = mydb.insertData("Elevate", "بلند کردن،ارتقاع دادن");
            a = mydb.insertData("Lottery", "بخت آزمایی");
            a = mydb.insertData("Finance", "امور مالی");
            a = mydb.insertData("Obtain", "به دست آوردن");
            a = mydb.insertData("Cinema", "سینما");
            a = mydb.insertData("Event", "رویداد");
            a = mydb.insertData("Discard", "دور انداختن");
            a = mydb.insertData("Soar", "اوج گرفتن");
            a = mydb.insertData("Subsequent", "بعدی");
            a = mydb.insertData("Relate", "مربوط بودن");
            a = mydb.insertData("Stationary", "ثابت");
            a = mydb.insertData("Prompt", "باعث شدن");
            a = mydb.insertData("Hasty", "شتابزده،عجول");
            a = mydb.insertData("Scorch", "جای سوختگی");
            a = mydb.insertData("Tempest", "مزاحم");
            a = mydb.insertData("Soothe", "آرام کردن،تسکین دادن");
            a = mydb.insertData("Sympathetic", "همدرد");
            a = mydb.insertData("Redeem", "باز خریدن،جبران کردن");
            a = mydb.insertData("Resume", "از سر گرفتن");
            a = mydb.insertData("Harmony", "سازگار");
            a = mydb.insertData("Refrain", "برگردان،خودداری کردن");
            a = mydb.insertData("Illegal", "بر خلاف قانون");
            a = mydb.insertData("Narcotic", "ماده مخدر");
            a = mydb.insertData("Heir", "وارث");
            a = mydb.insertData("Majestic", "با عظمت");
            a = mydb.insertData("Dwindle", "به تدریج ضعیف شدن");
            a = mydb.insertData("Surplus", "مازاد");
            a = mydb.insertData("Traitor", "خائن");
            a = mydb.insertData("Deliberate", "حساب شده");
            a = mydb.insertData("Vandal", "خرابکار");
            a = mydb.insertData("Drought", "خشکسالی");
            a = mydb.insertData("Abide", "تحمل کردن");
            a = mydb.insertData("Unify", "با هم متحد شدن");
            a = mydb.insertData("Summit", "قله،اوج");
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
