package com.danielasfregola.tutorial.cat.applicative

import com.danielasfregola.tutorial.cat._

// See solution at https://gist.github.com/DanielaSfregola/ddf48f6c5638f6284b563798c55d5ebd

/** 
  trait Applicative[Box[_]] extends Functor[Box] {
    def pure[A](a: A): Box[A]
    def ap[A, B](boxF: Box[A => B])(boxA: Box[A]): Box[B]
    override def map[A, B](boxA: Box[A])(f: A => B): Box[B] = 
      ap[A, B](pure(f))(boxA)
  }
*/

object ApplicativeInstances {

  implicit val maybeApplicative: Applicative[Maybe] = new Applicative[Maybe] {
    // def pure[A](a:A): Box[A]
    override def pure[A](a:A): Maybe[A] = Just(a)

    override def ap[A, B](boxF: Maybe[A => B])(boxA: Maybe[A]): Maybe[B] = (boxF, boxA) match {
      case (Just(f), Just(a)) => pure(f(a))
      case _ => Empty
    }
  }

  implicit val zeroOrMoreApplicative: Applicative[ZeroOrMore] = new Applicative[ZeroOrMore] {

    override def pure[A](a:A): ZeroOrMore[A] = OneOrMore(a, Zero)

    override def ap[A, B](boxFn: ZeroOrMore[A => B])(boxA: ZeroOrMore[A]): ZeroOrMore[B] = (boxFn, boxA) match {

      case (OneOrMore(headFn, headFns), OneOrMore(headA, tailA)) => OneOrMore(headFn(headA), ap(boxFn)(tailA))
      // a.zip(b) this implementation is wrong! should be applying all the functions to all the Box[A]s !
      // todo: zip the two lists together and then apply each function and A of each pair
      case _ => Zero

    }
  }

}
