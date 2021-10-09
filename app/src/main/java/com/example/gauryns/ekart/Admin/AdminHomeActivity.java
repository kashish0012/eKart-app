package com.example.gauryns.ekart.Admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.gauryns.ekart.Buyers.HomeActivity;
import com.example.gauryns.ekart.Buyers.MainActivity;
import com.example.gauryns.ekart.R;
import com.example.gauryns.ekart.Sellers.SellerProductCategoryActivity;

public class AdminHomeActivity extends AppCompatActivity
{
    private Button logoutAdmin, CheckOrdersBtn, maintainProductsBtn, checkApproveProductsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        logoutAdmin = (Button) findViewById(R.id.category_logout_admin);
        CheckOrdersBtn = (Button) findViewById(R.id.category_check_new_order);
        maintainProductsBtn = (Button) findViewById(R.id.category_maintain_btn);
        checkApproveProductsBtn = (Button) findViewById(R.id.check_approve_products_btn);

        logoutAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminHomeActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();

                Toast.makeText(AdminHomeActivity.this, "Admin Logged out", Toast.LENGTH_SHORT).show();
            }
        });

        CheckOrdersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminHomeActivity.this, AdminNewOrdersActivity.class);
                startActivity(intent);
            }
        });

        maintainProductsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminHomeActivity.this, HomeActivity.class);
                intent.putExtra("Admin", "Admin");
                startActivity(intent);
            }
        });

        checkApproveProductsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminHomeActivity.this, AdminCheckNewProductsActivity.class);
                startActivity(intent);
            }
        });

    }
}
