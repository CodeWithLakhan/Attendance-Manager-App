package com.example.attendencemaganer;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;

public class CustomBaseAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> subnames;
    LayoutInflater inflater;
    float[] percent;
    DBHelper dbHelper ;
    int[] youCanBunk;



    public CustomBaseAdapter(Context applicationContext, ArrayList<String> s1, float[] percent,int[] youCanBunk) {
        this.context = applicationContext;
        this.subnames = s1;
        this.inflater = LayoutInflater.from(context);
        this.percent = percent;
        this.dbHelper = new DBHelper(applicationContext);
        this.youCanBunk = youCanBunk;
    }

    @Override
    public int getCount() {
        return subnames.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = inflater.inflate(R.layout.subjects_view,null);
        TextView subnamesTextView = view.findViewById(R.id.sub);
        TextView percentTextView = view.findViewById(R.id.percent);
        Button bt_attend = view.findViewById(R.id.bt_attend);
        Button bt_bunk = view.findViewById(R.id.bt_bunk);
        Button del=view.findViewById(R.id.del);
        TextView youCanBunkTextView = view.findViewById(R.id.youCanBunk);

        percentTextView.setText(String.format("%.2f%%", this.percent[i]));
        if(this.percent[i]<80)
        {
            percentTextView.setTextColor(Color.RED);
        }
        else
        {
            percentTextView.setTextColor(Color.GREEN);
        }
        subnamesTextView.setText(this.subnames.get(i));
        youCanBunkTextView.setText(String.format(String.valueOf("You can Still Bunk "+this.youCanBunk[i])));


        ArrayList<String> x = this.subnames;
        bt_attend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "You have attended "+x.get(i), Toast.LENGTH_SHORT).show();
                int n = dbHelper.incrementAttendHelper(x.get(i));
                Toast.makeText(context, "Updated attended hours: " + n, Toast.LENGTH_SHORT).show();

                int attended = dbHelper.getAttendedHelper().get(i);
                int conducted = dbHelper.getConductedHelper().get(i);
                percent[i] = ((float)attended / conducted) * 100;
                if(percent[i]<80)
                {
                    percentTextView.setTextColor(Color.RED);
                }
                else
                {
                    percentTextView.setTextColor(Color.GREEN);
                }

                percentTextView.setText(String.format("%.2f%%", percent[i]));
                notifyDataSetChanged();
            }
        });

        bt_bunk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int n = dbHelper.incrementBunkHelper(x.get(i));
                Toast.makeText(context, "Updated Bunked Hours of "+x.get(i)+"is "+n, Toast.LENGTH_SHORT).show();
                int attended = dbHelper.getAttendedHelper().get(i);
                int conducted = dbHelper.getConductedHelper().get(i);
                int totalHours = dbHelper.getTotalHoursHelper().get(i);
                youCanBunk[i] = (totalHours*20/100) - (conducted - attended);
                percent[i] = ((float) attended / conducted) * 100;
                if(percent[i]<80)
                {
                    percentTextView.setTextColor(Color.RED);
                }
                else
                {
                    percentTextView.setTextColor(Color.GREEN);
                }
                percentTextView.setText(String.format("%.2f%%", percent[i]));
                youCanBunkTextView.setText(Float.toString(youCanBunk[i]));
                notifyDataSetChanged();
            }
        });

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dbHelper.delSubjectHelper(x.get(i));
                x.remove(i);
                notifyDataSetChanged();
            }
        });
        return view;

    }
}
