/*
 *    To cha  nge this template,   choos    e To   ols | Templates
 * and open th   e tem   plate in the      editor.
 */
pac   kage imagen;

/**
 *      Clase qu    e permite la correccion del con       tr           aste
 * de una imagen    .
 * 
 * @au   thor        s   ebaxtian
   *    /


public       class Contraste { 
      
       
            // Atr   ibutos         de clase
        p  rivate double contr    as        te;
    privat   e i   nt nive    lMin      ;
    p  ri    vate int nivelMax;
    
      pub     lic Co  ntrast      e() {
                     cont  raste = 0;
                     nivelMin           = 0;
        nivelM  ax = 0;
    }
    
    public do  uble getCo  ntra      st   e(Imagen objImagen) {
           Histogra    m     a his   to      gram a = n  ew Hist    ogr       am  a(  objImagen);
                int[]   n    ivele s = histograma.getHistogramaGris ();
               // niv   elMax y       nivelMi n
        n       ivelMin   =   0;
                nivel     Max = nive les.length          -      1;
                           
        /   / busca el     pr             ime                 r valo          r diferente a cero de    izquierda   a derecha
        while(niv      el    e    s[nive        lMin]  !=   0)          {
             nivelMin+ +;
        }
        
              // busca      el pri           mer valor di        fer     en     te a ce                       ro de der  echa a i    zqui   erd     a
                    while  (         n iv     ele s  [nivelMax] != 0) {
              nivelMax -   -;
        }
                                  
            /    / calc ula    el    contraste
            co     ntraste = (niv   elMa  x    - ni    velM   in     ) / (nivelMax       + nive lMin);
            
        return con        tra    st   e;
     }
    
    
    pu      blic Imagen correccionGam       ma(Imagen objIm  agen, double r) {
         Imagen objSalida =   new Imagen();       
                
             short[   ][] matr      izSal  ida = n  e   w    short[objIma   gen.        ge   tN()][objImagen.get     M()];
                short    [][] ma   t          rizEnt    rada = objImag   e         n.getMatrizGris(  );
                            
           if(r ==    1) {
                    return objImagen;
              }
            
        objSalida   .se    tFormato(       "P2");
          objSa   lida.se  t      N(objIma g    e     n.get   N())  ;
            objSalida.se    t M(objI      magen.ge tM      ());
            ob  j  Salida.setNi  v   e                 lIntensidad(objImagen.getNivelInt     ensid      ad());
          
                  for(int     i    = 0; i < mat  rizEn     trada.lengt       h; i++)               {  
                 for(i nt j = 0 ; j    < matrizEn   trada[0    ].lengt   h; j++)    {
                     matrizSali    da[    i][j]        = (short  )Math           .pow               (matri    zEntrada[i  ][j], r)  ;
                         }
            }  
            
            o bj  Salida.setMatrizGris(m     atrizSal  ida );    
                
        r etu    rn  ob jSalida;
    }
    
    public Ima    gen c         ontr   astStretchin    g  (Imagen objImagen) {
                Ima    gen objSalida = new Im      agen(          );
        
        short[][]     mat        rizSali  da = n   ew short[objImagen  .ge     tN()][ objImage  n.getM()];
           sho        rt[][]   matrizEntrada = objImag    en.getMatrizGris(   );       
        
            con  t  raste = getContr  aste(   objIm        agen    );
        
              objSalida.setFormato("P2");
        objSalida.setN(objImagen.getN());
         objSalida.setM(objImagen.getM());
        objSalida.set   NivelIntensid  ad(objImagen  .getNivelIntensidad());
         
          i        nt n     ivelInt  ensi    dad = objImagen.getNivelIntensidad();
            
        for(int i = 0; i < matrizEntrada.length; i++) {   
            for(int j = 0; j < matrizEntrada[0].length;    j++) {
                matrizSalida[i][j] = (short)((m        atrizEntrada[i][j] - nivelMin) *     nive lIntensidad /         (nivelMax - nivelMin));
            }
        }
        
        objSalida.setMatrizGris(matrizSalida);
        
        return objSalida;
    }
}
