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

<a href="/prova2/Customer/new"> Novo Cliente</a>
<a href="/prova2/Salesman/new">Novo Vendedor</a>
<a href="/prova2/Order/new">Nova Ordem de Venda</a>

<section>
    <h2>Clientes</h2>

    <table border="1">
        <thead>
        <tr>
            <th>ID do Cliente</th>
            <th>Nome do Cliente</th>
            <th>Cidade</th>
            <th>Classificação</th>
            <th>ID do Vendedor</th>
            <th colspan="2">Ações</th>
        </tr>
        </thead>
        <tbody>

        <c:if test="${listCustomer.size() > 0}">
            <c:forEach var="item" items="${listCustomer}">
                <tr>
                    <td><c:out value="${item.customerId}" /></td>
                    <td><c:out value="${item.custName}" /></td>
                    <td><c:out value="${item.city}" /></td>
                    <td><c:out value="${item.grade}" /></td>
                    <td><c:out value="${item.salesmanId}" /></td>
                    <td>
                        <a href="/prova2/Customer/delete?id=<c:out value='${item.customerId}' />">Remover</a>
                    </td>
                    <td>
                        <a href="/prova2/Customer/edit?id=<c:out value='${item.customerId}' />">Editar</a>
                    </td>
                </tr>
            </c:forEach>
        </c:if>

        <c:if test="${listCustomer.size() <= 0}">
            <tr>
                <td colspan="7">Sem clientes cadastrados</td>
            </tr>
        </c:if>
        </tbody>
    </table>

</section>

<section>
    <h2>Vendedor</h2>

    <table border="1">
        <thead>
        <tr>
            <th>ID do Vendedor</th>
            <th>Nome do Vendedor</th>
            <th>Cidade</th>
            <th>Comissão</th>
            <th colspan="2">Ações</th>
        </tr>
        </thead>
        <tbody>

        <c:if test="${listSalesman.size() > 0}">
            <c:forEach var="item" items="${listSalesman}">
                <tr>
                    <td><c:out value="${item.getSalesmanId()}" /></td>
                    <td><c:out value="${item.getName()}" /></td>
                    <td><c:out value="${item.getCity()}" /></td>
                    <td><c:out value="${item.getCommission()}" /></td>
                    <td>
                        <a href="/prova2/Salesman/delete?id=<c:out value='${item.getSalesmanId()}' />">Remover</a>
                    </td>
                    <td>
                        <a href="/prova2/Salesman/edit?id=<c:out value='${item.getSalesmanId()}' />">Editar</a>
                    </td>
                </tr>
            </c:forEach>
        </c:if>

        <c:if test="${listSalesman.size() <= 0}">
            <tr>
                <td colspan="6">Sem vendedores cadastrados</td>
            </tr>
        </c:if>
        </tbody>
    </table>
</section>


<section>
    <h2>Ordens de compra</h2>

    <table border="1">
        <thead>
        <tr>
            <th>Número da Ordem</th>
            <th>Valor da Compra</th>
            <th>Data da Ordem</th>
            <th>ID do Cliente</th>
            <th>ID do Vendedor</th>
            <th colspan="2">Ações</th>
        </tr>
        </thead>
        <tbody>

        <c:if test="${listOrders.size() > 0}">
            <c:forEach var="ordem" items="${listOrders}">
                <tr>
                    <td><c:out value="${ordem.getOrdNo()}" /></td>
                    <td><c:out value="${ordem.getPurchAmt()}" /></td>
                    <td><fmt:formatDate value="${ordem.getOrdDate()}" pattern="dd/MM/yyyy" /></td>
                    <td><c:out value="${ordem.getCustomerId()}" /></td>
                    <td><c:out value="${ordem.getSalesmanId()}" /></td>
                    <td>
                        <a href="/prova2/Order/delete?id=<c:out value='${ordem.getOrdNo()}' />">Remover</a>
                    </td>
                    <td>
                        <a href="/prova2/Order/edit?id=<c:out value='${ordem.getOrdNo()}' />">Editar</a>
                    </td>
                </tr>
            </c:forEach>
        </c:if>

        <c:if test="${listOrders.size() <= 0}">
            <tr>
                <td colspan="7">Sem ordens de venda cadastradas</td>
            </tr>
        </c:if>
        </tbody>
    </table>
</section>
</body>
</html>
