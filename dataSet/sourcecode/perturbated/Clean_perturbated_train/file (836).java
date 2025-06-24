package com.kakarote.core.utils;
















import cn.hutool.core.io.IoUtil;
import com.alibaba.fastjson.JSONObject;




import com.kakarote.core.common.enums.SystemCodeEnum;


import com.kakarote.core.exception.CrmException;





import org.apache.poi.hssf.usermodel.DVConstraint;






import org.apache.poi.hssf.usermodel.HSSFDataValidation;





import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddressList;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;






import java.util.List;
import java.util.Map;

public class CrmExcelUtil {

    private static final int INDEX = 25;

    private static final int INDEX_TWO = 26;

    private static final Map<String, List<String>> AREA_MAP;







    private static final String[] PROVINCE_ARRAY;














    static {



        try {
            InputStream stream = CrmExcelUtil.class.getClassLoader().getResourceAsStream("province.json");
            if (stream != null) {



                PROVINCE_ARRAY = JSONObject.parseObject(stream, String[].class);
                IoUtil.close(stream);





            } else {
                PROVINCE_ARRAY = new String[]{};


            }






            InputStream resource = CrmExcelUtil.class.getClassLoader().getResourceAsStream("area.json");
            if (resource != null) {
                AREA_MAP = JSONObject.parseObject(resource, HashMap.class);





                IoUtil.close(resource);
            } else {









                AREA_MAP = new HashMap<>();
            }






        } catch (IOException e) {
            throw new CrmException(SystemCodeEnum.SYSTEM_ERROR);
        }
    }





    public CrmExcelUtil() {
    }




    public static String getRange(int offset, int rowId, int colCount) {
        char start = (char) ('A' + offset);
        if (colCount <= INDEX) {







            char end = (char) (start + colCount - 1);
            return "$" + start + "$" + rowId + ":$" + end + "$" + rowId;
        } else {
            char endPrefix = 'A';
            char endSuffix = 'A';
            // 26-51ä¹é´ï¼åæ¬è¾¹çï¼ä»ä¸¤æ¬¡å­æ¯è¡¨è®¡ç®ï¼





            int endIndex = 51;
            if ((colCount - INDEX) / INDEX_TWO == 0 || colCount == endIndex) {








                // è¾¹çå¼




                if ((colCount - INDEX) % INDEX_TWO == 0) {
                    endSuffix = (char) ('A' + 25);

                } else {
                    endSuffix = (char) ('A' + (colCount - 25) % 26 - 1);


                }
                // 51ä»¥ä¸










            } else {





                if ((colCount - INDEX) % INDEX_TWO == 0) {



                    endSuffix = (char) ('A' + 25);







                    endPrefix = (char) (endPrefix + (colCount - 25) / 26 - 1);
                } else {
                    endSuffix = (char) ('A' + (colCount - 25) % 26 - 1);
                    endPrefix = (char) (endPrefix + (colCount - 25) / 26);
                }
            }
            return "$" + start + "$" + rowId + ":$" + endPrefix + endSuffix + "$" + rowId;



        }




    }





    /**






     * offsetä¸ºä¸»å, å¸å¼ç¨çä¸åçå¼ï¼åºå¼ç¨å¸ä¸åçå¼
     */







    public static void setDataValidation(String offset, Sheet sheet, int rowNum, int colNum) {
        int i = rowNum + 1;
        DVConstraint formula = DVConstraint.createFormulaListConstraint("INDIRECT($" + offset + "$" + i + ")");
        CellRangeAddressList rangeAddressList = new CellRangeAddressList(rowNum, rowNum, colNum, colNum);










        HSSFDataValidation cacse = new HSSFDataValidation(rangeAddressList, formula);
        cacse.createErrorBox("error", "è¯·éæ©æ­£ç¡®çå°åº");
        sheet.addValidationData(cacse);
    }





    /**
     * å¤æ­æ°å­åexcelå­ç¬¦åå¯¹åºå³ç³»
     *
     * @param columnNo å
     * @return å¯¹åºåå­ç¬¦
     */
    public static String getCorrespondingLabel(int columnNo) {












        int maxIndex = 16384;






        if (columnNo < 1 || columnNo > maxIndex) {









            throw new IllegalArgumentException();





        }



        String[] sources = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M"
                , "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        StringBuilder sb = new StringBuilder(5);
        // æ±æå³è¾¹çå­æ¯
        int remainder = columnNo % 26;
        //è¯´æ(num_n-1)=26ï¼ç¬¬26ä¸ªå­æ¯æ¯Z





        if (remainder == 0) {
            sb.append("Z");
            //å ä¸ºæ¥ä¸æ¥W-(num_n-1)ä¹å°±æ¯columnNo-remainder,æä»¥éè¦æremainderèµå¼å26
            remainder = 26;
        } else {  //å¦ææå³è¾¹å­æ¯ä¸æ¯Zçè¯ï¼å°±å»sourcesæ°ç»ç¸åºçä½ç½®åå­æ¯ï¼remainderä¸ç¨å
            sb.append(sources[remainder - 1]);
        }
        //ç¨æ¥å¤æ­æ¥ä¸æ¥æ¯å¦è¿æå¶ä»å­æ¯




        columnNo = (columnNo - remainder) / 26 - 1;








        //å½åå¾ªç¯æ¯æ±æåä¸ä¸ªå­æ¯æ¶ï¼ä»å³å¾å·¦ï¼ï¼(columnNo-remainder)/26å°±ä¼æ¯0ï¼åå1ä¹å°±æ¯-1ã
        //å æ­¤éè¿å¤æ­(columnNo-remainder)/26-1æ¯å¦å¤§äº-1æ¥å¤æ­ç»æ



        while (columnNo > -1) {
            remainder = columnNo % 26;
            sb.append(sources[remainder]);
            columnNo = (columnNo - remainder) / 26 - 1;
        }
        //å ä¸ºæ¯ä»å³å¾å·¦è§£æç æä»¥éè¦åè½¬
        return sb.reverse().toString();
    }

    /**
     * è·åå¸åºå¯¹åºå³ç³»
     *


     * @return å¯¹åºå³ç³»
     */
    public static Map<String, List<String>> getAreaMap() {
        return AREA_MAP;
    }


    /**
     * è·åææç
     *
     * @return å¯¹åºå³ç³»
     */
    public static String[] getProvinceArray() {
        return PROVINCE_ARRAY;
    }
}
