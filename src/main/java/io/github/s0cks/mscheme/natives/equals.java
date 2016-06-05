package io.github.s0cks.mscheme.natives;

import io.github.s0cks.mscheme.Scheme;
import io.github.s0cks.mscheme.SchemeUtils;
import io.github.s0cks.mscheme.primitives.SchemeBoolean;
import io.github.s0cks.mscheme.primitives.SchemeNumber;
import io.github.s0cks.mscheme.primitives.SchemeObject;
import io.github.s0cks.mscheme.primitives.SchemeProcedure;
import io.github.s0cks.mscheme.primitives.SchemeString;

public final class equals
extends SchemeProcedure{
  @Override
  public SchemeObject apply(Scheme scheme, SchemeObject args) {
    SchemeObject first = SchemeUtils.car(args);
    if(first instanceof SchemeNumber){
      SchemeNumber value = ((SchemeNumber) (SchemeUtils.car(args)));
      args = SchemeUtils.cdr(args);
      SchemeNumber target = ((SchemeNumber) (SchemeUtils.car(args)));
      return SchemeBoolean.of(value.value() == target.value());
    } else if(first instanceof SchemeString){
      SchemeString value = ((SchemeString) first);
      args = SchemeUtils.cdr(args);
      SchemeString target = ((SchemeString) SchemeUtils.car(args));
      return SchemeBoolean.of(value.value.equals(target.value));
    } else if(first instanceof SchemeBoolean){
      SchemeBoolean value = ((SchemeBoolean) first);
      args = SchemeUtils.cdr(args);
      return SchemeBoolean.of(value.value == ((SchemeBoolean) SchemeUtils.car(args)).value);
    } else{
      args = SchemeUtils.cdr(args);
      return SchemeBoolean.of(first == SchemeUtils.car(args));
    }
  }
}