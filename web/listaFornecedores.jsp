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

        <title>DEBUG SKATE - LISTA DE FORNECEDORES</title>

    </head>
    <body>

        <c:import url="/imports/cabecalho.jsp"/>

        <div class="container" id="container">
            <h1>Lista de Fornecedores</h1>
            <br/>
            <button name="btnNovo" type="button" class="btn btn-success" onClick="window.open('<c:url value="ControlePessoa?Acao=NEW_FORNECEDOR"/>', '_parent')"> NOVO FORNECEDOR </button>
            <br/>
            <br/>

            <table class="table table-striped table-hover smartTable" name="TabelaFornecedor" id="TabelaFornecedor">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>NOME FANTASIA</th>
                        <th>CNPJ</th>
                        <th>E-MAIL</th>
                        <th>CONTATO</th>
                        <th>TELEFONE</th>
                        <th>FUNÇÕES</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="fornecedor" items="${fornecedores}"> 
                        <tr>
                            <td>${fornecedor.id}</td>
                            <td>${fornecedor.nome}</td>
                            <td>${fornecedor.cpf_cnpj}</td>
                            <td>${fornecedor.email}</td>
                            <td>${fornecedor.contato}</td>
                            <td>${fornecedor.telefone}</td>
                            <td><button type="button" name="Editar" class="btn btn-default" onClick="window.open('<c:url value="ControlePessoa?IdObjeto=${fornecedor.id}&Acao=EDIT_FORNECEDOR"/>', '_parent')"><span class="glyphicon glyphicon-pencil"></span></button> &nbsp;
                                <button type="button" name="btnDeletar" class="btn btn-default" onClick="window.open('<c:url value="ControlePessoa?IdObjeto=${fornecedor.id}&Acao=DELETE_FORNECEDOR"/>', '_parent')"><span class="glyphicon glyphicon-remove"></span></button></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </body>
</html>
