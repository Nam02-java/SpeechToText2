package com.example.Selenium.SpeechToText.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class Test02 {
    public static void main(String[] args) throws IOException {
        String[] mp3Files = {
                "E:\\New folder\\mp3-output-ttsfree(dot)com.mp3",
                "E:\\New folder\\mp3-output-ttsfree(dot)com (1).mp3",
                "E:\\New folder\\mp3-output-ttsfree(dot)com (2).mp3",
                "E:\\New folder\\mp3-output-ttsfree(dot)com (3).mp3"
        };
        String outputFile = "E:\\New folder\\dune.mp3";

        mergeMP3Files(mp3Files, outputFile);
    }

    public static void mergeMP3Files(String[] mp3Files, String outputFile) throws IOException {
        FileOutputStream fostream = new FileOutputStream(outputFile);

        for (String mp3File : mp3Files) {
            try (FileInputStream fistream = new FileInputStream(mp3File)) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = fistream.read(buffer)) != -1) {
                    fostream.write(buffer, 0, bytesRead);
                }
            }
        }

        fostream.close();
    }
}
