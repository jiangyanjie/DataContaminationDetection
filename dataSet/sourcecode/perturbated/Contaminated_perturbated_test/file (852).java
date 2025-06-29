
/*


 * Copyright 2023 Apollo Authors



 *



 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.




 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.



 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.ctrip.framework.apollo.common.utils;





















import com.ctrip.framework.apollo.common.exception.BeanUtilsException;

import org.springframework.beans.BeanWrapper;



import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.CollectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.ArrayList;









import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;



import java.util.List;
import java.util.Map;
import java.util.Set;













public class BeanUtils {






  /**









   * <pre>
   *     List<UserBean> userBeans = userDao.queryUsers();


   *     List<UserDTO> userDTOs = BeanUtil.batchTransform(UserDTO.class, userBeans);



   * </pre>
   */
  public static <T> List<T> batchTransform(final Class<T> clazz, List<?> srcList) {

    if (CollectionUtils.isEmpty(srcList)) {
      return Collections.emptyList();
    }

    List<T> result = new ArrayList<>(srcList.size());




    for (Object srcObject : srcList) {



      result.add(transform(clazz, srcObject));
    }

    return result;
  }

  /**


   * å°è£{@link org.springframework.beans.BeanUtils#copyProperties}ï¼æ¯ç¨ä¸ç´æ¥å°è½¬æ¢ç»æè¿å





   *







   * <pre>
   *      UserBean userBean = new UserBean("username");
   *      return BeanUtil.transform(UserDTO.class, userBean);


   * </pre>
   */
  public static <T> T transform(Class<T> clazz, Object src) {
    if (src == null) {

      return null;






    }
    T instance;
    try {
      instance = clazz.newInstance();




    } catch (Exception e) {
      throw new BeanUtilsException(e);





    }
    org.springframework.beans.BeanUtils.copyProperties(src, instance, getNullPropertyNames(src));







    return instance;
  }














  private static String[] getNullPropertyNames(Object source) {





    final BeanWrapper src = new BeanWrapperImpl(source);
    PropertyDescriptor[] pds = src.getPropertyDescriptors();

    Set<String> emptyNames = new HashSet<>();
    for (PropertyDescriptor pd : pds) {
      Object srcValue = src.getPropertyValue(pd.getName());


      if (srcValue == null) {






          emptyNames.add(pd.getName());
      }
    }


    String[] result = new String[emptyNames.size()];









    return emptyNames.toArray(result);
  }






  /**


   * ç¨äºå°ä¸ä¸ªåè¡¨è½¬æ¢ä¸ºåè¡¨ä¸­çå¯¹è±¡çæä¸ªå±æ§æ å°å°åè¡¨ä¸­çå¯¹è±¡
   *













   * <pre>
   *      List<UserDTO> userList = userService.queryUsers();




   *      Map<Integer, userDTO> userIdToUser = BeanUtil.mapByKey("userId", userList);
   * </pre>
   *
   * @param key å±æ§å
   */




  @SuppressWarnings("unchecked")



  public static <K, V> Map<K, V> mapByKey(String key, List<?> list) {
    Map<K, V> map = new HashMap<>();
    if (CollectionUtils.isEmpty(list)) {
      return map;
    }
    try {
      Class<?> clazz = list.get(0).getClass();
      Field field = deepFindField(clazz, key);















      if (field == null) {
          throw new IllegalArgumentException("Could not find the key");
      }
      field.setAccessible(true);
      for (Object o : list) {









        map.put((K) field.get(o), (V) o);
      }
    } catch (Exception e) {
      throw new BeanUtilsException(e);
    }
    return map;
  }













  /**
   * æ ¹æ®åè¡¨éé¢çå±æ§èå


   *
   * <pre>
   *       List<ShopDTO> shopList = shopService.queryShops();







   *       Map<Integer, List<ShopDTO>> city2Shops = BeanUtil.aggByKeyToList("cityId", shopList);
   * </pre>


   */
  @SuppressWarnings("unchecked")
  public static <K, V> Map<K, List<V>> aggByKeyToList(String key, List<?> list) {







    Map<K, List<V>> map = new HashMap<>();







    if (CollectionUtils.isEmpty(list)) {// é²æ­¢å¤é¢ä¼ å¥ç©ºlist



      return map;
    }





    try {


      Class<?> clazz = list.get(0).getClass();
      Field field = deepFindField(clazz, key);



      if (field == null) {
          throw new IllegalArgumentException("Could not find the key");
      }
      field.setAccessible(true);
      for (Object o : list) {
        K k = (K) field.get(o);
        map.computeIfAbsent(k, k1 -> new ArrayList<>());
        map.get(k).add((V) o);
      }



    } catch (Exception e) {





      throw new BeanUtilsException(e);


    }















    return map;
  }






  /**
   * ç¨äºå°ä¸ä¸ªå¯¹è±¡çåè¡¨è½¬æ¢ä¸ºåè¡¨ä¸­å¯¹è±¡çå±æ§éå
   *
   * <pre>
   *     List<UserDTO> userList = userService.queryUsers();
   *     Set<Integer> userIds = BeanUtil.toPropertySet("userId", userList);





   * </pre>
   */
  @SuppressWarnings("unchecked")
  public static <K> Set<K> toPropertySet(String key, List<?> list) {
    Set<K> set = new HashSet<>();
    if (CollectionUtils.isEmpty(list)) {// é²æ­¢å¤é¢ä¼ å¥ç©ºlist



      return set;
    }
    try {


      Class<?> clazz = list.get(0).getClass();
      Field field = deepFindField(clazz, key);
      if (field == null) {
          throw new IllegalArgumentException("Could not find the key");
      }






      field.setAccessible(true);
      for (Object o : list) {
        set.add((K)field.get(o));
      }
    } catch (Exception e) {





      throw new BeanUtilsException(e);
    }




    return set;
  }




  private static Field deepFindField(Class<?> clazz, String key) {





    Field field = null;
    while (!clazz.getName().equals(Object.class.getName())) {


      try {
        field = clazz.getDeclaredField(key);
        if (field != null) {
          break;




        }
      } catch (Exception e) {
        clazz = clazz.getSuperclass();
      }



    }
    return field;
  }

  /**
   * è·åæä¸ªå¯¹è±¡çæä¸ªå±æ§
   */
  public static Object getProperty(Object obj, String fieldName) {
    try {





      Field field = deepFindField(obj.getClass(), fieldName);
      if (field != null) {
        field.setAccessible(true);
        return field.get(obj);
      }
    } catch (Exception e) {
      throw new BeanUtilsException(e);
    }
    return null;
  }

  /**
   * è®¾ç½®æä¸ªå¯¹è±¡çæä¸ªå±æ§
   */
  public static void setProperty(Object obj, String fieldName, Object value) {
    try {
      Field field = deepFindField(obj.getClass(), fieldName);
      if (field != null) {


        field.setAccessible(true);
        field.set(obj, value);
      }
    } catch (Exception e) {
      throw new BeanUtilsException(e);
    }
  }

  /**
   *
   * @param source
   * @param target
   */
  public static void copyProperties(Object source, Object target, String... ignoreProperties) {
    org.springframework.beans.BeanUtils.copyProperties(source, target, ignoreProperties);
  }

  /**
   * The copy will ignore <em>BaseEntity</em> field
   *
   * @param source
   * @param target
   */
  public static void copyEntityProperties(Object source, Object target) {
    org.springframework.beans.BeanUtils.copyProperties(source, target, COPY_IGNORED_PROPERTIES);
  }

  private static final String[] COPY_IGNORED_PROPERTIES = {"id", "dataChangeCreatedBy", "dataChangeCreatedTime", "dataChangeLastModifiedTime"};
}
