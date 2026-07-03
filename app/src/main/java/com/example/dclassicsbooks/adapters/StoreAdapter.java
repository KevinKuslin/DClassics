package com.example.dclassicsbooks.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.progressindicator.LinearProgressIndicator;

import com.example.dclassicsbooks.R;
import com.example.dclassicsbooks.models.Store;

import java.util.ArrayList;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.StoreViewHolder> {

    private final Context context;
    private final ArrayList<Store> storeList;

    public StoreAdapter(Context context, ArrayList<Store> storeList) {
        this.context = context;
        this.storeList = storeList;
    }

    @NonNull
    @Override
    public StoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_store, parent, false);

        return new StoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreViewHolder holder, int position) {

        Store store = storeList.get(position);

        holder.imgStore.setImageResource(store.getImage());

        holder.txtStoreName.setText(store.getName());

        holder.txtLocation.setText(store.getLocation());

        holder.txtDescription.setText(store.getDescription());

        holder.txtRating.setText(
                String.format("%.1f/5", store.getRating())
        );

        holder.storeStar.setText(
                String.format("%.1f", store.getRating())
        );

        holder.ratingProgress.setMax(50);

        int progress = Math.round(store.getRating() * 10);

        holder.ratingProgress.setProgress(progress);

    }

    @Override
    public int getItemCount() {
        return storeList.size();
    }

    static class StoreViewHolder extends RecyclerView.ViewHolder {

        ImageView imgStore;

        TextView txtStoreName;
        TextView txtLocation;
        TextView txtDescription;

        TextView txtRating;
        TextView storeStar;

        LinearProgressIndicator ratingProgress;

        StoreViewHolder(@NonNull View itemView) {
            super(itemView);

            imgStore = itemView.findViewById(R.id.imgStore);

            txtStoreName = itemView.findViewById(R.id.txtStoreName);
            txtLocation = itemView.findViewById(R.id.txtLocation);
            txtDescription = itemView.findViewById(R.id.txtDescription);

            txtRating = itemView.findViewById(R.id.txtRating);
            storeStar = itemView.findViewById(R.id.storeStar);

            ratingProgress = itemView.findViewById(R.id.ratingProgress);
        }
    }
}