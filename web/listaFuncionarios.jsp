<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <c:if test="${token != pageContext.session.id}">
            <c:redirect url="ControleAcesso?Acao=LOGOFF"/>
        </c:if>
        
        <title>DEBUG SKATE - LISTA DE FUNCIONARIOS</title>

        <c:import url="/imports/header.jsp"/>

    </head>
    <body>
        <c:import url="/imports/cabecalho.jsp"/>
        <div class="container">
            <h1>Lista de Funcionarios</h1>
            <br/>
            <button name="btnNovo" type="button" class="btn btn-success" onClick="window.open('<c:url value="ControlePessoa?Acao=NEW_FUNCIONARIO"/>', '_parent')"> NOVO FUNCIONARIO </button>
            <br/>
            <br/>
            <table class="table table-striped table-hover smartTable" name="TabelaFuncionario" id="TabelaFuncionario">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>NOME</th>
                        <th>CPF</th>
                        <th>TELEFONE</th>
                        <th>DATA DE NASC.</th>
                        <th>MATRICULA</th>
                        <th>USUARIO</th>
                        <th>PERFIL</th>
                        <th>FUNÇÕES</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="funcionario" items="${funcionarios}"> 
                        <tr>
                            <td>${funcionario.id}</td>
                            <td>${funcionario.nome}</td>
                            <td>${funcionario.cpf_cnpj}</td>
                            <td>${funcionario.telefone}</td>
                            <td>${funcionario.data_nasc}</td>
                            <td>${funcionario.matricula}</td>
                            <td>${funcionario.usuario.login}</td>
                            <td>${funcionario.usuario.perfil}</td>
                            <td><button type="button" name="Editar" class="btn btn-default" onClick="window.open('<c:url value="ControlePessoa?IdObjeto=${funcionario.id}&Acao=EDIT_FUNCIONARIO"/>', '_parent')"><span class="glyphicon glyphicon-pencil"></span></button>
                            <button type="button" name="btnDeletar" class="btn btn-default" onClick="window.open('<c:url value="ControlePessoa?IdObjeto=${funcionario.id}&Acao=DELETE_FUNCIONARIO"/>', '_parent')"><span class="glyphicon glyphicon-remove"></span></button></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </body>
</html>
