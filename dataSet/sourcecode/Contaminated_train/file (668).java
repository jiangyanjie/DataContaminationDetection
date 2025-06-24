package com.game.player.console;

import com.game.field.Field;
import com.game.player.EPlayerSide;
import com.game.player.Player;


import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsolePlayer extends Player{

    public ConsolePlayer(EPlayerSide playerSide) {
        super(playerSide);
    }

    @Override
    public void makeTurn(Field field) {
        Scanner scanner = new Scanner(System.in);
        boolean wrong = true;
        boolean valid = false;
        while (wrong) {
            System.out.print("Текущий ход:" + getPlayerSide()
                    + " Введите координаты хода в виде: №столбца №строки ");
            int column = 0, row = 0;
            try {
                column = scanner.nextInt();
                row = scanner.nextInt();
                valid = true;

            } catch (InputMismatchException e){
                System.out.println("Введено не валидное значение, попробуйте ещё раз");
                scanner = new Scanner(System.in);
                valid = false;
            }
            if (valid) {
                if (column > field.getField().length || row > field.getField().length
                        || field.getField()[column][row] != field.getDefaultValue()) {
                    field.showField();
                    System.out.println("Место уже занято, или не существует, попробуйте ещё раз");
                }
                else {
                    field.getField()[column][row] = getPlayerSide();
                    wrong = false;
                }
            }
        }
    }
}
