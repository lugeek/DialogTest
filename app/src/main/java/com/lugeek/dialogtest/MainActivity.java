package com.lugeek.dialogtest;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    TextView textView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.tv1);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SimpleListDialog.Builder(MainActivity.this)
                        .title("title")
                        .items(new String[] {"男", "女"})
                        .show();
            }
        });

        textView2 = (TextView) findViewById(R.id.tv2);
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(MainActivity.this);
                View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.simple_list_dialog, null);
                ((TextView)view.findViewById(R.id.tv_title)).setText("title");

                dialog.setContentView(view);
                dialog.show();
            }
        });
    }
}
