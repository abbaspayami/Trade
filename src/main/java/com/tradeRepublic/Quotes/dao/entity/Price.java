package com.tradeRepublic.Quotes.dao.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "price")
@Getter
@Setter
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "price_Id")
    private Integer id;

    private Date openTimestamp;
    private Float openPrice;
    private Float highPrice;
    private Float lowPrice;
    private Float closePrice;
    private Date closeTimestamp;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "isin", nullable = false)
    private Product product;


}
