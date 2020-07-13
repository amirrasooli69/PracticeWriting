package ir.papiloo.practiceenglish;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

public class SendWord extends AppCompatActivity {
    ReadSite.myDatabaseHelper mydb;
    RequestQueue requestQueue;
    Button btnSave,btnHome;
    TextView word,mean,pronounce,txtResult;
    Spinner category;
    ProgressBar pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save_word);
        word = findViewById(R.id.txtWord);
        mean = findViewById(R.id.txtMeansWord);
        pronounce = findViewById(R.id.txtPronounce);
        txtResult = findViewById(R.id.result);
        pb = findViewById(R.id.progressBar);
        pb.setVisibility(View.INVISIBLE);
        btnSave = findViewById(R.id.btnSave);
        btnHome=findViewById(R.id.btnHome);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        getSupportActionBar().hide();
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //add value in spinner
        category =(Spinner) findViewById(R.id.selectCategory);
        List<String> list = new ArrayList<String>();
        list.add("انگلیسی");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(adapter);
        //____________________
        btnSave.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                txtResult.setText("");
                String language, w, m, p;
                language = category.getSelectedItem().toString();
                w = word.getText().toString();
                m = mean.getText().toString();
                p = pronounce.getText().toString();
                if (w.isEmpty()) {
                    txtResult.setText("کلمه را بنویسید");
                    return;
                }

                mydb = new ReadSite.myDatabaseHelper(SendWord.this);
                boolean a = mydb.insertSelf(w, m);
                if (a == true) {
                    txtResult.setText("کلمه شما ثبت شد");
                } else
                {
                    txtResult.setText("کلمه ثبت نشد");
                }
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SendWord.this,Home.class));
                finish();
            }
        });
    }

}