package com.example.dclassicsbooks.adapters;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dclassicsbooks.R;

import java.util.ArrayList;
import java.util.List;

public class StoreCatalogAdapter extends RecyclerView.Adapter<StoreCatalogAdapter.StoreCatalogViewHolder> {
    private final List<StoreListing> visibleStores = new ArrayList<>();

    public StoreCatalogAdapter(List<StoreListing> stores) { visibleStores.addAll(stores); }
    public void submitList(List<StoreListing> stores) { visibleStores.clear(); visibleStores.addAll(stores); notifyDataSetChanged(); }

    @NonNull @Override public StoreCatalogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StoreCatalogViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_store_catalog, parent, false));
    }
    @Override public void onBindViewHolder(@NonNull StoreCatalogViewHolder h, int position) {
        StoreListing store = visibleStores.get(position);
        h.image.setImageResource(store.image);
        h.name.setText(store.name);
        h.rating.setText("★ " + store.rating + " (" + store.reviews + " reviews)");
        h.location.setText(store.location);
        h.description.setText(store.description);
        h.meta.setText(store.priceRange + "   • " + store.distance + " away • " + store.openStatus);
        bindTag(h.tagOne, store.tagOne, store.tagOneColor);
        bindTag(h.tagTwo, store.tagTwo, store.tagTwoColor);
    }
    private void bindTag(TextView textView, String label, String color) {
        textView.setText(label);
        GradientDrawable background = new GradientDrawable();
        background.setColor(Color.parseColor(color));
        background.setCornerRadius(12 * textView.getResources().getDisplayMetrics().density);
        textView.setBackground(background);
    }
    @Override public int getItemCount() { return visibleStores.size(); }

    static class StoreCatalogViewHolder extends RecyclerView.ViewHolder {
        ImageView image; TextView name, rating, tagOne, tagTwo, location, description, meta;
        StoreCatalogViewHolder(@NonNull View v) { super(v); image=v.findViewById(R.id.storeCatalogImage); name=v.findViewById(R.id.storeCatalogName); rating=v.findViewById(R.id.storeCatalogRating); tagOne=v.findViewById(R.id.storeCatalogTagOne); tagTwo=v.findViewById(R.id.storeCatalogTagTwo); location=v.findViewById(R.id.storeCatalogLocation); description=v.findViewById(R.id.storeCatalogDescription); meta=v.findViewById(R.id.storeCatalogMeta); }
    }
    public static class StoreListing {
        public final String name, rating, reviews, location, description, priceRange, distance, openStatus, tagOne, tagTwo, tagOneColor, tagTwoColor; public final int image;
        public StoreListing(String name, String rating, String reviews, String location, String description, String priceRange, String distance, String openStatus, String tagOne, String tagTwo, String tagOneColor, String tagTwoColor, int image) { this.name=name; this.rating=rating; this.reviews=reviews; this.location=location; this.description=description; this.priceRange=priceRange; this.distance=distance; this.openStatus=openStatus; this.tagOne=tagOne; this.tagTwo=tagTwo; this.tagOneColor=tagOneColor; this.tagTwoColor=tagTwoColor; this.image=image; }
    }
}
