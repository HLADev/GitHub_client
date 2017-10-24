package com.hlcsdev.x.github;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.hlcsdev.x.github.adapters.RvAdapter2;
import com.hlcsdev.x.github.models.commits.Commits;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main2Activity extends AppCompatActivity {

    ArrayList<Commits> commits = new ArrayList<>();
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        getData();

    }


    private void getData() {

        final String login = getIntent().getStringExtra("login");
        final String name = getIntent().getStringExtra("name");

        App.getApi().getCommits(login, name).enqueue(new Callback<List<Commits>>() {
            @Override
            public void onResponse(Call <List<Commits>> call, Response<List<Commits>> response) {

                // Добавление данных в коллекцию
                commits.addAll(response.body());

                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext())); // Установка менеджера
                RvAdapter2 adapter = new RvAdapter2(commits);  // Адаптер
                recyclerView.setAdapter(adapter); // Установка адаптера
            }

            @Override
            public void onFailure(Call<List<Commits>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Ошибка соединения", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
