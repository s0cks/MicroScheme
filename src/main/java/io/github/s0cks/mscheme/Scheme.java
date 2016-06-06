package io.github.s0cks.mscheme;

import io.github.s0cks.mscheme.primitives.SchemeBoolean;
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

  public SchemeObject eval(SchemeObject func) {
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
          if(SchemeUtils.car(args) instanceof SchemePair){
            return env.define(SchemeUtils.car(SchemeUtils.car(args)),
                              this.eval(SchemeUtils.cons(LAMBDA, SchemeUtils.cons(SchemeUtils.cdr(SchemeUtils.car(args)), SchemeUtils.cdr(args))), env));
          } else{
            return env.define(SchemeUtils.car(args), this.eval(SchemeUtils.cadr(args), env));
          }
        } else if (SchemeUtils.symbol(fn)
                              .equals("set!")) {
          return env.set(SchemeUtils.car(args), this.eval(SchemeUtils.car(SchemeUtils.cdr(args)), env));
        } else if (SchemeUtils.symbol(fn)
                              .equals("if")) {
          func = (SchemeUtils.truth(this.eval(SchemeUtils.car(args), env)))
                 ? SchemeUtils.cadr(args)
                 : SchemeUtils.caddr(args);
        } else if(SchemeUtils.symbol(fn).equals("cond")){
          func = this.reduce(args, env);
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
    if (pair == SchemeNull.instance) {
      return SchemeNull.instance;
    } else if (!(pair instanceof SchemePair)) {
      return SchemeNull.instance;
    } else {
      return SchemeUtils.cons(this.eval(SchemeUtils.car(pair), env), this.evalList(SchemeUtils.cdr(pair), env));
    }
  }

  private SchemeObject reduce(SchemeObject clauses, Environment env){
    SchemeObject result = SchemeNull.instance;
    for(;;){
      if(clauses == SchemeNull.instance) return SchemeBoolean.FALSE;
      SchemeObject clause = SchemeUtils.car(clauses);
      clauses = SchemeUtils.cdr(clauses);
      if ((SchemeUtils.symbol(SchemeUtils.car(clause))
                      .equals("else")) || SchemeUtils.truth(result = this.eval(SchemeUtils.car(clause), env))) {
        if(SchemeUtils.cdr(clause) == SchemeNull.instance) {
          return SchemeUtils.cons(new SchemeSymbol("quote"), SchemeUtils.cons(result, SchemeNull.instance));
        }
        else if(SchemeUtils.symbol(SchemeUtils.cadr(clause)).equals("=>")){
          return SchemeUtils.cons(SchemeUtils.caddr(clause), SchemeUtils.cons(SchemeUtils.cons(new SchemeSymbol("quote"), SchemeUtils.cons(result, SchemeNull.instance)), SchemeNull.instance));
        }
        else{
          return SchemeUtils.cons(new SchemeSymbol("begin"), SchemeUtils.cdr(clause));
        }
      }
    }
  }
}