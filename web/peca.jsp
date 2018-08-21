<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<HTML>
    <head>
        <c:if test="${token != pageContext.session.id}">
            <c:redirect url="ControleAcesso?Acao=LOGOFF"/>
        </c:if>

        <c:choose>
            <c:when test="${!empty peca.tipo}">
                <c:set var="TipoPeca" value="${peca.tipo}"/>
                <c:set var="ACAO" value="ALTERAR"/>
            </c:when>
            <c:otherwise>
                <c:set var="TipoPeca" value="NOVA PEÇA"/>
                <c:set var="ACAO" value="INCLUIR"/>
            </c:otherwise>
        </c:choose>

        <c:set var="tipos" value="${['SHAPE', 'TRUCK', 'RODA', 'LIXA']}"/>

        <c:import url="/imports/header.jsp"/>

        <title>PECA</title>

    </head>
    <body>
        <c:import url="/imports/cabecalho.jsp"/> 
        <div class="container">
            <div class="row">
                <div class="col-md-1"></div>
                <div class="col-md-10">
                    <div class="well well-sm">
                        <fieldset>
                            <legend>${ACAO} - ${TipoPeca}</legend> <br/>
                            <form class="form-horizontal" id="FormPeca" action="ControlePeca?Acao=INSERT_UPDATE_PECA" method="post" enctype="multipart/form-data">

                                <!-- ID -->
                                <div class="form-group">
                                    <div class="col-md-12">
                                        <input type="text" id="CliId" name="PecId" placeholder="ID" readonly
                                               value="${peca.id}"class="form-control"/>
                                    </div>
                                </div>

                                <!-- MODELO -->
                                <div class="form-group">
                                    <div class="col-md-8">
                                        <input type="text" id="PecModelo" name="PecModelo" placeholder="Modelo"
                                               value="${peca.modelo}" class="form-control"/>
                                    </div>

                                    <!-- TIPO -->
                                    <div class="col-md-4">
                                        <select class="form-control" id="PecTipo" name="PecTipo" required>
                                            <option value="" disabled  style="display:none;"   <c:if test="${empty cliente.id}">selected</c:if>>Tipo </option>                                            
                                            <c:forEach var="tipo" items="${tipos}">
                                                <option value="${tipo}"  <c:if test="${peca.tipo eq tipo}">selected</c:if>>${tipo}</option> 
                                            </c:forEach>                                            
                                        </select> 
                                    </div>
                                </div>

                                <!-- DESCRIÇÃO -->     
                                <div class="form-group">
                                    <div class="col-md-12">
                                        <textarea class="form-control" type="text" id="PecDescricao" name="PecDescricao" placeholder="Descrição da Peça" rows="3">${peca.descricao}</textarea>
                                    </div>
                                </div>

                                <!-- COR -->
                                <div class="form-group">
                                    <div class="col-md-12">
                                        <input type="text" id="PecCor" name="PecCor" placeholder="Cor "
                                               required value="${peca.cor}" class="form-control"/>
                                    </div>
                                </div>

                                <!-- TAMANHO -->
                                <div class="form-group">
                                    <div class="col-md-12">
                                        <input type="text" id="PecTamanho" name="PecTamanho" placeholder="Tamanho "
                                               required value="${peca.tamanho}" class="form-control"/>
                                    </div>
                                </div>

                                <!-- FORNECEDOR -->
                                <div class="form-group">
                                    <div class="col-md-12">
                                        <select class="form-control" id="PecFornecedor" name="PecFornecedor" required>
                                            <option value="" disabled  style="display:none;"   <c:if test="${empty fornecedor.id}">selected</c:if>>Fornecedor </option>                                            
                                            <c:forEach var="forn" items="${forns}">
                                                <option value="${forn.id}" <c:if test="${forn.id == peca.fornecedor.id}">selected</c:if>>${forn.nome}</option>
                                            </c:forEach>                                            
                                        </select> 
                                    </div>
                                </div>

                                <!-- ESTOQUE -->
                                <div class="form-group">
                                    <div class="col-md-12">
                                        <input type="text" id="PecQtd_estoque" name="PecQtd_estoque" placeholder="Quantidade de itens em Estoque "
                                               required value="${peca.qtd_estoque}" class="form-control"/>
                                    </div>
                                </div>

                                <!-- VALOR DE COMPRA -->
                                <div class="form-group">
                                    <div class="col-md-12">
                                        <input type="text" id="PecValor_compra" name="PecValor_compra" placeholder="Valor Unitário de Compra"
                                               required value="${peca.valor_compra}" class="form-control"/>
                                    </div>
                                </div>

                                <!-- VALOR DE VENDA -->
                                <div class="form-group">
                                    <div class="col-md-12">
                                        <input type="text" id="PecValor_venda" name="PecValor_venda" placeholder="Valor Unitário de Venda"
                                               required value="${peca.valor_venda}" class="form-control"/>
                                    </div>
                                </div>

                                <!-- DIRETORIO DA IMAGEM -->
                                <div class="form-group">
                                    <div class="col-md-12">
                                        <input type="file" id="PecRef_imagem" name="PecRef_imagem" placeholder=""
                                               <c:if test="${empty peca.ref_imagem}"> required </c:if>
                                               value="${peca.ref_imagem}" class="form-control"/>
                                    </div>
                                </div>
                                <!-- BTN ENVIAR -->
                                <br/>
                                <div class="row">
                                    <div class="col-md-5">
                                        <button class="btn btn-primary btn-block" type="submit" id="btnEnviar" name="btnEnviar"><span class="glyphicon glyphicon-ok"></span> Enviar</button>
                                    </div>
                                    <div class="col-md-2"></div>
                                    <div class="col-md-5">
                                        <button class="btn btn-danger btn-block" type="button" onClick="window.open('<c:url value="ControlePeca?Acao=LIST_PECAS"/>', '_parent')" id="btnEnviar" name="btnEnviar"><span class="glyphicon glyphicon-remove"></span> Cancelar</button>
                                    </div>
                                </div>
                            </form>
                            </fildset>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>