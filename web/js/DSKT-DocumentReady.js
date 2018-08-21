function vCNPJ(cnpj) {
    cnpj.setCustomValidity(ValidarCNPJ(cnpj.value) ? '' : 'CNPJ inválido!');
}

function vCPF(cpf) {
    cpf.setCustomValidity(ValidarCPF(cpf.value) ? '' : 'CPF inválido!');
}

function vData(data) {
    data.setCustomValidity(ValidarData(data.value) ? '' : 'Data Inválida!');
}

//FUNÇÕES QUE ATIVAM APÓS A DOM ESTAR CARREGADA

//*******************DATEPICKER*******************//
$(document).ready(function () {
    $('.datepicker').datepicker({
        format: 'dd/mm/yyyy',
        startView: 2,
        language: 'pt-BR',
        autoclose: true,
        defaultViewDate: {year: 1977, month: 04, day: 25}
    });
//*******************DATEPICKER*******************//



//*******************AJAX*******************//
    $('#submit').click(function (event) {
        var username = $('#user').val();
        $.get('ActionServlet', {user: username}, function (responseText) {
            $('#welcometext').text(responseText);
        });
    });
//*******************AJAX*******************//

    function vCNPJ(cnpj) {
        cnpj.setCustomValidity(ValidarCNPJ(cnpj.value) ? '' : 'CNPJ inválido!');
    }

    function vCPF(cpf) {
        cpf.setCustomValidity(ValidarCPF(cpf.value) ? '' : 'CPF inválido!');
    }

    function vData(data) {
        data.setCustomValidity(ValidarData(data.value) ? '' : 'Data Inválida!');
    }

//FUNÇÕES QUE ATIVAM APÓS A DOM ESTAR CARREGADA

//*******************DATEPICKER*******************//
    $('.datepicker').datepicker({
        format: 'dd/mm/yyyy',
        startView: 2,
        language: 'pt-BR',
        autoclose: true,
        defaultViewDate: {year: 1977, month: 04, day: 25}
    });
//*******************DATEPICKER*******************//



//*******************AJAX*******************//
    $('#submit').click(function (event) {
        var username = $('#user').val();
        $.get('ActionServlet', {user: username}, function (responseText) {
            $('#welcometext').text(responseText);
        });
    });
//*******************AJAX*******************//

    $('.smartTable').DataTable({
        order: [[1, "asc"]],
        scrollY: "300px",
        deferRender: true,
        scrollCollapse: true,
        colReorder: {realtime: false},
        dom: "<'row'<'col-sm-12 pull-right'f>>" +
                "<'row'<'col-sm-12'tr>>" +
                "<'row'<'col-sm-3'i><'col-sm-3'l><'col-sm-3'B><'col-sm-3'p>>",
        /*
         B = botões de ação
         l = numero de registros por pagina
         f = busca
         r = "processin display element"
         t = "the table"
         i = sumario de exibição
         p = paginação
         */
        buttons: ['csvHtml5', 'excelHtml5', {extend: 'pdfHtml5', download: 'open'}, {extend: 'print', text: 'Imprimir'}],
        lengthMenu: [[10, 25, 50, -1], ['10', '25', '50', 'Todos']],
        responsive: {
            details: {
                display: $.fn.dataTable.Responsive.display.modal({
                    header: function (row) {
                        var data = row.data();
                        return 'Detalhes de ' + data[1];
                    }
                }),
                renderer: function (api, rowIdx, columns) {
                    var data = $.map(columns, function (col, i) {
                        return '<tr>' +
                                '<td>' + col.title + ':' + '</td> ' +
                                '<td>' + col.data + '</td>' +
                                '</tr>';
                    }).join('');
                    return $('<table class="table"/>').append(data);
                }
            }
        },
        language: {
            emptyTable: "Nenhum registro disponivel na tabela",
            info: "Exibindo _START_ a _END_ de _TOTAL_ registros",
            infoEmpt: "Nenhum registro encontrado",
            infoFiltered: "(filtrando de _MAX_ registros)",
            infoPostFix: "",
            thousands: ".",
            lengthMenu: "Exibir _MENU_ registros",
            loadingRecords: "Carregando...",
            processing: "Processando...",
            search: "Buscar:",
            zeroRecords: "Nenhum registro encontrado",
            paginate: {
                first: "Primeiro",
                last: "Ultimo",
                next: "Proximo",
                previous: "Anterior"
            }
        }
    });
});