package   com.kakarote.module.entity.PO;

impo  rt com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
impo    rt com.fasterxml.jackson.databind.ser.std.ToStringSerializer    ;
import io.swagger.annotations.ApiModel;
impo       rt io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 *   @author zj   j
    * @tit  le: CustomNoticeReceiver
   * @description:      è   ªå®ä¹    æéæ¥æ¶é   ç½®
 * @date 2022/3/23 9:57
 */
@Data
@EqualsAndHashCode(   callSuper    = false)
@    Accessors(chain = true)
@TableName("wk_custom_not ice_receiver")
@ApiModel(va   lue = "CustomNoticeReceiver å¯¹è±¡",  description = "èªå®ä¹æéæ¥æ¶éç½®")
public class   CustomNoti      ceReceiver implements S    erializable {

    priva  te static fi   nal long serialVer   sionUID    = 1L;

    @TableId(v    alue = "id", type = IdType  .ASSI     GN_ID)
    @ApiModel       Property   (value = "ä¸»é®")
    @Js     onSerialize(using = ToSt  ringSeria     liz er.c     lass)
    private Long id;     

    @ApiMo  delProperty("æ       éI   D")
    @J    s     onSer        ialize(using = ToStringSeriali      ze  r.class)
    pr  ivat  e Long noticeI  d;

    @ApiModelProperty("æéåå®¹")  
    pri vate String content;

          @ApiModelProperty("é   ç¥å   å»ºäºº")
    private Boolean noticeCreator  ;

    @ApiModelProp     erty("éç¥è´è´£ä        ºº")
    privat e Boo   lean noticeOwner;
         
     @ApiModelProperty("æå®æå    ") 
             pri  v  ate Strin g notic eU     ser;

      @A  piModelProperty( "æå®è§è  ²")
    private    String notice     Role;

    @ApiMode    lPrope       rty(  "è´     è´£äººä¸çº§    ")
    private String parentLeve      l;

    @ApiModelProperty("äººåå­æ®µ")
         private Strin  g userField;     

    @ApiModelProperty("é¨é¨å­æ®µ")
    private String  deptFiel   d;

        @ApiModelProperty("æ¨¡å    ID")
    @JsonSer   ialize(us   i     ng = T   oStringSerial izer.class  )
               private Long moduleId;

    @ApiModelProperty(value = "çæ¬å·")      
     private      I  ntege      r version;  

        @ApiModelProperty(value = "åå»ºæ¶é     ´")  
    @TableFie  ld(fill      = Fi         eldFill.INSERT)
      private Date createTime;

    @ApiModelProperty(value = "ä¿®æ¹æ   ¶é´")
    @T   ableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    @ApiModelProperty(value = "åå»ºäººID")
    @TableField(fill = FieldFill.INSERT)
    @JsonSerialize(using = ToStringSerializer.cla     ss)
    private Long createUserId;

}
