package com.example.dclassicsbooks.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dclassicsbooks.R;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;
import android.text.Html;
import android.widget.TextView;
import com.example.dclassicsbooks.utils.ToastHelper;
import com.example.dclassicsbooks.utils.UserSession;

import com.example.dclassicsbooks.activities.HomeActivity;

public class LoginActivity extends AppCompatActivity {

    EditText etUsername, etPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        String message = getIntent().getStringExtra("toast_message");
        if (message != null) {
            ToastHelper.show(
                    this,
                    message,
                    R.drawable.ic_success
            );
        }

        TextView txtRegister = findViewById(R.id.txtRegister);

        txtRegister.setText(
                Html.fromHtml("<u>Register</u>", Html.FROM_HTML_MODE_LEGACY)
        );

        txtRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this,
                    RegisterActivity.class);
            startActivity(intent);
        });

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        configureKeyboardScrolling();

        btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if(username.isEmpty() || password.isEmpty()){
                ToastHelper.show(this,
                        "Please fill all fields!",
                        R.drawable.ic_error);
            }
            else{
                UserSession.saveUsername(this, username);
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                intent.putExtra("toast_message", "Login successful!");
                startActivity(intent);
                finish();
            }
        });
    }

    private void configureKeyboardScrolling() {
        ScrollView scrollView = findViewById(R.id.main);
        ViewCompat.setOnApplyWindowInsetsListener(scrollView, (view, insets) -> {
            int imeBottom = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom;
            int systemBottom = insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom;
            view.setPadding(0, 0, 0, Math.max(imeBottom, systemBottom));
            return insets;
        });
        etUsername.setOnFocusChangeListener((view, hasFocus) -> { if (hasFocus) revealField(scrollView, view); });
        etPassword.setOnFocusChangeListener((view, hasFocus) -> { if (hasFocus) revealField(scrollView, view); });
    }

    private void revealField(ScrollView scrollView, android.view.View field) {
        scrollView.postDelayed(() -> {
            int targetY = field.getTop();
            android.view.View parent = (android.view.View) field.getParent();
            while (parent != null && parent != scrollView) {
                targetY += parent.getTop();
                parent = parent.getParent() instanceof android.view.View
                        ? (android.view.View) parent.getParent() : null;
            }
            scrollView.smoothScrollTo(0, Math.max(0, targetY - 200));
        }, 350);
    }
}
