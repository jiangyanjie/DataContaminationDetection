package eu.pozoga.nice.code.helper;

import eu.pozoga.nice.classes.C;
import eu.pozoga.nice.classes.SimplePackFilter;
import eu.pozoga.nice.code.helper.converter.AbstractConverter;
import eu.pozoga.nice.code.helper.converter.Converter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import sun.security.jca.GetInstance;

/**
 *
 * @author Sebastian Pożoga
 */
public class ConverterHelper {
    
    private static Map<Key, AbstractConverter> converters; 
    
    public static Object convert(Object object, Class toType) throws Exception{
        if(object.getClass().equals(toType)){
            return object;
        }
        Key key = new Key(object.getClass(), toType);
        AbstractConverter converter = getConverters().get(key);
        if(converter==null){
            throw new Exception("No cenvertable type "+object.getClass()+" to "+toType);
        }
        return converter.convert(object);
    }

    protected static Map<Key, AbstractConverter> getConverters() throws Exception {
        if(converters==null){
            converters = new HashMap<Key, AbstractConverter>();
            SimplePackFilter f = new SimplePackFilter(AbstractConverter.class, Converter.class);
            Collection<Class> classes = C.getInstance().getPack().select(f).getClasses();
            for(Class converterClass : classes){
                Converter ann = (Converter) converterClass.getAnnotation(Converter.class);
                Key key = new Key(ann.from(), ann.to());
                converters.put(key, (AbstractConverter) converterClass.newInstance());
            }
        }
        return converters;
    }
    
    
    protected static class Key{
        Class from;
        Class to;

        public Key(Class from, Class to) {
            this.from = from;
            this.to = to;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Key other = (Key) obj;
            if (this.from != other.from && (this.from == null || !this.from.equals(other.from))) {
                return false;
            }
            if (this.to != other.to && (this.to == null || !this.to.equals(other.to))) {
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 29 * hash + (this.from != null ? this.from.hashCode() : 0);
            hash = 29 * hash + (this.to != null ? this.to.hashCode() : 0);
            return hash;
        }
    }
    
}
