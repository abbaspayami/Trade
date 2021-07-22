package com.tradeRepublic.Quotes.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tradeRepublic.Quotes.dao.entity.Product;
import com.tradeRepublic.Quotes.dao.repository.ProductRepository;
import com.tradeRepublic.Quotes.dto.Instrument;
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
import java.util.concurrent.ExecutionException;

/**
 * this class for reading from websocket of address ip:port/instrument
 * for reading all instrument
 *
 * @author Abbas
 */
@Slf4j
@Component
public class InstrumentsClient {

    private final StandardWebSocketClient standardWebSocketClient;
    private final ProductRepository productRepository;

    @Value("${gateway.baseurl}")
    private String baseUrl;
    @Value("${gateway.instrument}")
    private String quotes;
    @Value("DELETE")
    private String deleteType;

    public InstrumentsClient(ProductRepository productRepository) {
        this.productRepository = productRepository;
        standardWebSocketClient = new StandardWebSocketClient();
    }

    @PostConstruct
    public void postConstruct() throws ExecutionException, InterruptedException {
        start();
    }

    /**
     * for starting to read from websocket and convert to object mapper
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public void start() throws ExecutionException, InterruptedException {
        standardWebSocketClient.doHandshake(new TextWebSocketHandler() {
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
        }, new WebSocketHttpHeaders(), URI.create(baseUrl + quotes)).get();
    }

    /**
     * for analyzing json read and then delete or inserting into database h2
     * @param instrument
     */
    public void analyzing(Instrument instrument) {
        if (instrument.getType().equals(deleteType)) {
            productRepository.deleteByIsin(instrument.getData().getIsin());
        }
        Product product = new Product();
        product.setDescription(instrument.getData().getDescription());
        product.setIsin(instrument.getData().getIsin());
        productRepository.save(product);
    }


}
