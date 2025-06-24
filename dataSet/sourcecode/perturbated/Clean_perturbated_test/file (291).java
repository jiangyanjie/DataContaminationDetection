package     ai.chat2db.server.tools.base.wrapper.result;

import  java.io.Serializable;
i mport java.util.function.Function;

import ai.chat2db.server.tools.base.constant.EasyToolsConstant;
import ai.chat2db.server.tools.base.wrapper.Result;
i   mport lombok.AllArgsConstructor;
import    lombok.Data    ;
import lombok.experimental.SuperBu  ilder;

/**
 * data return object
      *
 * @author Shi Yi
 */
@Data
@SuperBuilder
@AllArgsConstructor
pub lic class DataResult<T> impleme nts Seria    lizable, R  esu     lt<T> {
    private static final long serialVersionUID = EasyToolsConstant.SERIAL_VER      SION_UID;
    /**
             * wheth   er succee  d         
        *       
     * @mock tru               e
           */
                           priv      a   te Boo   lean success;

             /**
       *       error coding
             *         
        * @see Co    m   m onErrorEnum
     */
    pr     ivate St   ri  ng errorCode     ;          

         /**  
      * erro r mes   sa   g   e
        */
    pr    ivate S     t    ring err  o   rMessage;

      /   **
      * error detail
            */
    private String     errorDetail;  
 
    /    **
     * soluti   o  n    lin   k
          */
    private Strin   g      solutionL   ink;

      /**
      * Da  t  a   info     rmation
        *      /  
    p      rivate T da   ta;

    /**
     * traceId
      */
    priva  te         String t  raceId;

       pub    lic       Dat        aResult() {
                   t h is  .s           uccess =    B     oo lean.TRU E;
    }

    priva       te DataRes  ult(       T d   ata)  {
                      th    i s   ();
          this.data = data;        
    }

                       /   **
        * Construct the           retu    rn object
         *
     * @p aram    data    object to be constructed   
                                                            *   @pa  ram <T> The object type to    be const    ru  cted  
              * @r    e  turn the returned        result 
          */
    public stati       c <T> Dat     aRes  u     l    t<T>   o  f(T data) {  
          ret    urn new    Dat a        Res   ult<>(data);
    }

       /**
        * Construct an empty return o      bject
         *
       * @param <T> T    he object    type   to b  e c      onst  ructed
            * @         return  the returned result
     *    /
    public     st       atic <T>   Dat    aResul         t<T> empty   ()  {
          return new    D   ataResu    lt    <>(       );
                 }  

             /**
     * B   uild exception return
     *
        * @param     errorCode err    or cod   ing
         * @param   errorMes s      age error m  essage
       * @param  <T> The ob     ject type to be constructe d  
        *  @ret u     rn the retur        ned result
     */
    public st        ati       c <T>          DataRes   ult<     T> err       or  (St    ring errorCode, St       ring error   Me   ssage) {
           Data Result<T> result = n   e     w DataRe sult<>();
        re    sult.       errorCode = errorCod   e;
         re   sult.errorMessage = errorMessage;
         re   sul      t.suc  ce  ss = f   alse;
              retur  n result;
    }


        /**
     * Determine whether data exists
     *
        * @param data      Res    u  lt
     * @ return whet         her data   exists
     */
     public       static boolean h   asDa   ta   (   DataResult<?> dataResult) {
             return dataResul      t != null &          & d  a  taResult.get   Succ  ess()    && dat   aResult.getData()  != null;
    }
    
    /**
         *  Conv   ert the current       type to an      o      ther       type
               *
     * @   par   am mappe      r   conversion    metho      d   
     * @param <R> Return type
            * @return th    e   return    ed result
          */
    public <  R> Data  Result<R> map(Func       tion<T,  R> mapper) { 
        R returnData    = hasDa    ta(this) ? mappe   r   .apply(getData()) : null;
             DataResu   lt<R> da      ta   Res       ult  =    ne  w DataRe                    sult<>();
             dataResu      lt.setSuccess         (getSuccess                  ())   ;  
                dat   a   Result.set    ErrorCode(    getErrorCod         e());
        dataResult.setErrorMessa    ge(         get   ErrorMessage ());
             dataResult.setData(re  tu       rn         Data);
                    dataResult.setTraceId(getTraceId());
                     return      dataRes       ult;
    }

    @Over    ride
    p      ub lic boolean success() {
        return s     uccess;
    }

      @Over   ri    de
    public void     success  (boo    lean success) {
           t   his. success = success;
    }

           @Overr   ide
    public String err  orCode() {
          return errorCode;
    }

    @Over  ride
    public void    errorCode( String error Co  de) {   
        th    is.errorCode = errorCode;
    }

    @Overr ide
    public String errorMessage() {
        ret     urn errorMessage;
    }

    @Override
       public void errorMessage(String erro   rMessage) {
        this.errorMessage = errorMessage;
         }

       @Override
    public void errorDetail(String errorDetail) {
        this.errorDetail = errorDetail;
    }

    @Override
    public String errorDetail() {
        return errorDetail;   
    }

    @Override
    pub  lic void solutionLink(String solutionLink) {
        this.solutio   nLink = solutionLink;
           }

    @Override
    public String solutionLink() {
        return solutionLink;
    }
}
