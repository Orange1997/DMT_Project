package com.example.dc.dmt.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

/**
 * Created by DC on 2018/9/2.
 */

public abstract class BaseCompatActivity extends AppCompatActivity {
    public BaseCompatActivity() {
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initVariables();
        this.initView(savedInstanceState);
        this.loadData();
    }

    public void setSupportActionBar(@Nullable Toolbar toolbar) {
        this.getDelegate().setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener () {
            public void onClick(View v) {
                if(BaseCompatActivity.this.onNavigation()) {
                    BaseCompatActivity.this.onBackPressed();
                }

            }
        });
        toolbar.setTitle(this.getTitle().toString());
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    protected abstract boolean onNavigation();

    protected abstract void initVariables();

    protected abstract void initView(Bundle var1);

    protected abstract void loadData();
}