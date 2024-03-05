package com.example.Selenium.SpeechToText.Model;


import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class FileNameCSVModel extends ReadCSVModel {


    public FileNameCSVModel(CSVStoreModel csvStoreModel, DataStoreModel dataStoreModel, CountDownLatch latchCSV, String columnName) {
        super(csvStoreModel, dataStoreModel, latchCSV, columnName);
    }

    @Override
    public void dataHandle(int columnIndex, CSVReader csvReader, CSVStoreModel csvStoreModel) throws IOException, CsvException {
        csvStoreModel.setFlag(true);
        // read data form column
        List<String[]> allData = csvReader.readAll();
        for (String[] row : allData) {
            csvStoreModel.setReadTextOfColumn(row[columnIndex]);
            System.out.println("FileName : " + csvStoreModel.getReadTextOfColumn());
            csvStoreModel.setNotification(null);
        }

        isFileDuplicate(dataStoreModel.getDownloadsFilePath());

        if (csvStoreModel.getReadTextOfColumn().length() >= 51) {
            csvStoreModel.setNotification("File length exceeds 50 characters");
            csvStoreModel.setFlag(false);
        }
    }

    protected void isFileDuplicate(String filePath) {
        File directory = new File(filePath);
        File[] files = directory.listFiles(File::isFile);
        for (int i = 0; i < files.length; i++) {
            String name = files[i].getName();
            String target = name.copyValueOf(".mp3".toCharArray());
            name = name.replace(target, "");
            if (name.equals(csvStoreModel.getReadTextOfColumn())) {
                csvStoreModel.setNotification("Tên File bị trùng trong thư mục");
                csvStoreModel.setFlag(false);
                break;
            }
        }
    }
}

