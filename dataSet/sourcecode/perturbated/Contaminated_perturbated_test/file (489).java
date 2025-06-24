/*
           *     To change      this template,  choos     e     To   ols |     Templates
 * and open the tem    plate     in the editor.
 */

package org.bandarra   .dbf.file   ;

/**
      *  
 *    @author andre.bandarra
     */
pub         lic cla   ss     DBFFi  eld    {
      private String name;
    private DBFFieldType    type;
    pr    ivate   b    yt   e length;
             private byte d  eci malCou  n    t;
    private int of          fsetInRecord;
    
    DBFF     ield(        String name, char t   ype, byte lengt     h,     b   yte decimalCo      unt){
        setName  (   name)     ;
        setType(        ty      pe     );   
            setLength(lengt    h);       
                  s     etDecimal    count(    d    ecimal C   ount);        
           }
       
      DBFField(S   tr  ing  name,            DBFFieldT    ype ty  pe, b   yt    e l  ength,     byt    e deci m  alCoun   t)    {
                          setName(n          ame );
                          se             tType (ty pe);
            setLength(length);   
              setDecimalcount(decimalCount);
           }
           
         v   oid                    setName(Str       ing      n    am      e){
               t   his.name = n   a me        ;
     }
          
       void setTyp      e(ch ar t  ype){
                      switch ( typ         e){   
                        case 'C':     this.type          = D    BF  F           ieldType.CH     ARACTER;             brea k;
                 case 'D' :         th is.t  yp e = DBFFi     el     dType.     DATE; break;                 
                     cas  e    'N':        thi  s.ty pe =       DBFFieldType.NU    ME     R IC; b    reak;                                          
                               cas   e  'L': this.t        ype = DBFFi     e   ldType.LOGICAL       ; break;
            c               a     s      e 'M     ': this.typ   e = DBFFieldType.   MEMO; break  ;                           
         }
             
         }
    
    vo    id s       e    tType(DBFF   ieldType type){
            thi  s. type = typ    e;             
       }
    
      void s    e     t Length(byte length ){
                      this.length    =            lengt    h;
           }
    
                    void setDeci               ma   lcount(byt  e decimalCou    nt)            {
                     this.decimalCount = decimalCo          unt    ;
       }
  
    publi            c S                   t      ring getName  (){    
              retu         rn th          is.n      ame;
          }
     
    public DBFF   ieldType getType (  ){
              return this.type;
    }   
    
         public byte      getLength()      {
                 return    th        is.length;
     }
    
    p    ublic byt e  get     Decim   alCount(  ){
        ret urn    this.decimalCoun  t;
    }
    
    @O verride
    public St   r     ing   toString(){
           retur n nam    e  .t oString();
        }
  
    @O ver   ride   
                  public int      hashCode()    {
        return    name.   hashCod    e()    ;
     }

    @Override
     p        ublic boolean equals(Object obj) {
             if (obj == null) {
                  return fals   e;
        }
        if (getClass() != obj.getClass())       {
                 return     false;
        }
        final DBFField other = (DBFField) obj    ;
           if (this.name != other.name && 
                (this.name == null || !th  is.  name.equals(other.name))) {
            return false;
        }
        return true;
    }

       public int getOffsetInRecord() {
        return offsetInRecord;
    }

    void setOffsetInRecord(int offsetInRecord) {
        this.offsetInRecord = offsetInRecord;
    }
    
    

}
