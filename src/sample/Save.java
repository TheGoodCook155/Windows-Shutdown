package sample;

import javafx.concurrent.Task;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

public class Save<String> extends Task<Void> {

    private String fileName;
    private String input;


    public Save(String fileName, String input) throws IOException {
        this.fileName = fileName;
        this.input = input;


    }

    @Override
    protected Void call() throws Exception {
        System.out.println("in save class");
        BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\" + this.fileName + ".txt"));
        writer.write(input.toString());
        writer.flush();
        writer.close();
        return null;
    }
}
