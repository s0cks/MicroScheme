package io.github.s0cks.mscheme.natives;

import io.github.s0cks.mscheme.Scheme;
import io.github.s0cks.mscheme.SchemeUtils;
import io.github.s0cks.mscheme.primitives.SchemeNumber;
import io.github.s0cks.mscheme.primitives.SchemeObject;
import io.github.s0cks.mscheme.primitives.SchemePair;
import io.github.s0cks.mscheme.primitives.SchemeProcedure;

public final class sqrt
extends SchemeProcedure {
  @Override
  public SchemeObject apply(Scheme scheme, SchemeObject args) {
    if(args instanceof SchemePair){
      return new SchemeNumber(Math.sqrt(((SchemeNumber) (SchemeUtils.car(args))).value()));
    } else{
      return new SchemeNumber(Math.sqrt(((SchemeNumber) args).value()));
    }
  }
}