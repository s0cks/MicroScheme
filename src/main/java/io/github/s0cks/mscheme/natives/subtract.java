package io.github.s0cks.mscheme.natives;

import io.github.s0cks.mscheme.Scheme;
import io.github.s0cks.mscheme.SchemeUtils;
import io.github.s0cks.mscheme.primitives.SchemeNull;
import io.github.s0cks.mscheme.primitives.SchemeNumber;
import io.github.s0cks.mscheme.primitives.SchemeObject;
import io.github.s0cks.mscheme.primitives.SchemePair;
import io.github.s0cks.mscheme.primitives.SchemeProcedure;

public final class subtract
extends SchemeProcedure {
  @Override
  public SchemeObject apply(Scheme scheme, SchemeObject args) {
    SchemeNumber result = new SchemeNumber(0);
    while(args != SchemeNull.instance){
      if(args instanceof SchemePair){
        result = result.subtract(SchemeUtils.car(args));
        args = SchemeUtils.cdr(args);
      } else{
        result = result.add(args);
        args = SchemeNull.instance;
      }
    }
    return result;
  }
}