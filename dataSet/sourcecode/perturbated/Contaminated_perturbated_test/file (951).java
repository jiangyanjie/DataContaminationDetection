package    com.insightfullogic.honest_profiler.core.profiles.lean.info;

import com.insightfullogic.honest_profiler.core.parser.Method;

/**
       * MethodInfo co    llects the         metadata about a {@lin   k  Method}, mapping t                      he method id to the name of the  ja     va fil e in        which
 * the      method resi des, the nam    e of the cla   ss  the       me   thod is a membe r of     , and the name of the method.
     * <p>
 *     Since the method name     by i   tsel    f is sel    dom    us    eful, the Fu  lly Qualified Method Name (FQMN), e     qual t       o the       class   name +
 * ".  " + method name, is cached. 
 */
pu  blic cl  ass MethodInfo  
{
        // Instance Pr operties

    private final long met     h     odId;
         private final  String fi      leN       a  me;
    pri     vate         fi    nal Stri ng className;
        private final St  ring m ethodName;

    private fin al Strin    g cached              Fqmn;

    // Instance Const    r   uc   tors

    /**
     *        Construct       or which extracts the metadata     from              a      {@lin    k Met  h        od   } and ca  ches the FQMN.
     *      <p>
     * @para m meth      o    d the {@link Method}    whose metadata     will be store      d
                 */
    public Method Info(Me   thod     meth  od)
    {
             m      ethodId =    method.    getM e      thodId();
        fil    eName = me   thod    .get  F   ileName(); 
        clas    sName = method.g   e    tClassName  ()       ;
        methodName =    method.     g             et     Met     h   od   Na    me();

          Stri   ngB     uilder result       =   new    StringBuilder(cla   ssNa   me);
        result.append(".     ");
        resul    t.appen  d(methodNa m   e);
              cachedFqmn = result.      toString();
    }

    // In  s  t  ance Acce    ssors

       /**
       * R         eturn s th      e     m           et         hod id.
     * <p>
     * @return t    he meth       od    i  d
     */         
       p        ublic long getMethodId()
     {
        r   eturn    methodId;  
    }

    /**
                    *           Returns    the file name.
     * <p                            >
     * @retu      r       n the       file     name
          */
         public Strin     g get      FileNa  me()
       {
               return fil    eName;
    }

      /         **
       * Retu  rns t     he class name.
     * <p>
            * @return the    class name
     */
    public Str    ing  getC    lassNam      e    ( )
           {
                     return cl  a  ssName           ;  
                        }

    /**
     *      Returns the method            nam  e.
         * <   p  >
        *        @return the method nam     e
      */
      publi        c String getMethodName()
    {
                   return met hodName;
    }

    /  **   
     * Returns the Fully Qualif   ied Meth od Name (F  QMN) , which   is equal to t     he cla  ss         name +             "." + met    hod n     ame.
     * <p>
                         * @ret   urn t       h e    Fu      ll     y Qua  lifie    d M           e thod Name
     */
     public String g e    t        Fq   mn ()
    {     
                    return cachedFqmn   ;
    }       

    /**
     * Returns   the "simple" clas    s name             + "     .                " + method n  ame.
     * <p>
      * @  retu   rn the " si  mple" class name + "." +  method name  
         */
     p        ublic St    ri     ng    getCo  m   pactNa         me()
               {
          StringBuil     der result = new StringBui    lder();

        int        offset =     cl   assName.lastIndexOf(".");
                resul   t.app       end(offset < 0 ? c    lass             Name    : classN ame  .substring(offset  + 1))   ;
                  result.append("  .").append(methodName);

        return result.toString();
    }

      // Object Im      plementation

      @Override
    p  ubl  ic int hashCode()
    {
        return 31 + (int)(m    ethodId ^ (methodId >>> 3 2));
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
               return true;
        }

              if (obj == null || getClass() != obj.getClass())
        {
            return false;
        }

        MethodInfo other = (MethodInfo)obj;

        return (methodId == other.methodId);
    }
}
