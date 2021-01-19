package com.example.calculatorcalorii;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewAdapter> {
    List<Day> days;
    private Context context;

    public HistoryAdapter(Context context, List<Day> d){
        this.context = context;
        this.days = d;
    }

    @Override
    public HistoryAdapter.MyViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item_layout, null);
       HistoryAdapter.MyViewAdapter vh = new HistoryAdapter.MyViewAdapter(v);

       return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.MyViewAdapter holder, int position) {
        if(days.isEmpty())
            return;

        long id = days.get(position).getId();
        String date = days.get(position).getDate();
        int steps = days.get(position).getSteps();
        float weight = days.get(position).getWeight();
        float calories = days.get(position).getCalories();

        holder.date.setText(date);
        holder.steps.setText(steps + "");
        holder.weight.setText(weight + "");
        holder.calories.setText(calories + "");
        holder.itemView.setTag(id);
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    public static class MyViewAdapter extends RecyclerView.ViewHolder {
        public TextView date;
        public TextView steps;
        public TextView weight;
        public TextView calories;

        public MyViewAdapter(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.dateView);
            steps = itemView.findViewById(R.id.stepsView);
            weight = itemView.findViewById(R.id.greutateView);
            calories = itemView.findViewById(R.id.caloriiView);
        }
    }
}
