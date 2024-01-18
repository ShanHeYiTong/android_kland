package com.example.kland;

// LoginActivity.java
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsername, editTextPassword;
    private Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); // 设置布局文件


        // 初始化控件
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);

        // 设置登录按钮点击事件
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 获取用户名和密码
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();

                // 在实际应用中，这里可以添加登录逻辑，例如与服务器交互验证用户名和密码
                loginToServer(username, password);
                // 这里简单示例，显示一个 Toast 提示
                String message = "Username: " + username + "\nPassword: " + password;
                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loginToServer(String username, String password) {
        // 创建 Retrofit 实例
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.176:9100/") // 服务器的基本 URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // 创建 AuthService 实例
        AuthService authService = retrofit.create(AuthService.class);

        // 发起登录请求
        Call<LoginResponse> call = authService.login(username, password);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    // 登录成功，保存返回的 token 信息
                    String token = response.body().getToken();

                    // 在实际应用中，你可能需要将 token 存储在 SharedPreferences 中或其他安全的方式

                    // 跳转到 App 的 Home 页面
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish(); // 结束当前登录页面
                } else {
                    // 登录失败，显示错误信息
                    Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                // 网络请求失败
                Toast.makeText(LoginActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

