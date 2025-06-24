package ru.hse.se.g272.ervo.ooaip.crosszeros;

import ru.hse.se.g272.ervo.ooaip.Form;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Form for cross-zero games.
 *
 * @author Ervo Victor
 * @since 18.03.14
 */
public class CrossZerosForm extends Form {
    /**
     * Socket for server.
     */
    private ServerSocket serverSocket;

    /**
     * Server for client.
     */
    private Socket clientSocket;

    /**
     * Stream for getting information.
     */
    private BufferedReader in;

    /**
     * Stream fot sending information.
     */
    private PrintWriter out;

    /**
     * {@code true}, if player can make his or her turn now.
     */
    private boolean turn;

    /**
     * Cross-Zeros game.
     */
    private CrossZerosGame game;

    /**
     * Creates form for crosses-zeroes game.
     */
    public CrossZerosForm() {
        final CrossZerosGame crossZerosGame = new CrossZerosGame(this);
        setGame(crossZerosGame);
        add(crossZerosGame);
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(final MouseEvent mouseEvent) {
                if (isTurn()) {
                    if (crossZerosGame.tryToPlaceCross(mouseEvent)) {
                        repaint();
                        crossZerosGame.checkWin();
                        waitTurn();
                        repaint();
                        crossZerosGame.checkWin();
                        repaint();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "It is not your turn");
                }
            }
        };
        addMouseListener(mouseAdapter);
    }

    /**
     * Waits for other player.
     */
    private void waitTurn() {
        setTurn(false);
        new Thread(() -> {
            try {
                repaint();
                String msg = getIn().readLine();
                Coordinates coordinates
                        = Coordinates.parseCoordinates(msg);
                getGame().placeZero(coordinates);
                repaint();
                getGame().checkWin();
                setTurn(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * Method that is executed when program starts.
     * @param args Command line arguments
     */
    public static void main(final String[] args) {
        CrossZerosForm crossZerosForm = new CrossZerosForm();
        crossZerosForm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        crossZerosForm.setTitle("Cross-zeros");
        crossZerosForm.setDefaultSize(HALFSCREEN);
        crossZerosForm.setVisible(true);
        ChooseForm chooseForm = new ChooseForm(crossZerosForm);
        chooseForm.setVisible(true);
    }

    /**
     * Sets socket for server.
     * @param socket Socket for server
     */
    public final void setServerSocket(final ServerSocket socket) {
        this.serverSocket = socket;
        JOptionPane.showMessageDialog(null, "Server is up on "
                + socket.getLocalPort());
        startGameAsServer();
    }

    /**
     * Starts game being a server.
     */
    private void startGameAsServer() {
        try {
            Socket client = getServerSocket().accept();
            setIn(new BufferedReader(new InputStreamReader(
                    client.getInputStream())));
            setOut(new PrintWriter(client.getOutputStream()));
            setTurn(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Starts game being a client.
     */
    private void startGameAsClient() {
        try {
            setIn(new BufferedReader(
                    new InputStreamReader(getClientSocket().getInputStream())));
            setOut(new PrintWriter(
                    getClientSocket().getOutputStream()));
            waitTurn();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets socket for server.
     * @return Socket for server.
     */
    public final ServerSocket getServerSocket() {
        return serverSocket;
    }

    /**
     * Sets socket for client.
     * @param clientSocket socket for client
     */
    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
        JOptionPane.showMessageDialog(null, "Connected to "
                + clientSocket.getInetAddress().getCanonicalHostName() + "("
                + clientSocket.getLocalPort() + ")");
        startGameAsClient();
    }

    /**
     * Gets socket for client.
     * @return socket for client
     */
    public Socket getClientSocket() {
        return clientSocket;
    }

    /**
     * Sets input stream.
     * @param in input stream
     */
    public void setIn(BufferedReader in) {
        this.in = in;
    }

    /**
     * Gets input stream.
     * @return input stream
     */
    public BufferedReader getIn() {
        return in;
    }

    /**
     * Sets output stream.
     * @param out output stream
     */
    public void setOut(PrintWriter out) {
        this.out = out;
    }

    /**
     * Gets output stream.
     * @return Output stream
     */
    public PrintWriter getOut() {
        return out;
    }

    /**
     * Sets turn.
     * @param turn turn.
     */
    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    /**
     * Checks if player can make his or her turn.
     * @return {@code true}, if he or she can
     */
    public boolean isTurn() {
        return turn;
    }

    /**
     * Gets game.
     * @return game
     */
    public CrossZerosGame getGame() {
        return game;
    }

    /**
     * Sets game
     * @param game cross-zeros game.
     */
    public void setGame(CrossZerosGame game) {
        this.game = game;
    }
}
