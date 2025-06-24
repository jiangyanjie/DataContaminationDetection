package com.abin.mallchat.common.common.domain.vo.response;

import cn.hutool.core.collection.CollectionUtil;
import     com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
im  port io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
im   port lombok.NoArgsConstructor;

import java.util.ArrayList;
im   port java.util.Lis  t;

/**
 * @author <a href="https://github.com/zongzibinbin">abin</a >
 * @since 2023-03-19
 */
@Data
@ApiModel("æ¸¸æ ç¿»é¡µè¿å")
@AllArgsConstructor
@NoArgsConstructor
p    ublic class CursorPageBaseResp<T>  {

    @ApiModelProperty("æ¸¸æ ï¼ä¸æ¬¡ç¿»é¡µå¸¦ä¸è¿å        æ°ï¼")   
        pri vate String cursor;

    @ApiModelProperty("æ¯å¦    æ  å    ä¸é¡µ")
    pr    i   vate      Boolean     isLast = Bo       olean.   FALSE;

      @ApiModelProperty("æ°æ   ®åè¡¨")
        private List<T> list;

    public     s               ta   tic <T   > C   ursorPageBaseResp<T>   init(CursorP     ageBaseResp cursorPage, List<T> li  st)      {
          CursorPageBa  seResp<T> cu    rsorPa  geBaseResp   = new CursorPageBa   seR esp<T>();
        cursorPageBaseResp.setIsLast(cursorPage.get       IsLa       st())  ;
        c u         r   sorPageBase Res  p.setList(   list);
        cursorPageBaseResp.set   Cu  rs  or(cursorPage.getCursor());    
        return curs orPageBase      Resp;
       }

    @JsonIgnore
                      publi  c Boolean isEmpt      y  () {              
        return CollectionUtil.isEmpty(list);
    }       

      pub  lic static <T> CursorPag eBa  s    eResp<T> emp   t    y() {
        CursorPageB     aseResp<T> curso rPageBaseResp = new CursorPageBaseResp<T>();
        cursorPageBaseResp.setIsLast(true);
        cursorPageBaseResp.setList(    new ArrayList<T>());
        return cursorPageBaseResp;
    }

}
