<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<nav class="navbar navbar-inverse">
    <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span> 
        </button>
        <a class="navbar-brand" href="<c:url value="ControleAcesso?Acao=HOME"/>"><span class="fi flaticon-web"></span> DebugSkate Shop</a>
    </div>

    <div class="collapse navbar-collapse" id="myNavbar">
        <c:choose>
            <c:when  test="${token == pageContext.session.id or user != null}">  
                <ul class="nav navbar-nav">
                    <!-- MENU PARA CLIENTES ----------------------------------------->
                    <li><a href="<c:url value="ControleSkate"/>"><span class="fi flaticon-transport"></span> Montar Skate</a></li>

                    <c:if test="${user.perfil eq 'ATENDENTE' or 
                                  user.perfil eq 'GERENTE' or 
                                  user.perfil eq 'ADMINISTRADOR'}">
                          <!-- MENU PARA ATENDENTES ----------------------------------------->
                          <li><a href="<c:url value="ControlePeca?Acao=LIST_PECAS"/>"><span class="fi flaticon-tools"></span> Gestão de Pecas</a></li>

                    </c:if>

                    <c:if test="${user.perfil eq 'GERENTE' or 
                                  user.perfil eq 'ADMINISTRADOR'}">
                          <!-- MENU PARA GERENTE ----------------------------------------->
                          <li class="dropdown">
                              <a href="#" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-delay="1000" data-close-others="false"><span class="fi flaticon-people-3"></span> Gestão de Pessoas <span class="caret"></span></a>
                              <ul class="dropdown-menu">
                                  <li><a href="<c:url value="ControlePessoa?Acao=LIST_FORNECEDORES"/>"><span class="fi flaticon-money-2"></span> Fornecedores</a></li><br/>
                                  <li><a href="<c:url value="ControlePessoa?Acao=LIST_CLIENTES"/>"><span class="fi flaticon-people-1"></span> Clientes</a></li><br/>
                                  <li><a href="<c:url value="ControlePessoa?Acao=LIST_FUNCIONARIOS"/>"><span class="fi flaticon-people-2"></span> Funcionarios</a></li><br/>
                              </ul>
                          </li>
                    </c:if>
                </ul>

                <ul class="nav navbar-nav navbar-right">
                    <li><a href="<c:url value="ControleVenda?Acao=CARRINHO"/>"><span class="fi flaticon-shop"></span> Carrinho</a></li>
                    <!-- ----------------------------------------------------FUNCIONARIOS---------------------------- -->
                    <c:if test="${user.perfil ne 'COMUM'}">

                        <li><a href="<c:url value="ControleVenda?Acao=LIST_VENDAS"/>"><span class="fi flaticon-signs"></span> Skates Pendentes</a></li>
                        <li><a>${funcionarioLogado.nome}</a></li>

                    </c:if>

                    <!-- ----------------------------------------------------CLIENTES---------------------------- -->
                    <c:if test="${user.perfil eq 'COMUM'}">

                        <li><a>${clienteLogado.nome}</a></li>

                    </c:if>

                    <li><a href="<c:url value="ControleAcesso?Acao=LOGOFF"/>"><span class="glyphicon glyphicon-log-out"></span> Sair</a></li>
                </ul>

            </c:when>

            <c:otherwise>

                <form class="navbar-form navbar-right" role="form" action="<c:url value="ControleAcesso?Acao=LOGIN"/>" method="post">
                    <div class="form-group input-group input-group-sm">
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-user"></span>
                        </span>
                        <input type="text" class="form-control" id="UsrLogin" name="UsrLogin" autofocus required placeholder="Usuario: "/>
                    </div>

                    <div class="form-group input-group input-group-sm">
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-lock"></span>
                        </span>
                        <input type="password" class="form-control" id="UsrSenha" name="UsrSenha" required placeholder="Senha: " />
                    </div>

                    <button type="submit" class="btn btn-default btn-sm " id="btnEnviar" name="btnEnviar"><span class="glyphicon glyphicon-log-in"></span> Login</button>
                </form>

            </c:otherwise>
        </c:choose>
    </div>
</nav>