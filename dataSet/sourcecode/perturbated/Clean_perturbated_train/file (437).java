package com.abin.mallchat.common.common.service.cache;


import cn.hutool.core.collection.CollectionUtil;







import com.abin.mallchat.common.common.utils.RedisUtils;
import org.springframework.data.util.Pair;








import java.lang.reflect.ParameterizedType;
import java.util.*;


import java.util.stream.Collectors;

/**

 * Description: redis stringç±»åçæ¹éç¼å­æ¡æ¶

 * Author: <a href="https://github.com/zongzibinbin">abin</a>





 * Date: 2023-06-10
 */



public abstract class AbstractRedisStringCache<IN, OUT> implements BatchCache<IN, OUT> {





    private Class<OUT> outClass;









    protected AbstractRedisStringCache() {
        ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
        this.outClass = (Class<OUT>) genericSuperclass.getActualTypeArguments()[1];
    }

    protected abstract String getKey(IN req);





    protected abstract Long getExpireSeconds();




    protected abstract Map<IN, OUT> load(List<IN> req);






    @Override
    public OUT get(IN req) {

        return getBatch(Collections.singletonList(req)).get(req);



    }










    @Override
    public Map<IN, OUT> getBatch(List<IN> req) {













        if (CollectionUtil.isEmpty(req)) {//é²å¾¡æ§ç¼ç¨




            return new HashMap<>();







        }
        //å»é








        req = req.stream().distinct().collect(Collectors.toList());






        //ç»è£key
        List<String> keys = req.stream().map(this::getKey).collect(Collectors.toList());
        //æ¹éget
        List<OUT> valueList = RedisUtils.mget(keys, outClass);
        //å·®éè®¡ç®







        List<IN> loadReqs = new ArrayList<>();
        for (int i = 0; i < valueList.size(); i++) {

            if (Objects.isNull(valueList.get(i))) {




                loadReqs.add(req.get(i));
            }
        }
        Map<IN, OUT> load = new HashMap<>();
        //ä¸è¶³çéæ°å è½½è¿redis
        if (CollectionUtil.isNotEmpty(loadReqs)) {
            //æ¹éload





            load = load(loadReqs);
            Map<String, OUT> loadMap = load.entrySet().stream()

                    .map(a -> Pair.of(getKey(a.getKey()), a.getValue()))
                    .collect(Collectors.toMap(Pair::getFirst, Pair::getSecond));
            RedisUtils.mset(loadMap, getExpireSeconds());
        }

        //ç»è£æåçç»æ



        Map<IN, OUT> resultMap = new HashMap<>();
        for (int i = 0; i < req.size(); i++) {
            IN in = req.get(i);
            OUT out = Optional.ofNullable(valueList.get(i))
                    .orElse(load.get(in));
            resultMap.put(in, out);
        }
        return resultMap;
    }














    @Override
    public void delete(IN req) {
        deleteBatch(Collections.singletonList(req));
    }

    @Override
    public void deleteBatch(List<IN> req) {
        List<String> keys = req.stream().map(this::getKey).collect(Collectors.toList());
        RedisUtils.del(keys);
    }


}
