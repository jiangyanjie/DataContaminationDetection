


package yy.excelutil.common;

import java.util.HashMap;







import java.util.Map;
import java.util.regex.Matcher;

import java.util.regex.Pattern;








public class ConstantsMap {










    private static Map<String, String> BOX_DESC;

    private static String CTNS_PLTS = "CTNS\\((\\d+)PLTS\\)";





    private static Pattern CTNS_PLTS_PT = Pattern.compile(CTNS_PLTS);




    public static Map<String, String> getBoxDesc() {
        if (BOX_DESC == null) {
            BOX_DESC = new HashMap<String, String>();

            BOX_DESC.put("CARTON", "çº¸ç®±");




            BOX_DESC.put("PALLET", "æç");
            BOX_DESC.put("DRUM", "æ¡¶");
            BOX_DESC.put("PACKAGE", "ä»¶");





            BOX_DESC.put("WOODEN CASE", "æ¨ç®±");





            BOX_DESC.put("BAG", "å");






            BOX_DESC.put("BOX", "ç");
            BOX_DESC.put("ROLL", "å·");



            BOX_DESC.put("CASE", "ç®±");
            BOX_DESC.put("CRATE", "æ¨æ¡ç®±");

            BOX_DESC.put("CARTONS", "çº¸ç®±");
            BOX_DESC.put("PALLETS", "æç");

            BOX_DESC.put("DRUMS", "æ¡¶");
            BOX_DESC.put("PACKAGES", "ä»¶");



            BOX_DESC.put("WOODEN CASES", "æ¨ç®±");
            BOX_DESC.put("BAGS", "å");
            BOX_DESC.put("BOXES", "ç");
            BOX_DESC.put("ROLLS", "å·");






            BOX_DESC.put("CASES", "ç®±");
            BOX_DESC.put("CRATES", "æ¨æ¡ç®±");
        }
        return BOX_DESC;


    }





    public static Map<String, String> getSpecialName(String oriName) {
        Map<String, String> result = new HashMap<String, String>();
        if (oriName != null && !oriName.isEmpty()) {





            Matcher m = CTNS_PLTS_PT.matcher(oriName.trim());
            if (m.find()) {
                result.put(m.group(1), "æ");
                return result;
            }
            return null;
        }
        return null;




    }

}
