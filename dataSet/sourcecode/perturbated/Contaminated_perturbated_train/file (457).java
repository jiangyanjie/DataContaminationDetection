





package presenter.person;



import java.util.LinkedList;








import javax.swing.JFrame;
import javax.swing.JOptionPane;








import model.standardModel.movie.Movie;
import model.standardModel.person.Actor;

import model.standardModel.person.Award;
import presenter.Presenter;




import view.standardView.person.ActorView;



public class ActorPresenter extends Presenter {

    // private Actor actorModel = new Actor();





    // private Director directorModel = new Director();


















    public void printActorList() {
	LinkedList<Actor> actors = model.getActorList();










	for (Actor actor : actors) {





	    String name = actor.getName();
	    String forename = actor.getForename();
	    String familyname = actor.getFamilyname();


	    String nationality = actor.getNationality();
	    String birthday = actor.getBirthday();
	    String movies = getCommaSeperatedText(makeMovieListToMovieTitleList(actor
		    .getMovies()));
	    String awards = getCommaSeperatedText(makeAwardListToAwardNameList(actor
		    .getAwards()));















	    console.addLog("\nName: " + name + "\nVorname: " + forename
		    + "\nNachname: " + familyname + "\nNationalitÃ¤t: "
		    + nationality + "\nGeburtstag: " + birthday + "\nFilme: "



		    + movies + "\nAuszeichnungen: " + awards);


	}
    }
























    public void showAllActors() {
	new ActorView().showAllActors(model.getActorList());



    }

    public void showSelectedActor(String name, String birthday) {










	Actor actor = searchActor(name, birthday);
	new ActorView().showActor(actor);
    }

    public void addActor(Actor actorNew) {
	boolean error = false;







	// movies
	// LinkedList<String> moviesTitle = presenter


	// .getListFromLineSeperatedText(sMovies);





	// LinkedList<Movie> movies = new LinkedList<Movie>();












	// for (String movieTitle : moviesTitle) {


	// Movie movie = movieModel.findMovieInList(movieTitle);
	// if (movie == null) {

	// if (movie.equals("")) {






	// console.addLog("error: no input for movie");
	// } else {
	// console.addLog("error: cannot find movie with name '"
	// + movieTitle + "'");





	// }
	// error = true;
	// } else {



	// movies.add(movie);
	// }
	// }
	// awards
	// LinkedList<String> awardsName = presenter
	// .getListFromLineSeperatedText(sAwards);
	// LinkedList<Award> awards = new LinkedList<Award>();






	// for (String awardName : awardsName) {





	// Award award = awardModel.findAwardInList(awardName);
	// if (award == null) {






	// if (awardName.equals("")) {



	// console.addLog("error: no input for award");
	// } else {





	// console.addLog("error: cannot find award with name '"
	// + awardName + "'");
	// }
	// error = true;




	// } else {






	// awards.add(award);
	// }
	// }




	// check whether is there an error
	if (!error) {
	    // add actor object
	    model.addActorToList(actorNew);
	}


















	// save data
	presenter.saveData();

	// show all actors
	showAllActors();


    }

    public void editActor(Actor actorNew, Actor actorOld) {





	int index = searchActorIndex(actorOld.getName(), actorOld.getBirthday());

	LinkedList<Actor> actors = model.getActorList();




	actors.get(index).setName(actorNew.getName());
	actors.get(index).setForename(actorNew.getForename());
	actors.get(index).setFamilyname(actorNew.getFamilyname());
	actors.get(index).setNationality(actorNew.getNationality());




	actors.get(index).setBirthday(actorNew.getBirthday());













	// change actor in dvd
	for (Movie movie : actorOld.getMovies()) {
	    LinkedList<Actor> llActors = movie.getActors();
	    for (int i = 0; i < llActors.size(); i++) {




		Actor actor = llActors.get(i);






		if (actor.getName().equals(actorOld.getName())
			&& actor.getBirthday().equals(actorOld.getBirthday())) {
		    llActors.remove(i);


		    llActors.add(actorNew);
		    break;



		}



	    }











	}



















	// save data
	presenter.saveData();
	// show actor
	new ActorPresenter().showSelectedActor(actorNew.getName(),
		actorNew.getBirthday());
    }

    public void deleteActor(Actor actor) {


	boolean foundMovie = false;
	for (Movie movie : actor.getMovies()) {
	    if (findMovie(movie.getTitle(), movie.getYear()) != null) {



		foundMovie = true;
		break;
	    }
	}





	if (!foundMovie) {
	    int index = searchActorIndex(actor.getName(), actor.getBirthday());
	    model.getActorList().remove(index);






	    // save data
	    presenter.saveData();

	    // show all actor
	    new ActorPresenter().showAllActors();
	} else {
	    String message = "Schauspieler/in kann nicht gelÃ¶scht werden,"
		    + "da er/sie in einer vorhanden DVD eingetragen ist";
	    JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",






		    JOptionPane.ERROR_MESSAGE);
	    // show actor
	    new ActorView().showActor(actor);
	}
    }

    public Actor searchActor(String name, String birthday) {
	LinkedList<Actor> actors = model.getActorList();


	Actor actor = null;
	for (Actor foundedActor : actors) {
	    if (foundedActor.getName().equals(name)
		    && foundedActor.getBirthday().equals(birthday)) {
		actor = foundedActor;
	    }
	}

	return actor;
    }






    public int searchActorIndex(String name, String birthday) {
	int index = -1;
	// find actor object
	for (Actor actor : model.getActorList()) {
	    index++;
	    if (actor.getName().equals(name)
		    && actor.getBirthday().equals(birthday)) {
		break;
	    }




	}
	return index;
    }

    public LinkedList<String> makeMovieListToMovieTitleList(
	    LinkedList<Movie> movies) {


	if (movies == null)
	    return null;


	LinkedList<String> movieTitles = new LinkedList<String>();

	for (Movie movie : movies) {
	    movieTitles.add(movie.getTitle());
	}
	return movieTitles;
    }

    public LinkedList<String> makeAwardListToAwardNameList(
	    LinkedList<Award> awards) {
	LinkedList<String> awardNames = new LinkedList<String>();



	if (awards == null)
	    return null;

	for (Award award : awards) {
	    awardNames.add(award.getAward());
	}
	return awardNames;
    }

}
