package com.swcommodities.wsmill.repository;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;

import com.swcommodities.wsmill.hibernate.dto.Exchange;

/**
 * Created by dunguyen on 9/16/16.
 */
public class ExchangeRepositoryImpl implements ExchangeCustom {
    @Autowired
    private ExchangeRepository exchangeDao;

    @Override
    public Exchange getFirstObject() {
        Iterable<Exchange> exchanges = exchangeDao.findAll();
        Iterator iterator = exchanges.iterator();
        Exchange ex0;
        if(iterator.hasNext()) {
            ex0 = (Exchange) iterator.next();
            return ex0;
        }
        return null;
    }
}
