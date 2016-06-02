package io.github.s0cks.mscheme.primitives;

public final class SchemeBoolean
implements SchemeObject{
  public final boolean value;

  public SchemeBoolean(boolean value){
    this.value = value;
  }
}