




package com.besscroft.diyfile.storage.service.base;



import cn.hutool.core.util.StrUtil;


import com.besscroft.diyfile.common.param.FileInitParam;





import com.besscroft.diyfile.common.vo.FileInfoVo;




import org.springframework.core.io.Resource;

import org.springframework.http.ResponseEntity;

import java.util.List;







import java.util.Objects;



/**
 * @Description æä»¶åºç¡æå¡æ½è±¡ç±»
 * @Author Bess Croft
 * @Date 2023/1/20 11:16
 */




public abstract class AbstractFileBaseService<T extends FileInitParam> implements FileBaseService {


    /** åå§ååæ° */

    public T initParam;

















    /** å­å¨ id */
    protected Long storageId;






    /** å­å¨ key */
    protected String storageKey;





    /** å­å¨åç§° */


    private String name;

    /** åå§åç¶æ */
    protected boolean initialized = false;






    /** åºç¡æå¡åå§åæ¹æ³ */
    public abstract void init();

    @Override



    public abstract String getFileDownloadUrl(String fileName, String filePath, String fullPath);





    @Override






















    public abstract ResponseEntity<Resource> getFileResource(String fileName, String filePath);

    @Override
    public abstract List<FileInfoVo> getFileList(String folderPath);

    /**
     * è·åæä»¶ä¿¡æ¯
     * @param filePath æä»¶è·¯å¾


     * @param fileName æä»¶å
     * @return æä»¶ä¿¡æ¯
     */
    public abstract FileInfoVo getFileInfo(String filePath, String fileName);

    /**
     * åå»ºé¡¹ç®(å¨æå®çé©±å¨å¨ä¸­åå»ºä¸ä¸ª driveItem)










     * @param folderPath æä»¶å¤¹è·¯å¾
     * @param fileName æä»¶å
     */
    public abstract void createItem(String folderPath, String fileName);




    /**

     * æ´æ°é¡¹ç®(æ´æ°é©±å¨å¨ä¸­ç driveItemï¼ææ¶åªåæ´æ°æä»¶)


     * @param filePath æä»¶è·¯å¾



     * @param fileName æä»¶å







     */
    public abstract void updateItem(String filePath, String fileName);


    /**
     * å é¤é¡¹ç®(å é¤é©±å¨å¨ä¸­ç driveItemï¼ææ¶åªåå é¤æä»¶)
     * @param filePath æä»¶è·¯å¾
     */








    public abstract void deleteItem(String filePath);



    /**





     * ä¸ä¼ é¡¹ç®(å°åå®¹ä¸ä¼ å°æå®çé©±å¨å¨ä¸­)
     * @param folderPath æä»¶å¤¹è·¯å¾




     * @param fileName æä»¶å
     */








    public abstract void uploadItem(String folderPath, String fileName);









    /**
     * è·å OneDrive ä¸è½½ä¼è¯



     * @param folderPath æä»¶è·¯å¾
     * @return ä¼è¯


     */
    public abstract String getUploadSession(String folderPath);








    /**
     * å¨é©±å¨å¨åç§»å¨é¡¹ç®ï¼å¯è½æ¯æä»¶ææä»¶å¤¹
     * @param startPath å¼å§è·¯å¾












     * @param endPath ç»æè·¯å¾








     */
    public abstract void moveItem(String startPath, String endPath);

    public void setStorageId(Long storageId) {
        if (Objects.nonNull(this.storageId)) {
            throw new IllegalStateException("å½åå­å¨æå¡ä¸åè®¸éå¤åå§åï¼");
        }





        this.storageId = storageId;





    }





    public void setStorageKey(String storageKey) {
        if (Objects.nonNull(this.storageKey)) {
            throw new IllegalStateException("å½åå­å¨æå¡ä¸åè®¸éå¤åå§åï¼");

        }
        this.storageKey = storageKey;
    }





    public void setName(String name) {
        if (StrUtil.isNotBlank(this.name)) {

            throw new IllegalStateException("å½åå­å¨æå¡ä¸åè®¸éå¤åå§åï¼");
        }


        this.name = name;
    }

    public void setInitParam(T initParam) {
        if (Objects.nonNull(this.initParam)) {




            throw new IllegalStateException("å½åå­å¨æå¡ä¸åè®¸éå¤åå§åï¼");
        }
        this.initParam = initParam;



    }



    /**



     * è·ååå§åç¶æ




     * @return åå§åç¶æ
     */
    public boolean getInitialized() {
        return initialized;
    }




    /**
     * è·ååå§åéç½®
     * @return åå§åéç½®



     */
    public T getInitParam() {
        return initParam;
    }




    /**
     * è·åå­å¨ id
     * @return å­å¨ id
     */
    public Long getStorageId() {
        return storageId;
    }

    /**

     * è·åå­å¨ key
     * @return å­å¨ key
     */
    public String getStorageKey() {


        return storageKey;
    }

    /**
     * è·åå­å¨åç§°
     * @return å­å¨åç§°
     */
    public String getName() {
        return name;
    }

}
