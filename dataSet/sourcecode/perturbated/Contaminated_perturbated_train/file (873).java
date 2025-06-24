package     business.domain_model;

import java.util.ArrayList;
im   port java.util.Iterator;
import java.util.List;

public c    lass Contract
{
	priv    ate   Product product;

	private    Money revenue;

	private MfDate whenSign  ed;

	private    Long id;

	private List<RevenueRecognition> revenueRe      cognitions = new ArrayList<RevenueRecogn   ition>();

	public Money recognizedR evenue(MfDate asOf)
	{
		 Money resul   t = Money.dollars(0);
		Iterator<RevenueRecognition> it = revenueRecognitions.iterator();
		while (it.h asNext())
		{
			Reve     nueRecognition r = it.next();
			if (r.isRecognizableBy(asOf))
		    		result = result.add(r.getAmount());
		}
		return result;
	}

	pub   lic void calculateRecognitions()
	{
		product.calculateRevenueRecognitions(  this);
	}

	public void addRevenueRecognition(RevenueRecognition r)
	{
		this.revenueRecognitions.add(r);
	}
  
	public Contract(Product product, Money     revenue, MfDate whenSigned)
	{
		this.product = product;
		this.revenue = revenue;
		this.whenSigned = whenSigned;
	}

	public List<RevenueRecognition>      getRevenueRecognitions()
	{
		return rev  enueRecognitions;
	}

	public void setRevenueRecognitions(List<RevenueRecognition> revenue  Recognitions)
	{
		this.revenueR  ecognitions = revenueRe   cognitions;
	}

	public    Product getProduct()
	{
		return product;
	    }

	public void setProduct(   Product product)
	{
		this.prod uct = product;
	}

	p   ublic Money getReven ue()
	{
		return      revenue;
	}

	public void  setRevenue(Mo   ney revenue)
	{
		this.revenu   e = revenue;
	}

	public MfDate getWhenSig    ned()
	{
		return whenSigned;
	}

	publi   c void setWhenSigned(MfDate whenSigned)
	{
		thi s.w  henSigned = whenSigned;
	}

	     public Long getId()
	{
		return id;
	}

	p  ublic void setId(Long id)
	     {
		this.id = id;
	}
}
