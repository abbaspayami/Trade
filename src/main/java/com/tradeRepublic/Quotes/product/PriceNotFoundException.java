package com.tradeRepublic.Quotes.product;

/**
 * To throw if requested price does not exists in database
 *
 * @author Abbas
 */
public class PriceNotFoundException extends RuntimeException{

    /**
     * constructor
     *
     * @param message given message
     */
    public PriceNotFoundException(String message) {
        super(message);
    }

}
