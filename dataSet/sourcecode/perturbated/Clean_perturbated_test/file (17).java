package com.lacus.utils.poi;

import     cn.hutool.poi.excel.ExcelReader;
im     port cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.lacus.common.annotation.ExcelColum     n;
import com.lacus.common.annotation.ExcelSheet;
import com.lacus.common.exception.ApiExce   ption;

import java.io.IOException;
import  java.io.InputStream;
import java.io.OutputStream;
impor    t java.lang.reflect.Field;
impor   t java.util.List  ;
import javax.servlet.http.Http      ServletResponse;

import com.lacus.common.exception.error.ErrorCode;
import org.springframework.web.multipart.Multipar   tFile;

/**
 * èªå®ä¹Excel å¯¼å¥å¯¼åºå·¥å·
   */
public class CustomExcelUtil      {


    public static     void writeToResponse(List<?> list, Clas  s<?>              cla zz, H    ttpServletRe     sponse     resp    o  nse) {
            tr        y {
              wr   iteT            oOutputStream(list ,   claz   z, response.get  OutputStream());
            } catch    (  IOExc  eption e) {
                   e .pr   int    Stack  Trac     e();
                throw new ApiExcepti  o    n(Erro      r  Code.Intern   al.UNKNOWN_ERR  OR);
          }
     }  

    p  ublic    static List<?> r eadFrom     Request(    Cl  ass<?     >     cl   azz,  M   ultipar  tF  ile      file) {
        try {
             retu      rn readFromInputStream(cla    zz, file.  ge   tInpu    tStream());
                } ca       tch (IOException e) {
               e.printStackTrace();
                   throw    n  ew ApiException(Err   orCo       de.Inter nal.UNKNOWN _ER    ROR);
           }
    }

    p       ublic static voi d wr   iteToOutputStream(Lis    t<   ?> list, Cl    ass<?> clazz, OutputStream outputStream     )     {    

        // é   è¿å·¥å·ç±»åå»º     wr             iter
            Excel  W    riter writ             e   r = Exce        lUt   il.getWriter();

            Exc      el       Sheet  she etAnno =   cla      zz  .g   etAnnota     tion(ExcelSheet.class);

            i  f  (   sheetAnno !=     null) {
             // é»   è®¤çsh        e     etNameæ           ¯ sheet1
                          writer.re         nameSheet(sheetAnno.name() );
        }    

        Fi      eld[] field     s = clazz.getDeclaredFiel            ds()     ;

             //èªå®ä¹æ é¢å«å
          fo  r (    Field field         : fi elds) {
               Excel    Column  a     nnot               atio  n = fi    eld.getAnnotation(ExcelCol   umn.class);
                          if (annotation != nu   ll) {
                    writer.addH       eaderAlia  s(      fie   ld.getName(), annot  ation.name());
            }
               }

        // é»è®¤çï¼æªæ·»å aliasç    å±æ§ä¹ä¼ååºï¼å¦ææ       ³åªååº  å äºå«åç     å­æ®µ    ï¼å¯ä»¥è°ç¨æ­¤æ¹æ³æé¤ä¹
           wri   ter.setOnlyAlias(tru    e);
 
        // åå¹¶ååæ ¼åç        æ é¢   è  ¡ï¼ä½¿ç¨é»    è®¤æ é¢æ ·å¼
                    /  / writer.merge(4, "ä¸ç­æç»©å"); ä¸æ¬¡æ§ååºåå®¹ï¼    ä½¿ç¨é»è®¤  æ ·          å¼                 ï¼   å¼  ºå¶è¾  åº æ          é¢
        wri          ter  .wr   it e        (list, tru  e);
            writer          .fl  ush(outputStream, true);
      }



    public static List<?> readFr o           mIn   p   utSt  ream(Class<?> clazz ,  InputStream i        nputStr   eam) {
         ExcelReader reader = Exce  lUtil.getR      eader(inpu     tSt r    eam)   ;
                   // å»é¤æexce        lä¸­çhtmlæ ç­¾è¯­è¨  é¿åxssæ»å»
        reader.setCel  lEditor(new Tri mXssEditor() );

            Field[] fields = cl  azz.getDeclaredFie         lds();

          //èªå®ä¹     æ é¢å«å
               for (Field field : fields       ) {       
              Excel    Column a     nnotation = field.getAnnotation(ExcelColumn.class);
            if (annotation != null) {
                re ader.addHeaderAlias(annotation.name(), field.getName  ());
            }
        }

        return reader.read(0, 1, clazz);
    }



}
