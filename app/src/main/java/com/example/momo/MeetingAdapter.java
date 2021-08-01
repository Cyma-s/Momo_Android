package com.example.momo;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MeetingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    // 모임 리사이클러뷰 어댑터
    private ArrayList<Meeting> meetings;

    public MeetingAdapter(ArrayList<Meeting> meetings) {
        this.meetings = meetings;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meeting_recyclerview_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        addItem((ItemViewHolder) holder, position);
    }

    @Override
    public int getItemCount() {
        return meetings == null ? 0 : meetings.size();
    }

    private void addItem(ItemViewHolder holder, int position) {
        Meeting meeting = meetings.get(position);
        holder.setItem(meeting);
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView meetingDay, meetingName, meetingdday;
        ImageButton noticeButton;
        ImageView meetingKind;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            meetingDay = (TextView) itemView.findViewById(R.id.meetingday);
            meetingName = (TextView) itemView.findViewById(R.id.meetingname);
            meetingdday = (TextView) itemView.findViewById(R.id.meetingdday);
            meetingKind = (ImageView) itemView.findViewById(R.id.meetingkind);
            noticeButton = (ImageButton) itemView.findViewById(R.id.noticeButton);

        }

        public void setItem(Meeting meeting) {
            String dday = "D - " + meeting.getdDay();
            meetingdday.setText(dday);
            if(meeting.getdDay() <= 7) {
                meetingdday.setTextColor(Color.parseColor("#FFD91F"));
            }
            setMeetingImage(meeting.getMeetingType(), meetingKind);
            meetingName.setText(meeting.getMeetingName());
            meetingDay.setText(meeting.getMeetingDay());
        }

        private void setMeetingImage(int i, ImageView imageView) {
            if (i == 1) {
                imageView.setImageResource(R.drawable.studyicon);
            }
        }
    }

}
