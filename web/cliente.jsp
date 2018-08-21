<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <c:if test="${token != pageContext.session.id}">
            <c:redirect url="ControleAcesso?Acao=LOGOFF"/>
        </c:if>

        <c:set var="estados" value="${['AC', 'AL', 'AM', 'AP', 'BA', 'CE', 'DF', 'ES', 'GO', 'MA', 'MG', 'MS', 'MT', 'PA', 'PB', 'PE', 'PI', 'PR', 'RJ', 'RN', 'RS', 'RO', 'RR', 'SC', 'SE', 'SP', 'TO']}" scope="application" />

        <c:import url="/imports/header.jsp"/>

        <title>DEBUG SKATE - CLIENTE</title>
    </head>
    <body>
        <c:import url="/imports/cabecalho.jsp"/>
        <div class="container">
            <div class="row">
                <div class="col-md-1"></div>
                <div class="col-md-10">
                    <div class="well well-sm">
                        <fildset>
                            <legend>CLIENTE</legend>
                            <form class="form-horizontal" id="formCli" action="ControlePessoa?Acao=INSERT_UPDATE_CLIENTE" method="post">

                                <!-- ID -->
                                <div class="form-group sr-only">
                                    <div class="col-md-12 sr-only">
                                        <input type="text" id="CliId" name="CliId" placeholder="ID"
                                               autofocus value="${cliente.usuario.id}"class="form-control sr-only"/>
                                    </div>
                                </div>

                                <!-- LOGIN -->
                                <div class="form-group">
                                    <div class="col-md-12">
                                        <input type="text" id="CliLogin" name="CliLogin" placeholder="Login " <c:if test="${!empty cliente.id}">readonly</c:if>
                                               autofocus required value="${cliente.usuario.login}"class="form-control"/>
                                    </div>
                                </div>

                                <!-- SENHA -->
                                <div class="form-group">
                                    <div class="col-md-12">
                                        <input type="password" id="CliSenha" name="CliSenha" placeholder="Senha "
                                               required class="form-control"/>
                                    </div>
                                </div>

                                <!-- NOME -->
                                <div class="form-group">
                                    <div class="col-md-12">
                                        <input type="text" id="CliNome" name="CliNome" placeholder="Nome "
                                               required value="${cliente.nome}" class="form-control"/>
                                    </div>
                                </div>

                                <!-- CPF -->
                                <div class="form-group">
                                    <div class="col-md-12">
                                        <input type="text" id="CliCpf" name="CliCpf" placeholder="CPF "
                                               required value="${cliente.cpf_cnpj}"
                                               oninput="vCPF(this)" onKeyPress="MascaraCPF(this);" maxlength="14" class="form-control"/>
                                    </div>
                                </div>

                                <!-- EMAIL -->
                                <div class="form-group">
                                    <div class="col-md-12">
                                        <input type="email" id="CliEmail" name="CliEmail" placeholder="E-Mail"
                                               value="${cliente.email}" class="form-control"/>
                                    </div>
                                </div>

                                <!-- TELEFONE -->
                                <div class="form-group">
                                    <div class="col-md-12">
                                        <input type="tel" id="CliTelefone" name="CliTelefone" placeholder="Telefone"
                                               value="${cliente.telefone}"
                                               onKeyPress="MascaraTelefone(this);" maxlength="14" class="form-control"/>
                                    </div>
                                </div>

                                <!-- ENDEREÇO -->
                                <div class="form-group">
                                    <div class="col-md-9">
                                        <input type="text" id="CliEndereco" name="CliEndereco" placeholder="Endereço"
                                               value="${cliente.endereco}" class="form-control"/>
                                    </div>


                                    <!-- UF -->
                                    <div class="col-md-3">
                                        <select class="form-control" id="CliUf" name="CliUf" required>
                                            <option value="" disabled  style="display:none;"   <c:if test="${empty cliente.id}">selected</c:if>>Estado </option>                                            
                                            <c:forEach var="uf" items="${estados}">
                                                <option value="${uf}"  <c:if test="${cliente.uf eq uf}">selected</c:if>>${uf}</option> 
                                            </c:forEach>                                            
                                        </select> 
                                    </div>
                                </div>

                                <!-- DATA DE NASCIMENTO -->
                                <div class="form-group">
                                    <div class="col-md-12">
                                        <input type="text" id="CliDtNasc" name="CliDtNasc" placeholder="Data de Nascimento"
                                               value="${cliente.data_nasc}"
                                               onKeyPress="MascaraData(this);" oninput="vData(this)" maxlength="10" class="form-control datepicker"/>
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
                                        <button class="btn btn-danger btn-block" type="button" onClick="window.open('<c:url value="ControlePessoa?Acao=LIST_CLIENTES"/>', '_parent')" id="btnEnviar" name="btnEnviar"><span class="glyphicon glyphicon-remove"></span> Cancelar</button>
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
