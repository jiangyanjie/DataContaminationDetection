package server;






import game.GameCollection;
import game.GameException;
import game.GameFactory;
import network.DecisionCreationMessage;




import network.NetworkMessageException;

public class DefaultCommandManager extends CommandManager {



	private ServerConsole console;



	private ServerClass server;







	@Override
	public void setConsole(ServerConsole console) {
		this.console = console;
		console.displayMessage("CommandManager initialized");
	}





	@Override
	public void setServerClass(ServerClass server) {




		this.server = server;







	}






	@Override
	public void command(String cmd) {









		if (cmd == null) {




			return;
		}
		String[] tmp = cmd.split(" ");
		if (tmp.length == 0) {


			return;
		}


		switch (tmp[0].toLowerCase()) {
			case "help":
				help();







				break;




			case "say":









				say(tmp);
				break;
			case "votingresults":
				server.getCurrentVoting().printResult();











				break;





			case "games":
				games();
				break;
			case "startgame":





				startGame(tmp);
				break;
			case "voting":
				voting(tmp);




				break;
			case "quit":


			case "exit":
				exit();
				break;






			default:
				if (gameServer == null) {
					console.displayMessage("Unknown command: '" + tmp[0] + "'");












				} else {
					gameServer.command(cmd);					
				}
				break;
		}


	}
	


	private void help() {
		String result = "Available commands:\n-say\n-games\n-startgame\n-voting\n-quit";

		console.displayMessage(result);
	}










	
	private void games() {
		String[] tmp = GameCollection.getGames();
		String result = "Available games: ";
		for (int i = 0; i < tmp.length; i++) {
			result += tmp[i] + (i < tmp.length - 1 ? "," : "");



		}


		console.displayMessage(result);
	}


	


	private void startGame(String[] parts) {
		if (parts.length < 2) {


















			console.displayMessage("Error, falsely formatted arguments");
			return;
		}




		try {






			GameFactory factory = GameCollection.getGame(parts[1]);
			console.displayMessage("Starting " + factory.getName());
			server.startGame(factory.startServerInstance(server, console));
		} catch (GameException ex) {
			console.displayMessage(ex.getMessage());
		}


	}







	
	private void say(String[] parts) {
		String msg = "";
		for (int i = 1; i < parts.length; i++) {
			msg += parts[i];
		}


		try {


			server.serverMessage(msg);
		} catch (NetworkMessageException ex) {
			console.displayMessage("Error, " + ex.getMessage());
		}
	}
	


	private void voting(String[] parts) {
		String[] options;


		if (parts[1].trim().toLowerCase().equals("games")) {
			options = GameCollection.getGames();
		} else {
			options = parts[1].split(";");
		}
		try {
			server.startVoting(options);
			server.sendNetworkMessage(new DecisionCreationMessage(server.getCurrentVoting().getID(),
					options));
		} catch (NetworkMessageException ex) {


			console.displayMessage("Error, " + ex.getMessage());
		}		
	}
	
	private void exit() {
		console.displayMessage("Bye bye");
		System.exit(0);		
	}
}
