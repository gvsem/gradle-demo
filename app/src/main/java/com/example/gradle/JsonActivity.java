package com.example.gradle;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.common.util.IOUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JsonActivity extends AppCompatActivity {

    protected ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);

        listView = findViewById(R.id.listview);
    }

    @Override
    protected void onStart() {
        super.onStart();

        new Thread(() -> {

            List<String> result = new ArrayList<>();

            try (InputStream is = new URL("https://hp-api.onrender.com/api/characters").openStream()) {
                String text = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))
                        .lines().collect(Collectors.joining("\n"));

                JsonArray data = new Gson().fromJson(text, JsonArray.class);
                for (JsonElement e : data) {
                    result.add(e.getAsJsonObject().get("name") + " " + e.getAsJsonObject().get("yearOfBirth"));
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            runOnUiThread(() -> {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(JsonActivity.this, android.R.layout.simple_list_item_1, result);
                listView.setAdapter(adapter);
            });

        }).start();

    }
}