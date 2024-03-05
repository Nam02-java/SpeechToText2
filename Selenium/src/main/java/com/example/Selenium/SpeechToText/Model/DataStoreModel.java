package com.example.Selenium.SpeechToText.Model;

import com.example.Selenium.SpeechToText.Controller.EnumController;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.*;
import java.util.concurrent.CountDownLatch;

public class DataStoreModel {
    private EnumSet<EnumController> statusEnumSet = EnumSet.noneOf(EnumController.class);
    public String userName = "nam03test";
    public String userPassWord = "IUtrangmaimai02";
    public String notification = null;
    public CountDownLatch countDownLatch;
    private String countDownLatchName;
    public final int limitChar = 2000;
    public ArrayList<String> arrayList_Char = new ArrayList<>();
    public ArrayList<File> fileMergeArray = new ArrayList<>();
    public Map<String, String> params;
    public String DownloadsFilePath = "E:\\New folder\\";
    public String ImageCaptchaFilePath = "E:\\CongViecHocTap\\Captcha\\";
    public WebElement webElement;
    public WebDriverWait webDriverWait;
    public JavascriptExecutor javascriptExecutor;
    public List<WebElement> element_solve;
    public WebDriver driver;
    public ChromeOptions chromeOptions;
    public boolean flag;


    public DataStoreModel(int count, String countDownLatchName) {
        this.countDownLatch = new CountDownLatch(count);
        this.countDownLatchName = countDownLatchName;
    }

    public ArrayList<File> getFileMergeArray() {
        return fileMergeArray;
    }

    public void setFileMergeArray(ArrayList<File> fileMergeArray) {
        this.fileMergeArray = fileMergeArray;
    }

    public void countDown() {
        countDownLatch.countDown();
    }


    public String getCountDownLatchName() {
        return countDownLatchName;
    }

    public void setCountDownLatchName(String countDownLatchName) {
        this.countDownLatchName = countDownLatchName;
    }

    public String getImageCaptchaFilePath() {
        return ImageCaptchaFilePath;
    }

    public void setImageCaptchaFilePath(String imageCaptchaFilePath) {
        ImageCaptchaFilePath = imageCaptchaFilePath;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public void setStatusEnumSet(EnumSet<EnumController> statusEnumSet) {
        this.statusEnumSet = statusEnumSet;
    }

    public ChromeOptions getChromeOptions() {
        return chromeOptions;
    }

    public void setChromeOptions(ChromeOptions chromeOptions) {
        this.chromeOptions = chromeOptions;
    }

    public DataStoreModel() {
    }

    public EnumSet<EnumController> getStatusEnumSet() {
        return statusEnumSet;
    }

    public void addStatus(EnumController status) {
        statusEnumSet.add(status);
    }


    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }


    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public WebElement getWebElement() {
        return webElement;
    }

    public void setWebElement(WebElement webElement) {
        this.webElement = webElement;
    }

    public WebDriverWait getWebDriverWait() {
        return webDriverWait;
    }

    public void setWebDriverWait(WebDriverWait webDriverWait) {
        this.webDriverWait = webDriverWait;
    }

    public JavascriptExecutor getJavascriptExecutor() {
        return javascriptExecutor;
    }

    public void setJavascriptExecutor(JavascriptExecutor javascriptExecutor) {
        this.javascriptExecutor = javascriptExecutor;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public List<WebElement> getElement_solve() {
        return element_solve;
    }

    public void setElement_solve(List<WebElement> element_solve) {
        this.element_solve = element_solve;
    }


    public CountDownLatch getCountDownLatch() {
        return countDownLatch;
    }

    public void setCountDownLatch(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    public int getLimitChar() {
        return limitChar;
    }

    public ArrayList<String> getArrayList_Char() {
        return arrayList_Char;
    }

    public void setArrayList_Char(ArrayList<String> arrayList_Char) {
        this.arrayList_Char = arrayList_Char;
    }


    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public String getDownloadsFilePath() {
        return DownloadsFilePath;
    }

    public void setDownloadsFilePath(String downloadsFilePath) {
        DownloadsFilePath = downloadsFilePath;
    }

    public String getUserPassWord() {
        return userPassWord;
    }

    public void setUserPassWord(String userPassWord) {
        this.userPassWord = userPassWord;
    }
}

