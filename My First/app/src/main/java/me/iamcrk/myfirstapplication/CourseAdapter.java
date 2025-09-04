package me.iamcrk.myfirstapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder>{

    private List<Course> courseList;

    public CourseAdapter(List<Course> courseList) {
        this.courseList = courseList;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_row, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Course course = courseList.get(position);
        holder.slNoTextView.setText(String.valueOf(position + 1));
        holder.courseNameTextView.setText(course.getCourseName());
        holder.courseHoursTextView.setText(String.valueOf(course.getCourseHours()));
        holder.durationTextView.setText(String.valueOf(course.getDurationMonths()));
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public static class CourseViewHolder extends RecyclerView.ViewHolder {
        TextView slNoTextView, courseNameTextView, courseHoursTextView, durationTextView;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            slNoTextView = itemView.findViewById(R.id.slNoTextView);
            courseNameTextView = itemView.findViewById(R.id.courseNameTextView);
            courseHoursTextView = itemView.findViewById(R.id.courseHoursTextView);
            durationTextView = itemView.findViewById(R.id.durationTextView);
        }
    }
}
