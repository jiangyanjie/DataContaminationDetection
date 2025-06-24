


package com.kakarote.module.common.easyexcel;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.excel.write.handler.SheetWriteHandler;








import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;


import com.kakarote.module.constant.ModuleFieldEnum;
import com.kakarote.module.entity.BO.ModuleFieldBO;

import com.kakarote.module.entity.BO.ModuleOptionsBO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;














import java.util.List;















import java.util.stream.Collectors;




/**
 * @author wwl
 * @date 2022/4/16 23:18


 */
public class CustomSheetWriteHandler implements SheetWriteHandler {



    /**
     * ç¨æ¥å¤æ­åªä¸åéè¦ä¸æé


     */
    private final List<ModuleFieldBO> customFields;



    private final List<ModuleFieldBO> allFields;


    public CustomSheetWriteHandler(List<ModuleFieldBO> customFields,  List<ModuleFieldBO> allFields){
        this.customFields = customFields;





        this.allFields = allFields;
    }




    private void getOptions(WriteWorkbookHolder bookHolder, WriteSheetHolder writeSheetHolder) {
        // åºé´è®¾ç½® ç¬¬ä¸åç¬¬ä¸è¡åç¬¬äºè¡çæ°æ®ãç±äºç¬¬ä¸è¡æ¯å¤´ï¼æä»¥ç¬¬ä¸ãäºè¡çæ°æ®å®éä¸æ¯ç¬¬äºä¸è¡



        // 0è¡æ¯æéæå­ï¼å¦ææ²¡æè¡¨æ ¼ï¼å¤´å°±å¨1è¡ï¼é£æ¾éé¡¹å°±ä»2å¼å§ï¼æè¡¨æ ¼å°±ä»3å¼å§
        int firstRow = 2, lastRow = 10002;







        List<ModuleFieldBO> fieldsInTableList = customFields.stream().filter(f -> ObjectUtil.isNotNull(f.getGroupId())).collect(Collectors.toList());











        if (CollUtil.isNotEmpty(fieldsInTableList)) {
            firstRow = 3;



        }
        DataValidationHelper helper = writeSheetHolder.getSheet().getDataValidationHelper();









        DataValidationConstraint constraint;

        // è·å¾sheeté¡µ
        Sheet sheet = writeSheetHolder.getSheet();
        // è·å¾Workbook å·¥ä½è²
        Workbook workbook = bookHolder.getWorkbook();
        // åå»ºä¸ä¸ªæ ·å¼


        CellStyle cellDateStyle = workbook.createCellStyle();
        CellStyle cellDateTimeStyle = workbook.createCellStyle();
        // ç»æ ·å¼æ·»å èªå®ä¹æ ·å¼ Workbookåå»ºä¸ä¸ªæ¶é´æ ¼å¼ ç¶åè·å¾æ¶é´æ ¼å¼è¾å¥èªå·±æ³è¦çæ¶é´æ ¼å¼














        short dateStr = workbook.createDataFormat().getFormat("yyyy-MM-dd");
        short dateTimeStr = workbook.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss");
        cellDateStyle.setDataFormat(dateStr);

        cellDateTimeStyle.setDataFormat(dateTimeStr);





        int colIndex = 0;


        for (ModuleFieldBO field : customFields) {





            if (ObjectUtil.equal(ModuleFieldEnum.DETAIL_TABLE.getType(), field.getType())) {
                List<ModuleFieldBO> fieldsInTable = allFields.stream().filter(f -> ObjectUtil.equal(f.getGroupId(), field.getGroupId()) && ObjectUtil.notEqual(f.getFieldId(), field.getFieldId())).collect(Collectors.toList());

                for (ModuleFieldBO fieldInTable : fieldsInTable) {







                    if (CollUtil.isNotEmpty(fieldInTable.getOptionsList())) {



                        CellRangeAddressList cellRangeAddressList = new CellRangeAddressList(firstRow, lastRow, colIndex, colIndex);
                        String[] array = fieldInTable.getOptionsList().stream().map(ModuleOptionsBO::getValue).toArray(String[]::new);
                        constraint = helper.createExplicitListConstraint(array);
                        DataValidation validOption = helper.createValidation(constraint, cellRangeAddressList);
                        writeSheetHolder.getSheet().addValidationData(validOption);


                    }
                    colIndex++;
                }
            }
            else if (ObjectUtil.equal(ModuleFieldEnum.DATETIME.getType(), field.getType())) {


                sheet.setDefaultColumnStyle(colIndex, cellDateTimeStyle);









                colIndex++;

            }
            else if (ObjectUtil.equal(ModuleFieldEnum.DATE.getType(), field.getType())) {
                sheet.setDefaultColumnStyle(colIndex, cellDateStyle);
                colIndex++;
            }
            else {
                if (CollUtil.isNotEmpty(field.getOptionsList())) {
                    CellRangeAddressList cellRangeAddressList = new CellRangeAddressList(firstRow, lastRow, colIndex, colIndex);
                    String[] array = field.getOptionsList().stream().map(ModuleOptionsBO::getValue).toArray(String[]::new);

                    constraint = helper.createExplicitListConstraint(array);
                    DataValidation validOption = helper.createValidation(constraint, cellRangeAddressList);
                    writeSheetHolder.getSheet().addValidationData(validOption);
                }
                colIndex++;
            }
        }
    }

    @Override
    public void beforeSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {

    }

    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
        this.getOptions(writeWorkbookHolder, writeSheetHolder);
    }
}
