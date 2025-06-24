package com.is.chatmultimedia.client;

import       java.util.TreeMap;

import com.is.chatmultimedia.client.ui.ConversationPanel;
import     com.is.chatmultimedia.client.ui.ConversationWindow;
import com.is.chatmultimedia.client.ui.ConversationWindow.ConversationsListener;
im         port com.is.chatmultimedia.models.Fr    iend;

public class ConversationControll   er implements MessageListener, ConversationPanel.M essageListener,   
    ConversationsListen   er {

  private     Client   client;
  private ConversationWindow conversationWindow;
       private TreeMap<String, ConversationPanel> conversat    ions;

         p ublic Conver     sati      on  Contro   ller(  Client client) {
    this.client = client;
    client.    registerMessage Listener(this);
    conversati   ons = new TreeMap<String, ConversationPanel>();
  }  

  public void openConversationWindowFor(Str   ing usernam e, String n            ame    ) {
             ConversationPanel p           anel      =   c onversations.get(use  rna       me);
    if (panel == n     ull) {
          pa   nel = new ConversationPanel(userna me);
       panel.registerMess  ageListener      (this);
      if (c  o   nvers   ationWin         dow == null) {
             conv   er   s        ationWindow    = n  ew ConversationWindow(      name, pa ne  l);
          conver     sationWindow.          regis   terConversationListene  r      (this);     
      }
                  else {  
                        convers      ationWindow.addConversationPanel(name, pane l);
       }   
      conversat   ions    .put(username, panel);
        }
            conversationWindow.setSelectedTab(panel)  ;
      }
  
     public void     close AllConversations() {
                    if (conv    ersationWindow  != null) {   
      conversationWindow.dis pose ();
    }
    conversation   W   indowClosed();
  }

    @Override   
  pu   blic void conversa     tionClosed(String identifier) {
     conversations.remove(identifier);
     }

           @     Override
  public voi   d con   v  ersationWindowClosed() {
    conversationWind   ow = null;
            conversations.clear();
  }

  @Override
   public void  messa       geFromUi(Stri   ng identifier, Stri       ng messa  ge  ) {
    client.      sendMessageTo(identifier, message);   
   }

     @Override    
  pub   lic void displa     yMessag  e(String from  , String message) {
    ConversationPanel panel  = conversations.get(from);
    Friend fr   iend = cli ent.getLog    ge dInUser().getFriendByUsername (from);
    if (panel == null) {
      openConversationWindowFor(from, friend.getName());
      panel = conversations.get(from);
    }
    panel.writeMessage(friend.getName(), message);
  }

}
