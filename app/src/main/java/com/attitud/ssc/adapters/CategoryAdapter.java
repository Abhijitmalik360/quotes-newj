package com.attitud.ssc.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.attitud.ssc.cls.CategoryData;
import com.attitud.ssc.databinding.CatListBinding;

import java.util.ArrayList;

public class CategoryAdapter extends  RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    ArrayList<CategoryData> arrayList;
    onCategoryClickListener clickListener;

    public interface onCategoryClickListener{
        void  categoryClickListener(int position);
    }

    public CategoryAdapter(ArrayList<CategoryData> arrayList, onCategoryClickListener categoryClickListener) {
        this.arrayList = arrayList;
        this.clickListener = categoryClickListener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
   CatListBinding binding = CatListBinding.inflate(LayoutInflater.from(parent.getContext()),parent, false);
   return  new CategoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
    CategoryData data = arrayList.get(position);
    if (data==null){
        return;
    }

    holder.binding.category.setText(data.getName());

    holder.binding.category.setOnClickListener(v -> {
        clickListener.categoryClickListener(position);
    });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder{

        CatListBinding binding;
        public CategoryViewHolder(@NonNull CatListBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
