package ir.papiloo.practiceenglish;

        import android.content.Context;
        import android.content.Intent;
        import android.net.Uri;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;

public class Email extends AppCompatActivity {
    EditText txtSub, txtMsg;
    Button btnSend,btnCancel,btnBack;
    String to, subject, message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eamil);
        getSupportActionBar().hide();
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        to="papiloosoft@gmail.com";
        txtSub =  findViewById(R.id.txtSub);
        txtMsg =  findViewById(R.id.txtMsg);
        btnSend =  findViewById(R.id.btnSend);
        btnCancel=findViewById(R.id.btnCancel);
        btnBack=findViewById(R.id.btnBack);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
//                subject = txtSub.getText().toString();
//                message = txtMsg.getText().toString();
//
//                Intent email = new Intent(Intent.ACTION_SEND);
//                email.putExtra(Intent.EXTRA_EMAIL, new String[]{ to});
//                email.putExtra(Intent.EXTRA_SUBJECT, subject);
//                email.putExtra(Intent.EXTRA_TEXT, message);
//
//                //need this to prompts email client only
//                email.setType("message/rfc822");
//
//                startActivity(Intent.createChooser(email, "Choose Email client :"));
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Email.this,Idea.class));
                finish();
            }
        });
    }

    private void sendEmail() {


//        String[] TO = {"papiloosoft@gmail.com","amirrasooli69@gmail.com"};
//
//        String[] CC = {"تمرین لغت"};
//
//        Intent emailIntent = new Intent(Intent.ACTION_SEND);
//
//        emailIntent.setData(Uri.parse("mailto:"));
//
//        emailIntent.setType("text/plain");
//        subject = txtSub.getText().toString();
//        message = txtMsg.getText().toString();
//
//        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
//        emailIntent.putExtra(Intent.EXTRA_CC, CC);
//        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
//        emailIntent.putExtra(Intent.EXTRA_TEXT, message);
//
//
//


        try {
            subject = txtSub.getText().toString();
            message = txtMsg.getText().toString();
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("message/rfc822");
            i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"papiloosoft@gmail.com"});
            i.putExtra(Intent.EXTRA_SUBJECT, subject);
            i.putExtra(Intent.EXTRA_TEXT   , message);
            try {
                startActivity(Intent.createChooser(i, "Send mail..."));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(Email.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
            }

//            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "papiloosoft@gmail.com", null));
//            intent.putExtra(subject, message);
//            startActivity(Intent.createChooser(intent, "لطفا انتخاب کنید...  :"));
//
//
////            startActivity(Intent.createChooser(emailIntent, "Send mail…"));
//            Toast.makeText(Email.this, "فرستاده شد", Toast.LENGTH_SHORT).show();

            //finish();


        } catch (android.content.ActivityNotFoundException ex) {

            Toast.makeText(Email.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();

        }
    }


}
