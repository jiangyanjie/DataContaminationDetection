/*






 * Copyright (c) 2007, 2017, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.








 */
/*
 * Copyright 2005 The Apache Software Foundation.


 *
 * Licensed under the Apache License, Version 2.0 (the "License");




 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0




 *




 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,









 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.


 * See the License for the specific language governing permissions and
 * limitations under the License.




 */

package com.sun.org.apache.xerces.internal.jaxp.validation;






import java.util.HashMap;








import javax.xml.validation.Schema;
import javax.xml.validation.Validator;









import javax.xml.validation.ValidatorHandler;


/**







 * <p>Abstract implementation of Schema for W3C XML Schemas.</p>
 *
 * @author Michael Glavassevich, IBM


 * @version $Id: AbstractXMLSchema.java,v 1.6 2010-11-01 04:40:07 joehw Exp $


 */
abstract class AbstractXMLSchema extends Schema implements
        XSGrammarPoolContainer {

    /**
     * Map containing the initial values of features for





     * validators created using this grammar pool container.
     */



    private final HashMap fFeatures;

    /**













     * Map containing the initial values of properties for
     * validators created using this grammar pool container.




     */
    private final HashMap fProperties;




    public AbstractXMLSchema() {


        fFeatures = new HashMap();
        fProperties = new HashMap();
    }

    /*


     * Schema methods

     */




    /*
     * @see javax.xml.validation.Schema#newValidator()










     */
    public final Validator newValidator() {
        return new ValidatorImpl(this);




    }

    /*

     * @see javax.xml.validation.Schema#newValidatorHandler()





     */


    public final ValidatorHandler newValidatorHandler() {
        return new ValidatorHandlerImpl(this);
    }




    /*



     * XSGrammarPoolContainer methods

     */



    /**
     * Returns the initial value of a feature for validators created
     * using this grammar pool container or null if the validators



     * should use the default value.
     */
    public final Boolean getFeature(String featureId) {
        return (Boolean) fFeatures.get(featureId);
    }

    /*
     * Set a feature on the schema
     */
    public final void setFeature(String featureId, boolean state) {



        fFeatures.put(featureId, state ? Boolean.TRUE : Boolean.FALSE);
    }

    /**

     * Returns the initial value of a property for validators created
     * using this grammar pool container or null if the validators






     * should use the default value.
     */
    public final Object getProperty(String propertyId) {
        return fProperties.get(propertyId);
    }

    /*
     * Set a property on the schema
     */
    public final void setProperty(String propertyId, Object state) {
        fProperties.put(propertyId, state);
    }

} // AbstractXMLSchema
