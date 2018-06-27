package ng.com.africasupport.alcwithgoogle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth mFireAuth;
    EditText mPassword;
    EditText mEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        The firebase instance is being initiated here
        mFireAuth = FirebaseAuth.getInstance();
    }

//    THis fuction  signs in the user
    public void signInUser(){

//        mPassword = findViewById(R.id.)
        mFireAuth.signInWithEmailAndPassword(mEmail.getText().toString(), mPassword.getText().toString());

    }
}
