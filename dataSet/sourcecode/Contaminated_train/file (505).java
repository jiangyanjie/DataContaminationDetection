/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.seeds;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author Guilherme
 */
public class ConfiguracoesServidor extends javax.swing.JDialog {

    int finalizar = 0;

    /**
     * Creates new form ConfiguracoesServidor
     */
    public ConfiguracoesServidor(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        txtPorta.setDocument(new OnlyNumberField(11));

        //Para Fechar Sistema Menu PopUp
        jPanel1.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent me) {
                //Verificando se o botão direito do mouse foi clicado  
                if ((me.getModifiers() & MouseEvent.BUTTON3_MASK) != 0) {
                    JPopupMenu menus = new JPopupMenu();
                    JMenuItem item = new JMenuItem("Clique-me para matar o processo no windows do java");
                    menus.add(item);

                    item.addActionListener(new ActionListener() {

                        public void actionPerformed(ActionEvent ae) {
                            //JOptionPane.showMessageDialog(null, "Fui clicado !");
                            kill("javaw.exe");
                            //System.exit(0);
                        }
                    });
                    menus.show(jPanel1, me.getX(), me.getY());
                }
            }
        });
    }

    public static boolean kill(String processo) {
        try {
            String line;
            Process p = Runtime.getRuntime().exec("tasklist.exe /fo csv /nh");
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = input.readLine()) != null) {
                if (!line.trim().equals("")) {
                    if (line.substring(1, line.indexOf("\"", 1)).equalsIgnoreCase(processo)) {
                        Runtime.getRuntime().exec("taskkill /F /IM " + line.substring(1, line.indexOf("\"", 1)));
                        return true;
                    }
                }
            }
            input.close();
        } catch (Exception err) {
            err.printStackTrace();
        }
        return false;
    }

    protected JRootPane createRootPane() {
        JRootPane rootPane = new JRootPane();
        KeyStroke stroke = KeyStroke.getKeyStroke("ESCAPE");
        Action actionListener = new AbstractAction() {

            public void actionPerformed(ActionEvent actionEvent) {
                ConfiguracoesServidor.this.dispose();
            }
        };
        InputMap inputMap = rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(stroke, "ESCAPE");
        rootPane.getActionMap().put("ESCAPE", actionListener);
        return rootPane;
    }

    public void Conectar() throws IOException {

        if (txtNomedaBasedeDados.getText().equals("")) {
            ConfiguracoesServidor.this.dispose();
        } else {
            String nomeDaBaseDeDados;
            nomeDaBaseDeDados = txtNomedaBasedeDados.getText().trim();
            FileWriter arq = new FileWriter("../seeds-java/basededados.txt");
            PrintWriter gravarArq = new PrintWriter(arq);
            gravarArq.printf(nomeDaBaseDeDados);
            arq.close();
            JOptionPane.showMessageDialog(rootPane,
                    "Sucesso!",
                    "Conexão configurada",
                    JOptionPane.INFORMATION_MESSAGE);
            if (finalizar == 1) {
                System.exit(0);
            }
            ConfiguracoesServidor.this.dispose();
        }
    }

    public void Conectar3() throws IOException {

        if (txtPorta.getText().equals("")) {
            ConfiguracoesServidor.this.dispose();
        } else {
            String nomeDaPorta;
            nomeDaPorta = txtPorta.getText().trim();
            FileWriter arq = new FileWriter("../seeds-java/porta.txt");
            PrintWriter gravarArq = new PrintWriter(arq);
            gravarArq.printf(nomeDaPorta);
            arq.close();
        }

    }

    public void Conectar2() throws IOException {

        if (txtEnderecoIPv4.getText().equals("")) {
            ConfiguracoesServidor.this.dispose();
        } else {
            String nomeDoEnderecoIPv4;
            nomeDoEnderecoIPv4 = txtEnderecoIPv4.getText().trim();
            FileWriter arq = new FileWriter("../seeds-java/ip.txt");
            PrintWriter gravarArq = new PrintWriter(arq);
            gravarArq.printf(nomeDoEnderecoIPv4);
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

        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        txtNomedaBasedeDados = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtEnderecoIPv4 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtPorta = new javax.swing.JFormattedTextField();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Configurações de conexão.");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/view-refresh.png"))); // NOI18N
        jButton1.setText("Atualizar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        txtNomedaBasedeDados.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtNomedaBasedeDados.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomedaBasedeDadosFocusLost(evt);
            }
        });
        txtNomedaBasedeDados.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNomedaBasedeDadosKeyTyped(evt);
            }
        });

        jLabel1.setText("Nome da base de dados: ");

        jLabel2.setText("Endereço IPv4:");

        txtEnderecoIPv4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel3.setText("Porta:");

        txtPorta.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 1, Short.MAX_VALUE)
                        .addComponent(txtNomedaBasedeDados, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtEnderecoIPv4)
                    .addComponent(txtPorta))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNomedaBasedeDados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtEnderecoIPv4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtPorta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/cancelar.png"))); // NOI18N
        jButton3.setText("Sair");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/entrar.png"))); // NOI18N
        jButton4.setText("Concluir e fechar o sistema.");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/buscar.png"))); // NOI18N
        jButton2.setText("Em uso");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jButton4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(59, 59, 59)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton1, jButton3, jButton4});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (txtNomedaBasedeDados.getText().contains(".")) {
            JOptionPane.showMessageDialog(rootPane,
                    "Não informar a extensão do arquivo.\n"
                    + "Informe apenas o nome da base de dados a ser utilizada.\n"
                    + "",
                    "Nome de arquivo inválido", JOptionPane.WARNING_MESSAGE);

            txtNomedaBasedeDados.setText(txtNomedaBasedeDados.getText().replace(".sql", ""));
            txtNomedaBasedeDados.setText(txtNomedaBasedeDados.getText().replace(".SQL", ""));
            txtNomedaBasedeDados.setText(txtNomedaBasedeDados.getText().replace(".sQL", ""));
            txtNomedaBasedeDados.setText(txtNomedaBasedeDados.getText().replace(".sQl", ""));
            txtNomedaBasedeDados.setText(txtNomedaBasedeDados.getText().replace(".sqL", ""));
            txtNomedaBasedeDados.setText(txtNomedaBasedeDados.getText().replace(".SqL", ""));
            txtNomedaBasedeDados.setText(txtNomedaBasedeDados.getText().replace(".Sql", ""));
            txtNomedaBasedeDados.setText(txtNomedaBasedeDados.getText().replace(".SQl", ""));

            txtNomedaBasedeDados.setText(txtNomedaBasedeDados.getText().replace(".txt", ""));
            txtNomedaBasedeDados.setText(txtNomedaBasedeDados.getText().replace(".TXT", ""));
            txtNomedaBasedeDados.setText(txtNomedaBasedeDados.getText().replace(".Txt", ""));
            txtNomedaBasedeDados.setText(txtNomedaBasedeDados.getText().replace(".TXt", ""));
            txtNomedaBasedeDados.setText(txtNomedaBasedeDados.getText().replace(".tXT", ""));
            txtNomedaBasedeDados.setText(txtNomedaBasedeDados.getText().replace(".txT", ""));
            txtNomedaBasedeDados.setText(txtNomedaBasedeDados.getText().replace(".tXt", ""));
            txtNomedaBasedeDados.setText(txtNomedaBasedeDados.getText().replace(".TxT", ""));

            txtNomedaBasedeDados.setText(txtNomedaBasedeDados.getText().replace(".doc", ""));
            txtNomedaBasedeDados.setText(txtNomedaBasedeDados.getText().replace(".DOC", ""));
            txtNomedaBasedeDados.setText(txtNomedaBasedeDados.getText().replace(".Doc", ""));
            txtNomedaBasedeDados.setText(txtNomedaBasedeDados.getText().replace(".doC", ""));
            txtNomedaBasedeDados.setText(txtNomedaBasedeDados.getText().replace(".Doc", ""));
            txtNomedaBasedeDados.setText(txtNomedaBasedeDados.getText().replace(".doC", ""));
            txtNomedaBasedeDados.setText(txtNomedaBasedeDados.getText().replace(".DoC", ""));
            txtNomedaBasedeDados.setText(txtNomedaBasedeDados.getText().replace(".dOc", ""));

            txtNomedaBasedeDados.setText(txtNomedaBasedeDados.getText().replace(".pdf", ""));
            txtNomedaBasedeDados.setText(txtNomedaBasedeDados.getText().replace(".PDF", ""));
            txtNomedaBasedeDados.setText(txtNomedaBasedeDados.getText().replace(".Pdf", ""));
            txtNomedaBasedeDados.setText(txtNomedaBasedeDados.getText().replace(".pdF", ""));
            txtNomedaBasedeDados.setText(txtNomedaBasedeDados.getText().replace(".Pdf", ""));
            txtNomedaBasedeDados.setText(txtNomedaBasedeDados.getText().replace(".PDf", ""));
            txtNomedaBasedeDados.setText(txtNomedaBasedeDados.getText().replace(".PdF", ""));
            txtNomedaBasedeDados.setText(txtNomedaBasedeDados.getText().replace(".PdF", ""));
            txtNomedaBasedeDados.setText(txtNomedaBasedeDados.getText().replace(".docx", ""));
            txtNomedaBasedeDados.setText(txtNomedaBasedeDados.getText().replace(".DOCX", ""));

        } else {
            try {
                Conectar3();
                Conectar2();
                Conectar();

            } catch (IOException ex) {
                Logger.getLogger(ConfiguracoesServidor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        String linha;
        String linha2;
        String linha3;
        try {
            FileReader arq = new FileReader("../seeds-java/basededados.txt");
            FileReader arq2 = new FileReader("../seeds-java/ip.txt");
            FileReader arq3 = new FileReader("../seeds-java/porta.txt");
            BufferedReader lerArq = new BufferedReader(arq);
            BufferedReader lerArq2 = new BufferedReader(arq2);
            BufferedReader lerArq3 = new BufferedReader(arq3);
            linha = lerArq.readLine(); // lê a primeira linha
            linha2 = lerArq2.readLine(); // lê a primeira linha
            linha3 = lerArq3.readLine(); // lê a primeira linha
            // a variável "linha" recebe o valor "null" quando o processo
            // de repetição atingir o final do arquivo texto
            while (linha != null) {
                JOptionPane.showMessageDialog(rootPane,
                        "Base de dados Mysql em uso: "
                        + "" + linha + " \n"
                        + "Endereço IPv4 em uso: "
                        + "" + linha2 + " \n"
                        + "Porta em uso: "
                        + "" + linha3 + " ",
                        "", JOptionPane.INFORMATION_MESSAGE);
                txtNomedaBasedeDados.setText(linha);
                txtEnderecoIPv4.setText(linha2);
                txtPorta.setText(linha3);
                linha = lerArq.readLine(); // lê da segunda até a última linha
            }
            arq.close();
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n",
                    e.getMessage());
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        ConfiguracoesServidor.this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        if (txtNomedaBasedeDados.getText().contains(".")) {
            JOptionPane.showMessageDialog(rootPane,
                    "Não informar a extensão do arquivo.\n"
                    + "Informe apenas o nome da base de dados a ser utilizada.\n"
                    + "",
                    "Nome de arquivo inválido", JOptionPane.WARNING_MESSAGE);

            txtNomedaBasedeDados.setText(txtNomedaBasedeDados.getText().replace(".sql", ""));
            txtNomedaBasedeDados.setText(txtNomedaBasedeDados.getText().replace(".SQL", ""));
            txtNomedaBasedeDados.setText(txtNomedaBasedeDados.getText().replace(".sQL", ""));
            txtNomedaBasedeDados.setText(txtNomedaBasedeDados.getText().replace(".sQl", ""));
            txtNomedaBasedeDados.setText(txtNomedaBasedeDados.getText().replace(".sqL", ""));
            txtNomedaBasedeDados.setText(txtNomedaBasedeDados.getText().replace(".SqL", ""));
            txtNomedaBasedeDados.setText(txtNomedaBasedeDados.getText().replace(".Sql", ""));
            txtNomedaBasedeDados.setText(txtNomedaBasedeDados.getText().replace(".SQl", ""));

            txtNomedaBasedeDados.setText(txtNomedaBasedeDados.getText().replace(".txt", ""));
            txtNomedaBasedeDados.setText(txtNomedaBasedeDados.getText().replace(".TXT", ""));
            txtNomedaBasedeDados.setText(txtNomedaBasedeDados.getText().replace(".Txt", ""));
            txtNomedaBasedeDados.setText(txtNomedaBasedeDados.getText().replace(".TXt", ""));
            txtNomedaBasedeDados.setText(txtNomedaBasedeDados.getText().replace(".tXT", ""));
            txtNomedaBasedeDados.setText(txtNomedaBasedeDados.getText().replace(".txT", ""));
            txtNomedaBasedeDados.setText(txtNomedaBasedeDados.getText().replace(".tXt", ""));
            txtNomedaBasedeDados.setText(txtNomedaBasedeDados.getText().replace(".TxT", ""));

            txtNomedaBasedeDados.setText(txtNomedaBasedeDados.getText().replace(".doc", ""));
            txtNomedaBasedeDados.setText(txtNomedaBasedeDados.getText().replace(".DOC", ""));
            txtNomedaBasedeDados.setText(txtNomedaBasedeDados.getText().replace(".Doc", ""));
            txtNomedaBasedeDados.setText(txtNomedaBasedeDados.getText().replace(".doC", ""));
            txtNomedaBasedeDados.setText(txtNomedaBasedeDados.getText().replace(".Doc", ""));
            txtNomedaBasedeDados.setText(txtNomedaBasedeDados.getText().replace(".doC", ""));
            txtNomedaBasedeDados.setText(txtNomedaBasedeDados.getText().replace(".DoC", ""));
            txtNomedaBasedeDados.setText(txtNomedaBasedeDados.getText().replace(".dOc", ""));

            txtNomedaBasedeDados.setText(txtNomedaBasedeDados.getText().replace(".pdf", ""));
            txtNomedaBasedeDados.setText(txtNomedaBasedeDados.getText().replace(".PDF", ""));
            txtNomedaBasedeDados.setText(txtNomedaBasedeDados.getText().replace(".Pdf", ""));
            txtNomedaBasedeDados.setText(txtNomedaBasedeDados.getText().replace(".pdF", ""));
            txtNomedaBasedeDados.setText(txtNomedaBasedeDados.getText().replace(".Pdf", ""));
            txtNomedaBasedeDados.setText(txtNomedaBasedeDados.getText().replace(".PDf", ""));
            txtNomedaBasedeDados.setText(txtNomedaBasedeDados.getText().replace(".PdF", ""));
            txtNomedaBasedeDados.setText(txtNomedaBasedeDados.getText().replace(".PdF", ""));
            txtNomedaBasedeDados.setText(txtNomedaBasedeDados.getText().replace(".docx", ""));
            txtNomedaBasedeDados.setText(txtNomedaBasedeDados.getText().replace(".DOCX", ""));

            txtEnderecoIPv4.setText(txtEnderecoIPv4.getText().replace("..", "."));
            txtPorta.setText(txtPorta.getText().replace(":", ""));

        } else {
            try {
                finalizar = 1;
                Conectar3();
                Conectar2();
                Conectar();
            } catch (IOException ex) {
                Logger.getLogger(ConfiguracoesServidor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void txtNomedaBasedeDadosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomedaBasedeDadosKeyTyped
        txtNomedaBasedeDados.setText(txtNomedaBasedeDados.getText().toString().toLowerCase());
    }//GEN-LAST:event_txtNomedaBasedeDadosKeyTyped

    private void txtNomedaBasedeDadosFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomedaBasedeDadosFocusLost
        txtNomedaBasedeDados.setText(txtNomedaBasedeDados.getText().toString().toLowerCase());
    }//GEN-LAST:event_txtNomedaBasedeDadosFocusLost

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ConfiguracoesServidor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ConfiguracoesServidor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ConfiguracoesServidor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ConfiguracoesServidor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the dialog
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                ConfiguracoesServidor dialog = new ConfiguracoesServidor(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                //IF VAI PERMITIR ABRIR SO UM FRAME POR VEZ
                //if(dialog.isDisplayable() == false){
                dialog.setVisible(true);
                //}
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField txtEnderecoIPv4;
    private javax.swing.JTextField txtNomedaBasedeDados;
    private javax.swing.JFormattedTextField txtPorta;
    // End of variables declaration//GEN-END:variables
}
