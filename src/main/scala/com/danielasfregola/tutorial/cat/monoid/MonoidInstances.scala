package com.danielasfregola.tutorial.cat.monoid

// See solution at https://gist.github.com/DanielaSfregola/ddf48f6c5638f6284b563798c55d5ebd
/** 
    trait Monoid[A] {
      def identity: A
      def compose(x: A, y: A): A
    }
 */
object MonoidInstances {

  implicit val intMonoid: Monoid[Int] = new Monoid[Int] {
    override def identity: Int = 0
    override def compose(x: Int, y: Int): Int = x + y
    // warning: thinking out loud follows! likely incorrect at this stage of the learning process!
    // why addition? because it's a **Mono**id? mono as in one as in the most basic case? so therefore it's a sum type?
    // a tuple would be a pair which is a product type? because there are many?
    // hmmm, so a monoid is like a factory for making typed set elements?
    // ...therefore its' identity = 0 + the-int-we-want-to-build ??
  }

  implicit val stringMonoid: Monoid[String] = new Monoid[String] {
    override def identity: String = ""
    override def compose(x: String, y: String): String = x ++ y
    // string is obvious. I mean; you don't multiply a string!
  }

}
