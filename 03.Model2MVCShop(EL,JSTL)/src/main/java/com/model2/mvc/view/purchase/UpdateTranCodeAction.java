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


public class UpdateTranCodeAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("UpdateTrancodeActionΩ√¿€");
		System.out.println(request.getParameter("tranCode"));
		
		Purchase purchase = new Purchase();
		purchase.setTranNo(Integer.parseInt(request.getParameter("tranNo")));
		purchase.setTranCode(request.getParameter("tranCode"));
		System.out.println(purchase);
		PurchaseService service = new PurchaseServiceImpl();
		service.updateTranCode(purchase);
		
		
		return "redirect:/listPurchase.do";
	}

}
