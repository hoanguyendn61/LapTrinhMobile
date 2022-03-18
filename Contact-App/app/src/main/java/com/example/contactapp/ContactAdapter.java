package com.example.contactapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {
    private List<Contact> contactList;
    private List<Contact> listCopy;
    private Context context;
    public ContactAdapter(Context context){
        this.context = context;
        this.contactList = new ArrayList<>();
    }
    public void setListContact(List<Contact> itemList){
        this.contactList.clear();
        this.contactList = itemList;
        this.listCopy = new ArrayList<>();
        this.listCopy.addAll(itemList);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ContactAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactAdapter.ViewHolder holder, int position) {
        holder.getTextView().setText(contactList.get(position).getName());
    }
    public void filter(String keyword){
        this.contactList.clear();
        if(!keyword.equals("")){
            keyword = keyword.toLowerCase(Locale.ROOT);
            for (Contact item: listCopy){
                if (item.getName().toLowerCase(Locale.ROOT).contains(keyword)){
                    this.contactList.add(item);
                }
            }
        } else {
            this.contactList.addAll(listCopy);
        }
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return contactList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            textView = (TextView) view.findViewById(R.id.tv_name);
        }

        public TextView getTextView() {
            return textView;
        }
    }
}
