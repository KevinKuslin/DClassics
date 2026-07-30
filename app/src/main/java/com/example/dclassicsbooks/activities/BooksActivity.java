package com.example.dclassicsbooks.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dclassicsbooks.R;
import com.example.dclassicsbooks.adapters.BookCatalogAdapter;
import com.example.dclassicsbooks.models.Book;
import com.example.dclassicsbooks.utils.UserSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BooksActivity extends AppCompatActivity {
    private final List<Book> allBooks = new ArrayList<>();
    private BookCatalogAdapter adapter;
    private EditText search;
    private boolean fictionOnly = false;
    private TextView count, nonFictionButton, fictionButton;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);
        UserSession.applyUsername(this);
        populateBooks();
        DrawerLayout drawer = findViewById(R.id.booksDrawer);
        search = findViewById(R.id.etSearchBooks);
        count = findViewById(R.id.tvBookCount);
        nonFictionButton = findViewById(R.id.btnNonFiction);
        fictionButton = findViewById(R.id.btnFiction);
        RecyclerView recyclerView = findViewById(R.id.rvBooks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BookCatalogAdapter(allBooks);
        recyclerView.setAdapter(adapter);
        findViewById(R.id.btnMenu).setOnClickListener(v -> drawer.openDrawer(GravityCompat.START));
        setupNavigation(drawer);
        nonFictionButton.setOnClickListener(v -> { fictionOnly = false; updateFilterButtons(); applyFilters(); });
        fictionButton.setOnClickListener(v -> { fictionOnly = true; updateFilterButtons(); applyFilters(); });
        search.addTextChangedListener(new SimpleTextWatcher(this::applyFilters));
        applyFilters();
    }
    private void populateBooks() {
        allBooks.add(new Book("Living Economics", "Peter J. Boettke", 5f, R.drawable.book1, "Finance", false));
        allBooks.add(new Book("Atomic Habits", "James Clear", 4.8f, R.drawable.book3, "Self Growth", false));
        allBooks.add(new Book("Christopher Columbus", "Kristal Zahide", 4.7f, R.drawable.book2, "History", false));
        allBooks.add(new Book("Sherlock Holmes", "Arthur Conan Doyle", 4.6f, R.drawable.book4, "Mystery", true));
        allBooks.add(new Book("Pride and Prejudice", "Jane Austen", 4.5f, R.drawable.book5, "Romance", true));
        allBooks.add(new Book("The Great Gatsby", "F. Scott Fitzgerald", 4.4f, R.drawable.book6, "Classic", true));
    }
    private void applyFilters() {
        String keyword = search.getText().toString().trim().toLowerCase(Locale.getDefault());
        List<Book> filtered = new ArrayList<>();
        for (Book book : allBooks) if (book.isFiction() == fictionOnly && book.getTitle().toLowerCase(Locale.getDefault()).contains(keyword)) filtered.add(book);
        adapter.submitList(filtered);
        count.setText("Showing " + filtered.size() + " books");
    }
    private void updateFilterButtons() {
        nonFictionButton.setBackgroundResource(fictionOnly ? R.drawable.bg_filter_unselected : R.drawable.bg_filter_selected);
        fictionButton.setBackgroundResource(fictionOnly ? R.drawable.bg_filter_selected : R.drawable.bg_filter_unselected);
        nonFictionButton.setTextColor(getColor(fictionOnly ? R.color.secondary_blue : R.color.white));
        fictionButton.setTextColor(getColor(fictionOnly ? R.color.white : R.color.secondary_blue));
    }
    private void setupNavigation(DrawerLayout drawer) {
        findViewById(R.id.navHome).setOnClickListener(v -> { startActivity(new Intent(this, HomeActivity.class)); finish(); });
        findViewById(R.id.navBooks).setOnClickListener(v -> drawer.closeDrawer(GravityCompat.START));
        findViewById(R.id.navStore).setOnClickListener(v -> { startActivity(new Intent(this, StoreActivity.class)); finish(); });
        findViewById(R.id.navLogout).setOnClickListener(v -> { Intent i = new Intent(this, LoginActivity.class); i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); startActivity(i); });
    }
    private static class SimpleTextWatcher implements TextWatcher { final Runnable action; SimpleTextWatcher(Runnable action) { this.action=action; } public void beforeTextChanged(CharSequence s,int st,int c,int a){} public void onTextChanged(CharSequence s,int st,int b,int c){ action.run(); } public void afterTextChanged(Editable s){} }
}
