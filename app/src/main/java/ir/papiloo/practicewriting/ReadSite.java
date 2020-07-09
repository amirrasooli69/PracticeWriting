package ir.papiloo.practicewriting;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import ir.papiloo.practicewriting.R;

public class ReadSite extends AppCompatActivity {

    myDatabaseHelper mydb;
    Button book504,btnHome,selfWord;
    RequestQueue requestQueue;
    ProgressDialog pDialog;
    ImageView imageView;
    TextView tv;
    ListView list;


//    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.read_site);
        getSupportActionBar().hide();

        tv = findViewById(R.id.tv);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        pDialog = new ProgressDialog(this);
        pDialog.setTitle("wait....");
        pDialog.setMessage("");
        pDialog.setCancelable(true);


        book504=findViewById(R.id.book504);
        book504.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getJson();
            }
        });
        selfWord=findViewById(R.id.btnSelfWord);
        selfWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReadSite.this,SendWord.class));

            }
        });

        btnHome=findViewById(R.id.btnHome);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReadSite.this,Home.class));
                finish();
            }
        });



    }

    private void sendParamsGet() {

        String language, w, m, p;
        language = "amir";
        w = "rasooli";
        m = "semnan";
        p = "iran";
        String URI_SHOW_PARAMS = "https://papiloo.ir/Papiloo/Game/Insert.php?" +
                "Language=" + language + "&Word=" + w + "&Mean=" + m + "&Pronounce=" + p;

        URI_SHOW_PARAMS = URI_SHOW_PARAMS.replace(" ", "%20");

        StringRequest request = new StringRequest(
                Request.Method.GET,
                URI_SHOW_PARAMS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        tv.setText(response);
//                        pb.setVisibility(View.INVISIBLE);
                        pDialog.dismiss();
                        new AlertDialog.Builder(ReadSite.this)
                                .setTitle("Resposnse ")
                                .setMessage(response)
                                .setPositiveButton("OK", null)
                                .show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();
                        new AlertDialog.Builder(ReadSite.this)
                                .setTitle("Error ")
                                .setMessage(error.getMessage())
                                .setPositiveButton("OK", null)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                }
        ) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Word","boy");
                params.put("Mean", "پسر");
                return params;
            }
        };

        pDialog.show();
        requestQueue.add(request);
    }

    private void SaveDB(final Integer id ,final String word,
                        final String mean) {
        mydb = new myDatabaseHelper(this);

                boolean a = mydb.insertData(word,mean);
//                if (a)
//                    mes = "اطلاعات ذخیره شد";
//                else
//                    mes = "اطلاعات ذخیره نشد";
//
//                Toast.makeText(ReadSite.this, mes, Toast.LENGTH_LONG).show();
            //}
        //});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem item1 = menu.add("GET");
        item1.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        item1.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                sendParamsGet();
                return false;
            }
        });
        MenuItem item2 = menu.add("Post");
        item2.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        item2.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                return false;
            }
        });
        MenuItem item3 = menu.add("Image");
        item3.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        item3.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                LoadImage();
                return false;
            }
        });
        MenuItem item4 = menu.add("Json");
        item4.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        item4.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                getJson();
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void getJson() {
            //pb.setVisibility(View.VISIBLE);
         //String URI_SHOW_PARAMS = "https://papiloo.ir/Papiloo/practicewriting/returnJson.php";
        String URI_SHOW_PARAMS = "https://papiloo.ir/Papiloo/practicewriting/writing.php";

        //URI_SHOW_PARAMS = URI_SHOW_PARAMS.replace(" ", "%20");

        final StringRequest request = new StringRequest(
                Request.Method.GET,
                URI_SHOW_PARAMS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        FlowerJsonParser parser=new FlowerJsonParser(response);
                        pDialog.dismiss();
                        new AlertDialog.Builder(ReadSite.this)
                                .setTitle("پاسخ ")
                                .setMessage("کلمات کتاب 504 اضافه شد")
                                .setPositiveButton("بستن", null)
                                .show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();
                        new AlertDialog.Builder(ReadSite.this)
                                .setTitle(" خطادرارتباط با سرور")
                                .setMessage(error.getMessage())
                                .setPositiveButton("بستن", null)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                }
        );

        pDialog.show();
        requestQueue.add(request);
    }

    private void LoadImage() {
        String url =
                "https://cdn.kaprila.com/image/22/84036673-fed8-46f1-a66a-c0355298d461.jpg";
        //String url="https://www.papiloo.ir/images/pic.jpg";
        ImageRequest request = new ImageRequest(
                url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        Toast.makeText(ReadSite.this, "done", Toast.LENGTH_SHORT).show();
                        imageView.setImageBitmap(response);
                    }
                },
                160, 160,
                ImageView.ScaleType.FIT_CENTER,
                Bitmap.Config.ARGB_8888,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ReadSite.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(request);
    }



    //****************** FlowerJsonParser
    public class FlowerJsonParser  {

        myDatabaseHelper mydb;
        public FlowerJsonParser(String jsonString)
        {
            try {
                JSONArray json=new JSONArray(jsonString);
                for(int i=0;i<json.length();i++)
                {
                    JSONObject jsonObject = json.getJSONObject(i);
                    //Flower flower= new Flower();
                    Integer id=jsonObject.getInt("id");
                    //flower.setId(jsonObject.getInt("Id"));
//                    String category=jsonObject.getString("category");
//                    //flower.setWord(jsonObject.getString("category"));
                    String word=jsonObject.getString("word");
                    //flower.setWord(jsonObject.getString("Word"));
                    String mean = jsonObject.getString("mean");
                    //flower.setMean(jsonObject.getString("Mean"));

                    //flowerList.add(flower);
                    //tv.setText(id.toString() + " " + category + " " + word + " " + mean );
                    SaveDB(id,word,mean);
                }

                Toast.makeText(ReadSite.this, "کلمات جدید ذخیره شد", Toast.LENGTH_SHORT).show();

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }



    //****************** MyDataBaseHelper
    public static class myDatabaseHelper extends SQLiteOpenHelper {
        public static final String DB_NAME= "EnglishWords.sqlite";
        public static final String TBL_NAME= "practice";
        public static final String TBL_SELF= "self";
        public String DB_PATH;
        private Context myContext;

        public myDatabaseHelper(@Nullable Context context) {
            super(context,DB_NAME,null,1);
            SQLiteDatabase db= this.getWritableDatabase();

            //this.DB_PATH = "/data/data/" + context.getPackageName() + "/databases/word.sqlite";
        }


        public void copyDataBase() throws IOException {
            try {
                InputStream myInput = myContext.getAssets().open(DB_NAME);
                String outFileName = DB_PATH + DB_NAME;
                OutputStream myOutput = new FileOutputStream(outFileName);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }
                myOutput.flush();
                myOutput.close();
                myInput.close();
            } catch (IOException ignored) {
            }
//            File dir = new File(DB_PATH);
//            if ( !dir.exists()) {
//                dir.mkdirs();
//            }
//            File database = new File(dir, DB_NAME);
//            if ( !database.exists()) {
//
//                InputStream myInput = myContext.getAssets().open(DB_NAME);
//                String outFileName = DB_PATH + DB_NAME;
//                OutputStream myOutput = new FileOutputStream(outFileName);
//                byte[] buffer = new byte[1024];
//                int length;
//                while ((length = myInput.read(buffer)) > 0) {
//                    myOutput.write(buffer, 0, length);
//                }
//                myOutput.flush();
//                myOutput.close();
//                myInput.close();
//            }
        }
        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL("CREATE TABLE " + TBL_NAME+ "(Id INTEGER PRIMARY KEY AUTOINCREMENT , " + " word TEXT UNIQUE" + ", mean TEXT )");
            db.execSQL("CREATE TABLE " + TBL_SELF+ "(Id INTEGER PRIMARY KEY AUTOINCREMENT , " + " word TEXT UNIQUE" + ", mean TEXT )");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            //db.execSQL(" DROP TABLE IF EXISTS " + TBL_NAME);
            //onCreate(db);
        }

        public boolean insertData(String word, String mean)
        {
            SQLiteDatabase db=this.getWritableDatabase();

            ContentValues cv =new ContentValues();
//            cv.put("Id",Id);
//            cv.put("category",category);
            cv.put("word",word);
            cv.put("mean",mean);


            long result=db.insert(TBL_NAME,null,cv);

            if(result==-1)
                return  false;
            else
                return true;
        }
        public boolean insertSelf(String word, String mean)
        {
            SQLiteDatabase db=this.getWritableDatabase();

            ContentValues cv =new ContentValues();
//            cv.put("Id",Id);
//            cv.put("category",category);
            cv.put("word",word);
            cv.put("mean",mean);
            long result=db.insert(TBL_SELF,null,cv);
            if(result==-1)
                return  false;
            else
                return true;
        }
        public boolean deleteData(String word)
        {
            SQLiteDatabase db= this.getWritableDatabase();

//            Cursor result = db.rawQuery("SELECT * FROM "+ TBL_SELF +" WHERE Id=" + 1,null);
            int deleterow = db.delete(TBL_SELF,"word = ? ",new String[]{word.toString()});
            Log.i("Delete row count=",Integer.toString(deleterow));
            if(deleterow>0)
                return true;
            else
                return false;

//            if(result==0)
//                return false;
//            else
//                return true;
        }

        public boolean updateData(String id, String n)
        {
            SQLiteDatabase db= this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("word",n);

            long result = db.update(TBL_NAME,cv,"Id=?",new String[] {id});

            if(result<1)
                return  false;
            else
                return true;

        }

        public Cursor ShowallData()
        {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor result = db.rawQuery("select * from " + TBL_NAME,null);
            return result;
        }

    }





}

