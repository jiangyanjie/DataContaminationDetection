package storage.person;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import model.standardModel.person.Actor;
import presenter.Presenter;
import storage.Storage;

public class ActorStorage extends Storage {

    public void saveActors() {

	File file = new File("memory/actor.ser");

	FileOutputStream fileStream = null;
	ObjectOutputStream oos = null;
	try {
	    fileStream = new FileOutputStream(file);
	    oos = new ObjectOutputStream(fileStream);

	    for (Actor actor : model.getActorList()) {
		oos.writeObject(actor);
	    }

	    oos.close();

	} catch (FileNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }

    public void loadActors() {

	File file = new File("memory/actor.ser");

	FileInputStream fileStream = null;
	ObjectInputStream ois = null;

	try {
	    fileStream = new FileInputStream(file);
	    ois = new ObjectInputStream(fileStream);

	    Object object = null;
	    while ((object = ois.readObject()) != null) {
		Actor actor = (Actor) object;
		model.addActorToList(actor);
	    }

	    ois.close();
	} catch (FileNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (EOFException e) {
	    Presenter.console.addLog("inf: actors loaded from file '"
		    + file.getName());
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (ClassNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
}
