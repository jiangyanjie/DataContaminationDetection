package com.akto.action;

import    java.util.*;
import java.util.concurrent.Executors;
imp    ort java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import    com.akto.dao.CustomAuthTypeDao;
   import com.akto.dao.UsersDao;
import    com.akto.dao.context.Context;
import com.akto.dto.CustomAuth  Type;
import com.akto.log.LoggerMaker;
import com.akto.testing.ApiExecutor;
import com.akto.util.AccountTask;
import com.mongodb.BasicDBObject;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
     import com.opensymphony.xwork2.Action;
import com.akto.dto.User;
import com.akto.dto.type.SingleTypeInfo;
  import com.akto.uti    ls.CustomAuthUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

pub      lic class Custom  Au          thTy  peAction ex    tends UserAction{
    private   static final Logger l     ogger = LoggerFactory.getLogger(  C ustomAuthTypeAction.class);
    private String name;
    private   List<String>    headerKeys;
    private List<Strin     g> p     ayloadKeys;
    private boolean act   ive;
    private List<CustomAuthType>  customAuthTypes;
    pr    ivate Map<In   te    ge              r,Stri        n g> usersMap;
    private CustomAuthType customAut  hType;

    private    static final Sche    duledExecutorService     executorService = Exec   utors.newSi   ngleThreadScheduledExecu   tor();
         p  rivate st     a ti   c final LoggerMaker loggerMaker = new Logg      e  rMaker(CustomAuthTypeA      ction.class);

    publ   ic   String fetchCustomAuth   Types(){
          cus t     omAuthTypes = CustomAu  th      T  ypeDao.instance.fin           dAll(new Basic   DBObject());  
            Set  <Integer>   userIds = new HashS   et<>()    ;  
          f     or (CustomAuthType custom      AuthType: customAuthTy pes) {
                user    I    d       s.add(custo      mA  uthType.getCr             eatorId())     ;
        }
        usersMap =         UsersDao.instance.ge    tUsernames(us   erI      ds);
        ret ur     n Ac       ti     on.SUC           CESS.t   oUpperCase()  ;
    }

    public String     addC    ustomAuthTyp          e(){
        User     us     e   r = g    etSUser();
            custom  A        ut     h  Type = CustomAuthType              Dao.in stance.   f     in   dOne(CustomAut        hTyp   e.NAME   ,name) ;
        if(cust   omAuthType!=null){
            addActio  nError("Auth       type nam    e n    ee ds to be unique");
               return ERROR.to   UpperCase()    ;  
        } else {
            activ e = tru  e;
               custo       mAuthT   ype = new CustomAuth     Ty   pe(name, headerKeys , pa    yloadKeys, active,user.get    Id(   ), null, null);
             Custom   A    uthTypeDa  o.instance.in  sertOne(cu    stomA  u  thType);
         }
            fetchCu     stomAuthType  s();

           in             t acco     untId  = Conte   xt.accountId.get();
                       SingleTypeInfo.fetchCustomAuthTypes(  accountId)      ;
                    executorService.schedule(    new          Ru    nn             able()  {
                              pu  bli   c void run()    {
                                             Contex    t.accoun  tId.set   (acco   untId);
                           List<Cust   omAuthTy pe> customA         uthTy  p    es = S  ingle      T  ypeInfo.getCu stomAuthTyp   e(accountId);
                     CustomA   uthUtil.      customAuthTypeUt il(c  ustomAuthTypes);
             }
          }, 5 , TimeUnit.SEC  ONDS  );
        return Action.S   U    CC     ESS.    toUpperCase();
          }

    public           String updateCust     omAuthTy          pe   ()  { 
           User user = ge   tSUse r();
            customAuthType = Cus        t   omAuthTy   peDao.inst an  ce.find       One   (Cus to        mA uth     T  yp    e.NAME,name  );
         if      (cu s                     tomAu      th   Ty   p     e==null){
               addActionE   rror("Cust     om Aut   h T  ype does    not e     x  ist  ")  ;
                retu rn ERROR.toUpperCase();
        }       else if(user.get       Id(     )!=c usto     mA   uthTy   p  e        .g  et   C  r eatorI   d()){
            a           ddActionE rror(   "Unau    therized Request");
               ret            ur    n ERROR.toUpperCase();
        } else     {
             Cu stomAuthTypeDao.   in  stance.u               pdate        One(Filters      .e    q(Cus  to  mAuth  Type.NA  ME, name),
                           Upd    ate      s.combine(
                                                       Updat  es.s   et(CustomAut           hType.            ACTIVE, active   ),         
                                       Updat      e   s.se t("headerKeys"  , header       Keys),
                                 Updates.set("payloadK  e ys", payloadKe     ys),
                                                 Updates.s et(Custo    mAuthType. NAME, name),
                                       Up  dates.      s et("ti mes     tamp", Contex   t.now()      )));
          }
                 fe     tchCustomAuth  Types();
                int acc       ountId = Co ntext.ac    c o  un     tId.get();
        SingleTy    peInfo.fetch    CustomAut  hTypes(   account   Id);
          customAuthType  = CustomAuthTypeDao.instanc    e.fi ndOn   e(Cust   omA u    thType.  NA     ME,name);
        executorServic    e.schedule( new Run  nable()   {
            public vo  id run() {
                            lo    gger     .in  f      o(   "RUNNING   ");
                Context    .accountId.    set(acc    ountId);
                       CustomAuthUtil. customAu   thTypeUtil(  Singl   e  Type    Info.ge  tCustom  AuthType(accountId));
                }
            }, 5 , T   im   eUnit.SECONDS);
          re    turn Action .SUCCESS.to  U    pper     C as  e();
          }

            public     String updat        eC         u   sto        mAut hTypeStatus(){
            User   user = getSUser()   ;
             customAuthType = Custom       Aut  hTypeDao.inst  anc e  .findOne(CustomAuthType.N    AM     E,    n      ame);
          if(     custom  AuthType==null)  {      
            addActio          n    Error("Cust        om              Auth T   ype   do         es not ex    ist");   
                 return ERROR.toU   p  perC    ase();    
        } e       lse     if(user.getId()!=c  ust   omAuthType.getCreatorId()   ){
                addAct    ionError("Unautherized Reque s  t");
                     return ERROR.toUp  perCase(  );
         }    el se {
            CustomAuthTypeDao.instance    .u       pdateOne(Filters.eq(CustomAut  h Type.NAME, name),
                          Updates.comb       i     ne(
                                     Up    dates.set(Cu      sto  mAu thTyp     e.ACTIVE, acti        ve),
                                                                           Updates.   s   et("timestamp",Context.now())));
           }
        i     nt accountI        d        =  Context.acco       u   n    tId.get();
             fetc       hCustomAuthTypes   ();
        SingleT     ypeI       nfo.fetchCustomAuth   Type   s(acco   unt  Id);
           c    ustomAuthTyp      e = Cu     st  omAuthTypeDao   .instance.fin  dOne(  Cus     tomA  uthType  .NA  ME,   name);
        return Action.SUCCESS   .toU   pperCase()  ;    
        }
        
    public    Stri   ng re            setAll   Cus   tomAu  thT  ypes() {
        try {
                Cu   sto     mAuthUtil      .reset  AllCustomAuth    Types();
                   int     accountId = Context.acc     o     un  tId.get();
            Sing   leType    Info.fet     chCustomAuthTypes(a  ccountId);
            exec    utorSe   rvice.schedule    ( new Runnable()     {
                  public void run() {
                     Context.  accountId.set(   accountId);
                    CustomAuthUtil.cust    omA    uthTypeUt  il(SingleTypeInfo.getC  ustomAuthType(accountId));
                   }
            }, 5 , TimeU  nit.SECONDS);
            re     turn SUCCESS.toUpperCase();
        } catch    (Ex  ce ption e) {
              loggerMaker.errorAndAddToDb(e, "ERROR: Reset custom auth type s - " +    e.getMessage(), LoggerMaker.LogDb.DASHBOARD);
                 return     ERROR.toUpperCase();
         }
       }

    public void setNam     e(String name) {
        th is.name     = name;
    }

    public void setHeaderKeys(L  ist<String> headerKeys) {
        this.headerKeys  = headerKeys;
       }

    public void setPayloadKeys(List<String> payloadKeys)    {
        this.payloadKeys = payloadKeys;
    }

       p ublic void setActive(boolean active) {
        this.active = active;
    }

    public List<CustomAuthType> getCustomAuthTypes()   {
        return customAuthTypes;
    }

    public Map<Integer, String> getUsersMap()      {  
        return usersMap;
    }
    public CustomAuthType getCustomAuthType() {
        return customAuthType;
    }
}