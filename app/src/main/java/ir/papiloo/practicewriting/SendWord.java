package ir.papiloo.practicewriting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

import ir.papiloo.words.R;

public class SendWord extends AppCompatActivity {

    RequestQueue requestQueue;
    Button btnSend,btnHome;
    TextView word,mean,pronounce,txtResult;
    Spinner category;
    ProgressBar pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_word);

        word = findViewById(R.id.txtWord);
        mean = findViewById(R.id.txtMeansWord);
        pronounce = findViewById(R.id.txtPronounce);
        txtResult = findViewById(R.id.result);
        pb = findViewById(R.id.progressBar);
        pb.setVisibility(View.INVISIBLE);
        btnSend = findViewById(R.id.btnSend);
        btnHome=findViewById(R.id.btnHome);
        requestQueue = Volley.newRequestQueue(getApplicationContext());


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //add value in spinner
        category =(Spinner) findViewById(R.id.selectCategory);
        List<String> list = new ArrayList<String>();
        list.add("فارسی");
        list.add("سمنانی");
        list.add("سنگسری");
        list.add("مازندرانی");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(adapter);
        //____________________
        btnSend.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                txtResult.setText("");
                String language,w,m,p;
                language= category.getSelectedItem().toString();
                w =  word.getText().toString();
                m = mean.getText().toString();
                p = pronounce.getText().toString();
                if(w.isEmpty())
                {
                    txtResult.setText("کلمه را بنویسید");
                    return;
                }
                String URI_SHOW_PARAMS = "https://Papiloo.ir/Papiloo/Game/Insert.php"+"?"+"Language=" +
                        language + "&Word="+w+"&Mean="+m+"&Pronounce="+p;

                URI_SHOW_PARAMS=URI_SHOW_PARAMS.replace(" ","%20");
                StringRequest request=new StringRequest(
                        Request.Method.GET,
                        URI_SHOW_PARAMS,
                        response -> {
                            txtResult.setText(response);
                            pb.setVisibility(View.INVISIBLE);
                            word.setText("");
                            mean.setText("");
                            pronounce.setText("");
                            word.requestFocus();
                        },
                        error -> {
                            txtResult.setText("لغت ثبت نشد");
                            pb.setVisibility(View.INVISIBLE);
                        });

                requestQueue.add(request);


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

