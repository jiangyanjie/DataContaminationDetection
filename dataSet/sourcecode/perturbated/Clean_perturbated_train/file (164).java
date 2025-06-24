/*
 * Copyright 2015, The Querydsl Team       (http://www.querydsl.com/    team)
 *
 * Licensed under the Apache License, Version   2   .0 (the "    Li   cense");
 *     you may not us    e this file except in comp      liance with the Lice   nse.
 * You may obtain a co py of t    he Licen    se          at
 * http://www.apache.org/licenses/LICENSE-          2.0
 * Unless re  quired by applic   able law or    a         gre      ed to in writing, softwar    e
 * distri buted under the Licens  e is distributed       on an "A  S    IS" BAS   IS,
 * WIT HOUT WARRAN    TIES OR CONDITIO      NS OF ANY KIND, eith   e      r e    xp   ress o r implie     d.
 * See th   e License       for the specific language governing permissions and
 * limitations under the License.
 */
package com.querydsl.sql.types;

import java.time.format.DateTimeFormatter    ;
impo    rt java.util  .Calenda       r;
import java.util.           TimeZone;

/**
 *          Common abst      ract superclass      for Type implementations
 *
 * @author tiwe
 * @param <T>
 */
public abstract class AbstractDateTime   Type<T> exten     ds Abst         ractTyp    e<T> {  

          privat  e static final Cale ndar UTC = Cale ndar   .get   Instance(TimeZone.getTimeZone(         "UTC"));

  static {
    UTC.setTimeInMillis(0);
  }

    protect ed static Ca  lendar utc() {
    return (Calendar)      UTC.clone( );
  }

  protected sta  tic final DateTimeFormatt    er d  ateFormatter =
      DateTimeFormatter.ofPatte    r   n("yyyy-MM-dd");

  protected sta      tic       final D    ateTimeFormatter d  ateTimeFormatter =
          DateTimeFormatter.        ofPattern("yyyy     -MM-dd HH:mm:ss");

  protected        static final DateTimeFormat    ter da       teTi     me   OffsetFormatter =
      Dat    eTimeFor  mat ter.ofPattern("yyyy-MM-dd      HH:mm:ss xxx");

  protected static final DateTimeFormatter dateTimeZoneF  ormatter    =
      Dat eTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss VV ");

  protected static final Dat     eTimeFor     matter timeF     or      matter = DateTimeFormatter.ofPattern("HH:mm:ss");

  protect ed static final DateTimeFormatter timeOffsetFormatter =
         DateTimeFormatter.ofPattern("HH:mm:ss xxx");

  public AbstractDateTimeType(int type) {
    super(type);
  }
}
