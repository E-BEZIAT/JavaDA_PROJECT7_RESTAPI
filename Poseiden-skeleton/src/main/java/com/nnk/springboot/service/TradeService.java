package com.nnk.springboot.service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.parameters.TradeParameter;
import com.nnk.springboot.domain.response.TradeDTO;
import com.nnk.springboot.repositories.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TradeService {

    @Autowired
    private TradeRepository tradeRepository;

    public void createTrade(TradeParameter tradeParameter) {

        Trade trade = new Trade(
                tradeParameter.getAccount(),
                tradeParameter.getType(),
                tradeParameter.getBuyQuantity(),
                tradeParameter.getSellQuantity(),
                tradeParameter.getBuyPrice(),
                tradeParameter.getSellPrice(),
                tradeParameter.getTradeDate(),
                tradeParameter.getSecurity(),
                tradeParameter.getStatus(),
                tradeParameter.getTrader(),
                tradeParameter.getBenchmark(),
                tradeParameter.getBook(),
                tradeParameter.getCreationName(),
                tradeParameter.getCreationDate(),
                tradeParameter.getRevisionName(),
                tradeParameter.getRevisionDate(),
                tradeParameter.getDealName(),
                tradeParameter.getDealType(),
                tradeParameter.getSourceListId(),
                tradeParameter.getSide()
        );

        tradeRepository.save(trade);
    }

    public void updateTrade(TradeParameter tradeParameter, int id) {
        Trade trade = tradeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Trade not found"));

        trade.setAccount(tradeParameter.getAccount());
        trade.setType(tradeParameter.getType());
        trade.setBuyQuantity(tradeParameter.getBuyQuantity());
        trade.setSellQuantity(tradeParameter.getSellQuantity());
        trade.setBuyPrice(tradeParameter.getBuyPrice());
        trade.setSellPrice(tradeParameter.getSellPrice());
        trade.setTradeDate(tradeParameter.getTradeDate());
        trade.setSecurity(tradeParameter.getSecurity());
        trade.setStatus(tradeParameter.getStatus());
        trade.setTrader(tradeParameter.getTrader());
        trade.setBenchmark(tradeParameter.getBenchmark());
        trade.setBook(tradeParameter.getBook());
        trade.setCreationName(tradeParameter.getCreationName());
        trade.setCreationDate(tradeParameter.getCreationDate());
        trade.setRevisionName(tradeParameter.getRevisionName());
        trade.setRevisionDate(tradeParameter.getRevisionDate());
        trade.setDealName(tradeParameter.getDealName());
        trade.setDealType(tradeParameter.getDealType());
        trade.setSourceListId(tradeParameter.getSourceListId());
        trade.setSide(tradeParameter.getSide());
        tradeRepository.save(trade);
    }

    public void deleteTrade(int id) {
        Trade trade = tradeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Trade not found"));

        tradeRepository.delete(trade);
    }

    public TradeDTO readTrade(int id) {
        Trade trade = tradeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Trade not found"));

        return new TradeDTO(
                trade.getTradeId(),
                trade.getAccount(),
                trade.getType(),
                trade.getBuyQuantity(),
                trade.getSellQuantity(),
                trade.getBuyPrice(),
                trade.getSellPrice(),
                trade.getTradeDate(),
                trade.getSecurity(),
                trade.getStatus(),
                trade.getTrader(),
                trade.getBenchmark(),
                trade.getBook(),
                trade.getCreationName(),
                trade.getCreationDate(),
                trade.getRevisionName(),
                trade.getRevisionDate(),
                trade.getDealName(),
                trade.getDealType(),
                trade.getSourceListId(),
                trade.getSide()
        );
    }
}
