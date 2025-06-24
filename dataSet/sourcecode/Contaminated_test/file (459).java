package controllers.server;

import entities.Player;
import entities.Players;
import gameMessages.DayArrivedMessage;
import gameMessages.KilledMessage;
import gameMessages.KilledPlayerMessage;

public class DayController {

    private final Players players;
    private final GamePlayEngine engine;
    private final GamePlay play;

    public DayController(Players players, GamePlayEngine engine, GamePlay play) {

        this.players = players;
        this.engine = engine;
        this.play = play;
    }

    public void start() {
        sendDayArrivedMessage();
        play.createPlayersPoll(new GamePoll(), players);
    }

    private void sendDayArrivedMessage() {
        DayArrivedMessage message = new DayArrivedMessage();
        message.setPlayersName(players.getAllPlayersName());
        players.sendMessage(message);
    }

    public void poll(String votedPlayer) {
        play.poll(votedPlayer);
        isEveryOneVoted();
    }

    private void isEveryOneVoted() {
        if (play.villagerPollStatus()) removePlayer();
    }

    private void removePlayer() {

        Player deadPlayer = play.getKilledPlayer();

        sendKilledMessage(deadPlayer);

        engine.removePlayer(deadPlayer);

        sendKilledPlayerMessage(deadPlayer);

        isGameStable();
    }

    private void isGameStable() {
        if (play.getGameStatus().equals(GameResult.GameStable)) engine.startNight();
        if (play.getGameStatus().equals(GameResult.MafiaWins)) engine.endGame(GameResult.MafiaWins);
        if (play.getGameStatus().equals(GameResult.VillagerWins)) engine.endGame(GameResult.VillagerWins);
    }

    private void sendKilledMessage(Player deadPlayer) {
        KilledMessage message = new KilledMessage();
        message.setLog(engine.getLog());
        deadPlayer.sendMessage(message);
        deadPlayer.sendMessage(message);
    }

    private void sendKilledPlayerMessage(Player removedPlayer) {
        KilledPlayerMessage message = new KilledPlayerMessage();
        message.setPlayerName(removedPlayer.getName());
        players.sendMessage(message);
    }
}
