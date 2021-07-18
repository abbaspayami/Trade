package com.tradeRepublic.Quotes.product;

/**
 * To throw if requested product does not exists in database
 *
 * @author Abbas
 */
public class ProductNotFoundException extends RuntimeException{

    /**
     * constructor
     *
     * @param message given message
     */
    public ProductNotFoundException(String message) {
        super(message);
    }

}
