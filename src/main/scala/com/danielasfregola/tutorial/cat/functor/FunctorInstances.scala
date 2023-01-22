package com.danielasfregola.tutorial.cat.functor

import com.danielasfregola.tutorial.cat._

// See solution at https://gist.github.com/DanielaSfregola/ddf48f6c5638f6284b563798c55d5ebd

/** 
    trait Functor[Box[_]] {
      def map[A, B](boxA: Box[A])(f: A => B): Box[B]
    }
 */
object FunctorInstances {

  implicit val maybeFunctor: Functor[Maybe] = new Functor[Maybe] {
    override def map[A, B](boxA: Maybe[A])(fn: A => B): Maybe[B] = boxA match {
      case Just(a) => Just(fn(a))
      case Empty => Empty
    }
  }

  /**
    sealed abstract class ZeroOrMore[+A] {
      def append[B >: A](other: ZeroOrMore[B]): ZeroOrMore[B]
    }

    final case class OneOrMore[A](head: A, tail: ZeroOrMore[A]) extends ZeroOrMore[A] {
      def append[B >: A](other: ZeroOrMore[B]): ZeroOrMore[B] =
        OneOrMore(head, tail.append(other))
    }

    case object Zero extends ZeroOrMore[Nothing] {
      def append[B](other: ZeroOrMore[B]): ZeroOrMore[B] = other
    }
  */

  // https://docs.scala-lang.org/tour/tour-of-scala.html
  // what's the syntax for lists in scala?? append tail to head? cons head to tail?
  implicit val zeroOrMoreFunctor: Functor[ZeroOrMore] = new Functor[ZeroOrMore] {
    override def map[A, B](boxA: ZeroOrMore[A])(fn: A => B): ZeroOrMore[B] = boxA match {
      case OneOrMore(a, tail) => OneOrMore(fn(a), map(tail)(fn))
      case Zero => Zero
    }
  }

}
