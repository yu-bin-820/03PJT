package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class UpdateProductAction extends Action {
	public String execute(	HttpServletRequest request,HttpServletResponse response) throws Exception {
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		//System.out.println("EE"+prodNo);

		//UpdateProductViewAction VO = new UpdateProductViewAction();
		Product product = new Product();
		User userVO = new User();
		//VO.execute(request, response);
		
		String userId=(String)request.getParameter("userId");
		//int prodNo = Integer.parseInt(request.getParameter("prodNo"));

		
		//System.out.println("EE"+productVO);
		//service.getProduct(prodNo);
		product.setProdNo(prodNo);
		
		product.setProdName(request.getParameter("prodName"));
		product.setProdDetail(request.getParameter("prodDetail"));
		product.setManuDate(request.getParameter("manuDate"));
		
		product.setPrice(Integer.parseInt(request.getParameter("price")));
		product.setFileName(request.getParameter("fileName"));
		//¹ÙÀÎµù
		
		ProductService service = new ProductServiceImpl();
		service.updateProduct(product);
		System.out.println("EE"+product);
		
		
		
		/*HttpSession session = request.getSession();
		int sessionId=((ProductVO)session.getAttribute("prodNo")).getProdNo();
		
		if(sessionId == prodNo){
			session.setAttribute("prodNo", productVO);
		}*/

		return "redirect:/getProduct.do?prodNo="+prodNo;
	}
}
