package ru.netology.data;

import lombok.Data;

@Data
public class Cards {
    private String number;
    private String month;
    private String year;
    private String holder;
    private String cvc;

    public Cards(String number, String month, String year, String holder, String cvc) {
        setNumber(number);
        setMonth(month);
        setYear(year);
        setHolder(holder);
        setCvc(cvc);
    }

    public String getNumber() {
        return number;
    }

    public String getCvc() {
        return cvc;
    }

    public String getHolder() {
        return holder;
    }

    public String getMonth() {
        return month;
    }

    public String getYear() {
        return year;
    }
}
