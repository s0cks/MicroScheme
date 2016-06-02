package io.github.s0cks.mscheme.primitives;

import io.github.s0cks.mscheme.Environment;
import io.github.s0cks.mscheme.Scheme;
import io.github.s0cks.mscheme.SchemeUtils;

public final class SchemeMacro
extends SchemeClosure{
  public SchemeMacro(SchemeObject params, SchemeObject body, Environment env) {
    super(params, body, env);
  }

  public SchemeObject expand(Scheme scheme, SchemePair old, SchemeObject args){
    SchemeObject expansion = this.apply(scheme, args);
    if(expansion instanceof SchemePair){
      old.car = ((SchemePair) expansion).car;
      old.cdr = ((SchemePair) expansion).cdr;
    } else{
      old.car = new SchemeString("begin");
      old.cdr = SchemeUtils.cons(expansion, null);
    }

    return old;
  }
}