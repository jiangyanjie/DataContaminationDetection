package Vista;


import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import Aplicacio.ControladorFacturarComanda;
import Aplicacio.ControladorGenerarIncidencies;
import Aplicacio.ControladorLogin;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class ConsultarIncidencies extends JFrame {

	private JPanel contentPane;

	private JButton btnComandesMaiPreparades;
	private JButton btnComandesRetornadesNoReposades;
	private JButton btnComandesPrepNoCobrades;
	private ControladorLogin controladorLogin;
	private ControladorFacturarComanda controladorFacturacio;
	private String idEmpleat;
	private ControladorGenerarIncidencies controladorIncidencies;


	public ConsultarIncidencies(ControladorLogin controladorLogin) {
		try {
			controladorIncidencies = new ControladorGenerarIncidencies();
			controladorFacturacio = new ControladorFacturarComanda();
			this.controladorLogin = controladorLogin;
			this.idEmpleat = controladorLogin.getIdEmpleat();

		} catch (Exception e) {
			tirarError(e.getMessage());
		}

		setTitle("Incidencies");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnComandesMaiPreparades = new JButton(
				"Consultar Comandes Mai Preparades");
		btnComandesMaiPreparades.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Llistat_incidencies llistat = new Llistat_incidencies(
							"Comandes mai preparades", controladorIncidencies
									.obtenirIncidenciesComandesMaiPreparades());
					llistat.setVisible(true);
				} catch (Exception e) {
					tirarError(e.getMessage());
				}
			}
		});
		btnComandesMaiPreparades.setBounds(77, 33, 320, 40);
		contentPane.add(btnComandesMaiPreparades);

		btnComandesRetornadesNoReposades = new JButton(
				"Consultar Comandes Retornades Sense Reposar");
		btnComandesRetornadesNoReposades
				.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							Llistat_incidencies llistat = new Llistat_incidencies(
									"Comandes retornades i no reposades",
									controladorIncidencies
											.obtenirIncidenciesComandesRetornadesNoReposades());

							llistat.setVisible(true);
						} catch (Exception e1) {
							tirarError(e1.getMessage());
						}
					}
				});
		btnComandesRetornadesNoReposades.setBounds(77, 84, 320, 40);
		contentPane.add(btnComandesRetornadesNoReposades);

		btnComandesPrepNoCobrades = new JButton(
				"Consultar Comandes Preparades Sense Cobrar");
		btnComandesPrepNoCobrades.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					Llistat_incidencies llistat = new Llistat_incidencies(
							"Comandes no cobrades",
							controladorIncidencies
									.obtenirIncidenciesComandesPreparadesNoCobrades());
					llistat.setVisible(true);
				} catch (Exception e1) {
					tirarError(e1.getMessage());
				}

			}
		});
		btnComandesPrepNoCobrades.setBounds(77, 135, 320, 40);
		contentPane.add(btnComandesPrepNoCobrades);
		
		JButton btnTornarEnrere = new JButton("Tornar Enrere");
		btnTornarEnrere.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					tornarEnrere();
				} catch (Exception e1) {
					tirarError(e1.getMessage());
				}
			}
		});
		btnTornarEnrere.setBounds(253, 227, 144, 23);
		contentPane.add(btnTornarEnrere);
	}
	// Manu
		public void tornarALogin() {
			Login login = new Login();
			login.setVisible(true);
			this.dispose();
		}

		// Manu
		public void tirarError(String missatge) {
			JOptionPane.showMessageDialog(new JFrame(), missatge, "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		
		//Manu
		public void tornarEnrere() throws Exception{
			this.dispose();
		}
}
