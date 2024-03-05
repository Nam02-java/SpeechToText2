package com.example.Selenium.SpeechToText.Controller;

import java.util.ArrayList;

public class GetChunksToArrayList  {

    public ArrayList<String> getChunksToArrayList(ArrayList<String> arrayListChar, String text, int chunkSize) {

        arrayListChar.clear();
        int length = text.length();
        int index = 0;

        while (index < length) {
            int start = index;
            int end = Math.min(index + chunkSize, length);
            arrayListChar.add(text.substring(start, end));
            index += chunkSize;
        }

        return arrayListChar;
    }
}
