package cn.teachtutorial.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.teachtutorial.connection.Dbcon;
import cn.teachtutorial.dao.OrderDao;
import cn.teachtutorial.model.*;
import cn.teachtutorial.model.Order;
import cn.teachtutorial.model.User;

@WebServlet("/cart-check-out")
public class CheckOutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try(PrintWriter out = response.getWriter()){
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			
			ArrayList<Cart> cart_list = (ArrayList<Cart>) request.getSession().getAttribute("cart-list");
			
			
	        User auth = (User)request.getSession().getAttribute("auth");
	        
	        if(cart_list != null && auth!=null) {
	        	for(Cart c:cart_list) {
	        		Order order = new Order();
	        		order.setId(c.getId());
	        		order.setUid(auth.getId());
	        		order.setQuantity(c.getQuantity());
	        		order.setDate(formatter.format(date));
	        		
	        		OrderDao oDao = new OrderDao(Dbcon.getConnection());
	        		boolean result = oDao.insertOrder(order);
	        		if(!result) break;
	        		
	        	}
	        	cart_list.clear();
	        	response.sendRedirect("orders.jsp");
	        	
	        }else {
	        	if(auth == null)
	        		response.sendRedirect("login.jsp");
	        response.sendRedirect("cart.jsp");
	        }
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		doGet(request, response);
	}

}
