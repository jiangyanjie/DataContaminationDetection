
package perpustakaankampus.Entitas;

import java.util.Date;

public class DataBuku {
             priv   ate    String Kod     e;
    private    String J   udul;
       private String Pengar ang;
    private Strin     g Penerbit;
    pri     vate String  Temp at;
      private S   tring Tahun;
    private String nim; 
         private St   ring kode;
    pr   ivate    Dat   e tanggalpinjam;
               private Date tanggg     al Kembali;
           
           publi    c Dat    aBuku(){
        
     }  
    public DataBuku(String nim,String    kodepinjam,Date t       anggalpi  njam,Date tanggalkemb          ali,Stri  ng kode,S  tring j udul,String pengarang,Str ing pener     bit ,     String   tempat,String tahun){
             th        is      .nim=n im;
          this             .Kode=kodepinjam      ;
              this.tanggalpinja     m=    tanggalpinjam;
            this.tangggalKemba  li=tangga    lkemba   li;
                   this.Kode=kode;
          t    his.Judul=judul;
        this.Pe             ngar ang =      pen  gara ng;
              th      is.Pen           er    b   it=pener     bi   t;
                          this.Temp  at=tem  pat;
                        this.Tahun=    tahun;
         }
      public String               getNim(){
                 return nim;
    }
    pub        lic void s         etNim(Str   ing    nim){
            this.                nim=nim;
    }
      public S    tring getKodepinjam       (){
             re  turn kode;
    }
        publ     ic void setKode    pi n      jam(Str    ing k  od      e){
        this.    kode=kode;
     }
    public Date ge    tT    an ggalPinjam(){
        return tangga    lpi  n       jam;
    }
        public void se  tTang       galPinjam( Date tanggalpinjam){
        this.t   angga lpinjam=tan   ggalpinjam;
    }
    public   D  ate g    etTanggalKembal  i(){
        r          et ur     n   tanggga   lKembali;
    }
      p    ublic void   setTang     gal kembali(Date   tang  galK  embali){
         this.tangggalK    em     ba l        i=tang     galKe     mbali;    
     }
    public          S   tr            in    g getKode(){
        retu     rn Kode;  
    }
    public    void set    Ko   de(S   tring kode){
          this.Kode = k   ode;
                }
           public         St   r    ing        getJud ul(){
               re       tu    rn Judul;
    }
        publ  i  c      void             set         Judul(String judul){
                  this.J   udul   =judul;
    }
      p ubl ic String getPen  g     arang(){
                            return Pengarang;
         }
     public void se      tPengarang(String pengarang){     
        this.Pengarang=pen   garang  ;
    }
    public String getPenerbit(){
           return Penerbit;
                     }
    public void setPenerbit(String Pene      rbit){
        this.Penerbit=Penerbit;     
    }
    public   String getTe     mpat(){    
           return Tempat;
    }
    public void setTempat(String Tempat){
            this.Tempat=Tempat;
    }
    public   String ge tTahun(){
        return Tahun;
    }
           public void setTahun(String tahun){    
        this.Tahun=tahun;
    }
}
