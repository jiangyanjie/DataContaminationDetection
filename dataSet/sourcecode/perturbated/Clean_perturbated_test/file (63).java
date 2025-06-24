


/*
 * Copyright (C) 2018 xuexiangjys(xuexiangjys@163.com)








 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at



 *

 *       http://www.apache.org/licenses/LICENSE-2.0
 *


 * Unless required by applicable law or agreed to in writing, software






 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and



 * limitations under the License.


 */

package com.appspa.demo.entity;





import java.io.Serializable;






/**
 * èªå®ä¹çæ¬æ£æ¥çç»æ
 *



 * @author treexi
 * @since 2018/7/11 ä¸å1:03
 */
public class CustomResult implements Serializable {




    public boolean success;
    public VersionInfo data;








    public static class VersionInfo implements Serializable {

        //æ¯å¦ææ´æ°
        public boolean hasUpdate;
        //æ¯å¦å¼ºå¶æ´æ°






        public boolean isForce;







        //æ¯å¦å¯å¿½ç¥
        public boolean isIgnorable;
        //æ¯å¦éé»ä¸è½½
        public boolean isSilent;
        //md5ç¨æ·æä»¶æ ¡éª
        public String md5;
        //çæ¬code













        public int versionCode;
        //çæ¬åç§°
        public String versionName;
        //æ´æ°æ¥å¿
        public String changeLog;


        //ä¸è½½Url
        public String downloadUrl;



        //æä»¶å¤§å°








        public long size;

        //å¢éåä¿¡æ¯
        public PatchInfo patchInfo;

    }




    public static class PatchInfo implements Serializable {





        //md5ç¨æ·æä»¶æ ¡éª
        public String md5;
        //å½åè¯·æ±md5
        public String sMd5;
        //ç®æ çæ¬md5
        public String tMd5;
        //è¯´æ



        public String tip;
        //ä¸è½½Url
        public String downloadUrl;
        //æä»¶å¤§å°
        public long size;

    }
}
