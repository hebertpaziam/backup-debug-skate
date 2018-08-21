<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <c:if test="${token != pageContext.session.id}">
            <c:redirect url="ControleAcesso?Acao=LOGOFF"/>
        </c:if>

        <c:import url="/imports/header.jsp"/>

        <title>DEBUG SKATE - LISTA DE CLIENTES</title>

    </head>
    <body>
        <c:import url="/imports/cabecalho.jsp"/>
        <div class="container" id="container">
            <h1>Lista de Clientes</h1>
            <br/>
            <button name="btnNovo" type="button" class="btn btn-success" onClick="window.open('<c:url value="ControlePessoa?Acao=NEW_CLIENTE"/>', '_parent')"> NOVO CLIENTE </button>
            <br/>
            <br/>
            <table class="table table-striped table-hover smartTable" name="TabelaCliente" id="TabelaCliente">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>NOME</th>
                        <th>CPF</th>
                        <th>TELEFONE</th>
                        <th>E-MAIL</th>                        
                        <th>DATA DE NASC.</th>
                        <th>USUARIO</th>
                        <th>FUNÇÕES</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="cliente" items="${clientes}"> 
                        <tr>
                            <td>${cliente.id}</td>
                            <td>${cliente.nome}</td>
                            <td>${cliente.cpf_cnpj}</td>
                            <td>${cliente.telefone}</td>
                            <td>${cliente.email}</td>
                            <td>${cliente.data_nasc}</td>
                            <td>${cliente.usuario.login}</td>
                            <td><button type="button" name="Editar" class="btn btn-default" onClick="window.open('<c:url value="ControlePessoa?IdObjeto=${cliente.id}&Acao=EDIT_CLIENTE"/>', '_parent')"><span class="glyphicon glyphicon-pencil"></span></button>&nbsp;
                                <button type="button" name="btnDeletar" class="btn btn-default" onClick="window.open('<c:url value="ControlePessoa?IdObjeto=${cliente.id}&Acao=DELETE_CLIENTE"/>', '_parent')"><span class="glyphicon glyphicon-remove"></span></button></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table> 
        </div>
    </body>
</html>
