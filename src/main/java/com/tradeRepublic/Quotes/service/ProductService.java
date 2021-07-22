package com.tradeRepublic.Quotes.service;

import com.tradeRepublic.Quotes.dao.entity.Product;
import com.tradeRepublic.Quotes.dao.repository.ProductRepository;
import com.tradeRepublic.Quotes.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The Trade logic implementation,
 * All changes will rollback by any exception
 *
 * @author Abbas
 */
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductDto> getInstrumentPrice() {
        List<Product> all = (List<Product>) productRepository.findAll();
        List<ProductDto> productDtoList = new ArrayList<>();
        for (Product product: all) {
            ProductDto productDto = new ProductDto(product.getIsin(), product.getDescription(), product.getClosePrice());
            productDtoList.add(productDto);
        }
         return productDtoList;
    }

    public List<ProductDto> getInstrumentPriceHistory() {
        List<Product> all = (List<Product>) productRepository.findAll();
        Date dateBefore = new Date(System.currentTimeMillis() - 60 * 30);
        return all.stream().filter(i -> i.getOpenTimestamp()!= null && i.getOpenTimestamp().getTime() > dateBefore.getTime()).map(i ->
                new ProductDto(i.getIsin(), i.getDescription(), i.getOpenTimestamp(), i.getOpenPrice(), i.getHighPrice()
                        , i.getLowPrice(), i.getClosePrice(), i.getCloseTimestamp())).collect(Collectors.toList());
    }


}
