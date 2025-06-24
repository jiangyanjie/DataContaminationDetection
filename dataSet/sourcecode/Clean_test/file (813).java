/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package project;

import java.io.*;
import static java.lang.Thread.sleep;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Seth Legaspi/Marco Santos
 */
public class createClient implements Runnable {
    
    Socket sock;
    PrintStream sendOut;
    Scanner in;
    private String ip;

    

    String stopCommand;
    String checker;
    
    boolean wait = true;
    boolean shoot;
    boolean left;
    boolean right;
    boolean up;
    boolean down;
    
    boolean settingDoneType = false;
    boolean settingDoneYPosA = false;
    boolean settingDoneYPosB = false;
    
    int one;
    int two;
    int type;
    
    int checkerInt;
    
    ArrayList<Integer> typeChecker;
    ArrayList<Integer> yPosALine;
    ArrayList<Integer> yPosBLine;
    

    /**
     *
     * @throws java.net.UnknownHostException
     */
    public createClient(String IPA) throws UnknownHostException, IOException {
           
               ip = IPA;
               
               sock = new Socket(ip, 8888);
               checkerInt = 8888;
               
        sendOut = new PrintStream(sock.getOutputStream());
        in = new Scanner(new InputStreamReader(sock.getInputStream()));
        
        typeChecker = new ArrayList<Integer>();
        yPosALine = new ArrayList<Integer>();
        yPosBLine = new ArrayList<Integer>();
        
        Thread client = new Thread(this);
        client.start();
   
    }
    
    public void sendCommand (String act) {
        sendOut.println(act);
        sendOut.flush();
    }
    
   
   
    //shoot
    public void setShootTrue() {
        shoot = true;
    }
    public void setShootFalse() {
        shoot = false;
    }
    public boolean getShoot() {
        return shoot;
    }
    //left
    public void setLeftTrue() {
        left = true;
    }
    public void setLeftFalse() {
        left = false;
    }
    public boolean getLeft() {
        return left;
    }
    //right
    public void setRightTrue() {
        right = true;
    }
    public void setRightFalse() {
        right = false;
    }
    public boolean getRight() {
        return right;
    }
    //up
    public void setUpTrue() {
        up = true;
    }
    public void setUpFalse() {
        up = false;
    }
    public boolean getUp() {
        return up;
    }
    //down
    public void setDownTrue() {
        down = true;
    }
    public void setDownFalse() {
        down = false;
    }
    public boolean getDown() {
        return down;
    }
    //type - for enemy generator
    public void addType(int a) {
        typeChecker.add(a);
        settingDoneType = true;
    }
    public int getType() { 
        return typeChecker.get(0);
    }
    public void removeType() {
        typeChecker.remove(0);
        if (typeChecker.size() == 0) {
            settingDoneType = false;
        }
    }
    public boolean getSettingSit() {
        return settingDoneType;
    }
    // YPosA
    public void setYPosA (int a) {
        if (a == 9999) {
            //do nothing
        }
        if (a != 9999) {
        yPosALine.add(a);
        settingDoneYPosA = true;
        }
    }
    public int getYPosA () {
        return yPosALine.get(0);
    }
    public void removeYPosA() {
        yPosALine.remove(0);
        if (yPosALine.size() == 0) {
            settingDoneYPosA = false;
        }
    }
    public boolean getPosASit() {
        return settingDoneYPosA;
    }
    // YPosB
    public void setYPosB(int b) {
        if (b == 9999) {
            //do nothing
        }
        if (b != 9999) {
              yPosBLine.add(b);
              settingDoneYPosB = true;
        }
    }
    public int getYPosB() {
        return yPosBLine.get(0);
    }
    public void removeYPosB() {
        yPosBLine.remove(0);
        if (yPosBLine.size() == 0) {
            settingDoneYPosB = false;
        }
    }
    public boolean getPosBSit() {
        return settingDoneYPosB;
    }
    //checkerInt
    public void setCheckerInt(String a) {
        checkerInt = Integer.parseInt(a);
    }
    public int getCheckerInt() {
        return checkerInt;
    }
    
    @Override
    public void run() {
        
       while (wait) {
           checker = "";
           try {
               checker = in.nextLine();
               try {
                        setCheckerInt(checker);  
                             } catch(NumberFormatException e) { 
              
               
               //shoot
               if (checker.equals("shootFalse")) {
                   setShootFalse();
               }
               if (checker.equals("shoot")) {
                   setShootTrue();
               }
               //left
               if (checker.equals("moveLeftFalse")) {
                   setLeftFalse();
               }
               if (checker.equals("moveLeft")) {
                   setLeftTrue();
               }
               //right
               if (checker.equals("moveRightFalse")) {
                   setRightFalse();
               }
               if (checker.equals("moveRight")) {
                   setRightTrue();
               }
               //up
               if (checker.equals("moveUpFalse")) {
                   setUpFalse();
               }
               if (checker.equals("moveUp")) {
                   setUpTrue();
               }
               //down
               if (checker.equals("moveDownFalse")) {
                   setDownFalse();
               }
               if (checker.equals("moveDown")) {
                   setDownTrue();
               }
               //types
               if (checker.equals("one")) {
                   addType(1);
               }
               if (checker.equals("two")) {
                   addType(2);
               }
               if (checker.equals("three")) {
                   addType(3);
               }
               if (checker.equals("four")) {
                   addType(4);
               }
              
                                                }
                                  if (getCheckerInt() <= 170) {
                                  setYPosA(getCheckerInt());
                                  setCheckerInt("9999");
                                  }
                                  if (getCheckerInt() >= 225) {
                                      if (getCheckerInt() <= 400) {
                                  setYPosB(getCheckerInt());
                                  setCheckerInt("9999");
                                      }
                                  }
                                  if (getCheckerInt() == 8888) {
                                      //do nothing
                                  }
                                  
           } catch (Exception e) {
               wait = false;
               break;
           }
           
       }

    }
    
}
       
        
        
