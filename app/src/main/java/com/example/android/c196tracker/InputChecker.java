package com.example.android.c196tracker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class InputChecker {

    // input type will be 1 for Term, 2 for Course, 3 for Assessment
    public static String checkItemName(int inputType, String name) {
        String errorMessage = "";
        if (name.length() == 0) {
            switch (inputType) {
                case 1:
                    errorMessage += "Please enter a term name.\n";
                    break;
                case 2:
                    errorMessage += "Please enter a course name.\n";
                    break;
                case 3:
                    errorMessage += "Please enter an assessment name.\n";
                    break;
            }
        }
        return errorMessage;
    }

    public static String checkStartAndEndDates(String start, String end) {
        String errorMessage = "";
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

    public static String checkAssessmentDate(String assessmentDate) {
        // TODO implement this method
        String errorMessage = "";

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

