package io.github.s0cks.mscheme.primitives;

public final class SchemeNull
implements SchemeObject{
  public static SchemeNull instance = new SchemeNull();

  private SchemeNull(){}

  @Override
  public String toString() {
    return "'()";
  }
}