package com.tradeRepublic.Quotes.dao.repository;

import com.tradeRepublic.Quotes.dao.entity.Price;
import org.springframework.data.repository.CrudRepository;

public interface PriceRepository extends CrudRepository<Price, Integer> {
}
