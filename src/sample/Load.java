package sample;

import javafx.concurrent.Task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Load extends Task<String> {

    private String time;



    @Override
    protected String call() throws Exception {
        Path path = Paths.get("C:\\Users\\shutdowntime.txt");
        boolean fileExists = Files.exists(path);
        System.out.println("File Exists is " + fileExists);


        if(fileExists == true) {
            BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\shutdowntime.txt"));

            time = reader.readLine();
            System.out.println("Read time in READER is " + time);


            reader.close();
        }else{

        }


        return time;
    }
}
