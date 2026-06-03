package com.sun.datepickerview.view;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.sun.datepickerview.R;
import com.sun.datepickerview.TimePickerView.Type;
import com.sun.datepickerview.adapter.ArrayWheelAdapter;
import com.sun.datepickerview.adapter.NumericWheelAdapter;
import com.sun.datepickerview.lib.WheelView;
import com.sun.datepickerview.listener.OnItemSelectedListener;
import com.sun.datepickerview.utils.ChinaDate;
import com.sun.datepickerview.utils.LunarCalendar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class WheelTime {
    public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private View view;
    private WheelView wv_year;
    private WheelView wv_month;
    private WheelView wv_day;
    private WheelView wv_hours;
    private WheelView wv_mins;

    private Type type;
    public static final int DEFULT_START_YEAR = 1949;
    public static final int DEFULT_END_YEAR = 2100;
    private int startYear = DEFULT_START_YEAR;
    private int endYear = DEFULT_END_YEAR;

    private boolean isLunarCalendar = false;

    public WheelTime(View view) {
        super();
        this.view = view;
        type = Type.ALL;
        init(view);
    }

    public WheelTime(View view, Type type) {
        super();
        this.view = view;
        this.type = type;
        init(view);
    }

    private void init(View view) {
        setView(view);
    }

    public void setLunarCalendar(boolean isLunarCalendar) {
        this.isLunarCalendar = isLunarCalendar;
    }

    public void setPicker(Calendar calendar) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        if (isLunarCalendar) {
            Log.d(getClass().getSimpleName(), "year:" + year + "，month：" + (month) + "，day：" + day);
//            int[] lunar = LunarCalendar.solarToLunar(year, month + 1, day);
//            isLeapMonth = lunar[3] == 1;
//            Log.d(getClass().getSimpleName(), "lunar:" + Arrays.toString(lunar));
//            int lunarYear =  lunar[0];
//            int lunarMonth =  lunar[1];
//            if (isLeapMonth) {
//                ++lunarMonth;
//            }
////            if (isLeapMonth || LunarCalendar.leapMonth(lunarYear) >= lunarMonth) {//20221008添加闰月
////                lunarMonth++;
////            }
//            Log.d(getClass().getSimpleName(), "lunarMonth:" + lunarMonth);
//            setLunar2(lunarYear, lunarMonth, lunar[2], hours, minute);
            try {
                setLunar2(isLeapMonth, year, month, day, hours, minute);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            setSolar(year, month, day, hours, minute);
        }
    }

    /**
     * 设置农历
     * @param isLeapMonth
     * @param year
     * @param month
     * @param day
     * @param h
     * @param m
     */
    public void setLunar2(boolean isLeapMonth, int year, int month, int day, int h, int m) {
        Context context = view.getContext();
        // 年
        wv_year = view.findViewById(R.id.year);
        wv_year.setAdapter(new NumericWheelAdapter(startYear, endYear));// 设置"年"的显示数据
        wv_year.setLabel("");// 添加文字
        wv_year.setCurrentItem(year - startYear);// 初始化时显示的数据

        // 月
        wv_month = view.findViewById(R.id.month);
        ArrayList<String> months = ChinaDate.getMonths(year);
        wv_month.setAdapter(new ArrayWheelAdapter(months));
        wv_month.setLabel("");
        int leapMonth = ChinaDate.leapMonth(year);
//        Log.d("setLunar2", "isLeapMonth:" + isLeapMonth);
        Log.d("setLunar2", "month:" + month);//这里的month是
//        Log.d("setLunar2", "leapMonth:" + leapMonth);
        //判断是否闰月
        if (isLeapMonth) {
            final String lunarMonth = ChinaDate.getMonth(month + 1);
            String monthPrefix = "闰" + lunarMonth;
            Log.d("setLunar2", "monthPrefix:" + monthPrefix);
            for (int i = 0; i < months.size(); i++) {
//                int monthTemp = i + 1;
                String monthStr = months.get(i);
                Log.d("setLunar2", "monthStr:" + monthStr);
                if (monthStr.startsWith(monthPrefix)) {
                    month = i;
                    break;
                }
//                else if (monthStr.startsWith(lunarMonth)) {
//                    month = monthTemp;
//                    break;
//                }
            }
        } else if (leapMonth != 0 && month >= leapMonth) {//闰月进1
            month += 1;
        }
        Log.d("setLunar2", "last month2:" + month);//这里的month是
        if (month > 13) {
            month = 13;//校正
        }
        wv_month.setCurrentItem(month);

        // 日
        wv_day = (WheelView) view.findViewById(R.id.day);
        // 判断大小月及是否闰年,用来确定"日"的数据
        if (leapMonth == 0) {
            wv_day.setAdapter(new ArrayWheelAdapter(ChinaDate.getLunarDays(ChinaDate.monthDays(year, month))));
        } else {
            wv_day.setAdapter(new ArrayWheelAdapter(ChinaDate.getLunarDays(ChinaDate.leapDays(year))));
        }
        wv_day.setLabel("");
        wv_day.setCurrentItem(day - 1);


        wv_hours = (WheelView) view.findViewById(R.id.hour);
        wv_hours.setAdapter(new NumericWheelAdapter(0, 23));
        wv_hours.setLabel(context.getString(R.string.pickerview_hours));// 添加文字
        wv_hours.setCurrentItem(h);

        wv_mins = (WheelView) view.findViewById(R.id.min);
        wv_mins.setAdapter(new NumericWheelAdapter(0, 59));
        wv_mins.setLabel(context.getString(R.string.pickerview_minutes));// 添加文字
        wv_mins.setCurrentItem(m);

        // 添加"年"监听
        OnItemSelectedListener wheelListener_year = index -> {
            int year_num = index + startYear;
            // 判断是不是闰年,来确定月和日的选择
            wv_month.setAdapter(new ArrayWheelAdapter(ChinaDate.getMonths(year_num)));
            if (ChinaDate.leapMonth(year_num) != 0 && wv_month.getCurrentItem() > ChinaDate.leapMonth(year_num) - 1) {
                wv_month.setCurrentItem(wv_month.getCurrentItem() + 1);
            } else {
                wv_month.setCurrentItem(wv_month.getCurrentItem());
            }

            int maxItem = 29;
            if (ChinaDate.leapMonth(year_num) != 0 && wv_month.getCurrentItem() > ChinaDate.leapMonth(year_num) - 1) {
                if (wv_month.getCurrentItem() == ChinaDate.leapMonth(year_num) + 1) {
                    wv_day.setAdapter(new ArrayWheelAdapter(ChinaDate.getLunarDays(ChinaDate.leapDays(year_num))));
                    maxItem = ChinaDate.leapDays(year_num);
                } else {
                    wv_day.setAdapter(new ArrayWheelAdapter(ChinaDate.getLunarDays(ChinaDate.monthDays(year_num, wv_month.getCurrentItem()))));
                    maxItem = ChinaDate.monthDays(year_num, wv_month.getCurrentItem());
                }
            } else {
                wv_day.setAdapter(new ArrayWheelAdapter(ChinaDate.getLunarDays(ChinaDate.monthDays(year_num, wv_month.getCurrentItem() + 1))));
                maxItem = ChinaDate.monthDays(year_num, wv_month.getCurrentItem() + 1);
            }

            if (wv_day.getCurrentItem() > maxItem - 1) {
                wv_day.setCurrentItem(maxItem - 1);
            }
        };
        // 添加"月"监听
        OnItemSelectedListener wheelListener_month = index -> {
            int month_num = index;
            int year_num = wv_year.getCurrentItem() + startYear;
            int maxItem = 29;
            if (ChinaDate.leapMonth(year_num) != 0 && month_num > ChinaDate.leapMonth(year_num) - 1) {
                if (wv_month.getCurrentItem() == ChinaDate.leapMonth(year_num) + 1) {
                    wv_day.setAdapter(new ArrayWheelAdapter(ChinaDate.getLunarDays(ChinaDate.leapDays(year_num))));
                    maxItem = ChinaDate.leapDays(year_num);
                } else {
                    wv_day.setAdapter(new ArrayWheelAdapter(ChinaDate.getLunarDays(ChinaDate.monthDays(year_num, month_num))));
                    maxItem = ChinaDate.monthDays(year_num, month_num);
                }
            } else {
                wv_day.setAdapter(new ArrayWheelAdapter(ChinaDate.getLunarDays(ChinaDate.monthDays(year_num, month_num + 1))));
                maxItem = ChinaDate.monthDays(year_num, month_num + 1);
            }

            if (wv_day.getCurrentItem() > maxItem - 1) {
                wv_day.setCurrentItem(maxItem - 1);
            }

        };
        wv_year.setOnItemSelectedListener(wheelListener_year);
        wv_month.setOnItemSelectedListener(wheelListener_month);

        // 根据屏幕密度来指定选择器字体的大小(不同屏幕可能不同)
        int textSize = 6;
        switch (type) {
            case ALL:
                textSize = textSize * 3;
                break;
            case YEAR_MONTH_DAY:
                textSize = textSize * 4;
                wv_hours.setVisibility(View.GONE);
                wv_mins.setVisibility(View.GONE);
                break;
            case HOURS_MINS:
                textSize = textSize * 4;
                wv_year.setVisibility(View.GONE);
                wv_month.setVisibility(View.GONE);
                wv_day.setVisibility(View.GONE);
                break;
            case MONTH_DAY_HOUR_MIN:
                textSize = textSize * 3;
                wv_year.setVisibility(View.GONE);
                break;
            case YEAR_MONTH:
                textSize = textSize * 4;
                wv_day.setVisibility(View.GONE);
                wv_hours.setVisibility(View.GONE);
                wv_mins.setVisibility(View.GONE);
        }
        wv_day.setTextSize(textSize);
        wv_month.setTextSize(textSize);
        wv_year.setTextSize(textSize);
        wv_hours.setTextSize(textSize);
        wv_mins.setTextSize(textSize);
    }

    /**
     * 设置农历
     * @param year 年
     * @param month 月
     * @param day 日
     * @param h 时
     * @param m 分
     */
//    public void setLunar3(int year, int month, int day, int h, int m) {
//        Context context = view.getContext();
//        // 年
//        wv_year = view.findViewById(R.id.year);
//        wv_year.setAdapter(new NumericWheelAdapter(startYear, endYear));// 设置"年"的显示数据
//        wv_year.setLabel("");// 添加文字
//        wv_year.setCurrentItem(year - startYear);// 初始化时显示的数据
//
//        // 月
//        wv_month = view.findViewById(R.id.month);
//        ArrayList<String> months = ChinaDate.getMonths(year);
//        wv_month.setAdapter(new ArrayWheelAdapter(months));
//        wv_month.setLabel("");
//        int leapMonth = ChinaDate.leapMonth(year);
//        if (months.size() >= 13 && month < months.size() && month > leapMonth) {//20221008确认闰月&& month >= leapMonth
//            wv_month.setCurrentItem(month);
//        } else {
//            wv_month.setCurrentItem(month - 1);
//        }
////        wv_month.setCurrentItem(month - 1);
//
//        // 日
//        wv_day = (WheelView) view.findViewById(R.id.day);
//        // 判断大小月及是否闰年,用来确定"日"的数据
//        if (leapMonth == 0) {
//            wv_day.setAdapter(new ArrayWheelAdapter(ChinaDate.getLunarDays(ChinaDate.monthDays(year, month))));
//        } else {
//            wv_day.setAdapter(new ArrayWheelAdapter(ChinaDate.getLunarDays(ChinaDate.leapDays(year))));
//        }
//        wv_day.setLabel("");
//        wv_day.setCurrentItem(day - 1);
//
//
//        wv_hours = (WheelView) view.findViewById(R.id.hour);
//        wv_hours.setAdapter(new NumericWheelAdapter(0, 23));
//        wv_hours.setLabel(context.getString(R.string.pickerview_hours));// 添加文字
//        wv_hours.setCurrentItem(h);
//
//        wv_mins = (WheelView) view.findViewById(R.id.min);
//        wv_mins.setAdapter(new NumericWheelAdapter(0, 59));
//        wv_mins.setLabel(context.getString(R.string.pickerview_minutes));// 添加文字
//        wv_mins.setCurrentItem(m);
//
//        // 添加"年"监听
//        OnItemSelectedListener wheelListener_year = new OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(int index) {
//                int year_num = index + startYear;
//                // 判断是不是闰年,来确定月和日的选择
//                wv_month.setAdapter(new ArrayWheelAdapter(ChinaDate.getMonths(year_num)));
//                if (ChinaDate.leapMonth(year_num) != 0 && wv_month.getCurrentItem() > ChinaDate.leapMonth(year_num) - 1) {
//                    wv_month.setCurrentItem(wv_month.getCurrentItem() + 1);
//                } else {
//                    wv_month.setCurrentItem(wv_month.getCurrentItem());
//                }
//
//                int maxItem = 29;
//                if (ChinaDate.leapMonth(year_num) != 0 && wv_month.getCurrentItem() > ChinaDate.leapMonth(year_num) - 1) {
//                    if (wv_month.getCurrentItem() == ChinaDate.leapMonth(year_num) + 1) {
//                        wv_day.setAdapter(new ArrayWheelAdapter(ChinaDate.getLunarDays(ChinaDate.leapDays(year_num))));
//                        maxItem = ChinaDate.leapDays(year_num);
//                    } else {
//                        wv_day.setAdapter(new ArrayWheelAdapter(ChinaDate.getLunarDays(ChinaDate.monthDays(year_num, wv_month.getCurrentItem()))));
//                        maxItem = ChinaDate.monthDays(year_num, wv_month.getCurrentItem());
//                    }
//                } else {
//                    wv_day.setAdapter(new ArrayWheelAdapter(ChinaDate.getLunarDays(ChinaDate.monthDays(year_num, wv_month.getCurrentItem() + 1))));
//                    maxItem = ChinaDate.monthDays(year_num, wv_month.getCurrentItem() + 1);
//                }
//
//                if (wv_day.getCurrentItem() > maxItem - 1) {
//                    wv_day.setCurrentItem(maxItem - 1);
//                }
//            }
//        };
//        // 添加"月"监听
//        OnItemSelectedListener wheelListener_month = new OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(int index) {
//                int month_num = index;
//                int year_num = wv_year.getCurrentItem() + startYear;
//                int maxItem = 29;
//                if (ChinaDate.leapMonth(year_num) != 0 && month_num > ChinaDate.leapMonth(year_num) - 1) {
//                    if (wv_month.getCurrentItem() == ChinaDate.leapMonth(year_num) + 1) {
//                        wv_day.setAdapter(new ArrayWheelAdapter(ChinaDate.getLunarDays(ChinaDate.leapDays(year_num))));
//                        maxItem = ChinaDate.leapDays(year_num);
//                    } else {
//                        wv_day.setAdapter(new ArrayWheelAdapter(ChinaDate.getLunarDays(ChinaDate.monthDays(year_num, month_num))));
//                        maxItem = ChinaDate.monthDays(year_num, month_num);
//                    }
//                } else {
//                    wv_day.setAdapter(new ArrayWheelAdapter(ChinaDate.getLunarDays(ChinaDate.monthDays(year_num, month_num + 1))));
//                    maxItem = ChinaDate.monthDays(year_num, month_num + 1);
//                }
//
//                if (wv_day.getCurrentItem() > maxItem - 1) {
//                    wv_day.setCurrentItem(maxItem - 1);
//                }
//
//            }
//        };
//        wv_year.setOnItemSelectedListener(wheelListener_year);
//        wv_month.setOnItemSelectedListener(wheelListener_month);
//
//        // 根据屏幕密度来指定选择器字体的大小(不同屏幕可能不同)
//        int textSize = 6;
//        switch (type) {
//            case ALL:
//                textSize = textSize * 3;
//                break;
//            case YEAR_MONTH_DAY:
//                textSize = textSize * 4;
//                wv_hours.setVisibility(View.GONE);
//                wv_mins.setVisibility(View.GONE);
//                break;
//            case HOURS_MINS:
//                textSize = textSize * 4;
//                wv_year.setVisibility(View.GONE);
//                wv_month.setVisibility(View.GONE);
//                wv_day.setVisibility(View.GONE);
//                break;
//            case MONTH_DAY_HOUR_MIN:
//                textSize = textSize * 3;
//                wv_year.setVisibility(View.GONE);
//                break;
//            case YEAR_MONTH:
//                textSize = textSize * 4;
//                wv_day.setVisibility(View.GONE);
//                wv_hours.setVisibility(View.GONE);
//                wv_mins.setVisibility(View.GONE);
//        }
//        wv_day.setTextSize(textSize);
//        wv_month.setTextSize(textSize);
//        wv_year.setTextSize(textSize);
//        wv_hours.setTextSize(textSize);
//        wv_mins.setTextSize(textSize);
//    }

    /**
     * 设置阳历
     * @param year 年
     * @param month 月
     * @param day 日
     * @param h 时
     * @param m 分
     */
    public void setSolar(int year, int month, int day, int h, int m) {
        // 添加大小月月份并将其转换为list,方便之后的判断
        String[] months_big = {"1", "3", "5", "7", "8", "10", "12"};
        String[] months_little = {"4", "6", "9", "11"};

        final List<String> list_big = Arrays.asList(months_big);
        final List<String> list_little = Arrays.asList(months_little);

        Context context = view.getContext();
        // 年
        wv_year = (WheelView) view.findViewById(R.id.year);
        wv_year.setAdapter(new NumericWheelAdapter(startYear, endYear));// 设置"年"的显示数据
        wv_year.setLabel(context.getString(R.string.pickerview_year));// 添加文字
        wv_year.setCurrentItem(year - startYear);// 初始化时显示的数据

        // 月
        wv_month = (WheelView) view.findViewById(R.id.month);
        wv_month.setAdapter(new NumericWheelAdapter(1, 12));
        wv_month.setLabel(context.getString(R.string.pickerview_month));
        wv_month.setCurrentItem(month);

        // 日
        wv_day = (WheelView) view.findViewById(R.id.day);
        // 判断大小月及是否闰年,用来确定"日"的数据
        if (list_big.contains(String.valueOf(month + 1))) {
            wv_day.setAdapter(new NumericWheelAdapter(1, 31));
        } else if (list_little.contains(String.valueOf(month + 1))) {
            wv_day.setAdapter(new NumericWheelAdapter(1, 30));
        } else {
            // 闰年
            if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
                wv_day.setAdapter(new NumericWheelAdapter(1, 29));
            else
                wv_day.setAdapter(new NumericWheelAdapter(1, 28));
        }
        wv_day.setLabel(context.getString(R.string.pickerview_day));
        wv_day.setCurrentItem(day - 1);


        wv_hours = (WheelView) view.findViewById(R.id.hour);
        wv_hours.setAdapter(new NumericWheelAdapter(0, 23));
        wv_hours.setLabel(context.getString(R.string.pickerview_hours));// 添加文字
        wv_hours.setCurrentItem(h);

        wv_mins = (WheelView) view.findViewById(R.id.min);
        wv_mins.setAdapter(new NumericWheelAdapter(0, 59));
        wv_mins.setLabel(context.getString(R.string.pickerview_minutes));// 添加文字
        wv_mins.setCurrentItem(m);

        // 添加"年"监听
        OnItemSelectedListener wheelListener_year = new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                int year_num = index + startYear;
                // 判断大小月及是否闰年,用来确定"日"的数据
                int maxItem = 30;
                if (list_big
                        .contains(String.valueOf(wv_month.getCurrentItem() + 1))) {
                    wv_day.setAdapter(new NumericWheelAdapter(1, 31));
                    maxItem = 31;
                } else if (list_little.contains(String.valueOf(wv_month
                        .getCurrentItem() + 1))) {
                    wv_day.setAdapter(new NumericWheelAdapter(1, 30));
                    maxItem = 30;
                } else {
                    if ((year_num % 4 == 0 && year_num % 100 != 0)
                            || year_num % 400 == 0) {
                        wv_day.setAdapter(new NumericWheelAdapter(1, 29));
                        maxItem = 29;
                    } else {
                        wv_day.setAdapter(new NumericWheelAdapter(1, 28));
                        maxItem = 28;
                    }
                }
                if (wv_day.getCurrentItem() > maxItem - 1) {
                    wv_day.setCurrentItem(maxItem - 1);
                }
            }
        };
        // 添加"月"监听
        OnItemSelectedListener wheelListener_month = new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                int month_num = index + 1;
                int maxItem = 30;
                // 判断大小月及是否闰年,用来确定"日"的数据
                if (list_big.contains(String.valueOf(month_num))) {
                    wv_day.setAdapter(new NumericWheelAdapter(1, 31));
                    maxItem = 31;
                } else if (list_little.contains(String.valueOf(month_num))) {
                    wv_day.setAdapter(new NumericWheelAdapter(1, 30));
                    maxItem = 30;
                } else {
                    if (((wv_year.getCurrentItem() + startYear) % 4 == 0 && (wv_year
                            .getCurrentItem() + startYear) % 100 != 0)
                            || (wv_year.getCurrentItem() + startYear) % 400 == 0) {
                        wv_day.setAdapter(new NumericWheelAdapter(1, 29));
                        maxItem = 29;
                    } else {
                        wv_day.setAdapter(new NumericWheelAdapter(1, 28));
                        maxItem = 28;
                    }
                }
                if (wv_day.getCurrentItem() > maxItem - 1) {
                    wv_day.setCurrentItem(maxItem - 1);
                }

            }
        };
        wv_year.setOnItemSelectedListener(wheelListener_year);
        wv_month.setOnItemSelectedListener(wheelListener_month);

        // 根据屏幕密度来指定选择器字体的大小(不同屏幕可能不同)
        int textSize = 6;
        switch (type) {
            case ALL:
                textSize = textSize * 3;
                break;
            case YEAR_MONTH_DAY:
                textSize = textSize * 4;
                wv_hours.setVisibility(View.GONE);
                wv_mins.setVisibility(View.GONE);
                break;
            case HOURS_MINS:
                textSize = textSize * 4;
                wv_year.setVisibility(View.GONE);
                wv_month.setVisibility(View.GONE);
                wv_day.setVisibility(View.GONE);
                break;
            case MONTH_DAY_HOUR_MIN:
                textSize = textSize * 3;
                wv_year.setVisibility(View.GONE);
                break;
            case YEAR_MONTH:
                textSize = textSize * 4;
                wv_day.setVisibility(View.GONE);
                wv_hours.setVisibility(View.GONE);
                wv_mins.setVisibility(View.GONE);
        }
        wv_day.setTextSize(textSize);
        wv_month.setTextSize(textSize);
        wv_year.setTextSize(textSize);
        wv_hours.setTextSize(textSize);
        wv_mins.setTextSize(textSize);

    }

    /**
     * 设置是否循环滚动
     *
     * @param cyclic 是否循环
     */
    public void setCyclic(boolean cyclic) {
        wv_year.setCyclic(cyclic);
        wv_month.setCyclic(cyclic);
        wv_day.setCyclic(cyclic);
        wv_hours.setCyclic(cyclic);
        wv_mins.setCyclic(cyclic);
    }

    /**是否直接返回农历*/
    private boolean isReturnLunar;
    
    public void setReturnLunar(boolean returnLunar) {
        isReturnLunar = returnLunar;
    }
    /**是否农历闰月*/
    private boolean isLeapMonth;
    
    /**
     * 是否农历闰月
     * @return
     */
    public boolean isLeapMonth() {
        return isLeapMonth;
    }

    public void setLeapMonth(boolean leapMonth) {
        isLeapMonth = leapMonth;
    }

    public String getTime() {
        StringBuffer sb = new StringBuffer();
        if (isLunarCalendar && !isReturnLunar) {//如果是农历,返回选的农历时间对应的公历时间
            int year = wv_year.getCurrentItem() + startYear;
            int month = 1;
            isLeapMonth = false;
            if (ChinaDate.leapMonth(year) == 0) {
                month = wv_month.getCurrentItem() + 1;
            } else {
                if ((wv_month.getCurrentItem() + 1) - ChinaDate.leapMonth(year) <= 0) {
                    month = wv_month.getCurrentItem() + 1;
                } else if ((wv_month.getCurrentItem() + 1) - ChinaDate.leapMonth(year) == 1) {
                    month = wv_month.getCurrentItem();
                    isLeapMonth = true;
                } else {
                    month = wv_month.getCurrentItem();
                }
            }
            int day = wv_day.getCurrentItem() + 1;
            int[] solar = LunarCalendar.lunarToSolar(year, month, day, isLeapMonth);

            sb.append(solar[0]).append("-")
                    .append(solar[1]).append("-")
                    .append(solar[2]).append(" ")
                    .append(wv_hours.getCurrentItem()).append(":")
                    .append(wv_mins.getCurrentItem());
        } else {
            if (isLunarCalendar) {
                int month;
                int year = wv_year.getCurrentItem() + startYear;
                isLeapMonth = false;
                if (ChinaDate.leapMonth(year) == 0) {
                    month = wv_month.getCurrentItem() + 1;
                } else {
                    if ((wv_month.getCurrentItem() + 1) - ChinaDate.leapMonth(year) <= 0) {
                        month = wv_month.getCurrentItem() + 1;
                    } else if ((wv_month.getCurrentItem() + 1) - ChinaDate.leapMonth(year) == 1) {
                        month = wv_month.getCurrentItem();
                        isLeapMonth = true;
                    } else {
                        month = wv_month.getCurrentItem();
                    }
                }
                int day = wv_day.getCurrentItem() + 1;
                sb.append(year).append("-").append(month).append("-").append(day).append(" ")
                        .append(wv_hours.getCurrentItem()).append(":")
                        .append(wv_mins.getCurrentItem());
            } else {
                sb.append((wv_year.getCurrentItem() + startYear)).append("-")
                    .append((wv_month.getCurrentItem() + 1)).append("-")
                        .append((wv_day.getCurrentItem() + 1)).append(" ")
                        .append(wv_hours.getCurrentItem()).append(":")
                        .append(wv_mins.getCurrentItem());
            }
            //计算闰月
//            if (isLunarCalendar) {
//                int year = wv_year.getCurrentItem() + startYear;
//                isLeapMonth = false;
//                if (ChinaDate.leapMonth(year) != 0) {
//                    if ((wv_month.getCurrentItem() + 1) - ChinaDate.leapMonth(year) == 1) {
//                        isLeapMonth = true;
//                    }
//                }
//            }
        }
        return sb.toString();
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public int getEndYear() {
        return endYear;
    }

    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }
}
