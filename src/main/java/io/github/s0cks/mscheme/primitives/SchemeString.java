package io.github.s0cks.mscheme.primitives;

public final class SchemeString
implements SchemeObject{
  public final String value;

  public SchemeString(String value){
    this.value = value;
  }

  @Override
  public String toString() {
    return this.value;
  }
}