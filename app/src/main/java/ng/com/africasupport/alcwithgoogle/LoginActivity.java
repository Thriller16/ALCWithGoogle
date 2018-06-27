package ng.com.africasupport.alcwithgoogle;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth mFireAuth;
    EditText mPasswordEdt;
    EditText mEmailEdt;
    ProgressDialog mProgressDialog;
    TextView textView;
    Button mLoginBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        getSupportActionBar().setTitle("Log In");
        textView = findViewById(R.id.go_to_reg);
        mLoginBtn = findViewById(R.id.login_btn);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
//        The firebase instance is being initiated here
        mFireAuth = FirebaseAuth.getInstance();
        mProgressDialog = new ProgressDialog(this);



        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInUser();
            }
        });
    }

//    THis fuction  signs in the user
    public void signInUser(){

        mProgressDialog.show();


        mPasswordEdt = findViewById(R.id.passw_login);
        mEmailEdt = findViewById(R.id.email_login);

        mFireAuth.signInWithEmailAndPassword(mEmailEdt.getText().toString(), mPasswordEdt.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                mProgressDialog.dismiss();
                startActivity(new Intent(LoginActivity.this, MainActivity.class).
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK));
            }
        });


    }

    @Override
    public void onBackPressed() {
        finish();
        System.exit(0);
        super.onBackPressed();
    }
}
