package com.example.appapi.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appapi.Game;
import com.example.appapi.Models.Game1;
import com.example.appapi.Models.Math1;
import com.example.appapi.Models.Trolls;
import com.example.appapi.R;

import java.util.ArrayList;
import java.util.List;

public class TrollAdapter extends RecyclerView.Adapter<MathAdapter.ViewHolder> {


    private Game1 game1;
    public List<Math1> list =new ArrayList<>();
    private Context context;
    private int score =0;

    public TrollAdapter(Game1 game1, Context context) {
        this.game1 = game1;
        this.context = context;
    }

    @NonNull
    @Override
    public MathAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from((parent.getContext()))
                .inflate(R.layout.item_quiz,parent,false);

        return new MathAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MathAdapter.ViewHolder holder, int position) {
        addList();
        holder.txtQs.setText("Câu hỏi "+(position+1)+": "+list.get(position).getQuestion());
        holder.rbA.setText(String.valueOf(list.get(position).getOptions().get(0)));
        holder.rbB.setText(String.valueOf(list.get(position).getOptions().get(1)));
        holder.rbC.setText(String.valueOf(list.get(position).getOptions().get(2)));
        holder.rbD.setText(String.valueOf(list.get(position).getOptions().get(3)));
        savePrefs1(score,"score1");
        String answer = list.get(position).getAnswer();
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
        return 4;
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
    public void addList(){
        List<String> ol = new ArrayList<>();
        ol.add(game1.getQuiz().getTrolls().getQ1().getOptions().get(0));
        ol.add(game1.getQuiz().getTrolls().getQ1().getOptions().get(1));
        ol.add(game1.getQuiz().getTrolls().getQ1().getOptions().get(2));
        ol.add(game1.getQuiz().getTrolls().getQ1().getOptions().get(3));
        Math1 data = new Math1(game1.getQuiz().getTrolls().getQ1().getQuestion(),ol,game1.getQuiz().getTrolls().getQ1().getAnswer());
        list.add(data);
        List<String> ol2 = new ArrayList<>();
        ol2.add(game1.getQuiz().getTrolls().getQ2().getOptions().get(0));
        ol2.add(game1.getQuiz().getTrolls().getQ2().getOptions().get(1));
        ol2.add(game1.getQuiz().getTrolls().getQ2().getOptions().get(2));
        ol2.add(game1.getQuiz().getTrolls().getQ2().getOptions().get(3));
        Math1 data2 = new Math1(game1.getQuiz().getTrolls().getQ2().getQuestion(),ol2,game1.getQuiz().getTrolls().getQ2().getAnswer());
        list.add(data2);
        List<String> ol3 = new ArrayList<>();
        ol3.add(game1.getQuiz().getTrolls().getQ3().getOptions().get(0));
        ol3.add(game1.getQuiz().getTrolls().getQ3().getOptions().get(1));
        ol3.add(game1.getQuiz().getTrolls().getQ3().getOptions().get(2));
        ol3.add(game1.getQuiz().getTrolls().getQ3().getOptions().get(3));
        Math1 data3 = new Math1(game1.getQuiz().getTrolls().getQ3().getQuestion(),ol3,game1.getQuiz().getTrolls().getQ3().getAnswer());
        list.add(data3);
        List<String> ol4 = new ArrayList<>();
        ol4.add(game1.getQuiz().getTrolls().getQ4().getOptions().get(0));
        ol4.add(game1.getQuiz().getTrolls().getQ4().getOptions().get(1));
        ol4.add(game1.getQuiz().getTrolls().getQ4().getOptions().get(2));
        ol4.add(game1.getQuiz().getTrolls().getQ4().getOptions().get(3));
        Math1 data4 = new Math1(game1.getQuiz().getTrolls().getQ4().getQuestion(),ol4,game1.getQuiz().getTrolls().getQ4().getAnswer());
        list.add(data4);
    }
    public void checkRb(RadioButton rb,String ans){
        SharedPreferences preferences = context.getSharedPreferences("data",0);
        if (rb.getText().equals(ans)){
            score = preferences.getInt("score1",0)+1;
        }
        else{
            score = preferences.getInt("score1",0);
        }
        savePrefs1(score,"score1");
    }

    protected void savePrefs1(int content,String name){
        SharedPreferences preferences = context.getSharedPreferences("data",0);
        SharedPreferences.Editor editor= preferences.edit();
        editor.putInt(name,content);
        editor.commit();
    }
}
