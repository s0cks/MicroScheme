package io.github.s0cks.mscheme;

import io.github.s0cks.mscheme.primitives.SchemeBoolean;
import io.github.s0cks.mscheme.primitives.SchemeNull;
import io.github.s0cks.mscheme.primitives.SchemeNumber;
import io.github.s0cks.mscheme.primitives.SchemeObject;
import io.github.s0cks.mscheme.primitives.SchemeString;
import io.github.s0cks.mscheme.primitives.SchemeSymbol;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

public final class SchemeParser
implements Closeable {
  private static final Set<Character> validSymbols = new HashSet<>();

  private static void addSymbol(char c){
    validSymbols.add(c);
  }

  static{
    addSymbol('_');
    addSymbol('*');
    addSymbol('/');
    addSymbol('+');
    addSymbol('-');
    addSymbol('!');
    addSymbol('>');
    addSymbol('<');
    addSymbol('\'');
    addSymbol('?');
  }

  public static boolean isSymbol(char c){
    return validSymbols.contains(c);
  }

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
        switch(this.next()){
          case 't':case 'T': return new SchemeBoolean(true);
          case 'f':case 'F': return new SchemeBoolean(false);
          default: return this.nextObject();
        }
      }
      case ';':{
        while((c = this.next()) != '\n');
        return this.nextObject();
      }
      case '(': return SchemeUtils.cons(this.parse(), this.parseTail());
      default:{
        if(Character.isAlphabetic(c) || isSymbol(c)){
          String buffer = c + "";
          while (Character.isAlphabetic((c = this.peek())) || isSymbol(c)) {
            buffer += c;
            this.next();
          }

          return new SchemeSymbol(buffer);
        } else if(Character.isDigit(c)){
          String buffer = c + "";
          boolean dbl = false;
          while(Character.isDigit(c = this.peek()) || (c == '.' && !dbl)){
            buffer += c;
            if(c == '.') dbl = true;
            this.next();
          }

          return new SchemeNumber(Double.valueOf(buffer));
        } else if(c == '"'){
          String buffer = "";
          while((c = this.peek()) != '"'){
            if(c == '\\'){
              this.next();
              switch((c = this.next())){
                case 'n': buffer += "\n"; break;
                case 't': buffer += "\t"; break;
                case 'r': buffer += "\r"; break;
                default: buffer += "\\" + c; break;
              }
            } else{
              buffer += c;
              this.next();
            }
          }
          this.next();
          return new SchemeString(buffer);
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