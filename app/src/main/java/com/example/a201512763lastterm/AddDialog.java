package com.example.a201512763lastterm;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import android.widget.Toast;

import android.content.ContentValues;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Administrator on 2017-08-07.
 */

public class AddDialog {
    String chooseColor = "yellow";
    private Context context;

    public AddDialog(Context context) {
        this.context = context;
    }

    // 호출할 다이얼로그 함수를 정의한다.
    public void callFunction() {




        //Dialog 생성, 상위 context가 반드시 필요하다.
        final Dialog dlg = new Dialog(context, android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
        //title Bar를 숨김
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //xml 파일과 match
        dlg.setContentView(R.layout.add_dialog);
        //dialog 출력
        dlg.show();

        final EditText nameEditText = (EditText) dlg.findViewById(R.id.name_EditText);
        final EditText periodEditText = (EditText) dlg.findViewById(R.id.period_EditText);
        final CheckBox onNotiBarCheckBox = (CheckBox) dlg.findViewById(R.id.on_Notification_CheckBox);
        final RadioGroup radioGroup = (RadioGroup) dlg.findViewById(R.id.color_radioGroup);

         final LinearLayout linearLayoutValue = (LinearLayout) dlg.findViewById(R.id.dialog_layout);
        final Button submit_btn = (Button) dlg.findViewById(R.id.submit_btn);
        final Button cancel_btn = (Button) dlg.findViewById(R.id.cancel_btn);




        linearLayoutValue.setBackgroundColor(Color.parseColor("#FFBC4A"));

        final RadioGroup.OnCheckedChangeListener radioGroupButtonChangeListener
                = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int i) {
                if (i == R.id.yellow_radioButton) {
                     linearLayoutValue.setBackgroundColor(Color.parseColor("#FFBC4A"));
                   chooseColor = "yellow";
                } else if (i == R.id.pink_radioButton) {
                    linearLayoutValue.setBackgroundColor(Color.parseColor("#FC98B4"));
                    chooseColor = "pink";
                } else if (i == R.id.blue_radioButton) {
                    linearLayoutValue.setBackgroundColor(Color.parseColor("#4C93FF"));
                    chooseColor = "blue";
                } else if (i == R.id.green_radioButton) {
                    linearLayoutValue.setBackgroundColor(Color.parseColor("#00B7A9"));
                    chooseColor = "green";
                }
            }
        };


        radioGroup.setOnCheckedChangeListener(radioGroupButtonChangeListener);

        submit_btn.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                if(nameEditText.getText().toString().matches("") || periodEditText.getText().toString().matches("")){
                    Toast.makeText(context, "입력하지 않은 곳이 있습니다", Toast.LENGTH_SHORT).show();
                }else{
                    Date currentTime = Calendar.getInstance().getTime();
                    String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(currentTime);

                    MainActivity.dbCount++;

                    MainActivity.itemList.add(new ItemModel(nameEditText.getText().toString(),Integer.parseInt(periodEditText.getText().toString()), onNotiBarCheckBox.callOnClick(), chooseColor, MainActivity.dbCount - 1));
                    MainActivity.listAdapter.notifyDataSetChanged();


                    ContentValues recordValues = new ContentValues();
                    recordValues.put("name", nameEditText.getText().toString());
                    recordValues.put("period", Integer.parseInt(periodEditText.getText().toString()));
                    recordValues.put("onNotification", onNotiBarCheckBox.isChecked());
                    recordValues.put("totalClearTimes", 0);
                    recordValues.put("lastly", date);
                    recordValues.put("color", chooseColor);
                    recordValues.put("checkToday1", 0);
                    recordValues.put("checkToday2", 0);
                    recordValues.put("checkToday3", 0);
                    recordValues.put("checkToday4", 0);
                    recordValues.put("checkToday5", 0);

                    MainActivity.db.insert(MainActivity.mainTableName, null, recordValues);

                    Toast.makeText(context, "습관을 길러보세요!", Toast.LENGTH_LONG).show();

                dlg.dismiss();
            }}
        });

        cancel_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dlg.dismiss(); //dialog를 닫는다
            }
        });

    }
}