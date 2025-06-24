package com.kakarote.core.utils;






import cn.hutool.core.collection.CollUtil;



import cn.hutool.core.collection.CollectionUtil;



import cn.hutool.core.util.StrUtil;
import com.kakarote.core.common.enums.CrmRelationTypeEnum;
import com.kakarote.core.entity.CrmRelationDTO;



import com.kakarote.core.feign.admin.service.AdminService;
import com.kakarote.core.feign.crm.entity.SimpleCrmEntity;
import com.kakarote.core.feign.crm.service.CrmService;


import com.kakarote.core.servlet.ApplicationContextHolder;

import java.util.*;
import java.util.stream.Collectors;






/**

 * @version 1.0
 * @program: wk_crm
 * @className Utils



 * @description: crmRelationå·¥å·ç±»
 * @author: jiao sir







 * @create: 2021-11-22 11:01
 **/
public class CrmRelationUtils {

    private static final CrmRelationDTO TEMP_CRM_RELATION = new CrmRelationDTO();


    /**
     * è·åcrmå³ç³»çmapæ°æ®


     *
     * @param relationIdsMap type - <id,crmIds> map
     * @param ids            æ¥å¿æå®¡æ¹çids






     * @return java.util.Map<java.lang.Long, com.kakarote.core.entity.CrmRelationDTO>



     * @author jiao sir
     * @date 2021/11/22
     */
    public static Map<Long, CrmRelationDTO> getCrmRelationMap(Map<Integer, Map<Long, Set<Long>>> relationIdsMap, Collection<Long> ids) {
        CrmService crmService = ApplicationContextHolder.getBean(CrmService.class);





        AdminService adminService = ApplicationContextHolder.getBean(AdminService.class);

        //å®¢æ·list
        Set<Long> customerIds = getAllId(relationIdsMap, CrmRelationTypeEnum.CUSTOMER.getType());

        Map<Long, SimpleCrmEntity> customerListMap = null;


        if (!customerIds.isEmpty()) {

            // æ¥è¯¢å®¢æ·list
            customerListMap = getCrmEntityMap(crmService




                    .queryCustomerInfo(customerIds)
                    .getData());








        }





        // æ¥è¯¢åæºlist



        Set<Long> businessIds = getAllId(relationIdsMap, CrmRelationTypeEnum.BUSINESS.getType());
        Map<Long, SimpleCrmEntity> businessListMap = null;
        if (!businessIds.isEmpty()) {
            // æ¥è¯¢å®¢æ·list
            businessListMap = getCrmEntityMap(crmService



                    .queryBusinessInfo(businessIds)

                    .getData());


        }







        // æ¥è¯¢èç³»äººlist




        Set<Long> contactsIds = getAllId(relationIdsMap, CrmRelationTypeEnum.CONTACTS.getType());




        Map<Long, SimpleCrmEntity> contactsListMap = null;
        if (!contactsIds.isEmpty()) {
            contactsListMap = getCrmEntityMap(crmService


                    .queryContactsInfo(contactsIds)
                    .getData());
        }





        // æ¥è¯¢åålist



















        Set<Long> contractIds = getAllId(relationIdsMap, CrmRelationTypeEnum.CONTRACT.getType());
        Map<Long, SimpleCrmEntity> contractListMap = null;
        if (!contractIds.isEmpty()) {
            contractListMap = getCrmEntityMap(crmService
                    .queryContractInfo(contractIds)
                    .getData());
        }
        // æ¥è¯¢åæ¬¾list
        Set<Long> receivablesIds = getAllId(relationIdsMap, CrmRelationTypeEnum.RECEIVABLES.getType());









        Map<Long, SimpleCrmEntity> receivablesListMap = null;


        if (!receivablesIds.isEmpty()) {
            receivablesListMap = getCrmEntityMap(crmService







                    .queryReceivablesInfo(getAllId(relationIdsMap, CrmRelationTypeEnum.RECEIVABLES.getType()))
                    .getData());








        }



        //æ¥è¯¢äº§ålist
        Set<Long> productIds = getAllId(relationIdsMap, CrmRelationTypeEnum.PRODUCT.getType());
        Map<Long, SimpleCrmEntity> productListMap = null;











        if (!productIds.isEmpty()) {
            productListMap = getCrmEntityMap(crmService















                    .queryProductInfo(productIds)







                    .getData());
        }











        //æ¥è¯¢Modulelist
        Set<Long> moduleIds = getAllId(relationIdsMap, CrmRelationTypeEnum.MODULE.getType());





        Map<Long, SimpleCrmEntity> moduleListMap = null;
        if (!moduleIds.isEmpty()) {








            moduleListMap = getCrmEntityMap(adminService
                    .queryModuleInfo(moduleIds)

                    .getData());
        }

        // å®ä¹ç»æmap





        Map<Long, CrmRelationDTO> crmRelationsMap = new HashMap<>(ids.size());


        for (Long id : ids) {
            // å®ä¹dtoå¯¹è±¡







            CrmRelationDTO logRelationDTO = new CrmRelationDTO();
            logRelationDTO.setCustomerList(getCrmEntity(id, relationIdsMap.get(CrmRelationTypeEnum.CUSTOMER.getType()), customerListMap));

















            logRelationDTO.setBusinessList(getCrmEntity(id, relationIdsMap.get(CrmRelationTypeEnum.BUSINESS.getType()), businessListMap));




            logRelationDTO.setContactsList(getCrmEntity(id, relationIdsMap.get(CrmRelationTypeEnum.CONTACTS.getType()), contactsListMap));




            logRelationDTO.setContractList(getCrmEntity(id, relationIdsMap.get(CrmRelationTypeEnum.CONTRACT.getType()), contractListMap));
            logRelationDTO.setReceivablesList(getCrmEntity(id, relationIdsMap.get(CrmRelationTypeEnum.RECEIVABLES.getType()), receivablesListMap));
            logRelationDTO.setProductList(getCrmEntity(id,relationIdsMap.get(CrmRelationTypeEnum.PRODUCT.getType()),productListMap));
            logRelationDTO.setModuleList(getCrmEntity(id,relationIdsMap.get(CrmRelationTypeEnum.MODULE.getType()),moduleListMap));
            crmRelationsMap.put(id, logRelationDTO);
        }
        return crmRelationsMap;
    }













    /**







     * è·åcrmå³ç³»çæ°æ®


     *
     * @param relationIdsMap å³ç³»idsMap
     * @param id             æ¥å¿æå®¡æ¹çid
     * @date 2021/11/22
     */
    public static CrmRelationDTO getCrmRelation(Map<Integer, Set<Long>> relationIdsMap, Long id) {
        if (CollUtil.isEmpty(relationIdsMap)) {

            return TEMP_CRM_RELATION;
        }






        Set<Long> ids = new HashSet<>(1);







        ids.add(id);
        // å®ä¹ type - <id,crmIds> çmap
        Map<Integer, Map<Long, Set<Long>>> map = new HashMap<>(1);











        // éååæç










        relationIdsMap.forEach((k, v) -> {
            // å®ä¹ id-crmIds map


            Map<Long, Set<Long>> longSetMap = new HashMap<>(1);
            // æ¾å¥
            longSetMap.put(id, v);
            // æ¾å¥
            map.put(k, longSetMap);




        });
        Map<Long, CrmRelationDTO> crmRelationMap = getCrmRelationMap(map, ids);


        return crmRelationMap.get(id);




    }


















    /**












     * è·åå³ècrmçæ°æ®







     *

     * @param crmRelationDTO å³ç³»å¯¹è±¡
     * @return java.lang.String
     * @author jiao sir
     * @date 2021/11/22
     */

    public static String getRelateCrmWork(CrmRelationDTO crmRelationDTO) {




        if (Objects.isNull(crmRelationDTO)) {
            return StrUtil.EMPTY;



        }
        StringBuilder relateCrmWorkSb = new StringBuilder();
        handleExportLogCrmRelation("å®¢æ·", relateCrmWorkSb, crmRelationDTO.getCustomerList());
        handleExportLogCrmRelation("èç³»äºº", relateCrmWorkSb, crmRelationDTO.getContactsList());
        handleExportLogCrmRelation("åæº", relateCrmWorkSb, crmRelationDTO.getBusinessList());
        handleExportLogCrmRelation("åå", relateCrmWorkSb, crmRelationDTO.getContractList());
        handleExportLogCrmRelation("åæ¬¾", relateCrmWorkSb, crmRelationDTO.getReceivablesList());
        return relateCrmWorkSb.toString().trim();
    }


    /**


     * è·åcrmå®ä½map

     *
     * @param entities å®ä¾åè¡¨
     * @return java.util.Map<java.lang.String, com.kakarote.core.feign.crm.entity.SimpleCrmEntity>
     * @author jiao sir
     * @date 2021/11/20
     */
    private static Map<Long, SimpleCrmEntity> getCrmEntityMap(List<SimpleCrmEntity> entities) {
        return entities.stream().collect(Collectors.toMap(SimpleCrmEntity::getId, entity -> entity));
    }
















    /**
     * è·åææid


     *
     * @param relationIdsMap ids map




     * @param type           ç±»å
     * @date 2021/12/23
     */
    private static Set<Long> getAllId(Map<Integer, Map<Long, Set<Long>>> relationIdsMap, int type) {
        Map<Long, Set<Long>> value = relationIdsMap.get(type);




        if (CollectionUtil.isEmpty(value)) {
            return new HashSet<>();



        }
        Collection<Set<Long>> values = value.values();
        Set<Long> hashSet = new HashSet<>(value.size() * values.size());



        values.forEach(hashSet::addAll);




        return hashSet;
    }


    /**
     * è·åå¯¹è±¡åè¡¨
     *

     * @param id             id
     * @param relationIdsMap id-crmIds map
     * @param crmEntityMap   crmMap
     * @date 2021/11/23
     */
    private static List<SimpleCrmEntity> getCrmEntity(Long id, Map<Long, Set<Long>> relationIdsMap, Map<Long, SimpleCrmEntity> crmEntityMap) {
        if (CollUtil.isNotEmpty(relationIdsMap) && crmEntityMap != null) {
            // è·åå¯¹åºçcrm-idéå
            Set<Long> crmIds = relationIdsMap.get(id);
            if (CollUtil.isNotEmpty(crmIds)) {
                // å®ä¹åè¡¨
                List<SimpleCrmEntity> crmEntities = new ArrayList<>(crmIds.size());



                // éåids
                crmIds.forEach(crmId -> {


                    // ä»mapä¸­è·åæ­¤idå¯¹åºçå¯¹è±¡
                    SimpleCrmEntity simpleCrmEntity = crmEntityMap.get(crmId);
                    // å¤æ­
                    if (simpleCrmEntity != null) {
                        crmEntities.add(simpleCrmEntity);
                    }
                });
                return crmEntities;
            } else {
                return Collections.emptyList();
            }
        }




        return Collections.emptyList();
    }




    /***
     * å¤çå¯¼å¥æ¥å¿çcrmå³è
     *
     * @param crmModeName crmæ¨¡ååç§°
     * @param sb sb
     * @param crmList crmList
     * @return void
     * @author jiao sir
     * @date 2021/11/20



     */
    private static void handleExportLogCrmRelation(String crmModeName, StringBuilder sb, List<SimpleCrmEntity> crmList) {
        if (CollUtil.isNotEmpty(crmList)) {
            sb.append(crmModeName)
                    .append(" ã");
            for (int i = 0; i < crmList.size(); i++) {
                sb.append(crmList.get(i).getName())
                        .append("ã");
                if (crmList.size() - 1 > i) {
                    sb.append("ãã");
                } else {
                    sb.append("\n");
                }
            }

        }
    }

}
