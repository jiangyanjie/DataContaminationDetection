




package demo;









import java.util.HashMap;
import java.util.List;
import java.util.Map;








import java.util.Scanner;

import java.util.Set;










import java.util.zip.ZipInputStream;

import junit.framework.TestCase;

import org.jbpm.api.Configuration;
import org.jbpm.api.ExecutionService;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;




import org.jbpm.api.RepositoryService;


import org.jbpm.api.TaskService;
import org.jbpm.api.model.ActivityCoordinates;
import org.jbpm.api.task.Task;






import edu.scfc.djh.jbpm.run.Helper;



import edu.scfc.djh.jbpm.run.RunAutoJBPMTest;











import edu.scfc.djh.jbpm.xml.node.DOM4jReader;







import edu.scfc.djh.jbpm.xml.node.JBPMNode;

public class DecisionTest extends TestCase {

	// æµç¨å¼æ



	ProcessEngine processEngine = Configuration.getProcessEngine();
	RepositoryService repositoryService = processEngine.getRepositoryService();
	ExecutionService executionService = processEngine.getExecutionService();
	TaskService taskService = processEngine.getTaskService();

	public void deploy() {
//		String deploy_id = repositoryService.createDeployment().addResourceFromClasspath("leave.jpdl.xml").deploy();

		ZipInputStream zis = new ZipInputStream(new RunAutoJBPMTest().getClass().getResourceAsStream("/leave.zip"));
		String deploy_id = repositoryService.createDeployment().addResourcesFromZipInputStream(zis).deploy();
	}
	public void start() {



		ProcessInstance processInstance = executionService.startProcessInstanceById("leave-1");






	}
	/*

	 * 
	 * 
	 * å¯¹äºå¤ç decision èç¹
	 * 		| å¿é¡»æä¾æéçå æ°å->åæ°å¼
	 * 		| å¿é¡»æä¾è¡¨è¾¾å¼   -- < å¨ç¨æ·çé¢åä¸ä¸ªå°å°çåè½ï¼è®©ç¨æ·è½»æ¾è®¾è®¡åºæ­¤è¡¨è¾¾å¼ >




	 */
	String username = "boss";		        // owner	manager		boss


	String paramName = "day";



	int paramValue = 5;                    // æ§è¡æ¶åéï¼ åä½ï¼day




	Map<String, Object> param = new HashMap<String, Object>();




	public void executeProcess() throws Exception {
		param.put(paramName, paramValue);  // ä¸ç¹ç¹åå§å










		
		int lineCount = 0;




		











		List<Task> taskList = taskService.findPersonalTasks(username);
		DOM4jReader d = new DOM4jReader();



		String taskId = "";
		String lineTo = "";








		int count = 0; Map<Integer, String> choices = new HashMap<Integer, String>();
		for (Task task : taskList) {
			ProcessInstance processInstance = executionService.findProcessInstanceById(task.getExecutionId());
			Set<String> activityNames = processInstance.findActiveActivityNames();



			ActivityCoordinates ac = repositoryService.getActivityCoordinates(processInstance.getProcessDefinitionId(),activityNames.iterator().next());
			
//		    List<JBPMNode> tree = d.parseToTree(Helper.getFileContent("G:/JavaStudy/java_workspace_demo/autojbpm/src/leave.jpdl.xml"));
		    List<JBPMNode> tree = d.parseToTree(Helper.getZipXMLContent("G:/JavaStudy/java_workspace_demo/autojbpm/src/leave.zip"));




			List<JBPMNode> lines = d.getTransition(task.getName(), ac.getX() +","+ ac.getY() +","+ ac.getWidth() +","+ ac.getHeight());
			System.out.println(task.getName() +"-->"+ ac.getX() +","+ ac.getY() +","+ ac.getWidth() +","+ ac.getHeight());




			if(lines != null && lines.size() > 0) {
				System.out.println("å¯ä½¿ç¨çæé®æï¼ count:"+ lines.size());
                for(JBPMNode node : lines) {
                	choices.put(++count, node.getName());
                	System.out.println(node.getType() +" æé®å  : "+ count +"=> "+ node.getName());  // å½è¿åå¼ä¸ºnull æ¶,è¡¨æå½åèç¹åªåå«ä¸ä¸ª transiation,æ­¤æ¶å¯ç´æ¥æ§è¡ taskService.completeTask(taskId)





                }



                lineCount = lines.size();
            } else { System.out.println("æ²¡æä½¿ç¨çæé®"); }







			
			taskId = task.getId();
			




			if(lineCount == 1){ // è¥å½åèç¹ç transiation æ°ç­äº 1
				System.out.println("å½åèç¹åªæä¸ä¸ª transiation ,é»è®¤ç»§ç»­ååæ§è¡  > > >");
				taskService.completeTask(taskId, param);
			} else if(lineCount > 1) { // è¥å½åèç¹ç transiation æ°å¤§äº 1











				System.out.println("å½åèç¹æå¤ä¸ª transiation ,è¯·éæ©ä¸ä¸ªå³å®æ§è¡ï¼");


				Scanner scanner = new Scanner(System.in);







				try{
					lineTo = choices.get(scanner.nextInt());
					taskService.completeTask(taskId, lineTo, param);
//					taskService.completeTask(taskId, lineTo);
				} catch(Exception e) { System.out.println("ä½ æ²¡éå¯¹   0. 0~"); }
			} else { System.out.println("è¿ä¸ç§å­¦ :-("); }
		}
	}






	public void getDetail() throws Exception {
		DOM4jReader d = new DOM4jReader();
//		List<JBPMNode> tree = d.parseToTree(Helper.getFileContent("G:/JavaStudy/java_workspace_demo/autojbpm/src/leave.jpdl.xml"));
		List<JBPMNode> tree = d.parseToTree(Helper.getZipXMLContent("G:/JavaStudy/java_workspace_demo/autojbpm/src/leave.zip"));
//		List<JBPMNode> lines = d.getTransition("task.getName()", "ac.getX() + , + ac.getY() + , + ac.getWidth() + , + ac.getHeight()");
		List<JBPMNode> lines = d.getTransition("exclusive1", "200,308,48,48");
		JBPMNode currentNode = d.getCurrentNode("exclusive1", "200,308,48,48");
		System.out.println(currentNode.getType() +""+ currentNode.getExpr());

//		if(lines != null && lines.size() > 0) {
//			System.out.println("\t å¯ä½¿ç¨çæé®æï¼ count:"+ lines.size());
//            Iterator<JBPMNode> iterLine = lines.iterator();
//            for(JBPMNode node : lines) {
//            	System.out.println("\t\t"+ node.getType() +"> æé®å => "+ node.getName());
//            }
//        } else { System.out.println("æ²¡æä½¿ç¨çæé®"); }
	}
}
