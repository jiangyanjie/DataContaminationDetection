package ru.hse.se.g272.ervo.ooaip.crosszeros;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.PrintWriter;

/**
 * Cross-Zeros Game.
 *
 * @author Ervo Victor
 * @since 18.03.14
 */
public class CrossZerosGame extends JComponent {
    /**
     * Number of sections in row or column.
     */
    private static final int COUNT = 3;

    /**
     * Part of panel filled with component.
     */
    public static final double BOUND_MULTIPLIER = 0.95;

    /**
     * Size of sections.
     */
    private double sectionSize;

    /**
     * Matrix that contains sections for the game.
     */
    private Section[][] sections;
    private final CrossZerosForm invoker;

    /**
     * Creates a game.
     * @param invoker invoker
     */
    public CrossZerosGame(CrossZerosForm invoker) {
        this.invoker = invoker;
        sections = new Section[COUNT][COUNT];
        for (int i = 0; i < sections.length; i++) {
            for (int j = 0; j < sections[i].length; j++) {
                sections[i][j] = new Section();
            }
        }
    }

    @Override
    protected final void paintComponent(final Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        g.translate(getWidth() / 2, getHeight() / 2);
        setSectionSize(BOUND_MULTIPLIER * Math.min(getHeight(),
                getWidth()) / COUNT);
        for (int i = 0; i < COUNT * COUNT; i++) {
            getSection(i % COUNT, i / COUNT).setLeftBound(
                    (int) (-COUNT / 2.0 * getSectionSize()
                            + (i % COUNT) * getSectionSize()));
            getSection(i % COUNT, i / COUNT).setRightBound(
                    (int) (-COUNT / 2.0 * getSectionSize()
                            + ((i % COUNT) + 1) * getSectionSize()));
            getSection(i % COUNT, i / COUNT).setTopBound(
                    (int) (-COUNT / 2.0 * getSectionSize()
                            + (i / COUNT) * getSectionSize()));
            getSection(i % COUNT, i / COUNT).setBottomBound(
                    (int) (-COUNT / 2.0 * getSectionSize()
                            + ((i / COUNT) + 1) * getSectionSize()));
            drawSection(g, getSection(i % COUNT, i / COUNT));
        }
    }

    /**
     * Checks if some of players won.
     */
    public final void checkWin() {
        checkColumns();
        checkRows();
        checkDiagonals();
        repaint();
    }

    /**
     * Checks if some of players filled one of diagonals.
     */
    private void checkDiagonals() {
        checkWin(new Section[]
                {sections[0][0], sections[1][1], sections[2][2]});
        checkWin(new Section[]
                {sections[0][2], sections[1][1], sections[2][0]});
    }

    /**
     * Checks if some of players filled one of rows.
     */
    private void checkRows() {
        for (int i = 0; i < COUNT; i++) {
            checkWin(new Section[]
                    {sections[0][i], sections[1][i], sections[2][i]});
        }
    }

    /**
     * Checks if some of players filled one of columns.
     */
    private void checkColumns() {
        for (Section[] sections1: sections) {
            checkWin(sections1);
        }
    }

    /**
     * Checks if some of players filled all sections of an array.
     * @param section Checked array
     */
    private void checkWin(final Section[] section) {
        SectionValue winner = section[0].getValue();
        if (winner != SectionValue.EMPTY) {
            for (Section s: section) {
                if (s.getValue() != winner) {
                    return;
                }
            }
            win(winner);
        }
    }

    /**
     * Ends game.
     * @param winner winner
     */
    private void win(final SectionValue winner) {
        if (winner == SectionValue.CROSS) {
            JOptionPane.showMessageDialog(null, "You won");
        } else if (winner == SectionValue.ZERO) {
            JOptionPane.showMessageDialog(null, "You lost");
        }
        clear();
    }

    /**
     * Clears sections to start a new game.
     */
    private void clear() {
        for (int i = 0; i < COUNT; i++) {
            for (int j = 0; j < COUNT; j++) {
                getSection(i, j).setValue(SectionValue.EMPTY);
            }
        }
    }

    /**
     * Gets sections by coordinates.
     * @param column Column
     * @param row Row
     * @return Section
     */
    public final Section getSection(final int column, final int row) {
        return sections[column][row];
    }

    /**
     * Draws one section of game panel.
     * @param g Graphics
     * @param section Section
     */
    private void drawSection(final Graphics2D g, final Section section) {
        g.drawRect(section.getLeftBound(), section.getTopBound(),
                (int) getSectionSize(), (int) getSectionSize());
        if (section.getValue() == SectionValue.CROSS) {
            g.drawLine(section.getLeftBound(), section.getTopBound(),
                    section.getRightBound(), section.getBottomBound());
            g.drawLine(section.getLeftBound(), section.getBottomBound(),
                    section.getRightBound(), section.getTopBound());
        } else if (section.getValue() == SectionValue.ZERO) {
            g.drawOval(section.getLeftBound(), section.getTopBound(),
                    (int) getSectionSize(), (int) getSectionSize());
        }
    }

    /**
     * Sets size of section.
     * @param size size of section
     */
    public final void setSectionSize(final double size) {
        this.sectionSize = size;
    }

    /**
     * Size of section.
     * @return Size of section
     */
    public final double getSectionSize() {
        return sectionSize;
    }

    /**
     * Places cross in section clicked by mouse, if possible.
     * @param mouseEvent Information about click
     * @return if cross was placed
     */
    public final boolean tryToPlaceCross(final MouseEvent mouseEvent) {
        try {
            if (getSection(mouseEvent).getValue() == SectionValue.EMPTY) {
                getSection(mouseEvent).setValue(SectionValue.CROSS);
                PrintWriter out = invoker.getOut();
                repaint();
                out.println(getCoord(mouseEvent).toString());
                out.flush();
                repaint();
                return true;
            }
            return false;
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage() + ", but it is not very bad");
            repaint();
            return false;
        }
    }

    private Coordinates getCoord(MouseEvent mouseEvent) {
        for (int i = 0; i < COUNT; i++) {
            for (int j = 0; j < COUNT; j++) {
                if (clickedInSection(mouseEvent, getSection(i, j))) {
                    return new Coordinates(i, j);
                }
            }
        }
        throw new IllegalArgumentException(
                "Event does not represents any section");
    }

    /**
     * Gets section that clicked by mouse.
     * @param mouseEvent Information about click
     * @return Section
     */
    private Section getSection(final MouseEvent mouseEvent) {
        for (int i = 0; i < COUNT; i++) {
            for (int j = 0; j < COUNT; j++) {
                if (clickedInSection(mouseEvent, getSection(i, j))) {
                    return getSection(i, j);
                }
            }
        }
        throw new IllegalArgumentException(
                "Event does not represents any section");
    }

    /**
     * Checks if section was clicked.
     * @param mouseEvent Information about click
     * @param section Section
     * @return if section was clicked
     */
    private boolean clickedInSection(final MouseEvent mouseEvent,
                                     final Section section) {
        int x = mouseEvent.getX() - getWidth() / 2;
        int y = mouseEvent.getY() - getHeight() / 2;
        return x > section.getLeftBound()
                && x < section.getRightBound()
                && y > section.getTopBound()
                && y < section.getBottomBound();
    }

    public void placeZero(Coordinates coordinates) {
        getSection(coordinates.getX(), coordinates.getY()).setValue(SectionValue.ZERO);
        repaint();
    }
}
