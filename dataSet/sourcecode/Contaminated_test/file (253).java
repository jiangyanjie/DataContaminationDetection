//2.给定一个字符串,（1，（2，3），（4，（5，6），7））,使它变为（1，2，3，4，5，6，7），设计一个算法消除其中嵌套的括号。(c/c++)


//思想，第一个")"对应最近一个")"

//核对用户输入的数据是否合法 ，并且精简用户输入的数据
//比如用户输入  (1),2,3,4,5,5,(4,5,6)   == 1,2,3,4,5,5,4,5,6

//使用两个栈来操作， 第一个栈是用来一个个存放所有的数据，当遇到)则 出站，知道遇到一个(

//	第一个栈是用来保存出站的数据
//	当) 遇不到一个 (括号和 最终  栈中还含有括号是 表示输入的字符有误



import java.util.*;
public class DataAnalysis{
	

    public static void  main(String args[]){

		String  testData  ="(1),((11,23),5),(((1,)24)))";  //测试数据
        try{
		System.out.print(getRightData(testData));
		}catch(Exception e){
		   System.out.print(e.getMessage());
		   e.printStackTrace();
		}
	}

	public static String  getRightData(String string) throws Exception{

          if(null==string||0>=string.length())
		        throw new Exception("字符串不能为空或者长度不能为0");
		  
           Stack<String>  stack1= new Stack<String>();  //用来一对一对的消除'('和')'
		   Stack<String>  Stack2= new Stack<String>();  //用来存放 stack1在消除'('时pop出来的临时整数

           String[]  datas = string.split(",");
            String item ="";
 
		   for(int i=0;i<datas.length;i++){

			    if(!item.equals("")){ //将特殊位置的字符合并成整数push到栈中 如 ((12 ,则'1''2'合并成12
				    stack1.push(item);
					item="";
				}
			   for(int j=0;j<datas[i].length();j++){
		                

				   if(datas[i].charAt(j)=='(' ){  //在扫瞄到是 '('时则 入栈，同时看看是否有未入栈的整数，一同入栈
					   if(!item.equals("")){
                        stack1.push(item);
					    item="";
					   }
						stack1.push(""+datas[i].charAt(j));
				   }
                 
				   if(datas[i].charAt(j)!='(' && datas[i].charAt(j)!=')'){//合并连接字符为整数，如"(12"则12字符需要合并在一起入栈
				         item +=datas[i].charAt(j);
				   }

				   if(datas[i].charAt(j)==')'){//扫瞄倒是')'时，则开始取出第一个被pop到的'(',并消除它
				       if(!"".equals(item)){
						    stack1.push(item);
                            item="";
					   }
				     
                        String   a= stack1.pop();
						while(!"(".equals(a)){
                                 
							if( stack1.empty()){
								throw new Exception("您的字符串不合法");
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


               //一下是获取结果
             String  result = ""; 
			 while(!stack1.empty()){
			    String   itema = stack1.pop();
		
				if("(".equals(itema) || ")".equals(itema)){
					throw new Exception("你的字符串不合法");

				}

                result+=itema+",";
			 }

           result= result.substring(0,result.length()-1);
		   return result;
  	}
}
