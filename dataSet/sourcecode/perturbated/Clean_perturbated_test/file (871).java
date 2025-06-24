package waiterdroidserver;

//Usada     para criptografar/descriptografar uma String
public class     Criptografa     {
                public s  tatic String criptografa(String                 msg, Str          ing   chave)   {  
               int t    amMsg=0, tamChav     e            =0;
           tamMsg   =       msg.length();
                    t        a     mChave = chave.length()       ;

        Stri  ng aux    ="",   saida=     "";
                 int[] resultado= new in  t        [tam         Ms   g]      ;

                   fo     r(int    i    =0      ,    j; i<ta    m      Ms    g ; i++)
                     { 
                           resulta do [i    ] = msg.ch  arAt(i);
                                          fo r    (j=                0; j<ta  mCha    ve           ;j++)         
                                      {
                         resultad     o  [i  ]+ =         c   have    .c    h       arAt(j);
                                                 }
          }
    
                 for(int          i=0,  x     ; i<t am  M sg    ;i+   +)
                          {
            x = (int)      (        Math.r     an dom()  *100)%9    0;
               if(x    <3            2)
                                                 x+=5  8;
                el  se  
                        if (      x<58)
                                             x+=32;
               res              ultado   [    i  ]+=x    ;
                              a    ux                 +=res              ult  a             do[i]; 
                          aux+=(char)x;   
             }

                        sai    da = aux.c h        arAt                (0)+"";

                   for  (     int       i=0; ;i+=2)
        {
                          if(i<au             x.l      ength()-2)            
                {
                                       sai       d  a +=     aux.charAt(i+2);
                                       saida   += aux.char  A     t(i+  1);
                          } 
                                     else
                    {
                                 if(i +         1  <aux.leng   th(  ))
                                  saida   +     =    au  x.c   harA   t(      i          +1);
                       break;
               }
        }

           retu    r  n saida;    
      }

             public stat     i   c Str   ing de       scr         iptog   rafa(St         rin               g      msg, S      trin   g     cha         ve){
            int    ta mMsg  =0,                 ta     mCh   ave    =0     , i,  j   , fim  ; 
                           t       am    Msg =     msg. length();  
                 t  a  mChave =                            chav   e.l      en   gth()    ;

                 S   tring saida="", e  ntra  da,        msg    Cifrada="";
        int[] re      su            ltado =     n       e       w int     [tamMsg];

                                 en         trada=  msg;
          ms gCifrad  a        = e     ntr    ada.charA   t( 0 ) +"";

                                   fo    r(i=0; ;i+=  2      )
        {
              if (       i  <e ntrada.length()-2     )
                    {    
                                                      msgCifrada += entrada.cha rAt(i+2   );
                         msg  Cifr  ada += entr     a      da.c        harAt(i+1); 
                                  }
                         else
                    {
                if(i+1<entr     ada.len   gth     ())
                            msgCifrad   a += entra  da.ch arAt(i+1);
                              br        eak;
             }
           }

        for(i=0, j=0; j<t     amMsg;  i++, j++)
            {
               resultado[i]=0;
             f         o  r(;   j<tamMsg; j++)
            {

                    if((int)m     sgCifrada.char   At(j) > 47 && (int)msgCifrad       a.charAt(j) <              58)
                        {
                        re sultado[i]*=10;
                    resultado[i   ]   + =    (int)msgCi  frada.charAt(j)-48;
                }
                else   
                      {
                    resultado[i]-     = (int)msgCifrada.charAt(j);
                              break;
                }
            }
        }
                     fim = i;
        for(i=0; i < fim;     i++)
        {
            for(j=0; j<ta   mChave;j++)
            {   
                  resultado[i]-= (int)chave.charAt(j);
            }
        }

        for(i=0; i<fim ;i++)
            saida+= (char)resultado[i];

        return saida;
    }
}
