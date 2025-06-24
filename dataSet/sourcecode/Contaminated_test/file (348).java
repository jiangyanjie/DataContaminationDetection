/**
 * 
 */
package DataAnalysis;

import DataObject.EvalObject;
import DataObject.Question;

/**
 *
 * Data Map Plugin implementation.
 * Choropleth maps are one of the most frequently used maps in infographic style
 * visualizations. As the name suggests, color is the important part to these 
 * maps. A color scale is assigned to categorical or numerical data, and the 
 * value for each region is used to color the region. These maps usually use 
 * political boundaries as the regions.
 * 
 * @author Nacer Abreu
 */
public class DataMapQuestion extends QuestionBase {

	private EvalObject eval;
	
	/**
	 * 
	 * @param eval
	 */
	public DataMapQuestion(EvalObject eval) {
		this.eval = eval;
		
		append("Is there a concentration in a certain location? Why?", 60, "s"); 
		append("Are there any inconclusive data areas?", 60, "s"); 
		append("Does the color scale contain patterns or textures to differentiate similar shades?", 60, "s"); 
		append("Can the under-represented areas be easily identified?", 60, "s");
		append("Can the over-represented areas be easily identified?", 60, "s");
		append("Are the boundaries clearly delineated?", 60, "s");
		append("Is context provided?", 60, "s");
		append("Are the colors used, readable for colorblind users?", 60, "s");
		append("Can you draw any conclusions from the data map?", 60, "s");
		append("Are the colors used representatives of the data in any way?", 60, "s");
	}

	/**
	 * This method returns a question and answer about the 
	 * maximum value on the current graph.
     * 
     * @return a question about a maximum value on the graph   
     */
	@Override
	public Question finMax() {
		 int time = 30;
	        int score = 0;
	        String type = "s";
	        
	        String question = "What's the percentage of most populated state/area?";
	        Question obj = new Question(question, time, type, score);
	        
	        // Determine the maximum value in the data set
	        double max = eval.getDatapoints().get(0).getValueY();
	        
	        //iterate through all of the datapoints find the min
	        for(int i =1; i < eval.getDatapoints().size(); i++)
	        {
	            if( eval.getDatapoints().get(i).getPercent() > max)
	            {
	                max = eval.getDatapoints().get(i).getPercent();
	            }            
	        } 
	        obj.setAnswer(Double.toString(max));
	        return obj;
	}

	/**
	 * This method returns a question and answer about the 
	 * minimum value on the current graph.
     * 
     * @return a question about a minimum value on the graph   
     */
	@Override
	public Question findMin() {
		int time = 30;
        int score = 0;
        String type = "s";
        String question = "What's the percentage of the least populated state/area?";
        Question obj = new Question(question, time, type, score);
        
        // Determine the minimum value in the data set
        double min = eval.getDatapoints().get(0).getValueY();

        //iterate through all of the datapoints find the min
        for (int i = 1; i < eval.getDatapoints().size(); i++) {

            if (eval.getDatapoints().get(i).getValueY() < min) {
                min = eval.getDatapoints().get(i).getValueY();
            }
        }
        obj.setAnswer(Double.toString(min));
        return obj;
	}

	/**
     * This method returns a question and answer about the 
	 * any outlier values on the current graph.
     * 
     * @return a question about an outlier 
     */
	@Override
	public Question findOutlier() {
		// This kind of plugin does not contain outliers
        return null;
	}

	/**
     * This method returns a question and answer about the 
	 * any recommendations based on the current graph.
     * 
     * @return a question asking a user to recommend something  
     */
	@Override
	public Question findRecomendation() {
		 // This kind of plugin does not contain any recommendation questions
        return null;
	}

	/**
	 * This method returns a question about the 
	 * exploring the current graph.
     * 
     * @return a question about exploring the graph 
     */
	@Override
	public Question exploration() {
		// This kind of plugin does not contain any exploration questions
		return null;
	}

	@Override
	String modifyQuestion(String s, int num, String l) {

		s = s.substring(0, s.indexOf("blank"));

		return s;
	}
	
    /**
     * This method will append to the list of "canned" questions, the ones
     * particular to this plugin. This method makes the following assumption(s):
     * 1. The question is always rated as 0 (zero)
     */
    private void append(String question, int time, String type) {
        int score = 0;
        Question obj = new Question(question, time, type, score);
        eval.addQuestion(obj);

    }   


}
