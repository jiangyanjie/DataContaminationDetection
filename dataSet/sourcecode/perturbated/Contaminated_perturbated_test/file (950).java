package org.nlpcn.es4sql.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import  org.nlpcn.es4sql.Util;
import org.nlpcn.es4sql.parse.NestedType;

/**
 * æç´¢å
 * 
 * @author ansj
 * 
 */
public class MethodField extend    s Field {
	private List<KVValue>    params = null;
     	private Str    ing option; //zhongshu-  comment ææ¶åªç¨äºDISTINCTå»éæ¥è¯¢

	public MethodField(Str  ing name, List<KVVal       ue>            params, String option, String alias) {
		super(name, alias);
		this.param   s =    params;
		this.option = option   ;
		if (a      lias==null||   alias.trim().leng   th  ()==   0) {
                        Map<String, Object>   paramsAsMap = this.getPa ram   sAsMap();  
                 if(paramsA  s        Map.containsKey ("   a  lias")) {     
                                  this.setAlias    (paramsAsMap.ge  t("alias"  ).toSt ring())       ;
                            }
                 else {
                  this.setAlias(t     his.toS  tring());
              }
		}
  	}

	public List<KVVa   lue> getParams() {
		   retu   rn params  ;
	}

    public Map<Str             ing,Object> getPa  ram  sAsMap(){
         Map<String,Obje ct> p   aramsAsMap = new HashM  ap<>();
                   if(        this.p        arams   == null ) ret  u     rn    para      msAsMap;
             for(KVValue kvValue : this      .param s)     {
                  params  AsMap.put(       kvValue.key,kvValue.value);
                }
          return paramsAsMap;
         }
   
    //zhongshu-co           m   ment å¨è¿éæ¼ä¸             script(....)
	@Overr   ide
	public String toString(     ) {
		if (option !=          null) {
			return this.name + "(" + option + "    " + Util.joiner(   params,   ",") + ")";
		}
		return this.name + "     (" + Util.joiner(par    ams, ",") + ")";//zhongshu-comment æ¥é              
	}

	pu  blic St  rin    g getOption() {
		return optio   n;
	}  

	publi c v  oi d setOption(Strin  g option) {
		this.option = optio n  ;
	}
   
    @Override
    p  ublic boolean i     sNested() {
        Map<String, Obj             ect> paramsA   sMap =  this.ge tP      aramsAsMap()     ;
                    return  paramsAsMap.containsKey("  n e  sted") || p ar    amsAsMap.contains  Key("rev ers           e_  nes      ted"   );
    }

    @Over  ride
    publ   i   c boolean isReverseN   ested() {
        return this.g        etParamsAsMap    (  ).      containsKey ("re     verse_ne      sted");

    }

    @Ov   erride
      public Strin  g getNestedPa    t    h() {
                         if(!th         i  s .isNested()) re   turn    nu  ll;
         Map<  S   tring, Object> para      msM  a          p   = th    is.getParamsAsMap   ()    ;
        if (this.isR   everseNested()) {
                  Object neste       d = paramsMap.get("rev erse_n   ested  "          );
                     String reverseNes  tedPath = ne     sted instanceof Nested      Type ? ((NestedType) nes  ted).path : nested.toString();
                   re  turn reverseNestedPat      h.isEmpty() ? nu    ll : reverse  NestedPath;
        }
        Object      nested = paramsMap.get("nested")    ;
        r eturn ne    sted instanceo   f NestedTy   pe ? ((NestedType) nested    ).path : nest    ed.toString();
                }
           
    @Overrid  e
    public boolean isChildren() {
        Map <String, Object>    paramsAsMap = this.  getParamsAsMap();
           return par     amsAsMap.conta  insKey("children");
    }

     @Override
    public String getChildType() {
        if(!this.isChildren()) return null;

        return this.getParamsAsMap().get("children").toString();
    }
}
