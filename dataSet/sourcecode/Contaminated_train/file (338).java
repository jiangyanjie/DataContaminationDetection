/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package acchat.newserver;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 *
 * @author
 */
public class AccountSave {

    private static AccountSave instance = null;

    public static AccountSave getInstance() {
        if (instance == null) {
            instance = new AccountSave();
        }
        return instance;
    }

    protected Scanner s;

    private AccountSave() {

    }

    public boolean accExists(String username, String md5) {
        try {
            s = new Scanner(new File("list.accounts"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AccountSave.class.getName()).log(Level.SEVERE, null, ex);
        }

        while (s.hasNextLine()) {
            //if(s.nextLine().equals(username+" "+md5))
            String t = s.nextLine();
            String[] command = t.split(Pattern.quote(" "));
            for (String str : command) {
                System.out.println(str);
            }
            if (command[0].equals(username) && command[1].equals(md5)) {
                return true;
            }
        }

        return false;
    }

    public void accCreate(String username, String md5) {
        System.out.println("This is the md5 " + md5);
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("list.accounts", true)))) {
            out.println(username + " " + md5);
        } catch (IOException e) {
            System.err.println("Fehler waehrend etwas in die Datei geschrieben wurde");
        }
    }

    public boolean nameExists(String username) {
        try {
            s = new Scanner(new File("list.accounts"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AccountSave.class.getName()).log(Level.SEVERE, null, ex);
        }

        while (s.hasNextLine()) {
            String[] acc = s.nextLine().split(Pattern.quote(" "));
            if (acc[0].equals(username)) {
                return true;
            }
        }

        return false;
    }

}
