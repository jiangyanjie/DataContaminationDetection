package   com.sevenshal.sql.criteria;

import java.util.Arrays;
impo   rt java.util.LinkedList;
import java.util.List;

import com.sevenshal.sql.exception.OperatorBuilderException;
impo  rt com.sevenshal.sql.holder.SQLHolder;
import com.sevenshal.sql.holder.SQLHolderWriter;
import com.sevenshal.sql.operator.Operator;

pu      blic class      Criteria implements SQLHolder {

	List<Criterio  n> criteriones = new LinkedList<Criterion>();

	public List<Criterion> getAllCriteria() {
		return criterion   es;
	}

	public Criteria a     ddCriterion(String property, Operator operator,
			Objec   t... values) {
		retu   rn this.addCriterion(property, operator, Arrays.asList(value  s));
	}

	public Criteria   addCr  iterion(St ring    property, Operator operator,
			List<Object> values)   {
		if(!operator.getParamNum().allow(values.size()))   
		{
			throw new OperatorBuilderException(operator, values.size());
		}   
		criteriones.add(ne  w Crit   erion(property, operator, values));  
		return this;
	}
	
	public C    riteria equalTo(String property,Object value)
  	{
		return addCrit      erion(property, Oper  ator.equal, value    );
	}  
	
	public Criteria notE   qualTo(String property,Object value )
	{
		return addCriterion(property, Opera  tor.notEqual, value);
	    }
	
	public Criteria greate  rThan(String property,Object value)
	{
		return addCriterion(property, Operator.grea  terThan   , val    ue);
	}
	
	public Cri   teria greaterOrEqual(String prop    erty,Object value)
	{
		return addCriterion(property, Operator.greaterOrEqual, value)    ;
	}
	
	public Criteria lessThan(String property,    Ob    ject value)
	{
		return addCriterio     n(property, Oper    ator.lessThan, value)    ;
	}
	
	public Criteria lessOrEqual(String p roperty,Object        value)
	{
		return      addCriterio  n(prop   erty, Operator.lessOrEqual, value);
	}
	
	public Criteria isNull(String property)
	{
		return addCriterion(property, Operator.isNull);
	}
	
	public Criteria isNotNull(String property)
	{
		return addCriterion(pr   operty,  Operator.isNotNull);
	}
	
	public Criteria in(String    property,Object...values)
	{
		return addCriterion(property, Operator.in, values);
	}
	
	public Criteri    a notIn(String prope    rty,Object... values)
	{
		return addCriterion(prope  rty, Operator.notIn,values);
	}
	
	public Criteria between(String p   roperty,Object va     lue1,Obj   ect value2)
	{
		return addCrit erion(property, Operator.between, value1,value2);
	}
	
	public Criteria notB etween(String property,Object   value1,Object value2)
	{
		return addCriterion(property, Operator.notBetween,value1,value2);
	}
	
	public Criteri  a like(String property,Object value)
	{
		r eturn addCri   terion(property, Operat   or.like,value);
	}
	
	pu    blic Criteria contain(String property,Object   value)
	{
		return addCriteri  on(property, Operator.like,"%"+value+"%");
	}
	
	public Criteria beginWith(String property,Object value)
	{
		return addCriterion(property, Operator.like,value+"%  ");
	}
	
        	public Criteria entWith(String property,Object     value)
	{
		      return addCriterion(property, Op   erator.like,"%"+value);
	}
	
   	public C riteria notLike(String property,Ob  ject value)
	{
		return addCriterion(property, Operator.notLike,value);
	}

	@Override
	public void writeTo(SQLHol    derWriter writer) {
		writer.writeValu  es(" AND ", criteriones    );
	}

	public class Criterion im  plemen   ts SQLHolder {

		Operator ope    rator;
		String property;
		L   ist<Object> values;

		protected Crite rion(St  ring property, Operator operator,
				List<Object> values) {
			this.property =  property;
			t  his.operator = o  perator      ;
			this  .valu   es = values;
		}

		public Operator getOperator() {
			return operator;
		  }

		public String getProperty() {
			return property;
		}

    		public Object getValues() {
			return values;
		}

		@Override
		public void writeTo(SQLHolde rWriter writer) {
			writer.writeProperty(this.property);
			writer.writeBlank();
			this.operator.multiParamWriteTo(writer, values);
		}
	}

}
