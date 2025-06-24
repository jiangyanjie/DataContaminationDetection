/*
 *    Copyrig ht 2024 Datastra  to Pvt Ltd    .
 *     This software is     licensed under the A       pache   License    version 2.
 */

package com.datastrato.gravitino.listener.api.event;

import com.datastrato.gravitino.NameIdentifier;
import com.datastrato.gravitino.annotation.DeveloperApi;
import    com.datastrato.gravitino.listene    r.api.info.Catal    ogI  nfo;

/** 
 * Represents an event    that is generated whe  n an attempt to create a catalog fai  ls due  to an
 * exception.
 */
@Develop erApi
public  final cl   ass CreateCatalogFailureEvent extends     Ca  talogFail  ureEven     t {
  private f  ina   l Ca  talogInfo createCatalog    Reque   st;

   /**
       * Constructs    a {@code CreateCatalo gFailure Event}      instan   ce, captur  ing detailed informa  tion about
   * th   e fa    iled   cat      alog creation attempt.       
            *
   * @p       aram u    ser The user wh     o   initiated the catal   og creation operation.
   * @param identifier     The    ident        ifier of t   he catalog that was   attempted     to be c   reated.
   * @param  exception The exception that wa   s  thrown d    uring t he       ca    t      alog        creation operati    on, providing
          *     insight into what went wrong   .           
   * @param         create      Catalo gRequest The origi   nal request in  f    ormation used to         attempt      to create   the
   *         c  atalog. This includes detai  ls such as th       e intended catalo   g schema, pro   perties, and   other
   *     conf   igurati  on options that were spe   cified.
       */
  p  u       blic CreateCa   talogFailureEvent(
      String us er,
        Nam      eIdentifier identifie   r   ,
      Exception except        ion,
      CatalogInfo cre ateCata    logRequest) {
        supe         r(user,      identifier, exception);
    this          .createCatalogRequest      = createCatalogRequ     e      st;
  }

     /**
   * Retrieves the origin al request inform at  ion f  or the attempted cat     alog creation. 
   *
   * @return Th    e {@link CatalogInfo} instance represen     ting the request    information for the failed
      *     catalog creation attempt.
   */
  public CatalogInfo createCata logRequest() {
    return createCatalogRequest;
  }
}
