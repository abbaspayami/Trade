package com.tradeRepublic.Quotes.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Quote {

    private Data data;
    private String type;

    @lombok.Data
    public static class Data{
        private String price;
        private String isin;
    }

}
