package com.example.universalyogaadmin.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.universalyogaadmin.R;
import com.example.universalyogaadmin.activity.CourseDetailActivity;
import com.example.universalyogaadmin.model.YogaClass;

import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ClassViewHolder> {
    private Context context;
    private ArrayList<YogaClass> yogaClasses;

    public CourseAdapter(Context context, ArrayList<YogaClass> yogaClasses) {
        this.context = context;
        this.yogaClasses = yogaClasses;
    }

    public void updateView( ArrayList<YogaClass> yogaClasses) {
        Log.i("DATA", yogaClasses.size() + "size");
        this.yogaClasses = yogaClasses;
        notifyDataSetChanged();
    }

    @Override
    public ClassViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the layout for each row
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_class, parent, false);
        return new ClassViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ClassViewHolder holder, int position) {
        YogaClass yogaClass = yogaClasses.get(position);

        // Bind data to the view holder
        holder.textViewDay.setText(yogaClass.getDay());
        holder.textViewTime.setText(yogaClass.getTime());
        holder.textViewCapacity.setText("Capacity: " + yogaClass.getCapacity());
        holder.textViewDuration.setText("Duration: " + yogaClass.getDuration() + " mins");
        holder.textViewPrice.setText("Price: £" + yogaClass.getPrice());
        holder.textViewType.setText(yogaClass.getType());
        holder.textViewDescription.setText(yogaClass.getDescription() != null ? yogaClass.getDescription() : "No description");

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, CourseDetailActivity.class);
            intent.putExtra("yoga_class_id", yogaClass.getId());
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return yogaClasses.size();
    }

    // ViewHolder class to hold the view for each item
    public static class ClassViewHolder extends RecyclerView.ViewHolder {
        TextView textViewDay, textViewTime, textViewCapacity, textViewDuration, textViewPrice, textViewType, textViewDescription;

        public ClassViewHolder(View itemView) {
            super(itemView);
            textViewDay = itemView.findViewById(R.id.textViewDay);
            textViewTime = itemView.findViewById(R.id.textViewTime);
            textViewCapacity = itemView.findViewById(R.id.textViewCapacity);
            textViewDuration = itemView.findViewById(R.id.textViewDuration);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            textViewType = itemView.findViewById(R.id.textViewType);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
        }
    }

    // Optional: Method to update the list if the data changes
    public void updateData(ArrayList<YogaClass> newYogaClasses) {
        yogaClasses.clear();
        yogaClasses.addAll(newYogaClasses);
        notifyDataSetChanged();
    }
}