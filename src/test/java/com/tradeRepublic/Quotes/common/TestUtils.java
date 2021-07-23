package com.tradeRepublic.Quotes.common;

import com.tradeRepublic.Quotes.dao.entity.Product;

import java.util.Date;

public class TestUtils {

    public static final int EXISTING_PRODUCT_ID = 1;
    public static final String EXISTING_PRODUCT_ISIN = "BW5235653587";

    public static final String BASE_URL = "http://localhost:8080/api";
    public static final String INSTRUMENT_PRICE = "/instrumentPrice";
    public static final String INSTRUMENT_PRICE_HISTORY = "/instrumentPriceHistory";

    public static Product newProduct() {
        Product product = new Product();

        product.setId(EXISTING_PRODUCT_ID);
        product.setIsin("BW5235653587");
        product.setOpenTimestamp(new Date());
        product.setCloseTimestamp(new Date());
        product.setDescription("postulant condimentum persecuti");
        product.setClosePrice("1660.05");
        return product;
    }

}
