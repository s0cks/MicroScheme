package io.github.s0cks.mscheme.primitives;

public final class SchemeNumber
implements SchemeObject{
  private double value;

  public SchemeNumber(double value){
    this.value = value;
  }

  public double value(){
    return this.value;
  }

  public SchemeNumber add(SchemeObject obj){
    if(!(obj instanceof SchemeNumber)) throw new IllegalStateException("Not a number: " + obj);
    return new SchemeNumber(this.value + ((SchemeNumber) obj).value);
  }

  public SchemeNumber subtract(SchemeObject obj){
    if(!(obj instanceof SchemeNumber)) throw new IllegalStateException("Not a number: " + obj);
    return new SchemeNumber(this.value - ((SchemeNumber) obj).value);

  }

  public SchemeNumber multiply(SchemeObject obj){
    if(!(obj instanceof SchemeNumber)) throw new IllegalStateException("Not a number: " + obj);
    return new SchemeNumber(this.value * ((SchemeNumber) obj).value);
  }

  public SchemeNumber divide(SchemeObject obj){
    if(!(obj instanceof SchemeNumber)) throw new IllegalStateException("Not a number: " + obj);
    return new SchemeNumber(this.value / ((SchemeNumber) obj).value);
  }

  @Override
  public String toString() {
    return this.value + "";
  }
}