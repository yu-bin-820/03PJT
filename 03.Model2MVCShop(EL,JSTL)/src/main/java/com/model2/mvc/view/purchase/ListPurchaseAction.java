package com.model2.mvc.view.purchase;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;
import com.model2.mvc.service.domain.*;

public class ListPurchaseAction extends Action {

	public ListPurchaseAction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Search search=new Search();
		
		int currentPage=1;
		if(request.getParameter("currentPage") != null && request.getParameter("currentPage").equals("")){
			currentPage=Integer.parseInt(request.getParameter("currentPage"));
		}
		System.out.println(currentPage);
		search.setCurrentPage(currentPage);
		search.setSearchCondition(request.getParameter("searchCondition"));
		search.setSearchKeyword(request.getParameter("searchKeyword"));
		
		// web.xml  meta-data 로 부터 상수 추출 
		int pageSize = Integer.parseInt( getServletContext().getInitParameter("pageSize"));//3
		int pageUnit  =  Integer.parseInt(getServletContext().getInitParameter("pageUnit"));//5
		search.setPageSize(pageSize);//3
		
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		System.out.println(user);
		String userId = user.getUserId();
		PurchaseService service=new PurchaseServiceImpl();
		Map<String,Object> map=service.getPurchaseList(search, userId);
		System.out.println();
		Page resultPage	= 
			new Page( currentPage, ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);

		request.setAttribute("map", map.get("list"));
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("search", search);
		
		System.out.println("request에 담긴 list"+map.get("list"));
		System.out.println("request에 담긴 resultPage"+resultPage);
		
		return "forward:/purchase/listPurchase.jsp";
	}

}
