package com.example.minepage;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.minepage.adapter.MineAdapter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private RecyclerView rv;
    //存储recyclerview滑动偏移量
    private int temp = 0;
    private ImageView iv;
    private int height, newHeight, newWidth;
    private ViewGroup.LayoutParams layoutParams;
    private int width;
    private float downY;
    private float mY;
    private float distanceY;
    private MineAdapter mineAdapter;
    private int preHeight;
    boolean isDone = false;
    private LinearLayoutManager linearLayoutManager;
    private int screenHeight;
    private int screenWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv = (RecyclerView) findViewById(R.id.rv);
        linearLayoutManager = new LinearLayoutManager(this);

        rv.setLayoutManager(linearLayoutManager);
        mineAdapter = new MineAdapter(this);
        rv.setAdapter(mineAdapter);
        //因为布局渲染需要一定的时间，几毫秒的时间
        // 如果在这几毫秒之内或之前你做了控件的属性获取，有可能会报空指针异常
        initRvTouchListener(rv);
        WindowManager windowManager = getWindowManager();
        Display defaultDisplay = windowManager.getDefaultDisplay();
//        DisplayMetrics metrics = new DisplayMetrics();
//        defaultDisplay.getMetrics(metrics);
//        int widthPixels = metrics.widthPixels;
//        int heightPixels = metrics.heightPixels;
        screenHeight = defaultDisplay.getHeight();
        screenWidth = defaultDisplay.getWidth();


    }

    private void initRvTouchListener(final RecyclerView rv) {
        rv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (mineAdapter.headViewHolder == null) {
                            return false;
                        }
                        layoutParams = mineAdapter.headViewHolder.iv.getLayoutParams();
                        height = layoutParams.height;
                        //拿到最原始的高度
                        if (preHeight == 0) {
                            preHeight = height;
                        }
                        //拿到按下时y的坐标
                        downY = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        //拿到手指滑动时当时的坐标
                        mY = event.getY();
                        distanceY = mY - downY;
                        newHeight = (int) (preHeight + distanceY);
                        newWidth = (int) (screenWidth + distanceY * 0.5f);
                        //当手指往上滑的时候，有时   newHeight 会小于原始高度
                        if (newHeight < preHeight) {
                            newHeight = preHeight;
                            newWidth = screenWidth;
                            //isDone=false;
                        } else {
                            if (newHeight >= screenHeight / 2) {
                                newHeight = screenHeight / 2;
                                newWidth = (int) (screenWidth + (newHeight - preHeight) * 0.5f);
                            }
//                            if (linearLayoutManager.findFirstVisibleItemPosition()!=0) {
//                                isDone = false;
//                            }else{
//                                isDone=true;
//                            }
                        }
                        layoutParams.height = newHeight;
                        layoutParams.width = newWidth;
                        mineAdapter.headViewHolder.iv.setLayoutParams(MainActivity.this.layoutParams);
                        break;
                    case MotionEvent.ACTION_UP:
                        float uY = event.getY();
                        distanceY = uY - downY;
                        newHeight = (int) (preHeight + distanceY);
                        newWidth = (int) (screenWidth + distanceY * 0.5f);
                        if (newHeight < preHeight) {
                            newHeight = preHeight;
                            newWidth = screenWidth;
                            isDone = false;
                        } else {
                            if (newHeight >= screenHeight / 2) {
                                newHeight = screenHeight / 2;
                                newWidth = (int) (screenWidth + (newHeight - preHeight) * 0.5f);
                            }
                        }
                        layoutParams.height = newHeight;
                        layoutParams.width = newWidth;
                        mineAdapter.headViewHolder.iv.setLayoutParams(MainActivity.this.layoutParams);
                        ExecutorService single = Executors.newSingleThreadExecutor();
                        single.execute(new Runnable() {
                            @Override
                            public void run() {
                                while (newHeight > preHeight) {
                                    SystemClock.sleep(5);
                                    //newHeight递减
                                    newHeight -= 10;
                                    newWidth -= 5;
                                    layoutParams.height = newHeight;
                                    layoutParams.width = newWidth;
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            mineAdapter.headViewHolder.iv.setLayoutParams(layoutParams);
                                        }
                                    });
                                }
                            }
                        });
                        break;


                }
                return false;

            }
        });

    }


}
