package com.example.dclassicsbooks.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ScrollView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dclassicsbooks.R;
import com.example.dclassicsbooks.utils.ToastHelper;

public class RegisterActivity extends AppCompatActivity {

    EditText etUsername, etPassword;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TextView txtLogin = findViewById(R.id.txtLogin);

        txtLogin.setText(
                Html.fromHtml("<u>Login</u>", Html.FROM_HTML_MODE_LEGACY)
        );

        txtLogin.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this,
                    LoginActivity.class);
            startActivity(intent);
        });

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);
        configureKeyboardScrolling();

        btnRegister.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if(username.isEmpty() || password.isEmpty()){
                ToastHelper.show(this,
                        "Please fill all fields!",
                        R.drawable.ic_error);
            }
            else{
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                intent.putExtra("toast_message", "Register successful!");
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
