package cn.teachtutorial.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.teachtutorial.connection.Dbcon;
import cn.teachtutorial.dao.OrderDao;
import cn.teachtutorial.model.Cart;
import cn.teachtutorial.model.Order;
import cn.teachtutorial.model.User;


@WebServlet("/order-now")
public class OrderNowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try(PrintWriter out = response.getWriter()){
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			
			User auth = (User)request.getSession().getAttribute("auth");
			if(auth!= null) {
				   String productid = request.getParameter("id");
				   int productQuantity = Integer.parseInt(request.getParameter("quantity"));
				   if(productQuantity<=0) {
					   productQuantity = 1;
				   }
				   Order orderModel = new Order();
				 orderModel.setId(Integer.parseInt(productid));
				 orderModel.setUid(Integer.parseInt(productid));
				 orderModel.setQuantity(Integer.parseInt(productid));
				 orderModel.setDate(formatter.format(date));
				 
				 OrderDao orderDao = new OrderDao(Dbcon.getConnection());
				boolean result =  orderDao.insertOrder(orderModel);
				if(result) {
					ArrayList<Cart> cart_list = (ArrayList<Cart>) request.getSession().getAttribute("cart-list");
					if (cart_list != null) {
						for (Cart c : cart_list) {
							if (c.getId() == Integer.parseInt(productid)) {
								cart_list.remove(cart_list.indexOf(c));
								break;
							}
						}
						
					}
					
					response.sendRedirect("orders.jsp");
				}else {
					out.print("order failed");
				}
				 
			}else {
				response.sendRedirect("login.jsp");
			}
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
