package me.iamcrk.medinfo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.ViewHolder> {

    private List<Medicine> medicines;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Medicine medicine);
    }

    public MedicineAdapter(List<Medicine> medicines, OnItemClickListener listener) {
        this.medicines = medicines;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Medicine med = medicines.get(position);
        holder.textView.setText(med.getName());
        holder.itemView.setOnClickListener(v -> listener.onItemClick(med));
    }

    @Override
    public int getItemCount() {
        return medicines.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
        }
    }
}
