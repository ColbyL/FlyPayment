package com.ahellhound.bukkit.flypayment;

import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

public class Utilities {

    public String convertIntToString(int intToConvert) {

        // Converts primitive int to Integer
        Integer convertedInteger = new Integer(intToConvert);
        // Converts Integer to String
        String convertedIntegerString = convertedInteger.toString();

        return convertedIntegerString;
    }

    public String convertDoubleToString(double doubleToConvert) {

        // Converts primitive double to Double
        Double convertedDouble = new Double(doubleToConvert);
        // Convert Double to String
        String convertedDoubleString = convertedDouble.toString();

        return convertedDoubleString;
    }

    public String convertTicksToMinutes(double ticksTimer) {
        // Converts ticks to seconds
        double convertedSeconds = (ticksTimer / 20);
        // Decimal is placed to 2 decimal places
        DecimalFormat df = new DecimalFormat("#.00");
        // Sees if converted ticks is greater or equal to 60
        if (convertedSeconds >= 60) {
            // Converts ticks to minutes
            double convertedMinutes = (convertedSeconds / 60);
            // Converts double to string
            String convertedMinutesString = df.format(convertedMinutes);
            // returns string in minutes
            return convertedMinutesString;
        }

        // Applies decimal format to convertedSecondsDouble
        String convertedTicksString = df.format(convertedSeconds);
        // returns string thats less than 60
        return convertedTicksString;
    }

    public String convertMinutesOrSeconds(double timeTicks) {
        // Messages constructor
        Messages Messages = new Messages();
        // Determines if time is greater than or less than a minute
        if (timeTicks < 1200) {

            // Gets seconds message
            String secondsConverted = Messages.secondsMessage();

            return secondsConverted;
        }

        // Gets minutes message
        String minutesConverted = Messages.minutesMessage();
        // Returns minutes message
        return minutesConverted;
    }

    //Give time in mili, returns formatted time
    public String getConvertedMiliTime(Player p) {
        Scheduler Scheduler = new Scheduler();
        //retrieves time player started flying
        long playerStartTime = Scheduler.getSchedulerStartTime(p);
        //Gets time left in mili seconds

        long playerTimeLeftMili = (playerStartTime - System.currentTimeMillis());
        //formats string
        String playerTimeString = String.format("%d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes(playerTimeLeftMili),
                TimeUnit.MILLISECONDS.toSeconds(playerTimeLeftMili) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(playerTimeLeftMili)));

        //returns string
        return playerTimeString;

    }

    public boolean getTimerIsZero(Player p) {
        //configuration constructor
        Configuration Configuration = new Configuration();
        //gets player's tier
        int tier = Configuration.getTier(p);
        //Checks if configuration has 0 as timer
        if (Configuration.getTimerAmount(tier) == 0) {

            return true;
        }


        return false;
    }

    public static long convertTimeFormatToNano(String formattedTime) {
        //converts formattedTime to nano
        //TODO Change to actual conversion
        long nanoTime = 0L;

        //returns nanoTie
        return nanoTime;
    }


}
