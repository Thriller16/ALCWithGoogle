package ng.com.africasupport.alcwithgoogle;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    EditText mEmailEdt, mPasswordEdt;
    Button mRegBtn;
    FirebaseAuth mFireAuth;
    ProgressDialog mProgressDialog;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().setTitle("Sign Up");

        mEmailEdt = findViewById(R.id.email_reg);
        mPasswordEdt = findViewById(R.id.passw_reg);
        mRegBtn = findViewById(R.id.reg_btn);
        mProgressDialog = new ProgressDialog(this);

        mFireAuth = FirebaseAuth.getInstance();

        mRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgressDialog.show();
                mProgressDialog.setTitle("Please Wait");
                mProgressDialog.setMessage("Creating your account");
                registerNewUser(mEmailEdt.getText().toString(), mPasswordEdt.getText().toString());

            }
        });

    }

    public  void registerNewUser(String email, String password){
        mFireAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    mProgressDialog.dismiss();
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                }
            }
        });
    }
}
