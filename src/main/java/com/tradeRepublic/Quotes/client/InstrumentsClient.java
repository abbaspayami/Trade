package com.tradeRepublic.Quotes.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tradeRepublic.Quotes.dao.entity.Product;
import com.tradeRepublic.Quotes.dao.repository.ProductRepository;
import com.tradeRepublic.Quotes.dto.Instrument;
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
import java.util.concurrent.ExecutionException;

@Slf4j
@Component
public class InstrumentsClient {

    private StandardWebSocketClient standardWebSocketClient;
    private WebSocketSession clientSession;
    @Autowired
    private  ProductRepository productRepository;

    public InstrumentsClient() throws ExecutionException, InterruptedException {
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
                    Instrument instrument = om.readValue(message.getPayload(), Instrument.class);
                    analyzing(instrument);
                    log.info(instrument.toString());
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        }, new WebSocketHttpHeaders(), URI.create("ws://localhost:8080/instruments")).get();
    }
    @Transactional(Transactional.TxType.REQUIRED)
    public void analyzing(Instrument instrument) {
        if (instrument.getType().equals("DELETE")) {
            productRepository.deleteByIsin(instrument.getData().getIsin());
        }
        Product product = new Product();
        product.setDescription(instrument.getData().getDescription());
        product.setIsin(instrument.getData().getIsin());
        productRepository.save(product);
    }


}
