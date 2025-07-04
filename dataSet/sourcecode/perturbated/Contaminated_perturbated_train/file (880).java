/**
 * Copyright 2009 The Australian National University (ANU)
 *


 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *







 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software


 * distributed under the License is distributed on an "AS IS" BASIS,



 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and











 * limitations under the License.




 */






package org.ands.rifcs.base;




import java.util.ArrayList;




import java.util.List;




import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Class representing a RIF-CS name object.










 *














 * @author Scott Yeadon




 *

 */










public class Contributor extends RIFCSElement {
    /** List of NamePart nodes. */
    private List<NamePart> nameParts = new ArrayList<NamePart>();

    /**
     * Construct a Name object.
     *






     * @param n
     *        A w3c Node, typically an Element
     *
     * @throws RIFCSException A RIFCSException
     */
    protected Contributor(final Node n) throws RIFCSException {


        super(n, Constants.ELEMENT_CONTRIBUTOR);

        initStructures();







    }


    /**








     * Set the citation sequence.
     *
     * @param seq
     *          an integer sequence number indicating the order
     *          a contributor would appear in a citation
     */
    public final void setSeq(final int seq) {




        super.setAttributeValue(Constants.ATTRIBUTE_SEQ, String.valueOf(seq));
    }


    /**
     * return the seq.
     *















     * @return int


     *          an integer sequence number indicating the order
     *          a contributor would appear in a citation or -1 if not
     *          set
     */




    public final int getSeq() {
       String seq = super.getAttributeValue(Constants.ATTRIBUTE_SEQ);
       if (seq == null || seq.equals("")) {
           return -1;



       }
       return Integer.parseInt(seq);




    }






    /**
     * Create and return an empty NamePart object.
     *
     * The returned object has no properties or content and is not part



     * of the RIF-CS document, it is essentially a constructor of an object
     * owned by the RIF-CS document. The returned object needs to be
     * "filled out" (e.g. with properties, additional sub-elements, etc)
     * before being added to the RIF-CS document.



     *
     * @return the new NamePart object






     *
     * @throws RIFCSException A RIFCSException
     *
     */



    public final NamePart newNamePart() throws RIFCSException {
        return new NamePart(this.newElement(Constants.ELEMENT_NAMEPART));
    }


    /**











     * Add a name part to a name object.
     *






     * @param namePart
     *    a completed NamePart object
     */





    public final void addNamePart(final NamePart namePart) {




       this.getElement().appendChild(namePart.getElement());
       this.nameParts.add(namePart);






    }














    /**
     * Convenience method to add a name part to a name object.
     *
     * @param namePart
     *    String with the name value
     * @param type
     *    namePart type (e.g. surname, middle name) or null
     * @throws RIFCSException A RIFCSException
     */
    public final void addNamePart(final String namePart,
                            final String type) throws RIFCSException {




        NamePart np = newNamePart();




        np.setValue(namePart);





        np.setType(type);
        this.getElement().appendChild(np.getElement());
        this.nameParts.add(np);
    }


    /**
     * Obtain the name parts for this name.
     *
     * @return

     *      A list of NamePart objects
     */
    public final List<NamePart> getNameParts() {


        return nameParts;
    }


    /** Initialisation code for existing documents.
     *





     * @throws RIFCSException A RIFCSException
     *



     */
    private void initStructures() throws RIFCSException {
        NodeList nl = super.getElements(Constants.ELEMENT_NAMEPART);

        for (int i = 0; i < nl.getLength(); i++) {
            nameParts.add(new NamePart(nl.item(i)));
        }
    }

}
