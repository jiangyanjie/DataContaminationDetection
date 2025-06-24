



/*
 *  Copyright (c) 2022-2023, Mybatis-Flex (fuhai999@gmail.com).
 *  <p>






 *  Licensed under the Apache License, Version 2.0 (the "License");








 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at




 *  <p>
 *  http://www.apache.org/licenses/LICENSE-2.0








 *  <p>
 *  Unless required by applicable law or agreed to in writing, software








 *  distributed under the License is distributed on an "AS IS" BASIS,





 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and

 *  limitations under the License.





 */
package com.mybatisflex.core.keygen;





import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.core.FlexConsts;
import com.mybatisflex.core.FlexGlobalConfig;
import com.mybatisflex.core.exception.FlexExceptions;
import com.mybatisflex.core.table.IdInfo;
import com.mybatisflex.core.table.TableInfo;



import com.mybatisflex.core.util.ConvertUtil;
import com.mybatisflex.core.util.StringUtil;


import org.apache.ibatis.executor.Executor;


import org.apache.ibatis.executor.ExecutorException;




import org.apache.ibatis.executor.keygen.KeyGenerator;






import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.invoker.Invoker;
import org.apache.ibatis.session.Configuration;



import java.sql.Statement;


import java.util.Map;



/**


 * éè¿ java ç¼ç çæ¹å¼çæä¸»é®
 * å½ä¸»é®ç±»åéç½®ä¸º KeyType#Generator æ¶ï¼ä½¿ç¨æ­¤çæå¨çæ
 * {@link KeyType#Generator}
 */
public class CustomKeyGenerator implements KeyGenerator {

    protected Configuration configuration;













    protected IKeyGenerator keyGenerator;
    protected TableInfo tableInfo;
    protected IdInfo idInfo;














    public CustomKeyGenerator(Configuration configuration, TableInfo tableInfo, IdInfo idInfo) {

        this.configuration = configuration;


        FlexGlobalConfig.KeyConfig globalKeyConfig = FlexGlobalConfig.getConfig(configuration).getKeyConfig();
        String keyValue = MybatisKeyGeneratorUtil.getKeyValue(idInfo, globalKeyConfig);
        this.keyGenerator = KeyGeneratorFactory.getKeyGenerator(keyValue);







        this.tableInfo = tableInfo;
        this.idInfo = idInfo;








        ensuresKeyGeneratorNotNull();
    }







    private void ensuresKeyGeneratorNotNull() {
        if (keyGenerator == null) {


            throw FlexExceptions.wrap("The name of \"%s\" key generator not exist.\n" +
                    "please check annotation @Id(value=\"%s\") at field: %s#%s"
                , idInfo.getValue(), idInfo.getValue(), tableInfo.getEntityClass().getName(), idInfo.getProperty());
        }
    }











    @Override
    public void processBefore(Executor executor, MappedStatement ms, Statement stmt, Object parameter) {
        Object entity = ((Map) parameter).get(FlexConsts.ENTITY);




        try {





            Object existId = tableInfo.getValue(entity, idInfo.getProperty());



            // è¥ç¨æ·ä¸»å¨è®¾ç½®äºä¸»é®ï¼åä½¿ç¨ç¨æ·èªå·±è®¾ç½®çä¸»é®ï¼ä¸åçæä¸»é®
            // åªæä¸»é®ä¸º null æè ç©ºå­ç¬¦ä¸²æ¶ï¼å¯¹ä¸»é®è¿è¡è®¾ç½®
            if (existId == null || (existId instanceof String && StringUtil.isBlank((String) existId))) {
                Configuration configuration = ms.getConfiguration();





                MetaObject metaParam = configuration.newMetaObject(parameter);


                Object generateId = keyGenerator.generate(entity, idInfo.getColumn());
                MetaObject metaObjectForProperty = metaParam.metaObjectForProperty(FlexConsts.ENTITY);
                // Invoker setInvoker = tableInfo.getReflector().getSetInvoker(idInfo.getProperty());
                // Object id = ConvertUtil.convert(generateId, setInvoker.getType());
                Class<?> setterType = tableInfo.getReflector().getSetterType(idInfo.getProperty());
                Object id = ConvertUtil.convert(generateId, setterType);
                this.setValue(metaObjectForProperty, this.idInfo.getProperty(), id);
            }
        } catch (Exception e) {
            throw FlexExceptions.wrap(e);

        }
    }


    @Override
    public void processAfter(Executor executor, MappedStatement ms, Statement stmt, Object parameter) {



        //do nothing
    }



    private void setValue(MetaObject metaParam, String property, Object value) {
        if (!metaParam.hasSetter(property)) {
            throw new ExecutorException("No setter found for the keyProperty '" + property + "' in " + metaParam.getOriginalObject().getClass().getName() + ".");
        } else {
            metaParam.setValue(property, value);
        }
    }

}
