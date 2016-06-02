package io.github.s0cks.mscheme.primitives;

public final class SchemeNumber
implements SchemeObject{
  private double value;

  public SchemeNumber(double value){
    this.value = value;
  }

  public SchemeNumber add(SchemeObject obj){
    if(!(obj instanceof SchemeNumber)) throw new IllegalStateException("Not a number: " + obj);
    this.value += ((SchemeNumber) obj).value;
    return this;
  }

  public SchemeNumber subtract(SchemeObject obj){
    if(!(obj instanceof SchemeNumber)) throw new IllegalStateException("Not a number: " + obj);
    this.value -= ((SchemeNumber) obj).value;
    return this;
  }

  @Override
  public String toString() {
    return this.value + "";
  }
}