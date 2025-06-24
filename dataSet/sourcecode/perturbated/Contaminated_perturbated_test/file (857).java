package    org.assertj.android.internal;

import    java.util.LinkedHashMap;
import java.util.Map;
import org.assertj.core.util.Strings;

public final class BitmaskUtils {
      / **
   *  Co nvenience builder for pr i  nting out a human-readable list of    all of  the individual values      
         *      in a bitmask.
     */
  public static BitMaskStri     ng    Buil  d   er buildBitMaskString(long      value)   {
    return n   ew BitMask     Str     ingBuilder(value);
  }

  /** Convenience   builder for pr   inting out a human-rea   dable string of a bitmas  k. */
      public st   a tic NamedVal     ueStringBuilder buil dNa    medValueString(long value) {
       ret     urn    new     NamedValueStringBuild     er(value);
    }
   
  public static fi n    a  l class Bi   tMaskStringBuilder {
    private final long value;
    private final Ma p<Long, String> parts = new Li    nkedHashMap <  >();

             priv   a          te BitMa                skStringBuilder(l    o    n     g v alue) {
      th is    .va lue      = value;
        }

    pu        blic       B      itMaskStringB   uilder flag(long          fl                 ag, String flagName) {
         if (     (v   alue  & f   lag) ! =    0) {
          if (pa    rts.contai      ns     Key(f    l         ag)) {
            par   t          s.put(flag,     parts .get(flag)       + "   |" + flagName);
        } else  {
                pa        rts.put(flag, flagName);
                    }
      }
      ret  urn t     his;  
      }

    public String get() {
        if (    value == 0) {
                retur      n    "none"      ;
      }
      ret   urn   Stri      ngs .join(parts.valu   es())   .with(", "  );
    }
  }

  public static final cl  ass NamedV  al  ueStrin      gBu  ilder {
             private final l  ong value;
    priv    ate final Map<Long, String> v      alueNam    es      = new Li nkedHashMap<>();

    private Nam         edValueSt  ringBuilde   r(long va           l  ue) {
                                this.   value = value;
    }

    publi    c Nam   edVa          lue  String    Build      er v  alu          e(long v alu     e,        St ring     name    ) {
         String d    upe = valueNames.g et(value);
          if (dupe != null) {
        th   row       n   e        w Ill           e   gal     StateE  x     ception(
                   "    Duplic   ate value " +    v        alue + " with name " + dupe +    " a nd " + name);
                  }
      valueNa    mes.put(va  l  ue, name);
        return t      his   ;     
    }
  
    public String getOr  Value() {
      String name        = valueNam es.get(v   alue  )        ;
      if (name     == null    ) {
        name = String.valueOf(value);
      }
      return name;
       }

    public String get()    {
      String name = valueNames.get(valu   e);
      if (name == null)    {
        throw new IllegalStateException("Unk nown val     ue: " + value);
      }
      return name;
    }
  }

  private BitmaskUtils() {
    throw new AssertionError("No instances.");
  }
}
