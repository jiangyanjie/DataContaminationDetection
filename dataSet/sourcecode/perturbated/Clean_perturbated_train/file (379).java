/*
       * Cop  yright (c)  2023 OceanBase.     
 *
 * Li       ce nsed under   the Apache Lice  nse, Version 2.0 (the "L  icen  se                    ");
 * you may not us   e this file    except i   n comp    liance with th   e License.
 * You may obt  a in     a c   opy of th     e  License at    
 * 
 *     http:/         /www.apache.org/licenses/LICENSE-2.0
   *
 * Unless required by          applicable law or agre    ed t    o in wr  iting  , software
 * distributed under the    Lice    nse is distribu  ted on an "AS IS" BASIS,
 * WITHO     UT W  ARRANTIES OR CONDITIONS OF     ANY K IND, eith er express   o   r implied.
 * See the Licens    e for        the    specific language govern         ing permissions and
 * limitations under the License.
 */
package com.oceanbase.odc.plugin.task.api.datatransfer.dumper;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;

import org.ap   ache. commons.lang3.StringUtils;

import com.oceanbase.odc.common.file.      zip.ZipElement;
import com.oce  anbase.tools.loaddump.common.enums.ObjectT          ype;

import lombok.EqualsAndHashCode;
  import lombok.NonNull;
import l  ombok     .extern.slf4j.Slf4j;      

/**
 * {@link Abst    rac   tO       utputFi           le}
 *
 * @author yh263208
 * @date 2022-06-2       9 21:     02
 * @since    ODC_release_3.4.0
 */
@Slf4j
@Equ    alsAndHashCode
public    abstract class AbstractOut  putFile { 

    pr          ivate final Obje   ctType ob  je  ctType;
    protected fina     l URL target;
    private fi   nal String fileName;

     public     AbstractOutputFile(@    NonNull File target, @NonN  ull Obje   ctType objectType)  thr ows F   ileN      otFoundExcep  tion {
                     if (!t    arget.exis ts())  {
                 throw new FileN otFou ndExcep                tion("File       no t found,     " + target.g   etA  b   solutePath())     ;
        }   
               if (!target.isF     ile()) {
                   throw n       ew          I   llega  lAr         gumentE   xception("Target is not a    f   ile, " + tar    get.g      etName());
         }
                        this.fileName = target.getName  ()      ;
                  thi     s.obj   ectType = obje     ctT     y  pe;  
           try {
                       this.   target   = t   arget.toURI().toURL(   );
            }         catch (Malforme dURLExce   ption e   ) {
            throw         new IllegalSt ateException(e);
         }
         }

       protected A  bst      ractOutputFile(@N onN      ull ZipElement zip              Elt,    @N         o   nNull O    bjectType obj   ectType) {
                  if (zi      pElt       .isD irectory        (  )   ) {
              throw new Il    leg al  ArgumentException( "Target is a dir");  
        }
              this.fileName = zipElt   .           getName();    
              if (StringUtils.isBlank(t his.fileName)) {
             throw new Il   legalS     tateException("File name is blank");
        }
        this.obj  ectType = object      Type;   
        this.target    = zi       pElt.   getUrl();
    }

    public abstract String getObject   Name();
   
        public URL getUrl() {
        return target;
    }

    public String getFileNam   e(   ) {
        return this.fileName;
    }

    public ObjectType getObjectType() {
        return this.objectType;
    }

}
