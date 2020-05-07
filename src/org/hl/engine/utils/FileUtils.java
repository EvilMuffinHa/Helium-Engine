package org.hl.engine.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileUtils {

    // Reads a filepath and returns that as a String
    public static String loadAsString(String filepath) {
        StringBuilder result = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new
                InputStreamReader(FileUtils.class.getResourceAsStream(filepath)))) {
            String line = "";
            while ((line = reader.readLine()) != null) {
                result.append(line) .append("\n");
            }
        } catch (Exception e) {
            System.err.println("Couldn't get the file at " + filepath);
            System.exit(1);

        }


        return result.toString();
    }
}
