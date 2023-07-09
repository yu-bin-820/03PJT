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
				System.out.println("<script>alert('������ ũ��� 100MB���� �Դϴ�. �ø��� ���� �뷮�� " + overSize + "MB�Դϴ�.");
				System.out.println("history.back();</script>");
			}
		} else {
			System.out.println("���ڵ� Ÿ���� multipart/form-data�� �ƴմϴ�..");
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
		
		//TODO �Ʒ� resultPage navigating ��İ� URIȮ�� ö���� �ϱ� 
		return "forward:/product/addProductView.jsp";
		// ?���� ���� ��� �Ǵ���? <-- ���� : ��üũ�� view���������� js�� �ϰ� ��ǰ ��ȸ�� ��ǰ ��ȣ�� where������ �ɾ� ������ �ϹǷ� ������ 1�� �����⶧���� ����
	 */
		return "forward:/product/addProduct.jsp";
	}// end of AddProduct execute()

}
