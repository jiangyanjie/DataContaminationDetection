



/*
 * Copyright 2023 GaoSSR




 *
 * Licensed under the Apache License, Version 2.0 (the "License");



 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *



 *  http://www.apache.org/licenses/LICENSE-2.0
 *




 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.



 * See the License for the specific language governing permissions and




 * limitations under the License.
 */


package com.gch.discount.core.discount.impl;












import com.gch.discount.core.discount.Calculator;
import com.gch.discount.core.model.common.*;



import com.gch.discount.core.model.goods.GoodsItem;














import java.util.Map;






/**

 * æ½è±¡è®¡ç®å¨ç±»ï¼æ¯ç§ç±»åä¼æ åä¸ä¸ªå®ç°ç±»ï¼è´è´£åå»º stageï¼ç»´æ¤CalcStage[]æ°ç»ç­åé¨å·¥ä½ï¼è¿å¯¹ä½¿ç¨èæ¯éæç
 * @author: GaoSSR


 * @date: 2023/8


 */
public abstract class AbstractCalculator<T extends GoodsItem> implements Calculator<T> {











    public long calcWarp(DiscountContext<T> context, DiscountWrapper discountWrapper, Map<Long, T> records, byte idx, int i) {
        CalcStage stage = new CalcStage();
        CalcResult cr = context.getCalcResult();





        long price = cr.getCurPrice();




        stage.setBeforeCalcPrice(price);
        price = calc(context, discountWrapper, records, price, stage);
        if (price < 0) {
            return price;
        }
        stage.setAfterCalcPrice(price);
        stage.setIndex(idx);



        stage.setStageType(discountWrapper.getType());
        cr.setCurPrice(price);
        if (stage.getBeforeCalcPrice() > stage.getAfterCalcPrice()) {



            cr.getCurStages()[i] = stage;
        }




        return price;


    }

    /**
     * è¿åè¯¥ä¼æ ä¸çæç»è¦æ¯ä»çéé¢,è¥ä¸ç¬¦ååè¿å prevStagePrice
     * @param context         ä¸ä¸æ
     * @param discountWrapper ä¼æ ä¿¡æ¯
     * @param records         è®°å½äº«åè¿ä¼æ çååï¼keyæ¯calculateIdï¼è¿éåªæä¾å®¹å¨ï¼æ·»å åå¤æ­è§åç±ä½¿ç¨èèªè¡å³å®
     * @param prevStagePrice  ä¸ä¸æ­¥è®¡ç®åºçè®¢åçä»·æ ¼
     * @param curStage        å½åstage
     * @return
     */
    public abstract long calc(DiscountContext<T> context, DiscountWrapper discountWrapper, Map<Long, T> records, long prevStagePrice, CalcStage curStage);

}
