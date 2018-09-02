package com.malong.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.malong.myliveshow.R;

/**
 * Created by Malong
 * on 18/8/7.
 * 自定义View
 */
public class CanvasActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv_back;
    private TextView tv_title;
    private CanvasView cv;
    private int[][] columInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);


        columInfo = new int[][]{
                {6, Color.BLUE},
                {5, Color.GREEN},
                {4, Color.RED},
                {3, Color.YELLOW},
                {2, Color.GRAY},
                {1, Color.LTGRAY}
        };

        init();
    }

    private void init() {
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        tv_title = findViewById(R.id.tv_title);
        tv_title.setText(R.string.canvas_view);

        cv = findViewById(R.id.cv);//自定义View
        cv.setColumnInfo(columInfo);
        cv.setXAxisValue(10, 9);
        cv.setYAxisValue(10, 7);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.iv_back:
                finish();
                break;

        }
    }
}
