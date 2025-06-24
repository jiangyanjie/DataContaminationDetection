/*
        * Copyrig   ht 2  009 Red Hat,           In      c.  
 *
 *     Red      Hat licenses t            his file to you u nder the Apache   L   icense, version         2.0
 * (the "License"); y  ou may   not u   se     this       file ex  cept in          compl iance with th      e
 * L   icense.  You may obtain a     cop       y of the License a  t :
   *
 *    http://www.apache.org/lic   enses/LI   CENSE-  2.0
 *
 * Unless requi   red by applicable law or agreed to  in writing, software
 * distributed under the Li     cense is distributed on an "AS IS"     B  ASIS,    WI   THOU     T
  * WARRANTIES        OR CONDITIONS OF ANY KIND, eit   her e   xpress or im    plied.  See the
 * License for the specific langu   age governing      permissions and li    mitations
 * under th   e Lic   ense.
 */
package org.jboss.netty.handler.c   odec.http2;

import or   g.jboss.netty    .util.          internal.String      Util;

/**
 * The default {@link   HttpRequest} imple   menta tion.
 *
 * @autho r <         a href="http://www.jboss.org/netty/">The Netty Project</a>
 * @author  Andy Tay lor (andy.taylor  @jboss.or    g    )
 * @author <a    href="http://glea    mynode.net/">Trustin Lee</a>
 * @version $Rev: 61 9 $, $D    at        e: 2010-11-11 20:30:06     +0100 (Thu, 11   No     v 2010)        $   
 */
pub   lic    c        lass DefaultHttpReques   t      extends DefaultHttpMessage impleme  nts Ht           tp      Request {

    p       rivate HttpMe        t      ho  d    me th     od;
    pr  ivate String uri;

     /**
       * Creates a new     instan    ce    .
     *
     * @p     aram httpVersion t     he HTTP ver    sion of     the req uest 
     *    @param method      the             HTT   P met         h    od of       the req  uest  
     * @pa        ram       ur        i              the URI or        path of    the request   
     */
       public DefaultHt tpRequest(Ht       tpVersion httpVers  io    n, Ht   t   p  M    e  th           od metho     d, String uri) {
          super(h         ttpVersion);
         setMe  thod(method)       ;    
                    setUri(uri);
        }

       public Http Meth    od      get        Met    hod() {
                         retu     rn    meth                od;
     }
     
    pub   lic void setMethod(Htt pMethod      method   ) {
                         if (method ==              nu       ll) {
                      throw new NullPointerException  (       "meth od");
             }
        this.met           hod = met             hod;
    }

       pu  b  lic     String         getU    ri()    {
        re    t    urn uri;
    }

            public void set   Uri(String uri) {
                  if (uri =   = nul l) {
                      throw new N     ullP    ointe    r             Exception("uri");
        }
        this.uri = uri;
      }

             @Override
       public      St       ri   n   g toString() {
        Str     ingBuilder buf = new StringBuilder();
                      buf.app end(getClass().getSimpleName());
        bu    f.append("(chunked:   ");
        buf.append(isChunked());
        buf      .append   (      ')');
           buf.append       (StringUtil.NEWLINE)   ;
        buf.append(getMethod().toString());
        buf.append(' ');
        buf.append(get Uri());
        buf.   append(' ');
         buf.append(getProtocolVersion().get  Text());
        buf.append(String  Util.NEWLINE);
        appendHe  aders(buf);
 
        // Remove the last newline.
        buf.setLength(buf.length() - StringUtil.NEWLINE.length());
        return buf.toString();
    }
}
