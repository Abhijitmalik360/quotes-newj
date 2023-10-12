package com.attitud.ssc.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.attitud.ssc.R;
import com.attitud.ssc.cls.QuoteData;
import com.attitud.ssc.databinding.QuotesListBinding;

import java.util.ArrayList;

public class QuoteAdapter extends RecyclerView.Adapter<QuoteAdapter.QuoteViewHolder> {

private ArrayList<QuoteData> arrayList;

private OnButtonsClickListener clickListener;

    public QuoteAdapter(ArrayList<QuoteData> arrayList, OnButtonsClickListener clickListener) {
        this.arrayList = arrayList;
        this.clickListener = clickListener;
    }

    public interface OnButtonsClickListener{
        void  onDownloadClick(int position,View view);
        void  onShareClick(int position,View view);
        void  onFavoriteClick(int position);
        void  onWallpaperClick(int position,View view);

        void  onCopyClick(int position,View view);

        void onbackimagelistClick(int position, View view);
    }

    @NonNull
    @Override
    public QuoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        QuotesListBinding binding = QuotesListBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return  new QuoteViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull QuoteViewHolder holder, int position) {
       QuoteData data = arrayList.get(position);

       if (data==null){
           return;
       }
       holder.binding.quote.setText(data.getQuote());
       String author = "|| "+data.getAuthor() + " ||";
       holder.binding.author.setText(author);



       if (data.isFavorite()){
           // show red heart
           holder.binding.favorite.setImageResource(R.drawable.ic_red_heart);
       }else {
           // show white heart
           holder.binding.favorite.setImageResource(R.drawable.ic_white_heart);
       }
       holder.binding.download.setOnClickListener(v -> {
 clickListener.onDownloadClick(position,holder.binding.container);
       });



       holder.binding.share.setOnClickListener(v -> {
           clickListener.onShareClick(position,holder.binding.container);
       });
       holder.binding.wallpaper.setOnClickListener(v -> {
           clickListener.onWallpaperClick(position,holder.binding.container);
       });
       holder.binding.favorite.setOnClickListener(v -> {
           clickListener.onFavoriteClick(position);
       });
        holder.binding.copy.setOnClickListener(v -> {
            clickListener.onCopyClick(position,holder.binding.container);
        });

        holder.binding.backimagelist.setOnClickListener(v -> {
            clickListener.onbackimagelistClick(position,holder.binding.container);
        });
        

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class QuoteViewHolder extends RecyclerView.ViewHolder{
     QuotesListBinding binding;
        public QuoteViewHolder(@NonNull QuotesListBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
