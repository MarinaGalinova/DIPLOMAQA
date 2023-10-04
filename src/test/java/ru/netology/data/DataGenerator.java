package ru.netology.data;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DataGenerator {

    public static Cards getValidCard() {
        return new Cards("4444 4444 4444 4441", "12", "23", "Ivan Ivanov", "123");
    }

    public static Cards getDeclinedCard() {
        return new Cards("4444 4444 4444 4442", "12", "23", "Ivan Ivanov", "123");
    }

    public static Cards getFakeCard() {
        return new Cards("4444 4444 4444 4449", "12", "23", "Ivan Ivanov", "123");
    }

    public static Cards getInvalidHolderCard() {
        return new Cards("4444 4444 4444 4441", "12", "22", "123456789Йцукенгшщзхъ!\"№;%:?*()123456789Йцукенгшщзхъ!\"№;%:?*()", "123");
    }

    public static Cards getInvalidExpDateCard(int months) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, months);
        String date = new SimpleDateFormat("dd.MM.yy").format(calendar.getTime());
        System.out.println(date);
        String month = new SimpleDateFormat("MM").format(calendar.getTime());
        String year = new SimpleDateFormat("yy").format(calendar.getTime());
        System.out.println(month + " " + year);
        return new Cards("4444 4444 4444 4441", month, year, "Ivan Ivanov", "123");
    }

    public static Cards getInvalidExpDateCardYear(int years) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, years);
        String date = new SimpleDateFormat("dd.MM.yy").format(calendar.getTime());
        System.out.println(date);
        String month = new SimpleDateFormat("MM").format(calendar.getTime());
        String year = new SimpleDateFormat("yy").format(calendar.getTime());
        System.out.println(month + " " + year);
        return new Cards("4444 4444 4444 4441", month, year, "Ivan Ivanov", "123");
    }

    public static Cards getInvalidEmptyMonthField() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        String year = new SimpleDateFormat("yy").format(calendar.getTime());
        return new Cards("4444 4444 4444 4441", null, year, "Ivan Ivanov", "123");
    }

    public static Cards getInvalidNumberLess16Digits() {
        Calendar calendar = Calendar.getInstance();
        //calendar.setTime(new Date());
        //String date =  new SimpleDateFormat("dd.MM.yy").format(calendar.getTime());
        //System.out.println(date);
        String month = new SimpleDateFormat("MM").format(calendar.getTime());
        String year = new SimpleDateFormat("yy").format(calendar.getTime());
        System.out.println(month + " " + year);
        return new Cards("4444 4444 4444 444", month, year, "Ivan Ivanov", "123");

    }

    public static Cards getInvalidOneDigitMonthField() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        String year = new SimpleDateFormat("yy").format(calendar.getTime());
        return new Cards("4444 4444 4444 4441", "1", year, "Ivan Ivanov", "123");
    }

    public static Cards getInvalidDoubleZeroMonthField() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        String year = new SimpleDateFormat("yy").format(calendar.getTime());
        return new Cards("4444 4444 4444 4441", "00", year, "Ivan Ivanov", "123");
    }

    public static Cards getInvalidDateCardMonthOver12() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        String year = new SimpleDateFormat("yy").format(calendar.getTime());
        return new Cards("4444 4444 4444 4441", "13", year, "Ivan Ivanov", "123");
    }

    public static Cards getInvalidEmptyYearField() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        String month = new SimpleDateFormat("MM").format(calendar.getTime());
        return new Cards("4444 4444 4444 4441", month, "", "Ivan Ivanov", "123");
    }

    public static Cards getInvalidOneDigitYearField() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        String month = new SimpleDateFormat("MM").format(calendar.getTime());
        return new Cards("4444 4444 4444 4441", month, "1", "Ivan Ivanov", "123");
    }

    public static Cards getInvalidEmptyNumber() {
        Calendar calendar = Calendar.getInstance();
        String month = new SimpleDateFormat("MM").format(calendar.getTime());
        String year = new SimpleDateFormat("yy").format(calendar.getTime());
        System.out.println(month + " " + year);
        return new Cards("", month, year, "Ivan Ivanov", "123");

    }

    public static Cards getInvalidEmptyCardholderField() {
        Calendar calendar = Calendar.getInstance();
        String month = new SimpleDateFormat("MM").format(calendar.getTime());
        String year = new SimpleDateFormat("yy").format(calendar.getTime());
        System.out.println(month + " " + year);
        return new Cards("4444 4444 4444 4441", month, year, "", "123");

    }

    public static Cards getInvalidCyrillicCardholderField() {
        Calendar calendar = Calendar.getInstance();
        String month = new SimpleDateFormat("MM").format(calendar.getTime());
        String year = new SimpleDateFormat("yy").format(calendar.getTime());
        System.out.println(month + " " + year);
        return new Cards("4444 4444 4444 4441", month, year, "Иван Иванов", "123");

    }

    public static Cards getInvalidEmptyCVCField() {
        Calendar calendar = Calendar.getInstance();
        String month = new SimpleDateFormat("MM").format(calendar.getTime());
        String year = new SimpleDateFormat("yy").format(calendar.getTime());
        System.out.println(month + " " + year);
        return new Cards("4444 4444 4444 4441", month, year, "Ivan Ivanov", "");

    }

    public static Cards getInvalidTwoDigitsCVCField() {
        Calendar calendar = Calendar.getInstance();
        String month = new SimpleDateFormat("MM").format(calendar.getTime());
        String year = new SimpleDateFormat("yy").format(calendar.getTime());
        System.out.println(month + " " + year);
        return new Cards("4444 4444 4444 4441", month, year, "Ivan Ivanov", "12");

    }
}
