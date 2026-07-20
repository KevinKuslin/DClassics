package com.example.dclassicsbooks.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dclassicsbooks.R;

public class BooksDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_books_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        int bookImage = getIntent().getIntExtra("book_image", 0);
        if (bookImage != 0) {
            ((ImageView) findViewById(R.id.imgBook)).setImageResource(bookImage);
        }

        if (getIntent().getBooleanExtra("open_order", false)) {
            ScrollView scrollView = findViewById(R.id.bookDetailScroll);
            View orderSection = findViewById(R.id.orderRequestSection);
            scrollView.post(() -> scrollView.smoothScrollTo(0, orderSection.getTop()));
        }
    }
}
