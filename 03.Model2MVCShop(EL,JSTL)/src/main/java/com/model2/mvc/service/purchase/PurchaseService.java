package com.model2.mvc.service.purchase;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;
import java.util.HashMap;
import java.util.Map;

public interface PurchaseService {

	public void addPurchase(Purchase purchase) throws Exception;
		
	public Purchase getPurchase(int tranNo) throws Exception;
	
	public Map<String, Object> getPurchaseList(Search search, String string) throws Exception;
	
	public Map<String, Object> getSaleList(Search search) throws Exception;
	
	public void updatePurchase(Purchase purchase) throws Exception;
	
	public void updateTranCode(Purchase purchase) throws Exception;
	
}
