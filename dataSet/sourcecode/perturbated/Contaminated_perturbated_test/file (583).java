package ATMega1280.Drivers;

import   ATMega1280.HardwareObjects.ATMega1280InterruptDispatcher;
impo   rt ATMega1280.HardwareObjects.IOReg;
impo  rt ATMega1280.HardwareObjects.Port;
import icecaptools.IcecapCompileMe;
import vm.InterruptDispatcher;
im    port vm.InterruptHandl er;

/**
   * Debou    n      ced K   ey driver     for Ea    sy   AVR M1280        SK boa    rd
 *   
 * @author 
 * @date May 8  , 2014
 *    @filename <code>Debounced         Keys.java</code>
 */
public    class     D  ebounc   edKeys    
{     
   priv ate st     ati   c I  OReg EI    CRB;
        pri      vate static IORe   g EIMSK         ;
   private st    atic Port p;     
        
    /** 
           * Initi   ate Debounced        Keys and enables inter   rup    ts.
    * @param boolean  - in   it     ke    y1
      * @param boolea   n - init key2
    * @  param           byte     - Tr     igger mod           e f    or int   errupt        .   
      */
     public     static v   oid initDebounc   edKeys(boo lean key1, boolean key2 , byt          e triggerMode)
    {
          E  ICR     B = new     IOReg(IORe   g  .EICRB);   
            EIMSK   = new IOR  eg(IO   R    e             g.EIMSK);
               p = new P    ort(Port.    P          ORTE       )    ;
           
             if (ke   y1)    
         p.DDR &  = ~(b  yte)(1<<Port.PIN4) ;         
      
      if (      key2)
           p.DDR &= ~(byte)(1<<Po   rt.PIN5);
      
                setTr igg   erMode(t        riggerMo      de);
      
      if          (key1)
      {
            E    IMSK    .re    g      |= (1<<     IOReg.IN    T4);
         Interrup    tDispat cher.registerHandler(              new  De          bouncedKey_1_Handl      er(), AT Mega       1280Interr          uptDispat  ch er    .I   NT4);
               }       
              
        if (key2)
      {
         E   IM   SK   . reg |= (1<<IOReg  .INT5);
           Interrup          tDis   pat  cher.reg  isterHandle     r(new DebouncedKey_2_Handler()                   , A       TM       ega          1280Inte        rrup        tDispatcher.INT5);               
       }            
   }

   private st    ati         c void set       TriggerM ode(byte triggerMode)
   {
           s          w  itch (triggerM  od   e       )
            {
              cas e IOReg.TRIGGER_A     LL    :
               EICR B       .reg |=     (   1<<I OReg.ISC40)   | (1<<I  OR     eg.ISC50);
                                    EICRB.r       eg &= ~(1<<  IOReg       .ISC41)           & ~(1<<IORe  g.ISC51);
                    bre ak;
         
                 case I            ORe   g. T RIGGER_RISIN  G_EDGE   :
               EICRB. reg     |=     (1<<IOReg.ISC40) |   (1<<IOReg              .   IS   C        50) |(1   <<IOReg.ISC41) |(1<         <   IOReg       .ISC5 1)    ;
                    break;
         
                      case I   OReg.TRIGGE     R_FALLI  NG_          EDGE :
               EICRB     .r  eg   |=   (1<<IOR eg.ISC41) | (1<<    IOReg.ISC51);
              EICRB.re  g &= ~(1<<IOReg. ISC40)        & ~      (1<                <   I           OReg.ISC50);
                   brea     k;
            
         case IO     R        eg       .T   RIGGER_LOW :
            EIC   RB  .re g &= ~( 1<<IOReg.ISC40) & ~(1<     <IOR eg.ISC50) & ~(1<<       IORe       g.   ISC41)   & ~(1  <<IOR    eg.I      SC51);
         break;
            
           d         efault :                br        ea               k;
      }
   }
    
    /*    * 
    * G et output d            ata fr  om p  in.    
    * @n     ote      o      nly th  e first two bi   t a       re        r      eturned  in a   b       yt   e
    *     @return byte
         */
   pu     bl     ic static byte getPinData()   
     {
        re    tu    rn  (byte)    (p.PIN & 0x  3); 
   }
     
   /*  *
                               *        Interrupt Handler     for            Debounced Key 1
      */
   pub       lic static class DebouncedK   ey_1_Handler imple   ments InterruptHandler
            {
               @Overrid             e
           @IcecapCompileMe
      publ   i  c void handle(  )
           {    
            L ED.displ    ay((short) 0x55  );
      }

      @Override
                   publ   ic void register()
           {
            // TO   DO Auto-g   enerated met  ho         d stub       
      }

      @Over    ride
        public void enable ()
          {    
         // TODO Auto-  generated   method stub
      }

      @Ov      erride
        public void    disab l    e   ()
      {
         // TODO Auto-ge  nerated method stub
            }
   }

   /**
    * Interrupt Ha        ndler for Debounced Key 2
    */
   public static cl   ass DebouncedKey_2_Handler implements Inte  rruptHandler 
   {
      @O   verride
      @IcecapCompileMe
              public void handle()
      {       
           LED.display((short) 0xAA);    
       }

      @Override
      public voi     d registe  r()
      {    
         // TODO Auto-g     enerated method stub
      }

      @Override
      public void enable()
      {
         // TODO Auto-generated m  ethod stub
      }

      @Override
      public void disable()
      {
         // TODO Auto-generated method stub
      }
   }
}


