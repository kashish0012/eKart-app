package com.example.gauryns.ekart.Admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.gauryns.ekart.R;
import com.example.gauryns.ekart.Sellers.SellerHomeActivity;
import com.example.gauryns.ekart.Sellers.SellerProductCategoryActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class AdminMaintainProductsActivity extends AppCompatActivity
{
    private Button applyChangesBtn, deleteBtn;
    private EditText name, price, description;
    private ImageView imageView;

    private String productID = "";
    private DatabaseReference productRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_maintain_products);


        productID = getIntent().getStringExtra("pid");
        productRef = FirebaseDatabase.getInstance().getReference().child("Products").child(productID);

        applyChangesBtn = findViewById(R.id.apply_changes_btn);
        deleteBtn = findViewById(R.id.delete_this_btn);
        name = findViewById(R.id.product_name_maintain);
        price = findViewById(R.id.product_price_maintain);
        description = findViewById(R.id.product_description_maintain);
        imageView = findViewById(R.id.product_image_maintain);


        displaySpecificProductInfo();


        applyChangesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applyChanges();
            }
        });


        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                CharSequence options[] = new CharSequence[]
                        {
                                "Yes",
                                "No"
                        };

                AlertDialog.Builder builder = new AlertDialog.Builder(AdminMaintainProductsActivity.this);
                builder.setTitle("Do you want to Delete this Product. Are you Sure?");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position)
                    {
                        if (position == 0)
                        {
                            deleteThisProduct();
                        }
                        if (position == 1)
                        {

                        }
                    }
                });
                builder.show();
            }
        });

    }

    private void deleteThisProduct()
    {
        productRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) 
            {
                Intent intent = new Intent(AdminMaintainProductsActivity.this, AdminHomeActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(AdminMaintainProductsActivity.this, "The Product is deleted successfully...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void applyChanges()
    {
       String pName = name.getText().toString();
       String pPrice = price.getText().toString();
       String pDescription = description.getText().toString();

       if (TextUtils.isEmpty(pName))
       {
           name.setError("Product Name Required");
           name.requestFocus();
       }
       else if (TextUtils.isEmpty(pPrice))
       {
           price.setError("Product Price Required");
           price.requestFocus();
       }
       else if (TextUtils.isEmpty(pDescription))
       {
           description.setError("Product Description Required");
           description.requestFocus();
       }
       else if (TextUtils.isEmpty(pName) && TextUtils.isEmpty(pPrice) && TextUtils.isEmpty(pDescription))
       {
           Toast.makeText(this, "All Fields are empty", Toast.LENGTH_SHORT).show();
       }
       else
       {
           HashMap<String, Object> productMap = new HashMap<>();
           productMap.put("pid", productID);
           productMap.put("description", pDescription);
           productMap.put("price", pPrice);
           productMap.put("pname", pName);

           productRef.updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
               @Override
               public void onComplete(@NonNull Task<Void> task)
               {
                   if (task.isSuccessful())
                   {
//
                           Toast.makeText(AdminMaintainProductsActivity.this, "Changes applied successfully", Toast.LENGTH_SHORT).show();

                           Intent intent = new Intent(AdminMaintainProductsActivity.this, AdminHomeActivity.class);
                           startActivity(intent);
                           finish();
//
                   }
               }
           });
       }

    }

    private void displaySpecificProductInfo()
    {
       productRef.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot)
           {
               if (dataSnapshot.exists())
               {
                   String pName = dataSnapshot.child("pname").getValue().toString();
                   String pPrice = dataSnapshot.child("price").getValue().toString();
                   String pDescription = dataSnapshot.child("description").getValue().toString();
                   String pImage = dataSnapshot.child("image").getValue().toString();

                   name.setText(pName);
                   price.setText(pPrice);
                   description.setText(pDescription);
                   Picasso.get().load(pImage).into(imageView);
               }
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });
    }
}
