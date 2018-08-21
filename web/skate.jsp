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

        <title>DEBUG SKATE - MONTA SKATE</title>        
    </head>

    <body>
        <c:import url="/imports/cabecalho.jsp"/>

        <div class="container" id="container">
            <h1>Monta Skate</h1>

            <!-- DIV Imagem do Skate -->
            <div class="montaSkate col-md-7" >
                <img id="skateBase" class="img-responsive" src="images/sktBase/sktBase.png" border="0" width="600" height="375" orgWidth="600" orgHeight="375" usemap="#skateBase" alt="" />
                <map name="skateBase" id="skateBase">

                    <!-- MAP TRUCKS -->
                    <area  id="mapTruck" title="TRUCK" href=# shape="poly" coords="449,281,435,280,424,287,350,298,345,294,331,294,328,283,325,272,322,266,316,261,309,258,306,247,306,245,311,241,312,236,302,197,309,195,312,204,313,215,317,232,324,239,333,244,333,257,334,264,337,273,340,281,347,276,356,273,356,265,359,262,369,260,381,258,389,255,391,244,397,242,403,248,402,256,412,264,420,269,432,269,439,272,446,269" style="outline:none;" target="_self" onclick="$('.panel-collapse').collapse('hide');
                            $('#collapse2').collapse('toggle');"/>
                    <area  title="TRUCK" href=# shape="poly" coords="332,187,391,185,428,187,430,194,416,193,411,205,405,205,389,205,362,213,351,214,347,213,345,196,337,195" style="outline:none;" target="_self" onclick="$('#mapTruck').click()"/>
                    <area  title="TRUCK" href=# shape="poly" coords="83,264,102,264,111,264,148,264,164,262,168,269,171,279,184,268,185,261,196,258,197,253,187,251,190,243,184,237,191,203,184,203,178,199,165,197,146,198,122,200,133,203,135,209,149,212,159,215,167,215,167,204,185,211,181,219,178,231,171,239,167,249,159,248,152,244,149,240,148,236,146,235,142,235,139,233,139,242,134,247,123,253,115,253,107,257,84,258" style="outline:none;" target="_self" onclick="$('#mapTruck').click()"/>
                    <!-- MAP SHAPE -->
                    <area  id="mapShape" title="SHAPE" href=# shape="poly" coords="221,178,296,172,351,169,396,163,429,164,456,164,497,152,547,132,577,107,583,95,573,86,556,85,536,85,515,87,483,92,444,103,389,128,366,139,334,144,310,144,279,146,259,150,240,151,217,152,196,159,165,163,144,164,119,168,92,174,82,176,114,180,154,183,186,184" style="outline:none;" onclick="$('.panel-collapse').collapse('hide');
                            $('#collapse1').collapse('toggle');"/>
                    <!-- MAP RODAS -->                    
                    <area  id="mapRoda" title="RODA" href=# shape="poly" coords="291,329,301,327,310,326,317,323,325,316,328,306,329,298,329,289,329,283,325,277,323,271,321,268,318,264,316,262,312,258,306,258,303,257,296,258,290,261,288,257,281,261,277,267,273,270,270,275,267,280,280,283,287,292,285,298,283,304,280,308,275,309,271,309,269,312,275,319,282,326" style="outline:none;" target="_self" onclick="$('.panel-collapse').collapse('hide');
                            $('#collapse3').collapse('toggle');"/>
                    <area  title="RODA" href=# shape="poly" coords="433,285,439,293,448,298,455,299,464,299,469,296,474,292,481,282,482,287,478,290,484,275,480,266,480,262,477,257,472,253,466,250,462,249,457,249,451,249,446,250,443,252,439,254,434,258,431,263,430,269,437,269,448,272,451,279,446,278,442,281,437,282" style="outline:none;" target="_self" onclick="$('#mapRoda').click()"/>
                    <area  title="RODA" href=# shape="poly" coords="58,243,69,242,76,249,79,258,80,268,78,276,75,281,71,283,65,284,58,284,53,283,48,277,45,272,47,255,48,248,53,245" style="outline:none;" target="_self" onclick="$('#mapRoda').click()"/>
                    <area  title="RODA" href=# shape="poly" coords="195,253,195,249,198,242,203,240,207,239,215,238,220,243,223,251,224,260,221,265,219,270,217,272,212,272,207,272,202,272,198,269,196,265,196,259" style="outline:none;" target="_self" onclick="$('#mapRoda').click()"/>
                    <!-- MAP LIXA -->
                    <area  id="mapLixa" alt="" title="LIXA" href=# shape="poly" coords="550,79,567,63,575,51,575,44,568,36,561,33,552,29,545,32,536,30,526,30,511,34,496,36,478,39,465,44,455,47,446,51,437,53,424,60,412,63,398,69,382,77,359,88,346,91,333,94,321,97,305,95,299,95,288,98,279,101,257,106,235,108,218,113,207,116,187,121,168,125,147,130,133,130,115,135,108,137,98,137,91,136,77,135,66,135,59,140,69,143,77,144,91,147,170,155,216,148,327,133,369,127,388,116,425,105,446,97,467,92,478,85,502,82,524,78,541,78" style="outline:none;" target="_self" onclick="$('.panel-collapse').collapse('hide');
                            $('#collapse4').collapse('toggle');"/>
                </map>
            </div>


            <div class="pecas col-md-5">
                <form method="post" action="ControleSkate">
                    <div class="panel-group" id="accordion">
                        <!-- DIV DAS SHAPES -->
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h4 class="panel-title">
                                    <a data-toggle="collapse" data-parent="#accordion" href="#collapse1" onclick="$('#mapShape').click()">SHAPE</a>
                                </h4>
                            </div>
                            <div id="collapse1" class="panel-collapse collapse">
                                <div class="panel-body" id="scroll">
                                    <div class="list-group">
                                        <c:forEach var="peca" items="${pecas}" varStatus="pos">
                                            <c:if test="${peca.tipo == 'SHAPE'}">
                                                <div class="list-group-item col-md-12 pecahover">
                                                    <!-- PEÇAS -->
                                                    <div class="shape-image col-md-2">
                                                        <a href="${peca.ref_imagem}" data-lightbox="image-${pos.count}" data-title="${peca.modelo}">
                                                            <img id="skateShapeImg" src="${peca.ref_imagem}" data-lightbox="image-${pos.count}" border="0" width="65" height="65"/>
                                                        </a>
                                                    </div>
                                                    <div class="shape-image col-md-1"></div>
                                                    <div class="shape-descricao col-md-8" onclick="$('#shape-radio-${pos.count}').click()">
                                                        <h4 class="list-group-item-heading">${peca.modelo}</h4>
                                                        <p class="list-group-item-text">${peca.descricao}</p>
                                                        <p class="list-group-item-text">R$ ${peca.valor_venda}</p>
                                                    </div>
                                                    <div class="shape-check col-md-1" >
                                                        <c:set var="radioShapeCheck" value="" />
                                                        <c:if test="${!empty skateEdit && skateEdit.shape.id == peca.id}">
                                                            <c:set var="radioShapeCheck" value="checked" />
                                                        </c:if>
                                                        <br/>
                                                        <input name="SktShape" id="shape-radio-${pos.count}" type="radio" class="radioCheckPecas" value="${peca.id}" required ${radioShapeCheck} onclick="$('#collapse1').collapse('toggle');">
                                                    </div>
                                                </div>
                                            </c:if>
                                        </c:forEach>
                                        <!-- FIM DE PEÇAS -->
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- FIM DIV SHAPES -->

                        <!-- DIV DAS TRUCKS -->
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h4 class="panel-title">
                                    <a data-toggle="collapse" data-parent="#accordion" href="#collapse2" onclick="$('#mapTruck').click()">TRUCK</a>
                                </h4>
                            </div>
                            <div id="collapse2" class="panel-collapse collapse">
                                <div class="panel-body" id="scroll">
                                    <div class="list-group">
                                        <c:forEach var="peca" items="${pecas}" varStatus="pos">
                                            <c:if test="${peca.tipo == 'TRUCK'}">
                                                <div class="list-group-item col-md-12 pecahover">
                                                    <!-- PEÇAS -->
                                                    <div class="truck-image col-md-2">
                                                        <a href="${peca.ref_imagem}" data-lightbox="image-${pos.count}" data-title="${peca.modelo}">
                                                            <img id="skateTruckImg" src="${peca.ref_imagem}" data-lightbox="image-${pos.count}" border="0" width="65" height="65"/>
                                                        </a>
                                                    </div>
                                                    <div class="shape-image col-md-1"></div>
                                                    <div class="truck-descricao col-md-8" onclick="$('#truck-radio-${pos.count}').click()">
                                                        <h4 class="list-group-item-heading">${peca.modelo}</h4>
                                                        <p class="list-group-item-text">${peca.descricao}</p>
                                                        <p class="list-group-item-text">R$ ${peca.valor_venda}</p>
                                                    </div>
                                                    <div class="truck-check col-md-1" >
                                                        <c:set var="radioTruckCheck" value="" />
                                                        <c:if test="${!empty skateEdit && skateEdit.truck.id == peca.id}">
                                                            <c:set var="radioTruckCheck" value="checked" />
                                                        </c:if>
                                                        <br/>
                                                        <input name="SktTruck" id="truck-radio-${pos.count}" type="radio" class="radioCheckPecas" value="${peca.id}" required ${radioTruckCheck} onclick="$('#collapse2').collapse('toggle');">
                                                    </div>
                                                </div>
                                            </c:if>
                                        </c:forEach>
                                        <!-- FIM DE PEÇAS -->
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- FIM DIV TRUCKS -->

                        <!-- DIV DAS RODAS -->
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h4 class="panel-title">
                                    <a data-toggle="collapse" data-parent="#accordion" href="#collapse3" onclick="$('#mapRoda').click()">RODA</a>
                                </h4>
                            </div>
                            <div id="collapse3" class="panel-collapse collapse">
                                <div class="panel-body" id="scroll">
                                    <div class="list-group">
                                        <input type="text" id="SktId" name="SktId"  hidden placeholder="Auto" 		
                                               readonly value="${skateEdit.id}"/>
                                        <c:forEach var="peca" items="${pecas}" varStatus="pos">
                                            <c:if test="${peca.tipo == 'RODA'}">
                                                <div class="list-group-item col-md-12 pecahover">
                                                    <!-- PEÇAS -->
                                                    <div class="roda-image col-md-2">
                                                        <a href="${peca.ref_imagem}" data-lightbox="image-${pos.count}" data-title="${peca.modelo}">
                                                            <img id="skateRodaImg" src="${peca.ref_imagem}" data-lightbox="image-${pos.count}" border="0" width="65" height="65"/>
                                                        </a>
                                                    </div>
                                                    <div class="shape-image col-md-1"></div>
                                                    <div class="roda-descricao col-md-8" onclick="$('#roda-radio-${pos.count}').click()">
                                                        <h4 class="list-group-item-heading">${peca.modelo}</h4>
                                                        <p class="list-group-item-text">${peca.descricao}</p>
                                                        <p class="list-group-item-text">R$ ${peca.valor_venda}</p>
                                                    </div>
                                                    <div class="roda-check col-md-1" >
                                                        <c:set var="radioRodaCheck" value="" />
                                                        <c:if test="${!empty skateEdit && skateEdit.roda.id == peca.id}">
                                                            <c:set var="radioRodaCheck" value="checked" />
                                                        </c:if>
                                                        <br/>
                                                        <input name="SktRoda" id="roda-radio-${pos.count}" type="radio" class="radioCheckPecas" value="${peca.id}" required ${radioRodaCheck} onclick="$('#collapse3').collapse('toggle');">
                                                    </div>
                                                </div>
                                            </c:if>
                                        </c:forEach>
                                        <!-- FIM DE PEÇAS -->
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- FIM DIV RODAS -->

                        <!-- DIV DAS LIXAS -->
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h4 class="panel-title">
                                    <a data-toggle="collapse" data-parent="#accordion" href="#collapse4" onclick="$('#mapLixa').click()">LIXA</a>
                                </h4>
                            </div>
                            <div id="collapse4" class="panel-collapse collapse">
                                <div class="panel-body" id="scroll">
                                    <div class="list-group">
                                        <c:forEach var="peca" items="${pecas}" varStatus="pos">
                                            <c:if test="${peca.tipo == 'LIXA'}">
                                                <div class="list-group-item col-md-12 pecahover">
                                                    <!-- PEÇAS -->
                                                    <div class="lixa-image col-md-2">
                                                        <a href="${peca.ref_imagem}" data-lightbox="image-${pos.count}" data-title="${peca.modelo}">
                                                            <img id="skateLixaImg" src="${peca.ref_imagem}" data-lightbox="image-${pos.count}" border="0" width="65" height="65"/>
                                                        </a>
                                                    </div>
                                                    <div class="shape-image col-md-1"></div>
                                                    <div class="lixa-descricao col-md-8" onclick="$('#lixa-radio-${pos.count}').click()">
                                                        <h4 class="list-group-item-heading">${peca.modelo}</h4>
                                                        <p class="list-group-item-text">${peca.descricao}</p>
                                                        <p class="list-group-item-text">R$ ${peca.valor_venda}</p>
                                                    </div>
                                                    <div class="lixa-check col-md-1" >
                                                        <c:set var="radioLixaCheck" value="" />
                                                        <c:if test="${!empty skateEdit && skateEdit.lixa.id == peca.id}">
                                                            <c:set var="radioLixaCheck" value="checked" />
                                                        </c:if>
                                                        <br/>
                                                        <input name="SktLixa" id="lixa-radio-${pos.count}" type="radio" class="radioCheckPecas" value="${peca.id}" required ${radioLixaCheck} onclick="$('#collapse4').collapse('toggle');">
                                                    </div>
                                                </div>
                                            </c:if>
                                        </c:forEach>
                                        <!-- FIM DE PEÇAS -->
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- FIM DIV LIXAS -->
                    </div>

                    <c:if test="${user.perfil ne 'COMUM' and (empty cpfClienteVenda or empty carrinho.skates) }">
                        <div class="row">
                            <div class="col-md-2"></div>
                            <div class="col-md-8">
                                <p>Identificamos que não há skates no carrinho, então para iniciar a venda, basta informar o <strong>CPF do cliente</strong></p>
                                <input type="text" id="cpfClienteVenda" name="cpfClienteVenda" required placeholder="CPF DO CLIENTE" oninput="vCPF(this)" onKeyPress="MascaraCPF(this);" maxlength="14" class="form-control"/>
                            </div>
                            <div class="col-md-6"></div>
                        </div>
                        <br/>
                    </c:if>

                    <div class="row">
                        <div class="col-md-2"></div>
                        <div class="col-md-4">
                            <button class="btn btn-primary btn-block" type="submit" id="btnEnviar" name="btnEnviar"><span class="glyphicon glyphicon-ok"></span> Salvar</button>
                        </div>
                        <div class="col-md-4">
                            <button class="btn btn-danger btn-block" type="button" onClick="$('.radioCheckPecas').prop('checked', '')" id="btnLimpar" name="btnLimpar"><span class="glyphicon glyphicon-remove"></span> Limpar</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
        <script src="js/lightbox.js"></script>
        <script src="js/jquery.rwdImageMaps.min.js"></script>
        <script>
                                $(document).ready(function (e) {
                                    $('img[usemap]').rwdImageMaps();
                                });
        </script>
    </body>
</html>
