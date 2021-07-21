package com.tradeRepublic.Quotes.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ProductDto {

    public ProductDto(String isin, String description, String closePrice) {
        this.isin = isin;
        this.description = description;
        this.closePrice = closePrice;
    }
    @JsonView({ProductViews.InstrumentPrice.class,ProductViews.InstrumentPriceHistory.class})
    private String isin;
    @JsonView({ProductViews.InstrumentPrice.class,ProductViews.InstrumentPriceHistory.class})
    private String description;
    @JsonView(ProductViews.InstrumentPriceHistory.class)
    private Date openTimestamp;
    @JsonView(ProductViews.InstrumentPriceHistory.class)
    private String openPrice;
    @JsonView(ProductViews.InstrumentPriceHistory.class)
    private String highPrice;
    @JsonView(ProductViews.InstrumentPriceHistory.class)
    private String lowPrice;
    @JsonView(ProductViews.InstrumentPriceHistory.class)
    private String closePrice;
    @JsonView({ProductViews.InstrumentPrice.class,ProductViews.InstrumentPriceHistory.class})
    private Date closeTimestamp;

}
