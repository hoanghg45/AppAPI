package com.example.appapi.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appapi.Models.Math1;
import com.example.appapi.R;

import java.util.List;

public class MathAdapter extends RecyclerView.Adapter<MathAdapter.ViewHolder> {
    private List<Math1> mathList;
    private Context context;
    private int score = 0;

    public MathAdapter(List<Math1> mathList, Context context) {
        this.mathList = mathList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from((parent.getContext()))
                .inflate(R.layout.item_quiz,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtQs.setText("Câu hỏi "+(position+1)+": "+mathList.get(position).getQuestion());
        holder.rbA.setText(String.valueOf(mathList.get(position).getOptions().get(0)));
        holder.rbB.setText(String.valueOf(mathList.get(position).getOptions().get(1)));
        holder.rbC.setText(String.valueOf(mathList.get(position).getOptions().get(2)));
        holder.rbD.setText(String.valueOf(mathList.get(position).getOptions().get(3)));
        savePrefs1(score,"score");
        String answer = mathList.get(position).getAnswer();
        holder.rbA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkRb (holder.rbA,answer);
            }
        });
        holder.rbB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkRb (holder.rbB,answer);
            }
        });
        holder.rbC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkRb (holder.rbC,answer);
            }
        });
        holder.rbD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkRb (holder.rbD,answer);
            }
        });





    }

    @Override
    public int getItemCount() {
        return mathList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txtQs;
        public RadioButton rbA,rbB,rbC,rbD;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtQs = itemView.findViewById(R.id.txtQs);
            rbA = itemView.findViewById(R.id.rbA);
            rbB = itemView.findViewById(R.id.rbB);
            rbC = itemView.findViewById(R.id.rbC);
            rbD = itemView.findViewById(R.id.rbD);


        }
    }
    public void checkRb(RadioButton rb,String ans){
        SharedPreferences preferences = context.getSharedPreferences("data",0);
        if (rb.getText().equals(ans)){
            score = preferences.getInt("score",0)+1;
        }
        else{
            score = preferences.getInt("score",0);
        }
        savePrefs1(score,"score");
    }

    protected void savePrefs1(int content,String name){
        SharedPreferences preferences = context.getSharedPreferences("data",0);
        SharedPreferences.Editor editor= preferences.edit();
        editor.putInt(name,content);
        editor.commit();
    }
}
