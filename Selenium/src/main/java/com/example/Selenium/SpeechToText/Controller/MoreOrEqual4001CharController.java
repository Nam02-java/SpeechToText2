package com.example.Selenium.SpeechToText.Controller;

import com.example.Selenium.SpeechToText.Model.CSVStoreModel;
import com.example.Selenium.SpeechToText.Model.DataStoreModel;
import com.example.Selenium.SpeechToText.Model.TelegramDataStoreModel;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CountDownLatch;

public class MoreOrEqual4001CharController extends InitializationDriverController {
    public MoreOrEqual4001CharController(CountDownLatch countDownLatch, TelegramDataStoreModel telegramDataStoreModel, DataStoreModel dataStoreModel, WebDriver driver, String textFromTextColumnCsvFile, CSVStoreModel textCsvStoreModel, CSVStoreModel voiceCsvStoreModel, CSVStoreModel fileNameCsvStoreModel) {
        super(telegramDataStoreModel, dataStoreModel, driver, textFromTextColumnCsvFile, textCsvStoreModel, voiceCsvStoreModel, fileNameCsvStoreModel);
        this.dataStoreModel.countDownLatch = countDownLatch;
    }


    @Override
    public void changeFileName( Map<String, String> params) {

    }

    @Override
    public void changeFileName() {

        File folder = new File("E:\\New folder\\");
        File[] files = folder.listFiles();

        // Sort by time in descending order
        Arrays.sort(files, Comparator.comparingLong(File::lastModified).reversed());

        File latestFile;

        for (int i = 0; i < count; i++) {
            latestFile = files[i];

            // Print out information file sorted by year - month - day - hour - minute - second
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long lastModifiedTimestamp = latestFile.lastModified();
            Date lastModifiedDate = new Date(lastModifiedTimestamp);
            String formattedTime = dateFormat.format(lastModifiedDate);
            System.out.println("File: " + latestFile.getName() + " - Download time : " + formattedTime);

            dataStoreModel.getFileMergeArray().add(latestFile);
        }

        Collections.reverse(dataStoreModel.getFileMergeArray());

        File paramFileName = new File("E:\\New folder\\" + dataStoreModel.getParams().get("FileName") + ".mp3");
        try {
            mergeMP3Files(dataStoreModel.getFileMergeArray(), paramFileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Delete old files
        for (File file : dataStoreModel.getFileMergeArray()) {
            Path path = Paths.get(file.getAbsolutePath());
            try {
                Files.delete(path);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void mergeMP3Files(List<File> mp3Files, File outputFile) throws IOException {
        try (OutputStream fostream = new FileOutputStream(outputFile)) {
            for (File mp3File : mp3Files) {
                Files.copy(mp3File.toPath(), fostream);
            }
        }
    }
}
