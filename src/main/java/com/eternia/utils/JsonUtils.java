package com.eternia.utils;

import com.eternia.Eternia;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class JsonUtils {
    public static Map<String, JsonObject> jsonObjects = new HashMap<>();
    private static final String dataFolder = Eternia.getInstance().getDataFolder().toString();
    static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    public static void loadConfig() {
        Map<String, String> filePaths = new HashMap<>();
        filePaths.put(dataFolder + "/ParticlesData/EyeofLight.json", "Eye of Light");

        for (String filePath : filePaths.keySet()) {
            try {
                // getting the data
                File file = new File(filePath);
                FileInputStream fis = new FileInputStream(file);
                JsonReader reader = new JsonReader(new InputStreamReader(fis, "UTF-8"));
                jsonObjects.put(filePaths.get(filePath), GSON.fromJson(reader, JsonObject.class));
            } catch (IOException e) {
                // print the error
                e.printStackTrace();
            }
        }
    }
}
