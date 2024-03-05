package com.example.Selenium.SpeechToText.Controller;

import com.example.Selenium.SpeechToText.Model.*;
import org.openqa.selenium.WebDriver;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.concurrent.CountDownLatch;

public class AllProcessController {

    public static DataStoreModel dataStoreModel; // set static ( further research )
    CSVStoreModel voiceCSVModel;
    CSVStoreModel fileNameCSVModel;
    CSVStoreModel textCSVModel;
    TelegramDataStoreModel telegramDataStoreModel;
    protected final String columnName1 = "Text";
    protected final String columnName2 = "Voice";
    protected final String columnName3 = "FileName";
    EnumController statusCheckRegisteredTelegramBot = null;

    public void work() throws InterruptedException, TelegramApiException {

        textCSVModel = new CSVStoreModel();
        voiceCSVModel = new CSVStoreModel();
        fileNameCSVModel = new CSVStoreModel();

        dataStoreModel = new DataStoreModel();

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId("1159534870");
        telegramDataStoreModel = new TelegramDataStoreModel("CaptchaSlove_bot", "6928830332:AAGmv3fN_k8YdITzJeOyjqtsDQfWuviF308", sendMessage);

        dataStoreModel.addStatus(EnumController.APPLICATION_STATUS_OK);

        GetDataCSVModel getDataCSVModel = new GetDataCSVModel(voiceCSVModel, fileNameCSVModel, textCSVModel, dataStoreModel);

        getDataCSVModel.getDataFromColumn(columnName1, columnName2, columnName3);

        getDataCSVModel.getNotificationErrorCSV(voiceCSVModel, fileNameCSVModel, dataStoreModel);

        if (dataStoreModel.getStatusEnumSet().contains(EnumController.ERROR_IN_CSV)) {
            System.out.println(dataStoreModel.getNotification());
            return;
        }

        GetChunksToArrayList getChunksToArrayListClass = new GetChunksToArrayList();
        getChunksToArrayListClass.getChunksToArrayList(dataStoreModel.getArrayList_Char(), textCSVModel.getReadTextOfColumn(), dataStoreModel.getLimitChar());

        WebDriver driver1 = null;

        if (textCSVModel.isFlag()) {

            if (dataStoreModel.getArrayList_Char().size() == 1) {

                dataStoreModel.addStatus(EnumController.ONE_DRIVERS);

                dataStoreModel.setCountDownLatch(new CountDownLatch(1));

                DataStoreModel dataStoreModelForNewDriver01 = new DataStoreModel();
                dataStoreModelForNewDriver01.setStatusEnumSet(dataStoreModel.getStatusEnumSet());

                LessOrEqual4000CharController lessOrEqual4000CharControllerForThread1 = new LessOrEqual4000CharController(dataStoreModel.getCountDownLatch(), telegramDataStoreModel, dataStoreModelForNewDriver01, driver1, dataStoreModel.getArrayList_Char().get(0), textCSVModel, voiceCSVModel, fileNameCSVModel);

                setStatusCheckRegisteredTelegramBot(lessOrEqual4000CharControllerForThread1);

                Thread thread01 = new Thread(lessOrEqual4000CharControllerForThread1);
                thread01.start();

                dataStoreModel.getCountDownLatch().await();

                lessOrEqual4000CharControllerForThread1.changeFileName(dataStoreModelForNewDriver01.getParams());

                return;

            } else if (dataStoreModel.getArrayList_Char().size() >= 2) {

                dataStoreModel.addStatus(EnumController.TWO_DRIVERS);

                WebDriver driver2 = null;

                dataStoreModel.setCountDownLatch(new CountDownLatch(2));

                DataStoreModel dataStoreModelForNewDriver01 = new DataStoreModel();
                DataStoreModel dataStoreModelForNewDriver02 = new DataStoreModel();
                dataStoreModelForNewDriver01.setStatusEnumSet(dataStoreModel.getStatusEnumSet());
                dataStoreModelForNewDriver02.setStatusEnumSet(dataStoreModel.getStatusEnumSet());

                LessOrEqual4000CharController lessOrEqual4000CharControllerForThread1 = new LessOrEqual4000CharController(dataStoreModel.getCountDownLatch(), telegramDataStoreModel, dataStoreModelForNewDriver01, driver1, dataStoreModel.getArrayList_Char().get(0), textCSVModel, voiceCSVModel, fileNameCSVModel);
                LessOrEqual4000CharController lessOrEqual4000CharControllerForThread2 = new LessOrEqual4000CharController(dataStoreModel.getCountDownLatch(), telegramDataStoreModel, dataStoreModelForNewDriver02, driver2, dataStoreModel.getArrayList_Char().get(1), textCSVModel, voiceCSVModel, fileNameCSVModel);

                setStatusCheckRegisteredTelegramBot(lessOrEqual4000CharControllerForThread1);

                Thread thread01 = new Thread(lessOrEqual4000CharControllerForThread1);
                Thread thread02 = new Thread(lessOrEqual4000CharControllerForThread2);

                thread01.start();
                thread02.start();

                dataStoreModel.getCountDownLatch().await();

                lessOrEqual4000CharControllerForThread2.changeFileName(dataStoreModelForNewDriver02.getParams());

                return;
            }
        }


        if (!textCSVModel.isFlag()) {

            dataStoreModel.addStatus(EnumController.MULTI_TAB);

            dataStoreModel.setCountDownLatch(new CountDownLatch(1));

            MoreOrEqual4001CharController moreOrEqual4001CharController = new MoreOrEqual4001CharController(dataStoreModel.getCountDownLatch(), telegramDataStoreModel, dataStoreModel, driver1, dataStoreModel.getArrayList_Char().toString(), textCSVModel, voiceCSVModel, fileNameCSVModel);

            setStatusCheckRegisteredTelegramBot(moreOrEqual4001CharController);

            Thread thread1 = new Thread(moreOrEqual4001CharController);

            thread1.start();

            dataStoreModel.getCountDownLatch().await();

            moreOrEqual4001CharController.changeFileName();

        }
    }


    //Overloading for LessOrEqual4000CharController
    public void setStatusCheckRegisteredTelegramBot(LessOrEqual4000CharController lessOrEqual4000CharController) throws TelegramApiException {
        if (statusCheckRegisteredTelegramBot != EnumController.REGISTERED_TELEGRAM_BOT) {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(lessOrEqual4000CharController);
        }
        statusCheckRegisteredTelegramBot = EnumController.REGISTERED_TELEGRAM_BOT;
    }

    //Overloading for MoreOrEqual4001CharController
    public void setStatusCheckRegisteredTelegramBot(MoreOrEqual4001CharController moreOrEqual4001CharController) throws TelegramApiException {
        if (statusCheckRegisteredTelegramBot != EnumController.REGISTERED_TELEGRAM_BOT) {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(moreOrEqual4001CharController);
        }
        statusCheckRegisteredTelegramBot = EnumController.REGISTERED_TELEGRAM_BOT;
    }
}

