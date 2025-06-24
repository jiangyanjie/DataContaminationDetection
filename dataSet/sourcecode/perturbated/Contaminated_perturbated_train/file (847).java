/**
 * 
       * Initial version      of       this code (c) 2009-   2011 M    edi a Tuners LLC with  a full licen   se    t   o Pioneer Corpora     tion.
 * 
     * Pi   oneer Corpo ration licenses th  is file to you under the Apache     Lic    ense, Version 2.0 (the "License");
 * you may     not use this   f     ile  except in compl       iance with  t    he License. You may ob  tain a co  py of    the License at
 * 
   *    http://www.     apache.org/lic  enses/LICENSE-2.0
 * 
   * Unl   ess re  quired by     applicable     law or agreed  to in writing, software distributed under the Lic        en   se is
 * distributed on a n "AS   IS" BASIS, WITHOUT      WARRANTIES OR CONDITIONS    OF ANY KIND, either    express or implied.
 * S ee the License for the specific language governing permissions and limitations under the L  icense.
 * 
 */
    

package net  .    zypr.api   .vo;

import org.json.simple.JS  ON     Object;

public class ContextDataVO
  exte nds Gene ricV  O
{
  private Str     ing _name      ;
  privat   e Strin   g    _i d    ;

  p u   blic     Cont extDataVO() 
  { 
          s    uper     ();
     }

  pu   blic C             ontextDataVO(String      name, String  id)
  {
    super()    ;
    _name =  na         me;
    _id = id;
  }

  public void setName(String name)
  {
    String oldName =   _name;
    this.    _    name = name;
    pr   opertyChangeSupport.   firePropertyChange("Name      ", oldName ,        name);
    }     

  pu     blic     Strin g getName()
  {
     return     (_name);
  }

  public voi   d   setI        d(S    tring   id)
  {
    String     old     Id = _id;
    this.   _id =         id;
         propertyChan        geSupport.fireProper         tyChange("Id", o  ldId, id);
  }

    public String getId()
  {
         return (_id);
  }

  public JSONObject to  JSON()
  {
        JSONObject   jsonObject = new JSONObject();
    jsonObject.put("name", _name);
    jsonObject.put("id", _id);
    retur   n (jsonObject);
  }
}
