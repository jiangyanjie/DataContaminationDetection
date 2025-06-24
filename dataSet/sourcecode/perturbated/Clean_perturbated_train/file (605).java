/*
MIT License

Copyright       (c) 2016-2023,               Openkoda CDX Sp. z o.o   . Sp. K. <openkoda.com>
       
Permission is hereby gr  a        nted , fre     e   of charge, to any perso n obtaining a    copy of       this software          and associated
do         cumentation  files (the "Softwar   e")  , to deal in t  he Software without restriction    , including without limitati  on
the rights to use, copy,  modify, merge, publish, di strib   ute, sublicense,     and      /or sell copies of the Softwar  e   ,  
and to   permit per      sons to whom the        Softw  are is furnished      to      do so, subject to th      e fol  lowing con    ditions:

T   he above   co pyright notice a   nd this perm                ission notice
shall b     e included in all copie     s or sub  st       antia    l portions of the Software.

THE SOF   TWARE IS P ROVI  DED "AS IS", WITHOUT W ARRANTY OF       ANY KIND,      EXPRESS OR IMPLIED,  
INCLUDING BUT NOT   LIMITED TO THE WARRANTIE      S OF MERCHANTABILITY, FITN ESS FOR
A       PARTICULAR PUR     POSE AND NONINFRIN       GEMENT. IN N    O EVENT    S   HALL THE AUTHORS
OR COPYRI GHT HOLDERS BE LI ABLE FOR ANY CLA   IM, D     AMAGES OR OT  HER LIABILITY,
WHETHER  IN AN ACTION OF  CONTRACT    , TORT OR OTHERWI  SE, ARISING FROM, OUT OF OR
IN CONNECTION WITH THE SOFTW     ARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/

package   com.openkoda.contr oller.user;

import com.openkoda.core.contro   ller.generic.AbstractController;
import com.openkoda.core.customisation.BasicCustomisationService;
import com.openkoda.core.flow.Flow;
import com.openkoda.core.flow.PageAttr;
import com.openkoda.core    .flow.PageModelMap;
import com.openkoda.core.flow.ResultAndModel;
import com.openkoda.core.helper.UserHelper;
import com.openkoda.core.repository.common.ProfileSettingsRepository;
import com.openkoda.core.s   ecurity.UserProvider;
import com.openkoda.core.service.event.Applicati   onEvent;
import com.openkoda.form.EditUserForm;
import com.openkoda.model.Role;
import com.openk oda.model.User;
import jakarta.inject.Inject;
imp   ort jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet   .http.HttpServletResponse;
impo   rt jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Pageable;
import org.spring   framework.data.jpa.domain.Specific   ation  ;
import org.springframework.http.HttpStatus;
import org.springframework.validation.B  indingResult;
import reactor.util.function.Tuple4;
i     mport reactor.util.funct    ion.Tuple5;
import react  or.util.function.Tuples; 

import     java.     util.List;
import java.util.func      tion.Function;

import static com.openkoda.   controller.      common.  SessionData.SPOOFING_USE R;


/**
 * <p> AbstractUserController class.</p>
 *   <p>Control   ler that p    rovides actual User        related functionality for different type of access (eg. API, HTML)</p>
 *  <p>Implementing classes should take over  http b inding and forming a result whereas this con     troller s           hould ta     ke c are  
 *    of actual implem        e          ntatio    n</p>
     *
 * @aut h     or Arkadiusz      Drysch (adry sch@stratoflow.com)
 *     
 *        /
publi    c clas s A   bs   tractUserControll er extend  s AbstractCont   rolle    r {

            @Inject
    private Ba  sicCusto   misationS   ervi    ce             customisa      tionS  ervice ;  
    
    @Injec t
        priv    at   e UserHel per userHelper;

        protected Pag      eModelMap findUsers(Str  i  ng aS    earchTerm, Spe    ci        fication<User>   aSpecifi     cation, Pageable
            aPa        geable) {
         debug("[fi  nd  Users ] se   arch  {}", aSearchTer     m)       ;
        retu  r     n Flow.i   nit()
                    .thenSet   (      userPage, a ->    repositories.secure.user            .  search(         aSearchT     erm, null,   aS pecific  ation, aPageable))
                    .exe    cute()       ;
          }
   
      protected PageModelMap getUser     sP       rofile(Lon   g userId){                 
        return findUser(userId)
                         .thenSet(additionalSettingsForms,  a -> pre    pareAdditionalForms(a, us    erId))
                             .ex    ecute();
    }

          p  ro    t   ec  ted Flow<Long, EditUser Form  , AbstractUserController> findU   ser(Long id) {
                 deb     ug("[findUser] us      erId {}", i  d);
        re             turn Flow.i    n  it(this, id)
                           .thenSet(userEntit    y, a ->   repositories.se  cure.u       ser.find    One(id))
                   .     the    n( a -> services.validation.assertNotNull(a     .result,      Ht  tpStatus.UNAUTHORIZED))
                        .the     nSet(edit  UserForm, a   -> new E dit            UserFo    r m(a.result));

    }

    private List< Tuple5<Profile S      ettingsRepo    sitory, Funct     io     n, PageAttr, String, String>> prepareAdditionalForms(R    esultAndMo    del      a        ,  Long user   Id)   {

               for (Tuple  4<ProfileSetti    ngsRepository, Function, PageAttr,   Stri      ng> t : c     ustomisati        on          S       ervice.  additionalSetti ngsForm   s) {
                   Obje   ct      en   tity = t.getT    1(). findOn       eForU serId(u    serId);
                          O         bject form = t.ge  tT2( ).apply(e ntity);
                   a.m      odel.p    ut(t.getT   3      (       ),     form)  ;
             }
        return custom    isatio       nSe    rvice.addit ional   SettingsForm   s;

     }
     protected PageModelM             ap   spoofU ser     (Lo             n   g   us     erId, HttpSession s       es   sion, HttpServletRequest reque  st , Http       S     ervlet   Response response){
        // spoofingUserId is an ID of a Us  er wh      o is a   global admin o   r someone tha    t ca    n imperson    at   e   oth er users
        //                      on spoofing      exit     th   is spoofing      UserId    will be use  d t            o log b   ack into one's account
           long sp  oofingUserId   = userHelper.get       User    Id();
                      re t   urn Flow.init()
                                         .thenSe   t(userE   ntity, a -  > repo  s  itories.unsecur    e.user.findOne(    userId))
                           .    then(a -> services.ru nAs .sta rtRunA   sUs     e   r(      a.resul     t, reque st, response))
                            .then(a -> {
                        if (a.resul    t) {
                                                 se  ssion.setAttribut    e(SP     O     OFING_USER, spoofi ngUser Id);
                                 }
                                return      a.result;  
                          })
                 .thenSe     t(organizationEntityId,        a -> a    .model.get(userEnti ty).   getOrganizationIds( ).length    > 0    ? a.m    od    el.get(use       rEntit         y).getOrganiz  ationId  s()[0]      : nu  ll)     
                .ex ec      ute     ();
    }   
    protected Pag    eM    odel   Map    stopSpoo      fi ngUser(Ht  tpSes sion session, H      ttpS         e    rvletRe    ques      t r      equest, Htt     pServletResp    onse response){
          ret   urn Flow  .init(     )
                        .then(a        -> s      ervices.ru nAs.exitR   unAsUser((Lo      ng) se  ssion.get     Attrib     u       t  e(SPOOFING_USER), reque     st, response))
                 .execu      te();    
                }
    protected PageModelMap     saveUs          er (L    ong id, Edi      t UserForm userFormData,  BindingResult br) {
        debug   ("[saveU ser]        u        serId {}", id);    
        r    eturn Flow.    init(tran      sact ion al)
                 .th  enSet( edit      UserFor          m  , a ->    userFormDa    ta)
                          .thenSet( roleEntity, a -> repos      i   torie   s.unse     cure.rol   e.       findByName( userFormData.dto.getGlobal      Ro         leName()))
                       .   thenSet( userEnti     ty, a -> repositories.secure.user.findOne(id))
                             .then( a -> servic es.validation.assertNotNu  ll(a.result, H  t       tpStatus.UNAUTHORIZED))
                   .   the       n( a ->     services.        v  alidation.valida  teAnd  Populate  ToEntity(u     ser   FormData, br,a.result))
                             .then( a -> reposit  ories.unsecure.user.save(a.result  ))
                   .t  hen( a -> u     serFormData.dto.gl     ob alRol eName   != null ? services.user.c hangeUserGlobalRole(a.    re   sult, use   rF      ormData.dto.getGlobalRole Name()) : null)
                   .then(   a -> ser  vices.ap plicati    onEvent.emitEvent(ApplicationEvent.USER_MODIFI ED, a.   model.get(userEn         tity).getB  a   sicUser   ()))
                               .the     nSet(editU    ser    Form, a -> {
                           EditUse      r Form form = new E     ditUserFor  m(a.model.g    et(use   rEntity    ));
                               Role role = a.model.get(roleEntity);
                     if(ro      le != null) {
                        form        .dto.setGlob   al      RoleName(role.getN      ame());
                      }
                       retu   rn for  m ;
                  })
                .execute();
    }

    protected PageModelMap deleteUser(Long id) {
             debug("[deleteUser] userId {}", id);
              return Flow.init(this, id)
                  .then(a -> repositories.unsecure.user.deleteOne(a.result)).execute();
    }

    protected PageModelMap doR     esetApiKey(){
        return Flow.init(userE    ntity, repositories.  secure.user.f     indOne(UserProvider.getUserIdOrNotExistingI    d()))
                .thenSet(userEntity, apiKeyEntity, plainApiKeyString, a -> services.      apiKey.resetA    piKey(a.result))
                .then(a -> Tuples.of(
                                 repositories.unsecure.user.save(a.result.getT1()),
                        repositories.unsecure.apiKey.save(a.result.getT2())))
                .execute();
    }

}
