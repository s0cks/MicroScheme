package io.github.s0cks.mscheme.primitives;

public final class SchemeBoolean
implements SchemeObject{
  public static final SchemeBoolean TRUE = new SchemeBoolean(true);
  public static final SchemeBoolean FALSE = new SchemeBoolean(false);

  public final boolean value;

  public SchemeBoolean(boolean value){
    this.value = value;
  }

  public static SchemeBoolean of(boolean v){
    return v ? TRUE : FALSE;
  }

  @Override
  public String toString() {
    return this.value ? "true" : "false";
  }
}