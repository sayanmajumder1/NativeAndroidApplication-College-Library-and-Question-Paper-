package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapter.ChatAdapter;
import com.example.myapplication.models.ChatMessage;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class chatai extends AppCompatActivity {
    ImageButton buttonBack;
    private RecyclerView recyclerViewChat;
    private EditText editTextMessage;
    Button buttonSend;
    private ChatAdapter chatAdapter;
    private List<ChatMessage> chatMessages;
    private SharedPreferences sharedPreferences;

    private static final String PREFS_NAME = "ChatPrefs";
    private static final String KEY_CHAT_HISTORY = "chatHistory";
    private static final int MAX_CHAT_HISTORY = 50;

    private static final String GEMINI_API_KEY = "AIzaSyAQAm0q7Aq88fLWE8AIk-f7p0n5fNclZ4w"; // Replace with a valid API key
    private static final String GEMINI_API_URL = "https://generativelanguage.googleapis.com/v1/models/gemini-1.5-flash:generateContent?key=" + GEMINI_API_KEY;

    private final OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chatai);

        // Adjust the window insets for better keyboard handling
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.edittext), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initializeUI();
        loadChatHistory();
    }

    private void initializeUI() {
        buttonBack = findViewById(R.id.button1333);
        recyclerViewChat = findViewById(R.id.recyclerViewChat);
        editTextMessage = findViewById(R.id.editTextMessage);
        buttonSend = findViewById(R.id.buttonSend);

        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        chatMessages = new ArrayList<>();

        chatAdapter = new ChatAdapter(chatMessages);
        recyclerViewChat.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewChat.setAdapter(chatAdapter);

        buttonBack.setOnClickListener(v -> finish());
        buttonSend.setOnClickListener(v -> sendMessage());
    }

    private void sendMessage() {
        String message = editTextMessage.getText().toString().trim();
        if (message.isEmpty()) {
            Toast.makeText(this, "Message cannot be empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        addMessageToChat(new ChatMessage(message, true));

        // Clear input field and hide keyboard after sending
        editTextMessage.setText("");
        hideKeyboard();

        getGeminiResponse(message);
    }

    private void getGeminiResponse(String message) {
        JsonObject requestBody = new JsonObject();
        JsonArray contents = new JsonArray();
        JsonObject part = new JsonObject();
        part.addProperty("text", message);

        JsonObject contentObject = new JsonObject();
        contentObject.add("parts", new JsonArray());
        contentObject.getAsJsonArray("parts").add(part);
        contents.add(contentObject);

        requestBody.add("contents", contents);

        RequestBody body = RequestBody.create(requestBody.toString(), MediaType.get("application/json; charset=utf-8"));

        Request request = new Request.Builder()
                .url(GEMINI_API_URL)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(() -> showToast("Failed to connect to API!"));
                Log.e("API_ERROR", "Connection failed: " + e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (!response.isSuccessful()) {
                    runOnUiThread(() -> showToast("API Error: " + response.code()));
                    return;
                }

                assert response.body() != null;
                String responseData = response.body().string();
                String reply = parseGeminiResponse(responseData);

                runOnUiThread(() -> addMessageToChat(new ChatMessage(reply, false)));
            }
        });
    }

    private String parseGeminiResponse(String responseData) {
        try {
            JsonObject jsonResponse = JsonParser.parseString(responseData).getAsJsonObject();
            JsonArray candidates = jsonResponse.getAsJsonArray("candidates");

            if (candidates != null && !candidates.isEmpty()) {
                JsonObject firstCandidate = candidates.get(0).getAsJsonObject();
                JsonArray parts = firstCandidate.getAsJsonObject("content").getAsJsonArray("parts");

                if (parts != null && !parts.isEmpty()) {
                    return parts.get(0).getAsJsonObject().get("text").getAsString();
                }
            }
            return "Sorry, I couldn't understand the response.";
        } catch (Exception e) {
            Log.e("API_PARSE_ERROR", "Parsing error: " + e.getMessage());
            return "Error processing response.";
        }
    }

    private void addMessageToChat(ChatMessage message) {
        chatMessages.add(message);
        chatAdapter.notifyItemInserted(chatMessages.size() - 1);
        recyclerViewChat.smoothScrollToPosition(chatMessages.size() - 1);
        saveChatHistory();
    }

    private void saveChatHistory() {
        if (chatMessages.size() > MAX_CHAT_HISTORY) {
            chatMessages = new ArrayList<>(chatMessages.subList(chatMessages.size() - MAX_CHAT_HISTORY, chatMessages.size()));
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_CHAT_HISTORY, new Gson().toJson(chatMessages));
        editor.apply();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadChatHistory() {
        String json = sharedPreferences.getString(KEY_CHAT_HISTORY, null);
        Type type = new TypeToken<List<ChatMessage>>() {}.getType();

        List<ChatMessage> savedMessages = new Gson().fromJson(json, type);
        if (savedMessages != null) {
            chatMessages.addAll(savedMessages);
            chatAdapter.notifyDataSetChanged();
        }
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sharedPreferences.edit().remove(KEY_CHAT_HISTORY).apply(); // Remove saved chat
    }
}

