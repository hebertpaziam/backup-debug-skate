/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dskt.util;

import dskt.controller.ControleSolicitacao;
import dskt.model.ItemDeSolicitacao;
import dskt.model.SolicitacaoPecas;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.mail.*;

public class EnviaEmail {

    private List<String> listaDeEnvio;

    public void send(final SolicitacaoPecas solicitacao) {
        new Thread() {
            @Override
            public void run() {
                //GERA UMA LISTA COM OS EMAILS A SEREM DISPARADOS
                listaDeEnvio = new ArrayList<>();
                int cont = 0;

                while (cont < solicitacao.getItensDeSolicitacao()
                        .size()) {
                    listaDeEnvio.add(solicitacao.getItensDeSolicitacao().get(cont).getPeca().getFornecedor().getEmail());
                    cont++;
                }
                listaDeEnvio = new ArrayList(new HashSet(listaDeEnvio)); //RETIRA OS EMAILS REPETIDOS DA LISTA

                //MONTAGEM DOS EMAILS DE FORNECEDOR E DE CONTROLE DA LOJA
                String contato = new String();
                String msgCorpoEmail = new String(); //STRING QUE IRA MONTAR O EMAIL EM FORMATO HTML PARA O FORNECEDOR
                String msgCorpoEmailControle = new String(); //STRING QUE IRA MONTAR O EMAIL EM FORMATO HTML PARA O CONTROLE DA LOJA
                msgCorpoEmailControle = "AS SEGUINTES PEÇAS FORAM SOLICITADAS COM ÊXITO PARA OS FORNECEDORES\n \n"
                        + "<table border=\"1\" name=\"TabelaSolicitacao\">\n"
                        + "<thead>\n"
                        + "<tr><th colspan=\"9\">PEÇAS</th></tr>\n"
                        + "<th>ID</th>\n"
                        + "<th>TIPO</th>\n"
                        + "<th>MODELO</th>\n"
                        + "<th>COR</th>\n"
                        + "<th>TAMANHO</th>\n"
                        + "<th>FORNECEDOR</th>\n"
                        + "<th>ESTOQUE</th>\n"
                        + "<th>QTD SOLICITADA</th>\n"
                        + "</thead>";

                for (String lista : listaDeEnvio) {
                    msgCorpoEmail = "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"><title>Solicitação de Peças</title></head><body>\n"
                            + "Por favor enviar a(s) peça(s) abaixo.<p>"
                            + "<table border=\"1\" name=\"TabelaSolicitacao\">\n"
                            + "<thead>\n"
                            + "<tr><th colspan=\"9\">PEÇAS</th></tr>\n"
                            + "<th>ID</th>\n"
                            + "<th>TIPO</th>\n"
                            + "<th>MODELO</th>\n"
                            + "<th>COR</th>\n"
                            + "<th>TAMANHO</th>\n"
                            + "<th>QTD NECESSARIA</th>\n"
                            + "</thead>";

                    for (ItemDeSolicitacao item : solicitacao.getItensDeSolicitacao()) {
                        if (lista.equals(item.getPeca().getFornecedor().getEmail())) {
                            msgCorpoEmail = msgCorpoEmail
                                    + " <tbody>\n"
                                    + " <tr>\n"
                                    + " <td>" + item.getPeca().getId() + "</td>\n"
                                    + " <td>" + item.getPeca().getTipo() + "</td>\n"
                                    + " <td>" + item.getPeca().getModelo() + "</td>\n"
                                    + " <td>" + item.getPeca().getCor() + "</td>\n"
                                    + " <td>" + item.getPeca().getTamanho() + "</td>\n"
                                    + " <td>" + item.getQtdPecas() + "</td>\n"
                                    + " </tr>\n"
                                    + " </tbody>";

                            msgCorpoEmailControle = msgCorpoEmailControle
                                    + " <tbody>\n"
                                    + " <tr>\n"
                                    + " <td>" + item.getPeca().getId() + "</td>\n"
                                    + " <td>" + item.getPeca().getTipo() + "</td>\n"
                                    + " <td>" + item.getPeca().getModelo() + "</td>\n"
                                    + " <td>" + item.getPeca().getCor() + "</td>\n"
                                    + " <td>" + item.getPeca().getTamanho() + "</td>\n"
                                    + " <td>" + item.getPeca().getFornecedor().getNome() + "</td>\n"
                                    + " <td>" + item.getPeca().getQtd_estoque() + "</td>\n"
                                    + " <td>" + item.getQtdPecas() + "</td>\n"
                                    + " </tr>\n"
                                    + " </tbody>";

                            contato = item.getPeca().getFornecedor().getContato();  //STRING QUE IRÁ ARMAZENAR O NOME DO CONTATO QUE SERÁ ADCIONADO AO CORPO DO EMAIL
                        }
                    }
                    //ENVIA O EMAIL HTML PARA OS FORNECEDORES
                    try {
                        HtmlEmail email = new HtmlEmail();
                        email.setHostName("smtp.gmail.com");
                        email.setStartTLSRequired(true);
                        email.setStartTLSEnabled(true);
                        email.setSmtpPort(587);
                        email.setSslSmtpPort("587");
                        email.setAuthenticator(new DefaultAuthenticator("debugskateshop@gmail.com", "debugskate2015"));
                        email.setSSLOnConnect(true);
                        email.setSubject("Solicitação de Peças - Nº." + solicitacao.getIdSol());
                        email.setFrom("debugskateshop@gmail.com", "DebugSkateShop");
                        email.setHtmlMsg("Olá " + contato + "!<p>" + msgCorpoEmail + "</table><br>Att.<p><p>Equipe <strong>DebugSkateShop</strong><br>www.debugskateshop.com<br>(11)4140-6660<br>Alameda Mokurenji, 666<br>São Paulo - SP</body></hmtl>");
                        email.addTo(lista);
                        email.send();

                    } catch (EmailException ex) {
                        Logger.getLogger(ControleSolicitacao.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                //Email para a loja com todas as pecas solicitadas    
                try {
                    HtmlEmail email2 = new HtmlEmail();
                    email2.setHostName("smtp.gmail.com");
                    email2.setStartTLSRequired(true);
                    email2.setStartTLSEnabled(true);
                    email2.setSmtpPort(587);
                    email2.setSslSmtpPort("587");
                    email2.setAuthenticator(new DefaultAuthenticator("debugskateshop@gmail.com", "debugskate2015"));
                    email2.setSSLOnConnect(true);
                    email2.setSubject("Controle de Peças Solicitadas - Nº." + solicitacao.getIdSol());
                    email2.setFrom("debugskateshop@gmail.com", "DebugSkateShop");
                    email2.setHtmlMsg(msgCorpoEmailControle + "</table><br>Att.<p><p>Equipe <strong>DebugSkateShop</strong><br>www.debugskateshop.com<br>(11)4140-6660<br>Alameda Mokurenji, 666<br>São Paulo - SP<p><p><i>*E-mail gerado automaticamente, por favor não responda.</i></body></hmtl>");
                    email2.addTo("debugskateshop@gmail.com", "DebugSkateShop");
                    email2.send();

                } catch (EmailException ex) {
                    Logger.getLogger(ControleSolicitacao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.start();
    }
}
