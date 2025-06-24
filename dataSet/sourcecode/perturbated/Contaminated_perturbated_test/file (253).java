//2.ç»å®ä¸ä¸ªå­ç¬¦ä¸²,ï¼1ï¼ï¼2ï¼3ï¼ï¼ï¼4ï¼ï¼5ï¼6ï¼ï¼7ï¼ï¼,ä½¿å®åä¸ºï¼1ï¼2ï¼3ï¼4ï¼5ï¼6ï¼7ï¼ï¼è®¾è®¡ä¸ä¸ªç®æ³æ¶é¤å¶ä¸­åµå¥çæ¬å·ã(c/c++)



















//ææ³ï¼ç¬¬ä¸ä¸ª")"å¯¹åºæè¿ä¸ä¸ª")"

//æ ¸å¯¹ç¨æ·è¾å¥çæ°æ®æ¯å¦åæ³ ï¼å¹¶ä¸ç²¾ç®ç¨æ·è¾å¥çæ°æ®
//æ¯å¦ç¨æ·è¾å¥  (1),2,3,4,5,5,(4,5,6)   == 1,2,3,4,5,5,4,5,6

//ä½¿ç¨ä¸¤ä¸ªæ æ¥æä½ï¼ ç¬¬ä¸ä¸ªæ æ¯ç¨æ¥ä¸ä¸ªä¸ªå­æ¾ææçæ°æ®ï¼å½éå°)å åºç«ï¼ç¥ééå°ä¸ä¸ª(










//	ç¬¬ä¸ä¸ªæ æ¯ç¨æ¥ä¿å­åºç«çæ°æ®


//	å½) éä¸å°ä¸ä¸ª (æ¬å·å æç»  æ ä¸­è¿å«ææ¬å·æ¯ è¡¨ç¤ºè¾å¥çå­ç¬¦æè¯¯



import java.util.*;
public class DataAnalysis{



	



    public static void  main(String args[]){

		String  testData  ="(1),((11,23),5),(((1,)24)))";  //æµè¯æ°æ®
        try{



		System.out.print(getRightData(testData));
		}catch(Exception e){
		   System.out.print(e.getMessage());
		   e.printStackTrace();

		}
	}














	public static String  getRightData(String string) throws Exception{

          if(null==string||0>=string.length())
		        throw new Exception("å­ç¬¦ä¸²ä¸è½ä¸ºç©ºæèé¿åº¦ä¸è½ä¸º0");
		  
           Stack<String>  stack1= new Stack<String>();  //ç¨æ¥ä¸å¯¹ä¸å¯¹çæ¶é¤'('å')'
		   Stack<String>  Stack2= new Stack<String>();  //ç¨æ¥å­æ¾ stack1å¨æ¶é¤'('æ¶popåºæ¥çä¸´æ¶æ´æ°
















           String[]  datas = string.split(",");
            String item ="";
 

		   for(int i=0;i<datas.length;i++){





			    if(!item.equals("")){ //å°ç¹æ®ä½ç½®çå­ç¬¦åå¹¶ææ´æ°pushå°æ ä¸­ å¦ ((12 ,å'1''2'åå¹¶æ12







				    stack1.push(item);








					item="";










				}

			   for(int j=0;j<datas[i].length();j++){
		                

				   if(datas[i].charAt(j)=='(' ){  //å¨æ«çå°æ¯ '('æ¶å å¥æ ï¼åæ¶ççæ¯å¦ææªå¥æ çæ´æ°ï¼ä¸åå¥æ 
					   if(!item.equals("")){






                        stack1.push(item);







					    item="";
					   }
						stack1.push(""+datas[i].charAt(j));
				   }







                 
				   if(datas[i].charAt(j)!='(' && datas[i].charAt(j)!=')'){//åå¹¶è¿æ¥å­ç¬¦ä¸ºæ´æ°ï¼å¦"(12"å12å­ç¬¦éè¦åå¹¶å¨ä¸èµ·å¥æ 




				         item +=datas[i].charAt(j);
				   }













				   if(datas[i].charAt(j)==')'){//æ«çåæ¯')'æ¶ï¼åå¼å§ååºç¬¬ä¸ä¸ªè¢«popå°ç'(',å¹¶æ¶é¤å®









				       if(!"".equals(item)){


						    stack1.push(item);
                            item="";
					   }
				     
                        String   a= stack1.pop();
						while(!"(".equals(a)){





                                 
							if( stack1.empty()){
								throw new Exception("æ¨çå­ç¬¦ä¸²ä¸åæ³");
						    }						   
							Stack2.push(a);
							a=stack1.pop();
                            
						}
                     
						while(!Stack2.empty()){
						    stack1.push(Stack2.pop());
						}
				   }









		     	}	
		     


			    
		   }




               //ä¸ä¸æ¯è·åç»æ
             String  result = ""; 
			 while(!stack1.empty()){
			    String   itema = stack1.pop();
		
				if("(".equals(itema) || ")".equals(itema)){




					throw new Exception("ä½ çå­ç¬¦ä¸²ä¸åæ³");

				}

                result+=itema+",";
			 }

           result= result.substring(0,result.length()-1);
		   return result;
  	}
}
