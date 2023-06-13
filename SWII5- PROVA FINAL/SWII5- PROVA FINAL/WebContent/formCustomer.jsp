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
	    <h1>Clientes</h1>
	    <div class="form-container" align="center">
	        <c:if test="${customer != null}">
	            <form action="update" method="post">
	        </c:if>
	        <c:if test="${customer == null}">
	            <form action="insert" method="post">
	        </c:if>
	        
             <div class="form-group">
	            <input type="hidden" id="id" name="id" value="<c:out value='${customer.customerId}' />" />
	        </div>
	        <div class="form-group">
	            <label for="nome">Nome do Cliente:</label>
	            <input type="text" id="nome" name="nome" value="<c:out value='${customer.custName}' />" />
	        </div>
	        <div class="form-group">
	            <label for="cidade">Cidade:</label>
	            <input type="text" id="cidade" name="cidade" value="<c:out value='${customer.city}' />" />
	        </div>
	        <div class="form-group">
	            <label for="classificacao">Classificação:</label>
	            <input type="text" id="classificacao" name="classificacao" value="<c:out value='${customer.grade}' />" />
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