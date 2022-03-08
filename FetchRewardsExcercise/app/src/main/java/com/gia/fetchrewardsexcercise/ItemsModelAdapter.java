package com.gia.fetchrewardsexcercise;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.MessageFormat;
import java.util.ArrayList;

public class ItemsModelAdapter extends RecyclerView.Adapter<ItemsModelAdapter.ViewHolder> {
    private ArrayList<ItemsModel> itemsModelList;
    private Context context;

    public ItemsModelAdapter(ArrayList<ItemsModel> itemsModelList, Context context) {
        this.itemsModelList = itemsModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemsModelAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsModelAdapter.ViewHolder holder, int position) {
        ItemsModel itemsModel = itemsModelList.get(position);
        holder.idTextView.setText(MessageFormat.format("Name:  {0}\nID:  {1}\nListId:  {2}",
                itemsModel.getName(), itemsModel.getId(), itemsModel.getListId()));

    }

    @Override
    public int getItemCount() {
        return itemsModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView idTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            idTextView = itemView.findViewById(R.id.itemIdTV);
        }
    }
}
