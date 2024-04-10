package org.example;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JsonAttributeSearch {
    public static String[] searchAttributes(String filePath, String[] jsonPaths) {
        String[] attributeValues = new String[jsonPaths.length];
        try {
            // Read JSON file
            String jsonString = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONObject jsonObject = new JSONObject(jsonString);

            // Iterate over each JSON path
            for (int i = 0; i < jsonPaths.length; i++) {
                String jsonPath = jsonPaths[i];
                // Use JSONPath expression to navigate through JSON object
                String[] pathSegments = jsonPath.split("\\.");
                JSONObject currentObject = jsonObject;
                for (String segment : pathSegments) {
                    if (currentObject.has(segment)) {
                        Object obj = currentObject.get(segment);
                        if (obj instanceof JSONObject) {
                            currentObject = (JSONObject) obj;
                        } else {
                            attributeValues[i] = obj.toString();
                        }
                    } else {
                        attributeValues[i] = "Attribute not found";
                        break;
                    }
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            attributeValues = new String[]{"Error reading file"};
        }
        return attributeValues;
    }

    public static void main(String[] args) {
        List<String> filepaths = new ArrayList<>();
        filepaths.add("C:/Users/mdumair/Downloads/BringgRecurringSlot_416 1.json");
        filepaths.add("C:/Users/mdumair/Downloads/BringgRecurringSlot_416 2.json");
        // Add more file paths as needed

        String[] jsonPaths = {"rsreservation.task.external_id", "rscreation.schedule_type"};

        for (String filepath : filepaths) {
            String[] attributeValues = searchAttributes(filepath, jsonPaths);
            System.out.println("File: " + filepath);
            for (int i = 0; i < attributeValues.length; i++) {
                System.out.println("Attribute " + (i+1) + " Value: " + attributeValues[i]);
            }
        }
    }
}
