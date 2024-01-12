package com.sist.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sist.dao.AmenityDAO;
import com.sist.dao.ImageDAO;
import com.sist.dao.ProductDAO;
import com.sist.dao.QnaDAO;
import com.sist.dao.ReviewDAO;
import com.sist.dao.UserDAO;
import com.sist.dao.WishlistDAO;

public class UserTestAction implements SistAction {

	@Override
	public String pro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ProductDAO dao = ProductDAO.getInstance();
		request.setAttribute("list", dao.menuSearchProduct(2, 2, "23/12/23", "23/12/30"));
		return "userTest.jsp";
	}

}
