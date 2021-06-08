package com.projectBootcamp.service;

import com.projectBootcamp.exceptions.BusinessException;
import com.projectBootcamp.mapper.StockMapper;
import com.projectBootcamp.model.Stock;
import com.projectBootcamp.model.dto.StockDTO;
import com.projectBootcamp.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class StockService {

    @Autowired
    private StockRepository repository;

    @Autowired
    private StockMapper mapper;


    @Transactional
    public StockDTO save(StockDTO dto) {

        Optional<Stock> optionalStock = repository.findByNameAndDate(dto.getName(), dto.getDate());
        if(optionalStock.isPresent()){
                throw new BusinessException("MessageUtils.STOCK_ALREADY_EXISTS");
        }
        Stock stock = mapper.toEntity(dto);
        repository.save(stock);
        dto.setId(stock.getId());
        return mapper.toDto(stock);

    }
}
