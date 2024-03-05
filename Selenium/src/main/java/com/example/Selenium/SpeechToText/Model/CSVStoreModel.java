package com.example.Selenium.SpeechToText.Model;

import java.util.concurrent.CountDownLatch;

public class CSVStoreModel {
    public String columnName;
    public String readTextOfColumn;
    public CountDownLatch countDownLatch;
    public String notification;
    public boolean flag ;
    public  String csvFilePath = "E:\\CongViecHocTap\\Jmeter\\dulieu.csv";

    public CSVStoreModel() {
        this.countDownLatch = new CountDownLatch(1);
    }


    public String getReadTextOfColumn() {
        return readTextOfColumn;
    }

    public void setReadTextOfColumn(String readTextOfColumn) {
        this.readTextOfColumn = readTextOfColumn;
    }

    public CountDownLatch getCountDownLatch() {
        return countDownLatch;
    }

    public void setCountDownLatch(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public void setCsvFilePath(String csvFilePath) {
        this.csvFilePath = csvFilePath;
    }

    public String getCsvFilePath() {
        return csvFilePath;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }
}
