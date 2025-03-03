package com.danielasfregola.tutorial.cat.monad

import com.danielasfregola.tutorial.cat.applicative.Applicative

// See solution at https://gist.github.com/DanielaSfregola/ddf48f6c5638f6284b563798c55d5ebd

trait Monad[Box[_]] extends Applicative[Box] {

  def flatMap[A, B](boxA: Box[A])(f: A => Box[B]): Box[B]

  def flatten[A](boxBoxA: Box[Box[A]]): Box[A] = 
    flatMap(boxBoxA)(a => a) 
  

  override def ap[A, B](boxFn: Box[A => B])(boxA: Box[A]): Box[B] = 
    flatMap(boxFn)(fn => map(boxA)(fn))

  override def map[A, B](boxA: Box[A])(fn: A => B): Box[B] = 
    flatMap(boxA)(a => pure(fn(a)))

}
