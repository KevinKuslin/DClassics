package com.example.dclassicsbooks.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.content.Context;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dclassicsbooks.R;
import com.google.android.material.button.MaterialButton;

public class BooksDetailsActivity extends AppCompatActivity {

    private ScrollView scrollView;
    private EditText etAddress;
    private EditText etPhoneNumber;

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

                    Insets ime = insets.getInsets(
                            WindowInsetsCompat.Type.ime()
                    );

                    boolean keyboardVisible =
                            insets.isVisible(WindowInsetsCompat.Type.ime());

                    int bottomPadding = keyboardVisible
                            ? ime.bottom
                            : systemBars.bottom;

                    v.setPadding(
                            systemBars.left,
                            systemBars.top,
                            systemBars.right,
                            bottomPadding
                    );

                    return insets;
                }
        );

        // =========================
        // FIND VIEWS
        // =========================
        scrollView = findViewById(R.id.bookDetailScroll);
        etAddress = findViewById(R.id.etAddress);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);

        MaterialButton btnOrderNow = findViewById(R.id.btnOrderNow);
        MaterialButton btnBack = findViewById(R.id.btnBack);

        // =========================
        // BOOK IMAGE
        // =========================
        int bookImage = getIntent().getIntExtra("book_image", 0);

        if (bookImage != 0) {
            ImageView imgBook = findViewById(R.id.imgBook);
            imgBook.setImageResource(bookImage);
        }

        // =========================
        // BACK BUTTON
        // =========================
        btnBack.setOnClickListener(v -> finish());

        // =========================
        // AUTO SCROLL TO ORDER
        // =========================
        if (getIntent().getBooleanExtra("open_order", false)) {

            View orderSection = findViewById(R.id.orderRequestSection);

            scrollView.post(() ->
                    scrollView.smoothScrollTo(
                            0,
                            orderSection.getTop()
                    )
            );
        }

        // =========================
        // SCROLL WHEN KEYBOARD OPENS
        // =========================
        setupKeyboardScroll(etAddress);
        setupKeyboardScroll(etPhoneNumber);

        // =========================
        // ORDER NOW
        // =========================
        btnOrderNow.setOnClickListener(v -> {

            String address = etAddress.getText().toString().trim();
            String phone = etPhoneNumber.getText().toString().trim();

            if (address.isEmpty()) {
                etAddress.setError("Address cannot be empty!");
                etAddress.requestFocus();
                scrollToField(etAddress);
                return;
            }

            if (phone.isEmpty()) {
                etPhoneNumber.setError("Phone Number cannot be empty!");
                etPhoneNumber.requestFocus();
                scrollToField(etPhoneNumber);
                return;
            }

            if (!phone.matches("[0-9]+")) {
                etPhoneNumber.setError("Phone Number must be in numbers!");
                etPhoneNumber.requestFocus();
                scrollToField(etPhoneNumber);
                return;
            }

            if (phone.length() < 7 || phone.length() > 14) {
                etPhoneNumber.setError("Phone Number must be between 7 and 14 digits!");
                etPhoneNumber.requestFocus();
                scrollToField(etPhoneNumber);
                return;
            }


            hideKeyboard();

            showOrderSuccessDialog();
        });
    }

    // =====================================
    // SCROLL FIELD ABOVE KEYBOARD
    // =====================================
    private void setupKeyboardScroll(EditText editText) {

        editText.setOnFocusChangeListener((v, hasFocus) -> {

            if (hasFocus) {
                scrollToField(v);
            }

        });
    }

    private void scrollToField(View view) {

        scrollView.postDelayed(() -> {

            int targetY = view.getTop();

            View parent = (View) view.getParent();

            while (parent != null && parent != scrollView) {
                targetY += parent.getTop();

                if (parent.getParent() instanceof View) {
                    parent = (View) parent.getParent();
                } else {
                    break;
                }
            }

            // Kasih ruang di atas field
            targetY -= 200;

            scrollView.smoothScrollTo(
                    0,
                    Math.max(targetY, 0)
            );

        }, 350);
    }

    // =====================================
    // HIDE KEYBOARD
    // =====================================
    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm =
                    (InputMethodManager) getSystemService(
                            Context.INPUT_METHOD_SERVICE
                    );
            imm.hideSoftInputFromWindow(
                    view.getWindowToken(),
                    0
            );
        }
    }


    // =====================================
    // ORDER SUCCESS DIALOG
    // =====================================
    private void showOrderSuccessDialog() {

        Dialog dialog = new Dialog(this);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.activity_dialog_order);

        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);


        // =========================
        // BUTTON CONTAINERS
        // =========================
        LinearLayout btnViewBook =
                dialog.findViewById(R.id.btnViewBookLayout);

        LinearLayout btnContinueOrdering =
                dialog.findViewById(R.id.btnContinueOrderingLayout);


        // =========================
        // VIEW BOOK
        // =========================
        btnViewBook.setOnClickListener(v -> {
            dialog.dismiss();

        });

        // =========================
        // CONTINUE ORDERING
        // =========================
        btnContinueOrdering.setOnClickListener(v -> {

            dialog.dismiss();

            Intent intent =
                    new Intent(
                            BooksDetailsActivity.this,
                            BooksActivity.class
                    );

            intent.addFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP |
                            Intent.FLAG_ACTIVITY_SINGLE_TOP
            );
            startActivity(intent);
            finish();
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

            window.setBackgroundDrawable(
                    new ColorDrawable(Color.TRANSPARENT)
            );

            window.setLayout(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT
            );

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