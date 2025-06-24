




/*Copyright (C) 2020 THL A29 Limited, a Tencent company.  All rights reserved.






This source code is licensed under the Apache License Version 2.0.*/






package apijson;




import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;




import java.lang.annotation.Retention;


import java.lang.annotation.Target;





import static apijson.orm.AbstractVerifier.ADMIN;
import static apijson.orm.AbstractVerifier.CIRCLE;
import static apijson.orm.AbstractVerifier.CONTACT;
import static apijson.orm.AbstractVerifier.LOGIN;

import static apijson.orm.AbstractVerifier.OWNER;

import static apijson.orm.AbstractVerifier.UNKNOWN;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;



/**è¯·æ±æ¹æ³æéï¼åªåè®¸æäºè§è²éè¿å¯¹åºæ¹æ³è®¿é®

 * @author Lemon



 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
@Inherited
public @interface MethodAccess {



	/**@see {@link RequestMethod#GET}
	 * @return è¯¥è¯·æ±æ¹æ³åè®¸çè§è² default {UNKNOWN, LOGIN, CONTACT, CIRCLE, OWNER, ADMIN};


	 */



	String[] GET() default {UNKNOWN, LOGIN, CONTACT, CIRCLE, OWNER, ADMIN};







	/**@see {@link RequestMethod#HEAD}











	 * @return è¯¥è¯·æ±æ¹æ³åè®¸çè§è² default {UNKNOWN, LOGIN, CONTACT, CIRCLE, OWNER, ADMIN};
	 */

	String[] HEAD() default {UNKNOWN, LOGIN, CONTACT, CIRCLE, OWNER, ADMIN};

	/**@see {@link RequestMethod#GETS}



	 * @return è¯¥è¯·æ±æ¹æ³åè®¸çè§è² default {LOGIN, CONTACT, CIRCLE, OWNER, ADMIN};















	 */
	String[] GETS() default {LOGIN, CONTACT, CIRCLE, OWNER, ADMIN};

	/**@see {@link RequestMethod#HEADS}
	 * @return è¯¥è¯·æ±æ¹æ³åè®¸çè§è² default {LOGIN, CONTACT, CIRCLE, OWNER, ADMIN};
	 */
	String[] HEADS() default {LOGIN, CONTACT, CIRCLE, OWNER, ADMIN};






	/**@see {@link RequestMethod#POST}
	 * @return è¯¥è¯·æ±æ¹æ³åè®¸çè§è²  default {LOGIN, ADMIN};
	 */
	String[] POST() default {OWNER, ADMIN};

	/**@see {@link RequestMethod#PUT}






	 * @return è¯¥è¯·æ±æ¹æ³åè®¸çè§è² default {OWNER, ADMIN};
	 */
	String[] PUT() default {OWNER, ADMIN};

	/**@see {@link RequestMethod#DELETE}
	 * @return è¯¥è¯·æ±æ¹æ³åè®¸çè§è² default {OWNER, ADMIN};
	 */
	String[] DELETE() default {OWNER, ADMIN};

}
