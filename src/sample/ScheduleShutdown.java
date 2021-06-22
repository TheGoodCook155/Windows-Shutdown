package sample;

import javafx.concurrent.Task;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ScheduleShutdown extends Task<Void> {

    private String scheduledShutDownTime;


    public ScheduleShutdown(String scheduledShutDownTime) {
        this.scheduledShutDownTime = scheduledShutDownTime;
    }

    @Override
    protected Void call() throws Exception {
//        System.out.println("'in call ()");

        String currentTime;

        Date date = Calendar.getInstance().getTime();
        DateFormat formater = new SimpleDateFormat("HHmm");
        //current time
        currentTime = formater.format(date).substring(0, 2) + ":" + formater.format(date).substring(2);

        System.out.println("Current time is " + currentTime);
        System.out.println("Saved time is " + this.scheduledShutDownTime);
        int currentTimeInt = (Integer.parseInt(formater.format(date).substring(0, 2)) * 60) + Integer.parseInt(formater.format(date).substring(2));
        int currentTimeIntSec = currentTimeInt * 60;

        int savedTimeInt = (Integer.parseInt(scheduledShutDownTime.substring(0, 2)) * 60) + Integer.parseInt(scheduledShutDownTime.substring(3));
        int savedTimeIntSec = savedTimeInt * 60;

        System.out.println("Current time in SEC is " + currentTimeIntSec + " saved time in SEC is " + savedTimeIntSec);



        int res = savedTimeIntSec - currentTimeIntSec;

        if (res > 0) {
            System.out.println("REs > 0");
            System.out.println("Restart is today in " + res);
            res = res;


        }
        if (res < 0) {
            System.out.println("Res is < 0");
            res = (24 * 60 * 60) - (currentTimeIntSec - savedTimeIntSec);
            System.out.println("Restart tomorrow in " + res);

        }
        if (res == 0) {
            res = 24 * 60 * 60;
        }

        System.out.println("Res is " + res + " restarting in " + res + " seconds");
        Runtime runtime = Runtime.getRuntime();
        Process proc = runtime.exec("shutdown -s -t " + res);
//        System.exit(0);

        return null;
    }


}
