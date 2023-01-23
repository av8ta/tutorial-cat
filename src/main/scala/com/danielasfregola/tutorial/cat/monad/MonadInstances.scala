package com.danielasfregola.tutorial.cat.monad

import com.danielasfregola.tutorial.cat._
import com.danielasfregola.tutorial.cat.applicative.ApplicativeInstances._

// See solution at https://gist.github.com/DanielaSfregola/ddf48f6c5638f6284b563798c55d5ebd

object MonadInstances {

  implicit val maybeMonad: Monad[Maybe] = new Monad[Maybe] {
    def pure[A](a: A): Maybe[A] = maybeApplicative.pure(a)

    // def flatMap[A, B](boxA: Box[A])(f: A => Box[B]): Box[B]
    override def flatMap[A, B](boxA: Maybe[A])(fn: A => Maybe[B]): Maybe[B] = boxA match {
      case Empty => Empty
      case Just(a) => fn(a)
    }

  }
  implicit val zeroOrMoreMonad: Monad[ZeroOrMore] = new Monad[ZeroOrMore] {
    def pure[A](a: A): ZeroOrMore[A] = zeroOrMoreApplicative.pure(a)
    /**
        implicit val zeroOrMoreFunctor: Functor[ZeroOrMore] = new Functor[ZeroOrMore] {
          override def map[A, B](boxA: ZeroOrMore[A])(fn: A => B): ZeroOrMore[B] = boxA match {
            case OneOrMore(a, tail) => OneOrMore(fn(a), map(tail)(fn))
            case Zero => Zero
          }
        }
    */
    override def flatMap[A, B](boxA: ZeroOrMore[A])(fn: A => ZeroOrMore[B]): ZeroOrMore[B] = boxA match {
      case Zero => Zero
      case OneOrMore(headA, tail) => fn(headA).append(flatMap(tail)(fn))
    }
  }

}
