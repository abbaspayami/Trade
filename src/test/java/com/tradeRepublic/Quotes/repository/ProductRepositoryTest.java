package com.tradeRepublic.Quotes.repository;

import com.tradeRepublic.Quotes.common.TestUtils;
import com.tradeRepublic.Quotes.dao.entity.Product;
import com.tradeRepublic.Quotes.dao.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    Product productSaved;

    /**
     * First scenario: Just save a Product then fetch from database
     * Second scenario: fetch a non existing Product
     */
    @Test
    void saveAndFindProductTest() {
        Product cartDefault = TestUtils.newProduct();

        productSaved = productRepository.save(cartDefault);
        assertNotNull(productSaved);

        Optional<Product> possibleProduct = productRepository.findByIsin(productSaved.getIsin());

        assertTrue(possibleProduct.isPresent());

        Product product = possibleProduct.get();
        assertEquals(TestUtils.EXISTING_PRODUCT_ID, cartDefault.getId());
        assertEquals("postulant condimentum persecuti", product.getDescription());
        assertEquals("1660.05", product.getClosePrice());
    }

    /**
     * Clear database after test
     */
    @AfterEach
    void clearCartSaved() {
        productRepository.deleteById(productSaved.getId());
    }

}
