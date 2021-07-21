package com.tradeRepublic.Quotes.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tradeRepublic.Quotes.dao.entity.Price;
import com.tradeRepublic.Quotes.dao.entity.Product;
import com.tradeRepublic.Quotes.dao.repository.PriceRepository;
import com.tradeRepublic.Quotes.dao.repository.ProductRepository;
import com.tradeRepublic.Quotes.dto.Quote;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.net.URI;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Slf4j
@Component
public class QuotesClient {

    private StandardWebSocketClient standardWebSocketClient;
    private WebSocketSession clientSession;
    @Autowired
    private PriceRepository priceRepository;
    @Autowired
    private ProductRepository productRepository;

    public QuotesClient() throws ExecutionException, InterruptedException {
        standardWebSocketClient = new StandardWebSocketClient();
    }

    @PostConstruct
    public void postConstruct() throws ExecutionException, InterruptedException {
        start();
    }

    public void start() throws ExecutionException, InterruptedException {
        this.clientSession = standardWebSocketClient.doHandshake(new TextWebSocketHandler() {
            @Override
            protected void handleTextMessage(WebSocketSession session, TextMessage message) {
                try {
                    ObjectMapper om = new ObjectMapper();
                    Quote quote = om.readValue(message.getPayload(), Quote.class);
                    analyzing(quote);
                    log.info(quote.toString());
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        }, new WebSocketHttpHeaders(), URI.create("ws://localhost:8080/quotes")).get();
    }

    @Transactional
    public void analyzing(Quote quote) {
        Optional<Price> possibleProduct = priceRepository.findByIsin(quote.getData().getIsin());
        if (possibleProduct.isPresent()) {
            Price price = possibleProduct.get();
            if (Float.parseFloat(quote.getData().getPrice()) > Float.parseFloat(price.getHighPrice())) {
                price.setHighPrice(quote.getData().getPrice());
            }
            if (Float.parseFloat(quote.getData().getPrice()) < Float.parseFloat(price.getLowPrice())) {
                price.setLowPrice(quote.getData().getPrice());
            }
            price.setClosePrice(quote.getData().getPrice());
            price.setCloseTimestamp(new Date());
            priceRepository.save(price);
        }
        else {
            Price price = new Price();
            price.setIsin(quote.getData().getIsin());
            price.setOpenTimestamp(new Date());
            price.setOpenPrice(quote.getData().getPrice());
            price.setHighPrice(quote.getData().getPrice());
            price.setLowPrice(quote.getData().getPrice());
            price.setClosePrice(quote.getData().getPrice());
            price.setCloseTimestamp(new Date());
            priceRepository.save(price);
        }
    }

}
