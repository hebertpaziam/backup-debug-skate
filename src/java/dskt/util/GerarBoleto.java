package dskt.util;

import dskt.model.Venda;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.jrimum.bopepo.BancosSuportados;
import org.jrimum.bopepo.Boleto;
import org.jrimum.bopepo.campolivre.NotSupportedBancoException;
import org.jrimum.bopepo.campolivre.NotSupportedCampoLivreException;
import org.jrimum.domkee.comum.pessoa.endereco.CEP;
import org.jrimum.domkee.comum.pessoa.endereco.Endereco;
import org.jrimum.domkee.comum.pessoa.endereco.UnidadeFederativa;
import org.jrimum.domkee.financeiro.banco.febraban.Agencia;
import org.jrimum.domkee.financeiro.banco.febraban.Carteira;
import org.jrimum.domkee.financeiro.banco.febraban.Cedente;
import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.NumeroDaConta;
import org.jrimum.domkee.financeiro.banco.febraban.Sacado;
import org.jrimum.domkee.financeiro.banco.febraban.SacadorAvalista;
import org.jrimum.domkee.financeiro.banco.febraban.TipoDeTitulo;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo.EnumAceite;

public final class GerarBoleto {

    public static Boleto getBoleto(Venda venda) {
        try {
            /*
             * INFORMAÇÕES PERSONALIZADAS.
             */
            Calendar dataVencimentoCalendar = Calendar.getInstance();
            dataVencimentoCalendar.add(Calendar.DATE, +3);
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            String dataVencimento = df.format(dataVencimentoCalendar.getTime());

            Random num = new Random();
            Integer numeroDocumento = num.nextInt(Integer.MAX_VALUE);

            String nossoNumero = String.format("%011d", venda.getId());


            /*
             * GERANDO BOLETO.
             */
            Cedente cedente = new Cedente("DebugSkate Shop", "07.263.172/0001-05");

            Sacado sacado = new Sacado(venda.getCliente().getNome(), venda.getCliente().getCpf_cnpj());

            // Informando o endereço do sacado.
            Endereco enderecoSac = new Endereco();
            enderecoSac.setUF(UnidadeFederativa.valueOf(venda.getCliente().getUf()));
            enderecoSac.setBairro(venda.getCliente().getEndereco());
            sacado.addEndereco(enderecoSac);

            /*
             * INFORMANDO DADOS SOBRE O SACADOR AVALISTA.
             */
            SacadorAvalista sacadorAvalista = new SacadorAvalista("Cyborg Corporation", "32.441.263/0001-22");
            // Informando o endereço do sacador avalista.
            Endereco enderecoSacAval = new Endereco();
            enderecoSacAval.setUF(UnidadeFederativa.SP);
            enderecoSacAval.setLocalidade("Mogi das Cruzes");
            enderecoSacAval.setCep(new CEP("08780-911"));
            enderecoSacAval.setBairro("Centro Cívico");
            enderecoSacAval.setLogradouro("Av. Dr. Cândido Xavier de Almeida Souza");
            enderecoSacAval.setNumero("200");
            sacadorAvalista.addEndereco(enderecoSacAval);

            /*
             * INFORMANDO OS DADOS SOBRE O TÍTULO.
             */
            // Informando dados sobre a conta bancária do título.
            ContaBancaria contaBancaria = new ContaBancaria(BancosSuportados.BANCO_BRADESCO.create());
            contaBancaria.setNumeroDaConta(new NumeroDaConta(123456, "0"));
            contaBancaria.setCarteira(new Carteira(30));
            contaBancaria.setAgencia(new Agencia(1234, "1"));

            Titulo titulo = new Titulo(contaBancaria, sacado, cedente, sacadorAvalista);
            titulo.setNumeroDoDocumento(numeroDocumento.toString());
            titulo.setNossoNumero(nossoNumero);
            titulo.setDigitoDoNossoNumero("5");
            titulo.setValor(BigDecimal.valueOf(venda.getValorTotal()));
            titulo.setDataDoDocumento(new Date());
            titulo.setDataDoVencimento(dataVencimentoCalendar.getTime());
            titulo.setTipoDeDocumento(TipoDeTitulo.NF_NOTA_FISCAL);
            titulo.setAceite(EnumAceite.N);
            titulo.setDesconto(BigDecimal.ZERO);
            titulo.setDeducao(BigDecimal.ZERO);
            titulo.setMora(BigDecimal.ZERO);
            titulo.setAcrecimo(BigDecimal.ZERO);
            titulo.setValorCobrado(BigDecimal.ZERO);

            /*
             * INFORMANDO OS DADOS SOBRE O BOLETO.
             */
            Boleto boleto = new Boleto(titulo);

            boleto.setLocalPagamento("Pagável preferencialmente nas agências do Bradesco S/A");
            boleto.setInstrucaoAoSacado("Senhor(a) " + venda.getCliente().getNome() + ", este boleto será inválido após o dia " + dataVencimento);
            boleto.setInstrucao1("Senhor(a) Caixa, não receber este boleto após a data de vencimento");
            boleto.setInstrucao2("Considerar o proximo dia útil caso a data de vencimento for sabado, domingo ou feriado");
            boleto.setInstrucao3("Pagavel em qualquer agencia bancária");
            boleto.setInstrucao4("");
            boleto.setInstrucao5("");
            boleto.setInstrucao6("");
            boleto.setInstrucao7("");
            boleto.setInstrucao8("");

            return boleto;

        } catch (IllegalArgumentException | NotSupportedBancoException | NotSupportedCampoLivreException err) {
            System.out.println(err);
            return null;
        }

    }
}
