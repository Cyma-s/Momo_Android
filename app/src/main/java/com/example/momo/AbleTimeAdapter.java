package com.example.momo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AbleTimeAdapter extends RecyclerView.Adapter<AbleTimeAdapter.DateViewHolder> {
    ArrayList<Date> list = new ArrayList<>();
    @NonNull
    @Override
    public DateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meeting_recyclerview_item, parent, false);
        return null;
    }

    public AbleTimeAdapter(ArrayList<Date> list) {
        this.list = list;
    }

    @Override
    public void onBindViewHolder(@NonNull DateViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class DateViewHolder extends RecyclerView.ViewHolder {
        TextView year, month, day, weekDay;
        public DateViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
