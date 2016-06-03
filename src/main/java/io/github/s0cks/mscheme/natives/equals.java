package io.github.s0cks.mscheme.natives;

import io.github.s0cks.mscheme.Scheme;
import io.github.s0cks.mscheme.SchemeUtils;
import io.github.s0cks.mscheme.primitives.SchemeBoolean;
import io.github.s0cks.mscheme.primitives.SchemeNumber;
import io.github.s0cks.mscheme.primitives.SchemeObject;
import io.github.s0cks.mscheme.primitives.SchemeProcedure;

public final class equals
extends SchemeProcedure{
  @Override
  public SchemeObject apply(Scheme scheme, SchemeObject args) {
    SchemeNumber value = ((SchemeNumber) (SchemeUtils.car(args)));
    args = SchemeUtils.cdr(args);
    SchemeNumber target = ((SchemeNumber) (SchemeUtils.car(args)));
    return value.value() == target.value() ? SchemeBoolean.TRUE : SchemeBoolean.FALSE;
  }
}