package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.parameters.BidListParameter;
import com.nnk.springboot.domain.response.BidListDTO;
import com.nnk.springboot.repositories.BidListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BidListService {

    @Autowired
    private BidListRepository bidListRepository;

    public BidListService(BidListRepository bidListRepository) {
        this.bidListRepository = bidListRepository;
    }

    /**
     * permet de créer un BidList
     *
     * @param bidListParameter body à remplir lors de la création du BidList
     */
    public void createBidList(BidListParameter bidListParameter) {
        BidList bidList = new BidList(
                bidListParameter.getAccount(),
                bidListParameter.getType(),
                bidListParameter.getBidQuantity(),
                bidListParameter.getAskQuantity(),
                bidListParameter.getBid(),
                bidListParameter.getAsk(),
                bidListParameter.getBenchmark(),
                bidListParameter.getBidListDate(),
                bidListParameter.getCommentary(),
                bidListParameter.getSecurity(),
                bidListParameter.getStatus(),
                bidListParameter.getTrader(),
                bidListParameter.getBook(),
                bidListParameter.getCreationName(),
                bidListParameter.getCreationDate(),
                bidListParameter.getRevisionName(),
                bidListParameter.getRevisionDate(),
                bidListParameter.getDealName(),
                bidListParameter.getDealType(),
                bidListParameter.getSourceListId(),
                bidListParameter.getSide()
        );

        bidListRepository.save(bidList);
    }

    /**
     * Permet d'update un BidList
     *
     * @param bidListParameter body à remplir lors de l'update
     * @param id id du BidList à update
     */
    public void updateBidList(BidListParameter bidListParameter, int id) {
        BidList bidList = bidListRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("bidList not found"));

        bidList.setAccount(bidListParameter.getAccount());
        bidList.setType(bidListParameter.getType());
        bidList.setBidQuantity(bidListParameter.getBidQuantity());
        bidList.setAskQuantity(bidListParameter.getAskQuantity());
        bidList.setBid(bidListParameter.getBid());
        bidList.setAsk(bidListParameter.getAsk());
        bidList.setBenchmark(bidListParameter.getBenchmark());
        bidList.setBidListDate(bidListParameter.getBidListDate());
        bidList.setCommentary(bidListParameter.getCommentary());
        bidList.setSecurity(bidListParameter.getSecurity());
        bidList.setStatus(bidListParameter.getStatus());
        bidList.setTrader(bidListParameter.getTrader());
        bidList.setBook(bidListParameter.getBook());
        bidList.setCreationName(bidListParameter.getCreationName());
        bidList.setCreationDate(bidListParameter.getCreationDate());
        bidList.setRevisionName(bidListParameter.getRevisionName());
        bidList.setRevisionDate(bidListParameter.getRevisionDate());
        bidList.setDealName(bidListParameter.getDealName());
        bidList.setDealType(bidListParameter.getDealType());
        bidList.setSourceListId(bidListParameter.getSourceListId());
        bidList.setSide(bidListParameter.getSide());

        bidListRepository.save(bidList);
    }

    /**
     * Permet de supprimer un BidList
     *
     * @param id id du BidList à supprimer
     */
    public void deleteBidList(int id) {
        BidList bidList = bidListRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("bidList not found"));

        bidListRepository.delete(bidList);
    }

    /**
     * Permet de récupérer et lire un BidList
     *
     * @param id id du BidList à lire et récupérer
     * @return un objet DTO qui contient les données nécessaires à la vue ou à l’API
     */
    public BidListDTO readBidList(int id) {
        BidList bidList = bidListRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("bidList not found"));

        return new BidListDTO(
                bidList.getBidListId(),
                bidList.getAccount(),
                bidList.getType(),
                bidList.getBidQuantity(),
                bidList.getAskQuantity(),
                bidList.getBid(),
                bidList.getAsk(),
                bidList.getBenchmark(),
                bidList.getBidListDate(),
                bidList.getCommentary(),
                bidList.getSecurity(),
                bidList.getStatus(),
                bidList.getTrader(),
                bidList.getBook(),
                bidList.getCreationName(),
                bidList.getCreationDate(),
                bidList.getRevisionName(),
                bidList.getRevisionDate(),
                bidList.getDealName(),
                bidList.getDealType(),
                bidList.getSourceListId(),
                bidList.getSide()
        );
    }
}
