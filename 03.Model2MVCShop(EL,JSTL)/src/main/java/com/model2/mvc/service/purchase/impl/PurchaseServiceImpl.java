package com.model2.mvc.service.purchase.impl;

import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.product.dao.ProductDAO;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.dao.PurchaseDAO;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.dao.*;
//import com.model2.mvc.service.purchase.PurchaseService;

public class PurchaseServiceImpl implements PurchaseService{
	
	private ProductDAO productDAO;
	private PurchaseDAO purchaseDAO;
	
	public PurchaseServiceImpl() {
		productDAO = new ProductDAO();
		purchaseDAO = new PurchaseDAO();
	}
	
	//public PurchaseVO addPurchase(PurchaseVO purchaseVO) throws Exception {
		
		
		//return 
	//}
	public Purchase getPurchase(int tranNo) throws Exception {
		//System.out.println(purchaseDAO.findPurchase(prodNo).getPurchaseProd());
		
		return purchaseDAO.findPurchase(tranNo);
	}

	@Override
	public void addPurchase(Purchase purchase) throws Exception{
		// TODO Auto-generated method stub
		 purchaseDAO.insertPurchase(purchase);
	}


	@Override
	public Map<String, Object> getPurchaseList(Search search, String userId) throws Exception {
		return ((PurchaseDAO) purchaseDAO).getPurchaseList(search, userId);
	}

	@Override
	public Map<String, Object> getSaleList(Search searchvo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updatePurchase(Purchase purchase) throws Exception {
		purchaseDAO.updatePurchase(purchase);	
	}

	@Override
	public void updateTranCode(Purchase purchase) throws Exception {
		// TODO Auto-generated method stub
		purchaseDAO.updateTranCode(purchase);	
	}

}
