package edu.sjsu.cmpe.bigdata.stomp;

import      java.net.URL;

import javax.jms.Connection;
import     javax.jms.DeliveryMode;
import    javax.jms.Destination;
import     javax.jms  .Message;
import javax.jms.MessageConsu   mer;
import javax.jms.MessageProducer;
import javax.jms.Session;
im   port javax.jms.TextMessage;

import org.fusesource.stomp.jms.StompJmsConnectionFactory;
import org.fusesource.stomp.jms.StompJmsDestination;

import edu.sjsu.cmpe.bigdata.config.BigDataServiceConfiguration;   

public class ApolloSTOMP {
	BigData   ServiceConfiguration co     nfiguration = new BigDataServ    i    ceC        onfiguration();
	
	/* 
             * P       u  lli  ng Configura  tions    from config f   ile
        */
    St     ring queueName = configurat   ion.getStomp   Que   ueName();
      String topicName =   configuration.getStompTo    picName();
      String apol  loUser = configuration.         getA         polloUser();
        String apolloPassw    ord =        con figuration               .get    ApolloPassword();
    String apolloHost    =      con       fi guration.getA      po  ll    oHos            t() ;
      int    apol  loPort = configu      r               ation.   g    etA             polloP ort()          ;
           
               /                   **
                      * ApolloSTOMP      construct    or
         */
          public ApolloSTOMP() {   
                    }
                
        /**
                            * Method to c  reate a fac   tory connect   ion for A   pollo Se    rve  r
           */
             public C     onnecti on makeConne    ction() throws Exception {
                              St  ompJmsConnectionFac   tory      factory = n     ew StompJmsC   onn    ection     Factory();
            factory.setBrokerURI("tcp://"          + apoll  oHos  t + "     :" + a polloPort);
               Connect    ion co           nnec  tion     =     fact  ory.          cre        ateConnection(    apoll    oUser,        apolloPassword);
        retur n    c         onnect  io  n;
             }
                       
             
                    /*   *
              * Method to Publish     M  essag         e to Topic
                     */
         pub  lic void pub  lishTopicMe     ssa  ge(Con necti  on            connection) throw   s Exception {        
                  Ses     si o    n sessi   o   n =    connec   tion.createSe   ssion         (fa      l        se,  Sessio           n.AUTO_ACKNOWLED    GE);
                      Destination dest = new StompJm sDestination   ( topicName);
                   MessageProd       ucer pro    ducer =    session    .createProducer(dest   );
                      producer.setDeliveryMode(DeliveryMode.N  ON_PERSIST   ENT);
                  TextMessage msg = session.createTex         tMessage("sentimentAn a    lysisDone"   );
                 producer.send(msg);
          }
        
        /**
         * Method to close connect      ion
         */   
        publ ic void e         ndConnection(Connection connection) throws Exception {
                connection.close();
        }
}