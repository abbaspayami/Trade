package com.tradeRepublic.Quotes.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.tradeRepublic.Quotes.dto.ProductDto;
import com.tradeRepublic.Quotes.dto.ProductViews;
import com.tradeRepublic.Quotes.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Instrument controller for getting instrument price and the price history of the 30 minutes
 * 2 endPoint /instrumentPrice and /instrumentPriceHistory
 *
 * @author Abbas
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class InstrumentController {

    private final ProductService productService;

    /**
     * You can see all the Instrument along with the latest prices
     *
     * @return List ProductDto Object
     */
    @GetMapping(value = "/instrumentPrice")
    @JsonView(ProductViews.InstrumentPrice.class)
    public ResponseEntity<List<ProductDto>> getInstrumentPrice() {
        return new ResponseEntity<>(productService.getInstrumentPrice(), HttpStatus.OK);
    }

    /**
     * You can See THE PRICE HISTORY OF the last 30 minutes
     * return the Candlesticks for the last 30 minutes
     *
     * @return List ProductDto Object
     */
    @GetMapping(value = "/instrumentPriceHistory/{isin}")
    @JsonView(ProductViews.InstrumentPriceHistory.class)
    public ResponseEntity<ProductDto> getInstrumentPriceHistory(@PathVariable String isin) {
        return new ResponseEntity<>(productService.getInstrumentPriceHistory(isin), HttpStatus.OK);
    }

}
