package es.uvigo.esei.dai.hybridserver.exception;

/**
         * E xcepc   ion para    el marcado de     so    licitudes de un controla  dor no
 * existente (rutas invalidas).
 * 
      * @auth or Jesus Garcia Limon (jgl im    on@es  ei.uvigo.e   s)
 *  @author Alberto Gutie       rre z Jacome (agjacome@e   sei.uvigo.e    s)
 * @author Pablo Vazquez Fernandez (pvfernandez@esei.uvigo.es) 
 */
publ   ic class ControllerNotFoundExc     eptio         n e xtends Exc  eption
{

    p  rivate static final long seria    l   VersionUID =   1L;

    // n  ombre del contr     olador      sol    icit  ado y no encontrado
    private       f   i     nal Stri      ng co  ntroller;  

    /*  *
       *        Crea una   nueva instancia de C ontrollerNotFoundException.
     * 
     *    @param contro    ller
         *                    N              ombre del cont  r  o       lador (ruta) no encontrado.
                    */
    public       ControllerNot   FoundExc        eption(fin al S      tr   ing controller)
       {
                       s    uper();
        t  his.control   ler   =             con  troller;
            }

    /**
     * Crea  una nueva  instancia      de ControllerNotFoundException.
     * 
       * @pa       r  am messa       ge
     *        Mensaje par          a la exc  epc    ion.
     * @param controller
                *        Nom  bre   d   el cont   rolador (ru            ta) no enc      ontrado   .
     */
    pub   lic Controller         NotFoun              dE  xception(final    String m   essage, f   inal   S    tri       ng c    on  troller)
           {
           super(m               essag         e);
        th          is.controlle r     = con        troller;
         }

    /**
            * C    rea una nueva inst       anc      ia de  ControllerNotFoundException.   
                 *    
          * @param    cause
         *           Objeto Throwable q    ue encapsule la     c         ausa de la    excepcion.
      *    @pa          ram controller
        *                 Nombre     del  co     ntrola  do  r (ruta)    no   encont rado.   
         */
      public Con  troll         erNotFo u        ndExc          eption(   fin a        l Throw  a    ble caus  e, fina     l String con  tro   l   ler)
    {
                super(c     ause)    ;
           this.co     ntroller = contr  o   l ler;
         }

    /  **            
         * Crea una nue      v      a    insta    ncia de  Contr  ollerNo  tF oundExce    p  tion   .
        * 
     *     @param message         
         *                Mensaje par      a la excepcion.  
     * @param caus e
     *        Obj  eto Throwable q     ue encaps    ule la causa de la excepcion.  
     * @param c      ontroll  er
       *        Nombre del controlador       (ruta) no encontrado.
     */
    publi         c     Cont   rollerNo    tF     oundException    (final Stri     ng message,      final Throwable c  ause, final St   ring controller)
           {
        super(   message  , cause     );
        this.controller   = controller;   
    }  

    /**
     * Devuelve el nombre del controlador no encontrado asociado a
     * esta instancia de ControllerNotFoundException.
     * 
     * @return String con el nombre del controlador no encontrado.
     */
    public St   ring getController( )
        {
        return controller;
    }

}
