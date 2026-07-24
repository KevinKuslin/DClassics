package com.example.dclassicsbooks.activities;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dclassicsbooks.R;
import com.google.android.material.button.MaterialButton;

public class BooksDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_books_details);

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(android.R.id.content),
                (v, insets) -> {

                    Insets systemBars = insets.getInsets(
                            WindowInsetsCompat.Type.systemBars()
                    );

                    v.setPadding(
                            systemBars.left,
                            systemBars.top,
                            systemBars.right,
                            systemBars.bottom
                    );

                    return insets;
                }
        );

        // =========================
        // BOOK IMAGE
        // =========================
        int bookImage = getIntent().getIntExtra("book_image", 0);

        if (bookImage != 0) {
            ImageView imgBook = findViewById(R.id.imgBook);
            imgBook.setImageResource(bookImage);
        }


        // =========================
        // AUTO SCROLL TO ORDER
        // =========================
        if (getIntent().getBooleanExtra("open_order", false)) {

            ScrollView scrollView =
                    findViewById(R.id.bookDetailScroll);

            View orderSection =
                    findViewById(R.id.orderRequestSection);

            scrollView.post(() ->
                    scrollView.smoothScrollTo(
                            0,
                            orderSection.getTop()
                    )
            );
        }


        // =========================
        // ORDER NOW BUTTON & VALIDATION
        // =========================
        android.widget.EditText etAddress = findViewById(R.id.etAddress);
        android.widget.EditText etPhoneNumber = findViewById(R.id.etPhoneNumber);
        MaterialButton btnOrderNow = findViewById(R.id.btnOrderNow);

        btnOrderNow.setOnClickListener(v -> {

            // Ambil text dari inputan dan hilangkan spasi berlebih
            String address = etAddress.getText().toString().trim();
            String phone = etPhoneNumber.getText().toString().trim();

            // Validasi apakah Address kosong
            if (address.isEmpty()) {
                etAddress.setError("Address tidak boleh kosong!");
                etAddress.requestFocus();
                return; // Stop proses di sini
            }

            // Validasi apakah Phone Number kosong
            if (phone.isEmpty()) {
                etPhoneNumber.setError("Phone Number tidak boleh kosong!");
                etPhoneNumber.requestFocus();
                return; // Stop proses di sini
            }

            // Validasi apakah Phone Number HANYA berisi angka
            if (!phone.matches("[0-9]+")) {
                etPhoneNumber.setError("Phone Number hanya boleh berisi angka!");
                etPhoneNumber.requestFocus();
                return; // Stop proses di sini
            }

            // Kalau semua inputan aman, baru jalankan pop-up dialog
            showOrderSuccessDialog();
        });
    }


    // =====================================
    // ORDER SUCCESS DIALOG
    // =====================================
    private void showOrderSuccessDialog() {

        Dialog dialog = new Dialog(this);

        dialog.requestWindowFeature(
                Window.FEATURE_NO_TITLE
        );

        dialog.setContentView(
                R.layout.activity_dialog_order
        );

        // Klik luar popup tidak menutup dialog
        dialog.setCanceledOnTouchOutside(false);

        // Back button masih bisa menutup
        dialog.setCancelable(true);


        // =========================
        // BUTTONS INSIDE DIALOG
        // =========================
        TextView btnViewBook =
                dialog.findViewById(R.id.btnViewBook);

        TextView btnContinueOrdering =
                dialog.findViewById(R.id.btnContinueOrdering);


        // VIEW BOOK
        btnViewBook.setOnClickListener(v -> {

            dialog.dismiss();

            // Nanti action View Book bisa
            // ditambahkan di sini.
        });


        // CONTINUE ORDERING
        btnContinueOrdering.setOnClickListener(v -> {

            // Tutup popup
            dialog.dismiss();
        });


        // =========================
        // SHOW DIALOG
        // =========================
        dialog.show();


        // =========================
        // WINDOW STYLE
        // =========================
        Window window = dialog.getWindow();

        if (window != null) {

            // Background popup transparan
            window.setBackgroundDrawable(
                    new ColorDrawable(Color.TRANSPARENT)
            );

            // Lebar popup
            window.setLayout(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT
            );

            // Background activity menjadi gelap
            WindowManager.LayoutParams params =
                    window.getAttributes();

            params.dimAmount = 0.55f;

            window.setAttributes(params);

            window.addFlags(
                    WindowManager.LayoutParams.FLAG_DIM_BEHIND
            );
        }
    }
}