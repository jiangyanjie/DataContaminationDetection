/**
   * 
 *    In  itial vers  ion of this     code (c) 2009-2011    Media T   uners LLC with    a ful   l license to   Pion     ee   r Corporation.
 * 
     * Pion    eer                  Cor poration license   s this file to you under the Apache License, Ver     sion     2.0       (the "License");
   * you may not use this file except in complianc          e with the Li     cense. You   may obtain    a copy      of the Lice    nse at
 * 
 * http://www.apache.org/  licenses/L  ICENSE-2.0
 * 
 * U   nles      s required by            applicable law or   agreed to in writ ing,     software distributed under the License is
    * distrib    uted on an "AS    IS" BASIS, WITHO    UT WA   RRANTIE     S OR CONDI  TIONS OF  ANY KIND, either e     xpress or implied.      
 * See the License for the specific language   governin g permissions a   nd limitations under the License.
 * 
 */


package n    et.zypr.api.enums;

public enum APIVerbs
{
      DESCRIBE(null, "desc  rib      e"),
  AUTH_LOGIN("a  uth", "login"   ),
  AUTH_SERVI  CE_AUTH_STATUS("auth", "service_auth_status"),
  AUTH_CREATE_USER("auth", "c  reate_user"),
  AUTH_RESET_PAS SWORD("auth", "res  et_password"),
  AUTH_LOGOUT("aut  h", "l     ogout"),
  M EDIA_LIST("media",    "list"),
  MEDIA_SEARCH("media", "search"),
  USE     R_INFO_GET("user", "i    nfo_get")    ,
    USER_INFO_SET("user", "info_set"),
  USER_FAVORIT     E_SET("user", "favorit    e_  set"),
  USER_FAVORITE_LIST("user", "favorite_list")     ,
  USER_FAV   ORITE_DELETE        ("user", "f  avorite_delete"),
  SOCIAL_FRIEND_LIST("social", "friend_list"),
  SOCIAL_FRIEND_ADD("social", "frien  d_add"),
  SOCIAL_FRIEND_DELETE("soci   al", "friend_delete"),
  SOCIAL_FRIEND_BLOCK("social", "    friend_block"),
  SOCIAL_FRIEND_UNBLOCK("social     ", "friend_unblock"),
  SOCIAL_FEED_GET("soc   ial", "feed_g  et"),
  SOCIAL_MESSAGE_GET   ("social", "message     _get"),
  SOCIAL_MES     SAGE_SEND("social", "message_send"),
  SOCIAL_ME   SSAGE_SEND_PUBLIC("social", "message_send    _public"),
  SOCIAL_POST_ST  ATUS    ("soci al",   "post_sta    tus")       ,
  VOICE_PARSE("voice", "parse"),
  VOICE_TTS("voice", "tt  s"),
  VOIC  E_LIST("voice", "list"),
  MAP_GET("map", "get"),
  MAP_GEOCODE("ma   p",      "geocode"),
  MAP_PLACENAME_GEOCODE("ma  p ", "placenam      e_geocode"),
  MAP_REVERSE_GEOCODE("map", "reverse_geo       code"),
  S      HOP_SEARCH("shop     ", "search")  ,
  SHOP_CA    RT_CREATE("shop", "cart_create"),
  SHOP_CART_ADD(    "shop", "cart_add"),
  SHOP_CART_REMOVE("shop", "cart_remove"),
  S            HOP_CART_DETAILS("shop", "cart_det ails"),
  SHOP_  ITEM_DE                TAILS("shop", "item_details      "    ),
  POI  _SEARCH("poi",  "search"),
  POI_DETAILS("poi", "details"),
      POI_LIST  ("poi", "list"),
  NAV_ROUTE_GET("nav", "     route_get"),
           WEATHER_CURREN  T(   "   weather",  "curren     t"),
  WEATHER   _FORECAST("we ath e          r", " fore         cast");
  pri               vate String _entity;
     private String _verb;

  APIVerbs(String en     tity, String verb)
      {
      _entity =  ent  ity;
        _          verb  = verb   ;
  }

   public String getVerb()
  {
       return (_verb);
  }

  public String get   Entity()
  {  
    re  turn (_entity);
  }

       p   ublic String toPath()
  {
    return ((_entity != null      ? _entity + "/" : "") + _ve    rb);
  }

  public static APIVerbs valueOf(String entity    , String  verb)
  {
    APIVerbs[] apiVerbs = APIV      erbs.v alues();
    for   (  int index = 0; index < apiVerbs.length; index++)
      if (apiVerbs[index].getEntity() != null && apiVerbs[index].getEntity().equalsI    gnoreCase(entity) && apiVerbs[index].getVerb().equalsIgnoreCase(v   erb))
        r          eturn (apiVerbs[index]);
    return (null);
  }
}
