package com.sunlin.weextest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.sunlin.weextest.common.picker.popwindow.DatePickerPopWin;
import com.sunlin.weextest.common.picker.popwindow.SelectPickerPopWin;
import com.sunlin.weextest.common.picker.popwindow.TimePickerPopWin;

public class MainActivity extends AppCompatActivity {
    private  String url="";
    private  Boolean isCatch=true;
    private EditText editText;
    private Button button;
    private Button button2;
    private Button button3;
    private Button button4;
    private CheckBox checkBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText=(EditText) findViewById(R.id.editText);
        button=(Button) findViewById(R.id.button);
        button2=(Button) findViewById(R.id.button2);
        button3=(Button) findViewById(R.id.button3);
        button4=(Button) findViewById(R.id.button4);
        checkBox=(CheckBox) findViewById(R.id.checkBox);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerPopWin pickerPopWin = new DatePickerPopWin.Builder(MainActivity.this, new DatePickerPopWin.OnDatePickedListener() {
                    @Override
                    public void onDatePickCompleted(int year, int month, int day, String dateDesc) {
                        Toast.makeText(MainActivity.this, dateDesc, Toast.LENGTH_SHORT).show();
                    }
                }).textConfirm("确认") //text of confirm button
                        .textCancel("取消") //text of cancel button
                        .btnTextSize(16) // button text size
                        .viewTextSize(25) // pick view text size
                        .colorCancel(Color.parseColor("#999999")) //color of cancel button
                        .colorConfirm(Color.parseColor("#009900"))//color of confirm button
                        .minYear(1990) //min year in loop
                        .maxYear(2550) // max year in loop
                        .dateChose("2018-04-01") // date chose when init popwindow
                        .showDayMonthYear(false)
                        .build();
                pickerPopWin.showPopWin(MainActivity.this);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectPickerPopWin pickerPopWin = new SelectPickerPopWin.Builder(MainActivity.this, new SelectPickerPopWin.OnSelectPickedListener() {
                    @Override
                    public void onSelectPickCompleted(int index, String valueDesc) {
                        Toast.makeText(MainActivity.this, valueDesc, Toast.LENGTH_SHORT).show();
                    }
                }).textConfirm("确认") //text of confirm button
                        .textCancel("取消") //text of cancel button
                        .btnTextSize(16) // button text size
                        .viewTextSize(25) // pick view text size
                        .colorCancel(Color.parseColor("#999999")) //color of cancel button
                        .colorConfirm(Color.parseColor("#009900"))//color of confirm button
                        .valueChose("Amber") //
                        .build();
                pickerPopWin.showPopWin(MainActivity.this);
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerPopWin timePickerPopWin=new TimePickerPopWin.Builder(MainActivity.this, new TimePickerPopWin.OnTimePickListener() {
                    @Override
                    public void onTimePickCompleted(int hour, int minute, String AM_PM, String time) {
                        Toast.makeText(MainActivity.this, time, Toast.LENGTH_SHORT).show();
                    }
                }).textConfirm("确认")
                        .textCancel("取消")
                        .btnTextSize(16)
                        .viewTextSize(25)
                        .colorCancel(Color.parseColor("#999999"))
                        .colorConfirm(Color.parseColor("#009900"))
                        .build();
                timePickerPopWin.showPopWin(MainActivity.this);
            }
        });

        SharedPreferences sp = this.getSharedPreferences("WEEXTEST", Context.MODE_PRIVATE);
        url=sp.getString("url", "");
        isCatch=sp.getBoolean("isCatch",true);

        if(url.isEmpty()) {
            url = "http://127.0.0.1:8081/dist/index.weex.js";
        }

        editText.setText(url);

        // Example of a call to a native method
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url=editText.getText().toString();
                Intent intent = new Intent(MainActivity.this, WeexActivity.class);
                intent.putExtra("url",url);
                intent.putExtra("title","WEEX");
                intent.putExtra("rTitle","刷新");
                intent.putExtra("rImg","save22");

                if(checkBox.isChecked()){
                    intent.putExtra("isCatch","true");
                }else{
                    intent.putExtra("isCatch","false");
                }

                startActivity(intent);

                SharedPreferences sp = MainActivity.this.getSharedPreferences("WEEXTEST", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("url", url);
                editor.putBoolean("isCatch",checkBox.isChecked());
                editor.commit();
            }
        });
    }
}
