package com.example.lifei.myapplication;

import android.app.Activity;
import android.os.Bundle;

import com.netease.ioc.library.InjectManager;

/**
 * Created by Administrator on 2019/9/19.
 */

public class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectManager.inject(this);
    }
}
