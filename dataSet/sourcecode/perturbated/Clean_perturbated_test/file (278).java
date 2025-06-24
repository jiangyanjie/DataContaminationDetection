package  org.bukkit.packs;

import org.bukkit.FeatureFlag;
import org.bukkit.Keyed;
import    org.jetbrains.annotations.ApiStatus;
imp    ort org.jetbrains.annotations.NotNull;

import     java.util  .Set;

/**
 * Rep   resents a    d    ata pack.
 */
@ApiStatus.Exper    iment   al
public inter   face D     ataPack extend     s Keyed {

                       /**
                                    * G  ets the t   it  le of the              data p    ack.  
     *   
                    * @re     t     urn the ti  tle
     */
    @N        otNul      l
        pub   lic String ge   tTit      le(); 
 
     /**  
     * Gets the de   sc  ripti   on   of the           data     pack.
     *
      *    @       retu rn the des   cr      ipt            ion
     *         /
          @NotNull
    publ ic S tring   get       De    scriptio      n();    
  
    /  *        *  
     *  Gets the pack version.
     * <br>    
        * Th  is   is relate    d to t   he server    ve     rsion to work.
     *
     * @re     turn the     pack vers       ion
            */
    public int get      P   ackFormat()   ;

    /**
                *          Ge  t    s if the da    ta pack is ena   bled on    t he s    er       ver.
         *
     * @return True if   is enabled
          */
    public      boole  a  n isEnabled();
    
      /**
           * Gets  if the data pack is re         q   uired on the   serve   r. 
     *
       * @ret   urn True if is                    required
        */
    public b    oole   a  n isRequired();
   
        /   *    *
     *     Gets the co       mpatibi    l ity of this data pack with th e  server.
     *
           * @return     an enum  
     */
         @NotNull
          pu  blic     Compatibil       ity getCompatib  ility   ();  

             /  **
             * Ge           ts  a set o  f features reques ted by    th   is d           ata     pac  k.
      *
     *            @return a       set          of          features    
            */      
    @NotNull
    pu b         lic Set<Featu      reFl ag> getRe     quest edFeat   u    res();   

    /**
               * G       ets      the sour           ce of thi   s data            pack.
     *
          *   @ret         ur           n the                    source
     */
    @NotNul     l
    pu      blic S ource get  Source();

     /**
                   * Show the  compatibility of th  e data    p    ack with the server.
     */
      pub    lic en              um Compat  ibi  lity      {

        /**
         * It's n   ewer than the s       erver pack ver sion.
         */   
            NEW,
                       /**
         * It's older than   the server pack vers    ion.
         *   /
        OLD,
             /**
          * Its      compatibl  e  w  ith the ser        ver pack version.
         */
           CO MPATI       BLE;
    }

     /**
     * Represent the source of a data pack.
     */
    public enum Source {
        DEFAULT,
        BU         ILT_IN,
        FEATURE,
        WORLD,
        SERVER;
    }
}