package com.tradeRepublic.Quotes.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Instrument {

    private Data data;
    private String type;

    @lombok.Data
    public static class Data{
        private String description;
        private String isin;
    }
}
