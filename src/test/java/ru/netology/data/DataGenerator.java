package ru.netology.data;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DataGenerator {

    public static Cards getValidCard() {
        return new Cards("4444 4444 4444 4441", getmonth(), getyear(), "Ivan Ivanov", "123");
    }

    public static Cards getDeclinedCard() {
        return new Cards("4444 4444 4444 4442", getmonth(), getyear(), "Ivan Ivanov", "123");
    }

    public static Cards getFakeCard() {
        return new Cards("4444 4444 4444 4449", getmonth(), getyear(), "Ivan Ivanov", "123");
    }

    public static Cards getInvalidHolderCard() {
        return new Cards("4444 4444 4444 4441", getmonth(), getyear(), "123456789Йцукенгшщзхъ!\"№;%:?*()123456789Йцукенгшщзхъ!\"№;%:?*()", "123");
    }

    public static String getmonth() {
        Calendar calendar = Calendar.getInstance();
        String month = new SimpleDateFormat("MM").format(calendar.getTime());
        return month;
    }
    public static String getyear() {
        Calendar calendar = Calendar.getInstance();
        String year = new SimpleDateFormat("yy").format(calendar.getTime());
        return year;
    }
    public static String getexpyear(int years) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, years);
        String expyear = new SimpleDateFormat("yy").format(calendar.getTime());
        return expyear;
    }
    public static String getexpmonth(int months) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, months);
        String expmonth = new SimpleDateFormat("MM").format(calendar.getTime());
        return expmonth;
    }

    public static Cards getInvalidExpDateCard(int months) {
        return new Cards("4444 4444 4444 4441", getexpmonth(months), getyear(), "Ivan Ivanov", "123");
    }

    public static Cards getInvalidExpDateCardYear(int years) {
        return new Cards("4444 4444 4444 4441", getmonth(), getexpyear(years), "Ivan Ivanov", "123");
    }

    public static Cards getInvalidEmptyMonthField() {
        return new Cards("4444 4444 4444 4441", null, getyear(), "Ivan Ivanov", "123");
    }

    public static Cards getInvalidNumberLess16Digits() {
        return new Cards("4444 4444 4444 444", getmonth(), getyear(), "Ivan Ivanov", "123");

    }

    public static Cards getInvalidOneDigitMonthField() {
        return new Cards("4444 4444 4444 4441", "1", getyear(), "Ivan Ivanov", "123");
    }

    public static Cards getInvalidDoubleZeroMonthField() {
        return new Cards("4444 4444 4444 4441", "00", getyear(), "Ivan Ivanov", "123");
    }

    public static Cards getInvalidDateCardMonthOver12() {
        return new Cards("4444 4444 4444 4441", "13", getyear(), "Ivan Ivanov", "123");
    }

    public static Cards getInvalidEmptyYearField() {
        return new Cards("4444 4444 4444 4441", getmonth(), "", "Ivan Ivanov", "123");
    }

    public static Cards getInvalidOneDigitYearField() {
        return new Cards("4444 4444 4444 4441", getmonth(), "1", "Ivan Ivanov", "123");
    }

    public static Cards getInvalidEmptyNumber() {
        return new Cards("", getmonth(), getyear(), "Ivan Ivanov", "123");
    }

    public static Cards getInvalidEmptyCardholderField() {
        return new Cards("4444 4444 4444 4441", getmonth(), getyear(), "", "123");
    }

    public static Cards getInvalidCyrillicCardholderField() {
        return new Cards("4444 4444 4444 4441", getmonth(), getyear(), "Иван Иванов", "123");
    }

    public static Cards getInvalidEmptyCVCField() {
        return new Cards("4444 4444 4444 4441", getmonth(), getyear(), "Ivan Ivanov", "");
    }

    public static Cards getInvalidTwoDigitsCVCField() {
        return new Cards("4444 4444 4444 4441", getmonth(), getyear(), "Ivan Ivanov", "12");
    }
}
