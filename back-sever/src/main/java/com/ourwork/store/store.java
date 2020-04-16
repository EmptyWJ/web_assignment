package com.ourwork.store;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.google.gson.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.ourwork.model.model;

@Service
public class store {
    @Value("${works.store.filename}")
    private String fileName;
    @Value("${env}")
    private String env;
    private Gson getGson() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>)
                        (json, typeOfT, context) -> LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>)
                        (localDateTime, typeOfT, context) -> new JsonPrimitive(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))))
                .create();
    }

    private File getFile() {
        if (env.equals("test")) {
            return new File(getClass().getClassLoader().getResource(fileName).getFile());
        }
        return new File(fileName);
    }

    public List<model> readModel() {
        try {
            String contents = new String(Files.readAllBytes(getFile().toPath()));
            model[] tasks = getGson().fromJson(contents, model[].class);
            return Arrays.asList(tasks);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public void writeTasks(List<model> tasks) {
        try {
            FileWriter fileWriter = new FileWriter(getFile().getAbsolutePath());
            fileWriter.write(getGson().toJson(tasks));
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}