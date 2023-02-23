package com.model2.mvc.view.product;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;



public class ListProductAction extends Action{

	
	public String execute(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		System.out.println("listProductAction시작");
		Search search = new Search();
		
		int currentPage=1;
		System.out.println("받은 currentPage : "+request.getParameter("currentPage"));
		if(request.getParameter("currentPage") != null && !request.getParameter("currentPage").equals(""))
			currentPage=Integer.parseInt(request.getParameter("currentPage"));
		
		search.setCurrentPage(currentPage);
		search.setSearchCondition(request.getParameter("searchCondition"));
		search.setSearchKeyword(request.getParameter("searchKeyword"));
		
		// web.xml  meta-data 로 부터 상수 추출 
		int pageSize = Integer.parseInt( getServletContext().getInitParameter("pageSize"));
		int pageUnit  =  Integer.parseInt(getServletContext().getInitParameter("pageUnit"));
		search.setPageSize(pageSize);
		
		// Business logic 수행
		ProductService service=new ProductServiceImpl();
		Map<String,Object> map=service.getProductList(search);
		
		Page resultPage	= 
		new Page( currentPage, ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println("ListProdcutAction ::"+resultPage);

		System.out.println("listProdcutAction에서 map.get(list)"+map.get("list"));
		request.setAttribute("list", map.get("list"));
		request.setAttribute("search", search);
		request.setAttribute("resultPage", resultPage);
		System.out.println("jsp넘겨줄 "+map.get("list"));
		if(request.getParameter("menu").equals("manage")) {
			
			return "forward:/product/listProduct.jsp";
		
		}else {
			HttpSession session=request.getSession();
			User user = (User)session.getAttribute("user");
			request.setAttribute("user", user);
			
			return "forward:/product/listProductSearch.jsp";
		}
	}

	
}
