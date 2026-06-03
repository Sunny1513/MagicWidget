package com.sun.datepickerview.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ChinaDate {
    final private static long[] lunarInfo = new long[]{0x04bd8, 0x04ae0,
            0x0a570, 0x054d5, 0x0d260, 0x0d950, 0x16554, 0x056a0, 0x09ad0,
            0x055d2, 0x04ae0, 0x0a5b6, 0x0a4d0, 0x0d250, 0x1d255, 0x0b540,
            0x0d6a0, 0x0ada2, 0x095b0, 0x14977, 0x04970, 0x0a4b0, 0x0b4b5,
            0x06a50, 0x06d40, 0x1ab54, 0x02b60, 0x09570, 0x052f2, 0x04970,
            0x06566, 0x0d4a0, 0x0ea50, 0x06e95, 0x05ad0, 0x02b60, 0x186e3,
            0x092e0, 0x1c8d7, 0x0c950, 0x0d4a0, 0x1d8a6, 0x0b550, 0x056a0,
            0x1a5b4, 0x025d0, 0x092d0, 0x0d2b2, 0x0a950, 0x0b557, 0x06ca0,
            0x0b550, 0x15355, 0x04da0, 0x0a5d0, 0x14573, 0x052d0, 0x0a9a8,
            0x0e950, 0x06aa0, 0x0aea6, 0x0ab50, 0x04b60, 0x0aae4, 0x0a570,
            0x05260, 0x0f263, 0x0d950, 0x05b57, 0x056a0, 0x096d0, 0x04dd5,
            0x04ad0, 0x0a4d0, 0x0d4d4, 0x0d250, 0x0d558, 0x0b540, 0x0b5a0,
            0x195a6, 0x095b0, 0x049b0, 0x0a974, 0x0a4b0, 0x0b27a, 0x06a50,
            0x06d40, 0x0af46, 0x0ab60, 0x09570, 0x04af5, 0x04970, 0x064b0,
            0x074a3, 0x0ea50, 0x06b58, 0x055c0, 0x0ab60, 0x096d5, 0x092e0,
            0x0c960, 0x0d954, 0x0d4a0, 0x0da50, 0x07552, 0x056a0, 0x0abb7,
            0x025d0, 0x092d0, 0x0cab5, 0x0a950, 0x0b4a0, 0x0baa4, 0x0ad50,
            0x055d9, 0x04ba0, 0x0a5b0, 0x15176, 0x052b0, 0x0a930, 0x07954,
            0x06aa0, 0x0ad50, 0x05b52, 0x04b60, 0x0a6e6, 0x0a4e0, 0x0d260,
            0x0ea65, 0x0d530, 0x05aa0, 0x076a3, 0x096d0, 0x04bd7, 0x04ad0,
            0x0a4d0, 0x1d0b6, 0x0d250, 0x0d520, 0x0dd45, 0x0b5a0, 0x056d0,
            0x055b2, 0x049b0, 0x0a577, 0x0a4b0, 0x0aa50, 0x1b255, 0x06d20,
            0x0ada0};
    final private static int[] year20 = new int[]{1, 4, 1, 2, 1, 2, 1, 1, 2,
            1, 2, 1};
    final private static int[] year19 = new int[]{0, 3, 0, 1, 0, 1, 0, 0, 1,
            0, 1, 0};
    final private static int[] year2000 = new int[]{0, 3, 1, 2, 1, 2, 1, 1,
            2, 1, 2, 1};
    public final static String[] nStr1 = new String[]{"", "正", "二", "三", "四",
            "五", "六", "七", "八", "九", "十", "冬", "腊"};
    private final static String[] Gan = new String[]{"甲", "乙", "丙", "丁", "戊",
            "己", "庚", "辛", "壬", "癸"};
    private final static String[] Zhi = new String[]{"子", "丑", "寅", "卯", "辰",
            "巳", "午", "未", "申", "酉", "戌", "亥"};
    private final static String[] Animals = new String[]{"鼠", "牛", "虎", "兔",
            "龙", "蛇", "马", "羊", "猴", "鸡", "狗", "猪"};

//    private final static String[] solarTerm = new String[]{"小寒", "大寒", "立春",
//            "雨水", "惊蛰", "春分", "清明", "谷雨", "立夏", "小满", "芒种", "夏至", "小暑", "大暑",
//            "立秋", "处暑", "白露", "秋分", "寒露", "霜降", "立冬", "小雪", "大雪", "冬至"};
//    private final static String[] sFtv = new String[]{"0101*元旦", "0214 情人节",
//            "0308 妇女节", "0312 植树节", "0315 消费者权益日", "0401 愚人节", "0501 劳动节",
//            "0504 青年节", "0512 护士节", "0601 儿童节", "0701 建党节", "0801 建军节",
//            "0808 父亲节", "0909 mzd逝世纪念", "0910 教师节", "0928 孔子诞辰", "1001*国庆节",
//            "1006 老人节", "1024 联合国日", "1112 孙中山诞辰", "1220 澳门回归", "1225 圣诞节",
//            "1226 mzd诞辰"};
//    private final static String[] lFtv = new String[]{"0101*农历春节",
//            "0115 元宵节", "0505 端午节", "0707 七夕情人节", "0815 中秋节", "0909 重阳节",
//            "1208 腊八节", "1224 小年", "0100*除夕"};
    private final static int[] dayArr = new int[]{20, 19, 21, 20, 21, 22, 23, 23, 23, 24, 23, 22};
    private final static String[] constellationArr = new String[]{"摩羯座", "水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座"};

    /**
     * 传回农历
     *
     * @param y 年的总天数
     * @return 农历
     */
    private static int lYearDays(int y) {
        int i, sum = 348;
        for (i = 0x8000; i > 0x8; i >>= 1) {
            if ((lunarInfo[y - 1900] & i) != 0)
                sum += 1;
        }
        return (sum + leapDays(y));
    }

    /**
     * 传回农历
     *
     * @param y 年闰月的天数
     * @return 农历
     */
    public static int leapDays(int y) {
        if (leapMonth(y) != 0) {
            if ((lunarInfo[y - 1900] & 0x10000) != 0)
                return 30;
            else
                return 29;
        } else
            return 0;
    }

    /**
     * 传回农历
     *
     * @param y 年闰哪个月 1-12 , 没闰传回 0
     * @return 农历
     */
    public static int leapMonth(int y) {
        int index = y - 1900;
        if (index >= lunarInfo.length) {
            return 0;
        }
        return (int) (lunarInfo[index] & 0xf);
    }

    /**
     * 传回农历 y
     *
     * @param y y年m月的总天数
     * @param m y年m月的总天数
     * @return 农历
     */
    final public static int monthDays(int y, int m) {
        int index = y - 1900;
        if (index >= lunarInfo.length) {
            return 30;
        }
        if ((lunarInfo[index] & (0x10000 >> m)) == 0)
            return 29;
        else
            return 30;
    }

    /**
     * 月转农历描述
     * @param month
     * @return
     */
    public static String getMonth(int month) {
        if (month >= nStr1.length) {
            month -= 1;
        }
        return nStr1[month];
    }

    /**
     * 传回农历
     *
     * @param y 年的生肖
     * @return
     */
    public static String AnimalsYear(int y) {
        return Animals[(y - 4) % 12];
    }

    /**
     * 传入
     *
     * @param num 月日的offset 传回干支,0是甲子
     * @return 干支
     */
    private static String cyclicalm(int num) {
        return (Gan[num % 10] + Zhi[num % 12]);
    }

    /**
     * 传入 offset 传回干支
     *
     * @param y 0是甲子
     * @return 干支
     */
    final public static String cyclical(int y) {
        int num = y - 1900 + 36;
        return (cyclicalm(num));
    }

    /**
     * 传出农历.year0 .month1 .day2 .yearCyl3 .monCyl4 .dayCyl5 .isLeap6
     *
     * @param y 年
     * @param m 月
     * @return 传出农历
     */
    final private long[] Lunar(int y, int m) {
        long[] nongDate = new long[7];
        int i = 0, temp = 0, leap = 0;
        Date baseDate = new GregorianCalendar(1900 + 1900, 1, 31).getTime();
        Date objDate = new GregorianCalendar(y + 1900, m, 1).getTime();
        long offset = (objDate.getTime() - baseDate.getTime()) / 86400000L;
        if (y < 2000)
            offset += year19[m - 1];
        if (y > 2000)
            offset += year20[m - 1];
        if (y == 2000)
            offset += year2000[m - 1];
        nongDate[5] = offset + 40;
        nongDate[4] = 14;
        for (i = 1900; i < 2050 && offset > 0; i++) {
            temp = lYearDays(i);
            offset -= temp;
            nongDate[4] += 12;
        }
        if (offset < 0) {
            offset += temp;
            i--;
            nongDate[4] -= 12;
        }
        nongDate[0] = i;
        nongDate[3] = i - 1864;
        leap = leapMonth(i); // 闰哪个月
        nongDate[6] = 0;
        for (i = 1; i < 13 && offset > 0; i++) {
            // 闰月
            if (leap > 0 && i == (leap + 1) && nongDate[6] == 0) {
                --i;
                nongDate[6] = 1;
                temp = leapDays((int) nongDate[0]);
            } else {
                temp = monthDays((int) nongDate[0], i);
            }
            // 解除闰月
            if (nongDate[6] == 1 && i == (leap + 1))
                nongDate[6] = 0;
            offset -= temp;
            if (nongDate[6] == 0)
                nongDate[4]++;
        }
        if (offset == 0 && leap > 0 && i == leap + 1) {
            if (nongDate[6] == 1) {
                nongDate[6] = 0;
            } else {
                nongDate[6] = 1;
                --i;
                --nongDate[4];
            }
        }
        if (offset < 0) {
            offset += temp;
            --i;
            --nongDate[4];
        }
        nongDate[1] = i;
        nongDate[2] = offset + 1;
        return nongDate;
    }

    /**
     * 传出y年m月d日对应的农历.year0 .month1 .day2 .yearCyl3 .monCyl4 .dayCyl5 .isLeap6
     *
     * @param y 年
     * @param m 月
     * @param d 日
     * @return y年m月d日对应的农历
     */
    public static long[] calElement(int y, int m, int d) {
        long[] nongDate = new long[7];
        int i = 0, temp = 0, leap = 0;
        Date baseDate = new GregorianCalendar(0 + 1900, 0, 31).getTime();
        Date objDate = new GregorianCalendar(y, m - 1, d).getTime();
        long offset = (objDate.getTime() - baseDate.getTime()) / 86400000L;
        nongDate[5] = offset + 40;
        nongDate[4] = 14;
        for (i = 1900; i < 2050 && offset > 0; i++) {
            temp = lYearDays(i);
            offset -= temp;
            nongDate[4] += 12;
        }
        if (offset < 0) {
            offset += temp;
            i--;
            nongDate[4] -= 12;
        }
        nongDate[0] = i;
        nongDate[3] = i - 1864;
        leap = leapMonth(i); // 闰哪个月
        nongDate[6] = 0;
        for (i = 1; i < 13 && offset > 0; i++) {
            // 闰月
            if (leap > 0 && i == (leap + 1) && nongDate[6] == 0) {
                --i;
                nongDate[6] = 1;
                temp = leapDays((int) nongDate[0]);
            } else {
                temp = monthDays((int) nongDate[0], i);
            }
            // 解除闰月
            if (nongDate[6] == 1 && i == (leap + 1))
                nongDate[6] = 0;
            offset -= temp;
            if (nongDate[6] == 0)
                nongDate[4]++;
        }
        if (offset == 0 && leap > 0 && i == leap + 1) {
            if (nongDate[6] == 1) {
                nongDate[6] = 0;
            } else {
                nongDate[6] = 1;
                --i;
                --nongDate[4];
            }
        }
        if (offset < 0) {
            offset += temp;
            --i;
            --nongDate[4];
        }
        nongDate[1] = i;
        nongDate[2] = offset + 1;
        return nongDate;
    }

    /**
     * 获得农历的日
     * @param day
     * @return
     */
    public static String getChinaDate(int day) {
        String a = "";
        if (day == 10)
            return "初十";
        if (day == 20)
            return "二十";
        if (day == 30)
            return "三十";
        int two = (int) ((day) / 10);
        if (two == 0)
            a = "初";
        if (two == 1)
            a = "十";
        if (two == 2)
            a = "廿";
        if (two == 3)
            a = "三";
        int one = (int) (day % 10);
        switch (one) {
            case 1:
                a += "一";
                break;
            case 2:
                a += "二";
                break;
            case 3:
                a += "三";
                break;
            case 4:
                a += "四";
                break;
            case 5:
                a += "五";
                break;
            case 6:
                a += "六";
                break;
            case 7:
                a += "七";
                break;
            case 8:
                a += "八";
                break;
            case 9:
                a += "九";
                break;
        }
        return a;
    }

    public static String today() {
        Calendar today = Calendar.getInstance(Locale.SIMPLIFIED_CHINESE);
        int year = today.get(Calendar.YEAR);
        int month = today.get(Calendar.MONTH) + 1;
        int date = today.get(Calendar.DATE);
        long[] l = calElement(year, month, date);
        StringBuilder sToday = new StringBuilder();
        try {
            int lunarYear = (int) l[0];
            sToday.append(new SimpleDateFormat("yyyy年M月d日 EEEEE", Locale.CHINESE).format(today.getTime()));
            sToday.append(" 农历");
            sToday.append(cyclical(lunarYear));
            sToday.append('(');
            sToday.append(AnimalsYear(lunarYear));
            sToday.append(")年");
            sToday.append(nStr1[(int) l[1]]);
            sToday.append("月");
            sToday.append(getChinaDate((int) (l[2])));
            return sToday.toString();
        } finally {
            sToday = null;
        }
    }

    public static String todayLunar() {
        Calendar today = Calendar.getInstance(Locale.SIMPLIFIED_CHINESE);
        int year = today.get(Calendar.YEAR);
        int month = today.get(Calendar.MONTH) + 1;
        int date = today.get(Calendar.DATE);
        long[] l = calElement(year, month, date);
        int lunarYear = (int) l[0];
        return cyclical(lunarYear) +
                '(' +
                AnimalsYear(lunarYear) +
                ")年" +
                nStr1[(int) l[1]] +
                "月" +
                getChinaDate((int) (l[2]));
    }

    /**
     * 获取今日农历方法
     * @param format
     * @return
     */
    public static String todayLunar(String format) {
        return formatLunar(Calendar.getInstance(Locale.SIMPLIFIED_CHINESE), format);
    }

    /**
     * 格式化农历
     * @param calendar 日期
     * @param format 格式
     * @return
     */
    public static String formatLunar(Calendar calendar, String format) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int date = calendar.get(Calendar.DATE);
        long[] l = calElement(year, month, date);
        int lunarYear = (int) l[0];
        format = format.replace("yyyy", lunarYear + "");
        if (format.contains("M")) {
            format = format.replace("M", nStr1[(int) l[1]]);
        }
        if (format.contains("D")) {
            format = format.replace("D", getChinaDate((int) (l[2])));
        }
        if (format.contains("G")) {
            format = format.replace("G", cyclical(lunarYear));
        }
        if (format.contains("CZ")) {
            format = format.replace("CZ", AnimalsYear(lunarYear));
        }
        if (format.contains("E")) {
            String[] arr = {"周日","周一","周二","周三","周四","周五","周六"};
            format = format.replace("E", arr[calendar.get(Calendar.DAY_OF_WEEK)-1]);
        }
        if (format.contains("CO")) {
            format = format.replace("CO", getConstellation(month, date));
        }
        if (format.contains("OHD")) {//仅节假日
            format = format.replace("OHD", getFestivalInfo(year, month, date, true));
        }
//        StringBuilder builder = new StringBuilder();
//        if ("G(CZ)年M月D".equals(format)) {
//            builder.append(cyclical(year)).append("(").append(AnimalsYear(year)).append(")年").
//                    append(nStr1[(int) l[1]]).append("月").append(getChinaDate((int) (l[2])));
//        } else if ("M月D".equals(format)) {
//            builder.append(nStr1[(int) l[1]]).append("月").append(getChinaDate((int) (l[2])));
//        } else if ("yyyy年M月D".equals(format)) {
//            builder.append(year).append(nStr1[(int) l[1]]).append("月").append(getChinaDate((int) (l[2])));
//        } else if ("yyyy年M月D(CZ)".equals(format)) {
//            builder.append(year).append(nStr1[(int) l[1]]).append("月").append(getChinaDate((int) (l[2])))
//                .append("(").append(AnimalsYear(year)).append(")");
//        } else if ("yyyy年M月D(E)".equals(format)) {
//            String[] arr = {"周日","周一","周二","周三","周四","周五","周六"};
//            builder.append(year).append(nStr1[(int) l[1]]).append("月").append(getChinaDate((int) (l[2])))
//                    .append("(").append(arr[today.get(Calendar.DAY_OF_WEEK)-1]).append(")");;
//        } else if ("yyyy年M月D(CO)".equals(format)) {
//            builder.append(year).append(nStr1[(int) l[1]]).append("月").append(getChinaDate((int) (l[2])))
//                    .append("(").append(getConstellation(month, date)).append(")");;
//        }
        return format;
    }

    /**
     * 获取当天是初几方法
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static String getLunarDay(int year, int month, int day) {
        return getChinaDate((int) (calElement(year, month, day)[2]));
    }

//    public static String oneDay(int year, int month, int day) {
//        //   Calendar today = Calendar.getInstance(Locale.SIMPLIFIED_CHINESE);
//        long[] l = calElement(year, month, day);
//        int lunarYear = (int) l[0];
//        StringBuffer sToday = new StringBuffer();
//        try {
//            //   sToday.append(sdf.format(today.getTime()));
//            sToday.append(" 农历");
//            sToday.append(cyclical(lunarYear));
//            sToday.append('(');
//            sToday.append(AnimalsYear(lunarYear));
//            sToday.append(")年");
//            sToday.append(nStr1[(int) l[1]]);
//            sToday.append("月");
//            sToday.append(getChinaDate((int) (l[2])));
//            return sToday.toString();
//        } finally {
//            sToday = null;
//        }
//    }

    //private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日 EEEEE");

    /**
     * 返回星座
     *
     * @return 星座
     */

    public static String getConstellation(int month, int day) {
        return day < dayArr[month - 1] ? constellationArr[month - 1] : constellationArr[month];
    }

    public static int getMonthByYear(int year) {
        if (leapMonth(year) == 0) {
            return 12;
        } else {
            return 13;
        }
    }

    /**
     * 获取year年的所有月份
     *
     * @param year 年
     * @return 月份列表
     */
    public static ArrayList<String> getMonths(int year) {
        ArrayList<String> baseMonths = new ArrayList<>();
        for (int i = 1; i < nStr1.length; i++) {
            baseMonths.add(nStr1[i] + "月");
        }
        if (leapMonth(year) != 0) {
            baseMonths.add(leapMonth(year), "闰" + nStr1[leapMonth(year)] + "月");
        }
        return baseMonths;
    }

    /**
     * 获取每月农历显示名称
     *
     * @param maxDay 天
     * @return 名称列表
     */
    public static ArrayList<String> getLunarDays(int maxDay) {
        ArrayList<String> days = new ArrayList<>();
        for (int i = 1; i <= maxDay; i++) {
            days.add(getChinaDate(i));
        }
        return days;
    }

    /**
     * 获取公历节日/公立节日
     * @return
     */
    public static Map<String, String> getFestivalSolarMap() {
        final Map<String, String> festivalSolarMap = new HashMap<>();
        festivalSolarMap.put("0101", "元旦节");festivalSolarMap.put("0512", "护士节");
        festivalSolarMap.put("0214", "情人节");festivalSolarMap.put("0601", "儿童节");
        festivalSolarMap.put("0308", "妇女节");festivalSolarMap.put("0701", "建党节");
        festivalSolarMap.put("0312", "植树节");festivalSolarMap.put("0801", "建军节");
        festivalSolarMap.put("0315", "打假节");festivalSolarMap.put("0808", "爸爸节");//中国父亲节
        festivalSolarMap.put("0401", "愚人节");festivalSolarMap.put("0910", "教师节");
        festivalSolarMap.put("0501", "劳动节");festivalSolarMap.put("1001", "国庆节");
        festivalSolarMap.put("0422", "地球日");festivalSolarMap.put("1024", "程序员节");
        festivalSolarMap.put("0519", "旅游日");festivalSolarMap.put("0531", "无烟日");
        festivalSolarMap.put("1204", "宪法日");festivalSolarMap.put("1224", "平安夜");
        festivalSolarMap.put("1031", "万圣节");festivalSolarMap.put("0423", "读书日");

        festivalSolarMap.put("0504", "青年节");festivalSolarMap.put("1225", "圣诞节");
        return festivalSolarMap;
    }

    /**
     * 获取农历历节日
     * 更多参考：https://www.bbsmax.com/A/Ae5Rexv2JQ/
     * @return
     */
    public static Map<String, String> getFestivalLunarMap() {
        final Map<String, String> festivalLunarMap = new HashMap<>();
//        festivalLunarMap.put("一月初一", "春节");festivalLunarMap.put("八月十五", "中秋节");
//        festivalLunarMap.put("一月十五", "元宵节");festivalLunarMap.put("九月初九", "重阳节");
//        festivalLunarMap.put("二月初二", "龙头节");festivalLunarMap.put("十月初一", "寒衣节");
//        festivalLunarMap.put("十月十五", "下元节");
//        festivalLunarMap.put("五月初五", "端午节");festivalLunarMap.put("腊月初八", "腊八节");
//        festivalLunarMap.put("七月初七", "七夕节");festivalLunarMap.put("腊月廿三", "北方小年");
//        festivalLunarMap.put("七月十五", "中元节");festivalLunarMap.put("腊月廿四", "南方小年");
        festivalLunarMap.put("0101", "春节");festivalLunarMap.put("0815", "中秋节");
        festivalLunarMap.put("0115", "元宵节");festivalLunarMap.put("0909", "重阳节");
        festivalLunarMap.put("0202", "龙头节");festivalLunarMap.put("0303", "上巳节");
        festivalLunarMap.put("0404", "寒食节");
        festivalLunarMap.put("1001", "寒衣节");
        festivalLunarMap.put("1015", "下元节");
        festivalLunarMap.put("0505", "端午节");festivalLunarMap.put("1208", "腊八节");
        festivalLunarMap.put("0707", "七夕节");festivalLunarMap.put("1223", "北方小年");
        festivalLunarMap.put("0715", "中元节");festivalLunarMap.put("1224", "南方小年");
        //腊月最后一天 为除夕 需要特殊处理  我直接写入 Lunar 类中了
        //festivalLunarMap.put("一月零零", "除夕");
//        if (month == 12) { // 除夕夜需要特殊处理
//            // 这个月共有daysOfMonth天  现在是第day天
//            if ((daysOfMonth == 29 && day == 29) || (daysOfMonth == 30 && day == 30)) {
//                return "除夕";
//            }
//        }
        return festivalLunarMap;
    }

    /**
     * 日期转字符串
     * @param day
     * @return
     */
    public static String dayToSr(int day) {
        String str = day + "";
        if (str.length() <= 1) {
            return "0" + str;
        }
        return str;
    }

    /**
     * 母亲节和父亲节
     * 5月第二个星期日 母亲节
     * 6月第三个星期日 父亲节
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    private static String getMotherOrFatherDay(int year, int month, int day) {
        if (month != 5 && month != 6) return null;
        if ((month == 5 && (day < 8 || day > 14)) || (month == 6 && (day < 15 || day > 21))) return null;
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);
        int weekDate = calendar.get(Calendar.DAY_OF_WEEK);
        weekDate = (weekDate == 1) ? 7 : weekDate - 1;
        switch (month) {
            case 5:
                if (day == 15 - weekDate) {
                    return "母亲节";
                }
                break;
            case 6:
                if (day == 22 - weekDate) {
                    return "父亲节";
                }
                break;
        }
        return null;
    }
    /**
     * 感恩节
     * 11月最后一个星期四 美国感恩节
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    private static String thanksgiving(int year, int month, int day) {
        if (month != 11) return null;
        if ((month == 11 && (day < 19 || day > 28))) return null;
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);
        int weekDate = calendar.get(Calendar.DAY_OF_WEEK);
        weekDate = (weekDate == 1) ? 7 : weekDate - 1;
        switch (month) {
            case 11:
                if (day == 29 - weekDate + 4) {
                    return "感恩节";
                }
                break;
        }
        return null;
    }

    /**
     * 获取复活节
     * 公历3月21日（春分）之后满月后的第一个星期日
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    private static String getEasterDay(int year, int month, int day) {
        int n = year - 1900;
        int a = n % 19;
        int q = n / 4;
        int b = (7 * a + 1) / 19;
        int m = (11 * a + 4 - b) % 29;
        int w = (n + q + 31 - m) % 7;
        int answer = 25 - m - w;
        String easterDay = "";
        if (answer > 0) {
            easterDay = year + "-" + 4 + "-" + answer;
        } else {
            easterDay = year + "-" + 3 + "-" + (31 + answer);
        }
        String searchDay = year + "-" + month + "-" + day;
        if (searchDay.equals(easterDay)) {
            return "复活节";
        }
        return null;
    }

    /**
     * 节气偏移量计算
     *
     * @param map
     * @param year
     * @param n
     * @param offset
     * @return
     */
    private static int getOffset(Map<Integer, Integer[]> map, int year, int n, int offset) {
        int off = 0;
        Integer[] years = map.get(n);
        if (null != years) {
            for (int i : years) {
                if (i == year) {
                    off = offset;
                    break;
                }
            }
        }
        return off;
    }

    /**
     * 特例,特殊的年分的节气偏移量,由于公式并不完善，所以算出的个别节气的第几天数并不准确，在此返回其偏移量
     *
     * @param year 年份
     * @param n    节气编号
     * @return 返回其偏移量
     */
    private static int specialYearOffset(int year, int n) {
        int offset = 0; //特殊年份节气日期偏移
        final Map<Integer, Integer[]> INCREASE_OFFSETMAP = new HashMap<>();//+1偏移
        final Map<Integer, Integer[]> DECREASE_OFFSETMAP = new HashMap<>();//-1偏移
        INCREASE_OFFSETMAP.put(0, new Integer[]{1982});//小寒
        DECREASE_OFFSETMAP.put(0, new Integer[]{2019});//小寒
        INCREASE_OFFSETMAP.put(1, new Integer[]{2082});//大寒
        DECREASE_OFFSETMAP.put(3, new Integer[]{2026});//雨水
        INCREASE_OFFSETMAP.put(5, new Integer[]{2084});//春分
        INCREASE_OFFSETMAP.put(9, new Integer[]{2008});//小满
        INCREASE_OFFSETMAP.put(10, new Integer[]{1902});//芒种
        INCREASE_OFFSETMAP.put(11, new Integer[]{1928});//夏至
        INCREASE_OFFSETMAP.put(12, new Integer[]{1925, 2016});//小暑
        INCREASE_OFFSETMAP.put(13, new Integer[]{1922});//大暑
        INCREASE_OFFSETMAP.put(14, new Integer[]{2002});//立秋
        INCREASE_OFFSETMAP.put(16, new Integer[]{1927});//白露
        INCREASE_OFFSETMAP.put(17, new Integer[]{1942});//秋分
        INCREASE_OFFSETMAP.put(19, new Integer[]{2089});//霜降
        INCREASE_OFFSETMAP.put(20, new Integer[]{2089});//立冬
        INCREASE_OFFSETMAP.put(21, new Integer[]{1978});//小雪
        INCREASE_OFFSETMAP.put(22, new Integer[]{1954});//大雪
        DECREASE_OFFSETMAP.put(23, new Integer[]{1918, 2021});//冬至
        offset += getOffset(DECREASE_OFFSETMAP, year, n, -1);
        offset += getOffset(INCREASE_OFFSETMAP, year, n, 1);
        return offset;
    }

    /**
     * 获取某年的第n个节气为几日(从0小寒起算)
     *
     * @param year
     * @param n
     * @return
     */
    private static int sTerm(int year, int n) {
        double centuryValue = 0;//节气的世纪值，每个节气的每个世纪值都不同
        int centuryIndex = -1;
        if (year >= 1901 && year <= 2000) {//20世纪
            centuryIndex = 0;
        } else if (year >= 2001 && year <= 2100) {//21世纪
            centuryIndex = 1;
        } else {
            throw new RuntimeException("不支持此年份：" + year + "，目前只支持1901年到2100年的时间范围");
        }
        //定义一个二维数组，第一维数组存储的是20世纪的节气C值，第二维数组存储的是21世纪的节气C值,0到23个，依次代表立春、雨水...大寒节气的C值
        final double[][] CENTURY_ARRAY = {
                {6.11, 20.84, 4.6295, 19.4599, 6.3826, 21.4155, 5.59, 20.888, 6.318, 21.86, 6.5, 22.2, 7.928, 23.65, 8.35, 23.95, 8.44, 23.822, 9.098, 24.218, 8.218, 23.08, 7.9, 22.6},
                {5.4055, 20.12, 3.87, 18.73, 5.63, 20.646, 4.81, 20.1, 5.52, 21.04, 5.678, 21.37, 7.108, 22.83, 7.5, 23.13, 7.646, 23.042, 8.318, 23.438, 7.438, 22.36, 7.18, 21.94}
        };
        centuryValue = CENTURY_ARRAY[centuryIndex][n];
        int dateNum = 0;

        int y = year % 100;//步骤1:取年分的后两位数
        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {//闰年
            if (n == 0 || n == 1 || n == 2 || n == 3) {
                //注意：凡闰年3月1日前闰年数要减一，即：L=[(Y-1)/4],因为小寒、大寒、立春、雨水这两个节气都小于3月1日,所以 y = y-1
                y = y - 1;//步骤2
            }
        }
        //二十四节气日期偏移度
        final double D = 0.2422;
        dateNum = (int) (y * D + centuryValue) - (int) (y / 4);//步骤3，使用公式[Y*D+C]-L计算
        dateNum += specialYearOffset(year, n);//步骤4，加上特殊的年分的节气偏移量
        return dateNum;
    }

    /**
     * 输入公历日期初始化当前日期的生肖、天干地支、农历年、农历月、农历日、公历节日、农历节日、24节气
     * 输入日期的格式为(YYYY-MM-DD)
     * @param onlyHoliday true 指显示节假日，不显示农历
     * @return
     */
    public static String getFestivalInfo(int year, int month, int day, boolean onlyHoliday) {
        String date = dayToSr(month) + dayToSr(day);
        //判断是否有公历节日
        Map<String, String> glMap = getFestivalSolarMap();
        if (glMap.containsKey(date)) {
            return glMap.get(date);
        }
        //判断是否有农历节日
        Map<String, String> nlMap = getFestivalLunarMap();
        long[] lun = calElement(year, month, day);
        String lunarDate = dayToSr((int) lun[1]) + dayToSr((int) lun[2]);
        if (nlMap.containsKey(lunarDate)) {
            return nlMap.get(lunarDate);
        }
//        int monthDays = monthDays((int) lun[0], (int) lun[1]);//农历总天数
        try {
            //判断是否除夕（12月最后1天）
            if (lun[1] == 12 && lun[2] == monthDays((int) lun[0], (int) lun[1])) {
                return "除夕";
            }
            //判断节日是否是父亲节或母亲节
            String motherOrFatherDay = getMotherOrFatherDay(year, month, day);
            if (motherOrFatherDay != null) {
                return motherOrFatherDay;
            }
            //判断节日是否是复活节
            String easterDay = getEasterDay(year, month, day);
            if (easterDay != null) {
                return easterDay;
            }
            //判断节日是否是感恩节
            String thanksgiving = thanksgiving(year, month, day);
            if (thanksgiving != null) {
                return thanksgiving;
            }
            final String[] solarTerms = new String[]{"小寒", "大寒", "立春", "雨水", "惊蛰", "春分", "清明", "谷雨", "立夏",
                    "小满", "芒种", "夏至", "小暑", "大暑", "立秋", "处暑", "白露", "秋分", "寒露", "霜降", "立冬", "小雪", "大雪", "冬至"};
            //设置节气
            String lunarTerm = null;
            if (day == sTerm(year, (month - 1) * 2)) {
                lunarTerm = solarTerms[(month - 1) * 2];
            } else if (day == sTerm(year, (month - 1) * 2 + 1)) {
                lunarTerm = solarTerms[(month - 1) * 2 + 1];
            }
            if (null != lunarTerm) {
                if ("清明".equals(lunarTerm)) {
                    return "清明节";
                }
                return lunarTerm;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (onlyHoliday) {
            return "";
        }
        if (lun[2] == 1 && lun[1] < nStr1.length) {//当月第一天返回月份
            return nStr1[(int) lun[1]] + "月";
        }
        return getChinaDate((int) (lun[2]));
    }

    /**
     * 获取节假日方法（没有时返回初三这种农历）
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static String getFestivalInfo(int year, int month, int day) {
        return getFestivalInfo(year, month, day, false);
    }

    /**
     * 获取节假日方法
     * @param calendar
     * @return 可能返回""
     */
    public static String getFestivalInfo(Calendar calendar, boolean onlyHoliday) {
        return getFestivalInfo(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH), onlyHoliday);
    }

    /**
     * 获取节假日方法
     * @param date
     * @return 可能返回""
     */
    public static String getFestivalInfo(Date date, boolean onlyHoliday) {
        final Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTime(date);
        return getFestivalInfo(calendar, onlyHoliday);
    }

    /**
     * 获取节假日方法（无节日时会返回初三这种农历）
     * @param date
     * @return
     */
    public static String getFestivalInfo(Date date) {
        return getFestivalInfo(date, false);
    }
}