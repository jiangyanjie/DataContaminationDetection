package br.com.gstok.configuracao;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class ConfPersistenciaGUI extends JDialog {

    private JLabel lbTitulo, lbHost, lbUsuario, lbSenha, lbPorta, lbAviso, lbCampos;
    private JTextField tfHost, tfUsuario;
    private JPanel pnConfigurar, pnReiniciar;
    private JPasswordField pfSenha;
    private JSpinner snPorta;
    private JButton btSalvar, btLimpar, btBackup, btApagar, btCancelar;

    public ConfPersistenciaGUI() {
        iniciarComponentes();
        definirEventos();
    }

    private void iniciarComponentes() {

        this.setPreferredSize(new Dimension(452, 357));
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setTitle("GstoK - Configurar persistência");
        this.setResizable(false);
        this.setModal(true);
        //this.setIconImage(getToolkit().getImage(this.getClass().getResource("/br/org/gstok/imagens/CadCidades.png")));
        this.getContentPane().setLayout(null);

        lbTitulo = new JLabel();
        lbTitulo.setText("Configurar Persistência");
        lbTitulo.setForeground(new Color(0, 90, 0));
        lbTitulo.setFont(new Font("Lucida Sans", 1, 17));
        lbTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lbTitulo.setHorizontalTextPosition(SwingConstants.CENTER);
        lbTitulo.setVerticalAlignment(SwingConstants.CENTER);
        lbTitulo.setVerticalTextPosition(SwingConstants.CENTER);
        lbTitulo.setBounds(12, 12, 426, 21);
        this.getContentPane().add(lbTitulo);

        pnConfigurar = new JPanel();
        pnConfigurar.setBorder(new TitledBorder(null, "Configurar Banco", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Lucida Sans", 0, 13), new Color(0, 0, 0)));
        pnConfigurar.setLayout(null);
        pnConfigurar.setBounds(12, 39, 426, 121);
        this.getContentPane().add(pnConfigurar);

        lbHost = new JLabel();
        lbHost.setText("Host:");
        lbHost.setForeground(new Color(0, 0, 0));
        lbHost.setFont(new Font("Lucida Sans", 0, 12));
        lbHost.setHorizontalAlignment(SwingConstants.RIGHT);
        lbHost.setHorizontalTextPosition(SwingConstants.CENTER);
        lbHost.setVerticalAlignment(SwingConstants.CENTER);
        lbHost.setVerticalTextPosition(SwingConstants.CENTER);
        lbHost.setBounds(18, 21, 52, 18);
        pnConfigurar.add(lbHost);

        tfHost = new JTextField();
        tfHost.setBackground(new Color(255, 255, 255));
        tfHost.setForeground(new Color(0, 0, 0));
        tfHost.setFont(new Font("Lucida Sans", 0, 12));
        tfHost.setToolTipText("Digite o hostname do banco de dados");
        tfHost.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        tfHost.setEditable(true);
        tfHost.setEnabled(true);
        tfHost.requestFocus();
        tfHost.setHorizontalAlignment(JTextField.LEFT);
        tfHost.setBounds(82, 21, 166, 20);
        pnConfigurar.add(tfHost);

        lbUsuario = new JLabel();
        lbUsuario.setText("Usuário:");
        lbUsuario.setForeground(new Color(0, 0, 0));
        lbUsuario.setFont(new Font("Lucida Sans", 0, 12));
        lbUsuario.setHorizontalAlignment(SwingConstants.RIGHT);
        lbUsuario.setHorizontalTextPosition(SwingConstants.CENTER);
        lbUsuario.setVerticalAlignment(SwingConstants.CENTER);
        lbUsuario.setVerticalTextPosition(SwingConstants.CENTER);
        lbUsuario.setBounds(18, 53, 52, 18);
        pnConfigurar.add(lbUsuario);

        tfUsuario = new JTextField();
        tfUsuario.setBackground(new Color(255, 255, 255));
        tfUsuario.setForeground(new Color(0, 0, 0));
        tfUsuario.setFont(new Font("Lucida Sans", 0, 12));
        tfUsuario.setToolTipText("Digite o nome de usuário do banco de dados");
        tfUsuario.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        tfUsuario.setEditable(true);
        tfUsuario.setEnabled(true);
        tfUsuario.setHorizontalAlignment(JTextField.LEFT);
        tfUsuario.setBounds(82, 53, 166, 20);
        pnConfigurar.add(tfUsuario);

        lbSenha = new JLabel();
        lbSenha.setText("Senha:");
        lbSenha.setForeground(new Color(0, 0, 0));
        lbSenha.setFont(new Font("Lucida Sans", 0, 12));
        lbSenha.setHorizontalAlignment(SwingConstants.RIGHT);
        lbSenha.setHorizontalTextPosition(SwingConstants.CENTER);
        lbSenha.setVerticalAlignment(SwingConstants.CENTER);
        lbSenha.setVerticalTextPosition(SwingConstants.CENTER);
        lbSenha.setBounds(18, 85, 52, 18);
        pnConfigurar.add(lbSenha);

        pfSenha = new JPasswordField();
        pfSenha.setBackground(new Color(255, 255, 255));
        pfSenha.setForeground(new Color(0, 0, 0));
        pfSenha.setFont(new Font("Lucida Sans", 0, 12));
        pfSenha.setEchoChar('*');
        pfSenha.setToolTipText("Digite a senha de usuário do banco de dados");
        pfSenha.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        pfSenha.setEditable(true);
        pfSenha.setEnabled(true);
        pfSenha.setHorizontalAlignment(JPasswordField.LEFT);
        pfSenha.setBounds(82, 85, 166, 20);
        pnConfigurar.add(pfSenha);

        lbPorta = new JLabel();
        lbPorta.setText("Porta:");
        lbPorta.setForeground(new Color(0, 0, 0));
        lbPorta.setFont(new Font("Lucida Sans", 0, 12));
        lbPorta.setHorizontalAlignment(SwingConstants.LEFT);
        lbPorta.setHorizontalTextPosition(SwingConstants.CENTER);
        lbPorta.setVerticalAlignment(SwingConstants.CENTER);
        lbPorta.setVerticalTextPosition(SwingConstants.CENTER);
        lbPorta.setBounds(260, 21, 52, 18);
        pnConfigurar.add(lbPorta);

        snPorta = new JSpinner();
        snPorta.setBackground(new Color(255, 255, 255));
        snPorta.setForeground(new Color(0, 0, 0));
        snPorta.setToolTipText("Digite ou selecione a porta do banco de dados.\nPadrão: 3306");
        snPorta.setFont(new Font("Lucida Sans", 0, 12));
        snPorta.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        snPorta.setModel(new SpinnerNumberModel(3306, 0, 65536, 1));
        snPorta.setEnabled(true);
        snPorta.setBounds(311, 21, 97, 20);
        pnConfigurar.add(snPorta);

        btSalvar = new JButton();
        btSalvar.setFont(new Font("Lucida Sans", 0, 12));
        btSalvar.setForeground(new Color(0, 0, 255));
        btSalvar.setText("Salvar");
        btSalvar.setToolTipText("Salva as alterações");
        btSalvar.setMnemonic('S');
        // btSalvar.setIcon(new ImageIcon(getClass().getResource("/br/org/gstok/imagens/salvar.png")));
        btSalvar.setHorizontalAlignment(SwingConstants.CENTER);
        btSalvar.setHorizontalTextPosition(SwingConstants.CENTER);
        btSalvar.setVerticalAlignment(SwingConstants.CENTER);
        btSalvar.setVerticalTextPosition(SwingConstants.CENTER);
        btSalvar.setEnabled(true);
        btSalvar.setBounds(254, 53, 154, 20);
        pnConfigurar.add(btSalvar);

        btLimpar = new JButton();
        btLimpar.setFont(new Font("Lucida Sans", 0, 12));
        btLimpar.setForeground(new Color(0, 0, 255));
        btLimpar.setText("Limpar");
        btLimpar.setToolTipText("Salva as alterações");
        btLimpar.setMnemonic('L');
        // btLimpar.setIcon(new ImageIcon(getClass().getResource("/br/org/gstok/imagens/salvar.png")));
        btLimpar.setHorizontalAlignment(SwingConstants.CENTER);
        btLimpar.setHorizontalTextPosition(SwingConstants.CENTER);
        btLimpar.setVerticalAlignment(SwingConstants.CENTER);
        btLimpar.setVerticalTextPosition(SwingConstants.CENTER);
        btLimpar.setEnabled(true);
        btLimpar.setBounds(254, 85, 154, 20);
        pnConfigurar.add(btLimpar);

        pnReiniciar = new JPanel();
        pnReiniciar.setBorder(new TitledBorder(null, "Reiniciar Banco", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Lucida Sans", 0, 13), new Color(0, 0, 0)));
        pnReiniciar.setLayout(null);
        pnReiniciar.setBounds(12, 166, 426, 147);
        this.getContentPane().add(pnReiniciar);

        lbAviso = new JLabel();
        lbAviso.setText("<html><p>ATENÇÃO: As opções abaixo irão apagar o banco de dados!</p><p>Faça backup caso precise dos dados novamente.</p></html>");
        lbAviso.setForeground(new Color(0, 0, 0));
        lbAviso.setFont(new Font("Lucida Sans", 0, 12));
        lbAviso.setHorizontalAlignment(SwingConstants.LEFT);
        lbAviso.setHorizontalTextPosition(SwingConstants.CENTER);
        lbAviso.setVerticalAlignment(SwingConstants.TOP);
        lbAviso.setVerticalTextPosition(SwingConstants.TOP);
        lbAviso.setBounds(18, 30, 390, 37);
        pnReiniciar.add(lbAviso);

        btApagar = new JButton();
        btApagar.setFont(new Font("Lucida Sans", 0, 12));
        btApagar.setForeground(new Color(0, 0, 255));
        btApagar.setText("Apagar dados e reiniciar o banco de dados");
        btApagar.setToolTipText("Salva as alterações");
        btApagar.setMnemonic('L');
        // btApagar.setIcon(new ImageIcon(getClass().getResource("/br/org/gstok/imagens/salvar.png")));
        btApagar.setHorizontalAlignment(SwingConstants.CENTER);
        btApagar.setHorizontalTextPosition(SwingConstants.CENTER);
        btApagar.setVerticalAlignment(SwingConstants.CENTER);
        btApagar.setVerticalTextPosition(SwingConstants.CENTER);
        btApagar.setEnabled(true);
        btApagar.setBounds(18, 73, 390, 20);
        pnReiniciar.add(btApagar);

        btBackup = new JButton();
        btBackup.setFont(new Font("Lucida Sans", 0, 12));
        btBackup.setForeground(new Color(0, 0, 255));
        btBackup.setText("Fazer backup e reiniciar o banco de dados");
        btBackup.setToolTipText("Salva as alterações");
        btBackup.setMnemonic('L');
        // btBackup.setIcon(new ImageIcon(getClass().getResource("/br/org/gstok/imagens/salvar.png")));
        btBackup.setHorizontalAlignment(SwingConstants.CENTER);
        btBackup.setHorizontalTextPosition(SwingConstants.CENTER);
        btBackup.setVerticalAlignment(SwingConstants.CENTER);
        btBackup.setVerticalTextPosition(SwingConstants.CENTER);
        btBackup.setEnabled(true);
        btBackup.setBounds(18, 104, 390, 20);
        pnReiniciar.add(btBackup);

        btCancelar = new JButton();
        btCancelar.setFont(new Font("Lucida Sans", 0, 12));
        btCancelar.setForeground(new Color(0, 0, 255));
        btCancelar.setText("Cancelar");
        btCancelar.setToolTipText("Salva as alterações");
        btCancelar.setMnemonic('L');
        // btCancelar.setIcon(new ImageIcon(getClass().getResource("/br/org/gstok/imagens/salvar.png")));
        btCancelar.setHorizontalAlignment(SwingConstants.CENTER);
        btCancelar.setHorizontalTextPosition(SwingConstants.CENTER);
        btCancelar.setVerticalAlignment(SwingConstants.CENTER);
        btCancelar.setVerticalTextPosition(SwingConstants.CENTER);
        btCancelar.setEnabled(true);
        btCancelar.setBounds(301, 319, 137, 25);
        this.getContentPane().add(btCancelar);

        lbCampos = new JLabel();
        lbCampos.setText("");
        lbCampos.setForeground(new Color(255, 0, 0));
        lbCampos.setFont(new Font("Lucida Sans", 1, 12));
        lbCampos.setHorizontalAlignment(SwingConstants.LEFT);
        lbCampos.setHorizontalTextPosition(SwingConstants.CENTER);
        lbCampos.setVerticalAlignment(SwingConstants.CENTER);
        lbCampos.setVerticalTextPosition(SwingConstants.CENTER);
        lbCampos.setBounds(12, 319, 300, 20);
        lbCampos.setVisible(false);
        this.getContentPane().add(lbCampos);

        this.pack();

    }

    private void definirEventos() {

        pfSenha.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {

                tamanhoSenha(pfSenha.getPassword());
            }
        });

        btCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                dispose();
            }
        });

        btLimpar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                limparCampos();
            }
        });

        btSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                desabilitarCampos();
                tamanhoSenha(pfSenha.getPassword());

                if (isValido()) {
                    ConfigurarPersistencia();
                } else {
                    habilitarCampos();
                }
            }
        });
    }

    private void tamanhoSenha(char[] senha) {

        if (senha.length < 6) {

            pfSenha.setBorder(new LineBorder(new Color(255, 0, 0), 1, true));
            pfSenha.requestFocus();
            lbCampos.setText("Digite uma senha com no mínimo 6 caractes.");
            lbCampos.setVisible(true);
            habilitarCampos();
        } else {

            pfSenha.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
            lbCampos.setText("");
            lbCampos.setVisible(false);
        }
    }

    private void limparCampos() {

        tfHost.setText("");
        tfUsuario.setText("");
        pfSenha.setText("");
        snPorta.setValue(3306);
    }

    private void desabilitarCampos() {

        tfHost.setEnabled(false);
        tfUsuario.setEnabled(false);
        pfSenha.setEnabled(false);
        snPorta.setEnabled(false);
    }

    private boolean isValido() {

        if ((!tfHost.getText().equals("")) && (!tfUsuario.getText().equals("")) && (pfSenha.getPassword().length != 0) && (!snPorta.getValue().equals(""))) {

            return true;
        } else {

            lbCampos.setText("Os campos em vermelho são obrigatórios!");
            lbCampos.setVisible(true);

            if (tfHost.getText().equals("")) {
                tfHost.setBorder(new LineBorder(new Color(255, 0, 0), 1, true));
            }
            if (tfUsuario.getText().equals("")) {
                tfUsuario.setBorder(new LineBorder(new Color(255, 0, 0), 1, true));
            }
            if (pfSenha.getPassword().length == 0) {
                pfSenha.setBorder(new LineBorder(new Color(255, 0, 0), 1, true));
            }

            return false;
        }
    }

    private void ConfigurarPersistencia() {

        if (!leitorXML().isEmpty()) {

            novaConfiguracao(leitorXML());
        } else {

            JOptionPane.showMessageDialog(this, "O arquivo de configuração está vazio.", "ERRO", JOptionPane.ERROR_MESSAGE, null);
        }
    }

    private void habilitarCampos() {

        tfHost.setEnabled(true);
        tfUsuario.setEnabled(true);
        pfSenha.setEnabled(true);
        snPorta.setEnabled(true);
    }

    private String leitorXML() {

        String arquivoXML = "";

        try {

            File arquivo = new File(getClass().getResource("/br/com/gstok/arquivos/hibernate.cfg.xml").toURI());
            Scanner leitor = new Scanner(arquivo);
            leitor.useDelimiter("\n");

            while (leitor.hasNext()) {

                String arqAtual = leitor.next() + "\n";
                arquivoXML += arqAtual;
            }

        } catch (FileNotFoundException ex) {

            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Não foi possível encontar o arquivo de configuração", "ERRO", JOptionPane.ERROR_MESSAGE, null);
        } catch (URISyntaxException ex) {

            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "O caminho para o arquivo de configuração é inválido!", "ERRO", JOptionPane.ERROR_MESSAGE, null);
        }

        return arquivoXML;

    }

    private void novaConfiguracao(String antigaConfiguracao) {

        String host = tfHost.getText();
        String porta = snPorta.getValue().toString();
        String usuario = tfUsuario.getText();
        String senha = pfSenha.getText();
        antigaConfiguracao = leitorXML();

        gravarXML(host, porta, usuario, senha, antigaConfiguracao);
    }

    private void gravarXML(String host, String porta, String usuario, String senha, String antigaConfiguracao) {

        String xmlArquivo = "";

        System.out.println("AntigaConfiguracao:\n\n" + antigaConfiguracao);
        Scanner gravador = new Scanner(antigaConfiguracao);
        gravador.useDelimiter("\n");

        while (gravador.hasNext()) {

            String novoArquivo = gravador.next() + "\n";

            novoArquivo = novoArquivo.replace("url\">jdbc:mysql://localhost:3306/aluno</property>", "url\">jdbc:mysql://" + host + ":" + porta + "/aluno</property>");
            novoArquivo = novoArquivo.replace("username\">root</property>", "username\">" + usuario + "</property>");
            novoArquivo = novoArquivo.replace("password\">Tah038</property>", "password\">" + senha + "</property>");
            xmlArquivo += novoArquivo;
        }

        System.out.println("\nNovaConfiguracao:\n\n" + xmlArquivo);
    }
}