package com.cy.yangbo.bezieranimator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.iv_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new TriangleMultiFunctionDialog(MainActivity.this).show();
                new CircleMultiFunctionDialog(MainActivity.this).show();
            }
        });
    }
}
