package com.example.dogapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dogapp.R;
import com.example.dogapp.model.DogBreed;
import com.apachat.swipereveallayout.core.SwipeLayout;
import com.apachat.swipereveallayout.core.ViewBinder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DogsAdapter extends RecyclerView.Adapter<DogsAdapter.ViewHolder> {
    private List<DogBreed> dogBreedList = new ArrayList<>();
    private List<DogBreed> listCopy;
    private List<String> posList = new ArrayList<>();
    private final ViewBinder binderHelper = new ViewBinder();
    private final Context context;
    public interface OnItemClickListener {
        void onItemClick(DogBreed item);
    }
    private final OnItemClickListener listener;
    public DogsAdapter(Context context, OnItemClickListener listener){
        this.context = context;
        this.listener = listener;
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setDogBreedList(List<DogBreed> listDog){
        this.dogBreedList.clear();
        this.dogBreedList = listDog;
        this.listCopy = new ArrayList<>();
        this.listCopy.addAll(listDog);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public DogsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dog, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DogsAdapter.ViewHolder holder, int position) {

        holder.bind(dogBreedList.get(position), context, listener);
        String dogId = (dogBreedList.get(position).getId());

        binderHelper.bind(holder.swipeLayout, dogId);
//        heart tap
        if (!posList.contains(dogId)){
            ImageViewCompat.setImageTintList(holder.heart,
                    ColorStateList.valueOf(ContextCompat.getColor(context, R.color.grey)));
        } else {
            ImageViewCompat.setImageTintList(holder.heart,
                    ColorStateList.valueOf(ContextCompat.getColor(context, R.color.red)));
        }
        holder.heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("DEBUG1","dogId: " + dogId);
                if (!posList.contains(dogId)){
                    posList.add(dogId);
                    ImageViewCompat.setImageTintList(holder.heart,
                            ColorStateList.valueOf(ContextCompat.getColor(context, R.color.red)));
                } else {
                    ImageViewCompat.setImageTintList(holder.heart,
                            ColorStateList.valueOf(ContextCompat.getColor(context, R.color.grey)));
                    posList.remove(dogId);
                }
            }
        });
    }
    public void filter(String keyword){
        this.dogBreedList.clear();
        if(!keyword.equals("")){
            keyword = keyword.toLowerCase(Locale.ROOT);
            for (DogBreed item: listCopy){
                String name = item.getName();
                if (name.toLowerCase(Locale.ROOT).contains(keyword)){
                    this.dogBreedList.add(item);
                }
            }
        } else {
            this.dogBreedList.addAll(listCopy);
        }
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return dogBreedList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public SwipeLayout swipeLayout;
        private View detailLayout;
        public final TextView name;
        public final TextView name_back;
        public final TextView origin;
        public final TextView lifespan;
        public final TextView temperament;
        public final TextView bredFor;
        public final ImageView image;
        public final ImageView heart;
        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            swipeLayout = (SwipeLayout) view.findViewById(R.id.swipeLayout);
            detailLayout = view.findViewById(R.id.detailLayout);
            name = (TextView) view.findViewById(R.id.tv_dogname);
            bredFor = (TextView) view.findViewById(R.id.tv_bredfor);
            image = (ImageView) view.findViewById(R.id.iv_dog);
            heart = (ImageView) view.findViewById(R.id.iv_heart);
            name_back = (TextView) view.findViewById(R.id.tv_dogname_2);
            origin = (TextView) view.findViewById(R.id.tv_origin);
            lifespan = (TextView) view.findViewById(R.id.tv_lifespan);
            temperament = (TextView) view.findViewById(R.id.tv_temp);
        }
        public void bind(final DogBreed item, final Context context, final OnItemClickListener listener){
            name.setText(item.getName());
            name_back.setText(item.getName());
            origin.setText(item.getOrigin());
            lifespan.setText(item.getLifeSpan());
            temperament.setText(item.getTemperament());
            bredFor.setText(item.getBredFor());
            Glide.with(context).load(item.getUrl()).into(image);
            detailLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}
