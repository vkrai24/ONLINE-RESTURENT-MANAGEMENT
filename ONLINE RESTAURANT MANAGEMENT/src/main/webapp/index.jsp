<%@page import="java.util.*"%>
<%@page import="cn.teachtutorial.dao.ProductDao"%>
<%@page import="cn.teachtutorial.connection.Dbcon"%>
<%@page import="cn.teachtutorial.model.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
User auth = (User) request.getSession().getAttribute("auth");
if (auth != null) {
	request.setAttribute("auth", auth);
}
ProductDao pd = new ProductDao(Dbcon.getConnection());
List<Product> products = pd.getAllProducts();

ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart-list");
 
if(cart_list != null){
	request.setAttribute("cart_list", cart_list);
}
%>
<!DOCTYPE html>
<html>
<head>̥̥
<title>FOOD-Cart</title>
<%@include file="includes/head.jsp"%>
<style>
* {
  box-sizing: border-box;
}

body {
  margin: 0;
  font-family: Arial;
  font-size: 17px;
}

#myVideo {
  position: fixed;
  right: 0;
  bottom: 0;
  min-width: 100%; 
  min-height: 100%;
}

.content {
  position: fixed;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  color: #f1f1f1;
  width: 100%;
  padding: 20px;
}

#myBtn {
  width: 200px;
  font-size: 18px;
  padding: 10px;
  border: none;
  background: #000;
  color: #fff;
  cursor: pointer;
}

#myBtn:hover {
  background: #ddd;
  color: black;
}
</style>
</head>
<body>
<video autoplay muted loop id="myVideo">
  <source src="./picture/v.webm" type="video/mp4">
</video>

<div class="content">
  <p>Welcome to VIVEK PLAZA</p>
</div>
	<%@include file="includes/navbar.jsp"%>

	<div class="container">
		<div class="card-header my-3">FOOD</div>
		<div class="row">
		<%
		if(!products.isEmpty()){
			for(Product p:products){%>
				<div class="col-md-3 my-3">
				<div class="card w-100 h-100" style="width: 18rem;">
					<img class="card-img-top" src="picture/<%= p.getId()%>.jpg" alt="Card image cap">
					<div class="card-body">
					<h5 class="card-title"><%=p.getName() %></h5>
					<h6 class="price">price:rs<%=p.getPrice() %></h6>
					<h6 class="category">Location:<%=p.getCategory() %></h6>
					<div class="mt-3 d-flex justify-content-between"></div>
						<a href = "add-to-cart?id=<%= p.getId() %>" class="btn btn-dark">Add to Cart</a>
						<a href = "order-now?quantity=1&id=<%= p.getId() %>" class="btn btn-primary">Buy Now</a>
					</div>
				</div>
			</div>
			<%}
		}
		%>
			
		</div>
	</div>

	<%@include file="includes/footer.jsp"%>
</body>
</html>