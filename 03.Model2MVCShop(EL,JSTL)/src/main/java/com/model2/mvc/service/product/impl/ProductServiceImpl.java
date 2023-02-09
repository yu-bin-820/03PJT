package com.model2.mvc.service.product.impl;

import java.util.HashMap;
import java.util.Map;

import com.model2.mvc.service.product.dao.ProductDAO;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

public class ProductServiceImpl implements ProductService{

	private ProductDAO productDAO;
	public ProductServiceImpl() {
		productDAO = new ProductDAO();
	}
	
	@Override
	public void addProduct(Product product) throws Exception {
		// TODO Auto-generated method stub
		productDAO.insertProduct(product);
	}

	@Override
	public Product getProduct(int productNo) throws Exception {
		// TODO Auto-generated method stub
		return productDAO.findProduct(productNo);
	}

	@Override
	public Map<String, Object> getProductList(Search search) throws Exception {
		// TODO Auto-generated method stub
		return productDAO.getProductList(search);
	}

	@Override
	public void updateProduct(Product product) throws Exception {
		// TODO Auto-generated method stub
		productDAO.updateProduct(product);
	}
	
	public boolean checkDuplication(int productNo) throws Exception {
		boolean result=true;
		Product product = productDAO.findProduct(productNo);
		if(product != null) {
			result=false;
		}
		return result;
	}
	
	
}
