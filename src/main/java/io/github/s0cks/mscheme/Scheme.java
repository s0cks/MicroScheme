package io.github.s0cks.mscheme;

import io.github.s0cks.mscheme.primitives.SchemeClosure;
import io.github.s0cks.mscheme.primitives.SchemeMacro;
import io.github.s0cks.mscheme.primitives.SchemeNull;
import io.github.s0cks.mscheme.primitives.SchemeObject;
import io.github.s0cks.mscheme.primitives.SchemePair;
import io.github.s0cks.mscheme.primitives.SchemeProcedure;
import io.github.s0cks.mscheme.primitives.SchemeSymbol;

public final class Scheme {
  public static final SchemeSymbol LAMBDA = new SchemeSymbol("lambda");
  public static final Environment GLOBAL = new Environment();

  public SchemeObject eval(SchemeObject func){
    return this.eval(func, GLOBAL);
  }

  public SchemeObject eval(SchemeObject func, Environment env) {
    while (true) {
      if (func instanceof SchemeSymbol) {
        return env.lookup(((SchemeSymbol) func).value);
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
          return env.set(SchemeUtils.car(args), this.eval(SchemeUtils.car(SchemeUtils.cdr(args)), env));
        } else if (SchemeUtils.symbol(fn)
                              .equals("if")) {
          func = (SchemeUtils.truth(this.eval(SchemeUtils.car(args), env))
                  ? SchemeUtils.car(SchemeUtils.cdr(args))
                  : SchemeUtils.car(SchemeUtils.cdr(SchemeUtils.cdr(args))));
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
            System.out.println("FN: " + fn);
            return ((SchemeProcedure) fn).apply(this, this.evalList(args, env));
          }
        }
      }
    }
  }

  private SchemeObject evalList(SchemeObject pair, Environment env) {
    if (pair == SchemeNull.instance) {
      return SchemeNull.instance;
    } else if (!(pair instanceof SchemePair)) {
      return SchemeNull.instance;
    } else {
      return SchemeUtils.cons(this.eval(SchemeUtils.car(pair), env), this.evalList(SchemeUtils.cdr(pair), env));
    }
  }
}