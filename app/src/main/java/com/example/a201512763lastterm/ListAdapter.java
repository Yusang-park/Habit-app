package com.example.a201512763lastterm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;


import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>{

    private ArrayList<ItemModel> data = null;
    private String itemRecordTableName;
    //데이터를 관리하는 ViewHolder
    class ViewHolder extends RecyclerView.ViewHolder{

        protected TextView percent_TextView;
        protected TextView title_TextView;
        protected CheckBox checkBox1;
        protected CheckBox checkBox2;
        protected CheckBox checkBox3;
        protected CheckBox checkBox4;
        protected CheckBox checkBox5;
        protected ImageView delete_Btn;
        protected View background_View;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.title_TextView = itemView.findViewById(R.id.title_TextView);
            this.delete_Btn = itemView.findViewById(R.id.delete_Btn);
            this.checkBox1 = itemView.findViewById(R.id.checkBox);
            this.checkBox2 = itemView.findViewById(R.id.checkBox2);
            this.checkBox3 = itemView.findViewById(R.id.checkBox3);
            this.checkBox4 = itemView.findViewById(R.id.checkBox4);
            this.checkBox5 = itemView.findViewById(R.id.checkBox5);
            this.percent_TextView = itemView.findViewById(R.id.percent_TextView);
            this.background_View = itemView.findViewById(R.id.view);



        }
    }

    //생성자
    ListAdapter(ArrayList<ItemModel> list){
        data = list;
    }

    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.list_item, parent, false);
        ListAdapter.ViewHolder vh = new ListAdapter.ViewHolder(view);

        return vh; //return 값은 반드시 viewholder여야한다.
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {





        itemRecordTableName = "RecordTable" + Integer.toString(data.get(position).getIndex());
        MainActivity.db.execSQL("create table if not exists " + itemRecordTableName + "("
                + " _id integer PRIMARY KEY autoincrement, "
                + " date text, "
                + " time text, checkBoxNum int);" );

        String sql = "select date, time from "+ itemRecordTableName;
        final Cursor cursor = MainActivity.db.rawQuery(sql, null);


        holder.delete_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.checkBox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int isChecked = holder.checkBox1.isChecked() == true ? 1 : 0;
                String sqlUpdate = "UPDATE "+ MainActivity.mainTableName + " SET checkToday1="+ isChecked +" WHERE id=" + Integer.toString(data.get(position).getIndex()+1);

                MainActivity.db.execSQL(sqlUpdate) ;




                Date currentTime = Calendar.getInstance().getTime();
                String date = new SimpleDateFormat("yyyy년 MM월 dd일 EE요일", Locale.getDefault()).format(currentTime);
                String time = new SimpleDateFormat("hh시 mm분", Locale.getDefault()).format(currentTime);
            }
        });
        holder.checkBox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.checkBox3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.checkBox4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.checkBox5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });


        //data를 view와 연결한다
        holder.title_TextView.setText(data.get(position).getName());
        holder.percent_TextView.setText(Double.toString(data.get(position).getPercent()) +  "%");

        switch (data.get(position).color){
            case "yellow" :
                holder.background_View.setBackgroundResource(R.drawable.item_yellow_background);
                break;
            case "blue" :
                holder.background_View.setBackgroundResource(R.drawable.item_blue_background);
                break;
            case "green" :
                holder.background_View.setBackgroundResource(R.drawable.item_green_background);
                break;
            case "pink" :
                holder.background_View.setBackgroundResource(R.drawable.item_pink_background);
                break;
            default:
                break;
        }



    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}