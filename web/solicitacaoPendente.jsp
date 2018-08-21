<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
    <head>
        <c:if test="${token != pageContext.session.id}">
            <c:redirect url="ControleAcesso?Acao=LOGOFF"/>
        </c:if>
        
        <c:import url="/imports/header.jsp"/>

        <title>Solicitação Pendente</title>
    </head>
    <body> 
        
        <c:import url="/imports/cabecalho.jsp"/>
        
        <c:if test="${!empty erro}"> onload="erroPendente()" </c:if> >
            <div align="center">

                <fieldset>

                    <form action="<c:url value="ControleSolicitacao"/>" method="get">
                    <c:if test="${spPecas.itensDeSolicitacao.size() == 0 || spPecas.itensDeSolicitacao.size() == null}">
                        <h1>NÃO HÁ SOLICITAÇÕES EM ABERTO.</h1>
                    </c:if>
                    <c:if test="${spPecas.itensDeSolicitacao.size()  > 0}">
                        <h1 align="center">Solicitação Pendente</h1><br/><br/>
                        <table>
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
                        <input type="button" onClick="window.open('ControleSolicitacao?Acao=DELETE_SOLICITACAO&IdObjeto=${spPecas.idSol}', '_parent')" value="REJEITAR SOLICITAÇÃO"/>
                        <c:if test="${user.perfil  eq 'GERENTE'}">
                            <input type="button" onClick="window.open('ControleSolicitacao?Acao=SEND_SOLICITACAO&IdObjeto=${spPecas.idSol}', '_parent')" value="APROVAR SOLICITAÇÃO"/>
                        </c:if>
                    </c:if>
                </form>
            </fieldset><br/><br/>
            <input type="button" onClick="window.open('ControlePeca?Acao=LIST_PECAS', '_parent')" value="VOLTAR"/>
        </div>

        <!-- BOOSTRAP & JQUERY --
        <script type="text/javascript" src="js/jquery-2.1.4.js"></script>
        <script type="text/javascript" src="js/bootstrap.js"></script>
        -->
        <script>
            function erroPendente() {   //ENVIA ALERTA PARA O USUÁRIO
                alert("<c:out value= "${erro}"/>");
            }
        </script>

    </body>
</html>