package com.example.android.c196tracker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class InputChecker {

    public static String checkNewItemInput(boolean isTerm, String name, String start, String end) {
        String errorMessage = "";
        if (name.length() == 0) {
            if (isTerm) {
                errorMessage += "Please enter a term name.\n";
            } else {
                errorMessage += "Please enter a course name.\n";
            }
        }
        if (start.equals("select date")) {
            errorMessage += "Please select a start date.\n";
        }
        if (end.equals("select date")) {
            errorMessage += "Please select an end date.\n";
        }
        if (!isValidCompareStartAndEndDates(start, end)) {
            errorMessage += "The start date must be before the end date.\n";
        }
        return errorMessage;
    }

    public static boolean isValidCompareStartAndEndDates(String startString, String endString) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
            Date startDate = simpleDateFormat.parse(startString);
            Date endDate = simpleDateFormat.parse(endString);

            return startDate.compareTo(endDate) < 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // TODO implement this method
    public static String isValidCourseMentor(String mentorName, String mentorEmail, String mentorPhone) {
        String errorMessage = "";
        return errorMessage;
    }
}

