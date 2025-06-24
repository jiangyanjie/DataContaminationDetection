package classfileparser;

import java.util.HashMap;
import java.util.Map;

public class ConstantPoolLookUp
{
    public static Map< Integer, ConstantPoolValue > constantPool = new HashMap< Integer, ConstantPoolValue >();

    public void put( int tag, ConstantPoolType type, Object value )
    {
        ConstantPoolValue constantPoolValue = new ConstantPoolValue();
        constantPoolValue.type = type;
        constantPoolValue.value = value;
        constantPool.put( tag, constantPoolValue );
    }

    public String lookUp( Integer tag ) throws Exception
    {
        ConstantPoolValue cpValue = constantPool.get( tag );

        if ( cpValue == null )
        {
            System.out.println("unable to look up:" + tag);
            return "";
        }
        if ( cpValue.type.equals( ConstantPoolType.CONSTANT_Utf8 ) )
        {
            return (String)cpValue.value;
        }
        else if ( cpValue.type.equals( ConstantPoolType.CONSTANT_Integer ) )
        {
            return ( (Integer)cpValue.value ).toString();
        }
        else if ( cpValue.type.equals( ConstantPoolType.CONSTANT_Long ) )
        {
            return ( (Long)cpValue.value ).toString();
        }
        else if ( cpValue.type.equals( ConstantPoolType.CONSTANT_Double ) )
        {
            return ( (Double)cpValue.value ).toString();
        }
        else if ( cpValue.type.equals( ConstantPoolType.CONSTANT_String ) || cpValue.type.equals( ConstantPoolType.CONSTANT_Class ) )
        {
            return lookUp( (Integer)cpValue.value );
        }
        else if ( cpValue.type.equals( ConstantPoolType.CONSTANT_Methodref ) || cpValue.type.equals( ConstantPoolType.CONSTANT_InterfaceMethodref ) || cpValue.type.equals( ConstantPoolType.CONSTANT_NameAndType ) || cpValue.type.equals( ConstantPoolType.CONSTANT_Fieldref ) )

        {
            String value = ( (String)cpValue.value );
            String[] tokens = value.split( ClassFileParser.TAG_SEPARATOR );
            return lookUp( new Integer( tokens[0] ) ) + " " + lookUp( new Integer( tokens[1] ) );
        }

        return " ";
    }

    class ConstantPoolValue
    {
        public ConstantPoolType type;
        public Object value;
    }
}
