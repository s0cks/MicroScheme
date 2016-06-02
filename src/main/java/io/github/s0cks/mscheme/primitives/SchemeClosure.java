package io.github.s0cks.mscheme.primitives;

import io.github.s0cks.mscheme.Environment;
import io.github.s0cks.mscheme.Scheme;

public class SchemeClosure
extends SchemeProcedure{
  public final SchemeObject params;
  public final SchemeObject body;
  public final Environment env;

  public SchemeClosure(SchemeObject params, SchemeObject body, Environment env) {
    this.params = params;
    this.body = body;
    this.env = env;
  }

  public SchemeObject apply(Scheme interpreter, SchemeObject args){
    return interpreter.eval(this.body, new Environment(this.params, args, this.env));
  }
}