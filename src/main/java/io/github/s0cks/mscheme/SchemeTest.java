package io.github.s0cks.mscheme;

import io.github.s0cks.mscheme.primitives.SchemeObject;

public class SchemeTest {
  @org.junit.Test
  public void testEval()
  throws Exception {
    Scheme scheme = new Scheme();
    Environment env = new Environment();

    SchemeObject prog = (new SchemeParser(System.class.getResourceAsStream("/Test.scm"))).parse();
    System.out.println(scheme.eval(prog, env));
  }
}