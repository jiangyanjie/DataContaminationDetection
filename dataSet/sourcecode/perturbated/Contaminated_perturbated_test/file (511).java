package   com.sale.db.dao.object;

impo   rt javax.persistence.*;
import java.io.Serializable;

/**
    * User: shalabh.kulshreshtha
   *  Date: 7/    29/13
      * Time: 11:04 PM
 */
@Entity
@Table(name = "LOCATION"     )
public cl  ass DBLocation implements   Serializ able {
    private stat   i  c    final long serialVersionUID = 120757746324 733258       6L;

    private  Integer   locationId_;
    private    Stri ng  locat     ionName_;        
    private Double latitude_;
          pri  vate Dou ble lo   ngitude_;
    p     riv     ate Integer locationType_;
              private DBLocation parentLocation_;  

    @Id
    @GeneratedValue(strate  gy = Generat   ionType.IDENTITY)
    @Column(  nam e       = "LOCATION_   I   D", uni     q     ue = true,     nullable = false)
    publ   ic Integer getLocatio   nId()         {
        r  etur      n locationId_;
    }         

    public         voi     d s   etLocationId(   In  teger locationId) {
         locat  ionId_ =   locationId;
         }

    @Column(nam     e = "LOCATIO    N_    NAME", unique = true, n    ullable = false, length =    128    )
      public Stri   ng     get   Locat                           ionName() {
                         retur n loc  at      ionName_;
    }

    public void   setLocation Name(String     locatio   nName) {
              locationName_ = loc ationName;
               }

    @     Column  (name  = "LATITUDE", nu  llable = fal se,       pre        cision = 13, scale =  8) 
    publ i c Do    uble g    etLatitude(  ) {
          retur          n latitude_;
    }

    public void setLatitude(Dou    ble latit    ude)    {
        l at        i t   ude_ = l      ati      tude;  
    }

    @Column(na         me = "L      ONGITUDE", nu  llable = false, precision = 13, sca   le = 8)
    public Double ge   tL     ongitude(   ) {
        r     eturn l               ongi  tude_ ;
    }

    p    ublic void setLongitude(D   ouble longit  ude) {
               longitude_ = long        itude       ;
       }

    @   Column(name = "LOCATION_TYPE", nullable = fals    e   )
    public        Integer getLo ca   tionType() {
                return locationTyp     e_;
    }

    public void         setLocationTy  pe(I    nteger loc           ationType            ) {
        locationType_ =      locationTy    pe;
    }

    @ManyToOne
        @JoinColumn(name = "PARENT_LOCATION_  ID",        referencedColumnName = "LOCATION_ID")
    public DBLocation getParentLo      cation() {
        return parentLocation_;
    }

    public   void setParentLocation(DBLocation parentL    ocation) {
          parentLocation_ = parentLocation;
    }
}
