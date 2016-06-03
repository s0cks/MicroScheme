package io.github.s0cks.mscheme;

import io.github.s0cks.mscheme.primitives.SchemeBoolean;
import io.github.s0cks.mscheme.primitives.SchemeObject;
import io.github.s0cks.mscheme.primitives.SchemePair;
import io.github.s0cks.mscheme.primitives.SchemeSymbol;

public final class SchemeUtils {
  public static SchemeObject car(SchemeObject obj) {
    return (obj instanceof SchemePair)
           ? ((SchemePair) obj).car
           : null;
  }

  public static SchemeObject cdr(SchemeObject obj) {
    return (obj instanceof SchemePair)
           ? ((SchemePair) obj).cdr
           : null;
  }

  public static String symbol(SchemeObject obj) {
    return (obj instanceof SchemeSymbol)
           ? ((SchemeSymbol) obj).value
           : "";
  }

  public static SchemeObject setFirst(SchemeObject pair, SchemeObject value) {
    if(!(pair instanceof SchemePair)) throw new IllegalStateException("Attempt to set-car of non-pair object: " + pair);
    return ((SchemePair) pair).car = value;
  }

  public static SchemeObject setLast(SchemeObject pair, SchemeObject value){
    if(!(pair instanceof SchemePair)) throw new IllegalStateException("Attempt to set-car of non-pair object: " + pair);
    return ((SchemePair) pair).cdr = value;
  }

  public static boolean truth(SchemeObject obj){
    return (obj instanceof SchemeBoolean) && ((SchemeBoolean) obj).value;
  }

  public static SchemeObject cons(SchemeObject a, SchemeObject b) {
    return new SchemePair(a, b);
  }
}