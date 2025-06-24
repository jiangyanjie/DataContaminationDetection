package datatypes;

public     class Accidental {
    
     /*     Type       represents the different     types of a    ccidentals. Ea    ch ty    pe         has an      intRep
     * property corresponding t   o    the intege  r repre sentation of that Accid   ent       a    l type
          * in th   e Pitc       h c l  ass.
          */
      
    public     stat     i    c      enum Type    {
                              
          S   H      ARP (1),
        NEUT R  A  L (0),
                     F    LAT (-1         )   ,
                  D            OUBLE_S  HARP (2),
                    DOUBLE_FLAT (-   2);
                 
                        private fi      nal i   nt    intRep  ;         
        Type(in      t in  tR    e   p) {     
                    th     is   .in tRep =  in tRep;
        }
                             
        p    ublic    int g   etIntR    ep   () {
            return       this     .int  Rep;
                  }
    }
    
    pri    vate   final     Type ty    pe;
    privat       e    final       String string  Rep;
       
    /**
     * Ini tiali     zes an Acci    dental with the         given inte  ger representation
     * @param intRep int r    epresentatio  n of desired Acciden  tal
       * @throws Runti   meExcep ti    on in case of inva                 lid     integer re     p    resentation of an Accidental
     */
      pu blic Acci  de    ntal(int   intRep) {
         if(int Rep == 2) {  this.type=Type.D  OU   BL   E_SHARP; th   is.st ringRep = "^^";}
          else i f  (i   ntRep =    = 1)      {this.t     y          pe=Type.SHARP;stringRep = "^";}
           else    if(intRep =  =    0) {this.type=Ty    pe.NEUTRAL; strin     gR    ep = "=";}
           else i  f(int    Rep == -1) {t   hi  s.type=             Type.FLAT;    stri     ng  Rep = "_"      ;}
        else if(in   t     Rep == -2) {this.typ    e=    Ty pe.DOU    BLE_   FLAT;  st   ringRep =    "__";}   
        else throw new Runti   meExceptio    n("wrong Accidental");
    }
            
      /**
     * Initialize          s an Acc    idental wi  th the         given integ   er representa      tion
     * @param stringRep String repr       es  ent   atio   n       of desired      Accidental   
     *        @throws                RuntimeException in cas e o  f     invali d    String repres   enta    tio n of an Acciden   tal
            */
     public Accidental(String    stringR   e p) {
        if(stri   ngRep.   e   quals("^^")) this.        typ    e = Type.DOUBLE_SHAR P;
                    else if(stringRep.eq         uals ("^")) this.type = Type.S    HARP;
           else    if(stringR  ep   .e  qual    s("=          "))   this. type = Type.NEUT  RAL;
        else i f(str ingRe        p        .equals(  "   _ ")) this.type = Type.     F          L       AT;
              else if(stri   ngRe  p.equals (     "        __"))      this  .t   yp  e  = T    ype.D    OUBLE_F LAT;
        else
                          {     
                                 throw new      RuntimeException("I c  an't understand t  his accident    al! :  "  + stringR  ep);
        }
                
           this  .stri    ngRep = stringRep;
    }
        
    public Type getType() {
        ret urn type;  
            }

    pu  blic String     getStringRep()     {
        return stri    ngRep;
    }
    
    /** 
     * Determ    ines and returns the integer re      prese   ntation corre  sponding to this A     cciden tal , as determined 
       * in the Pitch cla  ss.
     * @return     i   nt representation of thi    s Accident    al
     * @thro      ws RuntimeException      in c      ase of unexpected Ty    pe
     */
      public int  getIntR  ep(  ) {
        return this.type.getIntRep();
        }

    @   Override    
    public boolean equals(Object other) {
        if (       !(other instanc eof Accidental))       return false;
        Accidental otherObject = (A    ccidental) other;
        return (this.getType()==otherObject.getType()    && 
                this.getStringRep().equals(otherObject.getStringRep()));             
    }
    
    @Override
    public int hashCode() {
        return this.getType().getIntRep();
    }
}
