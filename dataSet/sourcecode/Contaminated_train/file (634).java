package assserver;
import java.awt.Color;
import java.net.*;
import java.io.*;
/**
 *
 * @author Shinobi
 */
public class connMan implements Runnable {
    private Socket server;
    private String ln, input;
    ObjectInputStream in;
    ObjectOutputStream out;
    String msg;
    int tID;
    String pID;
    private makeGUI mGUI;
    String cm;
    boolean newCM, gs, to;
    player tPlayer = new player();
    private makeServer tms;
    private pdt pp = new pdt();
    connMan(Socket server, makeGUI m, makeServer ms)
    {
        this.server = server;
        ln = "";
        input = "";
        msg="";
        pID="";
        tID = 0;
        mGUI=m;
        newCM = gs = to =false;
        tms=ms;
    }
    public void run(){
        try
        {
        //System.out.println("Thread ID (tID)="+tID);
        pp.pal("Thread ID (tID)="+tID);
        out = new ObjectOutputStream(server.getOutputStream());
        out.flush();
        in = new ObjectInputStream(server.getInputStream());
        sendMsg("Connection accepted");
        pp.pal("Connection accepted");
        do{
            try
            {
            msg = (String)in.readObject();
            //for setting the player names
            if(msg.contains("tID="))
            {
                String[] temp = msg.split("=");
                pID = temp[1];
                String ts = ("Player "+(tID+1)+" name is: "+pID+"\n");
                pp.pal(ts);
                mGUI.setPlayerName(tID, pID);
                mGUI.chat.append(ts);
                //send the player their hand and print it to GUI
                sendMsg("Your Hand:");
                String pht1 ="ph=Card 1: "+tPlayer.pHand.card1.cardFace+
                        " of "+tPlayer.pHand.card1.cardSuit;
                String pht2 ="ph=Card 2: "+tPlayer.pHand.card2.cardFace+ 
                        " of "+tPlayer.pHand.card2.cardSuit;
                sendMsg(pht1);
                sendMsg(pht2);
                pht(tID, pht1);
                pht(tID, pht2);
                pp.pal("Player 1: "+pht1);
                pp.pal("Player 1: "+pht2);
               //send the player their total and print it to GUI
                String ptt = "pt= Total: "+tPlayer.pHand.totalVal;
                sendMsg(ptt);
                pht(tID, ptt);
                pp.pal("Player 1: "+ptt);
                gs=true;
            }
            //twisting
            if(msg.contains("tw="))
            {
                tPlayer.isTwisting();
                String tw = pt(tPlayer.nCards);
                pht(tID, tw);
                String ptt = "pt= Total: "+tPlayer.pHand.totalVal;
                sendMsg(ptt);
                if(tPlayer.pTotal>21)
                    sendMsg("bust=You Went Bust!");
                    pp.pal("Player 1: Went Bust!");
                pht(tID, ptt);
            }
            //sticking
            if(msg.contains("st="))
            {
                tPlayer.isSticking();
                String st = "Player " +(tID+1)+" is sticking.";
                pht(tID, st);
                pp.pal(st);
                tPlayer.turnOver=true;
                tms.gt.start();
            }
            //For chatting
            if(msg.contains("cMsg="))
            {
                String[] um = msg.split(":");
                cm = um[1];
                mGUI.chat.append(pID+" says: "+cm+"\n");
                newCM = true;
            }
            System.out.println("Thread, "+tID+" : " + msg);
            //just in case player goes bust
            if(tPlayer.pTotal>21)
            {
                tms.gt.start();
                mGUI.p1h.setForeground(Color.red);
                mGUI.p1h.append("Player went bust!!");
                pp.pal("Player 1: Went Bust!");
                tPlayer.turnOver = true;
            }
            
        if(tms.leGame.winrar)
        {
            sendMsg("w=You Won!!");
        }
        if(tms.leGame.nwinrar)
        {
            sendMsg("w=You Lost");
        }
  
    }//end of try
            catch(ClassNotFoundException classnot){
            System.err.println("Data received in unknown format");
            }
          }while(!Thread.interrupted());
        } //end of try
     
        catch (IOException e)
        {
            System.out.println("IOEXception on socket listen "+e);
            e.printStackTrace();
        }
        finally{
            //Closing connection
            try{
                in.close();
                out.close();
                server.close();
            }
            catch(IOException ioException){
                ioException.printStackTrace();
            }
        }
     
    }//end of run()

    

    
void sendMsg(String m)
    {
        try{
            out.writeObject(m);
            out.flush();
            System.out.println("sent to client=>" + m);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

void pht(int id, String m)
{
    String p;
    if(m.contains("="))
    {
        String[] temp = m.split("=");
        p = temp[1];
    }
    else{p=m;}
    if(id==0)
    {
       mGUI.p1h.append(p+"\n");
    }
    if(id == 1)
    {
       mGUI.p2h.append(p+"\n"); 
    }
    if(id == 2)
    {
       mGUI.p3h.append(p+"\n");  
    }
    if(id == 3)
    {
       mGUI.p4h.append(p+"\n"); 
    }
}

String pt(int id)
{
    String temp= "";
    if(id == 3)
    {
        String c3 ="Card 3: "+tPlayer.pHand.card3.cardFace+" of "
                             +tPlayer.pHand.card3.cardSuit;
        pp.pal("Player 1: "+c3);
        sendMsg("ph="+c3);
        temp="ph="+c3;
    }
    if(id == 4)
    {
        String c4 ="Card 4: "+tPlayer.pHand.card4.cardFace+" of "
                             +tPlayer.pHand.card4.cardSuit;
        pp.pal("Player 1: "+c4);
        sendMsg("ph="+c4);   
        temp="ph="+c4;
    }
    if(id == 5)
    {
        String c5 ="Card 5: "+tPlayer.pHand.card5.cardFace+" of "
                             +tPlayer.pHand.card5.cardSuit;
        pp.pal("Player 1: "+c5);
        sendMsg("ph="+c5);  
        temp="ph="+c5;
    }
    return temp;
}

}//end of class
