package com.example.Selenium.SpeechToText.Model;


import com.example.Selenium.SpeechToText.Controller.ExceptionController;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.checkerframework.checker.units.qual.C;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class TextCSVModel extends ReadCSVModel {


    public TextCSVModel(CSVStoreModel csvStoreModel, CountDownLatch latchCSV, String columnName) {
        super(csvStoreModel, latchCSV, columnName);
    }

    @Override
    public void dataHandle(int columnIndex, CSVReader csvReader, CSVStoreModel csvStoreModel) throws IOException, CsvException {
        // read data form column
        List<String[]> allData = csvReader.readAll();
        for (String[] row : allData) {
            csvStoreModel.setReadTextOfColumn(row[columnIndex]);

            // check length and set value for flag
            if (csvStoreModel.getReadTextOfColumn().length() >= 4001) {
                System.out.println("TextCSV : " + csvStoreModel.getReadTextOfColumn());
                csvStoreModel.setFlag(false);
            } else if (csvStoreModel.getReadTextOfColumn().length() <= 4000) {
                System.out.println("TextCSV : " + csvStoreModel.getReadTextOfColumn());
                csvStoreModel.setFlag(true);
            }
        }
    }
}