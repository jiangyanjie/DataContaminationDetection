package org.common.jfunk.test.maps;

import static junit.framework.Assert.assertEquals;

import java.util.HashMap;

import org.common.jfunk.Maps;
import org.common.jfunk.Pair;
import org.common.jfunk.Predicate;
import org.junit.Test;

public class DeleteIfKeepIfTest {
    
    @Test
    public void deleteIf() {
        assertEquals(Maps.<String, String>new_map("key2", "value2", "key3", "value3"), Maps.deleteIf(
            Maps.<String, String>new_map("key1", "value1", "key2", "value2", "key3", "value3"), 
            new Predicate<Pair<String, String>>() {
                public Boolean call(Pair<String, String> x) {
                    return "key1" == x.h;
                }
            })
        );
    }

    @Test
    public void deleteIfEmptyMap() {
        assertEquals(new HashMap<String, String>(), Maps.deleteIf(
            new HashMap<String, String>(), 
            new Predicate<Pair<String, String>>() {
                public Boolean call(Pair<String, String> x) {
                    return "key1" == x.h;
                }
            })
        );
    }

    @Test(expected=NullPointerException.class)
    public void deleteIfNullMapIsNotAllowed() {
        Maps.deleteIf(null, 
            new Predicate<Pair<String, String>>() {
                public Boolean call(Pair<String, String> x) {
                    return "key1" == x.h;
                }
            }
        );
    }

    @Test(expected=NullPointerException.class)
    public void deleteIfNullPredicateIsNotAllowed() {
        Maps.deleteIf(Maps.<String, String>new_map("key1", "value1"), null);
    }

    @Test
    public void keepIf() {
        assertEquals(Maps.<String, String>new_map("key1", "value1"), Maps.keepIf(
            Maps.<String, String>new_map("key1", "value1", "key2", "value2", "key3", "value3"), 
                new Predicate<Pair<String, String>>() {
                    public Boolean call(Pair<String, String> x) {
                        return "key1" == x.h;
                    }
                })
         );
    }
    
    @Test
    public void keepIfEmptyMap() {
        assertEquals(new HashMap<String, String>(), Maps.keepIf(
            new HashMap<String, String>(), 
            new Predicate<Pair<String, String>>() {
                public Boolean call(Pair<String, String> x) {
                    return "key1" == x.h;
                }
            })
        );
    }
    
    @Test(expected=NullPointerException.class)
    public void keepIfNullMapIsNotAllowed() {
        Maps.keepIf(null, 
            new Predicate<Pair<String, String>>() {
                public Boolean call(Pair<String, String> x) {
                    return "key1" == x.h;
                }
            }
        );
    }
    
    @Test(expected=NullPointerException.class)
    public void keepIfNullPredicateIsNotAllowed() {
        Maps.keepIf(Maps.<String, String>new_map("key1", "value1"), null);
    }
}