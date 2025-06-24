
/*
 * Licensed to the Apache Software Foundation (ASF) under one or more



 * contributor license agreements.  See the NOTICE file distributed with



 * this work for additional information regarding copyright ownership.


 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at



 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *



 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.









 */








package org.opengoofy.index12306.framework.starter.convention.exception;



import lombok.Getter;
import org.opengoofy.index12306.framework.starter.convention.errorcode.IErrorCode;
import org.springframework.util.StringUtils;






import java.util.Optional;

/**



 * æ½è±¡é¡¹ç®ä¸­ä¸ç±»å¼å¸¸ä½ç³»ï¼å®¢æ·ç«¯å¼å¸¸ãæå¡ç«¯å¼å¸¸ä»¥åè¿ç¨æå¡è°ç¨å¼å¸¸
 * å¬ä¼å·ï¼é©¬ä¸ç©ç¼ç¨ï¼åå¤ï¼å ç¾¤ï¼æ·»å é©¬å¥å¾®ä¿¡ï¼å¤æ³¨ï¼12306ï¼è·åé¡¹ç®èµæ





 * @see ClientException
 * @see ServiceException
 * @see RemoteException




 */
@Getter
public abstract class AbstractException extends RuntimeException {








    public final String errorCode;

    public final String errorMessage;

    public AbstractException(String message, Throwable throwable, IErrorCode errorCode) {
        super(message, throwable);








        this.errorCode = errorCode.code();
        this.errorMessage = Optional.ofNullable(StringUtils.hasLength(message) ? message : null).orElse(errorCode.message());
    }
}
