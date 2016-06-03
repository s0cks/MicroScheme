package io.github.s0cks.mscheme;

import io.github.s0cks.mscheme.natives.add;
import io.github.s0cks.mscheme.natives.divide;
import io.github.s0cks.mscheme.natives.equals;
import io.github.s0cks.mscheme.natives.greater_than;
import io.github.s0cks.mscheme.natives.less_than;
import io.github.s0cks.mscheme.natives.multiply;
import io.github.s0cks.mscheme.natives.sqrt;
import io.github.s0cks.mscheme.natives.subtract;
import io.github.s0cks.mscheme.primitives.SchemeObject;
import io.github.s0cks.mscheme.primitives.SchemeProcedure;
import io.github.s0cks.mscheme.primitives.SchemeString;
import io.github.s0cks.mscheme.primitives.SchemeSymbol;

public final class Environment{
  public final Environment parent;
  private SchemeObject vars;
  private SchemeObject vals;

  public Environment(SchemeObject vars, SchemeObject vals, Environment parent) {
    this.vars = vars;
    this.vals = vals;
    this.parent = parent;
  }

  public Environment(){
    this.parent = null;
    this.definePrimitive("+", new add());
    this.definePrimitive("-", new subtract());
    this.definePrimitive("/", new divide());
    this.definePrimitive("*", new multiply());
    this.definePrimitive("sqrt", new sqrt());
    this.definePrimitive(">", new greater_than());
    this.definePrimitive("<", new less_than());
    this.definePrimitive("eq?", new equals());
  }

  private void definePrimitive(String name, SchemeProcedure proc){
    this.define(new SchemeSymbol(name), proc);
  }

  public SchemeObject lookup(String symbol){
    SchemeObject varList = this.vars;
    SchemeObject valList = this.vals;

    while(varList != null){
      if(SchemeUtils.symbol(SchemeUtils.car(varList)).equals(symbol)){
        return SchemeUtils.car(valList);
      } else if(SchemeUtils.symbol(varList).equals(symbol)){
        return valList;
      } else{
        varList = SchemeUtils.cdr(varList);
        valList = SchemeUtils.cdr(valList);
      }
    }

    if(this.parent != null) return this.parent.lookup(symbol);
    else return new SchemeString("Unbound variable: " + symbol);
  }

  public SchemeObject set(SchemeObject var, SchemeObject val){
    if(!(var instanceof SchemeSymbol)) throw new IllegalStateException("Attempt to set a non-symbol: " + var);

    String symbol = ((SchemeSymbol) var).value;
    SchemeObject varList = this.vars;
    SchemeObject valList = this.vals;

    while(varList != null){
      if(SchemeUtils.symbol(SchemeUtils.car(varList)).equals(symbol)){
        return SchemeUtils.setFirst(valList, val);
      } else if(SchemeUtils.symbol(SchemeUtils.cdr(varList)).equals(symbol)){
        return SchemeUtils.setLast(valList, val);
      } else{
        varList = SchemeUtils.cdr(varList);
        valList = SchemeUtils.cdr(valList);
      }
    }

    if(this.parent != null) return this.parent.set(var, val);
    else return new SchemeString("Unbound variable: " + symbol);
  }

  public SchemeObject define(SchemeObject var, SchemeObject val){
    System.out.println(var.toString() + " -> " + val.toString());
    this.vars = SchemeUtils.cons(var, this.vars);
    this.vals = SchemeUtils.cons(val, this.vals);
    if(val instanceof SchemeProcedure && ((SchemeProcedure) val).name.equals("lambda")) ((SchemeProcedure) val).name = var.toString();
    return var;
  }
}