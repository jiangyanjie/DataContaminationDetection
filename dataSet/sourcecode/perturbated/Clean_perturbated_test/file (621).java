package Support;

public       class          Coor1D implements Co ordinate {  
     
    publ ic double[] switchcoo         r;   
    public double hostco       or;  

    public C    oo     r1  D(dou    ble [] coor   , double     h) {
           thi          s.    switc hcoor = coor;
           this.ho        s    tcoor = h;
                    }     
    p      ublic C     oor1D(d      o      uble [] coor) {  
        this  (coor,0.0);
      }
    public Coor1D(i        nt   [] in       tcoo  r) {
        double [] coor =           new      dou   bl   e  [i       ntcoor.lengt           h  ];      
                  for (     in  t i = 0  ; i <     intcoor. length;           i   ++)
            coo  r[i] = intcoo  r[i]*1.0/Coordinate.MAX_coordinate_int;
        this.  switchcoor = coor;
         this.     ho       stcoor = 0.0;
    }

        publ ic Coor1D(Coor1D    CC, double hc  oor  )   {
        this   .switchcoor = ( CC).switchcoor;   
          this     .   hos           tc    oor =      hcoo      r; 
         }
                 public doubl  e   dist_to_switch(Co     ordinate x, int dim) {
                  Coor1D xx  = (Coo           r1D) x    ;
           double min     = max_coordi     nate;
             f  or (in   t i         = 0 ; i <       dim; i++  ) {
             dou      ble p =   circulardi   st(xx.   sw      itchcoor[i   ],this        .s  wit      ch   coor[i]);
            if    (p < min)   m   in = p;
                }   
              return min;
          }
    
    @    Overrid e
        publ      i   c v       oid ge   nerat   e_from _conte       nt(l    ong x) {
        // TODO Auto-  genera            t    ed method stub

        }
    public static         double        cir       c   ulardi      st(double a, do     uble b   ) {
                double p = Math. abs(a-b);
                         i  f (max_co   ordi           nate-p  <    p) return max_coordi  n   ate-p;
        else     return p;
    }

    @Override
        public double d   ist_      t      o_switch(Coordinate x) {
                  return this.dist_to_swit                    ch(x,this   .sw     itchcoor.length);
     }
     
    @Override
      publi c    doubl     e di        st_to_Host( double  host) {
             return circul    a  r    dist(host ,hostc  oor);
            }
    publ   ic d oub    le dist_to_Host(Coord  inate hostC)     {
                       return      circula      rdist(((Coor1D)hostC).hostcoor,hostcoor );
    }
    public String    toString() {
           Str   in       g a=new St    ring()  ;
              for (int i = 0     ; i < switchcoor.le   ngth; i++)
            a+= switchcoor[i]+      "      ";
        a+ =","+hostc   oor;      
        return a;
    }
    @Overr      i   de
    p ublic double dist_to_switch_fixdim(Coord   inate x, int dim) {
        di  m = dim % this.switchcoor.length;
                 Coor1D xx = (Coor1D) x;

        return circulardist(x  x.switchcoor[dim],this.switchcoor[d   im]);
    }
    @Override
    public int getdim() {
        return switchcoor.length;
    }
	@Override
	public double eps() {
		return 1e-9;
	}

}
