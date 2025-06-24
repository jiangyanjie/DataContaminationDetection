/* Copyright (C) 1998-2004  Caltech SSEL/UCLA CASSEL
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 * CASSEL: http://www.cassel.ucla.edu
 * SSEL: http://www.ssel.caltech.edu
 * email: multistage@ssel.caltech.edu
 */

/*
 * DegreeQuestion.java
 *
 * Created on August 11, 2003, 6:56 PM
 */

package edu.nyu.cess.multi.common.data;


/**
 * A series of questions that are all answered using the same criteria, which typically 
 * are some 'degree' of feeling. For example, use this Question object to create a question
 * like that below:
 * <p>
 * Enter your answers:
 * <br> 1 - a little        2 - a lot      3 - not at all
 * <p>
 * Do you feel accomplished in life? 
 * <br>Do you like eating ice cream?
 * <br>Do you enjoy fast food? 
 * <p>
 * There are no correct answers supported with the DegreeQuestion, so correctness is never
 * checked with this Question
 * <p>
 * To create the sample question above, set options = {a little, a lot, not at all} and set 
 * questions an array with the three strings above. Set instructions to 'Enter your answers:'
 * <p>
 * All strings can be written in HTML but for the options and instructions strings do NOT include
 * the &lthtml&gt and &lt/html&gt tags. For the options strings do not include any table formatting tags
 * <p>
 * Answers are output in the format: x - y - z - ... where X is the option chosen for the first question,
 * y for the second, and so on. If no answer is provided for a question (time expired) then '0'
 * will be output. For example, if the client answers questions one and two but not three, the output
 * could look like: 4 - 3 - 0
 * @author  raj
 */
public class DegreeQuestion extends Question {
    
    /** Creates a new instance of DegreeQuestion
     * @param instructions instructions to the user about the question
     * @param questions array of Strings representing the text of each successive
     * question
     * @param options String array representing the answer options of the questions 
     * ordered from 1 to n
     * @param outputName header to be used in the output file provided by the
     * outputService
     */
    public DegreeQuestion(String instructions, String[] questions, String[] options, String outputName) {
        this.options = questions;
        this.choices = options;
        this.question = generateQuestion(instructions, options);
        this.isSurvey = true;
        this.outputName = outputName;
        this.style = DEGREE_QUESTION;
    }
    
    /** Creates a DegreeQuestion without an output name
     * @param instructions instructions to user about the question
     * @param questions array of Strings representing the test of each successive
     * question
     * @param options String array representing the answer options of the questions
     * ordered from 1 to n
     */
    public DegreeQuestion(String instructions, String[] questions, String[] options) {
        this(instructions, questions, options, null);
    }
    
    public String[] getQuestions() {
        return questions;
    }
    
    public void setQuestions(String[] questions) {
        this.questions = questions;
    }
    
    private String generateQuestion(String instructions, String[] options) {
        StringBuffer sb = new StringBuffer("<html>");
        sb.append(instructions);
        sb.append("<br><table border=1 cellspacing=5><tr align=center>");
        for (int i=1; i<=options.length; i++)
            sb.append("<td>"+i+"</td>");
        sb.append("</tr><tr align=center>");
        for (int i=0; i<options.length; i++)
            sb.append("<td>"+options[i]+"</td>");
        sb.append("</table></html>");
        return sb.toString();
    }
    
    public int getMaxOption() {
        return choices.length;
    }
    
    public int getMinOption() {
        return 1;
    }
    
    private String[] questions;
    private String[] choices;
}
