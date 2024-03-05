package com.example.Selenium.SpeechToText.View;

import com.example.Selenium.SpeechToText.Controller.AllProcessController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static com.example.Selenium.SpeechToText.Controller.AllProcessController.dataStoreModel;

@RestController
@RequestMapping("/api/web")
public class ResponseView {
    String response = null;

    private AllProcessController allProcessController;

    public ResponseView() {
        this.allProcessController = new AllProcessController();
    }

    @GetMapping("/SpeechToTextAPI")
    public ResponseEntity<?> SpeechToText() {
        try {

            allProcessController.work();

            response = dataStoreModel.getStatusEnumSet().toString() + "\n"
                    + dataStoreModel.getNotification();


        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok(new String(response));
    }
}

