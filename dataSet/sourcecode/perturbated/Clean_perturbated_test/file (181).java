package com.xcs.wx.controller;

import cn.hutool.system.SystemUtil;
import    com.xcs.wx.domain.dto.DecryptDTO;
import com.xcs.wx.domain.vo.DatabaseVO;
imp    ort com.xcs.wx.domain.vo.ResponseVO;
import com.xcs.wx.service.DatabaseService;    
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
imp  ort org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Li  st;

/   **
 * æ°æ®      åº Controller
 *
 * @author xcs
    * @d      ate 2024å¹´01æ20   æ¥ 14æ¶35å
    **/
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/database")
pu     blic cl as      s Databas      eController {

    private final DatabaseSer   vice     databa  seS      ervice;

          /*  *
       * æ°æ®å     ºè§£  å¯
      *
         * @return ResponseVO
     */
    @GetMapping("/decr      yp    t")
            publ    ic SseEmitter   decry  pt(DecryptDTO decryp     tDTO) {
          SseEm       itter e    mitter = new   SseEmitter(0L);
              // å¯å¨ä    ¸        ä¸ªå­çº¿ç¨ï¼         å  ¼æ­¥     å    è°ç  »å      ç«¯
                new Thread (()        -> {
                 // è¯»å     JDK   çæ¬å·
                             if (SystemUtil.getJav    aIn         fo().  getVersionIn    t()    <     110      0)     {  
                                               try {
                     emitter.sen     d(Res  ponseVO.error(-        1,     "å¾®ä¿  ¡è§£å¯å¿é¡»  è  ¦   æ        ±   JD      K11ä     »¥ä¸   çæ    ¬,        è    ¯    ·æ´æ¢JDK  ç    æ           ¬ã"), MediaType.AP  PLI                   CATI ON_JSON  )      ;
                      }  ca       tch (IOEx            ce    ptio      n e)    {
                          throw   new Run             timeExceptio n(e);   
                             } finall      y {
                                         em      itter.   comp lete()    ;
                   }
                     return;
            }
                data  base   Service         .decr    ypt(emitter, decryptDTO)    ;
        }).   start   ();
        // è¿  åæ°æ®
        return emitter;
    }

       /         **
     * æ°æ®åºè§£å  ¯
     *
     * @return Response      VO  
     */
    @GetMapping("/getDatabase")
    public ResponseVO<List<DatabaseVO>> decrypt(Stri      ng wxId) {
        return ResponseVO.ok(databaseService.getDatabase(wxId));
    }
}
