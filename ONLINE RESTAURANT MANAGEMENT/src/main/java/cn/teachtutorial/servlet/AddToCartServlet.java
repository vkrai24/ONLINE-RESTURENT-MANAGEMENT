package cn.teachtutorial.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.teachtutorial.model.Cart;

@WebServlet("/add-to-cart")
public class AddToCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
	
		try(PrintWriter out = response.getWriter()){
			ArrayList<Cart> cartList = new ArrayList<>();
		
			int id = Integer.parseInt(request.getParameter("id"));
			Cart cm = new Cart();
			cm.setId(id);
			cm.setQuantity(1);
			
			HttpSession session = request.getSession();
			@SuppressWarnings("unchecked")
			ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart-list");
			
			if(cart_list == null) {
				cartList.add(cm);
				session.setAttribute("cart-list", cartList);
			}else {
					cartList = cart_list;
					boolean exist = false;
					
					
					
					for(Cart c: cart_list) {
						if(c.getId()== id)
						{
							exist = true;
							out.println("<h3 style='color:crimson; text-align:center'>Item already exist in cart.<a href='cart.jsp'>Go to Cart page</a>");
							
						}
					}
					
						if(!exist) {
							cartList.add(cm);
							out.println("product added");
							response.sendRedirect("index.jsp");
						}
				   }
			for(Cart c:cartList) {
				out.println(c.getId());
			}
			}
		}
	}

