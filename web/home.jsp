<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <c:import url="/imports/header.jsp"/>

        <title>DEBUG SKATE - HOME</title>

        <c:set var="estados" value="${['AC', 'AL', 'AM', 'AP', 'BA', 'CE', 'DF', 'ES', 'GO', 'MA', 'MG', 'MS', 'MT', 'PA', 'PB', 'PE', 'PI', 'PR', 'RJ', 'RN', 'RS', 'RO', 'RR', 'SC', 'SE', 'SP', 'TO']}" scope="application" />
        <link rel="stylesheet" href="css/carousel.css">
    </head>
    <body>
        <c:import url="/imports/cabecalho.jsp"/>
        <div id="mycarousel" class="carousel slide" data-ride="carousel">
            <!-- Indicadores -->
            <ol class="carousel-indicators">
                <li data-target="#mycarousel" data-slide-to="0"></li>
                <li data-target="#mycarousel" data-slide-to="1"></li>
                <li data-target="#mycarousel" data-slide-to="2"></li>
                <li data-target="#mycarousel" data-slide-to="3"></li>
                <li data-target="#mycarousel" data-slide-to="4"></li>
            </ol>
            <!-- Divs dos slides -->
            <div class="carousel-inner" role="listbox">
                <div class="item">
                    <img src="images/carousel/1.jpg">
                </div>
                <div class="item">
                    <img src="images/carousel/2.jpg">
                </div>
                <div class="item">
                    <img src="images/carousel/3.jpg">
                </div>
                <div class="item">
                    <img src="images/carousel/4.jpg">
                </div>
                <div class="item">
                    <img src="images/carousel/5.jpg">
                </div>
            </div>  
        </div>
        <!-- FIM DO CARROSSEL -->
        <img id="logoImg" src="images/logo.png">
        <div class="container" id="teste2323">
            <c:if test="${empty user}">
                <div class="col-xs-12 col-md-4 pull-right">
                    <div class="teemo-block">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="well well-sm">
                                    <fildset>
                                        <legend>CADASTRE-SE</legend>
                                        <form class="form-horizontal" id="formCli" action="ControleAcesso?Acao=CADASTRO_CLIENTE" method="post">

                                            <!-- LOGIN -->
                                            <div class="form-group">
                                                <div class="col-md-12">
                                                    <input type="text" id="CliLogin" name="CliLogin" required placeholder="Login" class="form-control"/>
                                                </div>
                                            </div>

                                            <!-- SENHA -->
                                            <div class="form-group">
                                                <div class="col-md-12">
                                                    <input type="password" id="CliSenha" name="CliSenha" placeholder="Senha" required class="form-control"/>
                                                </div>
                                            </div>

                                            <!-- NOME -->
                                            <div class="form-group">
                                                <div class="col-md-12">
                                                    <input type="text" id="CliNome" name="CliNome" required placeholder="Nome" class="form-control"/>
                                                </div>
                                            </div>

                                            <!-- CPF -->
                                            <div class="form-group">
                                                <div class="col-md-12">
                                                    <input type="text" id="CliCpf" name="CliCpf" required placeholder="CPF" oninput="vCPF(this)" onKeyPress="MascaraCPF(this);" maxlength="14" class="form-control"/> 
                                                </div>
                                            </div>

                                            <!-- EMAIL -->
                                            <div class="form-group">
                                                <div class="col-md-12">
                                                    <input type="email" id="CliEmail" name="CliEmail" placeholder="E-mail" class="form-control"/> 
                                                </div>
                                            </div>

                                            <!-- TELEFONE -->
                                            <div class="form-group">
                                                <div class="col-md-12">
                                                    <input type="tel" id="CliTelefone" name="CliTelefone" placeholder="Telefone" onKeyPress="MascaraTelefone(this);" maxlength="14" class="form-control"/>
                                                </div>
                                            </div>

                                            <!-- ENDEREÇO -->
                                            <div class="form-group">
                                                <div class="col-md-12">
                                                    <input type="text" id="CliEndereco" name="CliEndereco" placeholder="Endereço" class="form-control"/> 
                                                </div>
                                            </div>

                                            <!-- UF -->
                                            <div class="form-group">
                                                <div class="col-md-12">
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
                                                    <input type="text" id="CliDtNasc" name="CliDtNasc" placeholder="Data de Nascimento" onKeyPress="MascaraData(this);"  oninput="vData(this)" maxlength="10" class="form-control datepicker"/> 
                                                </div>
                                            </div>

                                            <!-- TERMOS DE USO -->
                                            <div class="checkbox" id="TermosUso">
                                                <label><input type="checkbox" id="TermoUso" name="TermoUso" data-toggle="collapse" data-target="#btnEnviarCollapse" required>Aceito os Termos de uso: </label>
                                            </div>
                                            <br/>

                                            <!-- BOTÃO ENVIAR -->
                                            <div id="btnEnviarCollapse" class="collapse">
                                                <button class="btn btn-primary btn-block" type="submit" id="btnEnviar" name="btnEnviar">Enviar</button>
                                            </div>
                                        </form>
                                    </fildset>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </c:if>
        </div>
        <script src="js/carousel.js"></script>
    </body>
</html>
