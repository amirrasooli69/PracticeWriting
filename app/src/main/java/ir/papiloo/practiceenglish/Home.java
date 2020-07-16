package ir.papiloo.practiceenglish;

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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
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

import ir.papiloo.practiceenglish.R;
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
            boolean a = mydb.insertData("Abandon", "ترک کردن،رها کردن","/əˈbandən/");
             a = mydb.insertData("Keen", "تیز،زیرک","/kiːn/");
            a = mydb.insertData("Jealous", "حسود","/ˈdʒɛləs/");
            a = mydb.insertData("Tact", "تدبیر","/takt/");
            a = mydb.insertData("Oath", "قسم،سوگند خوردن","/oʊθ/");
            a = mydb.insertData("Vacant", "خالی","/ˈveɪkənt/");
            a = mydb.insertData("Hardship", "سختی،دشواری","/ˈhɑːrd.ʃɪp/");
            a = mydb.insertData("Gallant", "شجاع،با شکوه","/ˈɡæl.ənt/");
            a = mydb.insertData("Data", "اطلاعات،داده ها","/ˈdeɪ.t̬ə/");
            a = mydb.insertData("Unaccustomed", "نا آشنا(به)،خو نگرفته","/ˌʌn.əˈkʌs.təmd/");
            a = mydb.insertData("Bachelor", "مرد مجرد","/ˈbætʃ.əl.ər/");
            a = mydb.insertData("Qualify", "واجد شرایط بودن","/ˈkwɑː.lə.faɪ/");
            a = mydb.insertData("Corpse", "جسد","/kɔːrps/");
            a = mydb.insertData("Conceal", "مخفی کردن،کتمان کردن","/kənˈsiːl/");
            a = mydb.insertData("Dismal", "دلگیر،غم انگیز،گرفته","/ˈdɪz.məl/");
            a = mydb.insertData("Frigid", "بسیار سرد،منجمد","/ˈfrɪdʒ.ɪd/");
            a = mydb.insertData("Inhabit", "ساکن بودن،زندگی کردن","/ɪnˈhæb.ɪt/");
            a = mydb.insertData("Numb", "کرخت،بی حس","/nʌm/");
            a = mydb.insertData("Peril", "خطر،به خطر انداختن","/ˈper.əl/");
            a = mydb.insertData("Recline", "تکیه دادن،لم دادن","/rɪˈklaɪn/");
            a = mydb.insertData("Shriek", "جیغ، جیغ کشیدن","/ʃriːk/");
            a = mydb.insertData("Sinister", "بدجنس،شرور،شیطان صفت","/ˈsɪn.ə.stər/");
            a = mydb.insertData("Tempt", "وسوسه کردن","/tempt/");
            a = mydb.insertData("Wager", "شرط بستن،شرط بندی","");
            a = mydb.insertData("Typical", "عادی،معمولی"," /ˈtɪp.ɪ.kəl/");
            a = mydb.insertData("Minimum", "حداقل، کمترین","/ˈmɪn.ə.məm/");
            a = mydb.insertData("Scarce", "کمیاب،نایاب"," /skers/");
            a = mydb.insertData("Annual", "سالیانه،سالانه","/ˈæn.ju.əl/");
            a = mydb.insertData("Persuade", "متقاعد کردن، مجاب کردن","/pərˈsweɪd/");
            a = mydb.insertData("Essential", "لازم،ضروری،اساسی","/ɪˈsen.ʃəl/");
            a = mydb.insertData("Blend", "مخلوط کردن،مخلوط","/blend/");
            a = mydb.insertData("Visible", "مرئی،قابل دیدن،آشکار"," /ˈvɪz.ə.bəl/");
            a = mydb.insertData("Expensive", "گران،پربها","/ɪkˈspen.sɪv/");
            a = mydb.insertData("Talent", "استعداد،قریحه"," /ˈtæl.ənt/");
            a = mydb.insertData("Devise", "طرح کردن،ابداع کردن،تدبیر کردن","/dɪˈvaɪz/");
            a = mydb.insertData("Wholesale", "عمده فروشی،کلان فروشی،گسترده","/ˈhoʊl.seɪl/");
            a = mydb.insertData("Vapor", "مه،بخار،دود و بخار","/ˈveɪ·pər/");
            a = mydb.insertData("Eliminate", "حذف کردن","/iˈlɪm.ə.neɪt/");
            a = mydb.insertData("Villain", "آدم شرور،مجرم","/ˈvɪl.ən/");
            a = mydb.insertData("Dense", "فشرده،انبوه","/dens/");
            a = mydb.insertData("Utilize", "به کار بردن","/ˈjuː.t̬əl.aɪz/");
            a = mydb.insertData("Humid", "مرطوب","/ˈhjuː.mɪd/");
            a = mydb.insertData("Theory", "تئوری،نظریه"," /ˈθɪr.i/");
            a = mydb.insertData("Descend", "فرود آمدن","/dɪˈsend/");
            a = mydb.insertData("Circulate", "گشتن،دور زدن","/ˈsɜːr.kjə.leɪt/");
            a = mydb.insertData("Enormous", "عظیم،بزرگ","/əˈnɔːr.məs/");
            a = mydb.insertData("Predict", "پیش بینی کردن","/prɪˈdɪkt/");
            a = mydb.insertData("Vanish", "ناپدید شدن","/ˈvæn.ɪʃ/");
            a = mydb.insertData("Tradition", "سنت","/trəˈdɪʃ.ən/");
            a = mydb.insertData("Rural", "روستایی","/ˈrʊr.əl/");
            a = mydb.insertData("Burden", "مسئولیت سنگین","/ˈbɜːr.dən/");
            a = mydb.insertData("Campus", "محوطه دانشگاه یا مدرسه","/ˈkæm.pəs/");
            a = mydb.insertData("Majority", "اکثریت","/məˈdʒɑː.rə.t̬i/");
            a = mydb.insertData("Assemble", "تجمع،مونتاژ کردن","/əˈsem.bəl/");
            a = mydb.insertData("Explore", "بررسی کردن","/ɪkˈsplɔːr/");
            a = mydb.insertData("Topic", "موضوع","/ˈtɑː.pɪk/");
            a = mydb.insertData("Debate", "مناظره","/dɪˈbeɪt/");
            a = mydb.insertData("Evade", "شانه خالی کردن،فرار کردن","/ɪˈveɪd/");
            a = mydb.insertData("Probe", "جستجو","/proʊb/");
            a = mydb.insertData("Reform", "اصلاح کردن","/rɪˈfɔːrm/");
            a = mydb.insertData("Approach", "نزدیک شدن","/əˈproʊtʃ/");
            a = mydb.insertData("Detect", "متوجه شدن،کشف کردن","/dɪˈtekt/");
            a = mydb.insertData("Defect", "نقص","/ˈdiː.fekt/");
            a = mydb.insertData("Employee", "کارمند","/ɪmˈplɔɪ.iː/");
            a = mydb.insertData("Neglect", "غفلت کردن از","/nɪˈɡlekt/");
            a = mydb.insertData("Deceive", "فریب دادن","/dɪˈsiːv/");
            a = mydb.insertData("Undoubtedly", "بی تردید","/ʌnˈdɑʊ·t̬ɪd·li/");
            a = mydb.insertData("Popular", "عامه پسند،محبوب","/ˈpɑː.pjə.lər/");
            a = mydb.insertData("Thorough", "تمام عیار","/ˈθɝː.oʊ/");
            a = mydb.insertData("Client", "موکل،مشتری","/ˈklaɪ.ənt/");
            a = mydb.insertData("Comprehensive", "جامع،مفصل","/ˌkɑːm.prəˈhen.sɪv/");
            a = mydb.insertData("Defraud", "گول زدن،پول گرفتن","/dɪˈfrɑːd/");
            a = mydb.insertData("Postpone", "به تعویق انداختن","/poʊstˈpoʊn/");
            a = mydb.insertData("Consent", "رضایت دادن","/kənˈsent/");
            a = mydb.insertData("Massive", "حجیم","/ˈmæs.ɪv/");
            a = mydb.insertData("Capsule", "کپسول","/ˈkæp.səl/");
            a = mydb.insertData("Preserve", "محافظت کردن","/prɪˈzɜːrv/");
            a = mydb.insertData("Denounce", "محکوم کردن،انتقاد کردن","/dɪˈnaʊns/");
            a = mydb.insertData("Unique", "منحصر به فرد","/juːˈniːk/");
            a = mydb.insertData("Torrent", "سیلاب","/ˈtɔːr.ənt/");
            a = mydb.insertData("Resent", "رنجیدن از","/rɪˈzent/");
            a = mydb.insertData("Molest", "آسیب رساندن،حمله کردن","/məˈlest/");
            a = mydb.insertData("Gloomy", "تیره تار،تاریکی","/ˈɡluː.mi/");
            a = mydb.insertData("Unforeseen", "غیر مترقبه","/ˌʌn.fɔːrˈsiːn/");
            a = mydb.insertData("Exaggerate", "مبالغه کردن","/ɪɡˈzædʒəreɪt/");
            a = mydb.insertData("Amateur", "آماتور،ناشی","/ˈæm.ə.tʃər/");
            a = mydb.insertData("Mediocre", "معمولی","/ˌmiːdiˈoʊkər/");
            a = mydb.insertData("Variety", "گوناگونی،تنوع","/vəˈraɪəti/");
            a = mydb.insertData("Valid", "معتبر،قانونی","/ˈvælɪd/");
            a = mydb.insertData("Survive", "جان سالم به در بردن","/sərˈvaɪv/");
            a = mydb.insertData("Weird", "عجیب و غریب،مرموز","/wɪrd/");
            a = mydb.insertData("Prominent", "مشهور،برجسته","/ˈprɑːmɪnənt/");
            a = mydb.insertData("Security", "امنیت،تضمین","/sɪˈkjʊrəti/");
            a = mydb.insertData("Bulky", "تنومند،چاق","/ˈbʌlki/");
            a = mydb.insertData("Reluctant", "ناراضی","/rɪˈlʌktənt/");
            a = mydb.insertData("Obvious", "آشکار،واضح","/ˈɑːb.vi.əs/");
            a = mydb.insertData("Vicinity", "نزدیکی،محله","/vəˈsɪnəti/");
            a = mydb.insertData("Century", "قرن","/ˈsentʃəri/");
            a = mydb.insertData("Rage", "خشم","/reɪdʒ/");
            a = mydb.insertData("Document", "سند","/ˈdɑːkjəmənt/");
            a = mydb.insertData("Conclude", "پایان دادن،به نتیجه رسیدن","/kənˈkluːd/");
            a = mydb.insertData("Undeniable", "غیر قابل انکار","/ˌʌndɪˈnaɪəbəl/");
            a = mydb.insertData("Resist", "مقاومت کردن در برابر","/rɪˈzɪst/");
            a = mydb.insertData("Lack", "نیاز،نداشتن"," /læk/");
            a = mydb.insertData("Ignore", "نادیده گرفتن","/ɪɡˈnɔːr/");
            a = mydb.insertData("Challenge", "به مبارزه طلبیدن","/ˈtʃæləndʒ/");
            a = mydb.insertData("Miniature", "مینیاتور،ریز","/ˈmɪniətʃər/");
            a = mydb.insertData("Source", "منشا،منبع","/sɔːrs/");
            a = mydb.insertData("Excel", "بی نظیر بودن","/ɪkˈsel/");
            a = mydb.insertData("Feminine", "زنانه","/ˈfemɪnɪn/");
            a = mydb.insertData("Mount", "سوار شدن،بالا رفتن","/maʊnt/");
            a = mydb.insertData("Compete", "رقابت کردن","/kəmˈpiːt/");
            a = mydb.insertData("Dread", "هراس،وحشت","/dred/");
            a = mydb.insertData("Masculine", "مردانه","/ˈmæskjəlɪn/");
            a = mydb.insertData("Menace", "تهدید،خطر","/ˈmenɪs/");
            a = mydb.insertData("Tendency", "تمایل،گرایش","/ˈtendənsi/");
            a = mydb.insertData("Underestimate", "کمتر  از حد برآورد کردن","/ˌʌndərˈestɪmeɪt/");
            a = mydb.insertData("Victorious", "فاتح،پیروزمندانه","/vɪkˈtɔːriəs/");
            a = mydb.insertData("Numerous", "متعدد","/ˈnuːmərəs/");
            a = mydb.insertData("Flexible", "انعطاف پذیر","/ˈfleksəbəl/");
            a = mydb.insertData("Evidence", "شهادت،گواه","/ˈevɪdəns/");
            a = mydb.insertData("Solitary", "آدم گوشه گیر،تنها","/ˈsɑːləteri/");
            a = mydb.insertData("Vision", "دید،خیال","/ˈvɪʒən/");
            a = mydb.insertData("Frequent", "مکرر","/ˈfriːkwənt/");
            a = mydb.insertData("Glimpse", "نظر اجمالی","/ɡlɪmps/");
            a = mydb.insertData("Recent", "اخیر","/ˈriːsənt/");
            a = mydb.insertData("Decade", "دهه،دوره ده ساله","/deˈkeɪd/");
            a = mydb.insertData("Hesitate", "مکث کردن،اکراه داشتن","/ˈhezɪteɪt/");
            a = mydb.insertData("Absurd", "پوچ","/əbˈsɜː:rd/");
            a = mydb.insertData("Conflict", "اختلاف","/ˈkɒ:nflɪkt/");
            a = mydb.insertData("Minority", "اقلیت،گروه اقلیت","/məˈnɑːː:rəti/");
            a = mydb.insertData("Fiction", "افسانه،خیال","/ˈfɪkʃən/");
            a = mydb.insertData("Ignite", "آتش گرفتن","/ɪɡˈnaɪt/");
            a = mydb.insertData("Abolish", "لغو کردن","/ə’bɑː lɪʃ/");
            a = mydb.insertData("Urban", "شهری","/ˈ ɜː rbən/");
            a = mydb.insertData("Population", "جمعیت","/ˌ pɑːpjəˈleɪʃən/");
            a = mydb.insertData("Frank", "رک و راست","/fræŋk/");
            a = mydb.insertData("Pollute", "آلوده کردن","/pəˈluːt/");
            a = mydb.insertData("Reveal", "آشکار کردن","/rɪˈviːl/");
            a = mydb.insertData("Prohibit", "قدغن کردن","/proʊˈhɪbɪt/");
            a = mydb.insertData("Urgent", "فوری","/ˈɜːrdʒənt/");
            a = mydb.insertData("Adequate", "کافی"," /ˈædɪkwət/");
            a = mydb.insertData("Decrease", "کاهش دادن","/dɪˈkriːs/");
            a = mydb.insertData("Audible", "قابل شنیدن،رسا","/ˈɒːdəbəl/");
            a = mydb.insertData("Journalist", "روزنامه نگار","/ˈdʒɜːrnəl-ɪst/");
            a = mydb.insertData("Famine", "قحطی","/ˈfæmɪn/");
            a = mydb.insertData("Revive", "نیروی تازه گرفتن","/rɪˈvaɪv/");
            a = mydb.insertData("Commence", "شروع کردن","/kəˈmens/");
            a = mydb.insertData("Observant", "تیز بین","/əbˈzɜːrvənt/");
            a = mydb.insertData("Identify", "نشان دادن هویت","/aɪˈdentɪfaɪ/");
            a = mydb.insertData("Migrate", "مهاجرت کردن","/ˈmaɪɡreɪt/");
            a = mydb.insertData("Vessel", "کشتی،ظرف،آوند","/ˈvesəl/");
            a = mydb.insertData("Persist", "اصرار کردن","/pərˈsɪst/");
            a = mydb.insertData("Hazy", "مه رقیق،مه آلود","/ˈheɪzi/");
            a = mydb.insertData("Gleam", "نور ضعیف","/ɡliːm/");
            a = mydb.insertData("Editor", "ویراستار","/ˈedɪtər/");
            a = mydb.insertData("Unruly", "عنان گسیخته،سرکش","/ʌnˈruːli/");
            a = mydb.insertData("Rival", "رقیب","/ˈraɪvəl/");
            a = mydb.insertData("Violent", "خشن","/ˈvaɪələnt/");
            a = mydb.insertData("Brutal", "وحشیانه","/ˈbruːtl/");
            a = mydb.insertData("Opponent", "حریف،رقیب","/əˈpoʊnənt /");
            a = mydb.insertData("Brawl", "کتک کاری","/brɒːl/");
            a = mydb.insertData("Duplicate", "کپی کردن","/ˈduːplɪkeɪt/");
            a = mydb.insertData("Vicious", "وحشی،وحشیانه","/ˈvɪʃəs/");
            a = mydb.insertData("Whirling", "چرخش،چرخیدن","/wɜːrlɪŋ/");
            a = mydb.insertData("Underdog", "آدم بازنده،توسری خور","/ˈʌndərdɒːɡ/");
            a = mydb.insertData("Thrust", "حمله کردن","/θrʌst/");
            a = mydb.insertData("Bewildered", "سردرگم کردن","/bɪˈwɪldərd/");
            a = mydb.insertData("Expand", "گسترش دادن","/ɪkˈspænd/");
            a = mydb.insertData("Alter", "اصلاح کردن","/ˈɒːltər/");
            a = mydb.insertData("Mature", "بالغ","/məˈtʃʊr/");
            a = mydb.insertData("Sacred", "مقدس،مذهبی","/ˈseɪkrɪd/");
            a = mydb.insertData("Revise", "تجدید نظر کردن،اصلاح کردن","/rɪˈvaɪz/");
            a = mydb.insertData("Pledge", "تعهد","/pledʒ/");
            a = mydb.insertData("Casual", "اتفاقی،بیخیال،عادی","/ˈkæʒuəl/");
            a = mydb.insertData("Pursue", "تعقیب کردن","/pərˈsuː/");
            a = mydb.insertData("Unanimous", "هم عقیده","/juːˈnænɪməs/");
            a = mydb.insertData("Fortunate", "خوش شانس","/ˈfɔːrtʃənət/");
            a = mydb.insertData("Pioneer", "پیشگام","/ˌpaɪəˈnɪr/");
            a = mydb.insertData("Innovative", "ابتکاری","/ˈɪnəˌveɪtɪv/");
            a = mydb.insertData("Slender", "لاغر،کم و اندک","/ˈslendər/");
            a = mydb.insertData("Surpass", "سبقت گرفتن از","/sərˈpæs/");
            a = mydb.insertData("Vast", "وسیع","/væst/");
            a = mydb.insertData("Doubt", "تردید کردن","/daʊt/");
            a = mydb.insertData("Capacity", "گنجایش،ظرفیت","/kəˈpæsəti/");
            a = mydb.insertData("Penetrate", "نفوذ کردن","/ˈpenətreɪt/");
            a = mydb.insertData("Pierce", "سوراخ کردن","/pɪrs/");
            a = mydb.insertData("Accurate", "صحیح،درست","/ˈækjərət/");
            a = mydb.insertData("Microscope", "میکروسکوپ","/ˈmaɪkrəskoʊp/");
            a = mydb.insertData("Grateful", "سپاسگزار","/ˈɡreɪtfəl/");
            a = mydb.insertData("Cautious", "محتاط","/ˈkɒːʃəs/");
            a = mydb.insertData("Confident", "مطمئن","/ˈkɑːnfɪdənt/");
            a = mydb.insertData("Appeal", "علاقه","/əˈpiːl/");
            a = mydb.insertData("Addict", "معتاد","/ˈædɪkt/");
            a = mydb.insertData("Wary", "مراقب","/ˈweri/");
            a = mydb.insertData("Aware", "آگاه،دانا","/əˈwer/");
            a = mydb.insertData("Misfortune", "بدشانس","/mɪsˈfɔːrtʃən/");
            a = mydb.insertData("Avoid", "اجتناب کردن","/əˈvɔɪd/");
            a = mydb.insertData("Wretched", "فلاکت بار","/ˈretʃɪd/");
            a = mydb.insertData("Keg", "بشکه کوچک","/keɡ/");
            a = mydb.insertData("Nourish", "تغذیه کردن","/ˈnɜːrɪʃ/");
            a = mydb.insertData("Harsh", "تند","/hɑːrʃ/");
            a = mydb.insertData("Quantity", "مقدار","/ˈkwɒːntəti/");
            a = mydb.insertData("Opt", "انتخاب کردن","/ɑːpt/");
            a = mydb.insertData("Tragedy", "تراژدی"," /ˈtrædʒədi/");
            a = mydb.insertData("Pedestrian", "عابر پیاده","/pəˈdestriən/");
            a = mydb.insertData("Glance", "نگاه گذرا،نگاهی انداختن","/ɡlæns/");
            a = mydb.insertData("Budget", "بودجه","/ˈbʌdʒɪt/");
            a = mydb.insertData("Nimble", "چابک ، فرز","/ˈnɪmbəl/");
            a = mydb.insertData("Manipulate", "دستکاری کردن","/məˈnɪpjəleɪt/");
            a = mydb.insertData("Reckless", "بی احتیاط","/ˈrekləs/");
            a = mydb.insertData("Horrid", "ترسناک،مهیب","/ˈhɑːrɪd/");
            a = mydb.insertData("Rave", "هذیان گفتن","/reɪv/");
            a = mydb.insertData("Economical", "مقرون به صرفه","/ˌekəˈnɑːmɪkəl/");
            a = mydb.insertData("Lubricate", "روغن کاری کردن","/ˈluːbrɪkeɪt/");
            a = mydb.insertData("Ingenious", "مبتکر","/ɪnˈdʒiːniəs/");
            a = mydb.insertData("Harvest", "درو کردن","/ˈhɑːrvɪst/");
            a = mydb.insertData("Abundant", "فراوان","/əˈbʌndənt/");
            a = mydb.insertData("Uneasy", "ناراحت","/ʌnˈiːzi/");
            a = mydb.insertData("Calculate", "محاسبه کردن","/ˈkælkjəleɪt/");
            a = mydb.insertData("Absorb", "جذب کردن رطوبت یا آب","/əbˈzɔːrb/");
            a = mydb.insertData("Estimate", "محاسبه کردن،قضاوت","/ˈestəmət/");
            a = mydb.insertData("Morsel", "لقمه","/ˈmɔːrsəl/");
            a = mydb.insertData("Quota", "سهمیه","/ˈkwoʊtə/");
            a = mydb.insertData("Threat", "تهدید،خطر","/θret/");
            a = mydb.insertData("Ban", "منع کردن،ممنوع کردن","/bæn/");
            a = mydb.insertData("Panic", "سراسیمگی،هول","/ˈpænɪk/");
            a = mydb.insertData("Appropriate", "مناسب،درست"," /əˈproʊpriət/");
            a = mydb.insertData("Emerge", "بیرون آمدن","/iˈmɜːrdʒ/");
            a = mydb.insertData("Jagged", "دندانه دار","/ˈdʒæɡɪd/");
            a = mydb.insertData("Linger", "باقی ماندن،طول کشیدن","/ˈlɪŋɡər/");
            a = mydb.insertData("Ambush", "شبیخون زدن","/ˈæmbʊʃ/");
            a = mydb.insertData("Crafty", "ماهر،حرفه ای","/ˈkræfti/");
            a = mydb.insertData("Defiant", "نافرمان","/dɪˈfaɪənt/");
            a = mydb.insertData("Vigor", "قدرت،توان","/ˈvɪɡər/");
            a = mydb.insertData("Perish", "هلاک شدن","/ˈperɪʃ/");
            a = mydb.insertData("Fragile", "شکستنی","/ˈfrædʒl/");
            a = mydb.insertData("Captive", "اسیر جنگی،محبوس","/ˈkæptɪv/");
            a = mydb.insertData("Prosper", "رونق گرفتن","/ˈprɑːspər/");
            a = mydb.insertData("Devour", "بلعیدن،از چیزی مملو بودن","/dɪˈvaʊər/");
            a = mydb.insertData("Plea", "تقاضا","/pliː/");
            a = mydb.insertData("Weary", "خسته","/ˈwɪri/");
            a = mydb.insertData("Collide", "تصادف کردن","/kəˈlaɪd/");
            a = mydb.insertData("Confirm", "تتایید کردن","/kənˈfɜːrm/");
            a = mydb.insertData("Verify", "تأیید کنید","/ˈverɪfaɪ/");
            a = mydb.insertData("Anticipate", "انتظار داشتن","/ænˈtɪsɪpeɪt/");
            a = mydb.insertData("Dilemma", "دوراهی،تنگنا","/dɪˈlemə/");
            a = mydb.insertData("Detour", "راه انحرافی","/ˈdiːtʊr/");
            a = mydb.insertData("Merit", "شایستگی","/ˈmerɪt/");
            a = mydb.insertData("Transmit", "انتقال دادن","/trænsˈmɪt/");
            a = mydb.insertData("Relieve", "تسکین دادن درد","/rɪˈliːv/");
            a = mydb.insertData("Baffle", "سردرگم،گیج","/ˈbæfl/");
            a = mydb.insertData("Warden", "مسئول،نگهبان","/ˈwɔːrdn/");
            a = mydb.insertData("Acknowledge", "پذیرفتن،به رسمیت شناختن","/əkˈnɑːlɪdʒ/");
            a = mydb.insertData("Justice", "عدالت","/ˈdʒʌstɪs/");
            a = mydb.insertData("Delinquent", "بزهکار","/dɪˈlɪŋkwənt/");
            a = mydb.insertData("Reject", "نپذیرفتن","/rɪˈdʒekt/");
            a = mydb.insertData("Deprive", "محروم کردن از","/dɪˈpraɪv/");
            a = mydb.insertData("Spouse", "همسر","/spaʊz/");
            a = mydb.insertData("Vocation", "حرفه","/voʊˈkeɪʃn/");
            a = mydb.insertData("Unstable", "ناپایدار،متزلزل","/ʌnˈsteɪbl/");
            a = mydb.insertData("Homicide", "قتل،قاتل","/ˈhɑːmɪsaɪd/");
            a = mydb.insertData("Penalize", "اجحاف کردن،ظلم کردن","/ˈpiːnəlaɪz/");
            a = mydb.insertData("Beneficiary", "ذینفع،بهره مند","/ˌbenɪˈfɪʃieri/");
            a = mydb.insertData("Reptile", "جانور خزنده","/ˈreptaɪl/");
            a = mydb.insertData("Rarely", "به ندرت","/ˈrerli/");
            a = mydb.insertData("Forbid", "ممنوع کردن","/fərˈbɪd/");
            a = mydb.insertData("Logical", "منطقی","/ˈlɑːdʒɪkl/");
            a = mydb.insertData("Exhibit", "به نمایش گذاشتن","/ɪɡˈzɪbɪt/");
            a = mydb.insertData("Proceed", "پیش رفتن","/proʊˈsiːd/");
            a = mydb.insertData("Precaution", "احتیاط","/prɪˈkɔːʃn/");
            a = mydb.insertData("Extract", "بیرون کشیدن","/ˈekstrækt/");
            a = mydb.insertData("Prior", "قبلی،قبل از","/ˈpraɪər/");
            a = mydb.insertData("Embrace", "آغوش،بغل","/ɪmˈbreɪs/");
            a = mydb.insertData("Valiant", "شجاع","/ˈvæliənt/");
            a = mydb.insertData("Partial", "نسبی،نا تمام","/ˈpɑːrʃl/");
            a = mydb.insertData("Fierce", "خشن و وحشی","/fɪrs/");
            a = mydb.insertData("Detest", "متنفر بودن","/dɪˈtest/");
            a = mydb.insertData("Sneer", "ریشخند کردن","/snɪr/");
            a = mydb.insertData("Scowl", "اخم کردن","/skaʊl/");
            a = mydb.insertData("Encourage", "تشویق کردن","/ɪnˈkɜːrɪdʒ/");
            a = mydb.insertData("Consider", "درباره چیزی فکر کردن","/kənˈsɪdər/");
            a = mydb.insertData("Vermin", "آفات جانوری","/ˈvɜːrmɪn/");
            a = mydb.insertData("Wail", "شیون کردن","/weɪl/");
            a = mydb.insertData("Symbol", "نماد","/ˈsɪmbl/");
            a = mydb.insertData("Authority", "قدرت،مقتدر","/əˈθɔːrəti/");
            a = mydb.insertData("Neutral", "بی طرف","/ˈnuːtrəl/");
            a = mydb.insertData("Trifle", "امر جزئی","/ˈtraɪfl/");
            a = mydb.insertData("Architect", "معمار","/ˈɑːrkɪtekt/");
            a = mydb.insertData("Matrimony", "ازدواج","/ˈmætrɪmoʊni/");
            a = mydb.insertData("Baggage", "اسباب سفر","/ˈbæɡɪdʒ/");
            a = mydb.insertData("Squander", "ضایع کردن","/ˈskwɑːndər/");
            a = mydb.insertData("Abroad", "به کشور دیگر،خارج از کشور","/əˈbrɔːd/");
            a = mydb.insertData("Fugitive", "فراری","/ˈfjuːdʒətɪv/");
            a = mydb.insertData("Calamity", "مصیبت","/kəˈlæməti/");
            a = mydb.insertData("Pauper", "فقیر،مسکین","/ˈpɔːpər/");
            a = mydb.insertData("Envy", "حسادت،حسادت ورزیدن","/ˈenvi/");
            a = mydb.insertData("Collapse", "فروپاشی،شکست","/kəˈlæps/");
            a = mydb.insertData("Prosecute", "پیگرد قانونی","/ˈprɑːsɪkjuːt/");
            a = mydb.insertData("Bigamy", "داشتن دو همسر همزمان","/ˈbɪɡəmi/");
            a = mydb.insertData("Possible", "احتمال،امکان","/ˈpɑːsəbl/");
            a = mydb.insertData("Compel", "مجبور کردن","/kəmˈpel/");
            a = mydb.insertData("Awkward", "دست و پا چلفتی،شرمسارانه","/ˈɔːkwərd/");
            a = mydb.insertData("Venture", "به خطر انداختن،جرات کردن","/ˈventʃər/");
            a = mydb.insertData("Awesome", "عالی","/ˈɔːsəm/");
            a = mydb.insertData("Guide", "راهنما","/ɡaɪd/");
            a = mydb.insertData("Quench", "خاموش کردن،عطش را فرو نشاندن","/kwentʃ/");
            a = mydb.insertData("Betray", "نارو زدن،لو دادن","/bɪˈtreɪ/");
            a = mydb.insertData("Utter", "بر زبان آوردن","/ˈʌtər/");
            a = mydb.insertData("Pacify", "آرام کردن","/ˈpæsɪfaɪ/");
            a = mydb.insertData("Respond", "پاسخ دادن،واکنش نشان دادن","/rɪˈspɑːnd/");
            a = mydb.insertData("Beckon", "اشاره","/ˈbekən/");
            a = mydb.insertData("Despite", "بر خلاف،با وجود اینکه","/dɪˈspaɪt/");
            a = mydb.insertData("Disrupt", "متلاشی کردن","/dɪsˈrʌpt/");
            a = mydb.insertData("Rash", "جوش،عجولانه","/ræʃ/");
            a = mydb.insertData("Rapid", "سریع","/ˈræpɪd/");
            a = mydb.insertData("Exhaust", "تا ته مصرف کردن،خسته کردن","/ɪɡˈzɔːst/");
            a = mydb.insertData("Severity", "شدت","/sɪˈverəti/");
            a = mydb.insertData("Feeble", "ضعیف","/ˈfiːbl/");
            a = mydb.insertData("Unite", "متحد کردن","/juˈnaɪt/");
            a = mydb.insertData("Cease", "متوقف کردن","/siːs/");
            a = mydb.insertData("Thrifty", "صرفه جو","/ˈθrɪfti/");
            a = mydb.insertData("Miserly", "خسیس","/ˈmaɪzərli/");
            a = mydb.insertData("Monarch", "سلطنت","/ˈmɑːnɑːrk/");
            a = mydb.insertData("Outlaw", "قانون شکن","/ˈaʊtlɔː/");
            a = mydb.insertData("Promote", "ترویج","/prəˈmoʊt/");
            a = mydb.insertData("Underbrush", "فرومایه","/ˌʌndərˈnɜːrɪʃt/");
            a = mydb.insertData("Illustrate", "مصور","/ˈɪləstreɪt/");
            a = mydb.insertData("Disclose", "فاش کردن","/dɪsˈkloʊz/");
            a = mydb.insertData("Excessive", "بیش از حد","/ɪkˈsesɪv/");
            a = mydb.insertData("Disaster", "فاجعه","/dɪˈzæstər/");
            a = mydb.insertData("Censor", "سانسور کردن","/ˈsensər/");
            a = mydb.insertData("Culprit", "مجرم","/ˈkʌlprɪt/");
            a = mydb.insertData("Juvenile", "نوجوان","/ˈdʒuːvənl/");
            a = mydb.insertData("Bait", "طعمه","/beɪt/");
            a = mydb.insertData("Insist", "تاکید کردن","/ɪnˈsɪst/");
            a = mydb.insertData("Toil", "زحمت","/tɔɪl/");
            a = mydb.insertData("Blunder", "اشتباه بزرگ","/ˈblʌndər/");
            a = mydb.insertData("Daze", "مبهوت کردن","/deɪz/");
            a = mydb.insertData("Mourn", "سوگواری کردن","/mɔːrn/");
            a = mydb.insertData("Subside", "فروکشی کردن","/səbˈsaɪd/");
            a = mydb.insertData("Maim", "علیل کردن","/meɪm/");
            a = mydb.insertData("Comprehend", "فهمیدن،درک","/ˌkɑːmprɪˈhend/");
            a = mydb.insertData("Commend", "تحسین کردن","/kəˈmend/");
            a = mydb.insertData("Final", "نهایی","/ˈfaɪnl/");
            a = mydb.insertData("Exempt", "معاف","/ɪɡˈzempt/");
            a = mydb.insertData("Vain", "مغرور،بیهوده","/veɪn/");
            a = mydb.insertData("Repetition", "تکرار","/ˌrepəˈtɪʃn/");
            a = mydb.insertData("Depict", "تصور کن","/dɪˈpɪkt/");
            a = mydb.insertData("Mortal", "فناپذیر،مهلک","/ˈmɔːrtl/");
            a = mydb.insertData("Novel", "نوین،جدید،رمان","/ˈnɑːvl/");
            a = mydb.insertData("Occupant", "ساکن،مقیم","/ˈɑːkjəpənt/");
            a = mydb.insertData("Appoint", "منصوب کردن،مقرر کردن","/əˈpɔɪnt/");
            a = mydb.insertData("Quarter", "محل سکونت،اسکان دادن","/ˈkwɔːrtər/");
            a = mydb.insertData("Site", "محل","/saɪt/");
            a = mydb.insertData("Quote", "نقل قول کردن","/kwoʊt/");
            a = mydb.insertData("Verse", "آیه","/vɜːrs/");
            a = mydb.insertData("Morality", "اخلاقیات","/məˈræləti/");
            a = mydb.insertData("Roam", "پرسه زدن","/roʊm/");
            a = mydb.insertData("Attract", "جذب کردن","/əˈtrækt/");
            a = mydb.insertData("Commuter", "مسافر هر روزه","/kəˈmjuːtər/");
            a = mydb.insertData("Confine", "محبوس کردن","/kənˈfaɪn/");
            a = mydb.insertData("Idle", "بیکار،تنبل","/ˈaɪdl/");
            a = mydb.insertData("Idol", "بت،محبوب","/ˈaɪdl/");
            a = mydb.insertData("Jest", "شوخی کردن،شوخی","/dʒest/");
            a = mydb.insertData("Patriotic", "میهنی،میهن پرستانه","/ˌpeɪtriˈɑːtɪk/");
            a = mydb.insertData("Dispute", "مخالفت،جر و بحث","/ˈdɪspjuːt/");
            a = mydb.insertData("Valor", "شجاعت","/ˈvælər/");
            a = mydb.insertData("Lunatic", "احمق","/ˈluːnətɪk/");
            a = mydb.insertData("Vein", "سیاهرگ،ورید","/veɪn/");
            a = mydb.insertData("Uneventful", "بی حادثه،بدون رویداد مهم","/ˌʌnɪˈventfl/");
            a = mydb.insertData("Fertile", "بارور شده،حاصلخیز","/ˈfɜːrtl/");
            a = mydb.insertData("Refer", "اشاره کردن،ارجاع کردن","/rɪˈfɜːr/");
            a = mydb.insertData("Distress", "درماندگی،مخمصه","/dɪˈstres/");
            a = mydb.insertData("Diminish", "کاهش دادن","/dɪˈmɪnɪʃ/");
            a = mydb.insertData("Maximum", "حداکثر","/ˈmæksɪməm/");
            a = mydb.insertData("Flee", "فرار کردن","/fliː/");
            a = mydb.insertData("Vulnerable", "آسیب پذیر،حساس","/ˈvʌlnərəbl/");
            a = mydb.insertData("Signify", "معنی دادن،مهم بودن","/ˈsɪɡnɪfaɪ/");
            a = mydb.insertData("Mythology", "اسطوره شناسی","/mɪˈθɑːlədʒi/");
            a = mydb.insertData("Colleague", "همکار","/ˈkɑːliːɡ/");
            a = mydb.insertData("Torment", "درد و رنج،عذاب دادن","/ˈtɔːrment/");
            a = mydb.insertData("Provide", "تامین کردن","/prəˈvaɪd/");
            a = mydb.insertData("Loyalty", "وفاداری","/ˈlɔɪəlti/");
            a = mydb.insertData("Volunteer", "داوطلب","/ˌvɑːlənˈtɪr/");
            a = mydb.insertData("Prejudice", "تعصب","/ˈpredʒudɪs/");
            a = mydb.insertData("Shrill", "گوش خراش","/ʃrɪl/");
            a = mydb.insertData("Jolly", "شاد،خوش","/ˈdʒɑːli/");
            a = mydb.insertData("Witty", "شوخ","/ˈwɪti/");
            a = mydb.insertData("Hinder", "به تاخیر انداختن","/ˈhɪndər/");
            a = mydb.insertData("Lecture", "تدریس،نصیحت","/ˈlektʃər/");
            a = mydb.insertData("Abuse", "سو استفاده کردن","/əˈbjuːs/");
            a = mydb.insertData("Mumble", "من من کردن","/ˈmʌmbl/");
            a = mydb.insertData("Mute", "خاموش،بی صدا","/mjuːt/");
            a = mydb.insertData("Wad", "گلوله پنبه،دسته","/wɑːd/");
            a = mydb.insertData("Retain", "حفظ کردن،وکیل گرفتن","/rɪˈteɪn/");
            a = mydb.insertData("Candidate", "کاندیدا،نامزد","/ˈkændɪdət/");
            a = mydb.insertData("Precede", "مقدم بودن بر","/prɪˈsiːd/");
            a = mydb.insertData("Adolescent", "نوجوان","/ˌædəˈlesnt/");
            a = mydb.insertData("Coeducational", "مشارکتی","/ˌkoʊ edʒuˈkeɪʃənl/");
            a = mydb.insertData("Radical", "ریشه ای،افراطی،تندرو","/ˈrædɪkl/");
            a = mydb.insertData("Spontaneous", "خود جوش","/spɑːnˈteɪniəs/");
            a = mydb.insertData("Skim", "از روی چیزی گذشتن","/skɪm/");
            a = mydb.insertData("Vaccinate", "واکسن زدن","/ˈvæksɪneɪt/");
            a = mydb.insertData("Untidy", "نامرتب","/ʌnˈtaɪdi/");
            a = mydb.insertData("Utensil", "وسایل آشپزی","/juːˈtensl/");
            a = mydb.insertData("Sensitive", "حساس،زود رنج","/ˈsensətɪv/");
            a = mydb.insertData("Temperate", "معتدل","/ˈtempərət/");
            a = mydb.insertData("Vague", "مبهم،دو پهلو","/veɪɡ/");
            a = mydb.insertData("Elevate", "بلند کردن،ارتقاع دادن","/ˈelɪveɪt/");
            a = mydb.insertData("Lottery", "بخت آزمایی","/ˈlɑːtəri/");
            a = mydb.insertData("Finance", "امور مالی","/faɪˈnæns/");
            a = mydb.insertData("Obtain", "به دست آوردن","/əbˈteɪn/");
            a = mydb.insertData("Cinema", "سینما","/ˈsɪnəmə/");
            a = mydb.insertData("Event", "رویداد","/ɪˈvent/");
            a = mydb.insertData("Discard", "دور انداختن","/dɪˈskɑːrd/");
            a = mydb.insertData("Soar", "اوج گرفتن","/sɔːr/");
            a = mydb.insertData("Subsequent", "بعدی","/ˈsʌbsɪkwənt/");
            a = mydb.insertData("Relate", "مربوط بودن","/rɪˈleɪt/");
            a = mydb.insertData("Stationary", "ثابت","/ˈsteɪʃəneri/");
            a = mydb.insertData("Prompt", "باعث شدن","/prɑːmpt/");
            a = mydb.insertData("Hasty", "شتابزده،عجول","/ˈheɪsti/");
            a = mydb.insertData("Scorch", "جای سوختگی","/skɔːrtʃ/");
            a = mydb.insertData("Tempest", "مزاحم","/ˈtempɪst/");
            a = mydb.insertData("Soothe", "آرام کردن،تسکین دادن","/suːð/");
            a = mydb.insertData("Sympathetic", "همدرد","/ˌsɪmpəˈθetɪk/");
            a = mydb.insertData("Redeem", "باز خریدن،جبران کردن","/rɪˈdiːm/");
            a = mydb.insertData("Resume", "از سر گرفتن","/rɪˈzuːm/");
            a = mydb.insertData("Harmony", "سازگار","/ˈhɑːrməni/");
            a = mydb.insertData("Refrain", "برگردان،خودداری کردن","/rɪˈfreɪn/");
            a = mydb.insertData("Illegal", "بر خلاف قانون","/ɪˈliːɡl/");
            a = mydb.insertData("Narcotic", "ماده مخدر","/nɑːrˈkɑːtɪk/");
            a = mydb.insertData("Heir", "وارث","/er/");
            a = mydb.insertData("Majestic", "با عظمت","/məˈdʒestɪk/");
            a = mydb.insertData("Dwindle", "به تدریج ضعیف شدن","/ˈdwɪndl/");
            a = mydb.insertData("Surplus", "مازاد","/ˈsɜːrplʌs/");
            a = mydb.insertData("Traitor", "خائن","/ˈtreɪtər/");
            a = mydb.insertData("Deliberate", "حساب شده","/dɪˈlɪbərət/");
            a = mydb.insertData("Vandal", "خرابکار","/ˈvændl/");
            a = mydb.insertData("Drought", "خشکسالی","/draʊt/");
            a = mydb.insertData("Abide", "تحمل کردن","/əˈbaɪd/");
            a = mydb.insertData("Unify", "با هم متحد شدن","/ˈjuːnɪfaɪ/");
            a = mydb.insertData("Summit", "قله،اوج","/ˈsʌmɪt/");
            a = mydb.insertData("Heed", "توجه کردن به","/hiːd/");
            a = mydb.insertData("Biography", "شرح حال","/baɪˈɑːɡrəfi/");
            a = mydb.insertData("Drench", "خیس کردن","/drentʃ/");
            a = mydb.insertData("Swarm", "گله","/swɔːrm/");
            a = mydb.insertData("Wobble", "لرزیدن،لرزش","/ˈwɑːbl/");
            a = mydb.insertData("Tumult", "غوغا","/ˈtuːmʌlt/");
            a = mydb.insertData("Kneel", "زانو زدن","/niːl/");
            a = mydb.insertData("Dejected", "افسرده","/dɪˈdʒektɪd/");
            a = mydb.insertData("Obedient", "مطیع","/əˈbiːdiənt/");
            a = mydb.insertData("Recede", "عقب رفتن","/rɪˈsiːd/");
            a = mydb.insertData("Tyrant", "ظالم","/ˈtaɪrənt/");
            a = mydb.insertData("Charity", "صدقه،بخشش،خیریه","/ˈtʃærəti/");
            a = mydb.insertData("Verdict", "رای،عقیده","/ˈvɜːrdɪkt/");
            a = mydb.insertData("Unearth", "از زیر خاک بیرون کشیدن","/ʌnˈɜːrθ/");
            a = mydb.insertData("Depart", "ترک کردن،فوت کردن","/dɪˈpɑːrt/");
            a = mydb.insertData("Coincide", "همزمان بودن","/ˌkoʊɪnˈsaɪd/");
            a = mydb.insertData("Cancel", "باطل کردن","/ˈkænsl/");
            a = mydb.insertData("Debtor", "بدهکار","/ˈdetər/");
            a = mydb.insertData("Legible", "خوانا","/ˈledʒəbl/");
            a = mydb.insertData("Placard", "پلاکارد","/ˈplækɑːrd/");
            a = mydb.insertData("Contagious", "مسری،واگیر","/kənˈteɪdʒəs/");
            a = mydb.insertData("Clergy", "روحانیت","/ˈklɜːrdʒi/");
            a = mydb.insertData("Customary", "مرسوم،رایج","/ˈkʌstəmeri/");
            a = mydb.insertData("Transparent", "واضح،شفاف","/trænsˈpærənt/");
            a = mydb.insertData("Scald", "سوزاندن،سوختگی","/skɔːld/");
            a = mydb.insertData("Epidemic", "همه گیر","/ˌepɪˈdemɪk/");
            a = mydb.insertData("Obesity", "فربهی،چاقی","/oʊˈbiːsəti/");
            a = mydb.insertData("Magnify", "بزرگ کردن","/ˈmæɡnɪfaɪ/");
            a = mydb.insertData("Chiropractor", "پزشک متخصص دست یا پا","/ˈkaɪəroʊpræktər/");
            a = mydb.insertData("Obstacle", "مانع","/ˈɑːbstəkl/");
            a = mydb.insertData("Ventilate", "تهویه","/ˈventɪleɪt/");
            a = mydb.insertData("Jeopardize", "به خطر انداختن","/ˈdʒepərdaɪz/");
            a = mydb.insertData("Negative", "منفی","/ˈneɡətɪv/");
            a = mydb.insertData("Pension", "مستمری،حقوق بازنشستگی","/ˈpenʃn/");
            a = mydb.insertData("Vital", "ضروری،حیاتی","/ˈvaɪtl/");
            a = mydb.insertData("Municipal", "مربوط به شهرداری،شهری","/mjuːˈnɪsɪpl/");
            a = mydb.insertData("Oral", "شفاهی،دهانی","/ˈɔːrəl/");
            a = mydb.insertData("Complacent", "از خود راضی","/kəmˈpleɪsnt/");
            a = mydb.insertData("Wasp", "زنبور بدون عسل","/wɑːsp/");
            a = mydb.insertData("Rehabilitate", "توان بخشیدن","/ˌriːəˈbɪlɪteɪt/");
            a = mydb.insertData("Parole", "موقت","/pəˈroʊl/");
            a = mydb.insertData("Vertical", "عمودی","/ˈvɜːrtɪkl/");
            a = mydb.insertData("Multitude", "فراوانی،جمعیت","/ˈmʌltɪtuːd/");
            a = mydb.insertData("Nominate", "نامزد کردن","/ˈnɑːmɪneɪt/");
            a = mydb.insertData("Potential", "بالقوه","/pəˈtenʃl/");
            a = mydb.insertData("Morgue", "سردخانه","/mɔːrɡ/");
            a = mydb.insertData("Preoccupied", "مشغول و نگران","/priˈɑːkjupaɪd/");
            a = mydb.insertData("Upholstery", "رویه","/ʌpˈhoʊlstəri/");
            a = mydb.insertData("Indifference", "بی توجهی","/ɪnˈdɪfrəns/");
            a = mydb.insertData("Maintain", "حفظ","/meɪnˈteɪn/");
            a = mydb.insertData("Snub", "بی اعتنایی کردن","/snʌb/");
            a = mydb.insertData("Endure", "تحمل","/ɪnˈdʊr/");
            a = mydb.insertData("Wrath", "خشم","/ræθ/");
            a = mydb.insertData("Expose", "فاش کردن","/ɪkˈspoʊz/");
            a = mydb.insertData("Legend", "افسانه","/ˈledʒənd/");
            a = mydb.insertData("Ponder", "به فکر فرو رفتن","/ˈpɑːndər/");
            a = mydb.insertData("Resign", "استعفا دادن","/rɪˈzaɪn/");
            a = mydb.insertData("Drastic", "اساسی،بنیادی","/ˈdræstɪk/");
            a = mydb.insertData("Wharf", "اسکله","/wɔːrf/");
            a = mydb.insertData("Amend", "اصلاح کردن","/əˈmend/");
            a = mydb.insertData("ballot", "برگه رای","/ˈbælət/");
            //-------
            //a=mydb.insertSelf("boy","پسر");
        }

        //test send to site
        Button btntest=(Button) findViewById(R.id.addword);
        btntest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, ReadSite.class));
                finish();
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
                switch (i)
                {
                    case 0:
                        startActivity(new Intent(Home.this, GameBoard.class));
                        finish();
                        break;
                    case 1:
                        if(selftWords()!=0) {
                            startActivity(new Intent(Home.this, GameBoardSelf.class));
                            finish();
                        }
                        break;
                }
//                if(i==0) {
//                    startActivity(new Intent(Home.this, GameBoard.class));
//
//
//                }
//                if(i==1) {
//                    startActivity(new Intent(Home.this, GameBoardSelf.class));
//                }

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

    public int selftWords()
    {
        wordsArray.clear();
        ArrayList<String> mylist = new ArrayList<String>();
        String DATABASE_NAME = "EnglishWords.sqlite";
        String TABLE_NAME = "self";
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
        if(wordsArray.isEmpty())
            return 0;
        else
        return wordsArray.size();

    }
    // create other language in list
    public void itemDetails() {

//        arrayItem.add(new Item("ico_sem", "سمنانی", "----", "----"));
//        arrayItem.add(new Item("ico_san", "سنگسری", "----", "----"));
//        arrayItem.add(new Item("ico_maz", "مازندرانی", "----", "----"));
        arrayItem.add(new Item("book_504", Integer.toString(buildListWords()),"book"));
        arrayItem.add(new Item("ico_english",Integer.toString (selftWords()),"self"));
        String [] wordsArrFa = getResources().getStringArray(R.array.english);
//        arrayItem.add(new Item("ico_english", Integer.toString(wordsArrFa.length)));

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
