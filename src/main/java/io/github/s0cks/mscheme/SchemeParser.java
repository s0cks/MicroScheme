package io.github.s0cks.mscheme;

import io.github.s0cks.mscheme.primitives.SchemeBoolean;
import io.github.s0cks.mscheme.primitives.SchemeNull;
import io.github.s0cks.mscheme.primitives.SchemeNumber;
import io.github.s0cks.mscheme.primitives.SchemeObject;
import io.github.s0cks.mscheme.primitives.SchemeString;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

public final class SchemeParser
implements Closeable {
  private final InputStream in;
  private char peek = '\0';

  public SchemeParser(InputStream in){
    this.in = in;
  }

  public SchemeObject parse()
  throws Exception{
    char c = this.peek();
    if(c == '('){
      return this.parseTail();
    } else{
      return this.nextObject();
    }
  }

  private SchemeObject parseTail()
  throws Exception{
    char c = this.next();
    if(c == ')'){
      return SchemeNull.instance;
    } else{
      return SchemeUtils.cons(this.parse(), this.parseTail());
    }
  }

  private SchemeObject nextObject()
  throws Exception{
    char c = this.nextReal();

    switch(c){
      case '#':{
        switch((c = this.next())){
          case 't':case 'T': return new SchemeBoolean(true);
          case 'f':case 'F': return new SchemeBoolean(false);
          default: return this.nextObject();
        }
      }
      case '(': return SchemeUtils.cons(this.parse(), this.parseTail());
      default:{
        if(Character.isAlphabetic(c) || c == '+' || c == '_' || c == '-'){
          String buffer = c + "";
          while (Character.isAlphabetic((c = this.peek())) || c == '+' || c == '_' || c == '-') {
            buffer += c;
            this.next();
          }

          return new SchemeString(buffer);
        } else if(Character.isDigit(c)){
          String buffer = c + "";
          boolean dbl = false;
          while(Character.isDigit(c = this.peek()) || (c == '.' && !dbl)){
            buffer += c;
            if(c == '.') dbl = true;
            this.next();
          }

          return new SchemeNumber(Double.valueOf(buffer));
        }
      }
    }

    throw new IllegalStateException("Unexpected token: " + c);
  }

  private char nextReal()
  throws IOException{
    char next;
    while(Character.isWhitespace(next = this.next()));
    return next;
  }

  private char next()
  throws IOException{
    int ret;
    if(this.peek == '\0'){
      ret = this.in.read();
    } else{
      ret = this.peek;
      this.peek = '\0';
    }

    if(ret == -1) return '\0';
    return (char) ret;
  }

  private char peek()
  throws IOException{
    if(this.peek != '\0'){
      return this.peek;
    }

    int peek = this.in.read();
    if(peek == -1){
      return '\0';
    } else{
      this.peek = (char) peek;
    }

    return this.peek;
  }

  @Override
  public void close()
  throws IOException {
    this.in.close();
  }
}