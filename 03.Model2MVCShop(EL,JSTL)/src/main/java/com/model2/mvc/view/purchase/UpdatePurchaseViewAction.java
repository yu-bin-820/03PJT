package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.domain.*;

public class UpdatePurchaseViewAction extends Action {

	public UpdatePurchaseViewAction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println("UpdatePurachaseViewAction시작");
		
		int tranNo=Integer.parseInt(request.getParameter("tranNo"));
		
		PurchaseService service = new PurchaseServiceImpl();
		Purchase purchase = service.getPurchase(tranNo);
		
		System.out.println("DAO마치고 Action");
		request.setAttribute("purchase",purchase);
		
		return "forward:/purchase/updatePurchase.jsp";
	}

}
