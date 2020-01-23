package com.example.android.c196tracker.Resources;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class InputChecker {

    private static final String emailRegex = "^[-a-z0-9~!$%^&*_=+}{'?]+(\\.[-a-z0-9~!$%^&*_=+}{'?]+)*@([a-z0-9_][-a-z0-9_]*(\\.[-a-z0-9_]+)*\\.(aero|arpa|biz|com|coop|edu|gov|info|int|mil|museum|name|net|org|pro|travel|mobi|[a-z][a-z])|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,5})?$";
    private static final String phoneRegex = "^(\\d{10})|(([(]?([0-9]{3})[)]?)?[ .\\-]?([0-9]{3})" +
            "[ .\\-]([0-9]{4}))$";

    // input type will be 1 for Term, 2 for Course, 3 for Assessment
    public static String checkItemNameExists(int inputType, String name) {
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

    public static String getDateErrorMessage(String start, String end) {
        String errorMessage = "";
        if (start.equals("select date")) {
            errorMessage += "Please select a start date.\n";
        }
        if (end.equals("select date")) {
            errorMessage += "Please select an end date.\n";
        }
        if (!isStartDateBeforeEndDate(start, end)) {
            errorMessage += "The start date must be before the end date.\n";
        }
        return errorMessage;
    }

    public static boolean isDateWithinParentDates(
            String childDateString, String parentStartString, String parentEndString) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy", Locale.US);
        Date childDate = new Date();
        Date parentStartDate = new Date();
        Date parentEndDate = new Date();
        try {
            childDate = simpleDateFormat.parse(childDateString);
            parentStartDate = simpleDateFormat.parse(parentStartString);
            parentEndDate = simpleDateFormat.parse(parentEndString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return !(childDate.before(parentStartDate) || childDate.after(parentEndDate));
    }

    public static boolean isStartDateBeforeEndDate(String startString, String endString) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy", Locale.US);
            Date startDate = simpleDateFormat.parse(startString);
            Date endDate = simpleDateFormat.parse(endString);

            return startDate.compareTo(endDate) < 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String checkCourseMentorFields(String mentorName, String mentorEmail, String mentorPhone) {
        String errorMessage = "";

        if (mentorName.length() == 0) {
            errorMessage += "Enter a mentor name.\n";
        }
        if (mentorEmail.length() == 0) {
            errorMessage += "Enter a mentor email.\n";
        }
        if (mentorPhone.length() == 0) {
            errorMessage += "Enter a mentor phone number.\n";
        }

        if (!mentorEmail.matches(emailRegex)) {
            errorMessage += "Enter a valid email address.\n";
        }
        if (!mentorPhone.matches(phoneRegex)) {
            errorMessage += "Enter a valid phone number.\n";
        }

        return errorMessage;
    }
}

