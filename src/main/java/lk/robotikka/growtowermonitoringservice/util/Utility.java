package lk.robotikka.growtowermonitoringservice.util;

import com.google.gson.Gson;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {

    public static String objectToJson(Object object) {

        return new Gson().toJson(object);
    }

    public static LocalDateTime getSystemDate() {
        return LocalDateTime.now();
    }

    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] hashBytes = digest.digest(password.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String reverseSHAwithSalt(String saltedValue) {
        String originalValue = saltedValue;
        ArrayList<Integer> positions = new ArrayList<>();
        ArrayList<Character> chars = new ArrayList<>();

        Pattern p = Pattern.compile("[*#.]");
        Matcher m = p.matcher(originalValue);

        while (m.find()) {
            positions.add(m.start());
            chars.add(originalValue.charAt(m.start()));
        }

        for (int i = 0; i < positions.size(); i++) {
            originalValue = originalValue.substring(0, positions.get(positions.size() - 1 - i)) + chars.get(i) + originalValue.substring(positions.get(positions.size() - 1 - i));
        }

        return originalValue;
    }

    public static String getSHAwithSalt(String value, int numberOfSaltChars) {

        ArrayList<Integer> positions = new ArrayList<>();
        String[] chars = {"*", "#", "."};
        SecureRandom r = new SecureRandom();

        try {

            for (int i = 0; i < numberOfSaltChars; i++) {
                int temp = r.nextInt(value.length() - 1);

                if (!positions.contains(temp)) {
                    positions.add(temp);
                }
            }

            for (int position : positions) {

                if (position == 0) {
                    value = chars[r.nextInt(3)] + value;
                } else {
                    String temp = value.substring(0, position + 1);
                    value = temp + chars[r.nextInt(3)] + value.substring(position + 1);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();

        }

        return value;
    }
}
