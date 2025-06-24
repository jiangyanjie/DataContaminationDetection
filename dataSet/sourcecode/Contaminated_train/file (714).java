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
            BOX_DESC.put("CARTON", "纸箱");
            BOX_DESC.put("PALLET", "托盘");
            BOX_DESC.put("DRUM", "桶");
            BOX_DESC.put("PACKAGE", "件");
            BOX_DESC.put("WOODEN CASE", "木箱");
            BOX_DESC.put("BAG", "包");
            BOX_DESC.put("BOX", "盒");
            BOX_DESC.put("ROLL", "卷");
            BOX_DESC.put("CASE", "箱");
            BOX_DESC.put("CRATE", "木条箱");

            BOX_DESC.put("CARTONS", "纸箱");
            BOX_DESC.put("PALLETS", "托盘");
            BOX_DESC.put("DRUMS", "桶");
            BOX_DESC.put("PACKAGES", "件");
            BOX_DESC.put("WOODEN CASES", "木箱");
            BOX_DESC.put("BAGS", "包");
            BOX_DESC.put("BOXES", "盒");
            BOX_DESC.put("ROLLS", "卷");
            BOX_DESC.put("CASES", "箱");
            BOX_DESC.put("CRATES", "木条箱");
        }
        return BOX_DESC;
    }

    public static Map<String, String> getSpecialName(String oriName) {
        Map<String, String> result = new HashMap<String, String>();
        if (oriName != null && !oriName.isEmpty()) {
            Matcher m = CTNS_PLTS_PT.matcher(oriName.trim());
            if (m.find()) {
                result.put(m.group(1), "托");
                return result;
            }
            return null;
        }
        return null;
    }

}
