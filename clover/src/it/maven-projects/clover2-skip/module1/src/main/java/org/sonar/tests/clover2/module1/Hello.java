package org.sonar.tests.clover2.module1;

public class Hello {
  private String hello;

  public Hello(String s){
    this.hello = s;
  }

  public String say() {
    return hello;
  }
}
