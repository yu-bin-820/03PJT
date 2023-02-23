package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.domain.*;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;

public class AddPurchaseAction extends Action{

	public AddPurchaseAction() {
		// TODO Auto-generated constructor stub
	}
	
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println("AddPurchase로 넘어온 request:"+request);
		
		UserService uservice=new UserServiceImpl();
		User user=uservice.getUser(request.getParameter("buyerId"));
		
		ProductService pservice= new ProductServiceImpl();
		Product product=pservice.getProduct(Integer.parseInt(request.getParameter("purchaseProd")));
		
		Purchase purchase=new Purchase();
		purchase.setBuyer(user);
		purchase.setPurchaseProd(product);
		purchase.setPaymentOption(request.getParameter("paymentOption"));
		purchase.setReceiverName(request.getParameter("receiverName"));
		purchase.setReceiverPhone(request.getParameter("receicerPhone"));
		purchase.setDivyAddr(request.getParameter("receiverAddr"));
		purchase.setDivyRequest(request.getParameter("receiverRequest"));
		purchase.setDivyDate(request.getParameter("receiverDate"));
		
		purchase.setTranCode("1");
		
		System.out.println("addPurchaseviewaction에 들어간 VO"+purchase);
		
		PurchaseService service = new PurchaseServiceImpl();
		service.addPurchase(purchase);
		
		request.setAttribute("purchase",purchase);
		
		return "forward:/purchase/addPurchase.jsp";
	}

}
