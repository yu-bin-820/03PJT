package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class GetProductAction extends Action{
	public String execute(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		int prodNo=Integer.parseInt(request.getParameter("prodNo")); // parameter값
		System.out.println("GetProductAction에서 ProdNo : "+prodNo);
		
		ProductService service = new ProductServiceImpl();
		
		Product vo = service.getProduct(prodNo);
		System.out.println(vo);
		request.setAttribute("vo", vo);
		System.out.println(vo);
		
		
		return "forward:/product/getProduct.jsp"; // JSP 입력
	}
}
