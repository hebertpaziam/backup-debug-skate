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
        <c:set var="perfis" value="${['ATENDENTE', 'GERENTE', 'ADMINISTRADOR']}" scope="application" />

        <c:import url="/imports/header.jsp"/>
        
        <title>DEBUG SKATE - FUNCIONARIO</title>
    </head>
    <body>
        <c:import url="/imports/cabecalho.jsp"/>        
        <div class="container">
            <div class="row">
                <div class="col-md-1"></div>
                <div class="col-md-10">
                    <div class="well well-sm">
                        <fildset>
                            <legend>FUNCIONARIO</legend>
                            <form class="form-horizontal" id="form" action="ControlePessoa?Acao=INSERT_UPDATE_FUNCIONARIO" method="post">

                                <!-- ID -->
                                <div class="form-group sr-only">
                                    <div class="col-md-12 sr-only">
                                        <input type="text" id="FuncId" name="FuncId" placeholder="ID"
                                               autofocus value="${funcionario.usuario.id}"class="form-control sr-only"/>
                                    </div>
                                </div>


                                <!-- LOGIN -->
                                <div class="form-group">
                                    <div class="col-md-12">
                                        <input type="text" id="FuncLogin" name="FuncLogin" placeholder="Login " <c:if test="${!empty funcionario.id}">readonly</c:if>
                                               autofocus required value="${funcionario.usuario.login}"class="form-control"/>
                                    </div>
                                </div>

                                <!-- SENHA -->
                                <div class="form-group">
                                    <div class="col-md-12">
                                        <input type="password" id="FuncSenha" name="FuncSenha" placeholder="Senha "
                                               required class="form-control"/>
                                    </div>
                                </div>

                                <!-- NOME -->
                                <div class="form-group">
                                    <div class="col-md-12">
                                        <input type="text" id="FuncNome" name="FuncNome" placeholder="Nome "
                                               required value="${funcionario.nome}" class="form-control"/>
                                    </div>
                                </div>

                                <!-- CPF -->
                                <div class="form-group">
                                    <div class="col-md-12">
                                        <input type="text" id="FuncCpf" name="FuncCpf" placeholder="CPF "
                                               required value="${funcionario.cpf_cnpj}"
                                               oninput="vCPF(this)" onKeyPress="MascaraCPF(this);" maxlength="14" class="form-control"/>
                                    </div>
                                </div>

                                <!-- EMAIL -->
                                <div class="form-group">
                                    <div class="col-md-12">
                                        <input type="email" id="FuncEmail" name="FuncEmail" placeholder="E-Mail"
                                               value="${funcionario.email}" class="form-control"/>
                                    </div>
                                </div>

                                <!-- TELEFONE -->
                                <div class="form-group">
                                    <div class="col-md-12">
                                        <input type="tel" id="FuncTelefone" name="FuncTelefone" placeholder="Telefone"
                                               value="${funcionario.telefone}"
                                               onKeyPress="MascaraTelefone(this);" maxlength="14" class="form-control"/>
                                    </div>
                                </div>

                                <!-- ENDEREÇO -->
                                <div class="form-group">
                                    <div class="col-md-9">
                                        <input type="text" id="FuncEndereco" name="FuncEndereco" placeholder="Endereço"
                                               value="${funcionario.endereco}" class="form-control"/>
                                    </div>


                                    <!-- UF -->
                                    <div class="col-md-3">
                                        <select class="form-control" id="FuncUf" name="FuncUf" required>
                                            <option value="" disabled  style="display:none;"   <c:if test="${empty funcionario.id}">selected</c:if>>Estado </option>                                            
                                            <c:forEach var="uf" items="${estados}">
                                                <option value="${uf}"  <c:if test="${funcionario.uf eq uf}">selected</c:if>>${uf}</option> 
                                            </c:forEach>                                            
                                        </select> 
                                    </div>
                                </div>

                                
                                <div class="form-group">
                                    
                                    <!-- DATA DE NASCIMENTO -->
                                    <div class="col-md-4">
                                        <input type="text" id="FuncDtNasc" name="FuncDtNasc" placeholder="Data de Nascimento"
                                               value="${funcionario.data_nasc}"
                                               onKeyPress="MascaraData(this);" oninput="vData(this)" maxlength="10" class="form-control datepicker"/>
                                    </div>

                                    <!-- MATRICULA -->
                                    <div class="col-md-4">
                                        <input type="number" id="FuncMatricula" name="FuncMatricula" placeholder="Matricula "
                                               required value="${funcionario.matricula}" class="form-control"/>
                                    </div>

                                    <!-- PERFIL -->
                                    <div class="col-md-4">
                                        <select class="form-control" id="FuncPerfil" name="FuncPerfil" required>
                                            <option value="" disabled  style="display:none;"   <c:if test="${empty funcionario.id}">selected</c:if>>Perfil </option>                                            
                                            <c:forEach var="perfil" items="${perfis}">
                                                <option value="${perfil}"  <c:if test="${funcionario.usuario.perfil eq perfil}">selected</c:if>>${perfil}</option> 
                                            </c:forEach>                                            
                                        </select> 
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
                                        <button class="btn btn-danger btn-block" type="button" onClick="window.open('<c:url value="ControlePessoa?Acao=LIST_FUNCIONARIOS"/>', '_parent')" id="btnEnviar" name="btnEnviar"><span class="glyphicon glyphicon-remove"></span> Cancelar</button>
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
