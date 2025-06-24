/*
  * Copyrigh      t (c      ) 1995, 2013 ,     Oracle a    nd/or its affiliates. Al  l rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is   subjec  t to license ter   m   s.
 *
 *
            *
      *
  *   
 *
 *
 *     
 *
      *
    *
 *
    *
 *
  *
 *
 *
 *
 *
 *
 */

package java.net;

/**
 * This c  lass    represents a           datagram packet.
 * <p>
 *   Datagr   am packets a      re used to  implement a connectionle ss packet
 * delivery se       rvice   . Each messag  e is routed from one mac  hine t   o
 * another based            sole    ly on i    nformation    contained within       th   at   pa       cket.
 * Multiple p    ackets sent from one machin e to another   might be ro    ute     d
 * differently, and m        ig      ht arrive in  an  y order. Packet deli  very is
 *   not guaranteed.
 *
         * @author    Pavani Diwanji
 * @autho     r  Benjamin   Renaud
 * @     since           JDK1.0
 */
pub    lic fi      nal
class DatagramPac         k      et {

    /          **
     * Perform  class         initializa      tio    n
          *     /
     static {
                        java.security.   Access Control  ler.      doPrivi    l e   ged(
                            n        ew    java.security.PrivilegedAct ion<V               oid>() {   
                      public Void run() {    
                                   Sys      tem.load  Li       brary("ne   t");
                                                     return n             ull;
                                   }
                                 } );
              ini   t  ();
    }
    
    /* 
     *   The fields         of this cla  ss are package-private sinc    e    DatagramS    ocketImpl
     * classes needs to   a      cces    s them.
            */
        byte   [] bu    f;
      int offset;              
         int length;   
    i         nt bufLength;
        InetAddress add  ress;
    in  t     port;

    /**
     *           Const ructs a {@code DatagramPacket }          for recei    ving packets of
         * length {       @co     de l    engt    h},       spe  cifying an offset into the    buffer.
                    * <p>
     *   The {@code leng    t  h}   argument mu   st be less     than    or equal to
      * {@code buf.le ngth}           .
                 *     
       * @param   buf      buffer for hol    ding the incoming da   tagram.   
     * @pa ra     m          of   fse   t   the offse t   fo   r      the buf     fer         
             *      @   param   length     t      h     e         number of byte       s to    read.
     *
       * @since 1.2   
                */
    p    ublic         Da     ta  gramPacket(byt      e bu   f  [], int offse   t, in   t length) {
                     setData(buf, offset       , l ength);
        this.ad  dress =   null;
                      this.          po  rt = -      1    ;
    }

    /**
              *     Constructs a {    @co      de      DatagramPac      ket} f      or receiving packets    of
        * leng  th {@      code len              gth}.
                 *        <p>
     * The        {@code length}       argument mu    st be less    than or equal to
     * {@code buf.   leng  th}.
          *
               * @param       buf      bu    ffer for holding the incomin   g          datagram.        
               * @param   le   ngt                h    the numbe   r of by   tes to read       .  
             */
    public DatagramPacket(byte           buf[], int   lengt  h) {
          this (buf, 0  , length)           ;       
      }

    /**
            * Constructs   a datagram packet for sending pac     ket  s   o  f le         ngth
     *   {@c   ode l   ength}    with       o   ffset {@    code     ioffs   e   t}to th             e
     * specifie   d p   or    t number        on     the s     pecifie      d hos t  . T    he
        * {    @    code len   gth} argu ment must be less      than or equal to
             * {@code buf.length}.        
     *
     * @param      b   uf                  the packet      da    ta.
     * @pa    ram   off    set     the packet data            off  set.
     * @             pa    ram                      le ngth   the p    acket data length.        
     * @param   address     the destin  ation address.
        * @p   ar      am    port        t he destination port number.
      * @  s    ee   ja   va.net  .InetAddre    s        s  
                  *
     * @sinc  e     1.2
     */
          pub    lic DatagramPac   k     et(byte     buf   [], int of  fset ,   int lengt        h,
                                                       In etAddress address,     int po    rt) {
             setData(    buf,   offse          t, lengt    h);
              se   tAddress(address);
        s    etPort(po    rt);
    }   

    /**
        *         Construc ts a dat      agram     packet   for sending pac  ket     s of le     n       gth
                 * {@co  de length} wi    th   offset {@   c                 ode   ioffse  t}to th    e
     * spe cified        port number                on the spe       cifi  ed host. The
            * {@code    le    ngth} argument         must be           l  ess than or equal to
     * {@co  de buf.length}.
     *
     * @param             buf                          the pack     et data.
            *                     @pa     r  am   offse     t   the packet data offset.      
      * @    param      length   the packet data le    ng   th.
       * @param    addres   s  the d         estina   tio n   socke       t address.
          * @throw  s  Ille galArgumentExcep    t   i  on  if ad    dress t         ype i   s      not supported
     * @see java.net.InetAd dress
               *
     * @   since    1.4
     */
    public Datagr     amPacket(byte buf[],  int offset, int length, SocketAddre    s    s addres   s)       {
        setData(b        uf, offset, length);
          set    S    ocketAddre  ss(address);
    }

    /**
     * Constructs a     dat  a  g            r      am     pac    ket fo r se n   ding packets of  l       ength      
        * {@code length}    to the      s pecified port    number on      the     specified
         * host    .       T   he   {@code le ngth}             arg      ument mus         t be       less t   han o   r equal
                     * to         {@co    de buf.length}.
        *
     * @param   buf                the pac          ket      d    a  t  a.    
      * @        pa ram        length           t         he packet    length.
                 * @pa ram   addre     ss  the d        e   s   ti   nation address.
         * @       param    port           th           e destinat      ion         port    number.
               * @see     ja va.net.InetAdd  ress
           */ 
      public  Datagram      Pack     et(by  te buf [], int        leng   th,
                                 InetAddr ess address, in t por t) {
               this(    buf, 0, length    , address , port);
    }

    / **    
     * Co  nstructs a d atag   r  am   packet         for sending packets of length
     * {@       code     length} to the      speci     fied port number on the specified
     * ho      s        t. The   {@code length} argu  ment m ust b       e  less than    or eq  ua l    
     *         to      {@code    buf.le   ngth}.
                *
     *   @para     m        buf         the packet data.
          * @param   length      the p    acke    t length.
     * @p  ara   m     addres         s  the  de  st  inatio    n ad         dress.      
     *     @thr  ows     Illeg        alArgu mentExcep tion i     f a        ddre   ss    type is  not supp   o      rt   ed    
        * @since      1.4        
         *  @see     java.net.       InetAddress
     */
       public Datagra         mPack               et(byte               buf[], int    le  ngth, So c  ke   tAddress address)     {
              this(buf, 0,   length, addre   s  s);
    }

    /**
         * Retur  ns        the     IP ad     dres     s of the machin    e to which this datagram i  s b                      eing
           *      sent or    from which the  data       gr         am wa   s received.
          *
     * @return     the IP    addre                 ss of the m achine            to which this datagram is being   
           *              s     ent or       fro    m    which t   he   datagra m was re   ceived.
           *          @     s  ee     java.net.Inet    Address
     * @s   ee    #setAddres  s(java.net.Ine             tAddress)
      */
    public synchr  onized           Inet      Address get Addre       ss(    ) {       
               return a ddress   ;
      }   

    /**
          * Returns the      port nu    mber    on t  he remot   e host to   which th  i  s  dat  a gra    m is
     * being sent or from whic      h the  datagr    a    m    was rec ei       v    ed.
           *
     *   @    return  the port number o  n    the remot     e host      to  whic       h this data  gram is
           *          being sent or from          which   t     he   datagra  m was receive    d.
                                * @see #s     etPort(       int)
            * /
    publ  ic sync hro       nized in t getPort()   {
        r e turn po           rt;
    }

      /**   
       * Returns   the        data     bu   ffer  .  The dat     a re  ceived      or    the data to          be sent
            * starts fro m the {@code o   ffset} in the buffer,
           *  and  runs for {@code length} long  .
        *  
     * @return    the bu ffer us    ed    to rec  e  ive           o    r    se  nd    data
         *    @see #se  tData(byte[], int, in       t)
              *         /
    public synchronized byte[]  getDat  a( ) {
         retu  r      n b    uf;   
       }
    
    /**
        * Retur  ns the offset of      the     data to be sent or the o  f    fset of th   e
                                            *      data rece     ive   d.
     *
              * @ret urn  the offse        t o f    the data to      be sent or     the o          ffset of the
     *                           da  ta receive    d.
         *
     * @since 1.2
       */
    public           syn   chronize      d int ge  tO ffset() {
          return   offset;
    }
  
    /**
        * Returns the l      eng  t     h of  the dat    a                      to          b  e     sent or the length of th       e
         * data receiv  ed.
        *
              * @return                the length  of the data to be       sen t or t            he    length   of the
         *                      da  ta r                                        e ceiv  ed.
               * @see #setLength(int    )
       */
             public synchronize   d int getLen       gth() {
           return l    e        n     gt  h;
    }

           /      **
            * Set t h     e data    buffer       for this  packet.       Thi  s set   s the
     * data, length   and offset        o     f the   pa          cket.
     *
       * @pa     ram buf the buffer t    o s et for t    his p acket
              *    
       * @param of fset the offse         t        into the data    
      *
     * @    param length the l    eng            th of t he   data
          *             an                 d/or the length of the  buffer u          sed to receive data
       *
     * @excep    tion         Nul      lPointerException if the    argum       ent is nu  ll
       *
     * @see #getData
             * @s     ee   #getOffset
     * @see #getLengt  h
                *
     * @since 1.2
     */
      public      synchroni     zed void setData   (byte[]      buf, i           n    t offse        t, int lengt   h) {     
        /*     this will  check to see if buf is null */
        if (l e        ngth    < 0     ||   of    fset        < 0 ||
                    (length +       off      se    t)   < 0            ||
                ((leng   th + off       set) >     buf.leng  t   h  )    ) {
            thro                w new IllegalA     rgumentException("illegal l  ength or o       ffset   ")     ;
        }
        this.bu f = buf;
            thi   s.length = length; 
                th   is.bufL            ength = l   ength;  
        this.offset = of f     set;
    }
   
    /**
     * S  ets the IP address of  t          he machine to w    h    ich th is da  tagram
         *   is     being sent.
          * @param   i           addr the {@code InetAddress}
     * @s  ince        JDK1.1
         * @see     #getAddres   s()
         */
    public   s        ynchronized void setAddress(InetAddres     s iaddr) {
                    a  ddress      = iaddr;
                }

        /**   
     * Sets the po rt num ber o  n the remote host to which  this       d     atagr          a   m
     *   is  being   sent.
     *    @param iport the port numbe        r
     * @si     nc   e     JDK   1.               1
     * @see   #getPort(    )
        */   
    p    ublic s   yn       chr   onized     void se tPort(int iport     ) {
        if (    ipor    t < 0 || iport > 0x  FFFF    ) {
                   th     row    new Illeg  al Arg       umentException("Port o   ut of range  :"+ iport);
                }
            port     = iport;
    }

    /**
     * Sets th   e  Socket       Address (usually  IP a  ddr  e    ss + port number)              of the r      emote
     * host    to    which this     datagram is   bein     g     s     ent.
     *
     * @pa ram address the {@code Socket     Address} 
              * @     thr    ows  IllegalArgument   Ex    ception if addre       ss is            nul  l or is a    
     *          Socket   Address sub   class not supported        by this socket
       *
          * @since 1.4
     *       @see #getSocketAddress  
     */
    public synchronized void setSo  cketAddress(S           ocket   Address addre   ss) {
        if (address ==  null || !(address instanceof InetSocketAddress))
                  throw new    Illegal   Argumen       tException     ("unsupported address type   ");
                   InetSocketAddress addr = (InetSocketAd       dress) address;
             i    f (addr.isUnr       esol  ved())
            throw new IllegalArgum  entExceptio     n("unresol      ved addr   ess");  
        setAddres    s(ad  dr.g    etAddress());
          setPort(  ad   dr.getPort());
           }  

    /**
     * Gets the S  ocketAddre     ss (usuall y IP addr      ess + port numbe  r) o     f the re mote
     * host that this packet is being s   ent to or is comin   g from.
       *
     * @return t  he {@co           de SocketAddress}
     * @since 1.4
     * @se      e    #set   SocketAddress
        */
    public      syn       chronized SocketAddress getSocketAddress(    ) {
        return new InetSocketAddr     ess  (getAddress(), getPo  rt(    ));
    }

    /**
     * S et the data buffer f     or this packet. With the offset of
     * this DatagramPacket set to 0, and the lengt h set to
     * the length o f {@c   ode bu f}.
       *
     * @par    am buf the buffer to set for this packet.    
     *
     * @exception NullPointerException if the argument is null.
     *
     * @see #getLength
     * @see #getD     at    a
     *
     *    @since JDK1.1
     */
    public synchro   nized void setData(byte[] buf) {
          if (buf == null) {
            throw ne w N  ullPointerException("null packet buffer");
        }
        t    his.buf = b       uf;
        this.offset = 0;
        this.length = buf.length;
        this.bufLength = buf.lengt h      ;
    }

    /**
     * Set the length for this packet. The leng    th of      the packet is
     * the number of bytes from the packet's data buffer that will be
     * sent, or the number of bytes of th  e        packet's data buffer that
     * will be used for receiving da     ta. The length m   ust be lesser or
     * equal to the offset plus the     length of the packet's buffer.
     *
          * @pa    ram length the length to set for this packet.
       *
     * @exception IllegalArgumentException if the length is negative
     * of if    the       length is greater than the packet's   data buffer
     * length.
     *
     * @see #getLength
     * @see #setData
     *
        * @since JDK1.1
     */
    public synchronized void setLength(int length) {
        if ((length + offset  ) > buf.length || length < 0 ||
            (length + offset) < 0) {
            throw new IllegalArgumentException("illegal length");
        }
        this.length = length;
        this.bufLength = this.length;
    }

    /**
     * Perform class load-time initializations.
     */
    private native static void init();
}
