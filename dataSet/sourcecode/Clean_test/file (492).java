/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * JFContrato.java
 *
 * Created on 12/07/2011, 05:35:18
 */
package br.com.seeds;

import Classes.*;
import ClassesDAO.*;
import Excessoes.BancoException;
import java.awt.Color;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Guilherme
 */
public class Contratos extends javax.swing.JFrame {

    float t2F = 0;
    float t1F = 0;
    float d1F = 0;
    float d2F = 0;
    String dataContrato = "";
    String valors = "";
    int tabelaPulaLinha = 0;
    int CodigoContrato = 0;
    String CodigoInstrutor = "";
    int parcela = 0;
    int parcelas = 0;
    int Dia = 0;
    int Mes = 0;
    int Ano = 0;
    int codigoR = 0;
    GregorianCalendar data = new GregorianCalendar();
    int mes = data.get(Calendar.MONTH);
    int dia = data.get(Calendar.DAY_OF_MONTH);
    int ano = data.get(Calendar.YEAR);
    int apagar = 0;
    StringBuffer nome = new StringBuffer();
    StringBuffer nomes = new StringBuffer();
    public ClientesDAO daos;
    public Cliente aluno = new Cliente();
    String parametroPesquisa = "";
    String campo;
    private String nomeCliente = "teste não vai aparecer";

    /**
     * Creates new form JFContrato
     */
    public Contratos() throws BancoException {
        initComponents();
        daos = new ClientesDAO();
        txtParcela.setDocument(new OnlyNumberField(11));
        txtTurmaCodigo.setDocument(new OnlyNumberField(11));
        txtCodigoAluno.setDocument(new OnlyNumberField(11));
        txtCodigoLocalidade.setDocument(new OnlyNumberField(11));
        txtValor.setDocument(new LimitadorMoeda());
        txtValor.setText("0000");
        txtMaterial.setDocument(new LimitadorMoeda());
        txtMaterial.setText("0000");
        txtNomeAluno.setDocument(new LimiteCampos.FixedLengthDocument(100));
        txtParcela.setText("1");
        txtPesquisa.setText("1");
        txtAno.setValue(ano);
        txtTurmaCodigo.setText("0");


        try {
            ContratoDAO dao = new ContratoDAO();
            txtCodigo.setText(String.valueOf(dao.gerarCodigoContrato()));
            dao.desconectar();
        } catch (Exception f) {
            f.printStackTrace();
        }

        try {
            LocalidadeDAO dao = new LocalidadeDAO();
            txtCodigoLocalidade.setText(String.valueOf(dao.gerarCodigoLocalidade() - 1));
            dao.desconectar();
        } catch (BancoException f) {
            f.printStackTrace();
        }
        txtCodigoAluno.requestFocus();

        //Para Editar o total
        txtTotal.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                //Verificando se o botão direito do mouse foi clicado  
                if ((me.getModifiers() & MouseEvent.BUTTON3_MASK) != 0) {
                    JPopupMenu menus = new JPopupMenu();
                    JMenuItem item = new JMenuItem("Clique-me para Editar");
                    menus.add(item);

                    item.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent ae) {
                            //JOptionPane.showMessageDialog(null, "Fui clicado !");
                            //kill("javaw.exe");
                            txtTotal.setEditable(true);
                            //System.exit(0);
                        }
                    });
                    menus.show(txtTotal, me.getX(), me.getY());
                }
            }
        });

        //Para Editar a data nascimento
        txtNascimento.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                //Verificando se o botão direito do mouse foi clicado  
                if ((me.getModifiers() & MouseEvent.BUTTON3_MASK) != 0) {
                    JPopupMenu menus = new JPopupMenu();
                    JMenuItem item = new JMenuItem("Clique-me para Editar");
                    menus.add(item);

                    item.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent ae) {
                            //JOptionPane.showMessageDialog(null, "Fui clicado !");
                            //kill("javaw.exe");
                            txtNascimento.setEditable(true);
                            //System.exit(0);
                        }
                    });
                    menus.show(txtNascimento, me.getX(), me.getY());
                }
            }
        });

        //Para Fechar Habilitar JBGerarParcelas sem gerar relatorios
        jBGerarParcelas.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                //Verificando se o botão direito do mouse foi clicado  
                if ((me.getModifiers() & MouseEvent.BUTTON3_MASK) != 0) {
                    JPopupMenu menus = new JPopupMenu();
                    JMenuItem item = new JMenuItem("Clique-me para me Habilitar");
                    menus.add(item);

                    item.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent ae) {
                            //JOptionPane.showMessageDialog(null, "Fui clicado !");
                            //kill("javaw.exe");
                            jBGerarParcelas.setEnabled(true);
                            //System.exit(0);
                        }
                    });
                    menus.show(jBGerarParcelas, me.getX(), me.getY());
                }
            }
        });
        //this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                try {
                    try {
                        try {
                            if (jBGerarParcelas.isEnabled() == true) {
                                int selection = JOptionPane.showConfirmDialog(rootPane,
                                        "Deseja sair sem Gerar as parcelas?",
                                        "Seeds", JOptionPane.OK_CANCEL_OPTION,
                                        JOptionPane.QUESTION_MESSAGE);
                                if (selection == JOptionPane.OK_OPTION) {
                                    Fechando();
                                }
                            } else {
                                Fechando();
                            }

                        } catch (ParseException ex) {
                            Logger.getLogger(BuscarSubprodutosCaixaVenda.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(BuscarSubprodutosCaixaVenda.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (BancoException ex) {
                    Logger.getLogger(BuscarSubprodutosCaixaVenda.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    public void Fechando() throws BancoException, SQLException, ParseException {
        this.dispose();
    }
    private String dataAtual = new SimpleDateFormat("dd/MM/yyyy").format(new Date(System.currentTimeMillis()));
    private FormatarData format = new FormatarData();

    //Calcula a Idade baseado em String. Exemplo: calculaIdade("20/08/1977","dd/MM/yyyy");
    public static int calculaIdade(String dataNasc, String pattern) {

        DateFormat sdf = new SimpleDateFormat(pattern);
        Date dataNascInput = null;
        try {
            dataNascInput = sdf.parse(dataNasc);
        } catch (Exception e) {
        }
        Calendar dateOfBirth = new GregorianCalendar();
        dateOfBirth.setTime(dataNascInput);
// Cria um objeto calendar com a data atual
        Calendar today = Calendar.getInstance();
// Obtém a idade baseado no ano
        int age = today.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR);
        dateOfBirth.add(Calendar.YEAR, age);
        if (today.before(dateOfBirth)) {
            age--;
        }
        return age;
    }

    public void valorPorExtenso(double vlr) {

        if (vlr == 0) //return("zero");
        {
            txtTotal.setToolTipText("Zero");
        }

        long inteiro = (long) Math.abs(vlr); // parte inteira do valor
        double resto = vlr - inteiro;       // parte fracionária do valor

        String vlrS = String.valueOf(inteiro);
        if (vlrS.length() > 15) //return("Erro: valor superior a 999 trilhões.");
        {
            txtTotal.setToolTipText("Erro: valor superior a 999 trilhões.");
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
        txtTotal.setToolTipText(svalor);
        //JOptionPane.showMessageDialog(rootPane, svalor);
    }

    public void GravarDadosContrato() throws IOException {

        if (nomeCliente.equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Não foi possível transferir os dados.\n"
                    + "Código nulo.",
                    "Atualizando",
                    JOptionPane.ERROR_MESSAGE);
            this.dispose();


        } else {
            FileWriter arq = new FileWriter("../seeds-java/Arquivo.txt");
            PrintWriter gravarArq = new PrintWriter(arq);
            gravarArq.printf(nomeCliente);
            arq.close();
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

        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        masterTable = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtParcela = new javax.swing.JTextField();
        txtValor = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtMes = new javax.swing.JSpinner();
        txtDia = new javax.swing.JSpinner();
        txtAno = new javax.swing.JSpinner();
        jPanel3 = new javax.swing.JPanel();
        txtNomeAluno = new javax.swing.JTextField();
        txtInstrutor = new javax.swing.JTextField();
        txtCidade = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtLogradouro = new javax.swing.JTextField();
        txtCodigoAluno = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtTurmaCodigo = new javax.swing.JTextField();
        txtEstado = new javax.swing.JTextField();
        txtBairro = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtPesquisar = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        txtPesquisa = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jBGerarParcelas = new javax.swing.JButton();
        txtCPF = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtCodigoLocalidade = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txtNumero = new javax.swing.JTextField();
        txtDatas = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jCheckBox1 = new javax.swing.JCheckBox();
        txtContratante = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jCBContratada = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtMaterial = new javax.swing.JTextField();
        txtTotal = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtProfissao = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jCBRuaC = new javax.swing.JComboBox();
        jLabel21 = new javax.swing.JLabel();
        jCBNumeroC = new javax.swing.JComboBox();
        jLabel22 = new javax.swing.JLabel();
        jCBBairroC = new javax.swing.JComboBox();
        jLabel23 = new javax.swing.JLabel();
        jCBCidadeC = new javax.swing.JComboBox();
        jLabel24 = new javax.swing.JLabel();
        jCBCNPJ = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();
        txtNascimento = new javax.swing.JTextField();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Contratos");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setText("Código");

        txtCodigo.setEditable(false);
        txtCodigo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        masterTable.setBackground(new java.awt.Color(255, 255, 255));
        masterTable.setBorder(javax.swing.BorderFactory.createTitledBorder("Contratos"));
        masterTable.setToolTipText("");

        jLabel7.setText("** Data Contrato");

        jLabel10.setText("Qtde. Parcelas");
        jLabel10.setToolTipText("Quantidade de parcelas.");

        txtParcela.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtParcela.setDragEnabled(true);
        txtParcela.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtParcelaFocusLost(evt);
            }
        });
        txtParcela.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtParcelaKeyTyped(evt);
            }
        });

        txtValor.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtValor.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtValorCaretUpdate(evt);
            }
        });
        txtValor.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtValorFocusLost(evt);
            }
        });
        txtValor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtValorKeyTyped(evt);
            }
        });

        jLabel14.setText("Valor da parcela");

        txtMes.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtMes.setModel(new javax.swing.SpinnerNumberModel(1, 1, 12, 1));

        txtDia.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtDia.setModel(new javax.swing.SpinnerNumberModel(1, 1, 31, 1));

        txtAno.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtAno.setModel(new javax.swing.SpinnerNumberModel(1900, 1900, 3000, 1));

        javax.swing.GroupLayout masterTableLayout = new javax.swing.GroupLayout(masterTable);
        masterTable.setLayout(masterTableLayout);
        masterTableLayout.setHorizontalGroup(
            masterTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(masterTableLayout.createSequentialGroup()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(txtDia, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(txtMes, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(txtAno, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(txtParcela, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        masterTableLayout.setVerticalGroup(
            masterTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(masterTableLayout.createSequentialGroup()
                .addGroup(masterTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(masterTableLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(masterTableLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(masterTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtAno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(masterTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtParcela, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jPanel3.setForeground(new java.awt.Color(204, 204, 204));

        txtNomeAluno.setEditable(false);
        txtNomeAluno.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtNomeAluno.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtNomeAlunoCaretUpdate(evt);
            }
        });
        txtNomeAluno.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNomeAlunoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNomeAlunoKeyTyped(evt);
            }
        });

        txtInstrutor.setEditable(false);
        txtInstrutor.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtInstrutor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtInstrutorKeyTyped(evt);
            }
        });

        txtCidade.setEditable(false);
        txtCidade.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtCidade.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCidadeKeyTyped(evt);
            }
        });

        jLabel5.setText("Instrutor");

        txtLogradouro.setEditable(false);
        txtLogradouro.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtLogradouro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtLogradouroKeyTyped(evt);
            }
        });

        txtCodigoAluno.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtCodigoAluno.setToolTipText("Pressione enter para pesquisar - Código do Aluno.");
        txtCodigoAluno.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodigoAlunoFocusLost(evt);
            }
        });
        txtCodigoAluno.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoAlunoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoAlunoKeyTyped(evt);
            }
        });

        jLabel13.setText("Uf");

        jLabel12.setText("Bairro");

        txtTurmaCodigo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtTurmaCodigo.setToolTipText("Pressione enter para pesquisar.");
        txtTurmaCodigo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTurmaCodigoFocusLost(evt);
            }
        });
        txtTurmaCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTurmaCodigoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTurmaCodigoKeyTyped(evt);
            }
        });

        txtEstado.setEditable(false);
        txtEstado.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtEstado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtEstadoKeyTyped(evt);
            }
        });

        txtBairro.setEditable(false);
        txtBairro.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtBairro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBairroKeyTyped(evt);
            }
        });

        jLabel3.setText("* Aluno");

        jLabel4.setText("* Turma");

        jLabel52.setText("Cidade");

        jLabel11.setText("Logradouro");

        txtPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/buscar.png"))); // NOI18N
        txtPesquisar.setBorderPainted(false);
        txtPesquisar.setContentAreaFilled(false);
        txtPesquisar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        txtPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPesquisarActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/arrow-left.png"))); // NOI18N
        jButton2.setBorderPainted(false);
        jButton2.setContentAreaFilled(false);
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton2MouseEntered(evt);
            }
        });
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        txtPesquisa.setEditable(false);
        txtPesquisa.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/arrow-right.png"))); // NOI18N
        jButton3.setBorderPainted(false);
        jButton3.setContentAreaFilled(false);
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton3MouseEntered(evt);
            }
        });
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jBGerarParcelas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/view-calendar-journal.png"))); // NOI18N
        jBGerarParcelas.setText("Gerar Parcelas");
        jBGerarParcelas.setToolTipText("Para gerar parcelas sem gerar o contrato clique com o botão direito do mouse e clique em Habilitar.");
        jBGerarParcelas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBGerarParcelas.setEnabled(false);
        jBGerarParcelas.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBGerarParcelas.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jBGerarParcelas.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jBGerarParcelas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBGerarParcelasActionPerformed(evt);
            }
        });

        txtCPF.setEditable(false);
        txtCPF.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel15.setText("CPF");

        txtCodigoLocalidade.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("/");

        jLabel19.setText("Nº");

        txtNumero.setEditable(false);
        txtNumero.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtDatas.setEditable(false);
        txtDatas.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCodigoAluno, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)
                        .addComponent(txtCodigoLocalidade, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtNomeAluno, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtTurmaCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel5)
                            .addGap(18, 18, 18)
                            .addComponent(txtInstrutor)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtDatas, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(9, 9, 9))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtCidade))
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtLogradouro, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addGap(19, 19, 19)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addGap(18, 18, 18)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(10, 10, 10)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtBairro, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                                .addComponent(txtCPF))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel19)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(txtNumero)))
                            .addGap(0, 70, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(txtPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jBGerarParcelas)
                        .addGap(19, 19, 19))))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel11, jLabel3, jLabel4, jLabel52});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtNomeAluno)
                                .addComponent(txtCodigoLocalidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel3)
                                .addComponent(txtCodigoAluno)
                                .addComponent(jLabel1)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtLogradouro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel11))
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtBairro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel19)
                                .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel52)
                                        .addComponent(txtCidade)
                                        .addComponent(txtCPF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtInstrutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtDatas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtTurmaCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jBGerarParcelas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel11, jLabel3, jLabel4, jLabel52});

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/draw-eraser (2).png"))); // NOI18N
        jButton4.setToolTipText("Excluir um contrato gerado.");
        jButton4.setBorderPainted(false);
        jButton4.setContentAreaFilled(false);
        jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(masterTable, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(masterTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(207, 207, 207))
        );

        jLabel6.setText("** Nota: a primeira parcela não será gerada com mês subsequente a data informada.");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jCheckBox1.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox1.setSelected(true);
        jCheckBox1.setText("Contratante: Aluno");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        txtContratante.setEditable(false);
        txtContratante.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel8.setText("Contratante");
        jLabel8.setToolTipText("Responsável pelo aluno.");

        jCBContratada.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jCBContratada.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "SEEDS - SPEAK EASY ENGLISH DIALOGUE SCHOOL", "SEEDS CONSULTING AND ACCESSORY – SEEDS INGLÊS PARA TODOS", "Usar a logradouro cadastrado na base de dados - LOCAL", "Editar" }));
        jCBContratada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBContratadaActionPerformed(evt);
            }
        });

        jLabel9.setText("Contratada");

        jLabel16.setText("Material ");
        jLabel16.setToolTipText("Valor do material.");

        txtMaterial.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtTotal.setEditable(false);
        txtTotal.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel17.setText("Total");

        jLabel18.setText("Profissão");

        txtProfissao.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel20.setText("Rua");
        jLabel20.setToolTipText("Rua da Contratada.");

        jCBRuaC.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jCBRuaC.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "rua Padre Augusto", "rua Pires e Albuquerque", "avenida Afonso Pena", "Editar" }));
        jCBRuaC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBRuaCActionPerformed(evt);
            }
        });

        jLabel21.setText("Número");
        jLabel21.setToolTipText("Número da contratada.");

        jCBNumeroC.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jCBNumeroC.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "335", "282", "266", "Editar" }));
        jCBNumeroC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBNumeroCActionPerformed(evt);
            }
        });

        jLabel22.setText("Bairro");

        jCBBairroC.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jCBBairroC.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Centro", "Editar" }));
        jCBBairroC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBBairroCActionPerformed(evt);
            }
        });

        jLabel23.setText("Cidade");

        jCBCidadeC.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jCBCidadeC.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Montes Claros", "Belo Horizonte", "Editar" }));
        jCBCidadeC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBCidadeCActionPerformed(evt);
            }
        });

        jLabel24.setText("CNPJ");

        jCBCNPJ.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jCBCNPJ.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "02.710.056/0001-65", "Editar" }));
        jCBCNPJ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBCNPJActionPerformed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/document-edit-sign (2).png"))); // NOI18N
        jButton1.setText("Gerar contrato");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        txtNascimento.setEditable(false);
        txtNascimento.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtContratante, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel16))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jCBContratada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(txtMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(12, 12, 12)
                                        .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10)
                                        .addComponent(txtProfissao, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(9, 9, 9)
                        .addComponent(jButton1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(32, 32, 32))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(24, 24, 24)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(24, 24, 24)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jCBBairroC, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jCBRuaC, 0, 360, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jCBCNPJ, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jCBNumeroC, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jCBCidadeC, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(20, 20, 20))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jCheckBox1)
                            .addComponent(txtContratante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addComponent(txtNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jCBContratada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(txtMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17)
                            .addComponent(jLabel18)
                            .addComponent(txtProfissao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(jCBRuaC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21)
                    .addComponent(jCBNumeroC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(jCBBairroC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23)
                    .addComponent(jCBCidadeC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(jCBCNPJ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        buttonGroup2.add(jRadioButton1);
        jRadioButton1.setSelected(true);
        jRadioButton1.setText("Vence pela data informada.");
        jRadioButton1.setToolTipText("A primeira parcela não será gerada com mês subsequente a data informada.");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        buttonGroup2.add(jRadioButton2);
        jRadioButton2.setText("Vence no próximo mês.");
        jRadioButton2.setToolTipText("A primeira parcela será gerada com mês subsequente a data informada.");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jRadioButton1)
                .addGap(18, 18, 18)
                .addComponent(jRadioButton2)
                .addGap(22, 22, 22))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel6, jRadioButton1, jRadioButton2});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNomeAlunoCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtNomeAlunoCaretUpdate
    }//GEN-LAST:event_txtNomeAlunoCaretUpdate

    private void txtValorCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtValorCaretUpdate
}//GEN-LAST:event_txtValorCaretUpdate

    private void jButton2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseEntered
        // TODO add your handling code here:
}//GEN-LAST:event_jButton2MouseEntered

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Integer conte;
        conte = Integer.parseInt(txtPesquisa.getText());
        conte -= 1;
        txtPesquisa.setText(String.valueOf(conte));

        apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
        nome.delete(0, apagar);
        nome.append(txtPesquisa.getText());
        apagar = nomes.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
        nomes.delete(0, apagar);
        nomes.append(txtCodigoLocalidade.getText());
        try {
            aluno = daos.carregarClientePeloCodigo(nome, nomes);
            if (aluno.getNome().equals("nulo")) {
                JOptionPane.showMessageDialog(rootPane, "O aluno informado não consta no banco!");
                conte += 1;
                txtPesquisa.setText(String.valueOf(conte));
            } else {
                txtNomeAluno.setText("");
                txtCodigoAluno.setText("");
                txtLogradouro.setText("");
                txtBairro.setText("");
                txtEstado.setText("");
                txtCidade.setText("");
                txtCPF.setText("");
                txtCodigoAluno.setText(String.valueOf(aluno.getCodigo()));
                txtCodigoLocalidade.setText(String.valueOf(aluno.getLocalidade()));
                txtNomeAluno.setText(aluno.getNome());
                txtLogradouro.setText(aluno.getEndereco());
                txtCidade.setText(aluno.getCidade());
                txtBairro.setText(aluno.getBairro());
                txtEstado.setText(aluno.getUf());
                txtCPF.setText(aluno.getCpf());
            }
            aluno = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
}//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseEntered
        // TODO add your handling code here:
}//GEN-LAST:event_jButton3MouseEntered

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        Integer conte;
        conte = Integer.parseInt(txtPesquisa.getText());
        conte += 1;
        txtPesquisa.setText(String.valueOf(conte));

        apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
        nome.delete(0, apagar);
        nome.append(txtPesquisa.getText());
        apagar = nomes.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
        nomes.delete(0, apagar);
        nomes.append(txtCodigoLocalidade.getText());
        try {
            aluno = daos.carregarClientePeloCodigo(nome, nomes);
            if (aluno.getNome().equals("nulo")) {
                JOptionPane.showMessageDialog(rootPane, "O aluno informado não consta no banco!");
                conte -= 1;
                txtPesquisa.setText(String.valueOf(conte));
            } else {
                txtNomeAluno.setText("");
                txtCodigoAluno.setText("");
                txtLogradouro.setText("");
                txtBairro.setText("");
                txtEstado.setText("");
                txtCidade.setText("");
                txtCPF.setText("");
                txtCodigoAluno.setText(String.valueOf(aluno.getCodigo()));
                txtCodigoLocalidade.setText(String.valueOf(aluno.getLocalidade()));
                txtNomeAluno.setText(aluno.getNome());
                txtLogradouro.setText(aluno.getEndereco());
                txtCidade.setText(aluno.getCidade());
                txtBairro.setText(aluno.getBairro());
                txtEstado.setText(aluno.getUf());
                txtCPF.setText(aluno.getCpf());

            }
            aluno = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void txtNomeAlunoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomeAlunoKeyPressed
    }//GEN-LAST:event_txtNomeAlunoKeyPressed

private void txtPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPesquisarActionPerformed
    apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
    nome.delete(0, apagar);
    nome.append(txtCodigoAluno.getText());
    apagar = nomes.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
    nomes.delete(0, apagar);
    nomes.append(txtCodigoLocalidade.getText());
    try {
        aluno = daos.carregarClientePeloCodigo(nome, nomes);
        if (aluno.getNome().equals("nulo")) {
            txtCodigoAluno.requestFocus();
            try {

                if (daos.gerarCodigoCliente() == 1) {
                    JOptionPane.showMessageDialog(this,
                            "O sistema não possuí um cliente cadastrado.",
                            "seeds",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {

                    final BuscarClientes pesq = new BuscarClientes("Clientes");
                    pesq.setVisible(true);

                    ActionListener acaoOk = new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            StringBuffer codigoAluno = new StringBuffer();
                            int codigoCliente = 0;
                            codigoCliente = pesq.getCodigoCliente();
                            codigoAluno.append(codigoCliente);
                            apagar = nomes.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
                            nomes.delete(0, apagar);
                            nomes.append(txtCodigoLocalidade.getText());
                            try {
                                ClientesDAO dao = new ClientesDAO();
                                Cliente cliente = new Cliente();
                                cliente = dao.carregarClientePeloCodigo(codigoAluno, nomes);
                                txtNomeAluno.setText("");
                                txtCodigoAluno.setText("");
                                txtLogradouro.setText("");
                                txtBairro.setText("");
                                txtEstado.setText("");
                                txtCidade.setText("");
                                txtCPF.setText("");
                                txtNumero.setText("");
                                txtNascimento.setText("");

                                txtCodigoAluno.setText(String.valueOf(cliente.getCodigo()));
                                txtNomeAluno.setText(cliente.getNome());
                                txtLogradouro.setText(cliente.getEndereco());
                                txtBairro.setText(cliente.getBairro());
                                txtEstado.setText(cliente.getUf());
                                txtCidade.setText(cliente.getCidade());
                                txtCPF.setText(cliente.getCpf());
                                txtNumero.setText(cliente.getNumero());
                                txtNascimento.setText(cliente.getNascimento());
                                if (!txtCodigoAluno.getText().equals("")) {
                                    nomeCliente = txtCodigoAluno.getText();
                                    try {
                                        GravarDadosContrato();
                                    } catch (IOException ex) {
                                        Logger.getLogger(BuscarClientesContratosExcluir.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                                txtTurmaCodigo.requestFocus();

                                //IDADE
                                if (txtNascimento.getText() == null || txtNascimento.getText().equals("")) {
                                } else {
                                    int age = calculaIdade(txtNascimento.getText().trim(), "dd/MM/yyyy");
                                    //JOptionPane.showMessageDialog(rootPane, age);
                                    if (age < 18) {
                                        txtNascimento.setBackground(Color.red);
                                        txtNascimento.setToolTipText("Menor de idade");
                                        jCheckBox1.setSelected(false);
                                        txtContratante.setEditable(true);
                                    } else {
                                        txtNascimento.setBackground(Color.green);
                                        txtNascimento.setToolTipText("Maior de idade");
                                        jCheckBox1.setSelected(true);
                                        txtContratante.setEditable(false);
                                    }
                                }
                                txtTurmaCodigo.requestFocus();
                                dao.desconectar();
                            } catch (BancoException b) {
                                JOptionPane.showMessageDialog(null, e);
                            }
                            pesq.dispose();
                        }
                    };
                    pesq.setAcao(acaoOk);
                }
                aluno = null;
            } catch (BancoException f) {
                f.printStackTrace();
            }

        } else {
            txtNomeAluno.setText("");
            txtCodigoAluno.setText("");
            txtLogradouro.setText("");
            txtBairro.setText("");
            txtEstado.setText("");
            txtCidade.setText("");
            txtCPF.setText("");
            txtNumero.setText("");
            txtNascimento.setText("");

            txtCodigoAluno.setText(String.valueOf(aluno.getCodigo()));
            txtNomeAluno.setText(aluno.getNome());
            txtLogradouro.setText(aluno.getEndereco());
            txtBairro.setText(aluno.getBairro());
            txtEstado.setText(aluno.getUf());
            txtCidade.setText(aluno.getCidade());
            txtCPF.setText(aluno.getCpf());
            txtNumero.setText(aluno.getNumero());
            txtNascimento.setText(aluno.getNascimento());
            //IDADE
            if (txtNascimento.getText() == null || txtNascimento.getText().equals("")) {
            } else {
                int age = calculaIdade(txtNascimento.getText().trim(), "dd/MM/yyyy");
                //JOptionPane.showMessageDialog(rootPane, age);
                if (age < 18) {
                    txtNascimento.setBackground(Color.red);
                    txtNascimento.setToolTipText("Menor de idade");
                    jCheckBox1.setSelected(false);
                    txtContratante.setEditable(true);
                } else {
                    txtNascimento.setBackground(Color.green);
                    txtNascimento.setToolTipText("Maior de idade");
                    jCheckBox1.setSelected(true);
                    txtContratante.setEditable(false);
                }
            }
            apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
            nome.delete(0, apagar);
            if (!txtCodigoAluno.getText().equals("")) {
                nomeCliente = txtCodigoAluno.getText();
                try {
                    GravarDadosContrato();
                } catch (IOException ex) {
                    Logger.getLogger(BuscarClientesContratosExcluir.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            txtTurmaCodigo.requestFocus();
        }
        aluno = null;
    } catch (BancoException e) {
        e.printStackTrace();
    }
    apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
    nome.delete(0, apagar);

}//GEN-LAST:event_txtPesquisarActionPerformed

private void jBGerarParcelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGerarParcelasActionPerformed

    if (txtCodigoAluno.getText().equals("") || txtCodigoLocalidade.getText().equals("")) {
        JOptionPane.showMessageDialog(rootPane, "Campo Aluno e Local tem que ser informado!",
                "Atenção!", JOptionPane.INFORMATION_MESSAGE);
        if (txtCodigoAluno.getText().equals("")) {
            txtCodigoAluno.requestFocus();
        } else {
            txtCodigoLocalidade.requestFocus();
        }
    } else {
        apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
        nome.delete(0, apagar);
        nome.append(txtCodigoAluno.getText());
        apagar = nomes.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
        nomes.delete(0, apagar);
        nomes.append(txtCodigoLocalidade.getText());
        try {
            aluno = daos.carregarClientePeloCodigo(nome, nomes);
        } catch (BancoException ex) {
            Logger.getLogger(Contratos.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (aluno.getNome().equals("nulo")) {
            JOptionPane.showMessageDialog(this,
                    "O aluno informado não consta no banco!");
        } else {
            jBGerarParcelas.setEnabled(false);
            Dia = (Integer) txtDia.getValue();
            Mes = (Integer) txtMes.getValue();
            Ano = (Integer) txtAno.getValue();
            // GregorianCalendar data = new GregorianCalendar();
            //int ano = data.get(Calendar.YEAR);
            if (Mes >= 10) {
                dataContrato = String.valueOf(Ano + "/" + Mes + "/" + Dia);
            } else {
                dataContrato = String.valueOf(Ano + "/0" + Mes + "/" + Dia);
            }
            txtDatas.setText(dataContrato);


            Contrato venda = new Contrato();

            venda.setCodigo(Integer.parseInt(txtCodigo.getText()));
            venda.setCodigo_aluno(Integer.parseInt(txtCodigoAluno.getText()));
            venda.setLocalidade(Integer.parseInt(txtCodigoLocalidade.getText()));

            salvarContrato();

            try {
                ContratoDAO dao = new ContratoDAO();
                dao.adicionarContrato(venda);
                JOptionPane.showMessageDialog(this, "Você poderá ver os contratos em relatórios!",
                        "Concluído",
                        JOptionPane.INFORMATION_MESSAGE);
                Contratos.this.dispose();
                new Contratos().setVisible(true);
                venda = null;
                dao.desconectar();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}//GEN-LAST:event_jBGerarParcelasActionPerformed

private void txtTurmaCodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTurmaCodigoKeyPressed
    if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

        try {
            TurmasDAO daop = new TurmasDAO();
            Turmas turmass = new Turmas();

            apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
            nome.delete(0, apagar);
            nome.append(txtTurmaCodigo.getText() + "");
            turmass = daop.carregarTurmaPeloCodigo(nome);
            apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
            nome.delete(0, apagar);
            nome.append(turmass.getFuncionario() + "");

            ProfessorDAO daus = new ProfessorDAO(); // estou instanciando a conexão
            Professor professor = new Professor();
            professor = daus.carregarProfessorPeloCodigo(nome);
            if (professor.getNome().equals("nulo")) {

                txtInstrutor.setText("");
                txtTurmaCodigo.setText("0");
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
                            txtTurmaCodigo.setText(String.valueOf(nome));
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
                txtInstrutor.setText(professor.getNome());
                txtDia.requestFocus();
            }
            daop.desconectar();
            daus.desconectar();
        } catch (Exception e) {
        }
    }
}//GEN-LAST:event_txtTurmaCodigoKeyPressed

private void txtValorFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtValorFocusLost
    float parcelaCalc, valorCalc;
    parcelaCalc = Integer.parseInt(txtParcela.getText().trim());

    String Valor = txtValor.getText();
    Valor = Valor.replace(".", "");
    Valor = Valor.replace(",", ".");
    valorCalc = Float.parseFloat(Valor);

    valorCalc = parcelaCalc * valorCalc;
    txtTotal.setText(valorCalc + "");

    valorPorExtenso(valorCalc);

}//GEN-LAST:event_txtValorFocusLost

private void txtParcelaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtParcelaFocusLost
    if (txtParcela.getText().equals("") || txtParcela.getText().equals("0")) {
        txtParcela.setText("1");
    }
    float parcelaCalc, valorCalc;
    parcelaCalc = Integer.parseInt(txtParcela.getText().trim());

    String Valor = txtValor.getText();
    Valor = Valor.replace(".", "");
    Valor = Valor.replace(",", ".");
    valorCalc = Float.parseFloat(Valor);

    valorCalc = parcelaCalc * valorCalc;
    txtTotal.setText(valorCalc + "");

    valorPorExtenso(valorCalc);
}//GEN-LAST:event_txtParcelaFocusLost

private void txtCodigoAlunoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoAlunoKeyTyped
}//GEN-LAST:event_txtCodigoAlunoKeyTyped

private void txtNomeAlunoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomeAlunoKeyTyped
}//GEN-LAST:event_txtNomeAlunoKeyTyped

private void txtLogradouroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLogradouroKeyTyped
}//GEN-LAST:event_txtLogradouroKeyTyped

private void txtBairroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBairroKeyTyped
}//GEN-LAST:event_txtBairroKeyTyped

private void txtTurmaCodigoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTurmaCodigoKeyTyped
}//GEN-LAST:event_txtTurmaCodigoKeyTyped

private void txtInstrutorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtInstrutorKeyTyped
}//GEN-LAST:event_txtInstrutorKeyTyped

private void txtCidadeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCidadeKeyTyped
}//GEN-LAST:event_txtCidadeKeyTyped

private void txtEstadoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEstadoKeyTyped
}//GEN-LAST:event_txtEstadoKeyTyped

private void txtParcelaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtParcelaKeyTyped
}//GEN-LAST:event_txtParcelaKeyTyped

private void txtValorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtValorKeyTyped
}//GEN-LAST:event_txtValorKeyTyped

    private void txtTurmaCodigoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTurmaCodigoFocusLost
        if (txtTurmaCodigo.getText().equals("") || txtTurmaCodigo.getText().equals("0")) {
            txtTurmaCodigo.setText("0");
            txtInstrutor.setText("");
            txtDia.requestFocus();
        } else {
            try {
                TurmasDAO daop = new TurmasDAO();
                Turmas turmass = new Turmas();

                apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
                nome.delete(0, apagar);
                nome.append(txtTurmaCodigo.getText() + "");
                turmass = daop.carregarTurmaPeloCodigo(nome);

                apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
                nome.delete(0, apagar);
                nome.append(turmass.getFuncionario() + "");

                ProfessorDAO daus = new ProfessorDAO(); // estou instanciando a conexão
                Professor professor = new Professor();
                professor = daus.carregarProfessorPeloCodigo(nome);
                if (professor.getNome().equals("nulo")) {

                    txtInstrutor.setText("");
                    txtTurmaCodigo.setText("0");
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
                                txtTurmaCodigo.setText(String.valueOf(nome));
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
                    txtInstrutor.setText(professor.getNome());
                    txtDia.requestFocus();
                }
                daop.desconectar();
                daus.desconectar();
            } catch (Exception e) {
            }
        }
    }//GEN-LAST:event_txtTurmaCodigoFocusLost

    private void txtCodigoAlunoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodigoAlunoFocusLost
    }//GEN-LAST:event_txtCodigoAlunoFocusLost

    private void txtCodigoAlunoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoAlunoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.dispose();
        }
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
            nome.delete(0, apagar);
            nome.append(txtCodigoAluno.getText());
            apagar = nomes.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
            nomes.delete(0, apagar);
            nomes.append(txtCodigoLocalidade.getText());
            try {
                aluno = daos.carregarClientePeloCodigo(nome, nomes);
                if (aluno.getNome().equals("nulo")) {
                    txtCodigoAluno.requestFocus();
                    try {

                        if (daos.gerarCodigoCliente() == 1) {
                            JOptionPane.showMessageDialog(this,
                                    "O sistema não possuí um cliente cadastrado.",
                                    "seeds",
                                    JOptionPane.INFORMATION_MESSAGE);
                        } else {

                            final BuscarClientes pesq = new BuscarClientes("Clientes");
                            pesq.setVisible(true);

                            ActionListener acaoOk = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    StringBuffer codigoAluno = new StringBuffer();
                                    int codigoCliente = 0;
                                    codigoCliente = pesq.getCodigoCliente();
                                    codigoAluno.append(codigoCliente);
                                    apagar = nomes.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
                                    nomes.delete(0, apagar);
                                    nomes.append(txtCodigoLocalidade.getText());
                                    try {
                                        ClientesDAO dao = new ClientesDAO();
                                        Cliente cliente = new Cliente();
                                        cliente = dao.carregarClientePeloCodigo(codigoAluno, nomes);
                                        txtNomeAluno.setText("");
                                        txtCodigoAluno.setText("");
                                        txtLogradouro.setText("");
                                        txtBairro.setText("");
                                        txtEstado.setText("");
                                        txtCidade.setText("");
                                        txtCPF.setText("");
                                        txtNumero.setText("");
                                        txtNascimento.setText("");

                                        txtCodigoAluno.setText(String.valueOf(cliente.getCodigo()));
                                        txtNomeAluno.setText(cliente.getNome());
                                        txtLogradouro.setText(cliente.getEndereco());
                                        txtBairro.setText(cliente.getBairro());
                                        txtEstado.setText(cliente.getUf());
                                        txtCidade.setText(cliente.getCidade());
                                        txtCPF.setText(cliente.getCpf());
                                        txtNumero.setText(cliente.getNumero());
                                        txtNascimento.setText(cliente.getNascimento());
                                        if (!txtCodigoAluno.getText().equals("")) {
                                            nomeCliente = txtCodigoAluno.getText();
                                            try {
                                                GravarDadosContrato();
                                            } catch (IOException ex) {
                                                Logger.getLogger(BuscarClientesContratosExcluir.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                        }
                                        txtTurmaCodigo.requestFocus();
                                        //IDADE
                                        if (txtNascimento.getText() == null || txtNascimento.getText().equals("")) {
                                        } else {
                                            int age = calculaIdade(txtNascimento.getText().trim(), "dd/MM/yyyy");
                                            //JOptionPane.showMessageDialog(rootPane, age);
                                            if (age < 18) {
                                                txtNascimento.setBackground(Color.red);
                                                txtNascimento.setToolTipText("Menor de idade");
                                                jCheckBox1.setSelected(false);
                                                txtContratante.setEditable(true);
                                            } else {
                                                txtNascimento.setBackground(Color.green);
                                                txtNascimento.setToolTipText("Maior de idade");
                                                jCheckBox1.setSelected(true);
                                                txtContratante.setEditable(false);
                                            }
                                        }
                                        cliente = null;
                                        txtTurmaCodigo.requestFocus();
                                        dao.desconectar();
                                    } catch (BancoException b) {
                                        JOptionPane.showMessageDialog(null, e);
                                    }
                                    pesq.dispose();
                                }
                            };
                            pesq.setAcao(acaoOk);
                        }
                    } catch (BancoException f) {
                        f.printStackTrace();
                    }
                } else {
                    txtNomeAluno.setText("");
                    txtCodigoAluno.setText("");
                    txtLogradouro.setText("");
                    txtBairro.setText("");
                    txtEstado.setText("");
                    txtCidade.setText("");
                    txtCPF.setText("");
                    txtNumero.setText("");
                    txtNascimento.setText("");

                    txtCodigoAluno.setText(String.valueOf(aluno.getCodigo()));
                    txtNomeAluno.setText(aluno.getNome());
                    txtLogradouro.setText(aluno.getEndereco());
                    txtBairro.setText(aluno.getBairro());
                    txtEstado.setText(aluno.getUf());
                    txtCidade.setText(aluno.getCidade());
                    txtCPF.setText(aluno.getCpf());
                    txtNumero.setText(aluno.getNumero());
                    txtNascimento.setText(aluno.getNascimento());
                    //IDADE
                    if (txtNascimento.getText() == null || txtNascimento.getText().equals("")) {
                    } else {
                        int age = calculaIdade(txtNascimento.getText().trim(), "dd/MM/yyyy");
                        //JOptionPane.showMessageDialog(rootPane, age);
                        if (age < 18) {
                            txtNascimento.setBackground(Color.red);
                            txtNascimento.setToolTipText("Menor de idade");
                            jCheckBox1.setSelected(false);
                            txtContratante.setEditable(true);
                        } else {
                            txtNascimento.setBackground(Color.green);
                            txtNascimento.setToolTipText("Maior de idade");
                            jCheckBox1.setSelected(true);
                            txtContratante.setEditable(false);
                        }
                    }
                    apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
                    nome.delete(0, apagar);
                    if (!txtCodigoAluno.getText().equals("")) {
                        nomeCliente = txtCodigoAluno.getText();
                        try {
                            GravarDadosContrato();
                        } catch (IOException ex) {
                            Logger.getLogger(BuscarClientesContratosExcluir.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    txtTurmaCodigo.requestFocus();
                }
                aluno = null;
            } catch (BancoException e) {
                e.printStackTrace();
            }
            apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
            nome.delete(0, apagar);
        }
    }//GEN-LAST:event_txtCodigoAlunoKeyPressed

    private void jCBContratadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBContratadaActionPerformed
        if (jCBContratada.getSelectedItem().equals("Editar")) {
            jCBContratada.setEditable(true);
            jCBContratada.setSelectedItem("");
        }
        if (jCBContratada.getSelectedItem().equals("Usar a logradouro cadastrado na base de dados - LOCAL")) {

            String codigo = "1";
            Localidade localidade = new Localidade();

            try {
                LocalidadeDAO dao = new LocalidadeDAO(); // estou instanciando a conexão
                localidade = dao.carregarLocalidadePeloCodigo(codigo);
                if (localidade.getBairro().equals("nulo")) {
                    JOptionPane.showMessageDialog(rootPane, "A localidade informado não consta no banco!");
                } else {

                    jCBBairroC.setEditable(true);
                    jCBBairroC.setSelectedItem(localidade.getBairro());
                    jCBCidadeC.setEditable(true);
                    jCBCidadeC.setSelectedItem(localidade.getCidade());
                    jCBRuaC.setEditable(true);
                    jCBRuaC.setSelectedItem(localidade.getEndereco());
                    jCBNumeroC.setEditable(true);
                    jCBNumeroC.setSelectedItem(localidade.getNumero());
                    jCBCNPJ.setEditable(true);
                    jCBCNPJ.setSelectedItem("");

                }
                dao.desconectar();
            } catch (BancoException e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_jCBContratadaActionPerformed

    private void jCBRuaCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBRuaCActionPerformed
        if (jCBRuaC.getSelectedItem().equals("Editar")) {
            jCBRuaC.setEditable(true);
            jCBRuaC.setSelectedItem("");
        }
    }//GEN-LAST:event_jCBRuaCActionPerformed

    private void jCBNumeroCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBNumeroCActionPerformed
        if (jCBNumeroC.getSelectedItem().equals("Editar")) {
            jCBNumeroC.setEditable(true);
            jCBNumeroC.setSelectedItem("");
        }
    }//GEN-LAST:event_jCBNumeroCActionPerformed

    private void jCBBairroCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBBairroCActionPerformed
        if (jCBBairroC.getSelectedItem().equals("Editar")) {
            jCBBairroC.setEditable(true);
            jCBBairroC.setSelectedItem("");
        }
    }//GEN-LAST:event_jCBBairroCActionPerformed

    private void jCBCidadeCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBCidadeCActionPerformed
        if (jCBCidadeC.getSelectedItem().equals("Editar")) {
            jCBCidadeC.setEditable(true);
            jCBCidadeC.setSelectedItem("");
        }
    }//GEN-LAST:event_jCBCidadeCActionPerformed

    private void jCBCNPJActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBCNPJActionPerformed
        if (jCBCNPJ.getSelectedItem().equals("Editar")) {
            jCBCNPJ.setEditable(true);
            jCBCNPJ.setSelectedItem("");
        }
    }//GEN-LAST:event_jCBCNPJActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        if (jCheckBox1.isSelected() == true) {
            txtContratante.setText(null);
            txtContratante.setEditable(false);
        } else {
            txtContratante.setEditable(true);
        }

    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        jBGerarParcelas.setEnabled(true);
        if (jCheckBox1.isSelected() == true) {
            try {
                Connection con = new Conexao().getConnection();
                HashMap parametros = new HashMap();
                parametros.put("CONTRATANTE", txtNomeAluno.getText().trim());
                parametros.put("CONTRATADA", jCBContratada.getSelectedItem().toString().trim());
                parametros.put("MENSALIDADE", txtValor.getText());
                parametros.put("MATERIAL", txtMaterial.getText());
                parametros.put("TOTAL", txtTotal.getText());
                parametros.put("PARCELAS", txtParcela.getText());
                parametros.put("PROFISSAO", txtProfissao.getText());
                parametros.put("RUA", txtLogradouro.getText());
                parametros.put("NUMERO", txtNumero.getText());
                parametros.put("BAIRRO", txtBairro.getText());
                parametros.put("CIDADE", txtCidade.getText());
                parametros.put("CPF", txtCPF.getText());
                parametros.put("RUAC", jCBRuaC.getSelectedItem().toString().trim());
                parametros.put("NUMEROC", jCBNumeroC.getSelectedItem().toString().trim());
                parametros.put("BAIRROC", jCBBairroC.getSelectedItem().toString().trim());
                parametros.put("CIDADEC", jCBCidadeC.getSelectedItem().toString().trim());
                parametros.put("CNPJC", jCBCNPJ.getSelectedItem().toString().trim());
                JasperPrint jp = JasperFillManager.fillReport("./relatorios/contratoAluno.jasper", parametros, con);

                JasperViewer jrv = new JasperViewer(jp, false);
                jrv.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                Connection con = new Conexao().getConnection();
                HashMap parametros = new HashMap();
                parametros.put("CONTRATANTE", txtContratante.getText().trim());
                parametros.put("CONTRATADA", jCBContratada.getSelectedItem().toString().trim());
                parametros.put("ALUNO", txtNomeAluno.getText().trim());
                parametros.put("MENSALIDADE", txtValor.getText());
                parametros.put("MATERIAL", txtMaterial.getText());
                parametros.put("TOTAL", txtTotal.getText());
                parametros.put("PARCELAS", txtParcela.getText());
                parametros.put("PROFISSAO", txtProfissao.getText());
                parametros.put("RUA", txtLogradouro.getText());
                parametros.put("NUMERO", txtNumero.getText());
                parametros.put("BAIRRO", txtBairro.getText());
                parametros.put("CIDADE", txtCidade.getText());
                parametros.put("CPF", txtCPF.getText());
                parametros.put("RUAC", jCBRuaC.getSelectedItem().toString().trim());
                parametros.put("NUMEROC", jCBNumeroC.getSelectedItem().toString().trim());
                parametros.put("BAIRROC", jCBBairroC.getSelectedItem().toString().trim());
                parametros.put("CIDADEC", jCBCidadeC.getSelectedItem().toString().trim());
                parametros.put("CNPJC", jCBCNPJ.getSelectedItem().toString().trim());
                JasperPrint jp = JasperFillManager.fillReport("./relatorios/contrato.jasper", parametros, con);

                JasperViewer jrv = new JasperViewer(jp, false);
                jrv.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        if (jRadioButton2.isSelected() == true) {
            jLabel6.setText(" ");
        }
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        if (jRadioButton1.isSelected() == true) {
            jLabel6.setText("** Nota: a primeira parcela não será gerada com mês subsequente a data informada.");
        }
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        try {
            if (daos.gerarCodigoCliente() == 1) {
                JOptionPane.showMessageDialog(this,
                        "O sistema não possuí um cliente cadastrado.",
                        "seeds",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                txtNomeAluno.setText(txtNomeAluno.getText().trim());
                apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
                nome.delete(0, apagar);
                nome.append(txtNomeAluno.getText());
                Cliente cliente = new Cliente();
                ClientesDAO daos = new ClientesDAO();
                cliente = daos.carregarCliente(nome);
                if (cliente.getNome().equals("nulo") || txtCodigoAluno.getText().equals("")) {
                    int selection = JOptionPane.showConfirmDialog(this,
                            "O Cliente informado não consta no banco!\n"
                            + "Deseja carregar todos os contratos assim mesmo?\n\n"
                            + "*Está operação pode ser demorada.\n",
                            "*O processo pode ser colocado em segundo plano.", JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE);
                    if (selection == JOptionPane.OK_OPTION) {
                        this.dispose();
                        ContratoDAO dao = new ContratoDAO();
                        List<Contrato> lista = dao.pesquisaContrato(nome, nomes);//Pra carregar todos os contratos [1]
                        new SplahContratoTodos(lista.size()).setVisible(true);
                        dao.desconectar();
                    }

                } else {
                    this.dispose();
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
                        ContratoDAO dao = new ContratoDAO();
                        if (numeroaluno.equals("") && numerocontrato.equals("")) {
                        } else {
                            parametroPesquisa = numerocontrato + "";
                            campo = numerocontrato;
                        }
                        List<Contrato> lista = dao.pesquisacontasReFiltroContrato(parametroPesquisa, campo);
                        new SplahContrato(lista.size()).setVisible(true);
                        lista = null;
                        dao.desconectar();
                    } catch (Exception e) {
                    }
                }
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    public void salvarContrato() {

        parcela = Integer.parseInt(txtParcela.getText());
        parcelas = parcela;
        parcelas++;
        parcela = 1;
        txtParcela.setText(String.valueOf(parcela));
        do {

            try {
                ContratoParcelaDAO dao = new ContratoParcelaDAO();
                CodigoContrato = dao.gerarCodigoContratoParcela();
                dao.desconectar();
            } catch (Exception f) {
                f.printStackTrace();
            }

            ContratoParcela venda = new ContratoParcela();
            venda.setCodigo(CodigoContrato);
            txtDatas.setText(dataContrato);
            //venda.setData(txtDatas.getText());
            if (jRadioButton2.isSelected() == true) {
                Mes++;//Mês aqui para acrescentar um mes na data inicial
            }
            //if (Mes > 9) {
            if (Mes > 12) {
                Ano = Ano + 1;
                Mes = 1;
            }
            //dataContrato = String.valueOf(Ano + "/" + Mes + "/" + Dia);
            // } else {
            //   dataContrato = String.valueOf(Ano + "/0" + Mes + "/" + Dia);
            //}

            if (Mes > 9) {
                if (Dia > 9) {
                    dataContrato = String.valueOf(Ano + "/" + Mes + "/" + Dia);
                    //JOptionPane.showMessageDialog(rootPane, dataContrato, "1", JOptionPane.ERROR_MESSAGE);
                } else {
                    dataContrato = String.valueOf(Ano + "/" + Mes + "/0" + Dia);
                    //JOptionPane.showMessageDialog(rootPane, dataContrato, "2", JOptionPane.ERROR_MESSAGE);
                }

            } else {
                if (Dia > 9) {
                    dataContrato = String.valueOf(Ano + "/0" + Mes + "/" + Dia);
                    //JOptionPane.showMessageDialog(rootPane, dataContrato, "3", JOptionPane.ERROR_MESSAGE);
                } else {
                    dataContrato = String.valueOf(Ano + "/0" + Mes + "/0" + Dia);
                    //JOptionPane.showMessageDialog(rootPane, dataContrato, "4", JOptionPane.ERROR_MESSAGE);
                }
            }

            txtDatas.setText(dataContrato);

            venda.setCodigo_contato(Integer.parseInt(txtCodigo.getText().trim()));//Ligar o contas a receber ao aluno posso add mais

            venda.setParcela(Integer.parseInt(txtParcela.getText()));

            String Valor = txtValor.getText();
            Valor = Valor.replace(".", "");
            Valor = Valor.replace(",", ".");
            venda.setValor(Float.parseFloat(Valor));
            venda.setData(txtDatas.getText());
            try {
                ContratoParcelaDAO dao = new ContratoParcelaDAO();
                dao.adicionarContratoParcela(venda);
                parcela++;
                txtParcela.setText(String.valueOf(parcela));
                dao.desconectar();
            } catch (Exception e) {
                e.printStackTrace();
            }


            try {
                ContasRDAO dao = new ContasRDAO();
                codigoR = dao.gerarCodigoContaR();
                dao.desconectar();
            } catch (Exception f) {
                f.printStackTrace();
            }
            ContasR conta = new ContasR();
            conta.setCodigo(codigoR);
            conta.setCodigo_turma(Integer.parseInt(txtTurmaCodigo.getText()));
            conta.setCodigo_cliente(Integer.parseInt(txtCodigoAluno.getText()));
            conta.setLocalidade(Integer.parseInt(txtCodigoLocalidade.getText()));
            conta.setParcela(Integer.parseInt(txtParcela.getText()) - 1);
            conta.setData_emissao("0000/00/00");
            //data = String.valueOf(ano + "/0" + mes + "/" + dia);
            conta.setData_pagamento(dataContrato);
            conta.setDesconto(0.0);

            Valor = txtValor.getText();
            Valor = Valor.replace(".", "");
            Valor = Valor.replace(",", ".");
            conta.setValor(Double.parseDouble(Valor));
            conta.setCodigo_contrato(Integer.parseInt(txtCodigo.getText()));

            try {
                ContasRDAO dao = new ContasRDAO();
                dao.adicionarContaR(conta);
                dao.desconectar();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (jRadioButton1.isSelected() == true) {
                Mes++;//Mes aqui para não acrescentar um mes a data inicial
            }
        } while (parcela != parcelas);
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
            } else {
                txtInstrutor.setText(funcionario.getNome());
                txtDia.requestFocus();
            }
            dao.desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Contratos().setVisible(true);
                } catch (BancoException ex) {
                    Logger.getLogger(Contratos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JButton jBGerarParcelas;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox jCBBairroC;
    private javax.swing.JComboBox jCBCNPJ;
    private javax.swing.JComboBox jCBCidadeC;
    private javax.swing.JComboBox jCBContratada;
    private javax.swing.JComboBox jCBNumeroC;
    private javax.swing.JComboBox jCBRuaC;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JPanel masterTable;
    private javax.swing.JSpinner txtAno;
    private javax.swing.JTextField txtBairro;
    private javax.swing.JTextField txtCPF;
    private javax.swing.JTextField txtCidade;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtCodigoAluno;
    private javax.swing.JTextField txtCodigoLocalidade;
    private javax.swing.JTextField txtContratante;
    private javax.swing.JTextField txtDatas;
    private javax.swing.JSpinner txtDia;
    private javax.swing.JTextField txtEstado;
    private javax.swing.JTextField txtInstrutor;
    private javax.swing.JTextField txtLogradouro;
    private javax.swing.JTextField txtMaterial;
    private javax.swing.JSpinner txtMes;
    private javax.swing.JTextField txtNascimento;
    private javax.swing.JTextField txtNomeAluno;
    private javax.swing.JTextField txtNumero;
    private javax.swing.JTextField txtParcela;
    private javax.swing.JTextField txtPesquisa;
    private javax.swing.JButton txtPesquisar;
    private javax.swing.JTextField txtProfissao;
    private javax.swing.JTextField txtTotal;
    private javax.swing.JTextField txtTurmaCodigo;
    private javax.swing.JTextField txtValor;
    // End of variables declaration//GEN-END:variables
}
