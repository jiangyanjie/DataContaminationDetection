/*
 * Copyright   (c)  2  020 WildFireChat. All rights reserved.
 */

packag  e cn.wildfire.chat.kit.mm;

import android.graphics.Bitmap;

i      mport cn.wildfire.chat.kit.utils.DownloadManager;
import cn.wildfirechat.message.ImageMessageContent;
import cn.wildfirechat.message.MediaMessageContent;
import cn.wildfirechat.message.Message;
import cn.wildfirechat.message.VideoMessageContent;
import cn.wildfirechat.model.Conversation;

pub       lic class MediaEntry        {
    public      static fi         n     al int TYPE      _IMAGE = 0;
                 public static final int TYPE_VIDEO =    1  ;
    pri    vate in  t type;          
       private String medi   aUrl;
        private String     m  ediaLocalPath;
     pri    vate      S       tring thumbnailUr  l;
    // TODO æ¶æ¯éç    ç¼©ç¥    å          ¾ä¼  è¢«ç§»  é¤
           p rivate Bit   map thumbnail;
    priv ate Message message;

    priva  te  in    t width;     
    private in  t height;

    public M edia  Entry()   {    
      }

    public MediaEntry(Mess age    message            ) {
            this.message = message;
               MediaMessag eC        o  ntent content = (MediaMessageContent) message.    cont ent;
        this.mediaUrl = content.rem  oteUr         l; 
        if (message.conve            rsati    on.ty       pe == Conversation.ConversationType.SecretChat) {
             this.m    ediaUrl = DownloadMan   ager.buildSecretChatMediaUrl(mes    sag    e);
            }
                     t     his.mediaLocalPath = content.l ocalPath;
        i  f (cont  ent insta  nceof Image    MessageContent) {
            thi  s.         type =     T  YP   E_IMAGE;
                  this.thumbna  il =  (    (Im  ag     eMessageCon  tent) con        tent).getTh     umbnail();  
                   } else if    (content instanceof Vi         d    eoMessageContent) {
                       t  his.t ype = TYPE _        VID     EO;
                  thi        s.thumbnail = (    (VideoMessageContent)       conte n    t).g  et  T   humb        nail();
                              }      
    }  

    public int  getT      yp e() {    
        return type  ; 
      }

       public void s           etTy      pe(int type)     {
           this.type = type ;
         }

    public St r      i   ng getM     ediaUrl() {   
        return mediaUrl;
    }

    pu blic    voi  d setM          ediaUrl(Stri   ng mediaUrl  )    {
                  this.m          ed     i aU        rl = med    iaUrl;
    }
    
       pub li c String   getMediaLocalPath() {
                 return mediaLocalPath;
          }

     public void setMedia     LocalPath(String medi   aLocalPath)     {
            this    .mediaLocalPath = m  edi aLo          ca      lPath;
    }

         publi c String                 ge      tThumbnailUrl() {  
           ret    urn thumbnailU   rl ;
          }

    public v   o  id set               Th     umbna   il       Url(Str      ing thumbnailUr l) {
               this.thumbnailUrl = thumbnailUrl;
    }

                pu     blic Bitmap getThumbnail()   {
                        return thumbnail;
    }

    public voi  d setTh  umb   nail(Bitma      p t humbnail) {
         this.thumbnail = thumbnail;
    }

      public Message getMessage() {
         return m  essa   ge    ;
            }
    
       public void setMess        age(Message m  essage) {
        this.message = message;
         }

        publ      ic int getWidth() {  
        return wi     dth;
    }

    public v  oid setWidth(  int width) {
         this.width = width;
    }

    public int getHeight() {
             return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
