package com.model2.mvc.view.product;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class UpdateProductAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int prodNo = 0;
if(FileUpload.isMultipartContent(request)) {
			System.out.println("����� ������Ʈ ���� " + request.getParameter("prodNo"));
			System.out.println("����� ������Ʈ ���� " + request.getParameter("prodName"));
			String temDir =
					"c:\\workspace\\01.Model2MVCShop(stu)\\src\\main\\webapp\\images\\uploadFiles\\";
			
			DiskFileUpload fileUpload = new DiskFileUpload();
			fileUpload.setRepositoryPath(temDir);
			fileUpload.setSizeMax(1024 * 1024 * 100);
			fileUpload.setSizeThreshold(1024*100);
			
			if(request.getContentLength()<fileUpload.getSizeMax()) {
				Product productVO=new Product();
				
				
				List fileItemList = fileUpload.parseRequest(request);
				int size = fileItemList.size();
				for(int i=0 ; i< size; i ++) {
					FileItem fileItem = (FileItem)fileItemList.get(i);
					if(fileItem.isFormField()) {
						if(fileItem.getFieldName().equals("manuDate")) {
							productVO.setManuDate(fileItem.getString("euc-kr"));
						} else if(fileItem.getFieldName().equals("prodName")) {
							productVO.setProdName(fileItem.getString("euc-kr"));
						} else if(fileItem.getFieldName().equals("prodDetail")) {
							productVO.setProdDetail(fileItem.getString("euc-kr"));
						} else if(fileItem.getFieldName().equals("price")) {
							productVO.setPrice( Integer.parseInt(fileItem.getString("euc-kr")));
						} else if(fileItem.getFieldName().equals("prodNo")) {
							System.out.println(fileItem.getString("euc-kr"));
							
							productVO.setProdNo( Integer.parseInt(fileItem.getString("euc-kr")));
							prodNo = productVO.getProdNo();
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
				productService.updateProduct(productVO);
				
				request.setAttribute("productVO", productVO);
				request.setAttribute("updateChecker", "true");
			} else {
				int overSize = (request.getContentLength()/1000000);
				System.out.println("<script>alert('������ ũ��� 100MB���� �Դϴ�. �ø��� ���� �뷮�� " + overSize + "MB�Դϴ�.");
				System.out.println("history.back();</script>");
			}
		} else {
			System.out.println("���ڵ� Ÿ���� multipart/form-data�� �ƴմϴ�..");
		}

		
		return "redirect:/getProduct.do?prodNo=" + prodNo + "&menu=search&updateChecker=true";
	}

}
