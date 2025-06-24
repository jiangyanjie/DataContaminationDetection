/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * JFPesquisaFornecedores.java
 *
 * Created on 06/01/2009, 18:00:51
 */
package br.com.seeds;

import Classes.*;
import ClassesDAO.*;
import Excessoes.BancoException;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Guilherme
 */
public class ContasReceberContratos extends javax.swing.JFrame {

    String s, f;
    StringBuffer nome = new StringBuffer();
    StringBuffer nomes = new StringBuffer();
    int apagar = 0;
    String parametroPesquisa = "";
    String campo;
    String CodigoInstrutor = "";
    String dataSemFormatacao;

    /**
     * Creates new form JFPesquisaFornecedores
     */
    public ContasReceberContratos(int Codigo) throws BancoException, SQLException, ParseException {
        initComponents();
        jLErroAOFiltraContrato.setVisible(false);
        //---------txtLocalizar.setText(Codigo + "");
        txtLocalizar.requestFocus();
        txtValorPago.setDocument(new LimitadorMoeda());
        txtValorPago.setText("0000");
        txtDesconto.setDocument(new LimitadorMoeda());
        txtDesconto.setText("0000");
        txtDataPagamento.setDocument(new LimiteCampos.FixedLengthDocument(10));
        txtDataEmQueFoiFeitoPagamento.setDocument(new LimiteCampos.FixedLengthDocument(10));

        try {
            UsuarioDAO dao = new UsuarioDAO();
            AcessoDAO daos = new AcessoDAO();
            Acesso user = new Acesso();
            txtCodigoUsuarioLogado.setText("" + dao.gerarCodigoUsuarioUltimoLogado());

            apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
            nome.delete(0, apagar);
            nome.append(txtCodigoUsuarioLogado.getText());
            //JOptionPane.showMessageDialog(rootPane, nome);

            if (txtCodigoUsuarioLogado.getText().equals("0")) {
                do {
                    int remove = Integer.parseInt(nome + "");
                    apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
                    nome.delete(0, apagar);
                    nome.append(remove - 1);
                    user = daos.carregarAcessoPeloCodigo(nome);
                    txtCodigoUsuarioLogado.setText("" + user.getUsuario());
                } while (nome.equals("0"));

                txtCodigoUsuarioLogado.setBackground(Color.red);
                txtCodigoUsuarioLogado.setToolTipText("Não foi possível identificar o usuário *logado no sistema! "
                        + "Isso não prejudica outras funções executadas.\n"
                        + "*Que está identificado no sistema computacional.");
            } else {
                user = daos.carregarAcessoPeloCodigo(nome);
                txtCodigoUsuarioLogado.setText("" + user.getUsuario());
            }
            user = null;//Liberar objetos obsoletos logo após o uso atribuindo null.
            dao.desconectar();
            daos.desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void NowString() {
        Date now = new Date();
        DateFormat df = DateFormat.getDateInstance();
        DateFormat dg = DateFormat.getTimeInstance();
        s = df.format(now);
        f = dg.format(now);
    }

    public void valorPorExtenso(double vlr) {

        if (vlr == 0) //return("zero");
        {
            txtValorPago.setToolTipText("Zero");
        }

        long inteiro = (long) Math.abs(vlr); // parte inteira do valor
        double resto = vlr - inteiro;       // parte fracionária do valor

        String vlrS = String.valueOf(inteiro);
        if (vlrS.length() > 15) //return("Erro: valor superior a 999 trilhões.");
        {
            txtValorPago.setToolTipText("Erro: valor superior a 999 trilhões.");
        }

        String svalor = "", saux, vlrP;
        String centavos = String.valueOf((int) Math.round(resto * 100));

        String[] unidade = {"", "um", "dois", "três", "quatro", "cinco",
            "seis", "sete", "oito", "nove", "dez", "onze",
            "doze", "treze", "quatorze", "quinze", "dezesseis",
            "dezessete", "dezoito", "dezenove"};
        String[] centena = {"", "cento", "duzentos", "trezentos",
            "quatrocentos", "quinhentos", "seiscentos",
            "setecentos", "oitocentos", "novecentos"};
        String[] dezena = {"", "", "vinte", "trinta", "quarenta", "cinquenta",
            "sessenta", "setenta", "oitenta", "noventa"};
        String[] qualificaS = {"", "mil", "milhão", "bilhão", "trilhão"};
        String[] qualificaP = {"", "mil", "milhões", "bilhões", "trilhões"};

// definindo o extenso da parte inteira do valor
        int n, unid, dez, cent, tam, i = 0;
        boolean umReal = false, tem = false;
        while (!vlrS.equals("0")) {
            tam = vlrS.length();
// retira do valor a 1a. parte, 2a. parte, por exemplo, para 123456789:
// 1a. parte = 789 (centena)
// 2a. parte = 456 (mil)
// 3a. parte = 123 (milhões)
            if (tam > 3) {
                vlrP = vlrS.substring(tam - 3, tam);
                vlrS = vlrS.substring(0, tam - 3);
            } else { // última parte do valor
                vlrP = vlrS;
                vlrS = "0";
            }
            if (!vlrP.equals("000")) {
                saux = "";
                if (vlrP.equals("100")) {
                    saux = "cem";
                } else {
                    n = Integer.parseInt(vlrP, 10);  // para n = 371, tem-se:
                    cent = n / 100;                  // cent = 3 (centena trezentos)
                    dez = (n % 100) / 10;            // dez  = 7 (dezena setenta)
                    unid = (n % 100) % 10;           // unid = 1 (unidade um)
                    if (cent != 0) {
                        saux = centena[cent];
                    }
                    if ((n % 100) <= 19) {
                        if (saux.length() != 0) {
                            saux = saux + " e " + unidade[n % 100];
                        } else {
                            saux = unidade[n % 100];
                        }
                    } else {
                        if (saux.length() != 0) {
                            saux = saux + " e " + dezena[dez];
                        } else {
                            saux = dezena[dez];
                        }
                        if (unid != 0) {
                            if (saux.length() != 0) {
                                saux = saux + " e " + unidade[unid];
                            } else {
                                saux = unidade[unid];
                            }
                        }
                    }
                }
                if (vlrP.equals("1") || vlrP.equals("001")) {
                    if (i == 0) // 1a. parte do valor (um real)
                    {
                        umReal = true;
                    } else {
                        saux = saux + " " + qualificaS[i];
                    }
                } else if (i != 0) {
                    saux = saux + " " + qualificaP[i];
                }
                if (svalor.length() != 0) {
                    svalor = saux + ", " + svalor;
                } else {
                    svalor = saux;
                }
            }
            if (((i == 0) || (i == 1)) && svalor.length() != 0) {
                tem = true; // tem centena ou mil no valor
            }
            i = i + 1; // próximo qualificador: 1- mil, 2- milhão, 3- bilhão, ...
        }

        if (svalor.length() != 0) {
            if (umReal) {
                svalor = svalor + " real";
            } else if (tem) {
                svalor = svalor + " reais";
            } else {
                svalor = svalor + " de reais";
            }
        }

// definindo o extenso dos centavos do valor
        if (!centavos.equals("0")) { // valor com centavos
            if (svalor.length() != 0) // se não é valor somente com centavos
            {
                svalor = svalor + " e ";
            }
            if (centavos.equals("1")) {
                svalor = svalor + "um centavo";
            } else {
                n = Integer.parseInt(centavos, 10);
                if (n <= 19) {
                    svalor = svalor + unidade[n];
                } else {             // para n = 37, tem-se:
                    unid = n % 10;   // unid = 37 % 10 = 7 (unidade sete)
                    dez = n / 10;    // dez  = 37 / 10 = 3 (dezena trinta)
                    svalor = svalor + dezena[dez];
                    if (unid != 0) {
                        svalor = svalor + " e " + unidade[unid];
                    }
                }
                svalor = svalor + " centavos";
            }
        }

        //return(svalor);
        txtValorPago.setToolTipText(svalor);
    }

    public void pegaNomeInstrutor() {
        Professor funcionario = new Professor();
        try {
            ProfessorDAO dao = new ProfessorDAO(); // estou instanciando a conexão
            apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
            nome.delete(0, apagar);
            nome.append(CodigoInstrutor);
            funcionario = dao.carregarProfessorPeloCodigo(nome);
            if (funcionario.getNome().equals("nulo")) {
                txtResponsavel.setText("");
            } else {
                txtResponsavel.setText(funcionario.getNome());
                txtValorPago.requestFocus();
            }
            funcionario = null;//Liberar objetos obsoletos logo após o uso atribuindo null.
            dao.desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //faz a pesquisa, dado um inteiro de 1 a 7  
    public String pesquisarMes(String mes) {
        mes = mes.replace("/1/", "/01/");
        mes = mes.replace("/2/", "/02/");
        mes = mes.replace("/3/", "/03/");
        mes = mes.replace("/4/", "/04/");
        mes = mes.replace("/5/", "/05/");
        mes = mes.replace("/6/", "/06/");
        mes = mes.replace("/7/", "/07/");
        mes = mes.replace("/8/", "/08/");
        mes = mes.replace("/9/", "/09/");
        if (mes.contains("/01/")) {
            mes = "janeiro";
        }
        if (mes.contains("/02/")) {
            mes = "fevereiro";
        }

        if (mes.contains("/03/")) {
            mes = "março";
        }
        if (mes.contains("/04/")) {
            mes = "abril";
        }
        if (mes.contains("/05/")) {
            mes = "maio";
        }
        if (mes.contains("/06/")) {
            mes = "junho";
        }

        if (mes.contains("/07/")) {
            mes = "julho";
        }

        if (mes.contains("/08/")) {
            mes = "agosto";
        }

        if (mes.contains("/09/")) {
            mes = "setembro";
        }

        if (mes.contains("/10/")) {
            mes = "outubro";
        }

        if (mes.contains("/11/")) {
            mes = "novembro";
        }

        if (mes.contains("/12/")) {
            mes = "dezembro";
        }

        return mes;

    }

    public Date transformaData(String data) {
        SimpleDateFormat formatador = new SimpleDateFormat("yyyy");
        try {
            return formatador.parse(data);
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        rowSorterToStringConverter1 = new converter.RowSorterToStringConverter();
        buttonGroupImpressoras = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        masterTable = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtLocalizar = new javax.swing.JTextField();
        jLErroAOFiltraContrato = new javax.swing.JLabel();
        jBCancelar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtValorDaParcela = new javax.swing.JTextField();
        txtValorPago = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtParcela = new javax.swing.JTextField();
        txtDataEmQueFoiFeitoPagamento = new javax.swing.JTextField();
        txtCodigo = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtDataPagamento = new javax.swing.JTextField();
        txtCodigoUsuarioLogado = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jPanel6 = new javax.swing.JPanel();
        txtResponsavel = new javax.swing.JTextField();
        txtCPF = new javax.swing.JTextField();
        txtCodigoTurma = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        txtNomeDoCliente = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtTelefone = new javax.swing.JFormattedTextField();
        txtCelular = new javax.swing.JFormattedTextField();
        jLabel8 = new javax.swing.JLabel();
        txtCodigoCliente = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtDiaTurma = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtHI = new javax.swing.JFormattedTextField();
        jLabel14 = new javax.swing.JLabel();
        txtHF = new javax.swing.JFormattedTextField();
        jSeparator1 = new javax.swing.JSeparator();
        txtCodigoLocalidade = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtNomeDoCurso = new javax.swing.JTextField();
        jBAlunos = new javax.swing.JButton();
        jRadioBNaoImprimir = new javax.swing.JRadioButton();
        jRadioBJasperIreport = new javax.swing.JRadioButton();
        jRBImpressoraPadrao = new javax.swing.JRadioButton();
        jLabel16 = new javax.swing.JLabel();
        txtDesconto = new javax.swing.JTextField();
        jCBPago = new javax.swing.JCheckBox();
        jBSalvar = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        rowSorterToStringConverter1.setTable(masterTable);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Contas à receber - Contratos");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(249, 249, 249));

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));

        masterTable.setModel(getDadosTabelaClientePesquisa());
        masterTable.getTableHeader().setReorderingAllowed(false);
        masterTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                masterTableMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(masterTable);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setText("Localizar: ");

        txtLocalizar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${rowSorter}"), txtLocalizar, org.jdesktop.beansbinding.BeanProperty.create("text"), "");
        binding.setConverter(rowSorterToStringConverter1);
        bindingGroup.addBinding(binding);

        txtLocalizar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtLocalizarKeyPressed(evt);
            }
        });

        jLErroAOFiltraContrato.setForeground(new java.awt.Color(204, 51, 0));
        jLErroAOFiltraContrato.setText("* Todos as parcelas foram carregadas.");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(txtLocalizar, javax.swing.GroupLayout.PREFERRED_SIZE, 527, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLErroAOFiltraContrato, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtLocalizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLErroAOFiltraContrato))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8))
        );

        jBCancelar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jBCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/cancelar.png"))); // NOI18N
        jBCancelar.setText("Sair");
        jBCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCancelarActionPerformed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(249, 249, 249));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setText("Valor da parcela: ");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel12.setText("Valor Pago: ");

        txtValorDaParcela.setEditable(false);
        txtValorDaParcela.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtValorPago.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtValorPago.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 0)));
        txtValorPago.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtValorPagoFocusLost(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jLabel6.setText("Pago em: ");

        txtParcela.setEditable(false);
        txtParcela.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtDataEmQueFoiFeitoPagamento.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtDataEmQueFoiFeitoPagamento.setToolTipText("yyyy-MM-dd");
        txtDataEmQueFoiFeitoPagamento.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDataEmQueFoiFeitoPagamentoFocusLost(evt);
            }
        });
        txtDataEmQueFoiFeitoPagamento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDataEmQueFoiFeitoPagamentoKeyTyped(evt);
            }
        });

        txtCodigo.setEditable(false);
        txtCodigo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel7.setText("Vencimento:");
        jLabel7.setToolTipText("Data do Pagamento/Vencimento");

        jLabel5.setText("Parcela nº. ");

        jLabel2.setText("Código:");

        txtDataPagamento.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtDataPagamento.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDataPagamentoFocusLost(evt);
            }
        });

        txtCodigoUsuarioLogado.setEditable(false);
        txtCodigoUsuarioLogado.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtCodigoUsuarioLogado.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodigoUsuarioLogado.setToolTipText("Código do Usuário do sistema.");

        jLabel17.setText("Usuário:");

        jCheckBox1.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox1.setText("Editar");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtParcela, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDataEmQueFoiFeitoPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDataPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCodigoUsuarioLogado, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtParcela, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtDataEmQueFoiFeitoPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(txtDataPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCodigoUsuarioLogado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(jCheckBox1))
                .addContainerGap())
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        txtResponsavel.setEditable(false);
        txtResponsavel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtCPF.setEditable(false);
        txtCPF.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtCodigoTurma.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtCodigoTurma.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodigoTurma.setText("0");
        txtCodigoTurma.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodigoTurmaFocusLost(evt);
            }
        });
        txtCodigoTurma.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoTurmaKeyPressed(evt);
            }
        });

        jLabel3.setText("Cliente: ");

        jLabel46.setText("Telefone:");

        txtNomeDoCliente.setEditable(false);
        txtNomeDoCliente.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtNomeDoCliente.setToolTipText("Background Vermelho: Aluno Inativo.");

        jLabel4.setText("Turma: ");

        txtTelefone.setEditable(false);
        try {
            txtTelefone.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##) ####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtTelefone.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtCelular.setEditable(false);
        try {
            txtCelular.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##) ####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtCelular.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel8.setText("Celular:");

        txtCodigoCliente.setEditable(false);
        txtCodigoCliente.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel10.setText("CPF:");

        jLabel11.setText("Dia:");

        txtDiaTurma.setEditable(false);
        txtDiaTurma.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel13.setText("Horário: ");
        jLabel13.setToolTipText("Hora do início.");

        txtHI.setEditable(false);
        try {
            txtHI.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtHI.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("às");

        txtHF.setEditable(false);
        try {
            txtHF.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtHF.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtCodigoLocalidade.setEditable(false);
        txtCodigoLocalidade.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel15.setText("/");

        txtNomeDoCurso.setEditable(false);
        txtNomeDoCurso.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jBAlunos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/Cliente.png"))); // NOI18N
        jBAlunos.setToolTipText("Ativa o aluno selecionado.");
        jBAlunos.setBorderPainted(false);
        jBAlunos.setContentAreaFilled(false);
        jBAlunos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBAlunos.setEnabled(false);
        jBAlunos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAlunosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtCodigoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCodigoLocalidade)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNomeDoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 395, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel10)
                        .addGap(18, 18, 18)
                        .addComponent(txtCPF, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtCodigoTurma, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNomeDoCurso, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtResponsavel, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDiaTurma)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtHI, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtHF, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtCelular, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel46)
                        .addGap(18, 18, 18)
                        .addComponent(txtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jBAlunos)))
                .addContainerGap())
            .addComponent(jSeparator1)
        );

        jPanel6Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel3, jLabel4, jLabel8});

        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtNomeDoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCPF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCodigoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(txtCodigoLocalidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCelular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel46)
                    .addComponent(txtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBAlunos))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtCodigoTurma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtResponsavel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(txtDiaTurma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(txtHI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(txtHF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNomeDoCurso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel6Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel3, jLabel4, jLabel8});

        jRadioBNaoImprimir.setBackground(new java.awt.Color(249, 249, 249));
        buttonGroupImpressoras.add(jRadioBNaoImprimir);
        jRadioBNaoImprimir.setText("Não imprimir");

        jRadioBJasperIreport.setBackground(new java.awt.Color(249, 249, 249));
        buttonGroupImpressoras.add(jRadioBJasperIreport);
        jRadioBJasperIreport.setText("Jasper");
        jRadioBJasperIreport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioBJasperIreportActionPerformed(evt);
            }
        });

        jRBImpressoraPadrao.setBackground(new java.awt.Color(249, 249, 249));
        buttonGroupImpressoras.add(jRBImpressoraPadrao);
        jRBImpressoraPadrao.setSelected(true);
        jRBImpressoraPadrao.setText("Impressora padrão");

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel16.setText("Desconto :");

        txtDesconto.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtDesconto.setToolTipText("Valor informativo de desconto a ser aplicado na próxima parcela subsequente.");

        jCBPago.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jCBPago.setText("Pago");
        jCBPago.setToolTipText("Selecionado indica que a parcela está pago independente do valor pago informado.");
        jCBPago.setContentAreaFilled(false);
        jCBPago.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtValorDaParcela, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDesconto, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtValorPago, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCBPago, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jRadioBJasperIreport, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRBImpressoraPadrao)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioBNaoImprimir)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtValorDaParcela, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(jRadioBJasperIreport)
                    .addComponent(jRadioBNaoImprimir)
                    .addComponent(jRBImpressoraPadrao)
                    .addComponent(jCBPago)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDesconto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(txtValorPago, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jRadioBJasperIreport, jRadioBNaoImprimir});

        jBSalvar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jBSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/entrar.png"))); // NOI18N
        jBSalvar.setText("Salvar");
        jBSalvar.setToolTipText("");
        jBSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSalvarActionPerformed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/gnome-reboot (2).png"))); // NOI18N
        jButton1.setToolTipText("Atualizar interface.");
        jButton1.setBorderPainted(false);
        jButton1.setContentAreaFilled(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/view-list-details.png"))); // NOI18N
        jButton2.setText("Detalhes");
        jButton2.setToolTipText("Buscar outro contrato.");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/view-refresh.png"))); // NOI18N
        jButton3.setText("Atualizar");
        jButton3.setToolTipText("Atualiza os dados - SEM ENTRADA NO CAIXA - SEM RELATÓRIO");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/relatorioGerar.png"))); // NOI18N
        jButton4.setText("Gerar");
        jButton4.setToolTipText("Apenas gera o relatório do registro selecionado.");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jBSalvar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jBCancelar, jBSalvar, jButton2, jButton3, jButton4});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jBSalvar)
                            .addComponent(jBCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3)
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jBCancelar, jBSalvar, jButton2, jButton3, jButton4});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void masterTableMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_masterTableMouseReleased
    txtNomeDoCliente.setBackground(Color.lightGray);
    try {
        ContasRDAO daos = new ContasRDAO(); // estou instanciando a conexão
        ContasR cliente = new ContasR();
        ClientesDAO dao = new ClientesDAO(); // estou instanciando a conexão
        Cliente aluno = new Cliente();
        TurmasDAO daoss = new TurmasDAO(); // estou instanciando a conexão
        Turmas turmas = new Turmas();
        ProfessorDAO daosss = new ProfessorDAO(); // estou instanciando a conexão
        Professor professor = new Professor();
        ContratoDAO daoc = new ContratoDAO(); // estou instanciando a conexão
        Contrato contract = new Contrato();

        int linha = masterTable.getSelectedRow();
        //codigoFornecedor = masterTable.getValueAt(linha, 8).hashCode();
        txtCodigo.setText(masterTable.getValueAt(linha, 8).hashCode() + "");
        apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
        nome.delete(0, apagar);
        nome.append(txtCodigo.getText());
        cliente = daos.carregarContasContratoPeloCodigo(nome);
        txtCodigoCliente.setText("" + cliente.getCodigo_cliente());
        txtCodigoLocalidade.setText("" + cliente.getLocalidade());
        txtCodigoTurma.setText("" + cliente.getCodigo_turma());
        txtParcela.setText("" + cliente.getParcela());
        txtDataEmQueFoiFeitoPagamento.setText("" + cliente.getData_emissao());
        txtDataEmQueFoiFeitoPagamento.setText(txtDataEmQueFoiFeitoPagamento.getText().replace("-", "/"));
        txtDataPagamento.setText("" + cliente.getData_pagamento());
        txtDataPagamento.setText(txtDataPagamento.getText().replace("/1/", "/01/"));
        if (cliente.getTotal() == 0 || cliente.getTotal() <= 0.0) {
            jCBPago.setSelected(false);
        } else {
            jCBPago.setSelected(true);
        }


        String Desconto = new DecimalFormat("0.00").format((cliente.getDesconto()));
        Desconto = Desconto.replace(".", "");
        Desconto = Desconto.replace(",", "");
        txtDesconto.setText(Desconto);


        String valorParcela = "" + cliente.getValor();
        //valorParcela = valorParcela.replace(".", "0");
        valorParcela = valorParcela.replace(",", ".");
        txtValorDaParcela.setText(valorParcela);

        apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
        nome.delete(0, apagar);
        nome.append(txtCodigoCliente.getText());
        apagar = nomes.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
        nomes.delete(0, apagar);
        nomes.append(txtCodigoLocalidade.getText());
        aluno = dao.carregarClientePeloCodigo(nome, nomes);
        apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
        nome.delete(0, apagar);
        nome.append(txtCodigoTurma.getText());
        turmas = daoss.carregarTurmaPeloCodigo(nome);

        if (!txtCodigoCliente.getText().equals("")) {
            txtNomeDoCliente.setText(aluno.getNome());
            txtCodigoLocalidade.setText(aluno.getLocalidade() + "");
            txtCPF.setText(aluno.getCpf());
            txtTelefone.setText(aluno.getTelefone());
            txtCelular.setText(aluno.getCelular());
            txtCodigoLocalidade.setText("" + aluno.getLocalidade());
        }

        if (txtNomeDoCliente.getText().equals("nulo")) {
            int codigoContrato = masterTable.getValueAt(linha, 1).hashCode();
            contract = daoc.carregarContratoPeloCodigo("" + codigoContrato);
            txtCodigoCliente.setText("" + contract.getCodigo_aluno());
            txtCodigoLocalidade.setText("" + contract.getLocalidade());

            apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
            nome.delete(0, apagar);
            nome.append(txtCodigoCliente.getText());
            apagar = nomes.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
            nomes.delete(0, apagar);
            nomes.append(txtCodigoLocalidade.getText());
            aluno = dao.carregarClientePeloCodigo(nome, nomes);
            txtNomeDoCliente.setText(aluno.getNome());
            txtCodigoLocalidade.setText(aluno.getLocalidade() + "");
            if (aluno.getLocalidade() == 0) {
                txtCodigoLocalidade.setText("" + masterTable.getValueAt(linha, 9).hashCode());
                txtCodigoLocalidade.setEditable(true);
            }
            txtCPF.setText(aluno.getCpf());
            txtTelefone.setText(aluno.getTelefone());
            txtCelular.setText(aluno.getCelular());


            if (!txtNomeDoCliente.getText().equals("nulo")) {
                cliente.setCodigo_cliente(Integer.parseInt(txtCodigoCliente.getText()));
                cliente.setLocalidade(Integer.parseInt(txtCodigoLocalidade.getText()));
                cliente.setCodigo_contrato(codigoContrato);
                daos.atualizaDadosCliente(cliente);
            } else {

                txtNomeDoCliente.setBackground(Color.red);
                jBAlunos.setEnabled(true);

                apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
                nome.delete(0, apagar);
                nome.append(txtCodigoCliente.getText());
                apagar = nomes.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
                nomes.delete(0, apagar);
                nomes.append(txtCodigoLocalidade.getText());
                aluno = dao.carregarClienteInativoPeloCodigo(nome, nomes);
                txtNomeDoCliente.setText(aluno.getNome());
                txtCodigoLocalidade.setText(aluno.getLocalidade() + "");
                if (aluno.getLocalidade() == 0) {
                    txtCodigoLocalidade.setText("" + masterTable.getValueAt(linha, 9).hashCode());
                    txtCodigoLocalidade.setEditable(true);
                }
                txtCPF.setText(aluno.getCpf());
                txtTelefone.setText(aluno.getTelefone());
                txtCelular.setText(aluno.getCelular());

                if (!txtNomeDoCliente.getText().equals("nulo")) {
                    cliente.setCodigo_cliente(Integer.parseInt(txtCodigoCliente.getText()));
                    cliente.setLocalidade(Integer.parseInt(txtCodigoLocalidade.getText()));
                    cliente.setCodigo_contrato(codigoContrato);
                    daos.atualizaDadosCliente(cliente);
                }

            }
        }


        //txtCodigoTurma.setText("" + cliente.getCodigo_turma());
        if (!txtCodigoTurma.getText().equals("")) {
            txtDiaTurma.setText("" + turmas.getDia());
            txtHI.setText("" + turmas.getHi());
            txtHF.setText("" + turmas.getHf());
            txtNomeDoCurso.setText("" + turmas.getCurso());
            //String Responsavel = "" + turmas.getFuncionario();

            apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
            nome.delete(0, apagar);
            nome.append(turmas.getFuncionario());
            professor = daosss.carregarProfessorPeloCodigo(nome);
            txtResponsavel.setText(professor.getNome());
        }
        cliente = null;//Liberar objetos obsoletos logo após o uso atribuindo null.
        aluno = null;
        professor = null;
        turmas = null;
        dao.desconectar();
        daos.desconectar();
        daoss.desconectar();
        daosss.desconectar();
        daoc.desconectar();
    } catch (Exception b) {
        JOptionPane.showMessageDialog(null, b);
    }
    if (txtDataEmQueFoiFeitoPagamento.getText().equals("0000/00/00")) {
        txtDataEmQueFoiFeitoPagamento.setBackground(Color.red);
    } else {
        txtDataEmQueFoiFeitoPagamento.setBackground(Color.white);
    }
}//GEN-LAST:event_masterTableMouseReleased

private void jBCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCancelarActionPerformed
    this.dispose();
}//GEN-LAST:event_jBCancelarActionPerformed

private void txtLocalizarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLocalizarKeyPressed
    if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
        ContasReceberContratos.this.dispose();
    }
}//GEN-LAST:event_txtLocalizarKeyPressed

    private void jRadioBJasperIreportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioBJasperIreportActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioBJasperIreportActionPerformed

    private void jBSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalvarActionPerformed
        if (txtValorPago.getText().equals("0,00")) {
            int selection = JOptionPane.showConfirmDialog(this,
                    "Deseja emitir recibo com valor pago 0,00?\n",
                    "", JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if (selection == JOptionPane.OK_OPTION) {
                txtCodigo.setText(txtCodigo.getText().trim());
                apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
                nome.delete(0, apagar);
                nome.append(txtCodigo.getText());
                try {
                    ContasRDAO dao = new ContasRDAO(); // estou instanciando a conexão
                    ContasR cliente = new ContasR();
                    cliente = dao.carregarContasContratoPeloCodigo(nome);
                    if (cliente.getData_emissao().equals("nulo")) {
                        JOptionPane.showMessageDialog(this,
                                "O cliente informado não consta no banco!");
                    } else {
                        if (txtValorPago.getText().equals("")) {
                            JOptionPane.showMessageDialog(this,
                                    "Campo Valor pago tem que ser informado!",
                                    "Atenção!", JOptionPane.INFORMATION_MESSAGE);
                            txtValorPago.requestFocus();
                        } else {

                            String Valor = txtValorPago.getText();
                            Valor = Valor.replace(".", "");
                            Valor = Valor.replace(",", ".");
                            cliente.setValor_pago(Double.parseDouble(Valor));
                            String Desconto = txtDesconto.getText();
                            Desconto = Desconto.replace(".", "");
                            Desconto = Desconto.replace(",", ".");
                            cliente.setDesconto(Double.parseDouble(Desconto));
                            cliente.setData_pagamento(txtDataPagamento.getText().trim());
                            cliente.setData_emissao(txtDataEmQueFoiFeitoPagamento.getText().trim());
                            if (jCBPago.isSelected() == true) {
                                cliente.setTotal(1.00);
                            } else {
                                cliente.setTotal(0.00);
                            }
                            dao.atualizaDados(cliente);
                            JOptionPane.showMessageDialog(null, "Efetuado com sucesso!");

                            //JOptionPane.showMessageDialog(this, "Sucesso!1");
                            EntradaCaixa SetandoEntrada = new EntradaCaixa();
                            CaixaDAO daossss;
                            daossss = new CaixaDAO();

                            NowString();
                            int CodigoAbertura = 0;

                            try {
                                ResultSet Verificando = daossss.VerificaAbertura(s, f);
                                while (Verificando.next()) {
                                    CodigoAbertura = Verificando.getInt("Codigo");
                                }
                                Verificando.close();
                            } catch (SQLException ex) {
                                Logger.getLogger(CaixaEntradaVenda.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            //Caixa daosssss;
                            //daosssss = new Caixa();
                            SetandoEntrada.setCodigoProduto(Integer.parseInt(txtCodigo.getText()));
                            //SetandoEntrada.setSimples(false);
                            SetandoEntrada.setQuantidade(Integer.parseInt("1"));
                            SetandoEntrada.setValor(Float.parseFloat(Valor));
                            SetandoEntrada.setData(s);
                            SetandoEntrada.setHora(f);
                            SetandoEntrada.setOperacao("Mensalidade");
                            SetandoEntrada.setCodigoAbertura(CodigoAbertura);

                            try {
                                daossss.InserindoEntradaContrato(SetandoEntrada);

                            } catch (BancoException ex) {
                                Logger.getLogger(CaixaEntradaVenda.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            //IMPRESSÃO 
                            if (jRadioBJasperIreport.isSelected()) {

                                try {
                                    Connection con = new Conexao().getConnection();
                                    HashMap parametros = new HashMap();
                                    parametros.put("NOME", txtNomeDoCliente.getText().trim());
                                    parametros.put("DINHEIRO", txtValorPago.getText());
                                    parametros.put("DINHEIROEX", txtValorPago.getToolTipText());
                                    String mes = (pesquisarMes(txtDataPagamento.getText()));
                                    parametros.put("MES", mes);
                                    parametros.put("TURMA", txtNomeDoCurso.getText().trim());
                                    parametros.put("DIA", txtDiaTurma.getText().trim());
                                    parametros.put("HI", txtHI.getText().trim());
                                    parametros.put("HF", txtHF.getText().trim());
                                    parametros.put("PROFESSOR", txtResponsavel.getText().trim());
                                    String ano;
                                    ano = txtDataPagamento.getText();
                                    ano = ano.replace("/1/", "/01/");
                                    ano = ano.substring(6);
                                    parametros.put("ANO", ano);
                                    if (jCheckBox1.isSelected() == true) {
                                        parametros.put("DATA", JOptionPane.showInputDialog(this, "Informe a data.\nNão precisa seguir nenhuma formatação"));
                                    } else {
                                        parametros.put("DATA", txtDataEmQueFoiFeitoPagamento.getText());
                                    }
                                    // JasperPrint jp = JasperFillManager.fillReport("C:/Users/Guilherme/Documents/NetBeansProjects/shiftsoftlight/src/relatorios/Alunos.jasper", parametros, con);
                                    //JasperPrint jp = JasperFillManager.fillReport("C:/Program Files/shiftsoftlight/relatorios/Alunos.jasper", parametros, con);
                                    JasperPrint jp = JasperFillManager.fillReport("./relatorios/contratosImpresorra.jasper", parametros, con);
                                    JasperViewer jrv = new JasperViewer(jp, false);
                                    jrv.setVisible(true);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            //IMPRESSÃO FIM

                            if (jRBImpressoraPadrao.isSelected()) {
                                if (jCheckBox1.isSelected() == true) {
                                    dataSemFormatacao = JOptionPane.showInputDialog(this, "Informe a data sem a necessidade de formatação predifinida.\n"
                                            + "Use a formatação que desejar.");
                                }
                                String caminho = "../seeds-java/";
                                File file = new File(caminho + File.separator + "COMPROVANTE DE PAGAMENTO - SEEDS.txt");
                                if (!file.exists()) {
                                    System.out.println("arquivo não existe");
                                    System.out.println("criando arquivo File.txt em..." + caminho);
                                    try {
                                        //Aqui é o que falta  
                                        file.createNewFile();
                                    } catch (IOException ex) {
                                        Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    System.out.println("arquivo COMPROVANTE DE PAGAMENTO - SEEDS.txt, criado em" + "caminho");
                                }
                                String mes = (pesquisarMes(txtDataPagamento.getText()));
                                String ano;
                                ano = txtDataPagamento.getText();
                                ano = ano.replace("/1/", "/01/");
                                ano = ano.substring(6);
                                String Final = "";
                                Final = "_____________________________________________________________________________________";
                                Final = Final + "\n Associação Filantrópica N.de Amparo Social";
                                Final = Final + "\n Utilidade Pública Municipal Lei 3.376 de           RECIBO/MENSALIDADE";
                                Final = Final + "\n 22/12/2004 - CNPJ: 07.003.661/0001";
                                Final = Final + "\n_____________________________________________________________________________________";
                                Final = Final + "\n   Recebemos de " + txtNomeDoCliente.getText() + " a quantia de R$ " + txtValorPago.getText() + " ( " + txtValorPago.getToolTipText() + " ), ";
                                Final = Final + "\n   Referente a " + mes + " " + ano + " Turma/Dia:" + txtNomeDoCurso.getText() + " - " + txtDiaTurma.getText() + "";
                                Final = Final + "\n   Horário: " + txtHI.getText() + " às " + txtHF.getText() + " Prof.: " + txtResponsavel.getText() + "";
                                Final = Final + "\n   Parceria/Local:________________________________";
                                Final = Final + "\n";
                                Final = Final + "\n 		Por ser verdade, firmo o presente termo.";
                                Final = Final + "\n";
                                Final = Final + "\n 		_____________________________________________________";
                                if (jCheckBox1.isSelected() == true) {
                                    Final = Final + "\n 			  Montes Claros - " + dataSemFormatacao + "";
                                } else {
                                    Final = Final + "\n 			  Montes Claros - " + txtDataEmQueFoiFeitoPagamento.getText() + "";
                                }
                                Final = Final + "\n_____________________________________________________________________________________";
                                Final = Final + "\n Rua Padre Augusto, 335 - 2º Andar - Centro - Montes Claros / MG Fone: (38) 3221-6288";
                                Final = Final + "\n Rua Pires e Albuquerque, Nº. 282 - Centro - Montes Claros / MG Fone: (38) 3221-0828";
                                Final = Final + "\n_____________________________________________________________________________________";
                                Final = Final + "\n                                            -- 1ª Via --";
                                Final = Final + "\n";
                                Final = Final + "\n";
                                Final = Final + "\n";
                                Final = Final + "\n";
                                Final = Final + "\n";
                                Final = Final + "\n";
                                Final = Final + "\n";
                                Final = Final + "\n_____________________________________________________________________________________";
                                Final = Final + "\n Associação Filantrópica N.de Amparo Social";
                                Final = Final + "\n Utilidade Pública Municipal Lei 3.376 de           RECIBO/MENSALIDADE";
                                Final = Final + "\n 22/12/2004 - CNPJ: 07.003.661/0001";
                                Final = Final + "\n_____________________________________________________________________________________";
                                Final = Final + "\n   Recebemos de " + txtNomeDoCliente.getText() + " a quantia de R$ " + txtValorPago.getText() + " ( " + txtValorPago.getToolTipText() + " ), ";
                                Final = Final + "\n   Referente a " + mes + " " + ano + " Turma/Dia:" + txtNomeDoCurso.getText() + " - " + txtDiaTurma.getText() + "";
                                Final = Final + "\n   Horário: " + txtHI.getText() + " às " + txtHF.getText() + " Prof.: " + txtResponsavel.getText() + "";
                                Final = Final + "\n   Parceria/Local:________________________________";
                                Final = Final + "\n";
                                Final = Final + "\n 		Por ser verdade, firmo o presente termo.";
                                Final = Final + "\n";
                                Final = Final + "\n 		_____________________________________________________";
                                if (jCheckBox1.isSelected() == true) {
                                    Final = Final + "\n 			  Montes Claros - " + dataSemFormatacao + "";
                                } else {
                                    Final = Final + "\n 			  Montes Claros - " + txtDataEmQueFoiFeitoPagamento.getText() + "";
                                }
                                Final = Final + "\n_____________________________________________________________________________________";
                                Final = Final + "\n Rua Padre Augusto, 335 - 2º Andar - Centro - Montes Claros / MG Fone: (38) 3221-6288";
                                Final = Final + "\n Rua Pires e Albuquerque, Nº. 282 - Centro - Montes Claros / MG Fone: (38) 3221-0828";
                                Final = Final + "\n_____________________________________________________________________________________";

                                BufferedWriter out = null;
                                try {
                                    out = new BufferedWriter(new FileWriter("COMPROVANTE DE PAGAMENTO - SEEDS.txt"));
                                } catch (IOException ex) {
                                    Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                out.write(Final); // "" = quebra de linha no Windows
                                //System.out.println("Acabei de escrever no arquivo");
                                out.close();

                                File Emails = new File("COMPROVANTE DE PAGAMENTO - SEEDS.txt");
                                try {
                                    Desktop.getDesktop().print(Emails);
                                } catch (IOException ex) {
                                    Logger.getLogger(ContasReceberContratos.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }

                            this.dispose();
                            ContasReceberContratos Entrada;
                            try {
                                Entrada = new ContasReceberContratos(0);
                                Entrada.setVisible(true);
                            } catch (ParseException ex) {
                                Logger.getLogger(ContasReceberContratos.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            txtValorPago.setText("00");
                            masterTable.setVisible(false);
                            getDadosTabelaClientePesquisa();
                            masterTable.setVisible(true);

                        }
                    }
                    cliente = null;//Liberar objetos obsoletos logo após o uso atribuindo null.
                    dao.desconectar();
                } catch (Exception e) {
                }
            }
        } else {
            txtCodigo.setText(txtCodigo.getText().trim());
            apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
            nome.delete(0, apagar);
            nome.append(txtCodigo.getText());
            try {
                ContasRDAO dao = new ContasRDAO(); // estou instanciando a conexão
                ContasR cliente = new ContasR();
                cliente = dao.carregarContasContratoPeloCodigo(nome);
                if (cliente.getData_emissao().equals("nulo")) {
                    JOptionPane.showMessageDialog(this,
                            "O cliente informado não consta no banco!");
                } else {
                    if (txtValorPago.getText().equals("")) {
                        JOptionPane.showMessageDialog(this,
                                "Campo Valor pago tem que ser informado!",
                                "Atenção!", JOptionPane.INFORMATION_MESSAGE);
                        txtValorPago.requestFocus();
                    } else {

                        String Valor = txtValorPago.getText();
                        Valor = Valor.replace(".", "");
                        Valor = Valor.replace(",", ".");
                        cliente.setValor_pago(Double.parseDouble(Valor));
                        String Desconto = txtDesconto.getText();
                        Desconto = Desconto.replace(".", "");
                        Desconto = Desconto.replace(",", ".");
                        cliente.setDesconto(Double.parseDouble(Desconto));
                        cliente.setData_pagamento(txtDataPagamento.getText().trim());
                        cliente.setData_emissao(txtDataEmQueFoiFeitoPagamento.getText().trim());
                        dao.atualizaDados(cliente);
                        JOptionPane.showMessageDialog(null, "Efetuado com sucesso!");

                        //JOptionPane.showMessageDialog(this, "Sucesso!1");
                        EntradaCaixa SetandoEntrada = new EntradaCaixa();
                        CaixaDAO daossss;
                        daossss = new CaixaDAO();

                        NowString();
                        int CodigoAbertura = 0;

                        try {
                            ResultSet Verificando = daossss.VerificaAbertura(s, f);
                            while (Verificando.next()) {
                                CodigoAbertura = Verificando.getInt("Codigo");
                            }
                            Verificando.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(CaixaEntradaVenda.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        //Caixa daosssss;
                        //daosssss = new Caixa();
                        SetandoEntrada.setCodigoProduto(Integer.parseInt(txtCodigo.getText()));
                        //SetandoEntrada.setSimples(false);
                        SetandoEntrada.setQuantidade(Integer.parseInt("1"));
                        SetandoEntrada.setValor(Float.parseFloat(Valor));
                        SetandoEntrada.setData(s);
                        SetandoEntrada.setHora(f);
                        SetandoEntrada.setOperacao("Mensalidade");
                        SetandoEntrada.setCodigoAbertura(CodigoAbertura);

                        try {
                            daossss.InserindoEntradaContrato(SetandoEntrada);

                        } catch (BancoException ex) {
                            Logger.getLogger(CaixaEntradaVenda.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        //IMPRESSÃO 
                        if (jRadioBJasperIreport.isSelected()) {

                            try {
                                Connection con = new Conexao().getConnection();
                                HashMap parametros = new HashMap();
                                parametros.put("NOME", txtNomeDoCliente.getText().trim());
                                parametros.put("DINHEIRO", txtValorPago.getText());
                                parametros.put("DINHEIROEX", txtValorPago.getToolTipText());
                                String mes = (pesquisarMes(txtDataPagamento.getText()));
                                parametros.put("MES", mes);
                                parametros.put("TURMA", txtNomeDoCurso.getText().trim());
                                parametros.put("DIA", txtDiaTurma.getText().trim());
                                parametros.put("HI", txtHI.getText().trim());
                                parametros.put("HF", txtHF.getText().trim());
                                parametros.put("PROFESSOR", txtResponsavel.getText().trim());
                                String ano;
                                ano = txtDataPagamento.getText();
                                ano = ano.replace("/1/", "/01/");
                                ano = ano.substring(6);
                                parametros.put("ANO", ano);
                                if (jCheckBox1.isSelected() == true) {
                                    parametros.put("DATA", JOptionPane.showInputDialog(this, "Informe a data.\nNão precisa seguir nenhuma formatação"));
                                } else {
                                    parametros.put("DATA", txtDataEmQueFoiFeitoPagamento.getText());
                                }
                                // JasperPrint jp = JasperFillManager.fillReport("C:/Users/Guilherme/Documents/NetBeansProjects/shiftsoftlight/src/relatorios/Alunos.jasper", parametros, con);
                                //JasperPrint jp = JasperFillManager.fillReport("C:/Program Files/shiftsoftlight/relatorios/Alunos.jasper", parametros, con);
                                JasperPrint jp = JasperFillManager.fillReport("./relatorios/contratosImpresorra.jasper", parametros, con);
                                JasperViewer jrv = new JasperViewer(jp, false);
                                jrv.setVisible(true);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        //IMPRESSÃO FIM

                        if (jRBImpressoraPadrao.isSelected()) {
                            if (jCheckBox1.isSelected() == true) {
                                dataSemFormatacao = JOptionPane.showInputDialog(this, "Informe a data sem a necessidade de formatação predifinida.\n"
                                        + "Use a formatação que desejar.");
                            }
                            String caminho = "../seeds-java/";
                            File file = new File(caminho + File.separator + "COMPROVANTE DE PAGAMENTO - SEEDS.txt");
                            if (!file.exists()) {
                                System.out.println("arquivo não existe");
                                System.out.println("criando arquivo File.txt em..." + caminho);
                                try {
                                    //Aqui é o que falta  
                                    file.createNewFile();
                                } catch (IOException ex) {
                                    Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                System.out.println("arquivo COMPROVANTE DE PAGAMENTO - SEEDS.txt, criado em" + "caminho");
                            }
                            String mes = (pesquisarMes(txtDataPagamento.getText()));
                            String ano;
                            ano = txtDataPagamento.getText();
                            ano = ano.replace("/1/", "/01/");
                            ano = ano.substring(6);
                            String Final = "";
                            Final = "_____________________________________________________________________________________";
                            Final = Final + "\n Associação Filantrópica N.de Amparo Social";
                            Final = Final + "\n Utilidade Pública Municipal Lei 3.376 de           RECIBO/MENSALIDADE";
                            Final = Final + "\n 22/12/2004 - CNPJ: 07.003.661/0001";
                            Final = Final + "\n_____________________________________________________________________________________";
                            Final = Final + "\n   Recebemos de " + txtNomeDoCliente.getText() + " a quantia de R$ " + txtValorPago.getText() + " ( " + txtValorPago.getToolTipText() + " ), ";
                            Final = Final + "\n   Referente a " + mes + " " + ano + " Turma/Dia:" + txtNomeDoCurso.getText() + " - " + txtDiaTurma.getText() + "";
                            Final = Final + "\n   Horário: " + txtHI.getText() + " às " + txtHF.getText() + " Prof.: " + txtResponsavel.getText() + "";
                            Final = Final + "\n   Parceria/Local:________________________________";
                            Final = Final + "\n";
                            Final = Final + "\n 		Por ser verdade, firmo o presente termo.";
                            Final = Final + "\n";
                            Final = Final + "\n 		_____________________________________________________";
                            if (jCheckBox1.isSelected() == true) {
                                Final = Final + "\n 			  Montes Claros - " + dataSemFormatacao + "";
                            } else {
                                Final = Final + "\n 			  Montes Claros - " + txtDataEmQueFoiFeitoPagamento.getText() + "";
                            }
                            Final = Final + "\n_____________________________________________________________________________________";
                            Final = Final + "\n Rua Padre Augusto, 335 - 2º Andar - Centro - Montes Claros / MG Fone: (38) 3221-6288";
                            Final = Final + "\n Rua Pires e Albuquerque, Nº. 282 - Centro - Montes Claros / MG Fone: (38) 3221-0828";
                            Final = Final + "\n_____________________________________________________________________________________";
                            Final = Final + "\n                                            -- 1ª Via --";
                            Final = Final + "\n";
                            Final = Final + "\n";
                            Final = Final + "\n";
                            Final = Final + "\n";
                            Final = Final + "\n";
                            Final = Final + "\n";
                            Final = Final + "\n";
                            Final = Final + "\n_____________________________________________________________________________________";
                            Final = Final + "\n Associação Filantrópica N.de Amparo Social";
                            Final = Final + "\n Utilidade Pública Municipal Lei 3.376 de           RECIBO/MENSALIDADE";
                            Final = Final + "\n 22/12/2004 - CNPJ: 07.003.661/0001";
                            Final = Final + "\n_____________________________________________________________________________________";
                            Final = Final + "\n   Recebemos de " + txtNomeDoCliente.getText() + " a quantia de R$ " + txtValorPago.getText() + " ( " + txtValorPago.getToolTipText() + " ), ";
                            Final = Final + "\n   Referente a " + mes + " " + ano + " Turma/Dia:" + txtNomeDoCurso.getText() + " - " + txtDiaTurma.getText() + "";
                            Final = Final + "\n   Horário: " + txtHI.getText() + " às " + txtHF.getText() + " Prof.: " + txtResponsavel.getText() + "";
                            Final = Final + "\n   Parceria/Local:________________________________";
                            Final = Final + "\n";
                            Final = Final + "\n 		Por ser verdade, firmo o presente termo.";
                            Final = Final + "\n";
                            Final = Final + "\n 		_____________________________________________________";
                            if (jCheckBox1.isSelected() == true) {
                                Final = Final + "\n 			  Montes Claros - " + dataSemFormatacao + "";
                            } else {
                                Final = Final + "\n 			  Montes Claros - " + txtDataEmQueFoiFeitoPagamento.getText() + "";
                            }
                            Final = Final + "\n_____________________________________________________________________________________";
                            Final = Final + "\n Rua Padre Augusto, 335 - 2º Andar - Centro - Montes Claros / MG Fone: (38) 3221-6288";
                            Final = Final + "\n Rua Pires e Albuquerque, Nº. 282 - Centro - Montes Claros / MG Fone: (38) 3221-0828";
                            Final = Final + "\n_____________________________________________________________________________________";


                            BufferedWriter out = null;
                            try {
                                out = new BufferedWriter(new FileWriter("COMPROVANTE DE PAGAMENTO - SEEDS.txt"));
                            } catch (IOException ex) {
                                Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            out.write(Final); // "" = quebra de linha no Windows
                            //System.out.println("Acabei de escrever no arquivo");
                            out.close();

                            File Emails = new File("COMPROVANTE DE PAGAMENTO - SEEDS.txt");
                            try {
                                Desktop.getDesktop().print(Emails);
                            } catch (IOException ex) {
                                Logger.getLogger(ContasReceberContratos.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }


                        this.dispose();
                        ContasReceberContratos Entrada;
                        try {
                            Entrada = new ContasReceberContratos(0);
                            Entrada.setVisible(true);
                        } catch (ParseException ex) {
                            Logger.getLogger(ContasReceberContratos.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        txtValorPago.setText("00");
                        masterTable.setVisible(false);
                        getDadosTabelaClientePesquisa();
                        masterTable.setVisible(true);
                    }
                }
                cliente = null;//Liberar objetos obsoletos logo após o uso atribuindo null.
                dao.desconectar();
            } catch (Exception e) {
            }
        }
    }//GEN-LAST:event_jBSalvarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        ContasReceberContratos.this.dispose();
        this.dispose();
        ContasReceberContratos Entrada;
        try {
            try {
                Entrada = new ContasReceberContratos(0);
                Entrada.setVisible(true);
            } catch (ParseException ex) {
                Logger.getLogger(ContasReceberContratos.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (BancoException ex) {
            Logger.getLogger(ContasReceberContratos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ContasReceberContratos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.dispose();
        try {
            new PagamentosDetalhes().setVisible(true);
        } catch (BancoException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jBAlunosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAlunosActionPerformed
        apagar = nome.length();
        nome.delete(0, apagar);
        nome.append(txtCodigoCliente.getText());
        try {
            ClientesDAO dao = new ClientesDAO();
            Cliente cliente = new Cliente();
            cliente = dao.carregarClienteInativoPeloCodigo(nome, nomes);
            txtCodigo.setText(String.valueOf(cliente.getCodigo()));
            if (cliente.getNome().equals("nulo")) {
                JOptionPane.showMessageDialog(rootPane,
                        "O cliente informado não está inativo ou não consta no banco!\n"
                        + "Verifique se o código do Local: " + txtCodigoLocalidade.getText() + " é \n"
                        + "o mesmo do Aluno selecionado.",
                        "Seeds", JOptionPane.ERROR_MESSAGE);
            } else {
                dao.adicionarCliente(cliente);
                dao.deletarClienteExcluido(cliente);
                    txtNomeDoCliente.setBackground(Color.lightGray);
                    jBAlunos.setEnabled(false);
            }
            dao.desconectar();
        } catch (BancoException b) {
            JOptionPane.showMessageDialog(null, b ,"Impossível executar",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jBAlunosActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if (txtValorPago.getText().equals("0,00") && jCBPago.isSelected() == false) {
            int selection = JOptionPane.showConfirmDialog(rootPane,
                    "Note que o valor pago será atualizado para 0,00 ",
                    "Confirma?", JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.WARNING_MESSAGE);
            if (selection == JOptionPane.OK_OPTION) {

                txtCodigo.setText(txtCodigo.getText().trim());
                apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
                nome.delete(0, apagar);
                nome.append(txtCodigo.getText());
                try {
                    ContasRDAO dao = new ContasRDAO(); // estou instanciando a conexão
                    ContasR cliente = new ContasR();
                    cliente = dao.carregarContasContratoPeloCodigo(nome);
                    if (cliente.getData_emissao().equals("nulo")) {
                        JOptionPane.showMessageDialog(this,
                                "O cliente informado não consta no banco!");
                    } else {
                        if (txtValorPago.getText().equals("")) {
                            JOptionPane.showMessageDialog(this,
                                    "Campo Valor pago tem que ser informado!",
                                    "Atenção!", JOptionPane.INFORMATION_MESSAGE);
                            txtValorPago.requestFocus();
                        } else {

                            int linha = masterTable.getSelectedRow();
                            String ValorPago = "" + masterTable.getValueAt(linha, 7).hashCode();
                            AcessoDAO daos = new AcessoDAO();
                            Acesso okay = new Acesso();
                            NowString();
                            okay.setUsuario(Integer.parseInt(txtCodigoUsuarioLogado.getText()));
                            okay.setData(s);
                            okay.setHora(f);
                            okay.setDescricao("Atualização do pagamento [Interface - Contas à receber - Contratos]\n"
                                    + "Registro: " + txtCodigo.getText() + " "
                                    + "Parcela: " + txtParcela.getText() + " "
                                    + "Valor da parcela: " + cliente.getValor() + " "
                                    + "Valor pago: " + cliente.getValor_pago() + " "
                                    + "Desconto: " + cliente.getDesconto() + " "
                                    + "Vencimento: " + cliente.getData_pagamento().replace("/1/", "/01/") + "\n"
                                    + "Pagamento: " + cliente.getData_emissao().replace("/1/", "/01/") + "\n"
                                    + "*Como era - antes da alteração");
                            daos.adicionarAcesso(okay);

                            String Valor = txtValorPago.getText();
                            Valor = Valor.replace(".", "");
                            Valor = Valor.replace(",", ".");
                            cliente.setValor_pago(Double.parseDouble(Valor));
                            String Desconto = txtDesconto.getText();
                            Desconto = Desconto.replace(".", "");
                            Desconto = Desconto.replace(",", ".");
                            cliente.setDesconto(Double.parseDouble(Desconto));
                            cliente.setData_pagamento(txtDataPagamento.getText().trim());
                            cliente.setData_emissao(txtDataEmQueFoiFeitoPagamento.getText().trim());//Data pagamento
                            if (jCBPago.isSelected() == true) {
                                cliente.setTotal(1.00);
                            } else {
                                cliente.setTotal(0.00);
                            }
                            dao.atualizaDados(cliente);
                            JOptionPane.showMessageDialog(null, "Efetuado com sucesso!");

                            //AcessoDAO daos = new AcessoDAO();
                            //Acesso okay = new Acesso();
                            NowString();
                            okay.setUsuario(Integer.parseInt(txtCodigoUsuarioLogado.getText()));
                            okay.setData(s);
                            okay.setHora(f);
                            okay.setDescricao("Atualização do pagamento [Interface - Contas à receber - Contratos]\n"
                                    + "Registro: " + txtCodigo.getText() + " "
                                    + "Parcela: " + txtParcela.getText() + " "
                                    + "Valor da parcela: " + txtValorDaParcela.getText() + " "
                                    + "Valor pago: " + txtValorPago.getText() + " "
                                    + "Desconto: " + txtDesconto.getText() + " "
                                    + "Vencimento: " + txtDataPagamento.getText() + "\n"
                                    + "Pagamento: " + txtDataEmQueFoiFeitoPagamento.getText() + "\n"
                                    + "*Como ficou - pós alteração");
                            daos.adicionarAcesso(okay);

                            this.dispose();
                            ContasReceberContratos Entrada;
                            try {
                                Entrada = new ContasReceberContratos(0);
                                Entrada.setVisible(true);
                            } catch (ParseException ex) {
                                Logger.getLogger(ContasReceberContratos.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            txtValorPago.setText("00");
                            masterTable.setVisible(false);
                            getDadosTabelaClientePesquisa();
                            masterTable.setVisible(true);
                        }
                    }
                    cliente = null;//Liberar objetos obsoletos logo após o uso atribuindo null.
                    dao.desconectar();
                } catch (Exception e) {
                }
            }
        } else {
            txtCodigo.setText(txtCodigo.getText().trim());
            apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
            nome.delete(0, apagar);
            nome.append(txtCodigo.getText());
            try {
                ContasRDAO dao = new ContasRDAO(); // estou instanciando a conexão
                ContasR cliente = new ContasR();
                cliente = dao.carregarContasContratoPeloCodigo(nome);
                if (cliente.getData_emissao().equals("nulo")) {
                    JOptionPane.showMessageDialog(this,
                            "O cliente informado não consta no banco!");
                } else {
                    if (txtValorPago.getText().equals("")) {
                        JOptionPane.showMessageDialog(this,
                                "Campo Valor pago tem que ser informado!",
                                "Atenção!", JOptionPane.INFORMATION_MESSAGE);
                        txtValorPago.requestFocus();
                    } else {

                        int linha = masterTable.getSelectedRow();
                        String ValorPago = "" + masterTable.getValueAt(linha, 7).hashCode();
                        AcessoDAO daos = new AcessoDAO();
                        Acesso okay = new Acesso();
                        NowString();
                        okay.setUsuario(Integer.parseInt(txtCodigoUsuarioLogado.getText()));
                        okay.setData(s);
                        okay.setHora(f);
                        okay.setDescricao("Atualização do pagamento [Interface - Contas à receber - Contratos]\n"
                                + "Registro: " + txtCodigo.getText() + " "
                                + "Parcela: " + txtParcela.getText() + " "
                                + "Valor da parcela: " + cliente.getValor() + " "
                                + "Valor pago: " + cliente.getValor_pago() + " "
                                + "Desconto: " + cliente.getDesconto() + " "
                                + "Vencimento: " + cliente.getData_pagamento().replace("/1/", "/01/") + "\n"
                                + "Pagamento: " + cliente.getData_emissao().replace("/1/", "/01/") + "\n"
                                + "*Como era - antes da alteração");
                        daos.adicionarAcesso(okay);

                        String Valor = txtValorPago.getText();
                        Valor = Valor.replace(".", "");
                        Valor = Valor.replace(",", ".");
                        cliente.setValor_pago(Double.parseDouble(Valor));
                        String Desconto = txtDesconto.getText();
                        Desconto = Desconto.replace(".", "");
                        Desconto = Desconto.replace(",", ".");
                        cliente.setDesconto(Double.parseDouble(Desconto));
                        cliente.setData_pagamento(txtDataPagamento.getText().trim());
                        cliente.setData_emissao(txtDataEmQueFoiFeitoPagamento.getText().trim());
                        if (jCBPago.isSelected() == true) {
                            cliente.setTotal(1.00);
                        } else {
                            cliente.setTotal(0.00);
                        }
                        dao.atualizaDados(cliente);
                        JOptionPane.showMessageDialog(null, "Efetuado com sucesso!");

                        //AcessoDAO daos = new AcessoDAO();
                        //Acesso okay = new Acesso();
                        NowString();
                        okay.setUsuario(Integer.parseInt(txtCodigoUsuarioLogado.getText()));
                        okay.setData(s);
                        okay.setHora(f);
                        okay.setDescricao("Atualização do pagamento [Interface - Contas à receber - Contratos]\n"
                                + "Registro: " + txtCodigo.getText() + " "
                                + "Parcela: " + txtParcela.getText() + " "
                                + "Valor da parcela: " + txtValorDaParcela.getText() + " "
                                + "Valor pago: " + txtValorPago.getText() + " "
                                + "Desconto: " + txtDesconto.getText() + " "
                                + "Vencimento: " + txtDataPagamento.getText() + "\n"
                                + "Pagamento: " + txtDataEmQueFoiFeitoPagamento.getText() + "\n"
                                + "*Como ficou - pós alteração");
                        daos.adicionarAcesso(okay);

                        this.dispose();
                        ContasReceberContratos Entrada;
                        try {
                            Entrada = new ContasReceberContratos(0);
                            Entrada.setVisible(true);
                        } catch (ParseException ex) {
                            Logger.getLogger(ContasReceberContratos.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        txtValorPago.setText("00");
                        masterTable.setVisible(false);
                        getDadosTabelaClientePesquisa();
                        masterTable.setVisible(true);
                    }
                }
                cliente = null;//Liberar objetos obsoletos logo após o uso atribuindo null.
                dao.desconectar();
            } catch (Exception e) {
            }
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void txtDataPagamentoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDataPagamentoFocusLost
        if (txtDataPagamento.getText().contains("-")) {
            txtDataPagamento.setText(txtDataPagamento.getText().replace("-", "/"));
        }
        if (txtDataPagamento.getText().contains(" ")) {
            txtDataPagamento.setText(txtDataPagamento.getText().replace(" ", ""));
        }
        if (txtDataPagamento.getText().length() < 10 || txtDataPagamento.getText().length() > 10) {
            JOptionPane.showMessageDialog(rootPane, "Não é um formato de data válido!\n"
                    + "Exemplo: 2012/01/01\n"
                    + "yyyy/MM/dd", "", JOptionPane.ERROR_MESSAGE);
            txtDataPagamento.requestFocus();
        }
    }//GEN-LAST:event_txtDataPagamentoFocusLost

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        if (txtValorPago.getText().equals("0,00")) {
            int selection = JOptionPane.showConfirmDialog(this,
                    "Deseja emitir recibo com valor pago 0,00?\n",
                    "", JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if (selection == JOptionPane.OK_OPTION) {
                txtCodigo.setText(txtCodigo.getText().trim());
                apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
                nome.delete(0, apagar);
                nome.append(txtCodigo.getText());
                try {
                    //ContasRDAO dao = new ContasRDAO(); // estou instanciando a conexão
                    ContasR cliente = new ContasR();
                    //cliente = dao.carregarContasContratoPeloCodigo(nome);
                    if (cliente.getData_emissao().equals("nulo")) {
                        JOptionPane.showMessageDialog(this,
                                "O cliente informado não consta no banco!");
                    } else {
                        if (txtValorPago.getText().equals("")) {
                            JOptionPane.showMessageDialog(this,
                                    "Campo Valor pago tem que ser informado!",
                                    "Atenção!", JOptionPane.INFORMATION_MESSAGE);
                            txtValorPago.requestFocus();
                        } else {

                            String Valor = txtValorPago.getText();
                            Valor = Valor.replace(".", "");
                            Valor = Valor.replace(",", ".");
                            //cliente.setValor_pago(Double.parseDouble(Valor));
                            String Desconto = txtDesconto.getText();
                            Desconto = Desconto.replace(".", "");
                            Desconto = Desconto.replace(",", ".");
                            //cliente.setDesconto(Double.parseDouble(Desconto));
                            //cliente.setData_pagamento(txtDataPagamento.getText().trim());
                            if (jCBPago.isSelected() == true) {
                                cliente.setTotal(1.00);
                            } else {
                                cliente.setTotal(0.00);
                            }

                            //IMPRESSÃO 
                            if (jRadioBJasperIreport.isSelected()) {

                                try {
                                    Connection con = new Conexao().getConnection();
                                    HashMap parametros = new HashMap();
                                    parametros.put("NOME", txtNomeDoCliente.getText().trim());
                                    parametros.put("DINHEIRO", txtValorPago.getText());
                                    parametros.put("DINHEIROEX", txtValorPago.getToolTipText());
                                    String mes = (pesquisarMes(txtDataPagamento.getText()));
                                    parametros.put("MES", mes);
                                    parametros.put("TURMA", txtNomeDoCurso.getText().trim());
                                    parametros.put("DIA", txtDiaTurma.getText().trim());
                                    parametros.put("HI", txtHI.getText().trim());
                                    parametros.put("HF", txtHF.getText().trim());
                                    parametros.put("PROFESSOR", txtResponsavel.getText().trim());
                                    String ano;
                                    ano = txtDataPagamento.getText();
                                    ano = ano.replace("/1/", "/01/");
                                    ano = ano.substring(6);
                                    parametros.put("ANO", ano);
                                    if (jCheckBox1.isSelected() == true) {
                                        parametros.put("DATA", JOptionPane.showInputDialog(this, "Informe a data.\nNão precisa seguir nenhuma formatação"));
                                    } else {
                                        parametros.put("DATA", txtDataEmQueFoiFeitoPagamento.getText());
                                    }
                                    // JasperPrint jp = JasperFillManager.fillReport("C:/Users/Guilherme/Documents/NetBeansProjects/shiftsoftlight/src/relatorios/Alunos.jasper", parametros, con);
                                    //JasperPrint jp = JasperFillManager.fillReport("C:/Program Files/shiftsoftlight/relatorios/Alunos.jasper", parametros, con);
                                    JasperPrint jp = JasperFillManager.fillReport("./relatorios/contratosImpresorra.jasper", parametros, con);
                                    JasperViewer jrv = new JasperViewer(jp, false);
                                    jrv.setVisible(true);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            //IMPRESSÃO FIM

                            if (jRBImpressoraPadrao.isSelected()) {
                                if (jCheckBox1.isSelected() == true) {
                                    dataSemFormatacao = JOptionPane.showInputDialog(this, "Informe a data sem a necessidade de formatação predifinida.\n"
                                            + "Use a formatação que desejar.");
                                }
                                String caminho = "../seeds-java/";
                                File file = new File(caminho + File.separator + "COMPROVANTE DE PAGAMENTO - SEEDS.txt");
                                if (!file.exists()) {
                                    System.out.println("arquivo não existe");
                                    System.out.println("criando arquivo File.txt em..." + caminho);
                                    try {
                                        //Aqui é o que falta  
                                        file.createNewFile();
                                    } catch (IOException ex) {
                                        Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    System.out.println("arquivo COMPROVANTE DE PAGAMENTO - SEEDS.txt, criado em" + "caminho");
                                }
                                String mes = (pesquisarMes(txtDataPagamento.getText()));
                                String ano;
                                ano = txtDataPagamento.getText();
                                ano = ano.replace("/1/", "/01/");
                                ano = ano.substring(6);
                                String Final = "";
                                Final = "_____________________________________________________________________________________";
                                Final = Final + "\n Associação Filantrópica N.de Amparo Social";
                                Final = Final + "\n Utilidade Pública Municipal Lei 3.376 de           RECIBO/MENSALIDADE";
                                Final = Final + "\n 22/12/2004 - CNPJ: 07.003.661/0001";
                                Final = Final + "\n_____________________________________________________________________________________";
                                Final = Final + "\n   Recebemos de " + txtNomeDoCliente.getText() + " a quantia de R$ " + txtValorPago.getText() + " ( " + txtValorPago.getToolTipText() + " ), ";
                                Final = Final + "\n   Referente a " + mes + " " + ano + " Turma/Dia:" + txtNomeDoCurso.getText() + " - " + txtDiaTurma.getText() + "";
                                Final = Final + "\n   Horário: " + txtHI.getText() + " às " + txtHF.getText() + " Prof.: " + txtResponsavel.getText() + "";
                                Final = Final + "\n   Parceria/Local:________________________________";
                                Final = Final + "\n";
                                Final = Final + "\n 		Por ser verdade, firmo o presente termo.";
                                Final = Final + "\n";
                                Final = Final + "\n 		_____________________________________________________";
                                if (jCheckBox1.isSelected() == true) {
                                    Final = Final + "\n 			  Montes Claros - " + dataSemFormatacao + "";
                                } else {
                                    Final = Final + "\n 			  Montes Claros - " + txtDataEmQueFoiFeitoPagamento.getText() + "";
                                }
                                Final = Final + "\n_____________________________________________________________________________________";
                                Final = Final + "\n Rua Padre Augusto, 335 - 2º Andar - Centro - Montes Claros / MG Fone: (38) 3221-6288";
                                Final = Final + "\n Rua Pires e Albuquerque, Nº. 282 - Centro - Montes Claros / MG Fone: (38) 3221-0828";
                                Final = Final + "\n_____________________________________________________________________________________";
                                Final = Final + "\n                                            -- 1ª Via --";
                                Final = Final + "\n";
                                Final = Final + "\n";
                                Final = Final + "\n";
                                Final = Final + "\n";
                                Final = Final + "\n";
                                Final = Final + "\n";
                                Final = Final + "\n";
                                Final = Final + "\n_____________________________________________________________________________________";
                                Final = Final + "\n Associação Filantrópica N.de Amparo Social";
                                Final = Final + "\n Utilidade Pública Municipal Lei 3.376 de           RECIBO/MENSALIDADE";
                                Final = Final + "\n 22/12/2004 - CNPJ: 07.003.661/0001";
                                Final = Final + "\n_____________________________________________________________________________________";
                                Final = Final + "\n   Recebemos de " + txtNomeDoCliente.getText() + " a quantia de R$ " + txtValorPago.getText() + " ( " + txtValorPago.getToolTipText() + " ), ";
                                Final = Final + "\n   Referente a " + mes + " " + ano + " Turma/Dia:" + txtNomeDoCurso.getText() + " - " + txtDiaTurma.getText() + "";
                                Final = Final + "\n   Horário: " + txtHI.getText() + " às " + txtHF.getText() + " Prof.: " + txtResponsavel.getText() + "";
                                Final = Final + "\n   Parceria/Local:________________________________";
                                Final = Final + "\n";
                                Final = Final + "\n 		Por ser verdade, firmo o presente termo.";
                                Final = Final + "\n";
                                Final = Final + "\n 		_____________________________________________________";
                                if (jCheckBox1.isSelected() == true) {
                                    Final = Final + "\n 			  Montes Claros - " + dataSemFormatacao + "";
                                } else {
                                    Final = Final + "\n 			  Montes Claros - " + txtDataEmQueFoiFeitoPagamento.getText() + "";
                                }
                                Final = Final + "\n_____________________________________________________________________________________";
                                Final = Final + "\n Rua Padre Augusto, 335 - 2º Andar - Centro - Montes Claros / MG Fone: (38) 3221-6288";
                                Final = Final + "\n Rua Pires e Albuquerque, Nº. 282 - Centro - Montes Claros / MG Fone: (38) 3221-0828";
                                Final = Final + "\n_____________________________________________________________________________________";


                                BufferedWriter out = null;
                                try {
                                    out = new BufferedWriter(new FileWriter("COMPROVANTE DE PAGAMENTO - SEEDS.txt"));
                                } catch (IOException ex) {
                                    Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                out.write(Final); // "" = quebra de linha no Windows
                                //System.out.println("Acabei de escrever no arquivo");
                                out.close();

                                File Emails = new File("COMPROVANTE DE PAGAMENTO - SEEDS.txt");
                                try {
                                    Desktop.getDesktop().print(Emails);
                                } catch (IOException ex) {
                                    Logger.getLogger(ContasReceberContratos.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                    }
                    cliente = null;//Liberar objetos obsoletos logo após o uso atribuindo null.
                } catch (Exception e) {
                }
            }
        } else {
            txtCodigo.setText(txtCodigo.getText().trim());
            apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
            nome.delete(0, apagar);
            nome.append(txtCodigo.getText());
            try {
                ContasRDAO dao = new ContasRDAO(); // estou instanciando a conexão
                ContasR cliente = new ContasR();
                cliente = dao.carregarContasContratoPeloCodigo(nome);
                if (cliente.getData_emissao().equals("nulo")) {
                    JOptionPane.showMessageDialog(this,
                            "O cliente informado não consta no banco!");
                } else {
                    if (txtValorPago.getText().equals("")) {
                        JOptionPane.showMessageDialog(this,
                                "Campo Valor pago tem que ser informado!",
                                "Atenção!", JOptionPane.INFORMATION_MESSAGE);
                        txtValorPago.requestFocus();
                    } else {

                        String Valor = txtValorPago.getText();
                        Valor = Valor.replace(".", "");
                        Valor = Valor.replace(",", ".");
                        //cliente.setValor_pago(Double.parseDouble(Valor));
                        String Desconto = txtDesconto.getText();
                        Desconto = Desconto.replace(".", "");
                        Desconto = Desconto.replace(",", ".");
                        //cliente.setDesconto(Double.parseDouble(Desconto));
                        //cliente.setData_pagamento(txtDataPagamento.getText().trim());
                        //dao.atualizaDados(cliente);
                        //JOptionPane.showMessageDialog(null, "Efetuado com sucesso! 2");

                        //IMPRESSÃO 
                        if (jRadioBJasperIreport.isSelected()) {

                            try {
                                Connection con = new Conexao().getConnection();
                                HashMap parametros = new HashMap();
                                parametros.put("NOME", txtNomeDoCliente.getText().trim());
                                parametros.put("DINHEIRO", txtValorPago.getText());
                                parametros.put("DINHEIROEX", txtValorPago.getToolTipText());
                                String mes = (pesquisarMes(txtDataPagamento.getText()));
                                parametros.put("MES", mes);
                                parametros.put("TURMA", txtNomeDoCurso.getText().trim());
                                parametros.put("DIA", txtDiaTurma.getText().trim());
                                parametros.put("HI", txtHI.getText().trim());
                                parametros.put("HF", txtHF.getText().trim());
                                parametros.put("PROFESSOR", txtResponsavel.getText().trim());
                                String ano;
                                ano = txtDataPagamento.getText();
                                ano = ano.replace("/1/", "/01/");
                                ano = ano.substring(6);
                                parametros.put("ANO", ano);
                                if (jCheckBox1.isSelected() == true) {
                                    parametros.put("DATA", JOptionPane.showInputDialog(this, "Informe a data.\nNão precisa seguir nenhuma formatação"));
                                } else {
                                    parametros.put("DATA", txtDataEmQueFoiFeitoPagamento.getText());
                                }
                                // JasperPrint jp = JasperFillManager.fillReport("C:/Users/Guilherme/Documents/NetBeansProjects/shiftsoftlight/src/relatorios/Alunos.jasper", parametros, con);
                                //JasperPrint jp = JasperFillManager.fillReport("C:/Program Files/shiftsoftlight/relatorios/Alunos.jasper", parametros, con);
                                JasperPrint jp = JasperFillManager.fillReport("./relatorios/contratosImpresorra.jasper", parametros, con);
                                JasperViewer jrv = new JasperViewer(jp, false);
                                jrv.setVisible(true);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        //IMPRESSÃO FIM

                        if (jRBImpressoraPadrao.isSelected()) {
                            if (jCheckBox1.isSelected() == true) {
                                dataSemFormatacao = JOptionPane.showInputDialog(this, "Informe a data sem a necessidade de formatação predifinida.\n"
                                        + "Use a formatação que desejar.");
                            }
                            String caminho = "../seeds-java/";
                            File file = new File(caminho + File.separator + "COMPROVANTE DE PAGAMENTO - SEEDS.txt");
                            if (!file.exists()) {
                                System.out.println("arquivo não existe");
                                System.out.println("criando arquivo File.txt em..." + caminho);
                                try {
                                    //Aqui é o que falta  
                                    file.createNewFile();
                                } catch (IOException ex) {
                                    Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                System.out.println("arquivo COMPROVANTE DE PAGAMENTO - SEEDS.txt, criado em" + "caminho");
                            }
                            String mes = (pesquisarMes(txtDataPagamento.getText()));
                            String ano;
                            ano = txtDataPagamento.getText();
                            ano = ano.replace("/1/", "/01/");
                            ano = ano.substring(6);
                            String Final = "";
                            Final = "_____________________________________________________________________________________";
                            Final = Final + "\n Associação Filantrópica N.de Amparo Social";
                            Final = Final + "\n Utilidade Pública Municipal Lei 3.376 de           RECIBO/MENSALIDADE";
                            Final = Final + "\n 22/12/2004 - CNPJ: 07.003.661/0001";
                            Final = Final + "\n_____________________________________________________________________________________";
                            Final = Final + "\n   Recebemos de " + txtNomeDoCliente.getText() + " a quantia de R$ " + txtValorPago.getText() + " ( " + txtValorPago.getToolTipText() + " ), ";
                            Final = Final + "\n   Referente a " + mes + " " + ano + " Turma/Dia:" + txtNomeDoCurso.getText() + " - " + txtDiaTurma.getText() + "";
                            Final = Final + "\n   Horário: " + txtHI.getText() + " às " + txtHF.getText() + " Prof.: " + txtResponsavel.getText() + "";
                            Final = Final + "\n   Parceria/Local:________________________________";
                            Final = Final + "\n";
                            Final = Final + "\n 		Por ser verdade, firmo o presente termo.";
                            Final = Final + "\n";
                            Final = Final + "\n 		_____________________________________________________";
                            if (jCheckBox1.isSelected() == true) {
                                Final = Final + "\n 			  Montes Claros - " + dataSemFormatacao + "";
                            } else {
                                Final = Final + "\n 			  Montes Claros - " + txtDataEmQueFoiFeitoPagamento.getText() + "";
                            }
                            Final = Final + "\n_____________________________________________________________________________________";
                            Final = Final + "\n Rua Padre Augusto, 335 - 2º Andar - Centro - Montes Claros / MG Fone: (38) 3221-6288";
                            Final = Final + "\n Rua Pires e Albuquerque, Nº. 282 - Centro - Montes Claros / MG Fone: (38) 3221-0828";
                            Final = Final + "\n_____________________________________________________________________________________";
                            Final = Final + "\n                                            -- 1ª Via --";
                            Final = Final + "\n";
                            Final = Final + "\n";
                            Final = Final + "\n";
                            Final = Final + "\n";
                            Final = Final + "\n";
                            Final = Final + "\n";
                            Final = Final + "\n";
                            Final = Final + "\n_____________________________________________________________________________________";
                            Final = Final + "\n Associação Filantrópica N.de Amparo Social";
                            Final = Final + "\n Utilidade Pública Municipal Lei 3.376 de           RECIBO/MENSALIDADE";
                            Final = Final + "\n 22/12/2004 - CNPJ: 07.003.661/0001";
                            Final = Final + "\n_____________________________________________________________________________________";
                            Final = Final + "\n   Recebemos de " + txtNomeDoCliente.getText() + " a quantia de R$ " + txtValorPago.getText() + " ( " + txtValorPago.getToolTipText() + " ), ";
                            Final = Final + "\n   Referente a " + mes + " " + ano + " Turma/Dia:" + txtNomeDoCurso.getText() + " - " + txtDiaTurma.getText() + "";
                            Final = Final + "\n   Horário: " + txtHI.getText() + " às " + txtHF.getText() + " Prof.: " + txtResponsavel.getText() + "";
                            Final = Final + "\n   Parceria/Local:________________________________";
                            Final = Final + "\n";
                            Final = Final + "\n 		Por ser verdade, firmo o presente termo.";
                            Final = Final + "\n";
                            Final = Final + "\n 		_____________________________________________________";
                            if (jCheckBox1.isSelected() == true) {
                                Final = Final + "\n 			  Montes Claros - " + dataSemFormatacao + "";
                            } else {
                                Final = Final + "\n 			  Montes Claros - " + txtDataEmQueFoiFeitoPagamento.getText() + "";
                            }
                            Final = Final + "\n_____________________________________________________________________________________";
                            Final = Final + "\n Rua Padre Augusto, 335 - 2º Andar - Centro - Montes Claros / MG Fone: (38) 3221-6288";
                            Final = Final + "\n Rua Pires e Albuquerque, Nº. 282 - Centro - Montes Claros / MG Fone: (38) 3221-0828";
                            Final = Final + "\n_____________________________________________________________________________________";


                            BufferedWriter out = null;
                            try {
                                out = new BufferedWriter(new FileWriter("COMPROVANTE DE PAGAMENTO - SEEDS.txt"));
                            } catch (IOException ex) {
                                Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            out.write(Final); // "" = quebra de linha no Windows
                            //System.out.println("Acabei de escrever no arquivo");
                            out.close();

                            File Emails = new File("COMPROVANTE DE PAGAMENTO - SEEDS.txt");
                            try {
                                Desktop.getDesktop().print(Emails);
                            } catch (IOException ex) {
                                Logger.getLogger(ContasReceberContratos.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }
                cliente = null;//Liberar objetos obsoletos logo após o uso atribuindo null.
                dao.desconectar();
            } catch (Exception e) {
            }
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void txtValorPagoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtValorPagoFocusLost
        String ValorExtenso = txtValorPago.getText();
        ValorExtenso = ValorExtenso.replace(".", "");
        ValorExtenso = ValorExtenso.replace(",", ".");
        valorPorExtenso(Double.parseDouble(ValorExtenso));
    }//GEN-LAST:event_txtValorPagoFocusLost

    private void txtCodigoTurmaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodigoTurmaFocusLost
        if (txtCodigoTurma.getText().equals("") || txtCodigoTurma.getText().equals("0")) {
            txtCodigoTurma.setText("0");
            txtResponsavel.setText("");
            txtDiaTurma.setText("");
            txtHI.setText("");
            txtHF.setText("");
        } else {
            try {
                TurmasDAO daop = new TurmasDAO();
                Turmas turmass = new Turmas();

                apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
                nome.delete(0, apagar);
                nome.append(txtCodigoTurma.getText() + "");
                turmass = daop.carregarTurmaPeloCodigo(nome);
                txtDiaTurma.setText("" + turmass.getDia());
                txtHI.setText("" + turmass.getHi());
                txtHF.setText("" + turmass.getHf());
                txtNomeDoCurso.setText("" + turmass.getCurso());

                apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
                nome.delete(0, apagar);
                nome.append(turmass.getFuncionario() + "");

                ProfessorDAO daus = new ProfessorDAO(); // estou instanciando a conexão
                Professor professor = new Professor();
                professor = daus.carregarProfessorPeloCodigo(nome);
                if (professor.getNome().equals("nulo")) {
                    txtResponsavel.setText("");
                    txtDiaTurma.setText("");
                    txtHI.setText("");
                    txtHF.setText("");
                    txtNomeDoCurso.setText("");
                    txtCodigoTurma.setText("0");
                    final BuscarTurmas pesq = new BuscarTurmas("Turmas");
                    pesq.setVisible(true);

                    ActionListener acaoOk = new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            //int codigoTurma = pesq.getCodigoCliente();
                            apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
                            nome.delete(0, apagar);
                            nome.append(pesq.getCodigoCliente());

                            try {
                                TurmasDAO dao = new TurmasDAO();
                                Turmas al = new Turmas();
                                al = dao.carregarTurmaPeloCodigo(nome);
                                txtCodigoTurma.setText(String.valueOf(nome));
                                CodigoInstrutor = "" + al.getFuncionario();
                                pegaNomeInstrutor();
                                dao.desconectar();
                            } catch (Exception b) {
                                JOptionPane.showMessageDialog(null, e);
                            }
                            pesq.dispose();
                        }
                    };
                    pesq.setAcao(acaoOk);
                } else {
                    txtResponsavel.setText(professor.getNome());
                }
                turmass = null;//Liberar objetos obsoletos logo após o uso atribuindo null.
                professor = null;
            } catch (Exception e) {
            }
        }
    }//GEN-LAST:event_txtCodigoTurmaFocusLost

    private void txtCodigoTurmaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoTurmaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                TurmasDAO daop = new TurmasDAO();
                Turmas turmass = new Turmas();

                apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
                nome.delete(0, apagar);
                nome.append(txtCodigoTurma.getText() + "");
                turmass = daop.carregarTurmaPeloCodigo(nome);

                //txtCodigo.setText(String.valueOf(turmass.getCodigo()));
                txtDiaTurma.setText("" + turmass.getDia());
                txtHI.setText("" + turmass.getHi());
                txtHF.setText("" + turmass.getHf());
                txtNomeDoCurso.setText("" + turmass.getCurso());

                apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
                nome.delete(0, apagar);
                nome.append(turmass.getFuncionario() + "");

                ProfessorDAO daus = new ProfessorDAO(); // estou instanciando a conexão
                Professor professor = new Professor();
                professor = daus.carregarProfessorPeloCodigo(nome);
                if (professor.getNome().equals("nulo")) {

                    txtResponsavel.setText("");
                    txtCodigoTurma.setText("0");
                    final BuscarTurmas pesq = new BuscarTurmas("Turmas");
                    pesq.setVisible(true);

                    ActionListener acaoOk = new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            //int codigoTurma = pesq.getCodigoCliente();
                            apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
                            nome.delete(0, apagar);
                            nome.append(pesq.getCodigoCliente());

                            try {
                                TurmasDAO dao = new TurmasDAO();
                                Turmas al = new Turmas();
                                al = dao.carregarTurmaPeloCodigo(nome);
                                txtCodigoTurma.setText(String.valueOf(nome));
                                CodigoInstrutor = "" + al.getFuncionario();
                                pegaNomeInstrutor();
                                dao.desconectar();
                            } catch (Exception b) {
                                JOptionPane.showMessageDialog(null, e);
                            }
                            pesq.dispose();
                        }
                    };
                    pesq.setAcao(acaoOk);
                } else {
                    txtResponsavel.setText(professor.getNome());
                }
                turmass = null;//Liberar objetos obsoletos logo após o uso atribuindo null.
                professor = null;
                daop.desconectar();
                daus.desconectar();
            } catch (Exception e) {
            }
        }
    }//GEN-LAST:event_txtCodigoTurmaKeyPressed

    private void txtDataEmQueFoiFeitoPagamentoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDataEmQueFoiFeitoPagamentoKeyTyped
    }//GEN-LAST:event_txtDataEmQueFoiFeitoPagamentoKeyTyped

    private void txtDataEmQueFoiFeitoPagamentoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDataEmQueFoiFeitoPagamentoFocusLost
        txtDataEmQueFoiFeitoPagamento.setText(txtDataEmQueFoiFeitoPagamento.getText().replace("-", "/"));
        txtDataEmQueFoiFeitoPagamento.setText(txtDataEmQueFoiFeitoPagamento.getText().replace(" ", ""));
        txtDataEmQueFoiFeitoPagamento.setText(txtDataEmQueFoiFeitoPagamento.getText().replace("/1/", "/01/"));
        txtDataEmQueFoiFeitoPagamento.setText(txtDataEmQueFoiFeitoPagamento.getText().replace("/2/", "/02/"));
        txtDataEmQueFoiFeitoPagamento.setText(txtDataEmQueFoiFeitoPagamento.getText().replace("/3/", "/03/"));
        txtDataEmQueFoiFeitoPagamento.setText(txtDataEmQueFoiFeitoPagamento.getText().replace("/4/", "/04/"));
        txtDataEmQueFoiFeitoPagamento.setText(txtDataEmQueFoiFeitoPagamento.getText().replace("/5/", "/05/"));
        txtDataEmQueFoiFeitoPagamento.setText(txtDataEmQueFoiFeitoPagamento.getText().replace("/6/", "/06/"));
        txtDataEmQueFoiFeitoPagamento.setText(txtDataEmQueFoiFeitoPagamento.getText().replace("/7/", "/07/"));
        txtDataEmQueFoiFeitoPagamento.setText(txtDataEmQueFoiFeitoPagamento.getText().replace("/8/", "/08/"));
        txtDataEmQueFoiFeitoPagamento.setText(txtDataEmQueFoiFeitoPagamento.getText().replace("/9/", "/09/"));
        if (txtDataEmQueFoiFeitoPagamento.getText().contains("/01/")
                || txtDataEmQueFoiFeitoPagamento.getText().contains("/02/")
                || txtDataEmQueFoiFeitoPagamento.getText().contains("/03/")
                || txtDataEmQueFoiFeitoPagamento.getText().contains("/04/")
                || txtDataEmQueFoiFeitoPagamento.getText().contains("/05/")
                || txtDataEmQueFoiFeitoPagamento.getText().contains("/06/")
                || txtDataEmQueFoiFeitoPagamento.getText().contains("/07/")
                || txtDataEmQueFoiFeitoPagamento.getText().contains("/08/")
                || txtDataEmQueFoiFeitoPagamento.getText().contains("/09/")
                || txtDataEmQueFoiFeitoPagamento.getText().contains("/10/")
                || txtDataEmQueFoiFeitoPagamento.getText().contains("/11/")
                || txtDataEmQueFoiFeitoPagamento.getText().contains("/12/")) {
        } else {
            txtDataEmQueFoiFeitoPagamento.setText("0000/00/00");
        }
        if (txtDataEmQueFoiFeitoPagamento.getText().length() < 10) {
            txtDataEmQueFoiFeitoPagamento.setText("0000/00/00");
        }
    }//GEN-LAST:event_txtDataEmQueFoiFeitoPagamentoFocusLost
    /**
     * @param args the command line arguments
     */
    public DefaultTableModel getDadosTabelaClientePesquisa() {

        String linha;
        String linha2;
        String numerocontrato = "";
        String numeroaluno = "";


        do {
            try {
                FileReader arq = new FileReader("../seeds-java/contratospagamentos.txt");
                FileReader arq2 = new FileReader("../seeds-java/contratospagamentosaluno.txt");

                BufferedReader lerArq = new BufferedReader(arq);
                BufferedReader lerArq2 = new BufferedReader(arq2);

                linha = lerArq.readLine(); // lê a primeira linha
                while (linha != null) {
                    numerocontrato = linha;
                    linha = lerArq.readLine(); // lê da segunda até a última linha
                }
                linha2 = lerArq2.readLine(); // lê a primeira linha
                while (linha2 != null) {
                    numeroaluno = linha2;
                    linha2 = lerArq2.readLine(); // lê da segunda até a última linha
                }

                arq.close();
                arq2.close();


            } catch (IOException e) {
                System.err.printf("Erro na abertura do arquivo: %s.\n",
                        e.getMessage());
            }
        } while (numerocontrato.equals(""));

        try {
            ContasRDAO dao = new ContasRDAO();
            if (numeroaluno.equals("") && numerocontrato.equals("")) {
                jLErroAOFiltraContrato.setVisible(true);
            } else {
                parametroPesquisa = numerocontrato + "";
                campo = numeroaluno;
            }

            List<ContasR> lista = dao.pesquisacontasReFiltro(parametroPesquisa, campo);

            Object[][] dados = new Object[lista.size()][10];

            for (int us = 0; us < lista.size(); us++) {

                dados[us][0] = lista.get(us).getCodigo_cliente();
                dados[us][1] = lista.get(us).getCodigo_contrato();
                dados[us][2] = lista.get(us).getParcela();
                dados[us][3] = lista.get(us).getData_emissao();
                dados[us][4] = lista.get(us).getData_pagamento();
                dados[us][5] = lista.get(us).getCodigo_turma();
                dados[us][6] = lista.get(us).getValor();
                dados[us][7] = lista.get(us).getValor_pago();
                dados[us][8] = lista.get(us).getCodigo();
                dados[us][9] = lista.get(us).getLocalidade();
            }
            lista = null;
            dao.desconectar();
            String[] nomeColunas = {"Cliente", "CONTRATO", "Parela", "Pago em", "Vencimento", "Turma", "Valor", "Valor Pago", "Código", "Local"};
            return new DefaultTableModel(dados, nomeColunas);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e + "Erro ao construir a tabela.");
            return null;
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroupImpressoras;
    private javax.swing.JButton jBAlunos;
    private javax.swing.JButton jBCancelar;
    private javax.swing.JButton jBSalvar;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JCheckBox jCBPago;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLErroAOFiltraContrato;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JRadioButton jRBImpressoraPadrao;
    private javax.swing.JRadioButton jRadioBJasperIreport;
    private javax.swing.JRadioButton jRadioBNaoImprimir;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable masterTable;
    private converter.RowSorterToStringConverter rowSorterToStringConverter1;
    private javax.swing.JTextField txtCPF;
    private javax.swing.JFormattedTextField txtCelular;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtCodigoCliente;
    private javax.swing.JTextField txtCodigoLocalidade;
    private javax.swing.JTextField txtCodigoTurma;
    private javax.swing.JTextField txtCodigoUsuarioLogado;
    private javax.swing.JTextField txtDataEmQueFoiFeitoPagamento;
    private javax.swing.JTextField txtDataPagamento;
    private javax.swing.JTextField txtDesconto;
    private javax.swing.JTextField txtDiaTurma;
    private javax.swing.JFormattedTextField txtHF;
    private javax.swing.JFormattedTextField txtHI;
    private javax.swing.JTextField txtLocalizar;
    private javax.swing.JTextField txtNomeDoCliente;
    private javax.swing.JTextField txtNomeDoCurso;
    private javax.swing.JTextField txtParcela;
    private javax.swing.JTextField txtResponsavel;
    private javax.swing.JFormattedTextField txtTelefone;
    private javax.swing.JTextField txtValorDaParcela;
    private javax.swing.JTextField txtValorPago;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
