package com.example.dclassicsbooks.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dclassicsbooks.R;
import com.example.dclassicsbooks.adapters.StoreCatalogAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class StoreActivity extends AppCompatActivity {
    private final List<StoreCatalogAdapter.StoreListing> allStores = new ArrayList<>();
    private StoreCatalogAdapter adapter;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        populateStores();
        DrawerLayout drawer = findViewById(R.id.storeDrawer);
        RecyclerView recyclerView = findViewById(R.id.rvStores);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new StoreCatalogAdapter(allStores);
        recyclerView.setAdapter(adapter);
        findViewById(R.id.btnMenu).setOnClickListener(v -> drawer.openDrawer(GravityCompat.START));
        setupNavigation(drawer);
        EditText search = findViewById(R.id.etSearchStores);
        search.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            public void onTextChanged(CharSequence s, int start, int before, int count) { filter(s.toString()); }
            public void afterTextChanged(Editable s) { }
        });
    }
    private void populateStores() {
        allStores.add(new StoreCatalogAdapter.StoreListing("Popular Book Store", "4.3/5", "120", "East Jakarta, Indonesia", "Large collection of popular books.", "$$", "1.2 km", "Open Now", "Bestseller", "Affordable", "#214C70", "#3E7C66", R.drawable.dclassics_store1_periplus));
        allStores.add(new StoreCatalogAdapter.StoreListing("Books & Beyond", "4.6/5", "78", "West Jakarta, Indonesia", "Perfect for academic learners.", "$$$", "9.5 km", "Open Now", "Academic", "Fiction", "#CC5500", "#9F0000", R.drawable.dclassics_store4_crossword));
        allStores.add(new StoreCatalogAdapter.StoreListing("Gunung Agung", "4.5/5", "137", "Central Depok, Indonesia", "Diverse collections of books.", "$$", "13.2 km", "Closed", "General", "Bestseller", "#4A0979", "#214C70", R.drawable.dclassics_store5_gramedia));
        allStores.add(new StoreCatalogAdapter.StoreListing("Kinokuniya Store", "4.5/5", "137", "Kuala Lumpur, Malaysia", "Broad child-friendly collections.", "$", "212 km", "Open Now", "Affordable", "Kids", "#3E7C66", "#C49A00", R.drawable.dclassics_store2_kino));
    }
    private void filter(String query) {
        String keyword = query.trim().toLowerCase(Locale.getDefault());
        List<StoreCatalogAdapter.StoreListing> filtered = new ArrayList<>();
        for (StoreCatalogAdapter.StoreListing store : allStores) if (store.name.toLowerCase(Locale.getDefault()).contains(keyword)) filtered.add(store);
        adapter.submitList(filtered);
    }
    private void setupNavigation(DrawerLayout drawer) {
        findViewById(R.id.navHome).setOnClickListener(v -> { startActivity(new Intent(this, HomeActivity.class)); finish(); });
        findViewById(R.id.navBooks).setOnClickListener(v -> { startActivity(new Intent(this, BooksActivity.class)); finish(); });
        findViewById(R.id.navStore).setOnClickListener(v -> drawer.closeDrawer(GravityCompat.START));
        findViewById(R.id.navLogout).setOnClickListener(v -> { Intent i = new Intent(this, LoginActivity.class); i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); startActivity(i); });
    }
}
