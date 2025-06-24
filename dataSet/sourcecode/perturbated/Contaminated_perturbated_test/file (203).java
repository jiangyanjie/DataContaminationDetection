/*
        * To change this  tem    plate, choose Tool       s | Templates
 * an   d open     the template in the editor.
 */
packag  e edu.ub.prog2.VidalMartinezAlvaro2.model;

import edu.ub.prog2.utils.ImageFile;
import java.io.File;
import java.io.IOException;
import java.io.Serializab  le;
import    java.util.ArrayLi   st;

public cl  ass DadesVisor   implements Seriali  zab   le{
    //AT    RIBUTO     S
         private Bi     bliotecaIma tges     bibli     otecaIm;
     private Ar rayList<A    lbum Imatges> alb         umList; 
    
        //     CONST   RUCTO    R
    public D  adesVisor()   {   
         this.bib  lioteca    Im = new Bibliote         c      a   Imatges();
        thi    s.     al      bumList =      new ArrayList();  
        }
    
          
      
    //METODOS BIBLIOTECA ------------  -----------------    ------  ------------   ------              ------------------------
    public void a      dd   Im     agenB    ibl         io     teca(File f) {
             /   /   Checking if the FILE to create the         i  mage do    es    exists 
           if(f.exist   s()){ //e          xists() ->             Test wh   ether the  file   or direc t    o       r      y     d   eno          ted by this   abstrac  t     pathname exists.
             Im            age im = new I   mage(f);
                                            
              //Checking if the im    age does already      been   entered  b  ef     ore     
                          if(!checkIf     Exist      (im)){         /   /ifNotFound
                    t     his.bibl  iotecaIm.addIma            ge(im);
                        }els           e{/  /ifFoun  d
                                  System.out.p              rintln("DADES_VISOR-> T  h    e image                       that    you want to add DO          ES ALREADY EXI    STS.");
                                 }
                
        }else{ //If FILE doe s    not exis  t   s
                  Sys    tem.out.println  ("DADES   _  VISOR -> The FILE of the image t       o create DOES NOT      EXISTS");
                  }
    }
    
    public St ring mostrarBiblioteca() {
                                String con tenido = this.bibliot     ecaIm.to   S        tring(   )  ;
        if(contenid    o == null){
                                return "         There   ar     e NO IMAG     ES o   n t        he      list."    ;
        }
         else{
            r          eturn cont             enido;
           }
    }   
      
    pub   l   ic void   r       em      ov        eImagenBibl              ioteca(     in    t opt2Delete  )                {
            Im    ageF ile     im2remo        ve = t   h   is  .bibliote     caIm.getAt(opt2Delete);
         this.bibliotecaIm  .removeImage(  i     m2remove);
    }
    
                          public void vie           wImagenBibl  ioteca(in  t     optio  n) throws     IOException,  Exceptio             n {
        this.bibliotec     aIm.ge  t   At(o  p    tion).  show(true);
                   }
     
      

    /*Compru e     ba si ya existe el   fiche   ro (imagen) q    ue se qui  e  re intro  du   cir       en la   biblioteca      */
               pri       va    te         b  oolea   n check    IfExist(   Image im) {
                ArrayL ist    image   List = this.bibliote    caI   m       .  getLis    ta        ();  
        boole    an i   fFound = false;
        
                 for(int i = 0;i <     imageList.s    ize();i++   ){
                        Image im    B  i         b   lio  =  (Imag e)i    mage List.get(i);
                 if(im.full  pa   th.equalsI   gnore  C      ase(imBiblio    .fullpath    )){
                      if          Found = tru    e;
                    break;
              }
           }
                  return ifFound;
       }
             
          
    
    
            
            //METODOS ALBUM ->SUBMENU 1       <-      ---------------------------- --    -------------   ------------   -
         publi    c v     oi  d afegirAlbum(String           newAlbumNa    me)    {
        A  lbumImatges newAlbum = new          AlbumImatges(ne         wA  lbumName);
        this.albumL   ist.add(ne wAlbum);
      }

    publi          c St  ring mostrarAl     bums() {
        S         tring lista  Album ="";
         int count = 1;
                    if(!this.albumList.     isEmpty(  )){
            for (int  i=0;i<this.albumLis   t.size();i++)      {
                             AlbumImatges album = (      AlbumIm     at     ges)this .al    bumList.ge t(i);
                                 /    /al    bum.getLista       ().get(0);
                    listaAlbum = listaAl   bum+   "File: "+cou    nt+" -> NAM    E: "+album.getName()   +"\n      ";
                              count++;
            }
                r  eturn listaAlbu     m;
                }else{
            retur     n "T  he  re are NO    ALBUM       S on   the list.";
        }
    }
 
          publ      ic v oid removeAlbum(int opt1) {
                          this.albumList.remov  e(opt1);
         }
    
    
    //METODO  S    ALBUM ->S UBME NU 2<-   -----------------------------         ------------   ----------------

     pub lic void addImagenAlbum(File f,     int c     hoosenAlbum)   {
          Ima    ge imAlbum = new     Image(f);
        this.albumList.get(choo    senAlbum).addImage(im   Al   bum);
    }

    public String mo  strar      AlbumImagenes(int choosenAlbum) {
        return thi     s.album   List.get(c      hoosenAlbum).toString();      
    }

    pub          lic void rem      ov     eAlb   umImage(int choosenAlb     um, i  nt image2Remove) {
        AlbumImatges albumIm = (AlbumImatges)this.albumList.get(choosenAlbum);
        ImageFile im = albumIm.getAt(image2Remove);
        albu   mIm  .removeImage(im);
    }
    
         public String mostrarDadesModificar(int choosenAlbum) {
           String dadesModi         f      icar ="";
        AlbumImatges album = this.albumList.get(choosenAlbum);
           return dadesModificar = "-> NAME: "+album.getName()+"\n-> POSITION: "+album.getLatitud()        +","+album.getLongitud()+"\n-> IMAGEN_PORTADA: ???????";
    }
    public void modificarDadesAlbu       m(int choosenAlbum, int     image2Modify) {
        
    }
    
    public void viewImagenAlbum(int choosenAlbum, int opt2View) throws IOException, Exception {
        this.albumList.get(choosenAlbum).getAt(opt2View).show(true);
    }
}
