package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.common.util.*;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;
import com.model2.mvc.service.domain.User;

public class PurchaseDAO {
	///field
	UserService uservice=new UserServiceImpl();
	ProductService pservice= new ProductServiceImpl();
	
	public PurchaseDAO() {
	}
	
	public Purchase findPurchase(int tranNo) throws Exception {
		Connection con = DBUtil.getConnection();
		
		String sql = "select * from transaction where tran_no = ?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, tranNo);
		
		ResultSet rs = stmt.executeQuery();
		
		Purchase purchase = null;
		
		while(rs.next()) {
			purchase = new Purchase();
			purchase.setTranNo(Integer.parseInt(rs.getString("tran_No")));
			purchase.setPurchaseProd((Product)(pservice.getProduct(rs.getInt("PROD_NO"))));
			purchase.setBuyer((User)(uservice.getUser(rs.getString("buyer_ID"))));
			purchase.setPaymentOption(rs.getString("PAYMENT_OPTION").trim());
			purchase.setReceiverName(rs.getString("RECEIVER_NAME"));
			purchase.setReceiverPhone(rs.getString("RECEIVER_PHONE"));
			purchase.setDivyAddr(rs.getString("DEMAILADDR"));
			purchase.setDivyRequest(rs.getString("DLVY_REQUEST"));
			purchase.setDivyDate(rs.getString("DLVY_DATE"));
			purchase.setOrderDate(rs.getDate("ORDER_DATA"));
			purchase.setTranCode(rs.getString("TRAN_STATUS_CODE").trim());
			
		}
		
		con.close();
		rs.close();
		stmt.close();
		
		return purchase;
		
	}
	
	
	public Map<String, Object> getPurchaseList(Search search, String userId) throws Exception {
		
		//System.out.println("PurchaseDAO에서 받은 userId"+userId);
		
		Map<String , Object>  map = new HashMap<String, Object>();
		
		Connection con = DBUtil.getConnection();
		
		String sql = "select * from transaction ";
		
		sql += " where buyer_ID='" + userId+"' ";
		
		sql += " order by ORDER_DATA ";
		
		//==> TotalCount GET
		int totalCount = this.getTotalCount(sql);
		System.out.println("PurchaseDAO :: totalCount  :: " + totalCount);
		
		//==> CurrentPage 게시물만 받도록 Query 다시구성
		sql = makeCurrentPageSql(sql, search);
		PreparedStatement stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		
		
		List<Purchase> list = new ArrayList<Purchase>();
		while(rs.next()){
				Purchase purchase = new Purchase();
				purchase.setTranNo(Integer.parseInt(rs.getString("tran_No")));
				purchase.setPurchaseProd((Product)(pservice.getProduct(rs.getInt("PROD_NO"))));
				purchase.setBuyer((User)(uservice.getUser(rs.getString("buyer_ID"))));
				purchase.setPaymentOption(rs.getString("PAYMENT_OPTION").trim());
				purchase.setReceiverName(rs.getString("RECEIVER_NAME"));
				purchase.setReceiverPhone(rs.getString("RECEIVER_PHONE"));
				purchase.setDivyAddr(rs.getString("DEMAILADDR"));
				purchase.setDivyRequest(rs.getString("DLVY_REQUEST"));
				purchase.setDivyDate(rs.getString("DLVY_DATE"));
				purchase.setOrderDate(rs.getDate("ORDER_DATA"));
				purchase.setTranCode(rs.getString("TRAN_STATUS_CODE").trim());
				
				list.add(purchase);
				if(!rs.next())
					break;
			}
	//System.out.println("list.size() :"+list.size());
	//==> totalCount 정보 저장
	map.put("totalCount", new Integer(totalCount));
	System.out.println(totalCount);
	//==> currentPage 의 게시물 정보 갖는 List 저장
	map.put("list", list);
	System.out.println("map().size() :"+map.size());
	
	con.close();
	rs.close();
	stmt.close();
	
	return map;
}

	
	public HashMap<String, Object> getSaleList(Search search) throws Exception {
		
		
		
		return null;
		
	}

	
	public void insertPurchase(Purchase purchase) throws Exception {
		
		Connection con = DBUtil.getConnection();
		
		System.out.println("PurchaseDAO로 넘어옴"+purchase);
		
		String sql = "insert into transaction values (seq_transaction_tran_no.nextval, ?, ?, ?, ?, ?, ?, ?, ? , sysdate,?)";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, purchase.getPurchaseProd().getProdNo());
		stmt.setString(2, purchase.getBuyer().getUserId());
		stmt.setString(3, purchase.getPaymentOption());
		stmt.setString(4, purchase.getReceiverName());
		stmt.setString(5, purchase.getReceiverPhone());
		stmt.setString(6, purchase.getDivyAddr());
		stmt.setString(7, purchase.getDivyRequest());
		stmt.setString(8, purchase.getTranCode().trim());
		stmt.setString(9, purchase.getDivyDate());
		
		int i = stmt.executeUpdate();
		
		if(i==1) {
			System.out.println("판매등록완료");
		}else {
			System.out.println("판매등록실패");
		}
		
		con.close();
		stmt.close();
	}
	
	public void updatePurchase(Purchase purchase) throws Exception {
		
		System.out.println("DAO에서 update시작");
		System.out.println(purchase);
		Connection con = DBUtil.getConnection();
		
		String sql = "update transaction set PAYMENT_OPTION=?, RECEIVER_NAME=?, RECEIVER_PHONE=?, DEMAILADDR=?, DLVY_REQUEST=?, DLVY_DATE=? where Tran_no=?";
			
		PreparedStatement stmt = con.prepareStatement(sql);
		
		stmt.setString(1, purchase.getPaymentOption().trim());
		stmt.setString(2, purchase.getReceiverName());
		stmt.setString(3, purchase.getReceiverPhone());
		stmt.setString(4, purchase.getDivyAddr());
		stmt.setString(5, purchase.getDivyRequest());
		stmt.setString(6, purchase.getDivyDate());
		stmt.setInt(7, purchase.getTranNo());
		stmt.executeUpdate();

		System.out.println(sql);
		
		
		con.close();
		stmt.close();
		
	}

	public void updateTranCode(Purchase purchase) throws Exception {
		
		System.out.println("DAO에서 updatetrancode시작");
		System.out.println(purchase);
		Connection con = DBUtil.getConnection();
		String tranCode = purchase.getTranCode().trim();
		System.out.println(purchase.getTranCode());
		if(tranCode.equals("2")) {
		
		String sql = "update transaction set TRAN_STATUS_CODE=? where PROD_NO=?";
			
		PreparedStatement stmt = con.prepareStatement(sql);
		
		stmt.setString(1, tranCode);
		stmt.setInt(2, purchase.getPurchaseProd().getProdNo());
		
		stmt.executeUpdate();

		System.out.println(sql);
		
		con.close();
		stmt.close();
		
		} else if(tranCode.equals("3")) {
			
		System.out.println("tranCode=3");
		String sql = "UPDATE TRANSACTION SET tran_status_code=? WHERE tran_no=?";
		System.out.println(purchase.getTranNo());	
		PreparedStatement stmt = con.prepareStatement(sql);
			
		stmt.setString(1, tranCode);
		stmt.setInt(2, purchase.getTranNo());

		//stmt.executeUpdate();
		System.out.println("update성공시 1 : "+stmt.executeUpdate());
		System.out.println(sql);
		
		con.close();
		stmt.close();
		
		}

		
	}
	// 게시판 Page 처리를 위한 전체 Row(totalCount)  return
	private int getTotalCount(String sql) throws Exception {
		
		sql = "SELECT COUNT(*) "+
		          "FROM ( " +sql+ ") countTable";
		
		Connection con = DBUtil.getConnection();
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
		
		int totalCount = 0;
		if( rs.next() ){
			totalCount = rs.getInt(1);
		}
		
		pStmt.close();
		con.close();
		rs.close();
		
		return totalCount;
	}
	
	// 게시판 currentPage Row 만  return 
	private String makeCurrentPageSql(String sql , Search search){
		sql = 	"SELECT * "+ 
					"FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " +
									" 	FROM (	"+sql+" ) inner_table "+
									"	WHERE ROWNUM <="+search.getCurrentPage()*search.getPageSize()+" ) " +
					"WHERE row_seq BETWEEN "+((search.getCurrentPage()-1)*search.getPageSize()+1) +" AND "+search.getCurrentPage()*search.getPageSize();
		
		System.out.println("PurchaseDAO :: make SQL :: "+ sql);	
		
		return sql;
	}
}

