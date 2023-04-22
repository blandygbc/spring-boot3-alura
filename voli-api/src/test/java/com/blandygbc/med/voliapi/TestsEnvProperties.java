package com.blandygbc.med.voliapi;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class TestsEnvProperties {
    public static void load() {
        File resourcesDirectory = new File("src/test/resources");
        try (Stream<String> stream = Files.lines(
                Paths.get(resourcesDirectory.getAbsolutePath() + File.separator + "test.env"))) {
            stream.forEach(rawLine -> {
                String pair[] = new String[2];
                pair[0] = rawLine.trim().substring(0, rawLine.indexOf("="));
                pair[1] = rawLine.trim().substring(rawLine.indexOf("=") + 1);
                System.setProperty(pair[0], pair[1]);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
