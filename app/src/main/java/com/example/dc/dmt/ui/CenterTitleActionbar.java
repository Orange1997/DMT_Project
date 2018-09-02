package com.example.dc.dmt.ui;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.dc.dmt.R;

/**
 * Created by DC on 2018/9/2.
 */

public class CenterTitleActionbar extends AppBarLayout{
    Toolbar toolbar;
    TextView tv_title;
    TextView tv_Right;
    AppBarLayout appBarLayout;
    RightClick rightClick=null;

    public void setOnRightClickListener(RightClick rightClickListener){
        rightClick = rightClickListener;
    }
    public interface RightClick{
        public void onRightClick();
    }
    public CenterTitleActionbar(Context context) {
        super(context);
    }

    public CenterTitleActionbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_center_title_actionbar, this, true);
        toolbar=(Toolbar)findViewById(R.id.toolbar_actionbar);
        tv_title=(TextView)findViewById(R.id.tv_title_actionbar);
        tv_Right=(TextView)findViewById (R.id.tv_right);
        appBarLayout=(AppBarLayout)findViewById(R.id.appBarLayout);
        if (isInEditMode()) { return; }
        tv_Right.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                rightClick.onRightClick ();
            }
        });
    }
    public Toolbar getToolbar()
    {
        return toolbar;
    }
    public AppBarLayout getAppBarLayout()
    {
        return appBarLayout;
    }
    public void setNavigationIcon(int iconId){
        toolbar.setNavigationIcon (iconId);
    }
    public void setTitle(String title)
    {
        tv_title.setText(title);
    }
    public void isRightShow(boolean isRightShow){
        if (isRightShow){
            tv_Right.setVisibility (VISIBLE);
        }else {
            tv_Right.setVisibility (GONE);
        }

    }
    public void setRightText(String text)
    {
        tv_Right.setText(text);
    }
    public TextView getTv_Right(){
        return tv_Right;
    }
    public TextView getTvTitle()
    {
        return tv_title;
    }}
