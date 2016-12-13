package com.giou.customviewgroup;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.giou.customviewgroup.model.Cheeses;
import com.giou.customviewgroup.view.DragLayout;

import java.util.Random;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity implements DragLayout.OnDragChangeListener {


    private final String TAG = MainActivity.class.getSimpleName();

    @InjectView(R.id.lv_left)
    ListView mLvLeft;
    @InjectView(R.id.iv_head)
    ImageView mIvHead;
    @InjectView(R.id.lv_main)
    ListView mLvMain;
    @InjectView(R.id.dl)
    DragLayout mDragLayout;
    private Random mRandom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        initView();
        initListener();

    }


    private void initView() {

        mRandom = new Random();

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


    private void initListener() {
        mDragLayout.setOnDragChangeListener(this);
    }

    @Override
    public void onClosed() {
        Log.d(TAG,"setOnDragChangeListener  onClosed");
    }

    @Override
    public void onOpened() {
        Log.d(TAG,"setOnDragChangeListener  onOpened");
        mLvLeft.smoothScrollToPosition(mRandom.nextInt(50));

    }

    @Override
    public void onDraging(float percent) {
        Log.d(TAG,"setOnDragChangeListener  onDraging  percent="+percent);
        mIvHead.setAlpha(1.0f-percent);
    }
}
