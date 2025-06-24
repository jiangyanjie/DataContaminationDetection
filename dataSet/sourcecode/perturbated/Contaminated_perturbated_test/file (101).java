package br.com.semeru.exceptions;

import  java.io.PrintWriter;
import   java.io.StringWriter;
imp   ort java.util.Iterator;
import java.util.Map;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
imp    ort javax.faces.application.NavigationHandler;    
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent; 
import javax.faces.event.ExceptionQueuedEventContext;
 
//Inicialmente deve   mos imple mentar a classe CustomExceptionHandler q  ue ex   tende a cl  asse Exceptio   nHandlerWrapper
public class CustomExceptionHandler extends E    xcep   t ionHandle    r  Wrapper {
     
    private Exc                eptionHandler w    rapped; 
 
       //ObtÃ©m uma instÃ¢ncia do FacesCon  tex    t
    final    FacesC    onte  xt    facesContext = FacesContext.getCurrentIns   tance();
 
    //ObtÃ©m um mapa do FacesCo   ntext
    final Map requestMap = fac  e  sContext.get   ExternalCon     text().g   e  tRequestMap();
 
      //ObtÃ©m o estado atual da navegaÃ§Ã£o ent      re pÃ¡ ginas do JSF
    final  NavigationHandle  r navigatio   nHandler = facesContext.getApplication().getNavigationHandl        er();
 
    //Declar   a o c    onstrutor que re   c     ebe uma exception do tipo Except   ionHandler como parÃ¢metro
                     Cus   tomExceptionHand   ler(ExceptionHandler  exception) {
              this.    wra    pped = excep          tio       n;
    }
 
       //Sobres    cr   eve o mÃ©todo Excepti   onH    and  ler que r   etorna a "pilha   " de   e   xcessÃ     µes      
    @O    verride
    publ            ic ExceptionHandler getWrapped(  ) {
                  return wrapped ;  
    }
 
                       //Sobr escreve o mÃ©todo handle que Ã© re      sponsÃ¡vel por m  an    ipular a s exceÃ§Ãµes do JSF
    @Override
              pub lic vo     id handle() thr ows Fac       esException {
 
        fin    a     l Iterator itera   tor = g  etUn    handled         ExceptionQueuedEvents    ().i      te rato     r();
              while (iterator.hasNext()) {
               ExceptionQ     ueuedEven    t eve    n t = (Excep            t     i   onQueuedEv    ent) iterator.next(       );
                              Ex     ce    pt       ionQ    ue       ued   Even    tContext context =       (Excep   t   ionQu      euedEventContext) event.getSourc       e(    )        ;
   
            //    Re  cup   era        a       exce    Ã§Ã£o      do contexto           
                             Thro   wa   ble  exc eption = conte  xt.getExcepti    on(  );
    
            //  Aqui     te         ntam   os tratar a exeÃ§Ã£o   
                     try   {        
 
//                          // Aqui v  ocÃª pod      eri   a por exem   p   loinstanciar    as cl      asse   s St       r    ingWriter   e Pr    intWriter
                              Str    ingWrit    er strin gWrite   r     = n        ew S     t    ringW   riter();
               		Pr  intWrite r pr  intWriter      = new Pr  int  W        riter(stringWrit     er);
                			exceptio   n.printSt          ackTra   c         e(printWr        iter);
                                    // Por fim      vocÃª pode    converter a    pilha de ex   ce      Ã§Ãµe  s em uma String
                String messa          ge = stri ngWrit    er          .                        t     oString(             ) ;

                                       // Aqui v     ocÃª pode        r   ia envia r um    email             co   m a StackT       race
                       // em anexo para a   e    qui    pe de desenvolvimento

                           // e depo    is im  primi     r a sta  ck     trace no l   og
                      except    ion.pri   ntStackT   race();  
  
                                          // C oloca      u        m          a men    sagem de exc eÃ  §Ã£o no    mapa da request
                                           req   uest   Map .put("excepti  onMessage",      exception.getMess        age())   ;
 
                     // Avisa o usuÃ¡rio do erro
                Fa  cesContext.getCurrentInst    ance().addMessage(      nu   ll, new       Face   sMes    sage
                           (Faces         M     ess    a    ge.SEVERI    TY_ERROR, "O  sis  tema se  recuperou de um    e   rro inesperado.", ""));
      
                   // Tranquil     iza o       u suÃ¡rio para que ele continue usando o   sis  tema    
                  FacesContext.getCurrentInstance().addMessage(null,    ne    w F    acesMessage
                         (Fa  cesMe  ssage.SEVERITY_INFO, "VocÃª pode contin     uar usando o sistem   a normalmente!", ""))           ;
 
                // Seta a navegaÃ§Ã£o para um   a pÃ¡g       ina padrÃ£o.
                     nav  igationHandler.handleNaviga    tio     n(facesContext, n  u  ll, "/restric  t/home.faces");
 
                     // Rend    eriza    a pagina de erro e exibe as mensagens
                facesContext.ren derResponse();
            } finally {
                // Remove a exeÃ§Ã£o da fila
                iterator.remove();
            }
        }
        // Manipula o erro
        getWrapped().handle();
    }
}
