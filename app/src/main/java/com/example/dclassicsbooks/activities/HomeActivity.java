package com.example.dclassicsbooks.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.dclassicsbooks.R;
import com.example.dclassicsbooks.adapters.BookAdapter;
import com.example.dclassicsbooks.models.Book;
import com.example.dclassicsbooks.utils.ToastHelper;
import com.example.dclassicsbooks.utils.UserSession;

import java.util.ArrayList;

import android.os.Handler;
import android.os.Looper;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.viewpager2.widget.ViewPager2;

import com.example.dclassicsbooks.adapters.StoreAdapter;
import com.example.dclassicsbooks.models.Store;
import com.example.dclassicsbooks.transformer.CarouselTransformer;

public class HomeActivity extends AppCompatActivity {
    private ViewPager2 storePager;

    private ImageButton btnPreviousStore;
    private ImageButton btnNextStore;

    private StoreAdapter storeAdapter;

    private ArrayList<Store> storeList;

    private ImageView[] dots;

    private final Handler sliderHandler = new Handler(Looper.getMainLooper());

    private final Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            if (storePager != null && storePager.getAdapter() != null) {
                int nextItem = (storePager.getCurrentItem() + 1)
                        % storePager.getAdapter().getItemCount();

                storePager.setCurrentItem(nextItem, true);
                sliderHandler.postDelayed(this, 5000);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        UserSession.applyUsername(this);
        setupNavigationDrawer();
        findViewById(R.id.viewAllBooks).setOnClickListener(v ->
                startActivity(new Intent(this, BooksActivity.class)));

        String message = getIntent().getStringExtra("toast_message");
        if (message != null) {
            ToastHelper.show(
                    this,
                    message,
                    R.drawable.ic_success
            );
        }

        ArrayList<Book> bookList = new ArrayList<>();

        bookList.add(new Book(
                "The Living Economics",
                "Peter J. Boettke",
                4.8f,
                R.drawable.book1));

        bookList.add(new Book(
                "Christopher Columbus",
                "Kristal Zahide",
                4.7f,
                R.drawable.book2));

        bookList.add(new Book(
                "Atomic Habits",
                "James Clear",
                4.8f,
                R.drawable.book3));

        bookList.add(new Book(
                "Adventure of Sherlock Holmes",
                "Arthur Conan Doyle",
                4.6f,
                R.drawable.book4));

        RecyclerView recyclerView = findViewById(R.id.bookRecycler);

        recyclerView.setLayoutManager(
                new LinearLayoutManager(
                        this,
                        LinearLayoutManager.HORIZONTAL,
                        false));

        BookAdapter adapter = new BookAdapter(bookList);
        recyclerView.setAdapter(adapter);

        // Initialize the store carousel
        initializeStoreCarousel();
    }

    private void loadStores() {

        storeList = new ArrayList<>();

        storeList.add(new Store(
                "Periplus",
                "Jakarta, Indonesia",
                "Your best one-stop destination for books and more.",
                4.5f,
                R.drawable.dclassics_store1_periplus
        ));

        storeList.add(new Store(
                "Kinokuniya",
                "Jakarta, Indonesia",
                "Displays highly-affordable books and popular educational books for kids.",
                4.2f,
                R.drawable.dclassics_store2_kino
        ));

        storeList.add(new Store(
                "Transit",
                "Tangerang, Indonesia",
                "Comfort zone for who seek safe delivery and aesthetic packaging.",
                4.6f,
                R.drawable.dclassics_store3_transit
        ));

        storeList.add(new Store(
                "Crossword",
                "Bekasi, Indonesia",
                "Perfect for readers who are looking for diverse books.",
                4.3f,
                R.drawable.dclassics_store4_crossword
        ));

        storeList.add(new Store(
                "Gramedia",
                "Bogor, Indonesia",
                "Best deals offer. making a great option for book enthusiasts.",
                4.5f,
                R.drawable.dclassics_store5_gramedia
        ));
    }

    @Override
    protected void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sliderHandler.postDelayed(sliderRunnable, 5000);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        ((DrawerLayout) findViewById(R.id.homeDrawer)).closeDrawer(GravityCompat.START, false);
    }

    private void updateDots(int position) {
        for(int i = 0; i < dots.length; i++){
            if(i == position){
                dots[i].setImageResource(R.drawable.dot_selected);
            }else{
                dots[i].setImageResource(R.drawable.dot_unselected);
            }
        }
    }

    private void initializeStoreCarousel() {

        storePager = findViewById(R.id.storePager);

        btnPreviousStore = findViewById(R.id.btnPreviousStore);
        btnNextStore = findViewById(R.id.btnNextStore);

        dots = new ImageView[]{
                findViewById(R.id.dot1),
                findViewById(R.id.dot2),
                findViewById(R.id.dot3),
                findViewById(R.id.dot4),
                findViewById(R.id.dot5)
        };

        loadStores();
        storeAdapter = new StoreAdapter(this, storeList);
        storePager.setAdapter(storeAdapter);
        sliderHandler.postDelayed(sliderRunnable, 5000);
        storePager.setOffscreenPageLimit(3);
        storePager.setClipToPadding(false);
        storePager.setClipChildren(false);
        storePager.setPageTransformer(new CarouselTransformer());

        updateDots(0);

        storePager.registerOnPageChangeCallback(
                new ViewPager2.OnPageChangeCallback() {

                    @Override
                    public void onPageSelected(int position) {
                        super.onPageSelected(position);

                        updateDots(position);

                        sliderHandler.removeCallbacks(sliderRunnable);
                        sliderHandler.postDelayed(sliderRunnable, 5000);
                    }
                });

        btnNextStore.setOnClickListener(v -> {
            int current = storePager.getCurrentItem();
            if(current < storeAdapter.getItemCount() - 1){
                storePager.setCurrentItem(current + 1, true);
            }
        });

        btnPreviousStore.setOnClickListener(v -> {
            int current = storePager.getCurrentItem();
            if(current > 0){
                storePager.setCurrentItem(current - 1, true);
            }
        });
    }

    private void setupNavigationDrawer() {
        DrawerLayout drawer = findViewById(R.id.homeDrawer);
        findViewById(R.id.menuBtn).setOnClickListener(v -> drawer.openDrawer(GravityCompat.START));
        findViewById(R.id.navHome).setOnClickListener(v -> drawer.closeDrawer(GravityCompat.START));
        findViewById(R.id.navBooks).setOnClickListener(v -> startActivity(new Intent(this, BooksActivity.class)));
        findViewById(R.id.navStore).setOnClickListener(v -> startActivity(new Intent(this, StoreActivity.class)));
        findViewById(R.id.navLogout).setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }
}
