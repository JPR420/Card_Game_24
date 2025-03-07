package edu.farmingdale.demo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class OpenAI {
    private static final String API_KEY = System.getenv("APIKEY");

    public static String getTextResponse(String prompt) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        JSONObject body = new JSONObject();
        body.put("model","gpt-4");  // Ensure you are using the correct model
        body.put("messages", new JSONArray()
                .put(new JSONObject().put("role", "system").put("content", "You are a helpful assistant."))
                .put(new JSONObject().put("role", "user").put("content", prompt))
        );
        body.put("max_tokens", 100);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + API_KEY)
                .POST(HttpRequest.BodyPublishers.ofString(body.toString(), StandardCharsets.UTF_8))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JSONObject responseBody = new JSONObject(response.body());

        // Checking if the response contains the "choices" field
        if (responseBody.has("choices")) {
            String text = responseBody.getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content");
            return text.trim();  // Returning only the content from the first choice
        } else {
            throw new JSONException("Response does not contain 'choices' field");
        }
    }

}
