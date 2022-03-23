package com.example.dogapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dogapp.R;
import com.example.dogapp.model.DogBreed;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DogAdapter extends RecyclerView.Adapter<DogAdapter.ViewHolder> {
    private List<DogBreed> dogBreedList = new ArrayList<>();
    private List<Integer> posList = new ArrayList<>();
    private final Context context;
    public DogAdapter(Context context){
        this.context = context;
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setDogBreedList(List<DogBreed> listDog){
        this.dogBreedList = listDog;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public DogAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dog, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DogAdapter.ViewHolder holder, int position) {
        holder.name.setText(dogBreedList.get(position).getName());
        holder.bredFor.setText(dogBreedList.get(position).getBredFor());
        Glide.with(context).load(dogBreedList.get(position).getUrl()).into(holder.image);

        if (!posList.contains(holder.getAdapterPosition())){
            ImageViewCompat.setImageTintList(holder.heart,
                    ColorStateList.valueOf(ContextCompat.getColor(context, R.color.grey)));
        } else {
            ImageViewCompat.setImageTintList(holder.heart,
                    ColorStateList.valueOf(ContextCompat.getColor(context, R.color.red)));
        }
        holder.heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("DEBUG1","pos: " + holder.getAdapterPosition());
                if (!posList.contains(holder.getAdapterPosition())){
                    posList.add(holder.getAdapterPosition());
                    ImageViewCompat.setImageTintList(holder.heart,
                            ColorStateList.valueOf(ContextCompat.getColor(context, R.color.red)));
                } else {
                    ImageViewCompat.setImageTintList(holder.heart,
                            ColorStateList.valueOf(ContextCompat.getColor(context, R.color.grey)));
                    posList.remove(Integer.valueOf(holder.getAdapterPosition()));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dogBreedList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView name;
        public final TextView bredFor;
        public final ImageView image;
        public final ImageView heart;
        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            name = (TextView) view.findViewById(R.id.tv_dogname);
            bredFor = (TextView) view.findViewById(R.id.tv_bredfor);
            image = (ImageView) view.findViewById(R.id.iv_dog);
            heart = (ImageView) view.findViewById(R.id.iv_heart);
        }
    }
}
