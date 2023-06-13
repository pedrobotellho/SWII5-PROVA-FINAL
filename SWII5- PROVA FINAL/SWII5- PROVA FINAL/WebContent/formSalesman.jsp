<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Prova 2</title>
</head>
<body>

  <a href="/prova2">voltar</a>
	  
	<main class="container">
	    <h1>Vendedores</h1>
	    <div class="form-container" align="center">
	        <c:if test="${salesman != null}">
	            <form action="update" method="post">
	        </c:if>
	        <c:if test="${salesman == null}">
	            <form action="insert" method="post">
	        </c:if>
	        
            <div class="form-group">
	            <input type="hidden" id="salesmanId" name="salesmanId" value="<c:out value='${salesman.salesmanId}' />" />
	        </div>
	        <div class="form-group">
	            <label for="name">Nome do Vendedor:</label>
	            <input type="text" id="name" name="name" value="<c:out value='${salesman.name}' />" />
	        </div>
	        <div class="form-group">
	            <label for="city">Cidade:</label>
	            <input type="text" id="city" name="city" value="<c:out value='${salesman.city}' />" />
	        </div>
	        <div class="form-group">
	            <label for="commission">Comissão:</label>
	            <input type="text" id="commission" name="commission" value="<c:out value='${salesman.commission}' />" />
	        </div>
	        <div class="form-group-buttons">
	        	<a href="/prova2" class="button btn-cinza">cancelar</a>
	            <input type="submit" value="Salvar" />
	        </div>
	    </form>
	</div>
	</main>
</body>
</html>
