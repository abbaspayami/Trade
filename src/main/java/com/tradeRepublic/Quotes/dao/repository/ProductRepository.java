package com.tradeRepublic.Quotes.dao.repository;

import com.tradeRepublic.Quotes.dao.entity.Price;
import com.tradeRepublic.Quotes.dao.entity.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {
    Optional<Product> findByIsin(String isin);
    @Transactional
    void deleteByIsin(String isin);
}
