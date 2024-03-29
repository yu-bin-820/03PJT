<%@ page language="java" contentType="text/html; charset=EUC-KR"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- /////////////////////// EL / JSTL 적용으로 주석 처리 ////////////////////////<%@ page import="java.util.*" %>
<%@ page import="com.model2.mvc.service.domain.*" %>
<%@ page import="com.model2.mvc.common.*" %>
<%@ page import="com.model2.mvc.common.util.*" %>


<% 
	List<Product> list= (List<Product>)request.getAttribute("list");
	Page resultPage=(Page)request.getAttribute("resultPage");

	Search search = (Search)request.getAttribute("search");
	//==> null 을 ""(nullString)으로 변경
	String searchCondition = CommonUtil.null2str(search.getSearchCondition());
	String searchKeyword = CommonUtil.null2str(search.getSearchKeyword());
	
	User user=(User)request.getAttribute("user");
	
	String role="";

	if(user != null) {
		role=user.getRole();
	}
%>/////////////////////// EL / JSTL 적용으로 주석 처리 //////////////////////// --%>


<html>
<head>
<title>상품 목록조회</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">
<!--
function fncGetProductList(currentPage){
	document.getElementById("currentPage").value = currentPage;
	document.detailForm.submit();
}
-->
</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width:98%; margin-left:10px;">

<form name="detailForm" action="/listProduct.do?menu=search" method="post">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37">
			<img src="/images/ct_ttl_img01.gif" width="15" height="37"/>
		</td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">
					
							상품 목록 조회
					
					</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37">
			<img src="/images/ct_ttl_img03.gif" width="12" height="37"/>
		</td>
	</tr>
</table>


<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="right">
			<select name="searchCondition" class="ct_input_g" style="width:80px">
				<%-- /////////////////////// EL / JSTL 적용으로 주석 처리 ////////////////////////
				<option value="0" <%= (searchCondition.equals("0") ? "selected" : "")%>>상품번호</option>
				<option value="1" <%= (searchCondition.equals("1") ? "selected" : "")%>>상품명</option>
				<option value="2" <%= (searchCondition.equals("2") ? "selected" : "")%>>상품가격</option>
				/////////////////////// EL / JSTL 적용으로 주석 처리 //////////////////////// --%>
				<option value="0" ${ ! empty search.searchCondition && search.searchCondition==0 ? "selected" : "" }>상품번호</option>
				<option value="1" ${ ! empty search.searchCondition && search.searchCondition==1 ? "selected" : "" }>상품명</option>
				<option value="2" ${ ! empty search.searchCondition && search.searchCondition==2 ? "selected" : "" }>상품가격</option>
			</select>
			<input 	type="text" name="searchKeyword" value="${ ! empty search.searchKeyword ? search.searchKeyword : ""}"  class="ct_input_g" 
							style="width:200px; height:20px" >
		</td>
		<td align="right" width="70">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23">
					</td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
						<a href="javascript:fncGetProductList('${search.currentPage}');">검색</a>
					</td>
					<td width="14" height="23">
						<img src="/images/ct_btnbg03.gif" width="14" height="23">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>


<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td colspan="11" >
		전체  ${ resultPage.totalCount} 건수,	현재 ${ resultPage.currentPage} 페이지
		</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">상품명</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">가격</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">등록일</td>	
		<td class="ct_line02"></td>
		<td class="ct_list_b">현재상태</td>	
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
	<%-- /////////////////////// EL / JSTL 적용으로 주석 처리 ////////////////////////
	<%
			for(int i=0; i<list.size(); i++) {
				Product product = (Product)list.get(i);
				//PurchaseVO purvo ;
		%>
				<%  if(role.equals("admin")){ %>
		<tr class="ct_list_pop">
			<td align="center"><%=i+1%></td>
			<td></td>
			<td align="left">
				<a href="/getProduct.do?prodNo=<%=product.getProdNo() %>"><%= product.getProdName() %></a>
			</td>
			<td></td>
			<td align="left"><%= product.getPrice() %></td>
			<td></td>
			<td align="left"><%= product.getRegDate() %></td>
			<td></td>
			<td align="left">
			<%if(product.getProTranCode().equals("0")||product.getProTranCode().equals("")||product.getProTranCode()==null){ %>
			판매중
			<%} else if(product.getProTranCode().equals("1")) {%>
				구매완료
			<%} else if(product.getProTranCode().equals("2")) {%>
				배송중
			<%} else if(product.getProTranCode().equals("3")) {%>
				배송완료
			<%} %>
				
			</td>		
		</tr>
		<tr>
			<%} else { %>
				<tr class="ct_list_pop">
			<td align="center"><%=i+1%></td>
			<td></td>
			<td align="left">
				<%if(product.getProTranCode().equals("0")||product.getProTranCode()==null){ %>
				<a href="/getProduct.do?prodNo=<%=product.getProdNo() %>"><%= product.getProdName() %></a>
				<%} else { %>
				<%= product.getProdName() %>
				<%} %>
			</td>
			<td></td>
			<td align="left"><%= product.getPrice() %></td>
			<td></td>
			<td align="left"><%= product.getRegDate() %></td>
			<td></td>
			<td align="left">
			<%if(product.getProTranCode().equals("0")||product.getProTranCode()==null){ %>
			판매중
			<%} else {%>

				재고 없음
				<%} %>
		</td>	
	</tr>
	
	<tr>
		<td colspan="11" bgcolor="D6D7D6" height="1"></td>
	</tr>
	<%} %>	
<%} %>	
</table>
/////////////////////// EL / JSTL 적용으로 주석 처리 //////////////////////// --%>
	<c:if test="${user.role== 'admin'}">
		<c:set var="i" value="0" />
		<c:forEach var="product" items="${list}">
		<c:set var="i" value="${i+1}"/>
			<tr class="ct_list_pop">
				<td align="center">${i}</td>
				<td></td>
				<td align="left">
					<a href="/getProduct.do?prodNo=${product.prodNo}">${product.prodName}</a>
				</td>
				<td></td>
				<td align="left">${product.price}</td>
				<td></td>
				<td align="left">${product.regDate}</td>
				<td></td>
				<td align="left">
					<c:choose>
						<c:when test= "${product.proTranCode=='0'}">
							판매중
						</c:when>
						<c:when test= "${product.proTranCode=='1'}">
							구매완료 	
						</c:when>
						<c:when test= "${product.proTranCode=='2' }">
							배송중
						</c:when>
						<c:when test= "${product.proTranCode=='3'}">
							배송완료
						</c:when>
					</c:choose>
				</td>		
			</tr>
			<tr>
				<td colspan="11" bgcolor="D6D7D6" height="1"></td>
			</tr>	
		</c:forEach>	
	</c:if>	
		
	<c:if test="${user.role== 'user'}">
	<c:set var="i" value="0" />
	<c:forEach var="product" items="${list}">
		<c:set var="i" value="${i+1}"/>
			<tr class="ct_list_pop">
				<td align="center">${i}</td>
				<td></td>
				<td align="left">
					<c:choose>
						<c:when test= "${product.proTranCode=='0'|| product.proTranCode==null}">
							<a href="/getProduct.do?prodNo=${product.prodNo}">${ product.prodName}</a>
						</c:when>
						<c:otherwise>
								${ product.prodName}
						</c:otherwise>
					</c:choose>
				</td>
				<td></td>
				<td align="left">${ product.price}</td>
				<td></td>
				<td align="left">${ product.regDate}</td>
				<td></td>
				<td align="left">
					<c:choose>
						<c:when test= "${product.proTranCode=='0'||product.proTranCode==null}">
							판매중
						</c:when>
						<c:otherwise>
							재고 없음
						</c:otherwise>
					</c:choose>
				</td>		
			</tr>
			<tr>
				<td colspan="11" bgcolor="D6D7D6" height="1"></td>
			</tr>	
		</c:forEach>	
	</c:if>	
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="center">
		   <input type="hidden" id="currentPage" name="currentPage" value=""/>
		   	<%-- /////////////////////// EL / JSTL 적용으로 주석 처리 //////////////////////// 
			<% if( resultPage.getCurrentPage() <= resultPage.getPageUnit() ){ %>
					◀ 이전
			<% }else{ %>
					<a href="javascript:fncGetProductList('<%=resultPage.getCurrentPage()-1%>')">◀ 이전</a>
			<% } %>

			<%	for(int i=resultPage.getBeginUnitPage();i<= resultPage.getEndUnitPage() ;i++){	%>
					<a href="javascript:fncGetProductList('<%=i %>');"><%=i %></a>
			<% 	}  %>
	
			<% if( resultPage.getEndUnitPage() >= resultPage.getMaxPage() ){ %>
					이후 ▶
			<% }else{ %>
					<a href="javascript:fncGetProductList('<%=resultPage.getEndUnitPage()+1%>')">이후 ▶</a>
			<% } %>
	 /////////////////////// EL / JSTL 적용으로 주석 처리 //////////////////////// --%>

		<jsp:include page="../common/pageNavigatorProduct.jsp"/>	
		
    	</td>
	</tr>
</table>
<!--  페이지 Navigator 끝 -->

</form>

</div>

</body>

</html>


