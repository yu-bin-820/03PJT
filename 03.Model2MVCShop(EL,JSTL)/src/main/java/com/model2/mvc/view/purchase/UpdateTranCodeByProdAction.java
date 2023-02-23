package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.domain.*;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class UpdateTranCodeByProdAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("UpdateTrancodeActionΩ√¿€");
		
		ProductService proservice = new ProductServiceImpl();
		Product product = proservice.getProduct(Integer.parseInt(request.getParameter("prodNo")));
		
		Purchase purchase = new Purchase();
		purchase.setPurchaseProd(product);
		purchase.setTranCode(request.getParameter("tranCode"));
		System.out.println(purchase);
		PurchaseService service = new PurchaseServiceImpl();
		service.updateTranCode(purchase);
		
		
		return "redirect:/listProduct.do?menu=manage";
	}

}
