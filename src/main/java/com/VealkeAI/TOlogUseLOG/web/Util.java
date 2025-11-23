package com.VealkeAI.TOlogUseLOG.web;

import java.time.Instant;
import java.util.Date;

public class Util {
    public static Date toDate(Instant date) {
        return Date.from(date);
    }
}
