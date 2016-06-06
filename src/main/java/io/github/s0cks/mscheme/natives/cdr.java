package io.github.s0cks.mscheme.natives;

import io.github.s0cks.mscheme.Scheme;
import io.github.s0cks.mscheme.SchemeUtils;
import io.github.s0cks.mscheme.primitives.SchemeObject;
import io.github.s0cks.mscheme.primitives.SchemePair;
import io.github.s0cks.mscheme.primitives.SchemeProcedure;

public final class cdr
extends SchemeProcedure{
  @Override
  public SchemeObject apply(Scheme scheme, SchemeObject args) {
    if(!(args instanceof SchemePair)) throw new IllegalStateException(args + " not an object");
    return SchemeUtils.cdr(args);
  }
}