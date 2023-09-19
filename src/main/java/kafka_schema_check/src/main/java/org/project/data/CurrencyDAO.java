package org.project.data;

import java.time.LocalDate;

public class CurrencyDAO {
    public CurrencyDAO(String[] arr) {
        DateOfExchange = LocalDate.parse(arr[0]);
        open_rate_in_CNY = Double.parseDouble(arr[1]);
        high_rate_in_CNY = Double.parseDouble(arr[2]);
        low_rate_in_CNY=Double.parseDouble(arr[3]);
        close_rate_in_CNY=Double.parseDouble(arr[4]);
        open_rate_in_USD=Double.parseDouble(arr[5]);
        high_rate_in_USD=Double.parseDouble(arr[6]);
        low_rate_in_USD=Double.parseDouble(arr[7]);
        close_rate_in_USD=Double.parseDouble(arr[8]);
        volume=Double.parseDouble(arr[9]);
        market_cap_USD=Double.parseDouble(arr[10]);
    }

    public CurrencyDAO(){}
    public LocalDate DateOfExchange;
    public double open_rate_in_CNY;
    public double high_rate_in_CNY;
    public double low_rate_in_CNY;
    public double close_rate_in_CNY;
    public double open_rate_in_USD;
    public double high_rate_in_USD;
    public double low_rate_in_USD;
    public double close_rate_in_USD;
    public double volume;
    public double market_cap_USD;
}
