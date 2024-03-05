package com.example.Selenium.SpeechToText.Controller;

import com.example.Selenium.SpeechToText.Model.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.CountDownLatch;


public abstract class InitializationDriverController extends BotTelegramActionController implements Runnable, ChangeFileNameInterfaceController {
    public static int count = 0;
    public WebDriver driver;
    public String textFromTextColumnCsvFile;
    public CSVStoreModel textCsvStoreModel;
    public CSVStoreModel voiceCsvStoreModel;
    public CSVStoreModel fileNameCsvStoreModel;
    private CountDownLatch countDownLatch;


    public InitializationDriverController(TelegramDataStoreModel telegramDataStoreModel, DataStoreModel dataStoreModel, WebDriver driver, String textFromTextColumnCsvFile, CSVStoreModel textCsvStoreModel, CSVStoreModel voiceCsvStoreModel, CSVStoreModel fileNameCsvStoreModel) {
        super(telegramDataStoreModel, dataStoreModel);
        this.driver = driver;
        this.textFromTextColumnCsvFile = textFromTextColumnCsvFile;
        this.textCsvStoreModel = textCsvStoreModel;
        this.voiceCsvStoreModel = voiceCsvStoreModel;
        this.fileNameCsvStoreModel = fileNameCsvStoreModel;
    }

    public void printParams() {
        System.out.println("Params text : " + dataStoreModel.getParams().get("Text") + "\n" + "Params voice : " + dataStoreModel.getParams().get("Voice") + "\n" + "Params file name : " + dataStoreModel.getParams().get("FileName"));
    }

    public void initializationDriver() {

        printParams();

        System.setProperty("webdriver.http.factory", "jdk-http-client");
        System.setProperty("webdriver.chrome.driver", "E:\\CongViecHocTap\\ChromeDriver\\chromedriver-win64\\chromedriver.exe");

        dataStoreModel.setChromeOptions(new ChromeOptions());
        dataStoreModel.getChromeOptions().setExperimentalOption("useAutomationExtension", false); // Disable chrome running as automation
        dataStoreModel.getChromeOptions().setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation")); // Disable chrome running as automation

        driver = new ChromeDriver(dataStoreModel.getChromeOptions());
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2)); // The number of seconds that a driver waits to load an element without the wait setting
        driver.manage().window().maximize();
    }

    public void configureDriverOnTheLogin() throws TelegramApiException, InterruptedException {

        driver.get("https://ttsfree.com/login");

        ((JavascriptExecutor) driver).executeScript("var images = document.getElementsByTagName('img'); for(var i = 0; i < images.length; i++) { images[i].setAttribute('hidden', 'true'); }");

        driver.findElement(By.xpath("//input[@placeholder='Username']")).sendKeys(dataStoreModel.getUserName());
        driver.findElement(By.xpath("//input[@placeholder='Enter password']")).sendKeys(dataStoreModel.getUserPassWord());

        countDownLatch = new CountDownLatch(2);
        Thread threadCheckEscAd = new Thread(new CheckEscAdController(driver, null, countDownLatch));
        Thread threadCheckHandAD = new Thread(new CheckHandAdController(driver, null, countDownLatch));
        threadCheckEscAd.start();
        threadCheckHandAD.start();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        driver.findElement(By.xpath("//ins[@class='iCheck-helper']")).click();
        driver.findElement(By.xpath("//input[@id='btnLogin']")).click();

        try {
            dataStoreModel.setWebDriverWait(new WebDriverWait(driver, Duration.ofSeconds(2)));
            dataStoreModel.getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("laptopaz.com"))).click();
        } catch (Exception exception) {
            ConnectionTransferBetweenloginAndHomePage();
        }
    }

    public void ConnectionTransferBetweenloginAndHomePage() throws TelegramApiException, InterruptedException {
        if (textCsvStoreModel.isFlag()) {
            configureActionDriverOnTheWebsite();
        } else if (!textCsvStoreModel.isFlag()) {
            for (int i = 0; i <= dataStoreModel.getArrayList_Char().size() + 1; i++) {
                System.out.println("size of arrayList_Char : " + dataStoreModel.getArrayList_Char().size());
                System.out.println("value of arraylist[index] : " + dataStoreModel.getArrayList_Char().get(0));
                configureActionDriverOnTheWebsite();
                //count += 1;
                dataStoreModel.getArrayList_Char().remove(0);
                if (dataStoreModel.getArrayList_Char().size() == 0) {
                    dataStoreModel.addStatus(EnumController.DOWNLOAD_SUCCESSFULLY);
                    driver.close();
                    dataStoreModel.getCountDownLatch().countDown();
                }
            }
        }
    }

    public void configureActionDriverOnTheWebsite() throws InterruptedException, TelegramApiException {

        driver.get("https://ttsfree.com/vn");

        ((JavascriptExecutor) driver).executeScript("var images = document.getElementsByTagName('img'); for(var i = 0; i < images.length; i++) { images[i].setAttribute('hidden', 'true'); }");
        ((JavascriptExecutor) driver).executeScript("var images = document.querySelectorAll('img[id=captcha_image]'); for(var i = 0; i < images.length; i++) { if(images[i].src.startsWith('https://ttsfree.com/voice/captcha.php?sign=?')) { images[i].removeAttribute('hidden'); } }");

        dataStoreModel.setJavascriptExecutor((JavascriptExecutor) driver);
        dataStoreModel.setWebElement(driver.findElement(By.xpath("//*[@id=\"input_text\"]")));

        dataStoreModel.getJavascriptExecutor().executeScript("arguments[0].scrollIntoView();", dataStoreModel.getWebElement());

        countDownLatch = new CountDownLatch(1);

        Thread threadCheckAdTop = new Thread(new CheckTopAdController(driver, countDownLatch));
        threadCheckAdTop.start();
        countDownLatch.await();

        Thread threadCheckEscAd = new Thread(new CheckEscAdController(driver, countDownLatch));
        threadCheckEscAd.start();
        countDownLatch.await();

        Thread threadCheckHandAd = new Thread(new CheckHandAdController(driver, countDownLatch));
        threadCheckHandAd.start();
        countDownLatch.await();

        Thread threadCheckHostAD = new Thread(new CheckHostAdController(driver, countDownLatch));
        threadCheckHostAD.start();
        countDownLatch.await();

        if (textCsvStoreModel.isFlag()) {
            System.out.println("true");
            System.out.println(dataStoreModel.getParams().get("Text"));
            driver.findElement(By.xpath("//textarea[@id='input_text']")).sendKeys(dataStoreModel.getParams().get("Text"));
        } else if (!textCsvStoreModel.isFlag()) {
            driver.findElement(By.xpath("//textarea[@id='input_text']")).sendKeys(dataStoreModel.getArrayList_Char().get(0));
        }

        threadCheckHandAd = new Thread(new CheckHandAdController(driver, countDownLatch));
        threadCheckHandAd.start();
        countDownLatch.await();

        if (driver.equals("https://ttsfree.com/vn#google_vignette")) {
            driver.navigate().back();
            driver.findElement(By.xpath("//a[normalize-space()='TTS Server 2']")).click();
        }

        if (dataStoreModel.getParams().get("Voice").equals("Female")) {
            driver.findElement(By.xpath("(//label[@for='radioPrimaryvi-VN'])[1]")).click();
        } else if (dataStoreModel.getParams().get("Voice").equals("Male")) {
            driver.findElement(By.xpath("(//label[@for='radioPrimaryvi-VN2'])[1]")).click();
        }
        driver.findElement(By.xpath("//a[@class='btn mb-2 lg action-1 text-white convert-now']")).click();

        checkAndProcessCaptcha();

        if (dataStoreModel.isFlag()) {
            dataStoreModel.setFlag(false);
            System.out.println("flag == true");
            try {
                dataStoreModel.setWebDriverWait(new WebDriverWait(driver, Duration.ofSeconds(5)));
                dataStoreModel.getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("laptopaz.com"))).click();
            } catch (Exception exception) {

            }
        } else {
        }

        threadCheckHandAd = new Thread(new CheckHandAdController(driver, countDownLatch));
        threadCheckHandAd.start();
        countDownLatch.await();

        dataStoreModel.setWebElement(driver.findElement(By.xpath("//*[@id=\"input_text\"]")));
        dataStoreModel.getJavascriptExecutor().executeScript("arguments[0].scrollIntoView();", dataStoreModel.getWebElement());

        dataStoreModel.setWebDriverWait(new WebDriverWait(driver, Duration.ofSeconds(120)));
        dataStoreModel.getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"progessResults\"]/div[2]/center[1]/div/a"))).click();

        countDownLatch = new CountDownLatch(2);

        threadCheckHostAD = new Thread(new CheckHostAdController(driver, countDownLatch));
        threadCheckEscAd = new Thread(new CheckEscAdController(driver, countDownLatch));
        threadCheckHostAD.start();
        threadCheckEscAd.start();

        countDownLatch.await();

        if (dataStoreModel.getStatusEnumSet().contains(EnumController.MULTI_TAB)) {

        }

        if (dataStoreModel.getStatusEnumSet().contains(EnumController.ONE_DRIVERS) || dataStoreModel.getStatusEnumSet().contains(EnumController.TWO_DRIVERS)) {
            dataStoreModel.addStatus(EnumController.DOWNLOAD_SUCCESSFULLY);
            driver.close();
            dataStoreModel.getCountDownLatch().countDown();

        }
        count += 1;
    }

    public void checkAndProcessCaptcha() throws InterruptedException, TelegramApiException {

        dataStoreModel.setFlag(false);

        try {

            dataStoreModel.setWebDriverWait(new WebDriverWait(driver, Duration.ofSeconds(10)));
            dataStoreModel.getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='alert alert-danger alert-dismissable']"))).isDisplayed();
            System.out.println("displayed captcha");

            dataStoreModel.setJavascriptExecutor((JavascriptExecutor) driver);
            dataStoreModel.setWebElement(driver.findElement(By.xpath("(//a[normalize-space()='Confirm'])[1]")));
            dataStoreModel.getJavascriptExecutor().executeScript("arguments[0].scrollIntoView();", dataStoreModel.getWebElement());

            getCaptChaImage(driver);
            saveImageCaptcha();
            sendPhoto();

            int zero = telegramDataStoreModel.getCountDownDuration(); // 30 seconds to count down

            for (int second = 0; second <= telegramDataStoreModel.getCountDownDuration(); second++) {
                zero = telegramDataStoreModel.getCountDownDuration() - second;
                System.out.println(zero);

                Thread.sleep(1000);

                Thread threadCheckHandAd = new Thread(new CheckHandAdController(driver, countDownLatch));
                threadCheckHandAd.start();
                countDownLatch.await();

                if (telegramDataStoreModel.getTextFromUserTelegram() != null) {
                    System.out.println("Text : " + telegramDataStoreModel.getTextFromUserTelegram());
                    for (int i = 0; i < telegramDataStoreModel.getTextFromUserTelegram().length(); i++) {
                        if (!Character.isDigit(telegramDataStoreModel.getTextFromUserTelegram().charAt(i))) {
                            telegramDataStoreModel.getMessage().setText("Value of text has char");
                            execute(telegramDataStoreModel.getMessage());
                            telegramDataStoreModel.setTextFromUserTelegram(null);
                            break;
                        }
                    }
                    if (telegramDataStoreModel.getTextFromUserTelegram() == null || telegramDataStoreModel.getTextFromUserTelegram().isEmpty() || telegramDataStoreModel.getTextFromUserTelegram().length() <= 3) {
                        if (telegramDataStoreModel.getTextFromUserTelegram() == null) {
                        } else {
                            telegramDataStoreModel.getMessage().setText("Text length must be 4 numbers or more");
                            execute(telegramDataStoreModel.getMessage());
                        }
                        telegramDataStoreModel.setTextFromUserTelegram(null);
                        continue;
                    }

                    driver.findElement(By.xpath("(//input[@id='captcha_input'])[1]")).sendKeys(telegramDataStoreModel.getTextFromUserTelegram());
                    dataStoreModel.setElement_solve(driver.findElements(By.xpath("(//img[@title='Ad.Plus Advertising'])[1]")));
                    if (dataStoreModel.getElement_solve().size() > 0 && dataStoreModel.getElement_solve().get(0).isDisplayed()) {
                        driver.findElement(By.xpath("(//img[@title='Ad.Plus Advertising'])[1]")).click();
                    }
                    driver.findElement(By.xpath("(//a[normalize-space()='Confirm'])[1]")).click();
                    try {
                        dataStoreModel.setWebDriverWait(new WebDriverWait(driver, Duration.ofSeconds(10)));
                        dataStoreModel.getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//h4[normalize-space()='Error!'])[1]")));
                        telegramDataStoreModel.getMessage().setText("Wrong number of captcha image , type again !");
                        execute(telegramDataStoreModel.getMessage());
                        telegramDataStoreModel.setTextFromUserTelegram(null);
                        getCaptChaImage(driver);
                        saveImageCaptcha();
                        sendPhoto();
                        continue;

                    } catch (Exception exception) {
                        dataStoreModel.setFlag(true);
                        telegramDataStoreModel.setTextFromUserTelegram(null);
                        telegramDataStoreModel.getMessage().setText("Valid captcha code");
                        execute(telegramDataStoreModel.getMessage());

                        threadCheckHandAd = new Thread(new CheckHandAdController(driver, countDownLatch));
                        threadCheckHandAd.start();
                        countDownLatch.await();

                        dataStoreModel.setWebDriverWait(new WebDriverWait(driver, Duration.ofSeconds(10)));

                        dataStoreModel.getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Tạo Voice')]"))).click();

                        break;
                    }
                }
                System.out.println("---------------------");
            }
            System.out.println("count here : " + zero);
            if (zero == 0) {
                telegramDataStoreModel.getMessage().setText("End of time to solove captcha");
                execute(telegramDataStoreModel.getMessage());
            }
        } catch (Exception exception) {
            System.out.println("Non display captcha");
        }
    }

    @Override
    public void run() {

        dataStoreModel.setParams(new HashMap<>());

        if (textCsvStoreModel.isFlag()) {
            dataStoreModel.getParams().put("Text", textFromTextColumnCsvFile);
        } else if (!textCsvStoreModel.isFlag()) {
            dataStoreModel.getParams().put("Text", "");
        }

        dataStoreModel.getParams().put("Voice", voiceCsvStoreModel.getReadTextOfColumn());
        dataStoreModel.getParams().put("FileName", fileNameCsvStoreModel.getReadTextOfColumn());

        initializationDriver();

        try {

            configureDriverOnTheLogin();
            configureActionDriverOnTheWebsite();

        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}


