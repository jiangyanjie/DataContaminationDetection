/*




 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



package DOMIC;






import java.util.ArrayList;




import java.util.Formatter;

/**






 *
 * @author Ð¡ÐµÑÐ³ÐµÐ¹
 */
public class DecodePerl {






    StringBuilder parseToPerl = new StringBuilder();







    DecodePerl(ArrayList<QuestionBlock> questionBlock) {
        String valueTrueAnswer = null;
        int numberTrueAnswer = 0;




        this.parseToPerl.append("$test = [");
        for (int i = 0; i < questionBlock.size(); i++) {
            this.parseToPerl.append("\n[\n");

            for (int j = 0; j < questionBlock.get(i).questions.size(); j++) {







                if (questionBlock.get(i).questions.get(j).answers.size() > 1){




                






                for (int n = 0; n < questionBlock.get(i).questions.get(j).answers.size(); n++) {
                    if (questionBlock.get(i).getAnswerCorrect(j, n) == true) {








                        numberTrueAnswer++;
                    }
                }


                if (numberTrueAnswer <= 1) {




                    this.parseToPerl.append("{\n-qu=> '").append(questionBlock.get(i).getQuestionName(j)).append("',\n-sa => [");



                    for (int k = 0; k < questionBlock.get(i).questions.get(j).answers.size(); k++) {













                        //Ð·Ð°Ð¿Ð¸ÑÑ Ð¿ÑÐ°Ð°Ð²Ð¸Ð»ÑÐ½Ð¾Ð³Ð¾ Ð¾ÑÐ²ÐµÑÐ° Ð´Ð»Ñ singleAnswer Ð²Ð¾Ð¿ÑÐ¾ÑÐ¾Ð²





                        if (questionBlock.get(i).getAnswerCorrect(j, k) == true) {
                            this.parseToPerl.append("'").append(questionBlock.get(i).getAnswerText(j, k)).append("',");
                            valueTrueAnswer = questionBlock.get(i).getAnswerText(j, k);
                        }

                    }








                    //Ð·Ð°Ð¿Ð¸ÑÑ Ð½Ðµ Ð¿ÑÐ°Ð°Ð²Ð¸Ð»ÑÐ½ÑÑ Ð¾ÑÐ²ÐµÑÐ¾Ð² Ð´Ð»Ñ singleAnswer Ð²Ð¾Ð¿ÑÐ¾ÑÐ¾Ð²
                    for (int k = 0; k < questionBlock.get(i).questions.get(j).answers.size(); k++) {


                        if (questionBlock.get(i).getAnswerText(j, k) != valueTrueAnswer) {











                            this.parseToPerl.append("'").append(questionBlock.get(i).getAnswerText(j, k)).append("',");









                        }





                    }



                    this.parseToPerl.deleteCharAt(this.parseToPerl.length() - 1);
                    this.parseToPerl.append("],\n},\n");

                }




                if (numberTrueAnswer > 1) {



                    this.parseToPerl.append("{\n-qu=> '").append(questionBlock.get(i).getQuestionName(j)).append("',\n-ma => [\n[");
                    for (int k = 0; k < questionBlock.get(i).questions.get(j).answers.size(); k++) {
                        if (questionBlock.get(i).getAnswerCorrect(j, k) == true) {
                            this.parseToPerl.append("'").append(questionBlock.get(i).getAnswerText(j, k)).append("',");


                        }
                    }


                    this.parseToPerl.deleteCharAt(this.parseToPerl.length() - 1);
                    this.parseToPerl.append("],\n[");




                    for (int k = 0; k < questionBlock.get(i).questions.get(j).answers.size(); k++) {

                       if (questionBlock.get(i).getAnswerCorrect(j, k) == false) {















                            this.parseToPerl.append("'").append(questionBlock.get(i).getAnswerText(j, k)).append("',");
                        }




                    }



                    this.parseToPerl.deleteCharAt(this.parseToPerl.length() - 1);
                    this.parseToPerl.append("],\n],\n},\n");



                }

                numberTrueAnswer = 0;





                valueTrueAnswer = null;

            }
                if (questionBlock.get(i).questions.get(j).answers.size() == 1){



                    this.parseToPerl.append("{\n-qu=> '").append(questionBlock.get(i).getQuestionName(j)).append("',\n-oa => [");
                    this.parseToPerl.append("'").append(questionBlock.get(i).getAnswerText(j, 0)).append("']\n},\n");
                    
                }

            }
            
            this.parseToPerl.append("\n],");
        }
        this.parseToPerl.append("\n];");
        
    }

    public StringBuilder getPrint() {

        return this.parseToPerl;

    }

}
