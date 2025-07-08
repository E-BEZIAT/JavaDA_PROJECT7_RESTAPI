package com.nnk.springboot.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;


@Entity
@Getter
@Setter
@ToString
@Table(name = "trade")
public class Trade {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int tradeId;
    @Column(length = 30, nullable = false)
    private String account;
    @Column(length = 30, nullable = false)
    private String type;

    private Double buyQuantity;

    private Double sellQuantity;

    private Double buyPrice;

    private Double sellPrice;

    private Timestamp tradeDate;
    @Column(length = 125)
    private String security;
    @Column(length = 10)
    private String status;
    @Column(length = 125)
    private String trader;
    @Column(length = 125)
    private String benchmark;
    @Column(length = 125)
    private String book;
    @Column(length = 125)
    private String creationName;

    private Timestamp creationDate;
    @Column(length = 125)
    private String revisionName;

    private Timestamp revisionDate;
    @Column(length = 125)
    private String dealName;
    @Column(length = 125)
    private String dealType;
    @Column(length = 125)
    private String sourceListId;
    @Column(length = 125)
    private String side;

    public Trade(String account, String type) {
        this.account = account;
        this.type = type;
    }

    public Trade(String account,
                 String type,
                 Double buyQuantity,
                 Double sellQuantity,
                 Double buyPrice,
                 Double sellPrice,
                 Timestamp tradeDate,
                 String security,
                 String status,
                 String trader,
                 String benchmark,
                 String book,
                 String creationName,
                 Timestamp creationDate,
                 String revisionName,
                 Timestamp revisionDate,
                 String dealName,
                 String dealType,
                 String sourceListId,
                 String side
    ){
        this.account = account;
        this.type = type;
        this.buyQuantity = buyQuantity;
        this.sellQuantity = sellQuantity;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.tradeDate = tradeDate;
        this.security = security;
        this.status = status;
        this.trader = trader;
        this.benchmark = benchmark;
        this.book = book;
        this.creationName = creationName;
        this.creationDate = creationDate;
        this.revisionName = revisionName;
        this.revisionDate = revisionDate;
        this.dealName = dealName;
        this.dealType = dealType;
        this.sourceListId = sourceListId;
        this.side = side;
    }

    public Trade() {}
}
