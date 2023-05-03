package com.tacs.bot.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Field {
    TYPE,
    TOKEN,
    EVNET_ID,
    OPTION_ID,
    STATUS,
    BODY;

    public static List<Field> getFields() {
        return new ArrayList<Field>(Arrays.asList(values()));
    }
}
