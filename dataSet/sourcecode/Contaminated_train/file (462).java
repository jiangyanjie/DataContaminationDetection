package view.standardView.person;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import model.standardModel.movie.Movie;
import model.standardModel.person.Actor;
import model.standardModel.person.Award;
import presenter.Presenter;
import presenter.person.ActorPresenter;
import view.iView.person.ActorViewInterface;
import view.standardView.StandardView;

/**
 * Shows the gui for an actor.
 * 
 * @author danzer
 * 
 */
public class ActorView extends StandardView implements ActorViewInterface {

    // labels
    JLabel lName;
    JLabel lForename;
    JLabel lFamilyname;
    JLabel lNationality;
    JLabel lBirthday;
    JLabel lMovie;
    JLabel lAward;
    JLabel lTitle;

    // textfields
    JTextField tfName;
    JTextField tfForename;
    JTextField tfFamilyname;
    JTextField tfNationality;
    JTextField tfBirthday;

    // textareas
    JTextArea taMovie;
    JTextArea taAward;

    // scrollpanes
    JScrollPane spMovie;
    JScrollPane spAward;

    // buttons
    JButton bSave;
    JButton bReset;
    JButton bBack;
    JButton bAdd;
    JButton bEdit;
    JButton bDelete;

    // old actor
    Actor actorOld;
    String moviesOld;
    String awardsOld;

    // table
    JTable tActors;
    DefaultTableModel tmActors;
    String cActors[];
    String dmActors[][];

    /**
     * Initialize all the elements (labels, textfield, textareas) in the frame.
     * 
     * @param title
     *            title in frame
     * @param size
     *            font size of title
     * @param actor
     *            selected actor
     * @param editable
     *            indicates whether the textfields and textareas are editable
     */
    private void initElements(String title, int size, Actor actor,
	    boolean editable) {

	// labels
	lName = new JLabel("Name");
	lForename = new JLabel("Vorname");
	lFamilyname = new JLabel("Nachname");
	lNationality = new JLabel("Nationalität");
	lBirthday = new JLabel("Geburtstag");
	lMovie = new JLabel("Filme");
	lAward = new JLabel("Auszeichnungen");

	// title in frame
	lTitle = new JLabel(title);
	// settings
	lTitle.setFont(new Font("Serif", Font.BOLD, size));

	if (actor != null) {
	    tfName = new JTextField(actor.getName(), 15);
	    tfForename = new JTextField(actor.getForename(), 15);
	    tfFamilyname = new JTextField(actor.getFamilyname(), 15);
	    tfNationality = new JTextField(actor.getNationality(), 15);
	    tfBirthday = new JTextField(actor.getBirthday(), 15);
	    // there can be more movies
	    taMovie = new JTextArea(5, 15);
	    if (actor.getMovies() != null) {
		firstIteration = true;
		for (Movie movie : actor.getMovies()) {
		    if (firstIteration) {
			taMovie.setText(movie.getTitle() + " ("
				+ movie.getYear() + ")");
			firstIteration = false;
		    } else {
			taMovie.append("\n" + movie.getTitle() + " ("
				+ movie.getYear() + ")");
		    }
		}
	    }
	    // there can be more awards
	    taAward = new JTextArea(5, 15);
	    if (actor.getAwards() != null) {
		firstIteration = true;
		for (Award award : actor.getAwards()) {
		    if (firstIteration) {
			taAward.setText(award.getAward() + " ("
				+ award.getYear() + ")");
			firstIteration = false;
		    } else {
			taAward.append("\n" + award.getAward() + " ("
				+ award.getYear() + ")");
		    }
		}
	    }

	} else {
	    // textfields
	    tfName = new JTextField(15);
	    tfForename = new JTextField(15);
	    tfFamilyname = new JTextField(15);
	    tfNationality = new JTextField(15);
	    tfBirthday = new JTextField(15);
	    // textareas
	    taMovie = new JTextArea(5, 15);
	    taAward = new JTextArea(5, 15);
	}

	// scrollpane
	spMovie = new JScrollPane(taMovie);
	spAward = new JScrollPane(taAward);

	// settings of textfields
	tfName.setEditable(editable);
	tfForename.setEditable(editable);
	tfFamilyname.setEditable(editable);
	tfNationality.setEditable(editable);
	tfBirthday.setEditable(editable);

	// settings of textarea
	taMovie.setEditable(false);
	taAward.setEditable(false);

	// settings of scrollpane
	spMovie.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	spMovie.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	spAward.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	spAward.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

    }

    /**
     * Sets the content in the content panel
     * 
     * @param action
     *            indicates the kind of the view (add, show, edit)
     */
    private void setContent(Action action) {

	// panel for information of actor
	// name of actor is the title
	gbcContent.gridx = 0;
	gbcContent.gridy = 0;
	gbcContent.gridheight = 2;
	gbcContent.gridwidth = 2;
	gbcContent.ipady = 20;
	gblContent.setConstraints(lTitle, gbcContent);
	pContent.add(lTitle);

	// general settings for following textfields
	gbcContent.gridheight = 1;
	gbcContent.gridwidth = 1;
	gbcContent.ipadx = 125;
	gbcContent.ipady = 0;

	// specifications of person
	// name
	gbcContent.gridx = 0;
	gbcContent.gridy = 2;
	gblContent.setConstraints(lName, gbcContent);
	pContent.add(lName);
	gbcContent.gridx = 1;
	gbcContent.gridy = 2;
	gblContent.setConstraints(tfName, gbcContent);
	pContent.add(tfName);
	// forename
	gbcContent.gridx = 0;
	gbcContent.gridy = 3;
	gblContent.setConstraints(lForename, gbcContent);
	pContent.add(lForename);
	gbcContent.gridx = 1;
	gbcContent.gridy = 3;
	gblContent.setConstraints(tfForename, gbcContent);
	pContent.add(tfForename);
	// familyname
	gbcContent.gridx = 0;
	gbcContent.gridy = 4;
	gblContent.setConstraints(lFamilyname, gbcContent);
	pContent.add(lFamilyname);
	gbcContent.gridx = 1;
	gbcContent.gridy = 4;
	gblContent.setConstraints(tfFamilyname, gbcContent);
	pContent.add(tfFamilyname);
	// nationality
	gbcContent.gridx = 0;
	gbcContent.gridy = 5;
	gblContent.setConstraints(lNationality, gbcContent);
	pContent.add(lNationality);
	gbcContent.gridx = 1;
	gbcContent.gridy = 5;
	gblContent.setConstraints(tfNationality, gbcContent);
	pContent.add(tfNationality);
	// birthday
	gbcContent.gridx = 0;
	gbcContent.gridy = 6;
	gblContent.setConstraints(lBirthday, gbcContent);
	pContent.add(lBirthday);
	gbcContent.gridx = 1;
	gbcContent.gridy = 6;
	gblContent.setConstraints(tfBirthday, gbcContent);
	pContent.add(tfBirthday);

	// general settings for following textareas
	gbcContent.gridheight = 3;
	gbcContent.gridwidth = 1;

	// movies
	gbcContent.gridx = 0;
	gbcContent.gridy = 7;
	gblContent.setConstraints(lMovie, gbcContent);
	pContent.add(lMovie);
	gbcContent.gridx = 1;
	gbcContent.gridy = 7;
	gblContent.setConstraints(spMovie, gbcContent);
	pContent.add(spMovie);
	// // awards
	// gbcContent.gridx = 0;
	// gbcContent.gridy = 12;
	// gblContent.setConstraints(lAward, gbcContent);
	// pContent.add(lAward);
	// gbcContent.gridx = 1;
	// gbcContent.gridy = 12;
	// gblContent.setConstraints(spAward, gbcContent);
	// pContent.add(spAward);

	// panel for buttons
	pButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));

	// buttons for interaction
	switch (action) {

	case ADD:
	    // create buttons
	    bSave = new JButton("speichern");
	    pButtons.add(bSave);
	    bReset = new JButton("zurücksetzen");
	    pButtons.add(bReset);
	    bBack = new JButton("zurück");
	    pButtons.add(bBack);
	    // add action listener
	    bSave.addActionListener(new AddSaveListener());
	    bReset.addActionListener(new AddResetListener());
	    bBack.addActionListener(new BackToStartListener());
	    break;

	case SHOW:
	    // create buttons
	    bEdit = new JButton("bearbeiten");
	    pButtons.add(bEdit);
	    bDelete = new JButton("löschen");
	    pButtons.add(bDelete);
	    bBack = new JButton("zurück");
	    pButtons.add(bBack);
	    // add action listener
	    bEdit.addActionListener(new EditListener());
	    bDelete.addActionListener(new DeleteListener());
	    bBack.addActionListener(new BackToShowAllActorsListener());
	    break;

	case EDIT:
	    // create buttons
	    JButton bSave = new JButton("speichern");
	    pButtons.add(bSave);
	    JButton bReset = new JButton("zurücksetzen");
	    pButtons.add(bReset);
	    JButton bBack = new JButton("zurück");
	    pButtons.add(bBack);
	    // add action listener
	    bSave.addActionListener(new EditSaveListener());
	    bReset.addActionListener(new EditResetListener());
	    bBack.addActionListener(new BackToShowActorListener());
	    break;
	}

	// set content in content pane
	cContentPane.add(pContent, BorderLayout.NORTH);
	cContentPane.add(pButtons, BorderLayout.SOUTH);

	// set Frame
	view.setFrame();
    }

    @Override
    public void addActor() {

	initPanel("Schauspieler hinzufügen");
	initElements("Neuen Schauspieler hinzufügen", 20, null, true);
	Action add = Action.ADD;
	setContent(add);
    }

    @Override
    public void showActor(Actor actor) {

	initPanel("Schauspieler anzeigen - " + actor.getName());
	initElements(actor.getName(), 25, actor, false);
	Action show = Action.SHOW;
	setContent(show);
    }

    @Override
    public void editActor(Actor actor) {

	initPanel("Schauspieler bearbeiten - " + actor.getName());
	initElements(actor.getName(), 25, actor, true);
	Action edit = Action.EDIT;
	setContent(edit);
    }

    @Override
    public void showAllActors(LinkedList<Actor> actors) {

	initPanel("Schauspieler auflisten");
	fillActorTable(actors);

	// set content
	tmActors = new DefaultTableModel(dmActors, cActors) {
	    /**
	     * 
	     */
	    private static final long serialVersionUID = 6945357246658242347L;

	    @Override
	    public boolean isCellEditable(int row, int column) {
		return false;
	    }
	};

	tActors = new JTable(tmActors);

	// listener
	tActors.getSelectionModel().addListSelectionListener(
		new ListSelectionListener() {

		    @Override
		    public void valueChanged(ListSelectionEvent event) {
			if (event.getValueIsAdjusting()) {
			    actorPresenter.showSelectedActor(
				    (String) tActors.getValueAt(
					    tActors.getSelectedRow(), 0),
				    (String) tActors.getValueAt(
					    tActors.getSelectedRow(), 2));
			}
		    }
		});

	// buttons
	pButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));

	// create buttons
	// bSave = new JButton("speichern");
	// pButtons.add(bSave);
	bAdd = new JButton("Schauspieler hinzufügen");
	pButtons.add(bAdd);
	bBack = new JButton("zurück");
	pButtons.add(bBack);
	// add action listener
	// bSave.addActionListener(new SaveListener());
	bAdd.addActionListener(new AddActorListener());
	bBack.addActionListener(new BackToStartListener());

	// Scrollpanel
	JScrollPane spActors = new JScrollPane(tActors);
	spActors.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	spActors.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

	// add table in scrollpanel
	cContentPane.add(spActors, BorderLayout.NORTH);
	cContentPane.add(pButtons, BorderLayout.SOUTH);
	view.setFrame();
    }

    public void fillActorTable(LinkedList<Actor> actors) {

	// columns of table
	cActors = new String[3];
	cActors[0] = "Name";
	cActors[1] = "Nationalität";
	cActors[2] = "Geburtstag";

	// data model
	dmActors = new String[actors.size()][cActors.length];
	int i = 0;
	for (Actor actor : actors) {
	    dmActors[i][0] = actor.getName();
	    dmActors[i][1] = actor.getNationality();
	    dmActors[i][2] = actor.getBirthday();
	    i++;
	}
    }

    // inner class for add save button
    class AddSaveListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {

	    boolean error = false;

	    String name = tfName.getText();
	    if (name.equals("") && !error) {
		Presenter.console.addLog("err: no input of name");
		JOptionPane.showMessageDialog(frame,
			"Es wurde kein Name eingegeben!", "Kein Name",
			JOptionPane.ERROR_MESSAGE);
		error = true;
	    }
	    String forename = tfForename.getText();
	    if (forename.equals("") && !error) {
		Presenter.console.addLog("err: no input of forename");
		JOptionPane.showMessageDialog(frame,
			"Es wurde kein Vorname eingegeben!", "Kein Vorname",
			JOptionPane.ERROR_MESSAGE);
		error = true;
	    }
	    String familyname = tfFamilyname.getText();
	    if (familyname.equals("") && !error) {
		Presenter.console.addLog("err: no input of familyname");
		JOptionPane.showMessageDialog(frame,
			"Es wurde kein Familienname eingegeben!",
			"Kein Familienname", JOptionPane.ERROR_MESSAGE);
		error = true;
	    }
	    String nationality = tfNationality.getText();
	    if (nationality.equals("") && !error) {
		Presenter.console.addLog("err: no input of nationality");
		JOptionPane.showMessageDialog(frame,
			"Es wurde keine Nationalität eingegeben!",
			"Kein Nationalität", JOptionPane.ERROR_MESSAGE);
		error = true;
	    }
	    String birthday = tfBirthday.getText();
	    if (birthday.equals("") && !error) {
		Presenter.console.addLog("err: no input of birthday");
		JOptionPane.showMessageDialog(frame,
			"Es wurde kein Geburtstag eingegeben!",
			"Kein Geburtstag", JOptionPane.ERROR_MESSAGE);
		error = true;
	    }

	    LinkedList<Movie> movies = new LinkedList<Movie>();

	    if (!error) {
		Actor actorNew = new Actor(name, forename, familyname,
			nationality, birthday, movies, null);
		actorPresenter.addActor(actorNew);
	    }
	}
    }

    // inner class for add reset button
    class AddResetListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent arg0) {
	    // / textfields
	    tfName.setToolTipText("");
	    tfForename.setText("");
	    tfFamilyname.setText("");
	    tfNationality.setText("");
	    tfBirthday.setText("");

	    // textareas
	    taMovie.setText("");
	    taAward.setText("");

	}

    }

    // inner class for edit button
    class EditListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {

	    String name = tfName.getText();
	    String forename = tfForename.getText();
	    String familyname = tfFamilyname.getText();
	    String nationality = tfNationality.getText();
	    String birthday = tfBirthday.getText();
	    LinkedList<Movie> movies = actorPresenter.getMoviesInList(taMovie
		    .getText());

	    actorOld = new Actor(name, forename, familyname, nationality,
		    birthday, movies, null);

	    editActor(actorOld);
	}
    }

    // inner class for delete button
    class DeleteListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
	    int dialog = JOptionPane.showConfirmDialog(null,
		    "Soll '" + tfName.getText()
			    + "' wirklich gelöschen werden?",
		    "Wirklich löschen?", JOptionPane.YES_NO_OPTION);

	    if (dialog == JOptionPane.YES_OPTION) {

		String name = tfName.getText();
		String forename = tfForename.getText();
		String familyname = tfFamilyname.getText();
		String nationality = tfNationality.getText();
		String birthday = tfBirthday.getText();
		LinkedList<Movie> movies = actorPresenter
			.getMoviesInList(taMovie.getText());

		Actor actor = new Actor(name, forename, familyname,
			nationality, birthday, movies, null);

		actorPresenter.deleteActor(actor);
	    }
	}
    }

    // inner class for edit save button
    class EditSaveListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {

	    boolean error = false;

	    String name = tfName.getText();
	    if (name.equals("") && !error) {
		Presenter.console.addLog("err: no input of name");
		JOptionPane.showMessageDialog(frame,
			"Es wurde kein Name eingegeben!", "Kein Name",
			JOptionPane.ERROR_MESSAGE);
		error = true;
	    }
	    String forename = tfForename.getText();
	    if (forename.equals("") && !error) {
		Presenter.console.addLog("err: no input of forename");
		JOptionPane.showMessageDialog(frame,
			"Es wurde kein Vorname eingegeben!", "Kein Vorname",
			JOptionPane.ERROR_MESSAGE);
		error = true;
	    }
	    String familyname = tfFamilyname.getText();
	    if (familyname.equals("") && !error) {
		Presenter.console.addLog("err: no input of familyname");
		JOptionPane.showMessageDialog(frame,
			"Es wurde kein Familienname eingegeben!",
			"Kein Familienname", JOptionPane.ERROR_MESSAGE);
		error = true;
	    }
	    String nationality = tfNationality.getText();
	    if (nationality.equals("") && !error) {
		Presenter.console.addLog("err: no input of nationality");
		JOptionPane.showMessageDialog(frame,
			"Es wurde keine Nationalität eingegeben!",
			"Kein Nationalität", JOptionPane.ERROR_MESSAGE);
		error = true;
	    }
	    String birthday = tfBirthday.getText();
	    if (birthday.equals("") && !error) {
		Presenter.console.addLog("err: no input of birthday");
		JOptionPane.showMessageDialog(frame,
			"Es wurde kein Geburtstag eingegeben!",
			"Kein Geburtstag", JOptionPane.ERROR_MESSAGE);
		error = true;
	    }

	    LinkedList<Movie> movies = actorPresenter.getMoviesInList(taMovie
		    .getText());

	    if (!error) {
		Actor actorNew = new Actor(name, forename, familyname,
			nationality, birthday, movies, null);
		actorPresenter.editActor(actorNew, actorOld);
	    }
	}
    }

    // inner class for edit reset button
    class EditResetListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent arg0) {
	    // textfields
	    tfName.setText(actorOld.getName());
	    tfForename.setText(actorOld.getForename());
	    tfFamilyname.setText(actorOld.getFamilyname());
	    tfNationality.setText(actorOld.getNationality());
	    tfBirthday.setText(actorOld.getBirthday());

	    // // textareas
	    // taMovie.setText(moviesOld);
	    // taAward.setText(awardsOld);

	}

    }

    // inner class for back button (to start window)
    class BackToStartListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent arg0) {
	    view.startDVDCollection();
	}

    }

    // inner class for back button (to show all actors)
    class BackToShowAllActorsListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent arg0) {
	    new ActorPresenter().showAllActors();
	}

    }

    // inner class for back button (to show an actor)
    class BackToShowActorListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent arg0) {
	    new ActorView().showActor(actorOld);
	}

    }

    // inner class for add actor button
    class AddActorListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent arg0) {
	    new ActorView().addActor();
	}

    }
}
