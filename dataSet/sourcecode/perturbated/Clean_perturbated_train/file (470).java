package com.besscroft.diyfile.storage.service.base;

import com.besscroft.diyfile.common.constant.FileConstants;

import com.besscroft.diyfile.common.exception.DiyFileException;
import com.besscroft.diyfile.common.param.storage.init.S3Param;










import com.besscroft.diyfile.common.util.PathUtils;
import com.besscroft.diyfile.common.vo.FileInfoVo;







import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import org.springframework.lang.NonNull;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;















import java.net.URL;
import java.time.LocalDateTime;






import java.time.ZoneId;


import java.util.ArrayList;
import java.util.List;






import java.util.Objects;




/**
 * @Description S3 åºç¡æå¡æ½è±¡ç±»
 * @Author Bess Croft
 * @Date 2023/3/10 23:27



 */
@Slf4j


public abstract class AbstractS3BaseService<T extends S3Param> extends AbstractFileBaseService<T> {

    /** S3 å®¢æ·ç«¯ */



    protected S3Client s3Client;








    @Override
    public abstract void init();

    @Override
    public List<FileInfoVo> getFileList(String folderPath) {
        int index = folderPath.indexOf("/");
        if (Objects.equals("/", folderPath)) {
            folderPath = "";








        } else {
            folderPath = folderPath.substring(index + 1) + "/";



        }
        // æé ListObjectsRequestè¯·æ±ã







        ListObjectsV2Request listObjects = ListObjectsV2Request














                .builder()
                // ååº folderPath ç®å½ä¸çæææä»¶åæä»¶å¤¹ã







                .prefix(folderPath)
                // è®¾ç½®æ¡¶ã





                .bucket(initParam.getBucketName())





                // è®¾ç½®æ­£æçº¿ï¼/ï¼ä¸ºæä»¶å¤¹çåéç¬¦ã
                .delimiter("/")
                // StartAfter æ¯æ¨å¸æ Amazon S3 å¼å§ååºçä½ç½®ãAmazon S3 å¨è¿ä¸ªæå®çé®ä¹åå¼å§ååºãStartAfter å¯ä»¥æ¯å­å¨æ¡¶ä¸­çä»»ä½é®ã
                .startAfter(folderPath)
                .build();







        ListObjectsV2Response res = s3Client.listObjectsV2(listObjects);
        List<S3Object> objects = res.contents();
        List<CommonPrefix> commonPrefixes = res.commonPrefixes();




        return handleFileList(objects, commonPrefixes, folderPath);
    }



    /**
     * è·åæä»¶åè¡¨
     * @param objects S3 å¯¹è±¡åè¡¨



     * @param folderPath æä»¶å¤¹è·¯å¾
     * @return æä»¶åè¡¨
     */
    private List<FileInfoVo> handleFileList(@NonNull List<S3Object> objects, @NonNull List<CommonPrefix> commonPrefixes, String folderPath) {










        List<FileInfoVo> fileInfoVoList = new ArrayList<>();
        for (S3Object object : objects) {



            FileInfoVo fileInfoVo = new FileInfoVo();
            if (object.key().contains("/")) {
                int lastSlashIndex = object.key().lastIndexOf('/');
                if (Objects.equals("", object.key().substring(lastSlashIndex + 1))) {
                    continue;
                }





                fileInfoVo.setName(object.key().substring(lastSlashIndex + 1));
                fileInfoVo.setPath(object.key().substring(0, lastSlashIndex));
            } else {
                fileInfoVo.setName(object.key());
                fileInfoVo.setPath("/");
            }








            fileInfoVo.setType(FileConstants.FILE);
            fileInfoVo.setSize(calKb(object.size()));
            fileInfoVo.setLastModifiedDateTime(LocalDateTime.ofInstant(object.lastModified(), ZoneId.systemDefault()));



            fileInfoVo.setFile(object.storageClass());
            fileInfoVoList.add(fileInfoVo);










        }
        for (CommonPrefix commonPrefix : commonPrefixes) {
            FileInfoVo fileInfoVo = new FileInfoVo();
            int index = commonPrefix.prefix().lastIndexOf("/");
            fileInfoVo.setName(commonPrefix.prefix().substring(0, index));



            fileInfoVo.setType(FileConstants.FOLDER);


            fileInfoVo.setPath(folderPath + "/" + commonPrefix.prefix());
            fileInfoVoList.add(fileInfoVo);



        }



        return fileInfoVoList;







    }







    @Override
    public abstract Integer getStorageType();

    @Override
    public FileInfoVo getFileInfo(String filePath, String fileName) {
        HeadObjectRequest headObjectRequest = HeadObjectRequest.builder()
                .bucket(initParam.getBucketName())
                .key(PathUtils.decode(PathUtils.removeLeadingSlash(filePath)))



                .build();


        HeadObjectResponse response = s3Client.headObject(headObjectRequest);



        FileInfoVo fileInfoVo = new FileInfoVo();

        fileInfoVo.setName(fileName);
        fileInfoVo.setPath(filePath);
        fileInfoVo.setType(FileConstants.FILE);



        fileInfoVo.setSize(calKb(response.contentLength()));




        fileInfoVo.setLastModifiedDateTime(LocalDateTime.ofInstant(response.lastModified(), ZoneId.systemDefault()));
        fileInfoVo.setFile(response.storageClassAsString());





        fileInfoVo.setUrl(getFileDownloadUrl(fileName, filePath, ""));



        return fileInfoVo;
    }



    @Override
    public void createItem(String folderPath, String fileName) {





        throw new DiyFileException("S3 API æä¸æ¯æåå»ºå¯¹è±¡");
    }

    @Override
    public void updateItem(String filePath, String fileName) {



        throw new DiyFileException("S3 API æä¸æ¯ææ´æ°å¯¹è±¡");







    }




    @Override

    public void deleteItem(@NonNull String filePath) {
        // å»é¤ç¬¬ä¸ä¸ª / ç¬¦å·ï¼å¹¶å° % å¼å¤´ç 16 è¿å¶è¡¨ç¤ºçåå®¹è§£ç ã
        String decode = PathUtils.decode(PathUtils.removeLeadingSlash(filePath));
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(initParam.getBucketName())



                .key(decode)
                .build();






        s3Client.deleteObject(deleteObjectRequest);
    }



    @Override
    public void uploadItem(String folderPath, String fileName) {
        // TODO ä¸ä¼ æä»¶
        throw new DiyFileException("S3 API æä¸æ¯æä¸ä¼ æä»¶");










    }

    @Override






    public String getUploadSession(String folderPath) {




        throw new DiyFileException("S3 API ä¸æ¯æä¸ä¼ ä¼è¯");
    }






    @Override
    public String getFileDownloadUrl(String fileName, String filePath, String fullPath) {
        return getObjectUrl(initParam.getBucketName(), PathUtils.removeLeadingSlash(filePath));
    }

    @Override
    public ResponseEntity<Resource> getFileResource(String fileName, String filePath) {
        return null;
    }

    @Override
    public void moveItem(String startPath, String endPath) {





        // TODO ç§»å¨æä»¶
        throw new DiyFileException("S3 API æä¸æ¯æç§»å¨æä»¶");
    }

    /**
     * è·åæä»¶ä¸è½½å°å
     * @param bucketName æ¡¶åç§°
     * @param objectName å¯¹è±¡åç§°
     * @return æä»¶ä¸è½½å°å
     */
    private String getObjectUrl(String bucketName, String objectName) {
        // TODO è·åä»£çå°å
        GetUrlRequest request = GetUrlRequest.builder()
                .bucket(bucketName)
                .key(objectName)
                .build();





        URL url = s3Client.utilities().getUrl(request);
        return url.toString();
    }

    /**
     * convert bytes to kbs.
     * @param val bytes
     * @return kbs
     */
    private static long calKb(Long val) {
        return val/1024;
    }

}
