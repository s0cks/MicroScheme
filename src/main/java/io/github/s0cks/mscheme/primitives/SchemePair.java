package io.github.s0cks.mscheme.primitives;

public final class SchemePair
implements SchemeObject{
  public SchemeObject car;
  public SchemeObject cdr;

  public SchemePair(SchemeObject car, SchemeObject cdr) {
    this.car = car;
    this.cdr = cdr;
  }

  @Override
  public String toString() {
    return String.format("(%s, %s)", this.car.toString(), this.cdr.toString());
  }
}