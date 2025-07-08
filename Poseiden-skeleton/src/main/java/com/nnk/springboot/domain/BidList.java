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
@Table(name = "bidlist")
public class BidList {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "BidListId")
    private int bidListId;
    @Column(nullable = false, length = 30)
    private String account;
    @Column(nullable = false, length = 30)
    private String type;

    private Double bidQuantity;

    private Double askQuantity;

    private Double bid;

    private Double ask;
    @Column(length = 125)
    private String benchmark;

    private Timestamp bidListDate;
    @Column(length = 125)
    private String commentary;
    @Column(length = 125)
    private String security;
    @Column(length = 10)
    private String status;
    @Column(length = 125)
    private String trader;
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

    public BidList() {}

    public BidList(String account, String type, Double bidQuantity) {
        this.account = account;
        this.type = type;
        this.bidQuantity = bidQuantity;
    }

    public BidList(String account,
                   String type,
                   Double bidQuantity,
                   Double askQuantity,
                   Double bid,
                   Double ask,
                   String benchmark,
                   Timestamp bidListDate,
                   String commentary,
                   String security,
                   String status,
                   String trader,
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
        this.bidQuantity = bidQuantity;
        this.askQuantity = askQuantity;
        this.bid = bid;
        this.ask = ask;
        this.benchmark = benchmark;
        this.bidListDate = bidListDate;
        this.commentary = commentary;
        this.security = security;
        this.status = status;
        this.trader = trader;
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

}
