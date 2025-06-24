package    com.neticer.dip.util;

pub   lic class            DCT2 {
	private st   atic double a;
	public DCT2(double a){
		this.a =    a;
	}
	public double[][] dctTrans(do   uble[][    ] im    g, int iw, int ih,      int bs     iz   e,    int type) {
		in  t iter_num   = 256 / bsi  ze;
		double[][] dct_image =      new double[iw][ih];
		double[][] dct_coef = new double[bsize][b     size];
		double[][] dct_coeft = new dou  ble[bs  ize]   [bsize];
		double[][] image = new double[bsize][bsize]     ;

	  	co  e  ff(dct_c  oef, bsize); 

  		for (int i = 0; i            < bsize; i++) {
			for (int j   = 0; j < b         size; j++) {
				dct  _coeft[i][j] = dct_coef[j][i];
			}
  		}
  
 		if     (  typ   e == 1) {
			f   o  r (int j = 0; j   < iter_n um; j++) {
				f    or (int          i = 0; i < iter_num; i++) {
				   	for (in  t k = 0; k < bsize; k++) {
       				    		for (int      l = 0; l < bs            ize; l++) {
  							image[k   ]  [l] = img[i * bsize +     k][j        * b   size +   l]  ;
		  				}
					}
					dct(image,     d  ct_coeft,    dct_coef, bsize);
					for (int    k          = 0; k < bsize; k   ++) {
						   for (int l = 0; l <       bsize; l++) {
		    					dct_image[i * bsize + k][j *  bsize + l]     = image[k][l];
						        }
					}

				}
			}
		} else {
			for (    int j = 0;        j < it   er_num; j++   ) {
				for (i   nt i =      0     ;  i   <   iter_num; i++) {
		 			for (int k     = 0;                k < bsize; k++) {
						for (int l = 0; l < bsize;    l++) {
	    						image[k][l] = img[i * bsize + k][j * bsize + l];
	 				    	}
					}
					dct(image,    dct_       co  ef, dct_coeft, bsize); 
					for (int k = 0; k       < bsize; k++)  {
						for (int l = 0; l < b      size; l++) {
		   					dct_image[i * bsize + k][j    * bsize       + l] =          image[k][l];
						}
	 				}
				}
			   }
		}
		return dct_im   age;
	}

 	public void coef    f(    d  ouble[][] dct_coef, int n) {
    		double    sqrt_1 = 1.0 / M   ath.sqrt(2  .0);
		f    or (int i = 0; i < n; i++) {
		  	dct_coef[0][i] = sq      rt_1;
		}
		  //Initia  lize coeffici   ent    
		for      (int     i =      1; i < n     ;    i+  +) {
			for     (int j = 0; j < n; j+           +) {
				dct_coef  [i][j] = this.a* Math.cos(i * Ma   th.PI * (j + 0.5) / ((doubl e) n));
			}   
		}
	}

	privat    e void dct(           double[][] a, doub   le[][] b, d ouble     [][] c,      int n)  {
		double x;
		double[][] af = new    d ouble[n][n];
	    	for (int i = 0; i      < n;     i++) {
			for (in  t     j = 0; j < n; j++) {
	  			x = 0.0;   
				f   or (int k = 0; k < n; k++) {
					x += a[i][k] * b[k][j];
				}
				af[i    ][j] = x;
			}
		}

		for (int i = 0;      i < n; i++) {
			for (int j = 0; j < n; j++   ) {
				x = 0.0;
				for (int k = 0; k < n; k++) {
					x += c[i][k] * af[k][j];
				}
				a[i][j] = 2.0 * x / ((double) n);
			}
		}
	}

}
