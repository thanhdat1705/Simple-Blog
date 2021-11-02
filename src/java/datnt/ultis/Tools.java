/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datnt.ultis;

import datnt.mailValidCode.MailValidCode;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author NTD
 */
public class Tools {

    public static Integer pageSize = 5;
    public static Integer pageIndex = 1;

    public static String EncodeMail(String email) {
        if (email != null) {
            email = email.replaceAll("@", "Ged2uMA423232IL29d");
            email = email.replaceAll("\\.", "364828482dot9443");
            email = email.replaceAll("\\-", "nfow47fivnmfl3n4");
            email = email.replaceAll("\\_", "923yr78dnc4nb53m");
            return email;
        }
        return "";
    }

    public static String DecodeMail(String email) {
        if (email != null) {
            email = email.replaceAll("Ged2uMA423232IL29d", "@");
            email = email.replaceAll("364828482dot9443", "\\.");
            email = email.replaceAll("nfow47fivnmfl3n4", "\\-");
            email = email.replaceAll("923yr78dnc4nb53m", "\\_");
            return email;
        }
        return "";
    }

    public static String TimeFormat(Timestamp stamp) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(stamp);
    }

    public static Timestamp toDay() {
        Date date = new Date();
        long time = date.getTime();
        Timestamp ts = new Timestamp(time);
        return ts;
    }

    public static String getTime(Timestamp stamp) {
        String dateString = stamp.toString();
        String dateSub[] = dateString.split(" ");
        String date = dateSub[0];
        String time = dateSub[1];

        String timeFormat[] = time.split(":");
        String hour = timeFormat[0];
        String min = timeFormat[1];
        String secformat[] = timeFormat[2].split("\\.");

        String result = hour + ":" + min;
        return result;
    }

    public static Timestamp convertStringToTimestamp(String date) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date parsedDate = formatter.parse(date);
        Timestamp ts = new Timestamp(parsedDate.getTime());
        return ts;
    }

    public static float roundTo2Decimal(float number) {
        BigDecimal numberBigDecimal = new BigDecimal(Float.toString(number));
        numberBigDecimal = numberBigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
        return numberBigDecimal.floatValue();
    }

    public static String encryptPass(String base) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(base.getBytes("UTF-8"));
        StringBuffer hexString = new StringBuffer();

        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }

    public static String resourceFormat(String url) {
        if (url.contains(";jsessionid")) {
            String subStr[] = url.split(";jsessionid");
            return subStr[0];
        } else {
            return url;
        }
    }

    public static String detectLineBreak(String text) {
        String newText = "";
        String[] lines = text.split("\r\n|\n|\r");
        for (String line : lines) {
            newText += line + "CHAR(13)";
        }
        return newText;
    }

    static String alphaBet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
            + "0123456789"
            + "abcdefghijklmnopqrstuvxyz";

    public static String activeAcountCode(String email) {
        StringBuilder s = new StringBuilder(6);
        int y;
        for (y = 0; y < 6; y++) {
            int code = (int) (alphaBet.length() * Math.random());
            s.append(alphaBet.charAt(code));
        }
        MailValidCode.sendEmail(s.toString(), email.trim());
        return s.toString();
    }
}
