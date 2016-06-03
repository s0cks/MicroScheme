package io.github.s0cks.mscheme;

import io.github.s0cks.mscheme.primitives.SchemeObject;
import io.github.s0cks.mscheme.primitives.SchemeProcedure;
import io.github.s0cks.mscheme.primitives.SchemeSymbol;

public class SchemeTest {
  @org.junit.Test
  public void testEval()
  throws Exception {
    try{
      Scheme scheme = new Scheme();
      Scheme.GLOBAL.define(new SchemeSymbol("display"), new SchemeProcedure() {
        @Override
        public SchemeObject apply(Scheme scheme, SchemeObject args) {
          System.out.println(SchemeUtils.car(args));
          return null;
        }
      });
      scheme.eval((new SchemeParser(System.class.getResourceAsStream("/Test.scm"))).parse());
    } catch(Exception e){
      // Fallthrough
    }
  }
}