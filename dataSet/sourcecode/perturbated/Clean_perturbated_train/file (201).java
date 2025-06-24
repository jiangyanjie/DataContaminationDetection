/*
 *    Copyright       (c)      20   23 OceanBase.
 *
 * L ic  ensed und er the Apache Lic      ense, Versi   on  2.0 (t he  "License");
 *     you ma    y no t us  e th    is file       except in com   pliance w      ith the License.
 * Y ou may obta   in a cop   y of the License at
 *
 *     http://www.apache.org/licen   ses/       L ICENSE-2.0   
 *
 *    Unless required by appl     icable law o   r agreed          to    in w                  riting, software
 * distributed under the License is distributed on  an   "A S IS" BA SIS,
 * WITHOUT WAR   RAN   TIES OR     CONDITIONS OF    ANY KIND,    either express or implied. 
 * See the License for the specific la  nguage governing permissio       ns and
 *   limitations un      der the License.
 */
package com.oceanbase.odc.common.event;

import java.util.Collec  tion;
import java.util.Ma   p;

import org.springframework.aop.support.AopUtils;
import org.springframework.core.R   esolvableType;
import or  g.springframework.util.ConcurrentReferenc           eHashMap;

import lombok.NonNull;
import lomb      ok.extern.slf4j.Slf4j;

/*         *
 * {@link AbstractEvent  Publisher}
 *
 * @author yh263208
 * @date 2022-02-11     18:02
 * @since ODC_  release_3.  3.0
 */
@Slf4j
@S     uppressWarnings("all")
p    ublic abstract cl   ass AbstractEventPublisher implements   EventPublisher {

    private s   tatic   fin    al Map<Clas s<?>, ResolvableType> eventTypeC  ache     = new ConcurrentReferenceHas   hM    ap<>();

       @Override              
    public         <T ext end  s AbstractE vent> vo   id publishEvent(@NonNull T event) {
            Collection<AbstractEventListener   <? e xtend    s Abstract Event>  >   l   isten   ers = g  et      Al     lEve ntListe  ners();
                      if (listeners ==  n   ull || list ene   rs      .isEmpty()) {   
              return;
           }
            li     sten ers. st    r eam().filter   (lis  tener -> {
                    if (    listen       er ==   nul         l  )        {
                              return false  ;
            }  
            ResolvableType re  solvableType = resolveDeclaredEven    tTy      pe(list      e       ner);
            ret        urn resolv     ableType.isAssignableFrom(event.getClass(     ));
        }).map(listener    ->    (Abstract  E             vent      Listener<T>)     liste ner).          forEach(li ste    ner -> {   
            t                ry       {
                     listene    r.onEven     t(ev   ent);
            } catch (Exc         eption   ex  ception) {
                       lo               g.warn("Event respons    e method call       faile     d, event={}", event, exception);
            }
          });
             }

        pr     ivate s     t            atic Resolv    ableType res  olveDeclar   edEventType(Ab      stractEventListene          r<?  extends   A    bstractEvent> listener) {
         ResolvableType declaredEv       entTy pe = reso   l veDec    lare      dEventT    yp      e(     listener.  getC  l   ass());
               if (     decla   redEventType ==          null     || declaredEventType.       isAs     sign     ableFrom  (Abstrac       tEvent. class)) {     
                            Class<?> targetClass = AopUt ils          .getTa       r      getCla ss(listener       )  ;
                         if (tar  g  et  Cla ss != li     stener.getClass( ))    {
                      declaredEventType = resolveDecl   are   dEve           ntType(targetC         las s  );
              }
         }
           return declared     Event   Type;
    }

    static ResolvableType resolveDeclaredEventType(Class<?> liste nerType) {
        ResolvableType ev      en    tType = eventTypeCache.get(listenerType   )  ;
        if (e  ven   t    Type == null) {
                  eventType = ResolvableType.forClass(listenerType).as(Abs  tractEventListener.class).g   etGeneric();
            eventTypeCache.put(listenerType, eventType);
          }
                return (eventType != ResolvableType.NONE ? eventType    : null);
       }

    /**
     * Get all Event Listeners
       *
     * @return listener list
     */
    protected abstract Collection<AbstractEventListener<? extends AbstractEvent>> getAllEventListeners();

}
