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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SellerRegistrationActivity extends AppCompatActivity
{

    private TextView sellerLoginBegin;
    private EditText nameInput, phoneInput, passwordInput, emailInput, addressInput;
    private Button registerBtn;

    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_registration);

        sellerLoginBegin = findViewById(R.id.seller_already_have_an_account);
        nameInput = findViewById(R.id.seller_name);
        phoneInput = findViewById(R.id.seller_phone);
        emailInput = findViewById(R.id.seller_email);
        passwordInput = findViewById(R.id.seller_password);
        addressInput = findViewById(R.id.seller_address);
        registerBtn = findViewById(R.id.seller_register_btn);


        mAuth = FirebaseAuth.getInstance();

        loadingBar = new ProgressDialog(this);


        sellerLoginBegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent =  new Intent(SellerRegistrationActivity.this, SellerLoginActivity.class);
                startActivity(intent);
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                registerSeller();
            }
        });

    }

    private void registerSeller()
    {
       final String name = nameInput.getText().toString();
       final String phone = phoneInput.getText().toString();
       final String email = emailInput.getText().toString();
       final String password = passwordInput.getText().toString();
       final String address = addressInput.getText().toString();

       if (TextUtils.isEmpty(name))
       {
           nameInput.setError("Full name Required");
           nameInput.requestFocus();
       }
       else if (!(phone.length() == 10))
       {
           phoneInput.setError("Please enter a valid phone");
           phoneInput.requestFocus();
       }
       else if (TextUtils.isEmpty(email))
       {
           emailInput.setError("Email Address Required");
           emailInput.requestFocus();
       }
       else if (TextUtils.isEmpty(password))
       {
           passwordInput.setError("Password Required");
           passwordInput.requestFocus();
       }
       else if (TextUtils.isEmpty(address))
       {
           addressInput.setError("Full Address Required");
           addressInput.requestFocus();
       }
       else if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(address))
       {
           Toast.makeText(this, "All Fields are Empty", Toast.LENGTH_SHORT).show();
       }
       else
       {
           loadingBar.setTitle("Creating Seller Account");
           loadingBar.setMessage("Please wait, while we are checking the credentials.");
           loadingBar.setCanceledOnTouchOutside(false);
           loadingBar.show();

           mAuth.createUserWithEmailAndPassword(email, password)
                   .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task)
                       {
                           if (task.isSuccessful())
                           {
                               final DatabaseReference rootRef;
                               rootRef = FirebaseDatabase.getInstance().getReference().child("Sellers");

                               String sid = mAuth.getCurrentUser().getUid();

                               HashMap<String, Object> sellerMap = new HashMap<>();
                               sellerMap.put("sid", sid);
                               sellerMap.put("phone", phone);
                               sellerMap.put("email", email);
                               sellerMap.put("address", address);
                               sellerMap.put("password", password);
                               sellerMap.put("name", name);

                               rootRef.child(sid).updateChildren(sellerMap)
                                       .addOnCompleteListener(new OnCompleteListener<Void>() {
                                           @Override
                                           public void onComplete(@NonNull Task<Void> task)
                                           {

                                               if (task.isSuccessful())
                                               {
                                                   loadingBar.dismiss();

                                                   Intent intent = new Intent(SellerRegistrationActivity.this, SellerLoginActivity.class);
                                                   intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                   startActivity(intent);
                                                   finish();
                                                   Toast.makeText(SellerRegistrationActivity.this, "You are Registered Successfully.", Toast.LENGTH_SHORT).show();
                                               }
                                               else
                                               {
                                                   loadingBar.dismiss();
                                                   String message = task.getException().toString();
                                                   Toast.makeText(SellerRegistrationActivity.this, "Error " + message, Toast.LENGTH_SHORT).show();
                                               }
                                           }
                                       });
                           }
                       }
                   });
       }

    }
}
