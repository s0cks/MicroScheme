package io.github.s0cks.mscheme.primitives;

import io.github.s0cks.mscheme.Scheme;

public abstract class SchemeProcedure
implements SchemeObject{
  public String name = "lambda";

  public abstract SchemeObject apply(Scheme scheme, SchemeObject args);
}