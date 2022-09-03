package com.yyok.share.nlp.text

object TestDo {
  def main1(args: Array[String]) {
    var a = 10;
    var b = 20;
    var c = 25;
    var d = 25;
    println("a + b = " + (a + b));
    println("a - b = " + (a - b));
    println("a * b = " + (a * b));
    println("b / a = " + (b / a));
    println("b % a = " + (b % a));
    println("c % a = " + (c % a));

  }

  def main(args: Array[String]) {
    var x = 10;

    if (x < 20) {
      println("x < 20");
    }

    println(aa(5,6));
  }

    def aa(x: Int, y: Int):Int = {
        var sun = x * y
      return sun
    }


  def addInt(a: Int, b: Int): Int = {
    var sum: Int = 0
    sum = a + b

    return sum
  }

}
