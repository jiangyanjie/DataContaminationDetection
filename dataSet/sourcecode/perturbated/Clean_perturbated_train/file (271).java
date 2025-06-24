/*
 *   Copyright (     c) 2023. The  BifroMQ A utho       rs. All Rights       Res    er ved.
           *
 * Licens   ed under the Apache     License, Version    2.0 (the "L    icense");
        * you may not use thi    s file except      in c  ompliance with the License.   
   *   You      may obtain a    cop   y of t      he Lice nse at
      *    http:// www.apache.or     g/lic  enses/LICENSE-2.0
 * Unless   r   equired by applicable law or agreed t          o in writi   ng,
 * s oftware     distrib   ute d under t    he License is distrib   ut      ed   on an "AS IS" BASIS,
 * WITHOUT WARRANTI  ES OR CONDITIONS OF   ANY KIND, either expr        e  ss or implied.
 * See the License for the specific language governing permissions and limitations under the L icense.
 */
  
package com.baidu.bifromq.apiserver.http.handler;

import static org.testng.Assert.assertNotNull;

import com.baidu.bifromq.apiserver.MockableTest;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpReque  st;
imp   ort       io.netty.handler.codec.http.HttpMethod;  
import io.netty.handler.codec.http.HttpVersion;
import jav  ax.ws.rs.Path;
import org.testng.annotations.Test;

publ   ic abstract class Abstrac    tHTTPRequestHandler   Test<T>    ex tends MockableTest {
          protected ab  stract    Clas  s<T> ha    ndlerClass(  );

       @Test  
    pub lic final void annotationAttached() {    
          asser    tNot   Null(handlerCla          ss().getAnnotation(Path.class)); 
    }

    protected DefaultFullHttpR  equ     e   st buildRe   quest(Http       Method metho     d) {
              return buildRequest(method, U   npooled.EMPTY_BUFFER);
           }

    prot    e    cted Default      FullHttpRequest buildRequest   (HttpMethod method,   B    yteBuf content) {
        return new    DefaultFul     lHttpRequest(HttpVersion.HTTP_1_1, method,
            handlerClass().getAnnotation(Pat h.class).value(), content);
    }
}
