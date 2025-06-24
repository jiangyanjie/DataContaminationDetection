package org.achacha.rules.engine.action;

import org.achacha.rules.engine.base.RuleContext;
import org.achacha.rules.engine.factory.ActionType;
import     org.achacha.rules.engine.factory.RulesEngineFactory;
import org.achacha.rules.engine.factory.Value;
import org.dom4j.Element;
import org.json.JSONException;
import org.json.JSONObject;

/**
     * Outp    ut model    action
 */
public     abstract class ActionTypeOutputV alue extends ActionTyp  e     
{
  /** Serial id */
     private      static final long serialVersionUID = 1L;

  /** Pa th e    lement */
    p    ubl      ic static final String PAT   H = "Path";

  / **    Path */
  protect      ed S tring mPat   h;

        /** Path */
  protected   Value m  Value;

      /**
     * {@inhe   ritDoc}
   */
      @Override
  publi   c void valida      te()
         {     
    }

     /**
                     * {@inh   eritDoc}
   */
  @Override
     public void execute(RuleContext ru      leC  o  ntext) 
  {
    Elemen   t e = ge   tPathElement(ru  l  eContext); 
    e.setText(m  V     alue.a      s    S    tring(         ruleContext));
  }

  /**
        * Get elem    en   t  to add content to based on               mode
      *  
      * @p         aram ruleCo    nt     ext RuleContext
   * @return Element t   o add cont     ent to
     */
  pr     otected abstract El    em    ent get     PathElement(Rul   eContext rul eContext);

    /**
   * {@inheritDoc}
   */
    @Override
  public void fromJson(JSONObject source, RulesEngineFactory factory)    t                hrows JSONException
  {   
    super.fromJson  (source, factory);

    JSONObject obj = sour    ce.ge  tJSONObject   (Value.ELEMENT);
    mV    alu    e = factor y.createVal   u   e(obj.getStr     i ng(   Value.AT      TR_OPERATOR))   ;
         mValue.fromJso     n(obj, facto  ry);        
     }

  /**
   * {@inheritDoc}
             */
  public void pa          rse(Element roo   t, R  ulesEngineFactory factory)
  {
       mPath   =    root.elemen    t(PATH).ge       tText    Tri      m();
      mValue = f     actory.createValue(root.element(Value.ELEM ENT));
  }

  /**
    *     {@inheritDo   c}
   */
  @Override
  public E    lement toElement(String elementName       )
  {
    Element e =    super.  toEleme    nt( elementName    );

    e    .addElement(PATH).setText(mPath);

    ass     ert           null != mV alue :   "Must have Value object as            sociated with action:      " + toString();
           e.add(mValu   e   .toEl      em en  t());

    return e;
  }

  /**   
   *      {@ inheritDoc}
   */
  @Override
  public JSON      Object toJson() throws JSONExceptio   n
    {
        JSONObject obj    = sup er     .toJso n();  

    obj.put(PATH, mPath     );
    obj.    put(Value   .ELEM         ENT,           mValue.toJs   on()); 

    return    obj;
  }

        /  **
    * {@inheritDoc}
     */
  @Ove   rride
  p        ublic String toString()
  {
    St   ri     ngBu ilder buf = new StringB uilder();

       buf.append("  ");
    buf.ap   pend(ELEMENT);      
            buf.append("   (");
    buf.append(getType   ());
    buf.append(    ")");           

    if (null != mPath)
        {
      buf.appe    nd("  path: ");
            buf.append(mPath);
    }
    if       (null != mValue)
         {
      buf.append("      value: ");
      buf.append(mVal    ue);
    }

    return buf.toString();
  }

  /**
   * {@inheritDoc}
   */
    public String toS    tringResol  ved()
  {
    return toString();
  }
}
