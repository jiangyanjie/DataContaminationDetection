package views.graphics;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import plugin.Plugin;
import plugin.PluginLoader;
import plugin.util.PluginMenuItem;
import plugin.util.PluginMenuItemFactory;
import strategies.ElevatorStrategy;
import observers.*;

public class ConfigView extends JFrame {

	private static final long serialVersionUID = 8164118974463460991L;
	private static final String POLICE = "Monaco";
	private static final int MIN_FLOOR_COUNT = 1;
	public static final int MAX_FLOOR_COUNT = 10;
	private static final int MIN_ELEVATOR_COUNT = 1;
	public static final int MAX_ELEVATOR_COUNT = 10;
	private static final int MIN_PERSON_COUNT = 1;
	private static final int MAX_PERSON_COUNT = 200;
	private static final int MIN_GROUP_COUNT = 0;
	private static final int MAX_GROUP_COUNT = 20;
	private static final int MIN_PERSON_PER_ELEVATOR_COUNT = 3;
	public static final int MAX_PERSON_PER_ELEVATOR_COUNT = 10;

	// |Nb etage: <slider>
	// |Nb ascenseur: <slider>
	// |Nb individu: <slider>
	// |Nb groupes: <slider>
	//
	private static Logger logger = Logger.getLogger("fr.unice.plugin.PluginLoader");

	private ElevatorStrategy elevatorStrategy = null;

	private JPanel jpanel_principal;
	private JPanel jpanel_floor_count;
	private JSlider jslider_floor_count;
	private JPanel jpanel_elevator_count;
	private JSlider jslider_elevator_count;
	private JPanel jpanel_person_per_elevator_count;
	private JSlider jslider_person_per_elevator_count;
	private JPanel jpanel_person_count;
	private JSlider jslider_person_count;
	private JPanel jpanel_group_count;
	private JSlider jslider_group_count;
	private JPanel jpanel_start_simulation;
	private JButton jbutton_start_simulation;

	private PluginMenuItemFactory pluginMenuItemFactory;
	private JMenuBar mb = new JMenuBar();
	private JMenu menuPlugins;
	private PluginLoader pluginLoader;

	public ConfigView() throws MalformedURLException {
		logger.setLevel(Level.OFF);
		
		this.setLocationByPlatform(true);
		this.setSize(600, 400);
		this.setTitle("Projet Java 2008 : Simulation de comportement d'ascenseurs - Tic/Tac/Viet");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		Container content_pane = this.getContentPane();

		// Les plugins seront dans le repertoire plugins
		// (chemin relatif au repertoire dans lequel java est lance).
		pluginLoader = new PluginLoader("plugins");

		// Chargement de tous les plugins places dans les URLs donnes ci-dessus.
		pluginLoader.loadPlugins();

		// Met le 1er plugin strategyElevator de la liste des plugins comme strategy par defaut
		Plugin[] plugins = pluginLoader.getPluginInstances(ElevatorStrategy.class);
		if (plugins.length != 0) {
			elevatorStrategy = (ElevatorStrategy)plugins[0];
		}
		// Construction de la barre de menus
		setJMenuBar(mb);

		// Construction du menu des plugins
		buildPluginMenu();

		buildReloadButton();

		this.jpanel_principal = new JPanel();

		this.jpanel_principal.setLayout(new BoxLayout(jpanel_principal, BoxLayout.PAGE_AXIS));
		{			
			// Cadre du choix du nombre d'etages
			this.jpanel_floor_count = new JPanel(new GridLayout(1,2));
			{	
				JLabel choose_floor_count = new JLabel("Nombre d'étages");
				choose_floor_count.setFont(new Font(POLICE, Font.BOLD, 13));
				jpanel_floor_count.add(choose_floor_count);

				JPanel panel = new JPanel();
				
				JLabel jlabel_floor_count = new JLabel(Integer.toString((int)MAX_FLOOR_COUNT/2));
				jlabel_floor_count.setFont(new Font(POLICE, Font.BOLD, 15));

				jslider_floor_count = new JSlider(MIN_FLOOR_COUNT, MAX_FLOOR_COUNT);
				jslider_floor_count.setValue((int)MAX_FLOOR_COUNT/2);
				jslider_floor_count.addChangeListener(new SliderUpdateObserver(jlabel_floor_count));
				
				panel.add(jslider_floor_count);
				panel.add(jlabel_floor_count);
				jpanel_floor_count.add(panel);
			}
			this.jpanel_principal.add(jpanel_floor_count);

			// Cadre du choix du nombre d'ascenseur
			this.jpanel_elevator_count = new JPanel(new GridLayout(1,2));
			{	
				JLabel choose_elevator_count = new JLabel("Nombre d'ascenseurs");
				choose_elevator_count.setFont(new Font(POLICE, Font.BOLD, 13));
				jpanel_elevator_count.add(choose_elevator_count);

				JPanel panel = new JPanel();
				
				JLabel jlabel_elevator_count = new JLabel(Integer.toString((int)MAX_ELEVATOR_COUNT/2));
				jlabel_elevator_count.setFont(new Font(POLICE, Font.BOLD, 15));

				jslider_elevator_count = new JSlider(MIN_ELEVATOR_COUNT, MAX_ELEVATOR_COUNT);
				jslider_elevator_count.setValue((int)MAX_ELEVATOR_COUNT/2);
				jslider_elevator_count.addChangeListener(new SliderUpdateObserver(jlabel_elevator_count));
				
				panel.add(jslider_elevator_count);
				panel.add(jlabel_elevator_count);
				jpanel_elevator_count.add(panel);
			}
			this.jpanel_principal.add(jpanel_elevator_count);

			// Cadre du choix du nombre d'ascenseur
			this.jpanel_person_per_elevator_count = new JPanel(new GridLayout(1,2));
			{	
				JLabel choose_person_per_elevator_count = new JLabel("Personnes max par ascenseur");
				choose_person_per_elevator_count.setFont(new Font(POLICE, Font.BOLD, 13));
				jpanel_person_per_elevator_count.add(choose_person_per_elevator_count);

				JPanel panel = new JPanel();
				
				JLabel jlabel_person_per_elevator_count = new JLabel(Integer.toString((int)MAX_PERSON_PER_ELEVATOR_COUNT/2));
				jlabel_person_per_elevator_count.setFont(new Font(POLICE, Font.BOLD, 15));

				jslider_person_per_elevator_count = new JSlider(MIN_PERSON_PER_ELEVATOR_COUNT, MAX_PERSON_PER_ELEVATOR_COUNT);
				jslider_person_per_elevator_count.setValue((int)MAX_ELEVATOR_COUNT/2);
				jslider_person_per_elevator_count.addChangeListener(new SliderUpdateObserver(jlabel_person_per_elevator_count));
				
				panel.add(jslider_person_per_elevator_count);
				panel.add(jlabel_person_per_elevator_count);
				jpanel_person_per_elevator_count.add(panel);
			}
			this.jpanel_principal.add(jpanel_person_per_elevator_count);

			// Cadre du choix du nombre d'individu
			this.jpanel_person_count = new JPanel(new GridLayout(1,2));
			{	
				JLabel choose_person_count = new JLabel("Nombre de personnes (total)");
				choose_person_count.setFont(new Font(POLICE, Font.BOLD, 13));
				jpanel_person_count.add(choose_person_count);

				JPanel panel = new JPanel();
				
				JLabel jlabel_person_count = new JLabel(Integer.toString((int)MAX_PERSON_COUNT/2));
				jlabel_person_count.setFont(new Font(POLICE, Font.BOLD, 15));

				jslider_person_count = new JSlider(MIN_PERSON_COUNT, MAX_PERSON_COUNT);
				jslider_person_count.setValue((int)MAX_PERSON_COUNT/2);
				jslider_person_count.addChangeListener(new SliderUpdateObserver(jlabel_person_count));
				
				panel.add(jslider_person_count);
				panel.add(jlabel_person_count);
				jpanel_person_count.add(panel);
			}
			this.jpanel_principal.add(jpanel_person_count);

			// Cadre du choix du nombre de groupes
			this.jpanel_group_count = new JPanel(new GridLayout(1,2));
			{	
				JLabel choose_group_count = new JLabel("Nombre de groupes (2 ou 3 personnes)");
				choose_group_count.setFont(new Font(POLICE, Font.BOLD, 13));
				jpanel_group_count.add(choose_group_count);

				JPanel panel = new JPanel();
				
				JLabel jlabel_group_count = new JLabel(Integer.toString((int)MAX_GROUP_COUNT/2));
				jlabel_group_count.setFont(new Font(POLICE, Font.BOLD, 15));
				
				jslider_group_count = new JSlider(MIN_GROUP_COUNT, MAX_GROUP_COUNT);
				jslider_group_count.setValue((int)MAX_GROUP_COUNT/2);
				jslider_group_count.addChangeListener(new SliderUpdateObserver(jlabel_group_count));
				
				panel.add(jslider_group_count);
				panel.add(jlabel_group_count);
				jpanel_group_count.add(panel);
			}
			this.jpanel_principal.add(jpanel_group_count);

			// Cadre du bouton "start simulation"
			this.jpanel_start_simulation = new JPanel();
			this.jpanel_start_simulation.setLayout(new BoxLayout(jpanel_start_simulation,BoxLayout.Y_AXIS));
			{
				jbutton_start_simulation = new JButton("Lancer la simulation", new ImageIcon("src/10.png"));
				jbutton_start_simulation.setAlignmentX(CENTER_ALIGNMENT);
				jbutton_start_simulation.setFont(new Font(POLICE, Font.BOLD, 20));
				jbutton_start_simulation.addActionListener(new StartSimulationObserver(this));
				jpanel_start_simulation.add(jbutton_start_simulation);
				setButtonStartEnabledOrNot();
			}
			this.jpanel_principal.add(jpanel_start_simulation);
		}
		content_pane.add(jpanel_principal);

		this.setResizable(true);
		this.setVisible(true);		
	}

	private void setButtonStartEnabledOrNot() {
		if(pluginLoader.getPluginInstances().length < 1) jbutton_start_simulation.setEnabled(false);
		else jbutton_start_simulation.setEnabled(true);
	}

	public ElevatorStrategy getElevatorStrategy() {
		return elevatorStrategy;
	}

	public int get_floor_count() {
		return jslider_floor_count.getValue();
	}

	public int get_elevator_count() {
		return jslider_elevator_count.getValue();
	}

	public int get_person_per_elevator_count() {
		return jslider_person_per_elevator_count.getValue();
	}

	public int get_person_count() {
		return jslider_person_count.getValue();
	}

	public int get_group_count() {
		return jslider_group_count.getValue();
	}


	private void buildReloadButton() {
		JMenu menuReload = new JMenu("Actions");		

		// Enleve les entrees precedentes s'il y en avait
		menuReload.removeAll();

		JMenuItem item = new JMenuItem("Recharger les comportements");
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pluginLoader.reloadPlugins();
				buildPluginMenu();
				setButtonStartEnabledOrNot();
			}
		});
		menuReload.add(item);

		item = new JMenuItem("Surprise");
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "« Si vous voulez connaître vos vrais amis,\nhabitez un cinquième étage sans ascenseur. »\n[Charles Morellet]", "Citation", JOptionPane.OK_OPTION);
			}
		});
		menuReload.add(item);

		mb.add(menuReload);
	}

	/**
	 * Construit les entres du menu lies aux plugins.
	 */
	private void buildPluginMenu() {
		menuPlugins = new JMenu("Choisir un comportement");

		// L'actionListener qui va ecouter les entrees du menu des plugins
		ActionListener listener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Met l'instance de plugin associee a l'entree du menu comme
				// strategy courante.
				elevatorStrategy = (ElevatorStrategy)((PluginMenuItem)e.getSource()).getPlugin();
				logger.info("Plugin choisi :" + elevatorStrategy);
			}
		};

		if (pluginMenuItemFactory == null) {
			pluginMenuItemFactory =
				new PluginMenuItemFactory(menuPlugins, pluginLoader, listener);
		}

		buildPluginMenuEntries();

		mb.add(menuPlugins);
	}

	private void buildPluginMenuEntries() {
		// Fait construire les entrees du menu des plugins
		pluginMenuItemFactory.buildMenu(ElevatorStrategy.class);
	}
	
}