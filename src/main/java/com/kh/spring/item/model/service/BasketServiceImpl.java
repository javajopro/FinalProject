package com.kh.spring.item.model.service;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kh.spring.item.model.dao.BasketDao;
import com.kh.spring.item.model.vo.Basket;

@Service
public class BasketServiceImpl implements BasketService {

	Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	BasketDao basketDao;

	@Override
	public int countBasket(int productId, String userId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void insert(Basket vo) {
		basketDao.insert(vo);
	}

	@Override
	public void updateBasket(Basket vo) {
		// TODO Auto-generated method stub
		
	}




	
}