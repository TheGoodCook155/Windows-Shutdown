package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.IOException;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;


public class Controller {

    private AtomicReference<String> input;
    private String scheduledShutDownTime;


    @FXML
    private TextField textFieldInput;


    @FXML
    private Button enterTimeButton;

    @FXML
    private Label errorField;



    public void initialize() throws Exception {
        Load load = new Load();
        new Thread(load).start();
        String readFileStr = load.call();
        if(readFileStr != null) {
            errorField.setText("Scheduled shutdown at: " + readFileStr);
        }
//        System.out.println("Return load string is " + readFileStr);
        ScheduleShutdown shutdown = new ScheduleShutdown(readFileStr);
//        System.out.println("In Initialize -- shutdown class started");
        new Thread(shutdown).start();


        if (textFieldInput.getText() == null || textFieldInput.getText().isBlank()) {
            enterTimeButton.setDisable(true);
        }

    }

    public void enableButton() {
        textFieldInput.setOnKeyReleased(e -> {
            if (!e.getText().isEmpty() || !e.getText().isBlank()) {
                enterTimeButton.setDisable(false);
            } else {
                enterTimeButton.setDisable(true);
                checkInputString();
            }
        });
    }

    public void disableSaveIfInvalid() {
        if (!input.get().isEmpty() || !input.get().isBlank()) {
            if (input.get().matches("^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$")) {

                errorField.setText("Valid!");
                enterTimeButton.setDisable(false);

            } else {

                errorField.setText("Incorrect input");
                enterTimeButton.setDisable(true);
            }
        } else {
            errorField.setText("Enter valid time!");
        }
    }

    public void checkInputString() {
        input = new AtomicReference<>(textFieldInput.getText());
        textFieldInput.setOnKeyReleased(e -> {
            textFieldInput.setVisible(true);
            if (input.get().matches("^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$")) {

                errorField.setText("Valid!");
                enterTimeButton.setDisable(false);

            } else {

                errorField.setText("Incorrect input");
                enterTimeButton.setDisable(true);
            }
        });

    }


    public void saveFile() throws Exception {
        Save save = new Save("shutdowntime", input.get());
        new Thread(save).start();
        scheduledShutDownTime = input.get().substring(0, 2) + input.get().substring(2);
        enterTimeButton.setDisable(true);
        save.setOnSucceeded(e -> {
            errorField.setText("Saved! You can quit now.");

        });
        scheduleShutdown();

    }


    public void scheduleShutdown() throws Exception {
        ScheduleShutdown shutdown = new ScheduleShutdown(scheduledShutDownTime);
        new Thread().start();
        shutdown.call();

    }
}
