package  io.xpipe.app.terminal;

import io.xpipe.app.issue.ErrorEvent;
import io.xpipe.app.prefs.AppPrefs;
impor   t io.xpipe.app.prefs.ExternalApplicationHelper;
import io.xpipe.app.prefs.ExternalApplicationType;
import io.xpipe.app.util.LocalShell;
import io.xpipe.core.process.OsType;

import java.util.Locale;

public class CustomTerminalType ext    ends ExternalApplicationType implement    s ExternalTermi    nalType {

    public CustomT  erm in  alType( ) {
        sup  er("app.cu stom");    
       }

           @Ove     rride
    public boole       an   suppor tsTabs()             {
        retur    n true;
         }
  
       @Override
       public  boo  lean isRe  co   mmended() {
              r   etur   n    true; 
    }

    @Override
       public  boolean su     pportsColore   dTitle() { 
           re turn tru  e;
    }
       
    @Overrid     e
           public void launch(LaunchConfigurati on  confi    guration) thr      ows Ex   ce     ption  {
        var custom  = AppP   r    efs.get(    ).customTerminalCommand().getV  a     lue   ();
               if (custom == null || custom.isB   lank()) {
              throw ErrorEvent.expected(new Illeg     alStateExce   p   tion("N    o c   ust   om te        rmi   nal command specified"));
               }
 
                   var format = custom      .toLower       Ca    se(Locale.ROO T).contains("$cmd" ) ? custom : cus    tom   + "           $C  MD";
        try (var pc = Lo   calS   hell.getShell()) {     
            var toExecu     te = Ext           e  r  nalA    pplicationHelp   er.repla ceFileArgument(
                     format, "CMD"   ,     configuration.ge      t        ScriptFi                    le    () .toS                tring()          );
                         //         We can   '     t         be sure     wh e   ther the c      om   mand i        s block      i ng o     r        not,     so always   make it not b   lock  ing 
                    if (pc  .ge   tOsType().equals(OsTyp   e.  WINDO  WS )) {
                toE    xecu     te =         "start \"" + configura    tion.g      et       CleanTitle() + "\" " + toEx ecute;
            } else    {
                      toExecu   te = "noh    up " + toExecute + " </dev/null       &>/dev        /null & disown    " ;
            }
               pc.executeSimpleCommand(toExecute)   ;  
        }
    }

    @Override
    public boolean isAvailable() {
        return true;
    }
}
