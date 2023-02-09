package com.model2.mvc.view.product;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;



public class ListProductAction extends Action{

	
	public String execute(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		System.out.println("listProductAction����");
		Search search = new Search();
		
		int currentPage=1;
		System.out.println("���� currentPage : "+request.getParameter("currentPage"));
		if(request.getParameter("currentPage") != null && !request.getParameter("currentPage").equals(""))
			currentPage=Integer.parseInt(request.getParameter("currentPage"));
		
		search.setCurrentPage(currentPage);
		search.setSearchCondition(request.getParameter("searchCondition"));
		search.setSearchKeyword(request.getParameter("searchKeyword"));
		
		// web.xml  meta-data �� ���� ��� ���� 
		int pageSize = Integer.parseInt( getServletContext().getInitParameter("pageSize"));
		int pageUnit  =  Integer.parseInt(getServletContext().getInitParameter("pageUnit"));
		search.setPageSize(pageSize);
		
		// Business logic ����
		ProductService service=new ProductServiceImpl();
		Map<String,Object> map=service.getProductList(search);
		
		Page resultPage	= 
		new Page( currentPage, ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println("ListProdcutAction ::"+resultPage);

		System.out.println("listProdcutAction���� map.get(list)"+map.get("list"));
		request.setAttribute("list", map.get("list"));
		request.setAttribute("search", search);
		request.setAttribute("resultPage", resultPage);
		char c = 'm';
		
		//String searchname = "search";
		System.out.println(request.getParameter("menu").charAt(0));
		
		
		
		if(c == request.getParameter("menu").charAt(0))
		return "forward:/product/listProduct.jsp";
		else //if(search == (String)request.getParameter("menu"))
		return "forward:/product/listProductSearch.jsp";
	}

	
}
