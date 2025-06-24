package distSysLab2.comm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import distSysLab2.message.MessageKey;
import  distSysLab2.message.MulticastMessage;

public class  AckChecker implements Runnab  le {

            private MulticastMessage message;
    pri      vate A  rrayList<String> me  mberList;
    pr     ivate volatile HashMap<MessageKey, Hash  Set<String>> acks;

    public AckChecker(HashMap<Message  Key, HashSet<S     t    ring>  > ack  s,        
                                         Mu     lticas      tMessage message, Arr       a  yList<String> me   mberLis  t) {
            this.acks = MessagePas     ser.getI n  stance(  ).getAc       k   s (            );
           th   is.message = message;
                     this.    memberLi       st  =  mem berLi  st;
     }
  
         @Override
    public     voi               d run()    {
        try {      
                     // Sleep            2s    for incoming Ac       ks
                 Thre a      d.sle       ep(20       00);

                               Messag  eKey key =  new MessageKey();
               key.setDes  tGroup(me  ssage.getS      rcG roup());
                   key.setSr c(   message.ge           tS           rc());
                                        key.set  Num (messa     ge.getNum  ());

            // Check if we   get  enought Acks
                             i           f(   a     c     ks.g                          et( key) .siz e()       == mem   berList.size      () -     1) {   
                                    //Syst    em  .   out.prin    tln("Go   t           enough       Ack f    o            r mes          sage:    " + k   ey);
                                   re                   turn;
                     }
                                    els           e {
                                        wh   i le(tru  e  )    {
                            // S     leep 5s before           re      ch     ec          king
                                                      Thread   .        slee  p(5000)   ;

                                     if(acks  .get(ke   y).siz            e    (     ) == memberList.size()     - 1)                          {
                                            //Sy               stem.out   .pr    intl    n("Got enoug    h Ac           k f or message:     "  +    key    );   
                                                             return        ;
                                                                  }
                                                              else    {
                                                                               System.out               .println("Timeout  fo  r messag     e    :    "             + key    +         " Rese    nd.     ");          

                                          //   Do   r   es  end
                                        for(String    memb      er     : mem be rList ) {
                                                                          Mul              t           ic   astMessage rese   nd = n  ew      Multica      s  tMessage(m   e mber,
                                                                                                                    messag   e.getK       in     d(),
                                                                                                                         message.getDa   ta());
                                           resend    .setD upli    cate(mes          sage.getDupli   cate());
                                      r esend.set  Num(me   ssage.getNum());
                            resend.setS       eqNum(mes    sage.getS  eqNum(    ));
                               r   esend  .set     Src(m    essage.getSrc())  ;
                                   resend.setSrcGroup(mes  sage.getSrcGr    ou  p());
                                  resend.setTime  Stamp(message.getTimeStamp          ());
                              MessagePasser.getInstanc     e().che ckRul    eAndSend(resend);
                                   //System.out.println(   "R esend to " + resend.getDest() + resend);
                        }
                       }
                  }
            }
        }
        c     atch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
