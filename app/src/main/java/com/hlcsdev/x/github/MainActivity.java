package com.hlcsdev.x.github;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.widget.Toast;

import com.hlcsdev.x.github.adapters.RvAdapter;
import com.hlcsdev.x.github.models.repos.Repos;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    ArrayList<Repos> repos = new ArrayList<>();
    RecyclerView recyclerView;

    boolean authOk; // Аутентификация была/нет
    String username;
    String password;

    SharedPreferences sPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        // Загрузка состояния
        loadState();

        if (!authOk) {
            // Если не было аутентификации
            Intent intent = new Intent(this, AuthActivity.class);
            startActivityForResult(intent, 1);
        }
        else {
            getData();
        }

    }


    private void getData() {

        // Аутентификация
        String auth = null;
        try {
            auth = "Basic " + Base64.encodeToString((username + ":" + password).getBytes("UTF-8"), Base64.NO_WRAP);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        App.getApi().getAuthentication(auth).enqueue(new Callback<List<Repos>>() {
            @Override
            public void onResponse(Call <List<Repos>> call, Response<List<Repos>> response) {

                // Добавление данных в коллекцию
                repos.addAll(response.body());

                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext())); // Установка менеджера
                RvAdapter adapter = new RvAdapter(repos, getApplicationContext());  // Адаптер
                recyclerView.setAdapter(adapter); // Установка адаптера
                // Клик
                adapter.setItemClickCallback(new RvAdapter.ItemClickCallback() {
                    @Override
                    public void onItemClick(String login, String name) {
                        // Переход к commits
                        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                        intent.putExtra("login", login);
                        intent.putExtra("name", name);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Repos>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Ошибка соединения", Toast.LENGTH_SHORT).show();
            }
        });
    }


    // Возврат данных
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                authOk = data.getBooleanExtra("authOk", false);
                username = data.getStringExtra("username");
                password = data.getStringExtra("password");

                getData();
            }
        }
    }
    // Сохранение состояния
    private void saveState() {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sPref.edit();
        editor.putBoolean("authOk", authOk);
        editor.putString("username", username);
        editor.putString("password", password);
        editor.apply();
    }
    // Загрузка состояния
    private void loadState() {
        sPref = getPreferences(MODE_PRIVATE);
        authOk = sPref.getBoolean("authOk", false);
        username = sPref.getString("username", "");
        password = sPref.getString("password", "");
    }
    @Override
    public void onPause() {
        saveState(); // Сохранить
        super.onPause();
    }

}
