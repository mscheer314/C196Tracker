package com.example.android.c196tracker;

import android.text.Editable;
import android.text.TextUtils;

public class InputChecker {
    public static boolean isValidTermName(Editable editable) {

        return !TextUtils.isEmpty(editable);
    }
}
