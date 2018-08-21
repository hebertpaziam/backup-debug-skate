<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <c:if test="${token != pageContext.session.id}">
            <c:redirect url="ControleAcesso?Acao=LOGOFF"/>
        </c:if>

        <c:set var="estados" value="${['AC', 'AL', 'AM', 'AP', 'BA', 'CE', 'DF', 'ES', 'GO', 'MA', 'MG', 'MS', 'MT', 'PA', 'PB', 'PE', 'PI', 'PR', 'RJ', 'RN', 'RS', 'RO', 'RR', 'SC', 'SE', 'SP', 'TO']}" scope="application" />

        <c:import url="/imports/header.jsp"/>
        
        <title>DEBUG SKATE - FORNECEDOR</title>
    </head>
    <body>
        <c:import url="/imports/cabecalho.jsp"/>
        <div class="container">
            <div class="row">
                <div class="col-md-1"></div>
                <div class="col-md-10">
                    <div class="well well-sm">
                        <fildset>
                            <legend>FORNECEDOR</legend>
                            <form class="form-horizontal" id="form" action="ControlePessoa?Acao=INSERT_UPDATE_FORNECEDOR" method="post">

                                <!-- ID -->
                                <div class="form-group sr-only">
                                    <div class="col-md-12 sr-only">
                                        <input type="text" id="FornId" name="FornId" placeholder="ID"
                                               autofocus value="${fornecedor.id}"class="form-control sr-only"/>
                                    </div>
                                </div>

                                <!-- NOME FANTASIA -->
                                <div class="form-group">
                                    <div class="col-md-12">
                                        <input type="text" id="FornNome" name="FornNome" placeholder="Nome Fantasia"
                                               required value="${fornecedor.nome}" class="form-control"/>
                                    </div>
                                </div>

                                <!-- CNPJ -->
                                <div class="form-group">
                                    <div class="col-md-12">
                                        <input type="text" id="FornCnpj" name="FornCnpj" placeholder="CNPJ "
                                               required value="${fornecedor.cpf_cnpj}"
                                               oninput="vCNPJ(this)" onKeyPress="MascaraCNPJ(this);" maxlength="18" class="form-control"/>
                                    </div>
                                </div>

                                <!-- EMAIL -->
                                <div class="form-group">
                                    <div class="col-md-12">
                                        <input type="email" id="FornEmail" name="FornEmail" placeholder="E-Mail"
                                               value="${fornecedor.email}" class="form-control"/>
                                    </div>
                                </div>

                                <!-- TELEFONE -->
                                <div class="form-group">
                                    <div class="col-md-12">
                                        <input type="tel" id="FornTelefone" name="FornTelefone" placeholder="Telefone"
                                               value="${fornecedor.telefone}"
                                               onKeyPress="MascaraTelefone(this);" maxlength="14" class="form-control"/>
                                    </div>
                                </div>

                                <!-- ENDEREÇO -->
                                <div class="form-group">
                                    <div class="col-md-9">
                                        <input type="text" id="FornEndereco" name="FornEndereco" placeholder="Endereço"
                                               value="${fornecedor.endereco}" class="form-control"/>
                                    </div>


                                    <!-- UF -->
                                    <div class="col-md-3">
                                        <select class="form-control" id="FornUf" name="FornUf" required>
                                            <option value="" disabled  style="display:none;"   <c:if test="${empty fornecedor.id}">selected</c:if>>Estado </option>                                            
                                            <c:forEach var="uf" items="${estados}">
                                                <option value="${uf}"  <c:if test="${fornecedor.uf eq uf}">selected</c:if>>${uf}</option> 
                                            </c:forEach>                                            
                                        </select> 
                                    </div>
                                </div>

                                <!-- CONTATO -->
                                <div class="form-group">
                                    <div class="col-md-12">
                                        <input type="text" id="FornContato" name="FornContato" placeholder="Contato"
                                               required value="${fornecedor.contato}" class="form-control"/>
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
                                        <button class="btn btn-danger btn-block" type="button" onClick="window.open('<c:url value="ControlePessoa?Acao=LIST_FORNECEDORES"/>', '_parent')" id="btnEnviar" name="btnEnviar"><span class="glyphicon glyphicon-remove"></span> Cancelar</button>
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
