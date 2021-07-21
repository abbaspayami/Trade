package com.tradeRepublic.Quotes.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.tradeRepublic.Quotes.dao.entity.Product;
import com.tradeRepublic.Quotes.dao.repository.ProductRepository;
import com.tradeRepublic.Quotes.dto.ProductDto;
import com.tradeRepublic.Quotes.dto.ProductViews;
import com.tradeRepublic.Quotes.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.jni.Proc;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class InstrumentController {

    private final ProductService productService;
    /**
     * Finding Cart Object
     * @return Cart Object
     */
    @GetMapping(value = "/instrumentPrice")
    @JsonView(ProductViews.InstrumentPrice.class)
    public ResponseEntity<List<ProductDto>> getInstrumentPrice() {
        return new ResponseEntity<>(productService.getInstrumentPrice(), HttpStatus.OK);
    }

    /**
     * Finding Cart Object
     * @return Cart Object
     */
    @GetMapping(value = "/instrumentPriceHistory")
    @JsonView(ProductViews.InstrumentPriceHistory.class)
    public ResponseEntity<List<ProductDto>> getInstrumentPriceHistory() {
        return new ResponseEntity<>(productService.getInstrumentPriceHistory(), HttpStatus.OK);
    }

}
