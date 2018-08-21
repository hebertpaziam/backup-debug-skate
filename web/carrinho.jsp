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

        <title>DEBUG SKATE - LISTA SKATES DO CARRINHO</title>        
    </head>

    <body>
        <c:import url="/imports/cabecalho.jsp"/>
        <div class="container" id="container">
            <h1>Lista Skates no Carrinho</h1>

            <c:if test="${!empty carrinho.skates}">
                <div class="row">
                    <div class="col-md-12">
                        <table class="table table-striped">
                            <thead>
                                <tr>
                                    <td>SHAPE</td>
                                    <td>TRUCK</td>
                                    <td>RODA</td>
                                    <td>LIXA</td>
                                    <td colspan="2">FUNÇÕES</td>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="skate" items="${carrinho.skates}" varStatus="status">
                                    <tr>
                                        <td>${skate.shape.modelo}</td>
                                        <td>${skate.truck.modelo}</td>
                                        <td>${skate.roda.modelo}</td>
                                        <td>${skate.lixa.modelo}</td>
                                        <td><button type="button" name="Editar" class="btn btn-default" onClick="window.open('<c:url value="ControleSkate?Acao=EDIT_SK8&IdSk8=${status.index}"/>', '_parent')"><span class="fi flaticon-tool"></span></button></td>
                                        <td><button type="button" name="btnDeletar" class="btn btn-default" onClick="window.open('<c:url value="ControleSkate?Acao=DELETE_SK8&IdSk8=${status.index}"/>', '_parent')"><span class="glyphicon glyphicon-remove"></span></button></td>    
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="row" id="divBtnFinalizar">
                    <div class="col-md-4"></div>
                    <div class="col-md-4"><button id="btnFinalizar" class="btn btn-success btn-lg" data-toggle="modal" data-target="#ConfirmarVenda" name="btnFinalizar"><span class="glyphicon glyphicon-ok"></span> Finalizar a Venda</button></div>
                    
                    
                    <!-- Modal -->
                    <div class="modal fade" id="ConfirmarVenda" role="dialog">
                        <div class="modal-dialog modal-sm">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                                    <h4 class="modal-title">Deseja realmente confirmar esta venda?</h4>
                                </div>
                                <div class="modal-body">
                                    <button type="button" class="btn btn-success" data-dismiss="modal" onClick="window.open('<c:url value="ControleVenda?Acao=NEW_VENDA"/>', '_blank'), window.location.href = 'ControleAcesso?Acao=HOME'">Sim</button>
                                    <button type="button" class="btn btn-danger" data-dismiss="modal">Não</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>



            </c:if>
            <br/>
        </div>
    </body>
</html>
