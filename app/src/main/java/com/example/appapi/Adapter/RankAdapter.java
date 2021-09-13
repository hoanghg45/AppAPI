package com.example.appapi.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appapi.Models.UserRank;
import com.example.appapi.R;


import java.util.List;

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.ViewHolder> {
    private List<UserRank> userRanksList;
    private Context context;


    public RankAdapter(List<UserRank> userRanksList, Context context) {
        this.userRanksList = userRanksList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from((parent.getContext()))
                .inflate(R.layout.item_rank,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtRank.setText(String.valueOf(userRanksList.get(position).getScore()));
        holder.txtName.setText(String.valueOf(userRanksList.get(position).getFullName()));
    }

    @Override
    public int getItemCount() {
        return userRanksList.size();
    }

public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txtName,txtRank;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtRank = itemView.findViewById(R.id.txtRank);
        }
    }
}
