package com.example.android_project.data.remote;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public final class CurrencyApi {

    // one central place for the endpoint, in the course's "a constant per link" style
    public static final String BASE_URL = "https://open.er-api.com/v6/latest/USD";

    private CurrencyApi() {
    }

    // callers run this off the main thread; it throws on any network or parse failure so the
    // repository can fall back to the cached rate instead of showing a wrong number
    public static double fetchUsdToIls() throws Exception {
        HttpURLConnection connection = (HttpURLConnection) new URL(BASE_URL).openConnection();
        try {
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);

            int code = connection.getResponseCode();
            if (code != HttpURLConnection.HTTP_OK) {
                throw new Exception("HTTP " + code);
            }

            String body = readAll(connection.getInputStream());
            JSONObject rates = new JSONObject(body).getJSONObject("rates");
            return rates.getDouble("ILS");
        } finally {
            connection.disconnect();
        }
    }

    private static String readAll(InputStream in) throws Exception {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(in, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        return sb.toString();
    }
}
