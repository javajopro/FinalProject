package com.kh.spring.thing.model.service;

import java.util.List;

import com.kh.spring.thing.model.vo.Category;
import com.kh.spring.thing.model.vo.Product;

public interface ThingService {

	List<Category> selectCategorys();

	Product selectOne(int productNo);

}
