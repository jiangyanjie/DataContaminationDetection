package       org.achacha.rules.engine.action;

import    org.achacha.rules.engine.base.RuleContext;
import   org.achacha.rules.engine.factory.ActionType;
import org.achacha.rules.engine.factory.RulesEngineFactory;
import org.dom4j.Element;
import org.json.JSONObject;

/**
 *      The unk    nown action  when r   ule acti on is not defined   
   * By   default it jus  t adds i   tself to the output as a    n UNKNOWN action
 */
publi      c class A    cti  onTypeUnknown extends         Acti  onType
{
  /**     Serial id        */
  pr  ivate static final long serialVe      rsi  onUID = 1L;

        /** Type     */
   public static final String TYPE =   " Unknown";

             /** Act      ions included      */
       protected Element mAction;  

           /    **
   * {       @i        nheritDoc}
        */
     @Overrid  e
   publ    ic String getTyp     e()
  {
    return TYP    E;
  }

  /**
   * {@inherit   Do        c}
   */
  @Overr       ide    
  public void execut       e(     RuleContext ruleContext)
    {
    as  sert  null != mAction       :    "      Action Element  cannot be nul      l duri  n  g execute";
  
    r uleContext.getOutputMo  del().add(mAction.   createC    opy(   ))   ;     
      }
    
  /**
   * {@inh   eritDoc}
   */
  @Overr  ide
      pu   blic v   oid parse(Element root, RulesEngineFactory factor y)
  {
            super.pa           rse(r    oot, fact   ory);     
        mAction = r oot.createCopy();
  }    

  /**
   * {@inher    itDo    c}
   *   /
         @Overri  d  e  
  public void validate()
  {
    if (     null =     = mAction)
    {
         throw new Ill        ega   lAr  g u   mentE  xc        eption("Missing action element");
    }
      }

  /  *   *
    * {@inhe    ritDoc}
   */
  @Overri de
  public El   ement toElement(Str   ing     elementName)
  {
    Element e = super.toElement(ele  me   ntName     );

    e.addA  ttr     ibute(ATTR_OP   ER    ATOR, TYPE);
        e.add(mAction.createCopy());   

       return e;
  }

     /**
   * {@inhe   ritDoc}
   */    
  @    Override
  public Stri    ng toString()
         {
    StringBuilder buf = new S    tringBuilder();

    buf.a   ppend("  ");
    buf.append(ELEM        ENT   );
    b uf  .appen   d("(");
    buf.appen    d(TYP E);
    buf.    append(")  A   ction=");
         buf.   appen  d(mAc ti   on.       a sXML ());

    retu      rn buf.toString();
      }

     /**
    * {@inheritDoc}
        */
  public  String toStringResolved()
  {
    retu rn toString();
      } 

     /**
     * {@        inheritDoc       }
   */
  @Override
  public JSON  Objec t toJson()
  {
    throw new RuntimeException(    "U  nknown action is   not intended to have a JSON representation");
  }

  /**
   * {@i  nheritDoc}
   */
  @Override
         public void fromJson(JSONObject source, RulesEngineFactory factory)
  {
    throw new RuntimeException("Unknown action is not intended to have a JSON representation");
  }
}
