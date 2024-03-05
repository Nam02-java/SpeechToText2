package com.example.Selenium.SpeechToText.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Test05 {
    public static void main(String[] args) throws IOException {
        List<File> mp3Files = new ArrayList<>();
        mp3Files.add(new File("E:\\New folder\\mp3-output-ttsfree(dot)com.mp3"));
        mp3Files.add(new File("E:\\New folder\\mp3-output-ttsfree(dot)com (1).mp3"));
        mp3Files.add(new File("E:\\New folder\\mp3-output-ttsfree(dot)com (2).mp3"));
        mp3Files.add(new File("E:\\New folder\\mp3-output-ttsfree(dot)com (3).mp3"));
        mp3Files.add(new File("E:\\New folder\\mp3-output-ttsfree(dot)com (4).mp3"));
        mp3Files.add(new File("E:\\New folder\\mp3-output-ttsfree(dot)com (5).mp3"));

        File outputFile = new File("E:\\New folder\\p.mp3");

        mergeMP3Files(mp3Files, outputFile);
    }

    public static void mergeMP3Files(List<File> mp3Files, File outputFile) throws IOException {
        try (OutputStream fostream = new FileOutputStream(outputFile)) {
            for (File mp3File : mp3Files) {
                Files.copy(mp3File.toPath(), fostream);
            }
        }
    }
}
