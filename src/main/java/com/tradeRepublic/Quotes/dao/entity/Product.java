package com.tradeRepublic.Quotes.dao.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "product")
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    private String isin;

    private String description;

    private Date openTimestamp;
    private String openPrice;
    private String highPrice;
    private String lowPrice;
    private String closePrice;
    private Date closeTimestamp;

}
