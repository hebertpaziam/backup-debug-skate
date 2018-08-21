<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
    <head>
        <c:if test="${token != pageContext.session.id}">
            <c:redirect url="ControleAcesso?Acao=LOGOFF"/>
        </c:if>
        
        <c:import url="/imports/header.jsp"/>
        

        <title>Solicitação de Peças</title>
    </head>
    <body>
        <c:import url="/imports/cabecalho.jsp"/>
        
        <div align="center">
            <h1 align="center">Solicitação de Peças</h1><br/><br/>
            <fieldset>
                <legend align="center">PEÇAS A REPOR NO ESTOQUE</legend><br/>
                <form action="<c:url value="ControleSolicitacao"/>" method="get">
                    <c:if test="${spPecas.itensDeSolicitacao.size()  > 0}">   
                        <table border="1" name="TabelaSolicitacao" class="table-hover table-condensed">
                            <thead>
                            <th>ID</th>
                            <th>TIPO</th>
                            <th>MODELO</th>
                            <th>COR</th>
                            <th>TAMANHO</th>
                            <th>FORNECEDOR</th>
                            <th>ESTOQUE</th>
                            <th>QTD NECESSARIA</th>
                            </thead>
                            <tbody>
                                <c:forEach var="solicitacao" items="${spPecas.itensDeSolicitacao}">   
                                    <tr>
                                        <td>${solicitacao.idSol}</td>
                                        <td>${solicitacao.peca.tipo}</td>
                                        <td>${solicitacao.peca.modelo}</td>
                                        <td>${solicitacao.peca.cor}</td>
                                        <td>${solicitacao.peca.tamanho}</td>
                                        <td>${solicitacao.peca.fornecedor.nome}</td>
                                        <td>${solicitacao.peca.qtd_estoque}</td>
                                        <td>${solicitacao.qtdPecas}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table> <br/><br/>
                        <input type="button" onClick="window.open('ControleSolicitacao?Acao=NEW_SOLICITACAO', '_parent')" value="ENVIAR SOLICITAÇÃO DE PEÇAS"/>
                    </c:if>
                    <c:if test="${spPecas.itensDeSolicitacao.size() == 0 || spPecas.itensDeSolicitacao.size() == null}">
                        <h1>NÃO HÁ PEÇAS PARA REPOR</h1>
                    </c:if>    
                </form>
            </fieldset><br/><br/>
            <input type="button" onClick="window.open('ControlePeca?Acao=LIST_PECAS', '_parent')" value="VOLTAR"/>
        </div>

        <!-- BOOSTRAP & JQUERY --
        <script type="text/javascript" src="js/jquery-2.1.4.js"></script>
        <script type="text/javascript" src="js/bootstrap.js"></script>
        -->
    </body>
</html>