package io.github.s0cks.mscheme.primitives;

public final class SchemeSymbol
implements SchemeObject{
  public final String value;

  public SchemeSymbol(String value){
    this.value = value;
  }

  @Override
  public String toString() {
    return this.value;
  }
}