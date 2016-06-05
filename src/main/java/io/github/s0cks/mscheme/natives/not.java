package io.github.s0cks.mscheme.natives;

import io.github.s0cks.mscheme.Scheme;
import io.github.s0cks.mscheme.SchemeUtils;
import io.github.s0cks.mscheme.primitives.SchemeBoolean;
import io.github.s0cks.mscheme.primitives.SchemeObject;
import io.github.s0cks.mscheme.primitives.SchemeProcedure;

public final class not
extends SchemeProcedure{
  @Override
  public SchemeObject apply(Scheme scheme, SchemeObject args) {
    return SchemeBoolean.of(SchemeUtils.car(args) == SchemeBoolean.FALSE);
  }
}