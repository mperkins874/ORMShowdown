package realmdemo.perkins.com.realmdemo.config;


import java.util.Date;
import java.util.Random;

/**
 * Created by matt on 6/14/16.
 */
public class Constants {

    public static String generateRandomString(int length){

        String returnValue = "";
        String availableChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Date now = new Date();
        Random random = new Random(now.getTime());

        for(int i = 0; i < length; i++) {
            returnValue += availableChars.charAt(random.nextInt(availableChars.length()));
            random = new Random(random.nextInt());
        }

        return returnValue;
    }

}
