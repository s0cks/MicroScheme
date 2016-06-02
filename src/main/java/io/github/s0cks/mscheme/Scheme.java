package io.github.s0cks.mscheme;

import io.github.s0cks.mscheme.primitives.SchemeClosure;
import io.github.s0cks.mscheme.primitives.SchemeMacro;
import io.github.s0cks.mscheme.primitives.SchemeNull;
import io.github.s0cks.mscheme.primitives.SchemeObject;
import io.github.s0cks.mscheme.primitives.SchemePair;
import io.github.s0cks.mscheme.primitives.SchemeProcedure;
import io.github.s0cks.mscheme.primitives.SchemeString;

public final class Scheme {
  private final SchemeString LAMBDA = new SchemeString("lambda");

  public SchemeObject eval(SchemeObject func, Environment env) {
    while (true) {
      if (func instanceof SchemeString) {
        return env.lookup(((SchemeString) func).value);
      } else if (!(func instanceof SchemePair)) {
        return func;
      } else {
        SchemeObject fn = SchemeUtils.car(func);
        SchemeObject args = SchemeUtils.cdr(func);

        if (SchemeUtils.symbol(fn)
                       .equals("quote")) {
          return SchemeUtils.car(args);
        } else if (SchemeUtils.symbol(fn)
                              .equals("begin")) {
          for (; SchemeUtils.cdr(args) != SchemeNull.instance; args = SchemeUtils.cdr(args)) {
            this.eval(SchemeUtils.car(args), env);
          }
          func = SchemeUtils.car(args);
        } else if (SchemeUtils.symbol(fn)
                              .equals("define")) {
          if ((SchemeUtils.car(args) instanceof SchemePair)) {
            return env.define(SchemeUtils.car(SchemeUtils.car(args)),
                              this.eval(SchemeUtils.cons(LAMBDA, SchemeUtils.cons(SchemeUtils.cdr(SchemeUtils.car(args)), SchemeUtils.cdr(args))), env));
          } else {
            return env.define(SchemeUtils.car(args), this.eval(SchemeUtils.car(SchemeUtils.cdr(args)), env));
          }
        } else if (SchemeUtils.symbol(fn)
                              .equals("set!")) {
          return env.set(SchemeUtils.car(args), this.eval(SchemeUtils.cdr(args), env));
        } else if (SchemeUtils.symbol(fn)
                              .equals("if")) {
          func = (SchemeUtils.truth(this.eval(SchemeUtils.car(args), env))
                  ? SchemeUtils.cdr(args)
                  : SchemeUtils.cdr(SchemeUtils.cdr(args)));
        } else if (SchemeUtils.symbol(fn)
                              .equals("lambda")) {
          return new SchemeClosure(SchemeUtils.car(args), SchemeUtils.cdr(args), env);
        } else if (SchemeUtils.symbol(fn)
                              .equals("macro")) {
          return new SchemeMacro(SchemeUtils.car(args), SchemeUtils.cdr(args), env);
        } else {
          fn = this.eval(fn, env);
          if (fn instanceof SchemeMacro) {
            func = ((SchemeMacro) fn).expand(this, ((SchemePair) func), args);
          } else if (fn instanceof SchemeClosure) {
            SchemeClosure cls = ((SchemeClosure) fn);
            func = cls.body;
            env = new Environment(cls.params, this.evalList(args, env), cls.env);
          } else {
            return ((SchemeProcedure) fn).apply(this, this.evalList(args, env));
          }
        }
      }
    }
  }

  private SchemeObject evalList(SchemeObject pair, Environment env) {
    if (pair == null) {
      return SchemeNull.instance;
    } else if (!(pair instanceof SchemePair)) {
      return SchemeNull.instance;
    } else {
      return SchemeUtils.cons(this.eval(SchemeUtils.car(pair), env), this.evalList(SchemeUtils.cdr(pair), env));
    }
  }
}