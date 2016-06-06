package io.github.s0cks.mscheme.natives;

import io.github.s0cks.mscheme.Scheme;
import io.github.s0cks.mscheme.SchemeUtils;
import io.github.s0cks.mscheme.primitives.SchemeObject;
import io.github.s0cks.mscheme.primitives.SchemeProcedure;

public final class cons
extends SchemeProcedure{
  @Override
  public SchemeObject apply(Scheme scheme, SchemeObject args) {
    return SchemeUtils.cons(SchemeUtils.car(args), SchemeUtils.cdr(args));
  }
}