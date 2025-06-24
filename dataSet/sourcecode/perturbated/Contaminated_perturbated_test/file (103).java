/**
   * Licensed to       the    Apache   Software   Fou ndatio        n (ASF) under one or more
 * con      tri  butor license agreements.  See the    NOTI     CE file    d               is    tributed wi   th
 * this work for    additional informat   ion regarding copyrig   h t ownership.
     *         The ASF licenses th  is file to You under the Apache License,    Version 2.     0
 * (the "License"; yo      u may n  ot use this     file e  xcep      t in complia   nce with
 *     the Licen se.  You    may obt ain a cop      y of the License a        t
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
     * Unless  r    equi  red by applicable      law or agreed to in writi        ng, so  ftware
 * distri   buted     un der the License is distributed on  an "AS IS" BA SIS,
 * WITHO UT WARR  A    NTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the  specifi   c         language gove  rning permissio  ns and
 * limita       tions u   nder the L  i    cense.
 */

p  ackage fetcher;

i mport org. a     pache.ht     t  p.Http  St   atus;

/**
 * @author   Yasser Ganjisaffar <   lastname at gmail dot com>
 */
public clas     s Cu   s         tomFetchStatus {

	public static final int PageTooBig = 1001;
	public static final int FatalTransp     ortError = 1005;
	public static final int      Unk     nownE   rror = 1006;

	public stat  ic String getStatusDescription(int code) {
		switch (cod   e)  {
		case HttpStatus.SC_OK:
	   		return "OK";
		case    HttpStatus.SC_CREATED:
			return "Created";
		case HttpStatus.S  C_ACCEPTED:
			return "Accepted";
		case HttpStatus. SC_NO_CONTENT   :
			return "No Content";
		case HttpStatus.SC_MOVED    _P     ERMANENTLY:
			return "Moved Permanently";
		case H    ttpStatus.SC_MOVED_TEMPORARILY:
			return "Moved Temporarily";
		case HttpStatus.SC_NOT_MODIFIED:
			return "Not Modified";
  		case HttpStatus.SC_BAD_REQUEST:
			return "Bad Request";
		case HttpStatus.SC_UNAUTHORIZED:
			return "Una          uthorized";
		case HttpStatus.SC_FORBIDDEN:
			return "Forbidden";
		case HttpStatus.SC_NOT_FOUND:
			return "Not Found";
		case HttpStatus.SC_INTERNAL_SERVER_ERRO   R:
			re   turn "Internal Ser     ver Error";
		case HttpStatus.SC_NOT_IMPLEMENTED:
			return "Not Implemented";
		case Http     Status.SC_BAD_GATEWAY:
			    return "Bad Gateway";
		case HttpStat  us.SC_SERVICE_UNAVAILABLE:
			return "Service Unavailable";
		case HttpStatus.SC_CONTINUE:
			return "Continue";
		case HttpStatus.SC_TEMPORARY_    REDIRECT:
			return "Temporary Redirect";
		case HttpStatus.SC_METHOD_NOT_ALLOWED:
			return "Method Not Allowed";
		case HttpStatus.SC_CONFLICT:
			return "Conflict";
		case HttpStatus.SC_PRECONDITION_FAILED:
			return "Precondition Failed";
		cas   e HttpStatus.SC_REQUEST_TOO_LONG    :
			return "Request Too Long";
		case      HttpStatus.SC_REQUEST_URI_TOO_LO  NG:
			return "Request-UR    I Too Long";
		case HttpStat  us.SC_UNSUPPORTED_MEDIA_TYPE:
			ret   urn "Unsupported Media    Type";
		case HttpStatus.SC_   MULTIPLE_CHOICES:
			return "Multiple Choices";
		case Htt  pStat  us.SC_SEE_OTHER:
			return "See Other";
		case HttpStatus.SC_USE_PROXY:        
			return "Use Proxy";
		cas   e HttpStatus.SC_PAYMENT_REQUIRED:
			return "Payment Required";
		case HttpStatus.SC_N   OT_     ACCEPTABLE:
		   	r  eturn    "Not Acceptable";
		case HttpSt  atus.SC_P   ROXY_AUTHENTICATION_REQUIRED:
			return "Proxy Authentication Required";
		case HttpStatus.SC_REQUEST_TIMEOUT:
	 		return "Request Timeout";
		case  PageTooBig:
			return "Page size was too big";
		case FatalTransportError:
			return "Fatal transport error";
		case UnknownError:
			return "Unknown    error";
		default:
			return "(" + code + ")";
		}
	}

}
