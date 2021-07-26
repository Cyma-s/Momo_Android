package com.example.momo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class BasicAddressSetActivity1 extends AppCompatActivity {
    // 주소 요청코드 상수 requestCode
    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;
    private EditText editAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_basic_address_set1);

        editAddress = findViewById(R.id.address);

        editAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BasicAddressSetActivity1.this, AddressApiActivity.class);
                //화면전환 애니메이션 없앰
                overridePendingTransition(0, 0);
                startActivity(i);
            }
        });
    }
}