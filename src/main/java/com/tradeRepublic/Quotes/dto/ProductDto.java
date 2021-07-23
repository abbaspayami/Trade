package com.tradeRepublic.Quotes.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * this class use for showing result to client
 * @author Abbas
 */
@Data
public class ProductDto {

    public ProductDto(String isin, String description, String closePrice) {
        this.isin = isin;
        this.description = description;
        this.closePrice = closePrice;
    }

    public ProductDto(Date openTimestamp, String openPrice, String highPrice, String lowPrice, String closePrice, Date closeTimestamp) {
        this.openTimestamp = openTimestamp;
        this.openPrice = openPrice;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.closePrice = closePrice;
        this.closeTimestamp = closeTimestamp;
    }

    @JsonView(ProductViews.InstrumentPrice.class)
    private String isin;
    @JsonView(ProductViews.InstrumentPrice.class)
    private String description;
    @JsonView(ProductViews.InstrumentPriceHistory.class)
    private Date openTimestamp;
    @JsonView(ProductViews.InstrumentPriceHistory.class)
    private String openPrice;
    @JsonView(ProductViews.InstrumentPriceHistory.class)
    private String highPrice;
    @JsonView(ProductViews.InstrumentPriceHistory.class)
    private String lowPrice;
    @JsonView({ProductViews.InstrumentPrice.class,ProductViews.InstrumentPriceHistory.class})
    private String closePrice;
    @JsonView(ProductViews.InstrumentPriceHistory.class)
    private Date closeTimestamp;

}
