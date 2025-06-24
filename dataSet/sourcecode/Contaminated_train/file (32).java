package eu.pozoga.nice.classes;

import eu.pozoga.nice.classes.ex.TypeAnn;
import eu.pozoga.nice.classes.test.C1;
import eu.pozoga.nice.classes.test.C2;
import eu.pozoga.nice.classes.test.C3;
import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;


public class AbstractCloudTest {
    
    public AbstractCloud newInstance() throws Exception{
        return new AbstractCloud();
    }
    
    @Test
    public void testInitMap() throws Exception {
        AbstractCloud instance = newInstance();
        //instance.initMap(); - invoke by constructor
        assertNotNull(instance.objects);
    }

    @Test
    public void testGetObjects_NotNull() throws Exception {
        AbstractCloud instance = newInstance();
        Map result = instance.getObjects();
        assertNotNull(result);
    }
    
    @Test
    public void testGetObjects_Result() throws Exception {
        AbstractCloud instance = newInstance();
        instance.objects.clear();
        instance.objects.put("o1", new C1());
        instance.objects.put("o2", new C2());
        Map expResult = new HashMap(instance.objects);
        Map result = instance.getObjects();
        //assertTrue(result.containsValue(expResult.values()));
        assertEquals(expResult, result);
    }

    @Test
    public void testGet() throws Exception {
        AbstractCloud instance = newInstance();
        String name = "myName";
        C2 expResult = new C2();
        instance.objects.put(name, expResult);
        Object result = instance.get(name);
        assertEquals(expResult, result);
    }

    @Test
    public void testPut() throws Exception {
        String name = "myName";
        C2 result = new C2();
        AbstractCloud instance = newInstance();
        instance.put(name, result);
        assertTrue(instance.objects.containsValue(result));
    }

    @Test
    public void testAdd() throws Exception {
        C2 object = new C2();
        AbstractCloud instance = newInstance();
        instance.add(object);
        assertTrue( instance.objects.containsValue(object) );
    }

    @Test
    public void testSelect_NotNull() throws Exception {
        PackFilter filter = new SimplePackFilter();
        AbstractCloud instance = newInstance();
        AbstractCloud result = instance.select(filter);
        assertNotNull(result);
    }
    
    @Test
    //Important!!!!
    public void testSelect_NotEmpty() throws Exception {
        PackFilter filter = new SimplePackFilter();
        AbstractCloud instance = newInstance();
        AbstractCloud result = instance.select(filter);
        //Contain all class
        assertTrue(result.classPack.getClasses().size()>0);
    }
    
    @Test
    //Important!!!!
    public void testSelect_ContainTestedClasses() throws Exception {
        PackFilter filter = new SimplePackFilter();
        AbstractCloud instance = newInstance();
        AbstractCloud result = instance.select(filter);
        //Contain all class
        Set mustContain = new HashSet();
        mustContain.add(C1.class);
        mustContain.add(C2.class);
        mustContain.add(C3.class);
        assertTrue(result.classPack.getClasses().containsAll(mustContain));
    }
    
    @Test
    //Important!!!!
    public void testSelect_byAnnotation() throws Exception {
        PackFilter filter = new SimplePackFilter(null, TypeAnn.class);
        AbstractCloud instance = newInstance();
        AbstractCloud result = instance.select(filter);
        //Contain C1 and C2 classes (all clas with testAnn)
        Set mustContain = new HashSet();
        mustContain.add(C1.class);
        mustContain.add(C2.class);
        assertEquals(result.classPack.getClasses(), mustContain);
    }
}
