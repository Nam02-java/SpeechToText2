package com.example.Selenium.SpeechToText.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Test03 {
    public static void main(String[] args) throws IOException {
        String[] mp3Files = {
                "E:\\New folder\\mp3-output-ttsfree(dot)com.mp3",
                "E:\\New folder\\mp3-output-ttsfree(dot)com (1).mp3",
                "E:\\New folder\\mp3-output-ttsfree(dot)com (2).mp3",
                "E:\\New folder\\mp3-output-ttsfree(dot)com (3).mp3",
                "E:\\New folder\\mp3-output-ttsfree(dot)com (4).mp3",
                "E:\\New folder\\mp3-output-ttsfree(dot)com (5).mp3"
        };
        String outputFile = "E:\\New folder\\all.mp3";

        mergeMP3Files(mp3Files, outputFile);
    }

    public static void mergeMP3Files(String[] mp3Files, String outputFile) throws IOException {
        try (FileOutputStream fostream = new FileOutputStream(outputFile)) {
            for (String mp3File : mp3Files) {
                Files.copy(Paths.get(mp3File), fostream);
            }
        }
    }
}
