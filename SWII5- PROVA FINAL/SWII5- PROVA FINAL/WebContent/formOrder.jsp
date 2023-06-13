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
	    <h1>Pedidos</h1>
	    <div class="form-container" align="center">
	        <c:if test="${order != null}">
	            <form action="update" method="post">
	        </c:if>
	        <c:if test="${order == null}">
	            <form action="insert" method="post">
	        </c:if>
	        
            <div class="form-group">
	            <input type="hidden" id="ordNo" name="ordNo" value="<c:out value='${order.ordNo}' />" />
	        </div>
	        <div class="form-group">
	            <label for="purchAmt">Valor da Compra:</label>
	            <input type="text" id="purchAmt" name="purchAmt" value="<c:out value='${order.purchAmt}' />" />
	        </div>
	        <div class="form-group">
	            <label for="ordDate">Data do Pedido:</label>
	            <input type="text" id="ordDate" name="ordDate" value="<fmt:formatDate value="${order.getOrdDate()}" pattern="dd/MM/yyyy" />" />
	        </div>
	        <div class="form-group">
	            <label for="customerId">ID do Cliente:</label>
	            <input type="text" id="customerId" name="customerId" value="<c:out value='${order.customerId}' />" />
	        </div>
	        <div class="form-group">
	            <label for="salesmanId">ID do Vendedor:</label>
	            <input type="text" id="salesmanId" name="salesmanId" value="<c:out value='${order.salesmanId}' />" />
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
