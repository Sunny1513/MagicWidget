package com.sun.datepickerview.model;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class LunarCalendarVo {
    private Calendar calendar;
    /**是否闰月*/
    private boolean isLeap;

    public LunarCalendarVo(Calendar calendar, boolean isLeap) {
        this.calendar = calendar;
        this.isLeap = isLeap;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public boolean isLeap() {
        return isLeap;
    }

    public void setLeap(boolean leap) {
        isLeap = leap;
    }
}
