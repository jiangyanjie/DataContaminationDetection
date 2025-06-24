package classfileparser;

import java.io.DataInputStream;
import java.io.IOException;

public class ConstantPoolReader
{
    public static int i;

    private DataInputStream dis = null;

    private ConstantPoolLookUp constantPoolLookUp = null;

    public void setDis( DataInputStream dis )
    {
        this.dis = dis;
    }

    public void setConstantPoolLookUp( ConstantPoolLookUp constantPoolLookUp )
    {
        this.constantPoolLookUp = constantPoolLookUp;
    }

    public void readConstantPool() throws Exception
    {
        int constantPoolCount = ByteReader.read_u2( dis );

        System.out.println( "Constant Pool Count:" + constantPoolCount );

        for ( i = 1; i < constantPoolCount; i++ )
        {
            int tag = dis.readUnsignedByte();

            switch ( tag )
            {
                case 1:
                    //System.out.println( "CONSTANT_Utf8" );
                    readConstantUTF8Info();
                    break;
                case 3:
                    //System.out.println( "CONSTANT_Integer" );
                    readConstantIntegerInfo();
                    break;
                case 4:
                    //System.out.println( "CONSTANT_Float" );
                    readConstantFloatInfo();
                    break;
                case 5:
                    //System.out.println( "CONSTANT_Long" );
                    readConstantLongInfo();
                    i++;
                    break;
                case 6:
                    //System.out.println( "CONSTANT_Double" );
                    readConstantDoubleInfo();
                    i++;
                    break;
                case 7:
                    //System.out.println( "CONSTANT_Class" );
                    readConstantClassInfo();
                    break;
                case 8:
                    //System.out.println( "CONSTANT_String" );
                    readConstantStringInfo();
                    break;
                case 9:
                    //System.out.println( "CONSTANT_Fieldref" );
                    readConstantFieldRefInfo();
                    break;
                case 10:
                    //System.out.println( "CONSTANT_Methodref" );
                    readConstantMethodRefInfo();
                    break;
                case 11:
                    //System.out.println( "CONSTANT_InterfaceMethodref" );
                    readConstantIntfMethodRefInfo();
                    break;
                case 12:
                    //System.out.println( "CONSTANT_NameAndType" );
                    readConstantNameandTypeInfo();
                    break;

                default:
                    throw new Exception( "Unknown Tag:" + tag );
            }
        }
    }

    private void readConstantUTF8Info() throws IOException
    {
        //Read UTF directly reads the length
        String utf = dis.readUTF();
        constantPoolLookUp.put( i, ConstantPoolType.CONSTANT_Utf8, utf );
        System.out.println( i + " " + utf );

    }

    private void readConstantIntegerInfo() throws IOException
    {
        Integer const_integer = dis.readInt();
        constantPoolLookUp.put( i, ConstantPoolType.CONSTANT_Integer, const_integer );
        System.out.println( i + "  " + const_integer );

    }

    private void readConstantFloatInfo() throws IOException
    {
        Float const_float = dis.readFloat();
        constantPoolLookUp.put( i, ConstantPoolType.CONSTANT_Float, const_float );
        System.out.println( i + "  " + const_float );

    }

    private void readConstantLongInfo() throws IOException
    {
        Long const_long = dis.readLong();
        constantPoolLookUp.put( i, ConstantPoolType.CONSTANT_Long, const_long );
        System.out.println( i + "  " + const_long );
        //These values consume two entries in constant_pool table. Hence the index of the next item will be two more than the current entry

    }

    private void readConstantDoubleInfo() throws IOException
    {
        Double const_double = dis.readDouble();
        constantPoolLookUp.put( i, ConstantPoolType.CONSTANT_Long, const_double );
        System.out.println( i + "  " + const_double );

    }

    private void readConstantClassInfo() throws IOException
    {
        int class_info = ByteReader.read_u2( dis );
        constantPoolLookUp.put( i, ConstantPoolType.CONSTANT_Class, class_info );
        System.out.println( i + "  " + class_info );

    }

    private void readConstantStringInfo() throws IOException
    {
        Integer string_info = ByteReader.read_u2( dis );
        constantPoolLookUp.put( i, ConstantPoolType.CONSTANT_String, string_info );
        System.out.println( i + "  " + string_info.toString() );

    }

    private void readConstantFieldRefInfo() throws IOException
    {
        String field_ref_info = ByteReader.read_u2( dis ) + ClassFileParser.TAG_SEPARATOR + ByteReader.read_u2( dis );
        constantPoolLookUp.put( i, ConstantPoolType.CONSTANT_Fieldref, field_ref_info );
        System.out.println( i + "  " + field_ref_info );

    }

    private void readConstantMethodRefInfo() throws IOException
    {
        String method_ref_info = ByteReader.read_u2( dis ) + ClassFileParser.TAG_SEPARATOR + ByteReader.read_u2( dis );
        constantPoolLookUp.put( i, ConstantPoolType.CONSTANT_Methodref, method_ref_info );
        System.out.println( i + "  " + method_ref_info );
    }

    private void readConstantIntfMethodRefInfo() throws IOException
    {
        String intf_method_ref_info = ByteReader.read_u2( dis ) + ClassFileParser.TAG_SEPARATOR + ByteReader.read_u2( dis );
        constantPoolLookUp.put( i, ConstantPoolType.CONSTANT_InterfaceMethodref, intf_method_ref_info );
        System.out.println( i + "  " + intf_method_ref_info );

    }

    private void readConstantNameandTypeInfo() throws IOException
    {
        String name_type_info = ByteReader.read_u2( dis ) + ClassFileParser.TAG_SEPARATOR + ByteReader.read_u2( dis );
        constantPoolLookUp.put( i, ConstantPoolType.CONSTANT_NameAndType, name_type_info );
        System.out.println( i + "  " + name_type_info );
    }

}
