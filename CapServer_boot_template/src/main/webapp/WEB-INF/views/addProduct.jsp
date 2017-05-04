<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%-- 객체와 form 의 데이터를 자동으로 맵핑 시켜주는 taglib --%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>

<div class="container-wrapper">
	<div class="container">
		<h2>Add Product</h2>
		<p class="lead">Fill the below information to add a product.</p>
		
		<%-- modelAttribute 의 이름과 controller 에서 넘겨준 model 로 넘기는 객체의 이름을 동일하게 해야 한다. --%>
		<sf:form action="${pageContext.request.contextPath}/admin/productInventory/addProduct?${_csrf.parameterName}=${_csrf.token}"
		method="post" modelAttribute="product"
		enctype="multipart/form-data">
			
			<div class="form-group">
				<label for="name">Name</label> 
				<sf:input path="name" id="name" class="form-control" />
				<sf:errors path="name" cssStyle="color:#ff0000" />
			</div>
			
			<div class="form-group">
				<label for="category">Category: </label> 
				<sf:radiobutton path="category" id="category" value="컴퓨터" />컴퓨터
				<sf:radiobutton path="category" id="category" value="가전" />가전
				<sf:radiobutton path="category" id="category" value="신발" />신발
			</div>
			
			<div class="form-group">
				<label for="description">Description</label> 
				<sf:input path="description" id="description" class="form-control" />
			</div>

			<div class="form-group">
				<label for="price">Price</label> 
				<sf:input path="price" id="price" class="form-control" />
				<sf:errors path="price" cssStyle="color:#ff0000" />
				
			</div>

			<div class="form-group">
				<label for="unitInStock">Unit in Stock</label> 
				<sf:input path="unitInStock" id="unitInStock" class="form-control" />
				<sf:errors path="unitInStock" cssStyle="color:#ff0000" />
				
			</div>
			
			<div class="form-group">
				<label for="manufacturer">Manufacturer</label> 
				<sf:input path="manufacturer" id="manufacturer" class="form-control" />
				<sf:errors path="manufacturer" cssStyle="color:#ff0000" />
				
			</div>
			
			<div class="form-group">
				<label for="productImage">Upload Picture</label> 
				<sf:input path="productImage" id="productImage" type="file" class="form-control" />				
			</div>
			
			<input type="submit" class="btn btn-default" value="Submit">
			<a href="<c:url value="/admin/productInventory"/>" class="btn btn-default">Cancel</a>


		</sf:form>
	</div>
</div>