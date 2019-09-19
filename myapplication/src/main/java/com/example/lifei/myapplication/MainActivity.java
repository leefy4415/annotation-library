package com.example.lifei.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.lifei.myapplication.rxjava.RxJavaTest0;
import com.netease.ioc.library.annotation.ContentView;
import com.netease.ioc.library.annotation.InjectView;
import com.netease.ioc.library.annotation.OnClick;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

//    @InjectView(R.id.btn)
//    Button btn;

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RxJavaTest0.test1();
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        /*btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, btn.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    @OnClick(R.id.btn)
    private void show() {
        Toast.makeText(MainActivity.this, "Hello IOC", Toast.LENGTH_SHORT).show();
    }
}
