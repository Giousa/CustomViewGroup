package com.giou.customviewgroup;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.giou.customviewgroup.model.Cheeses;
import com.giou.customviewgroup.view.DragLayout;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.lv_left)
    ListView mLvLeft;
    @InjectView(R.id.iv_head)
    ImageView mIvHead;
    @InjectView(R.id.lv_main)
    ListView mLvMain;
    @InjectView(R.id.dl)
    DragLayout mDragLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        initView();
    }

    private void initView() {
        mLvLeft.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Cheeses.sCheeseStrings) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                ((TextView) view).setTextColor(Color.WHITE);
                return view;
            }
        });

        mLvMain.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Cheeses.NAMES));

    }
}
