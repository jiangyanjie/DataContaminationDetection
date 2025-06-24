package com.github.assisstion.ModulePack.logging;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public abstract class AbstractConsoleFrame extends JFrame{

	private static final long serialVersionUID = -3573978323215074454L;
	protected static final String DEFAULT_SUBMIT_BUTTON_TEXT = "Submit";
	protected static final String DEFAULT_LOGGER_NAME = "main";

	private JPanel contentPane;
	protected JTextField textField;
	protected JButton submitButton;
	protected Logger log;

	public AbstractConsoleFrame(){
		this(Logger.getLogger(DEFAULT_LOGGER_NAME));
	}

	public AbstractConsoleFrame(Logger logger){
		log = logger;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		LoggerPane loggerPane = new LoggerPane(log, false);
		contentPane.add(loggerPane);

		JPanel panel = new JPanel();
		loggerPane.add(panel, BorderLayout.SOUTH);

		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					submitButton.doClick();
				}
			}
		});
		panel.add(textField);
		textField.setColumns(25);

		submitButton = new JButton(getSubmitButtonText());
		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String s = textField.getText();
				process(s);
				textField.setText("");
			}
		});
		panel.add(submitButton);
	}

	protected String getSubmitButtonText(){
		return DEFAULT_SUBMIT_BUTTON_TEXT;
	}

	protected abstract void process(String s);
}
