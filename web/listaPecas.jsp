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

        <title>DEBUG SKATE - LISTA DE PEÇAS</title>        
    </head>
    <body>
        <c:import url="/imports/cabecalho.jsp"/>   
        <div class="container-fluid">
            <h1>Lista de Pecas</h1>

            <button type="button" class="btn btn-success" onClick="window.open('<c:url value="ControlePeca?Acao=NEW_PECA"/>', '_parent')"> NOVA PEÇA </button>
            <button type="button" class="btn btn-primary" onClick="window.open('<c:url value="ControleSolicitacao?Acao=LIST_SOLICITACAO"/>', '_parent')"> SOLICITAR PEÇAS </button>
            <button type="button" class="btn btn-primary" onClick="window.open('<c:url value="ControleSolicitacao?Acao=APROV_SOLICITACAO"/>', '_parent')"> SOLICITAÇÕES </button>

            <br/><br/>


            <ul class="nav nav-tabs">
                <li class="active"><a data-toggle="pill" href="#SelShape">Shape</a></li>
                <li><a data-toggle="pill" href="#SelTruck">Truck</a></li>
                <li><a data-toggle="pill" href="#SelRoda">Roda</a></li>
                <li><a data-toggle="pill" href="#SelLixa">Lixa</a></li>
            </ul>

            <div class="tab-content">
                <!-- ---------------------TABELA SHAPE --------------------------- -->
                <div id="SelShape" class="tab-pane fade in active">
                    <h3>Shape</h3>
                    <table class="table table-striped table-hover smartTable" name="TabelaShape" id="TabelaShape">
                        <thead>
                            <tr class="text-center">
                                <th> ID         </th>
                                <th> MODELO     </th>
                                <th> COR        </th>
                                <th> TAMANHO    </th>
                                <th> VALOR      </th>
                                <th> ESTOQUE    </th>
                                <th> FORNECEDOR </th>
                                <th> FUNÇÕES    </th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="peca" items="${pecas}"> 
                                <c:if test="${peca.tipo eq 'SHAPE'}">
                                    <tr>
                                        <td class="text-center">${peca.id}</td>
                                        <td>${peca.modelo}</td>
                                        <td>${peca.cor}</td>
                                        <td class="text-center">${peca.tamanho}</td>
                                        <td class="text-right" >${peca.valor_venda}</td>
                                        <td class="text-center">${peca.qtd_estoque}</td>
                                        <td>${peca.fornecedor.nome}</td>
                                        <td><button type="button" name="Editar" class="btn btn-default" onClick="window.open('<c:url value="ControlePeca?Acao=EDIT_PECA&IdObjeto=${peca.id}"/>', '_parent')"><span class="glyphicon glyphicon-pencil"></span></button>&nbsp;
                                            <button type="button" name="btnDeletar" class="btn btn-default" onClick="window.open('<c:url value="ControlePeca?Acao=DELETE_PECA&IdObjeto=${peca.id}"/>', '_parent')"><span class="glyphicon glyphicon-remove"></span></button></td>
                                    </tr>
                                </c:if>    
                            </c:forEach>
                        </tbody>
                    </table>
                </div>


                <!-- ---------------------TABELA TRUCK --------------------------- -->
                <div id="SelTruck" class="tab-pane fade">
                    <h3>Truck</h3>
                    <table class="table table-striped table-hover smartTable" name="TabelaTruck" id="TabelaTruck">
                        <thead>
                            <tr>
                                <th> ID         </th>
                                <th> MODELO     </th>
                                <th> COR        </th>
                                <th> TAMANHO    </th>
                                <th> VALOR      </th>
                                <th> ESTOQUE    </th>
                                <th> FORNECEDOR </th>
                                <th> FUNÇÕES    </th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="peca" items="${pecas}"> 
                                <c:if test="${peca.tipo eq 'TRUCK'}">
                                    <tr>
                                        <td class="text-center">${peca.id}</td>
                                        <td>${peca.modelo}</td>
                                        <td>${peca.cor}</td>
                                        <td class="text-center">${peca.tamanho}</td>
                                        <td class="text-right" >${peca.valor_venda}</td>
                                        <td class="text-center">${peca.qtd_estoque}</td>
                                        <td>${peca.fornecedor.nome}</td>
                                        <td><button type="button" name="Editar" class="btn btn-default" onClick="window.open('<c:url value="ControlePeca?Acao=EDIT_PECA&IdObjeto=${peca.id}"/>', '_parent')"><span class="glyphicon glyphicon-pencil"></span></button>&nbsp;
                                            <button type="button" name="btnDeletar" class="btn btn-default" onClick="window.open('<c:url value="ControlePeca?Acao=DELETE_PECA&IdObjeto=${peca.id}"/>', '_parent')"><span class="glyphicon glyphicon-remove"></span></button></td>
                                    </tr>
                                </c:if>    
                            </c:forEach>
                        </tbody>
                    </table>
                </div>


                <!-- ---------------------TABELA RODA --------------------------- -->
                <div id="SelRoda" class="tab-pane fade">
                    <h3>Roda</h3>
                    <table class="table table-striped table-hover smartTable" name="TabelaRoda" id="TabelaRoda">
                        <thead>
                            <tr>
                                <th> ID         </th>
                                <th> MODELO     </th>
                                <th> COR        </th>
                                <th> TAMANHO    </th>
                                <th> VALOR      </th>
                                <th> ESTOQUE    </th>
                                <th> FORNECEDOR </th>
                                <th> FUNÇÕES    </th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="peca" items="${pecas}"> 
                                <c:if test="${peca.tipo eq 'RODA'}">
                                    <tr>
                                        <td class="text-center">${peca.id}</td>
                                        <td>${peca.modelo}</td>
                                        <td>${peca.cor}</td>
                                        <td class="text-center">${peca.tamanho}</td>
                                        <td class="text-right" >${peca.valor_venda}</td>
                                        <td class="text-center">${peca.qtd_estoque}</td>
                                        <td>${peca.fornecedor.nome}</td>
                                        <td><button type="button" name="Editar" class="btn btn-default" onClick="window.open('<c:url value="ControlePeca?Acao=EDIT_PECA&IdObjeto=${peca.id}"/>', '_parent')"><span class="glyphicon glyphicon-pencil"></span></button>&nbsp;
                                            <button type="button" name="btnDeletar" class="btn btn-default" onClick="window.open('<c:url value="ControlePeca?Acao=DELETE_PECA&IdObjeto=${peca.id}"/>', '_parent')"><span class="glyphicon glyphicon-remove"></span></button></td>
                                    </tr>
                                </c:if>    
                            </c:forEach>
                        </tbody>
                    </table>
                </div>


                <!-- ---------------------TABELA LIXA --------------------------- -->
                <div id="SelLixa" class="tab-pane fade">
                    <h3>Lixa</h3>
                    <table class="table table-striped table-hover smartTable" name="TabelaLixa" id="TabelaLixa">
                        <thead>
                            <tr>
                                <th> ID         </th>
                                <th> MODELO     </th>
                                <th> COR        </th>
                                <th> TAMANHO    </th>
                                <th> VALOR      </th>
                                <th> ESTOQUE    </th>
                                <th> FORNECEDOR </th>
                                <th> FUNÇÕES    </th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="peca" items="${pecas}"> 
                                <c:if test="${peca.tipo eq 'LIXA'}">
                                    <tr>
                                        <td class="text-center">${peca.id}</td>
                                        <td>${peca.modelo}</td>
                                        <td>${peca.cor}</td>
                                        <td class="text-center">${peca.tamanho}</td>
                                        <td class="text-right" >${peca.valor_venda}</td>
                                        <td class="text-center">${peca.qtd_estoque}</td>
                                        <td>${peca.fornecedor.nome}</td>
                                        <td><button type="button" name="Editar" class="btn btn-default" onClick="window.open('<c:url value="ControlePeca?Acao=EDIT_PECA&IdObjeto=${peca.id}"/>', '_parent')"><span class="glyphicon glyphicon-pencil"></span></button>&nbsp;
                                            <button type="button" name="btnDeletar" class="btn btn-default" onClick="window.open('<c:url value="ControlePeca?Acao=DELETE_PECA&IdObjeto=${peca.id}"/>', '_parent')"><span class="glyphicon glyphicon-remove"></span></button></td>
                                    </tr>
                                </c:if>    
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </body>
</html>
