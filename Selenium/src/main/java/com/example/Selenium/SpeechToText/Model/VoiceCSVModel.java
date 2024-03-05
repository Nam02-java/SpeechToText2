package com.example.Selenium.SpeechToText.Model;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class VoiceCSVModel extends ReadCSVModel {


    public VoiceCSVModel(CSVStoreModel csvStoreModel, CountDownLatch latchCSV, String columnName) {
        super(csvStoreModel, latchCSV, columnName);
    }

    @Override
    public void dataHandle(int columnIndex, CSVReader csvReader, CSVStoreModel csvStoreModel) throws IOException, CsvException {
        csvStoreModel.setFlag(true);
        // read data form column
        List<String[]> allData = csvReader.readAll();
        for (String[] row : allData) {
            csvStoreModel.setReadTextOfColumn(row[columnIndex]);
            System.out.println("Voice CSV : " + csvStoreModel.getReadTextOfColumn());
        }

        if (CheckGender(csvStoreModel.getReadTextOfColumn())) {
        } else {
            csvStoreModel.setFlag(false);
            csvStoreModel.setNotification("Unknown Voice information, please re-enter");
        }
    }

    private boolean CheckGender(String gender) {
        // Convert the value of gender to lowercase for case-insensitive comparison
        gender = gender.toLowerCase();

        // Check if gender belongs to the allowed values
        return gender.equals("male") || gender.equals("female");
    }
}


