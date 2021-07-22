package com.tradeRepublic.Quotes.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tradeRepublic.Quotes.dao.entity.Product;
import com.tradeRepublic.Quotes.dao.repository.ProductRepository;
import com.tradeRepublic.Quotes.dto.Quote;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Slf4j
@Component
public class QuotesClient {

    private final StandardWebSocketClient standardWebSocketClient;
    private final ProductRepository productRepository;

    @Value("${gateway.baseurl}")
    private String baseUrl;
    @Value("${gateway.quotes}")
    private String quotes;

    public QuotesClient(ProductRepository productRepository) {
        this.productRepository = productRepository;
        standardWebSocketClient = new StandardWebSocketClient();
    }

    @PostConstruct
    public void postConstruct() throws ExecutionException, InterruptedException {
        start();
    }

    public void start() throws ExecutionException, InterruptedException {
        standardWebSocketClient.doHandshake(new TextWebSocketHandler() {
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
        }, new WebSocketHttpHeaders(), URI.create(baseUrl + quotes)).get();
    }

    public void analyzing(Quote quote) {
        Optional<Product> possibleProduct = productRepository.findByIsin(quote.getData().getIsin());
        if (possibleProduct.isPresent()) {
            Product product = possibleProduct.get();
            if (product.getOpenTimestamp() == null) {
                product.setOpenTimestamp(new Date());
                product.setOpenPrice(quote.getData().getPrice());
                product.setHighPrice(quote.getData().getPrice());
                product.setLowPrice(quote.getData().getPrice());
            }
            if (Float.parseFloat(quote.getData().getPrice()) > Float.parseFloat(product.getHighPrice())) {
                product.setHighPrice(quote.getData().getPrice());
            }
            if (Float.parseFloat(quote.getData().getPrice()) < Float.parseFloat(product.getLowPrice())) {
                product.setLowPrice(quote.getData().getPrice());
            }
            product.setClosePrice(quote.getData().getPrice());
            product.setCloseTimestamp(new Date());
            productRepository.save(product);
        }
    }

}
