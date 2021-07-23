package com.tradeRepublic.Quotes.service;

import com.tradeRepublic.Quotes.dao.entity.Product;
import com.tradeRepublic.Quotes.dao.repository.ProductRepository;
import com.tradeRepublic.Quotes.dto.ProductDto;
import com.tradeRepublic.Quotes.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        for (Product product : all) {
            ProductDto productDto = new ProductDto(product.getIsin(), product.getDescription(), product.getClosePrice());
            productDtoList.add(productDto);
        }
        return productDtoList;
    }

    public ProductDto getInstrumentPriceHistory(String isin) {
        Optional<Product> possibleProduct = productRepository.findByIsin(isin);
        if (!possibleProduct.isPresent()) {
            throw new ProductNotFoundException("Product Not Found.");
        }

        Product product = possibleProduct.get();
        return new ProductDto(product.getOpenTimestamp(), product.getOpenPrice(),
                product.getHighPrice(), product.getLowPrice(), product.getClosePrice(), product.getCloseTimestamp());
    }


}
