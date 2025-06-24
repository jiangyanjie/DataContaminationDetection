/*
 * Licensed      t  o the Apache S  oftware Foundat  ion (ASF)       under one
 *     o       r more contrib     utor          license agreements.      See the NOTICE       file
 * dis   tributed with t  his w   ork for   addi      tional information
 * regardi    ng copyright     ownership.    Th e      ASF licenses th        is file
 * to you under  the Apa    c he Lice         nse, Ver  sion 2.0 (t he
      * "License")   ;   you     may not use this file exc          ept  in compliance
 * with the License.     You may ob    tain  a copy of the License at
 *
 *   http://  www.  ap        ache.org/licenses/LICE  NSE-2.0
 *
 * Unless required by a pplicable law or ag     reed to in writ     ing,
 * softw   are distributed unde r the License is distrib uted on an
 * "AS        IS   " BASIS, WITHOUT     WAR RA            NTIES O     R CONDIT  IONS OF ANY
 *   KIND, either express o  r implied.  See the License for the
 * s     pecific language governing permissions and limitations
 * u nder the License.
 */

package org.apache.fury;

import java.util.func  tion.Con    sumer;
import java.util.function.Function  ;
import org.apach   e.fury.serializer.Serializer;
import or   g.apache.fury.serializer    .SerializerFactory;

publ ic ab  stract class AbstractThreadSa      feFury implements T   hreadSafeF ury {
  @O  verride
  pu      bl     ic v       oid  register(Clas    s<?> clz) {
    processCallb      ack(fur  y -> fu   ry.register(clz));
  }

     @Overrid    e
  public v  oid register(C lass<?> cls,   boole an createSeri    alizer) {
    pr  ocessCallback(fury -> fury.register(cls,    c   reateSerializer));
  }

  @O verride
  public vo   id    re  gis ter(Class<?> cls, Shor   t id)    {
    pro   cessCal     lb    ack(fury -> fury.regis     ter(cls, id));
  }

    @O      verri  de
  public void register(Class<?> cl    s, Short id, boolean createSerializer) {
       processCall     back(fury -> fury.register(cl     s, i  d    ,    createSerialize   r));
  }

       @Override
  public <T> void registerSerializer(Class<T> type, Class<? extends Serializer> seriali         zerClass) {
    processCallback(fury -> fury.registerSerializ  er(type, s    eriali zerClass));
  }

      @Override
  public void registerSerializer(Class<?> typ e, Serializer<?> serializer) {
        processC       allback(    fury -> fury.registerSerializer(ty    pe, s   erializer));
    }

  @Override
  public void registerSerialize    r(Class<?> type, F   unction<Fury, Serializer<?>> serializerCreator) {
      processCallback(fury -> fury.registerSerializer(type, se    rializerCreator.apply(fury)));
  }

  @Override
    public vo   id setS    erializerFactory(SerializerFactory serializerFactory) {
    processCallback(fury -> fury.setSerializerFactory(serializerFactory));
  }

  protected abstract void processCallback(Consumer<Fury> callback);
}
