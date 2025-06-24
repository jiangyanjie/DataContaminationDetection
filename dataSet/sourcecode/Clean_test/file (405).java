package com.afomina.datamining.ui;

import com.afomina.datamining.DataMiner;
import com.afomina.datamining.model.Actor;
import com.afomina.datamining.model.Base;
import com.afomina.datamining.model.Movie;
import com.afomina.datamining.parser.Parser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by alexandra on 14.05.14.
 */
public class AppForm {
    private JTextField filePath;
    private JTextField yearBegin;
    private JTextField yearEnd;
    private JButton startButton;
    private JTextArea resultTextArea;
    private JPanel panel1;
    private JButton openButton;

    public AppForm() {
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    System.out.println("Starting parse " + filePath.getText());
                    System.out.println("Years: " + yearBegin.getText() + " - " + yearEnd.getText());
                    resultTextArea.setText("Please wait...");

                    String path = filePath.getText();
                    Map<Base, List<Base>> actors;
                    boolean woman = false;
                    if (path.contains("actresses")) {
                        woman = true;
                        System.out.println("Searching the most popular actress...");
                        actors = Parser.actressesParse(path, Integer.parseInt(yearBegin.getText()), Integer.parseInt(yearEnd.getText()));
                    } else {
                        System.out.println("Searching the most popular actor...");
                        actors = Parser.actorsParse(path, Integer.parseInt(yearBegin.getText()), Integer.parseInt(yearEnd.getText()));
                    }

                    Actor maxMoviesActor = DataMiner.findTheMostPopularActor(actors);
                    System.out.println("Actor: " + maxMoviesActor);
                    Movie movie = DataMiner.findTheMostPopularMovie(actors);
                    System.out.println("Movie: " + movie);
                    resultTextArea.setText("The most popular " + (woman ? "actress" : "actor") + " for given period is " + maxMoviesActor.getName() + ", amount of movies: " + actors.get(maxMoviesActor).size()
                            + "\nThe most popular movie is '" + movie.getName() + "', " + movie.getYear() + ", amount of actors: " + actors.get(movie).size());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        panel1.setVisible(true);
        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int chooseState = fileChooser.showOpenDialog(panel1);
                if (chooseState == JFileChooser.APPROVE_OPTION) {
                    filePath.setText(fileChooser.getSelectedFile().getPath());
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("AppForm");
        frame.setMinimumSize(new Dimension(600, 400));
        frame.setContentPane(new AppForm().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
