package io.github.s0cks.mscheme.natives;

import io.github.s0cks.mscheme.Scheme;
import io.github.s0cks.mscheme.SchemeUtils;
import io.github.s0cks.mscheme.primitives.SchemeBoolean;
import io.github.s0cks.mscheme.primitives.SchemeNull;
import io.github.s0cks.mscheme.primitives.SchemeObject;
import io.github.s0cks.mscheme.primitives.SchemePair;
import io.github.s0cks.mscheme.primitives.SchemeProcedure;

public final class isnull
extends SchemeProcedure {
  @Override
  public SchemeObject apply(Scheme scheme, SchemeObject args) {
    if (args instanceof SchemePair) {
      SchemeObject obj = SchemeUtils.car(args);
      return obj == null || (obj instanceof SchemeNull)
             ? SchemeBoolean.TRUE
             : SchemeBoolean.FALSE;
    } else {
      return args == null || (args instanceof SchemeNull)
             ? SchemeBoolean.TRUE
             : SchemeBoolean.FALSE;
    }
  }
}