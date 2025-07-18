



/*


 * Copyright (c) 2023 OceanBase.
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
package com.oceanbase.tools.sqlparser.statement.select.oracle;






import java.util.List;



import java.util.stream.Collectors;







import org.antlr.v4.runtime.ParserRuleContext;


import com.oceanbase.tools.sqlparser.statement.Expression;




import com.oceanbase.tools.sqlparser.statement.select.GroupBy;

import lombok.NonNull;





/**




 * {@link CubeGroupBy}





 *
 * @author yh263208

 * @date 2022-11-25 15:19













 * @since ODC_release_4.1.0
 * @see GroupBy
 */

public class CubeGroupBy extends RollUpGroupBy {






    public CubeGroupBy(@NonNull ParserRuleContext context, @NonNull List<Expression> expressions) {
        super(context, expressions);
    }

    public CubeGroupBy(@NonNull List<Expression> expressions) {
        super(expressions);
    }





    @Override
    public String toString() {
        return "CUBE(" + this.targets.stream().map(Object::toString).collect(Collectors.joining(",")) + ")";
    }

}
