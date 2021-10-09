package com.example.gauryns.ekart.Sellers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gauryns.ekart.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SellerLoginActivity extends AppCompatActivity
{
    private TextView registerHere;
    private EditText emailInput, passwordInput;
    private Button sellerLoginBtn;

    private ProgressDialog loadingBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_login);

        registerHere = findViewById(R.id.seller_register_here);
        emailInput = findViewById(R.id.seller_login_email);
        passwordInput = findViewById(R.id.seller_login_password);
        sellerLoginBtn = findViewById(R.id.seller_login_btn);

        loadingBar = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();


        registerHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(SellerLoginActivity.this, SellerRegistrationActivity.class);
                startActivity(intent);
            }
        });

        sellerLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginSeller();
            }
        });
    }

    private void loginSeller()
    {
        final String email = emailInput.getText().toString();
        final String password = passwordInput.getText().toString();

        if (TextUtils.isEmpty(email))
        {
            emailInput.setError("Email Address Required");
            emailInput.requestFocus();
        }
        else if (TextUtils.isEmpty(password))
        {
            passwordInput.setError("Password Required");
            passwordInput.requestFocus();
        }
        else if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "All Fields are Empty", Toast.LENGTH_SHORT).show();
        }
        else
            {
                loadingBar.setTitle("Seller Account Login");
                loadingBar.setMessage("Please wait, while we are checking the credentials.");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();


                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task)
                            {
                                if (task.isSuccessful())
                                {
                                    loadingBar.dismiss();

                                    Intent intent = new Intent(SellerLoginActivity.this, SellerHomeActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();
                                    Toast.makeText(SellerLoginActivity.this, "Logged in Successfully.", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    loadingBar.dismiss();

                                    String error = task.getException().getMessage();
                                    Toast.makeText(SellerLoginActivity.this, error, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
    }
}
