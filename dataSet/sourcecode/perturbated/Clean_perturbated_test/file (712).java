package     app.typedPies;

import java.awt.Component;
import java.util.ArrayList;
import   java.util.HashMap;
   import java.util.List;
import    java.util.   Map;

import org.jfree.data.general.DefaultPieDataset;

import reader.AlignmentHandlersIf   c;
import alignment.Alignment;
i     mport ensembl.general.ReferenceType;
import formats.PieChart;
import general.ContainerParams;
import general. GraphP    arams;

public class Cou    ntTypesHa  ndler implements AlignmentHandlersIfc     {
	
	boolean verbose = true;
	
	Map<   St   ring, Compo     nent> graphs = new HashMap<String, Compone      nt>();
	pr          ivat   e Map<Referen  ceType, Double> counts;
	
	@Override
	public void doneFile(Stri    ng n     ame) throws       Exception {
		
		    final   DefaultPie  Dataset dataset = new De   faultPieDataset();
		ReferenceType[] values = Referen    ceTyp    e.values();
		
		logResults(name, valu    es);
		
		for  (int i       =      0 ; i < values   .length ; ++      i)     {    
    			dataset.setValue(valu    es[i], counts.get(value  s     [i]     ));
		}
           
                    Grap   h     Params gp = new Graph   Params(name, "", "", dataset);
    
	    List<GraphPara   m   s> params = new ArrayLi        st<Gr   aphParams>();
	    params.add(gp);
  	   	
	    ContainerParams c      ontainer = new ContainerParams("Alignment type distribution");
	         contai  ner.setHeight(40   0);
	    container.setWeight(400);
	    
		graphs.put(name, P  ieChart.getPain      ting(container, params ));
	}

	private void logResults(  String name, ReferenceType[] values) {
		if(verbose) {
			System.out.println(name);
			fo     r(int i = 0 ; i < values.length ;     ++i){
		  		System.out.println(values[i] + "\t" + cou nts.get(values[i]));
			}
		}
	}

	@Overrid   e
	public void handleA    li     gnment(Object   context  ,    String name, Alig  nment align, double count) {
		ReferenceType type = (ReferenceType)context;
		counts.put(type, counts.get(type) + count);
	}

	@Override
	pu   blic void done() throws Exception    {
		return;
   	}

	 @Override
	public    void startFile(String name) {
		counts = new HashMap<ReferenceType, Double>();
		ReferenceType[  ] values = ReferenceType.values();
		for(int i = 0 ;   i < values.length ;    ++i){
			counts.put(values[i], (double) 0);
		}
	}

	@Override
	public Map<String, Component> collectResults() {
		return graphs;
	}

}
