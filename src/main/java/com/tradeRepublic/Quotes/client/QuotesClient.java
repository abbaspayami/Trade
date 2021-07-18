package com.tradeRepublic.Quotes.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tradeRepublic.Quotes.dao.entity.Product;
import com.tradeRepublic.Quotes.dao.repository.ProductRepository;
import com.tradeRepublic.Quotes.dto.Quote;
import com.tradeRepublic.Quotes.product.ProductNotFoundException;
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
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Slf4j
@Component
public class QuotesClient {

    private StandardWebSocketClient standardWebSocketClient;
    private WebSocketSession clientSession;
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
                    Product product = isExist(quote);
                    analyzing(product, quote);
                    log.info(quote.toString());
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        }, new WebSocketHttpHeaders(), URI.create("ws://localhost:8080/quotes")).get();
    }

    private Product isExist(Quote quote) {
        Optional<Product> possibleProduct = productRepository.findByIsin(quote.getData().getIsin());
        if (!possibleProduct.isPresent()) {
            throw new ProductNotFoundException("Product Not Found.");
        }
        return possibleProduct.get();
    }

    private void analyzing(Product product, Quote quote) {

        product.setDescription(quote.getData().getPrice());
        product.setIsin(quote.getData().getIsin());
        productRepository.save(product);
    }

}
