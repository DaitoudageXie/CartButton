package com.xyb.cartbutton;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import com.xyb.mylibrary.CartNumClickListener;
import com.xyb.mylibrary.CartNumView;

public class MainActivity extends AppCompatActivity {

    CartNumView cartNumView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cartNumView= (CartNumView) findViewById(R.id.cartNumView);

        cartNumView.setClickListener(new CartNumClickListener() {
            @Override
            public void onAdd(int count) {
                Toast.makeText(MainActivity.this,"增加"+count,Toast.LENGTH_SHORT).show();
                cartNumView.increaseCount();

            }
            @Override
            public void onDel(int count) {

                cartNumView.reduceCount();
                //
                Toast.makeText(MainActivity.this,"减少"+count,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onEmety() {
                Toast.makeText(MainActivity.this,"删除成功",Toast.LENGTH_SHORT).show();

            }
        });
    }
}
