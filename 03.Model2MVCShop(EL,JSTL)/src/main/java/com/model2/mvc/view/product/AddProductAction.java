package com.model2.mvc.view.product;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class AddProductAction extends Action {

	public AddProductAction() {
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/*ProductVO productVO = request.getAttribute("productVO");*/
		if(FileUpload.isMultipartContent(request)) {
			
			String temDir =
					"C:\\Users\\majja\\git\\03miniShopProject\\03.Model2MVCShop(EL,JSTL)\\src\\main\\webapp\\images\\uploadFiles";
			
			DiskFileUpload fileUpload = new DiskFileUpload();
			fileUpload.setRepositoryPath(temDir);
			fileUpload.setSizeMax(1024 * 1024 * 100);
			fileUpload.setSizeThreshold(1024*100);
			
			if(request.getContentLength()<fileUpload.getSizeMax()) {
				Product productVO=new Product();
				
				StringTokenizer token = null;
				
				List fileItemList = fileUpload.parseRequest(request);
				int size = fileItemList.size();
				for(int i=0 ; i< size; i ++) {
					FileItem fileItem = (FileItem)fileItemList.get(i);
					if(fileItem.isFormField()) {
						if(fileItem.getFieldName().equals("manuDate")) {
							token = new StringTokenizer(fileItem.getString("euc-kr"),"-");
							String manuDate = token.nextToken() + token.nextToken() + token.nextToken();
							productVO.setManuDate(manuDate);
						} else if(fileItem.getFieldName().equals("prodName")) {
							productVO.setProdName(fileItem.getString("euc-kr"));
						} else if(fileItem.getFieldName().equals("prodDetail")) {
							productVO.setProdDetail(fileItem.getString("euc-kr"));
						} else if(fileItem.getFieldName().equals("price")) {
							productVO.setPrice( Integer.parseInt(fileItem.getString("euc-kr")));
						}
					} else {
						if (fileItem.getSize() > 0) {
							int idx=fileItem.getName().lastIndexOf("\\");
							if(idx==-1) {
								idx = fileItem.getName().lastIndexOf("/");
							}
							String fileName = fileItem.getName().substring(idx+1);
							productVO.setFileName(fileName);
							try {
								File uploadedFile = new File(temDir, fileName);
								fileItem.write(uploadedFile);
							} catch (IOException e) {
								System.out.println(e);
							}
						} else {
							productVO.setFileName("../../image/empty.GIF");
						}
					} // end of else
				}// end of for
				ProductService productService = new ProductServiceImpl();
				productService.addProduct(productVO);
				
				request.setAttribute("product", productVO);
				System.out.println(productVO);
				
			} else {
				int overSize = (request.getContentLength()/1000000);
				System.out.println("<script>alert('파일의 크기는 100MB까지 입니다. 올리신 파일 용량은 " + overSize + "MB입니다.");
				System.out.println("history.back();</script>");
			}
		} else {
			System.out.println("인코딩 타입이 multipart/form-data가 아닙니다..");
		}
		/*
		ProductVO productVO=new ProductVO();
		String prodManuDateSQL = request.getParameter("manuDate").replaceAll("-", "");
		 
		
		productVO.setProdName(request.getParameter("prodName"));
		productVO.setProdDetail(request.getParameter("prodDetail"));
		productVO.setManuDate(prodManuDateSQL);
		productVO.setPrice(Integer.parseInt(request.getParameter("price")));
		productVO.setFileName(request.getParameter("fileName"));
		
		
		System.out.println(productVO);
		request.setAttribute("productVO", productVO);
		ProductService service=new ProductServiceImpl();
		service.addProduct(productVO);
		
		//TODO 아래 resultPage navigating 방식과 URI확인 철저히 하기 
		return "forward:/product/addProductView.jsp";
		// ?쿼리 실행 결과 판단은? <-- 추측 : 널체크를 view페이지에서 js로 하고 상품 조회후 상품 번호로 where조건을 걸어 쿼리를 하므로 무조건 1이 나오기때문에 생략
	 */
		return "forward:/product/addProduct.jsp";
	}// end of AddProduct execute()

}
