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

        <title>DEBUG SKATE - LISTA DE VENDAS</title>

    </head>
    <body>
        <c:import url="/imports/cabecalho.jsp"/>
        <div class="container">
            <h1>Lista de Vendas</h1>
            <br/>

            <c:choose>
                <c:when test="${user.perfil eq 'GERENTE'}">
                    <!-- ---------------------------VENDAS PARA INTERVENÇÃO DO GERENTE--------------------------- -->
                    <table class="table table-striped table-hover smartTable" name="TabelaVenda" id="TabelaVenda">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>CLIENTE</th>
                                <th>DATA PEDIDO</th>
                                <th>VALOR VENDA</th>
                                <th>VALOR FRETE</th>
                                <th>VALOR TOTAL</th>
                                <th colspan="2">FUNÇÕES</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="venda" items="${nPagas}"> 
                                <tr>
                                    <td>${venda.id}</td>
                                    <td>${venda.cliente.nome}</td>
                                    <td>${venda.dtPedido}</td>
                                    <td>${venda.valorVenda}</td>
                                    <td>${venda.valorFrete}</td>
                                    <td>${venda.valorTotal}</td>
                                    <td><button type="button" name="btnAprovarPagamento" class="btn btn-success" onClick="window.open('<c:url value="ControleVenda?IdObjeto=${venda.id}&Acao=APR_PAGAMENTO_VENDA"/>', '_parent')"><span class="glyphicon glyphicon-ok"></span> APROVAR PAGAMENTO</button></td>
                                    <td><button type="button" name="btnCancelarVenda" class="btn btn-danger" onClick="window.open('<c:url value="ControleVenda?IdObjeto=${venda.id}&Acao=CANCEL_VENDA"/>', '_parent')"><span class="glyphicon glyphicon-remove"></span> CANCELAR VENDA</button></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:when>

                <c:when test="${user.perfil eq 'ATENDENTE'}">
                    <!-- ---------------------------VENDAS PARA INTERVENÇÃO DO ATENDENTE--------------------------- -->
                    <table class="table table-striped table-hover smartTable" name="TabelaVenda" id="TabelaVenda">
                        <thead>
                            <tr>
                                <th>VENDA</th>
                                <th>CLIENTE</th>
                                <th>DATA PEDIDO</th>
                                <th>SHAPE</th>
                                <th>TRUCK</th>
                                <th>RODA</th>
                                <th>LIXA</th>
                                <th>FUNÇÃO</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="venda" items="${pagas}">
                                <c:forEach var="skate" items="${venda.skates}"> 
                                    <tr>
                                        <td>${venda.id}</td>
                                        <td>${venda.cliente.nome}</td>
                                        <td>${venda.dtPedido}</td>
                                        <td>${skate.shape.modelo}</td>
                                        <td>${skate.truck.modelo}</td>
                                        <td>${skate.roda.modelo}</td>
                                        <td>${skate.lixa.modelo}</td>
                                        <td><button type="button" name="Editar" class="btn btn-primary" onClick="window.open('<c:url value="ControleVenda?IdObjeto=${skate.id}&Acao=APR_MONTAGEM_VENDA"/>', '_parent')"><span class="glyphicon glyphicon-ok"></span> MONTAR SKATE</button></td>
                                    </tr>
                                </c:forEach>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:when>


                <c:when test="${user.perfil eq 'ADMINISTRADOR'}">
                    <!-- ---------------------------VENDAS PARA INTERVENÇÃO DO ADMINISTRADOR--------------------------- -->
                    <ul class="nav nav-tabs">
                        <li><a data-toggle="pill" href="#SelFilaSkates">Fila de Skates</a></li>
                        <li><a data-toggle="pill" href="#SelVendasEmAberto">Vendas em Aberto</a></li>
                    </ul>

                    <div class="tab-content">

                        <div class="tab-pane fade" id="SelVendasEmAberto"  name="SelVendasEmAberto">
                            <table class="table table-striped table-hover smartTable" name="TabelaVenda" id="TabelaVenda">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>CLIENTE</th>
                                        <th>DATA PEDIDO</th>
                                        <th>VALOR VENDA</th>
                                        <th>VALOR FRETE</th>
                                        <th>VALOR TOTAL</th>
                                        <th colspan="2">FUNÇÕES</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="venda" items="${nPagas}"> 
                                        <tr>
                                            <td>${venda.id}</td>
                                            <td>${venda.cliente.nome}</td>
                                            <td>${venda.dtPedido}</td>
                                            <td>${venda.valorVenda}</td>
                                            <td>${venda.valorFrete}</td>
                                            <td>${venda.valorTotal}</td>
                                            <td><button type="button" name="btnAprovarPagamento" class="btn btn-success" onClick="window.open('<c:url value="ControleVenda?IdObjeto=${venda.id}&Acao=APR_PAGAMENTO_VENDA"/>', '_parent')"><span class="glyphicon glyphicon-ok"></span> APROVAR PAGAMENTO</button></td>
                                            <td><button type="button" name="btnCancelarVenda" class="btn btn-danger" onClick="window.open('<c:url value="ControleVenda?IdObjeto=${venda.id}&Acao=CANCEL_VENDA"/>', '_parent')"><span class="glyphicon glyphicon-remove"></span> CANCELAR VENDA</button></td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>

                        <div class="tab-pane fade" id="SelFilaSkates" name="SelFilaSkates">
                            <table class="table table-striped table-hover smartTable" name="TabelaVenda" id="TabelaVenda">
                                <thead>
                                    <tr>
                                        <th>VENDA</th>
                                        <th>CLIENTE</th>
                                        <th>DATA PEDIDO</th>
                                        <th>SHAPE</th>
                                        <th>TRUCK</th>
                                        <th>RODA</th>
                                        <th>LIXA</th>
                                        <th>FUNÇÃO</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="venda" items="${pagas}">
                                        <c:forEach var="skate" items="${venda.skates}"> 
                                            <tr>
                                                <td>${venda.id}</td>
                                                <td>${venda.cliente.nome}</td>
                                                <td>${venda.dtPedido}</td>
                                                <td>${skate.shape.modelo}</td>
                                                <td>${skate.truck.modelo}</td>
                                                <td>${skate.roda.modelo}</td>
                                                <td>${skate.lixa.modelo}</td>
                                                <td><button type="button" name="Editar" class="btn btn-primary" onClick="window.open('<c:url value="ControleVenda?IdObjeto=${skate.id}&Acao=APR_MONTAGEM_VENDA"/>', '_parent')"><span class="glyphicon glyphicon-ok"></span> MONTAR SKATE</button></td>
                                            </tr>
                                        </c:forEach>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </c:when>  
            </c:choose>
        </div>
    </body>
</html>
