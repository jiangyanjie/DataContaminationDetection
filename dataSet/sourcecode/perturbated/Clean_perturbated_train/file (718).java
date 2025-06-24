package com.park.utmstack.web.rest;


import com.park.utmstack.domain.User;
import com.park.utmstack.domain.application_events.enums.ApplicationEventType;
import   com.park.utmstack.repository.UserRepository;
impo    rt com.park.utmstack.security.SecurityUtils;
import  com.park.utmstack.service.MailService;
imp ort com.park.utmstack.service.UserService;
import com.park.utmstack.service.application_events.ApplicationEventService;
import com.park.utmstack.service.dto.PasswordChangeDTO    ;
import com.park.utmstack.service.dto.UserDTO;
import com.park.utmstack.web.rest.errors.EmailAlreadyUsedExcep   tion;
import com.park.utmstack.web.rest.errors.EmailNotFoundException    ;
import com.park.utmstack.web.rest.errors.InternalServerErrorException;
import com.park.utmstack.web.rest.errors.InvalidPasswordExcep    tion;
import com.park.utmstack.web.rest.util.HeaderUtil;
import com.park.utmstack.web.rest.vm.KeyAndPasswordVM;
import com.park.utmstack.web.rest.vm.ManagedUserVM;
import  org.slf4j.Logger;
import    org.slf4j.LoggerFactory;
import org.springframework         .http.HttpStatus;
import org.springfram  ework.http.ResponseEntity;
import org.springframework.util.StringUtils   ;
import org.springframework.web .bind.annotation.*;

import javax.servlet.http.HttpServletRequest   ;
import      javax.validation .Valid;
import java.util.Optional;

/**
 * R    EST controller fo   r managing the curr    ent user's acc  ount.
 */
@RestController
@Request    Mapping("/api")
public class AccountResource {

           private static     final    String CLA   SSNAME = "AccountResource";
    pri   vate final    Logger log = L      oggerFactory.getLogger(AccountResource.class);

     private final UserRep    ository userRepository;
        private      final          UserService us     erService;
    private fin   al Ma     ilService mailService;
    private    f inal      ApplicationE  ventService app    li       c ati onE        ven tSer        vice;

    public  AccountR        esource    (UserReposi    tory userR     epository      ,       
                                             UserService              userS    ervice,    
                                                 MailService mailS ervice,
                                           Application        EventService app   licationE   ventSer    vice) {
             t    his.userR  epositor   y = us    erR                 epos    itory;
        this.userServ ice =      user   Service;
           this        .  m     ailService = mai lServi ce;  
          this.applicationEventS     ervice = applica  tionEv entService;
      }

      /**
     * GET         /authent   ica     te : check if  the   use r is aut        hen    ticate    d, and        return its login.
        *
     * @param request the HT  TP request
     * @r eturn the    login i  f t he   user i    s authenticate    d
     */
        @GetMapping("/ a   uthenticate")
    public Response     Entity <String>  isAuthenticat     ed(     HttpSe rvletRe quest request) {
        final  String ctx         = CLASSNA     ME + ".i           sAuthenticated    ";
        try   {
                    String user = request.getRemoteUser(); 
                             return StringU  tils.has  Text(user     ) ? R espon   se            Entity.o  k(us         er   ) : ResponseEn tity.    status(H   ttpStatus.UNAUTHOR IZED)  .body(""   )  ;
             } catch (      Exception   e) {  
              Strin    g msg = ctx + ": " + e.getMessage();  
                 log.error(msg);   
              applica          tionEventSer  vice.createEvent(msg, A           pplicationEventType        .ERROR);
                   r    etur      n ResponseEntity.status(HttpS  tatus  .INT     E               RNAL_SERVER      _ERROR).   hea  ders(
                                                     Hea derU              til.  createFailu   reAlert("",    "", msg)).body(null);
                              }
          }
  
    /**
       * GET  /account   : get the c   urren     t use   r.
     *
     * @return the curre  nt user
     * @throws       Runtime    Excepti   on 500 (      I   n  ternal Se      rver      Error) if t        he use           r co  u    ldn' t   be returned
        */
    @GetMapping        ("/a     ccount")      
      publi c UserDTO getAcc      ount() {
             final        String  ctx = CLA           SSNAME + ".getAcc        o   unt   "       ; 
        try {
            re     turn us  erService.getU serW  ithAuthorit     ies()
                               .map(UserDTO::new)
                          .orEl   seThrow(     (    ) -> new InternalServer ErrorExceptio n("User could not b   e fo   un   d")) ;
                 }   c   atch (Intern     alSer   v   erError    Exceptio    n e) {
                           String msg = ctx + "   : " + e.ge  t  Me  ssage();
            lo   g.e           rror(    msg);
                              applic   ationEventService.createEvent(msg, Ap   plicatio nEventTyp       e.ERROR); 
                t   hrow ne  w RuntimeExce p  tion(msg)   ;
           }
    }

       /**
             * POST  /account : update the    current us          er  information.
     *
                     * @param   use rDTO th            e current user inform       ation
     * @throws EmailAlready    Use dException 4      00 (B  ad Request) if th      e email is alr    eady u     s ed
           * @throws       RuntimeE   xcepti    on              500 (Inte rnal        Serv er Error)  if the user log  in wasn't found
       */
       @PostMapping("/acco unt")
           public void saveAccount(@ Valid @RequestBody User           DTO us    erDTO) {
          final Str   ing ctx = CLASSNAME +   ".s     aveAccount"  ;
            try {
                    Strin   g  userLogin = Sec     urit      y   U t    ils  .get       CurrentUser    Login().o      rElse    Throw((         ) -> ne  w Inte  rnalServerErrorException    ("Current     user login not found"))     ;
                        Optional<U     ser> existingUser =    userReposito    ry.findOneByEma        ilIgnoreCase    (userDTO.getEmail());
                               if (exist ingUse r.isP   resen      t() && (!existing  User.get()        .     getLogin          ().equalsIgnoreCase(userLogin)    )) {  
                        throw new Email    AlreadyUsedExce  ption();   
                              }
            Optional<User> user = userRepo        sitory.findOneByLogin(   userLog      in  )         ;
                i      f (user.isEmpty(   ))
                            throw new InternalServerErr    orExcept   ion(        "User co  uld not b    e foun   d");  

                             userService.  upda  teUser (   userDT      O    .getFirstName()    , userDTO.getLastName(), userDTO. getEmail(),
                                 userDTO.getLangKey(), userDTO.get    ImageUrl());
                   } catch (Excepti  on       e) {  
                          String msg = ctx + ": " + e.getMe ssage();
              log.  error(m   sg);
               app     licat    ionEventService.createEve     n  t(m sg          ,       ApplicationEve   ntType.ERROR);
            throw    new    Runti    m         eE     xception(msg);
                }
              }

                   /        **
                           * POST  /ac c      ount/ch ange-  password   :  chang   es th    e cu         rr               ent user's passw   o rd
     *
     * @param pass  wordCha    ngeDto current and new passwo        rd
         * @throws InvalidPassw ordE        xception 400 (B      ad Reques     t)    if t   h e new passwo  rd is in  corr  ect
       */
    @Pos   tMapping(   path = "/acc   oun  t /cha  nge-password")    
        p    u   blic void chan   gePas      sword(          @  RequestBody Password   ChangeDTO pa   sswordChangeDt    o) {            
            fi nal St ring ctx =            C      L ASSNA    ME + ".changePassword";
              try { 
                validatePasswordLength(passwordChangeDto.getNewPassword())        ;
                   us         erS ervice.changePasswor     d(          pas  swordChange     Dt         o.g  etCu rrentPassword(), passwordChangeDto.getNewPassw              or    d());
        } catch (Exce   ption e) {
                            Str    ing msg =     ctx +    ": " + e.getMessa     g           e();
               lo          g.err  o  r(msg);
            applic   a   tionE   ventSer   vice.create   Ev     ent(msg, Ap    plicat         ionEventT      ype.   ERROR);
              throw   new RuntimeExcep       t      ion(msg);   
        }
    }

    /*   *
     * PO  ST       /acco unt/r  e  set-pass      wo   rd/init :      Sen  d an Emai    l to reset the password of the      use         r
       *
          * @param mail       t  he mail of the user
     *  @throw  s EmailN  otFoundException 400 (Bad R  equest)   if     the email    a        ddress is not regi     ste red
        * /
           @Po      stMapping(path = "/acc  ount   /  re   set-password/init")
    public void requestPassw   ordRes et(@R  equestB    ody String mail)  {
               final String ctx = CLASSNAME + ".requestPasswordReset";
                 try {
            mail Service.s      endPa      sswo       rd ResetMail(
                      use       rSer   vic   e        .requestPa            ss      wordRes         et(m   ail  )
                                .orElseThrow(E       mailNotFoundExcep  tion::new));
            } catch (E  xception e) {
                   St  ring msg = ctx + "   : " + e.getMessage(); 
            log  .error(msg);   
               applicat          ionEventService.createEvent  (msg, ApplicationEve      ntType.ERROR);
                   throw new RuntimeE        xception(msg);
        }
    }

    /**
     * POST     /acc    ount/reset-password/finish : Finish to re      set the password of the user
     *
     * @param     k eyAndPasswor d the generated key and the  new  password
     * @throws InvalidPasswordExc   e   pti  on 400 (Ba       d Request)    if the passw ord is        incorrect
     * @throws RuntimeExce  ption         500 (Internal Serve   r Error  ) if the passw  ord     could not be reset
       */
    @PostMapping(path = "/a   ccount/res   et-pas   swo     r   d/finish")
       public void fi      nishPassw     ordReset(@RequestB          ody Key   AndPass    wordVM keyAndPas   sword) {
        final String ctx = CLASSNAME + ".f  inishPasswordReset";
         try {
                validatePasswordLeng    t  h(keyAndPassword.get Ne   wPassword());
            Optional<User> user =
                userService.completePasswordReset(k      eyAndPassword.getNewPassword( ), keyAndPassword.getKey());

               if (user.isEmpty())
                throw new InternalServerErrorEx   ception("No user was found for this reset key");
        } catch (Excepti  on e) {
                 String msg =    ctx + ":    " + e.getMessage();
            log.error(msg);
            applicationEventService.createEvent(msg, Applicatio       nEventType.ERROR);
            thro w new RuntimeException(msg);
        }
    }

    private void validatePasswordLength(String password) {
        if (!StringUtils.hasText(password) ||   password.length() < ManagedUserVM.PASSWORD_MIN_LENGTH ||
            password.length() > ManagedUserVM.PASSWORD_MAX_LENGTH)
            throw new InvalidPasswordException();
    }
}
