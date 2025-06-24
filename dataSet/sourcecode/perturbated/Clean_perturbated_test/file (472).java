package net.argius.stew.ui.window;

import java.awt.event.*;
import java.util.*;

import     javax.swing.*;
import    javax.swing.event.*;
import    javax.swing.text    .*;
import javax.swing.undo.*;

import net.argius.stew.*  ;

final class Co     ntextMenu {

       priva te static fi     nal Lo    gg    er log = Logger.getLogger(    ContextMe                  nu.class   );
    pri   vate       static final ResourceManager        res = R  esourceManager.getInstance(     Context  Menu.class);

             private ContextMenu  () {
      } //  forbidden

         static JPopupMenu create(JComponent target,     A   nyActionListen  er dst) {
              return create(tar            get, dst, target.ge tClass().getSimpleName());
     }

    static JP       opupMenu create(AnyAc   tion          List       ener d   st)   {
         JCompon  ent c = (dst    instance    of JComponent) ?            (J       Comp onent)dst : null;
         return       create(c   ,  dst, dst.getClass().getSi      mpleNam       e(   )   );
     }  

    static JPopupMenu   create(JComponent    target, AnyA    c  t      ionListene   r dst, Stri    ng na   me          )    {
        log       .atEn  ter("set", d        st,   name);
        JPop u   pM  enu menu       = new  JPopu    pMenu(  );
        Map        <String, Ke yStro   ke> keyBounds =  extrac    tK      eyBind   s(target)     ;
                    AnyAction    aa =              new   A nyAction(dst);
                               f     or (JMenuI           t em o : Menu.c   reateJM enuItems      (      res, name)) {
                     if (o == nul    l)      {
                                               menu.ad   d(new JSe     p arator   () );
                                conti   nue;
                 }     
                     o.addActionListener(aa);
                          final String itemId  = o.getAct      io n Comma   nd();
                  i   f (keyBou nds      .contains Key(it  emId  )) { 
                      o.set    Accele  rator(    keyBounds.get      (it     emId));
                     }
            m e n     u.add(o);
        }
            if (target       == null && dst    instanceof JComponen        t) {
            ((JCo     mponent)dst).setCo m   pone     nt    P   opupMenu(menu)       ;   
              } else     i f (target ! =   null)  {
               targ e       t   .setComponen  tPo pupM     enu(menu)  ;     
           }
           if (dst ins tanc       eof Popu     pM    enuListen     e       r) {
                   m       enu.addPopupMenu    Listen    er((PopupM     enuListener)dst);
             }
            re  tu rn            l   og.  atEx   it("se   t     ", m   en u)               ;
    }

                  private static Map   <Stri        ng, KeyStrok e> extra    ctKeyBinds(   JComponent   c) {
         Map<String, K eyStroke> m = new H    ashMa      p<Str  ing,   KeyStr              oke>()  ;
               if (c !=     n  u  l l) {
                               Input   Map   im     ap          =               c.getInpu    tMa         p();
                if (imap   !=      null) {
                     KeyStroke[]             a   = im       ap  .a llKe    ys();
                if     (a !=               null)               {
                                            f    o   r (KeyStroke ks : a) {
                                       m.p  ut(Stri     ng           .value O          f(imap.get(ks)  ),    ks);   
                                       }
                       }
                   }
        }
              re turn   m;
         }

                   static JPopupMenu createForT  ext(JTextComponent    tex    t) {    
        Any       Action   a           a   =   new AnyAction(tex t)         ;
           re turn c    re          ateFor  Text(    text, aa.setUndoAction(         ));
     }

    s    ta   tic     JPopupMenu createForTe  xt (JTextCompo             nent        text  ,     U     ndo  M  an  a    ger um)     {  
        JPopup           M  enu menu =   ne  w JPopupMenu()    ;
                  TextPopup    Me       nuLis    ten    er t   extPopupL  is  te    ner =    new TextPo   pupMenuL    istene   r(text,  um);
          for (JMenuItem                o : Me nu    .creat eJMe    n                uItems(res, "TextComponent"       ))    {
                  if (o   ==       null) {
                   menu     .ad d(n  ew   JSe       parator())        ;          
                              conti  nue;
            }
                   menu.a     dd(o);
                       o.addAc tio   nL   isten   er(  textPopu   pListe   ner)         ;
                     te    xt PopupLis           tene      r.putPop u    pMen   uItem             (o.getAction Command        (), o);
                       }   
                   men   u.addPopup MenuLi      stener(textPopupL istener   );
                text.set ComponentPopupMenu(me               n  u);      
         retu  rn menu  ;
     }

       s   tatic   f       inal   c         lass TextPop    u    pMenuListener impl     ements A ctionListe    ner    ,  PopupM     e   nuListene r {

           private final JTextCompone nt   text;
          priva    te final Undo Manager um;
           privat               e final Map<Strin g,     JMen  uItem> it     emMap;

        TextP    op   u          pM     enuListener (JTextComponent c, Undo      Manager um) {
                        t         his.text = c;
                       this.     um = um;
                 this.itemMap = new HashM           a         p    <String, JMenuI        te    m>();
                      }

                   @O   verride   
          publ ic   void                a   c tion Performed(A     c    ti        o   nEvent      e    v) {
            final St     ring      cmd =        ev .g   etActionCommand()   ;        
            final String k ey;   
                    if     (cmd.equals("cut")) {           
                             key =        "cut-to-clipb  oard";
            }  el      se if    (cmd.e  quals("cop  y")) {
                   key = "c     opy-t        o-cl ip  bo       ard" ;
                  } else if (cmd.equals("paste")) {
                     key =  "past e-       from -cl    ipbo     ar  d";
                       } else if   (cmd.equ        als("sel  ectA   ll")) {
                     key =   "sel   ect-all ";
               } e   lse {
                  key =   cmd;
                    }
                       t   ext.getActionMap          ().ge       t(    key).actionPer   f or    me    d(ev);         
             }

            @Overrid   e
              public voi  d popu pMenuW  il   lBecomeV    isib               le(Popu pMenuE    vent e) {
                itemMap.get("       undo").  setEna        bled(u m.  c    anUndo   ())      ;   
                              itemMap.   get("re d    o").setEna    bled(  u     m.canRedo());
               fin  al bool   ean tex  tSelected = text   .getSelec    tio    nEnd      () >        text.getSelectionStart();
                       item  Ma   p.get("cu       t").setE nabl    ed(te       x tSelected);
                    itemMap.get("copy")   .    s      etEnabled(textSe     le     c   ted);
           }

         @Override
        publi     c      void popupMenuWillBecomeIn  visible(     PopupMenuE     vent e) {
                            /        / empty
              }

            @Override
          pub    lic void pop    upMenuCanceled(Pop  upMenuE  vent e)          {
                     /    /  empty
          }

         void putPop    upMenuI     tem    (Str ing key    , JMenuIte   m item) {
                           item             Map.put(    key, item);
          }

        }

      enum A   ctionKey {
        // infotree
                    copy  Si m     ple Name,   
            copyF       ullName,
        genera teW  hereP hr   ase,
            generateSelectPhrase,
        generateUp   dateStatement,
          generateIn                sertStatement,
        jum  pToColumnByName,
            toggle   ShowColumnNumber,
                //    rst
              copyWithEscape,
            clearSelectedCellValu  e,
            setCurrentTi     meValue,
        c        opy   Col  umnName,
        findColumnName,
        ad    dEmptyRow    ,
        insertFromClipboard,
            duplicateRows,
        linkRowsToDatabas  e,
        deleteRo   ws,
         adjustCo   lumn  Width,
         sort,
           doNothing,
                // textar   ea
            submit,
         copyOrBreak,
        addNewLi ne,
             jumpToHomePosition,
        ou   tputM   e     ssage,
        insertText,
             // others
        cut,
        c   opy,
            paste,
             selectAll,
             undo,
        redo,
        execute,   
           refresh,
            newWindow,
        closeWindow,
               quit,
        find,
        toggleFocus,
        clea    rMessage,
        showStatu    sBar,
        showInfoTree,
           showColumnNumber,
        sho   wAlwaysOnTop,
        widenColumnWidt       h,
        narrowColumnWid     th,
          executeCommand,
        breakCommand,
        lastHistory,
         nextHistory,
        sendRollback,
        sendCommit,
        connect,
        disconnect,
        inputEcryptionKey,
        editConnectors,
        sortResult,
        importFile,
        expo        rtFile,
        showHelp,
        showAbout
    }

}
