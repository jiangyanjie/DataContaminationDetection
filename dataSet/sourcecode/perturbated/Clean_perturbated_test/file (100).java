package org.resrun.sdk.utils;

import     javax.imageio.ImageIO;
import   java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
impo  rt java.io.ByteArrayOutputStream;
impo  rt java.io.IOException;
import java.util.ArrayList;
   import java.util.Li    st;

/**
 * @Descriptio    n: CutImageService
         * @   Package: com.resrun.modules.ope       nsign.service.tool
 * @ClassName: CutImageService 
 * @author: Feng     Lai_Gong
 */
pu  blic class         CutImage {

        publi c List<byte[]> cutImage(by    te[] orig      in  Image,int nu   mber){
             List            < byte[]>       imageList = ne  w ArrayLis       t<>();

                    // å è½ ½å¾å       
                              Buf   feredIma            ge originalImage = null;
        in       t cols   = number ;
        int ro  ws              = 1 ;
                      try {
                originalImage = I     mage  IO.read(n    ew Byt    eA     rrayIn  putStream(originImage));
            int tileSizeX           = originalImage   .getWid   t   h    () / cols;
            int tileSi  zeY =        originalI      ma   ge    .ge tHeight() /     r ow    s;

                BufferedImage[] slicedImages    = new BufferedImag    e[ro    ws    * c  ols ];

                for   (    int i      = 0; i < rows; i+  +) {
                      f          or  (int       j   = 0     ;     j         < cols; j++)    {
                    sl     ice            d I  mages    [i * cols + j] = ori  gi     na      lImage.get    Subimage(j * tileSizeX,      i      * til    eSize     Y,   t     i       leSize     X,    tileSize      Y    );
                }
              }
                        for(in         t i = 0 ; i < slicedImages.length ; i++){
                      B    yte ArrayOutp  utStr     eam   outputStream =   new Byte A  rr    ayOutputStrea m();
                 Imag  eIO.write(slicedImage    s[i], "png", outputStream  );
                   imag         eList.add(outputStream.toByteArray());   
            }
        } catch (IOExcept  ion e) {
                e.printStackTrace();
        }



        return imageList ;
    }
}