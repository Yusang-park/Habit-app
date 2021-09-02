package com.example.a201512763lastterm;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    protected Button addItem_Btn;
    static ArrayList<ItemModel> itemList = new ArrayList<ItemModel>();;
    static ListAdapter listAdapter;

    static SQLiteDatabase db;
    static String dbName = "db36";
    static String mainTableName = "habit36";
    static int dbCount;

    void initDB(){
        try {
            db = openOrCreateDatabase(
                    dbName,
                    Activity.MODE_PRIVATE,
                    null);


        } catch(Exception ex) {
            ex.printStackTrace();
        }

        db.execSQL("create table if not exists " + mainTableName + "("
                + " id integer PRIMARY KEY autoincrement, "
                + " name text, "
                + " period integer, "
                + " onNotification int, totalClearTimes int, lastly text, color text, checkToday1 int, checkToday2 int, checkToday3 int, checkToday4 int, checkToday5 int);" );

        String sql = "select name, period, onNotification, totalClearTimes, lastly, color, checkToday1, checkToday2, checkToday3, checkToday4, checkToday5 from "+ mainTableName;
        Cursor cursor = MainActivity.db.rawQuery(sql, null);
        dbCount = cursor.getCount();
        insertDBtoList(cursor);
    }

    void insertDBtoList(Cursor cursor){
        String name;
        int period;
        int onNotification;
        int totalClearTimes;
        String lastly;
        String color;
        Boolean[] checkToday;
        int index;

        for(int i=0; i<dbCount; i++){
            cursor.moveToNext();
            name = cursor.getString(0);
            period = cursor.getInt(1);
            onNotification = cursor.getInt(2);
            totalClearTimes = cursor.getInt(3);
            lastly = cursor.getString(4);
            color = cursor.getString(5);
            checkToday = new Boolean[]{cursor.getInt(6) > 0,cursor.getInt(7) > 0,cursor.getInt(8) > 0,cursor.getInt(9) > 0,cursor.getInt(10) > 0};
            index = i;

            itemList.add(new ItemModel(name,10, true, color, index));
        }
        cursor.close();
        listAdapter.notifyDataSetChanged();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Button addItem_Btn = (Button) findViewById(R.id.add_btn);


        addItem_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddDialog addDialog = new AddDialog(MainActivity.this);
                System.out.print("Clicked");
                addDialog.callFunction();
            }
        });
//


        //상태바 아이콘 색상 변경을 위함
        View view = getWindow().getDecorView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (view != null) {
                // 23 버전 이상일 때 상태바 회색 아이콘 색상을 설정
                view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }


        getSupportActionBar().hide(); //APPBAR를 숨긴다

        //View와 java를 연동
        RecyclerView recyclerView =  (RecyclerView) findViewById(R.id.recyclerView);

        //Vertical 형태로 움직이도록 Layout 지정
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        //adapter에 data insert
        listAdapter = new ListAdapter(itemList);
//        //view와 adapter 연결
        recyclerView.setAdapter(listAdapter);

        initDB();

}}