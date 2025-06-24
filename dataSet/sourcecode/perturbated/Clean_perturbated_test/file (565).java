package     webstore.bb;

import java.io.Serializable;
import javax.annotation.PreDestroy;
import javax.enterprise.context.Conversation;
im    port javax.inject.Inject;
im     port webstore.core.IProductCata  logue;
import webstore.core.IReservationRegistry;
import webstore.core.JPAStore;
import webstore.core.Product;
import webstore.core.Reser   vation;        

/**
 * Base class for  EditProduct, DelProduct   , AddReservation.
                *
 * @author hajon rewritten by Jona  s Ha
  */
public abstrac     t class ConversationalBase  imp   lements Serializ  a   ble {   

     private   P  roduc      t product;
    pr  ivate Long id;
     privat    e String name;
    priv   ate int quantity;
         private S   t   ring pr     ice; 
    @I   nject
      private   JP ASto     re jp     a;   
         @Inje       ct
            p  rivate Co       nversatio    n   conversation;
         
      /  **
     *     Se tter met       hod for   the for the e   d it and  delete prod   uct
     *                 
     * @p   a     ram id for th e produc  t
           */
       pu      blic v   oid    setSel  e   cted(Stri     ng   i d) {
                          if (conversation.i  sTrans       ient(   )) {
               conversation.beg   in();
               }
        Product p   = jpa.   getProductCat alog  ue().find(Long.v       alueOf(id));
              this. p        rodu ct = p    ;
            this.id = p.getId     ();
                  this.name = p.getN ame();
           this       .quanti     ty =    p.getQuantity()     ;
                this     .pri     c          e = String.valueOf(p.getPrice())        ;
     }

       /**
         * Set te  r           metho  d f   or the reservatio        n
     *
     * @param id  1, id         2 for the r        eserva   t  io  n and pr oduct
     */
    p       ublic void   set   SelectedR   (St     ring i   d1, String id2) {
        if (co   n    versation.isT    ransient   ())   {
             conv      ersat ion.begi            n     ();     
            }
        Reservation r   = j pa.getReservation     Re    gistry().f    ind(Long.va    lu  eOf(id1));       
                   Prod      uct           p    = jpa      .getProductCatalogue().  f i   nd(      Lo     ng.valueOf(id2   ));

              this.id =           r.g    etI   d();
        th        is.product =     p;

      }

    @Pr    eDe     str  oy   // Must    hav       e for bac       k button etc.
                    p   ublic void destroy    () {
           if (!conve    rsation.isTransient()) {
            conversati    on.end(        );       
        }
         }

         /*       *
                  * Metho  d to     be        execu   ted when doing an    ac    tion in edit, d   e  l    or add
                     * r eserva    tion
          *   
     * @return S tring o   f    the coutcome which redire c     t yo   u to a specif          ik jsf pa     ge
          */    
    pu   bl    ic String actOnSelected() {
        if (!conver     sat  i   on.isTransient  () )       {    
                            c   onvers   ati         on.en               d()     ;      
         }
        return     execute();
    }   

          // Imp  lemented by     subcla     ss es
     protected abstract String execute();
        
    /**
     * Getter for the  productCatalogue
                  *
            * @return IPro   ductC   at      alog        ue
       */  
    p rotected IProductCatalogue ge    tProductC   a   talogue(    )    {
        r      etur       n jpa.getPro   d  uctCata log     ue    (         );
    }

    /* *
     * Getter for  th           e re     servationRegist    ry         
     *
       * @return IRes    ervationRegist  ry  
     */
    prot                       ected    IReservat             i   on        Regi       stry      getR e         s       erva                 tionRegistry()  {
          return j    pa.ge          tRe      se        rvat    i onReg  istry();
    }

        /**
            * Return     s the   id
         *
       * @ ret      urn id
       *     /
      publi  c L     ong   g   et   Id() {
        r   eturn    i  d;
    }

      /**
            * Sets   th   e i       d
         *
       *  @  param i              d      
         */   
    pub  lic void    s etI     d(L   ong id   ) {
            this.id =     id;
    }  

    /**
             * Returns t he      name
     *
     *  @retur  n     name
     *         /
    public St  ring getName() {
        return name;
                    }

    /**
                   * Sets        the      name
     *
          * @param name
           */
    p    ublic void setName(Str          in  g name) {
        this.name = name;
    }

    /**
     * Retu  rns th    e quantity
     *
      * @return   q   uan  tity
     */
    p   ub  lic int getQuantity() {
        return qu   antity;
    }

        /**
     * S        ets t    he quantity
     *
     * @param quantity
     */
    public void    setQuantity(in  t quantity) {
            this. quantity = quantity;
    }

    /     **
     *   Re   turns the p  ri    ce
      *
     * @return price
        */
    public Strin  g getPrice() {
        return price   ;     
    }

    /**
     * Se   ts the price
     *
     *   @param p    rice
     */
    public void setPrice(String price) {
        this.price = price;
    }
   
    /**
     * Return        s the product
     *
     * @return product
      */
    publ  ic Product getProduct() {
        return product;
    }
}
