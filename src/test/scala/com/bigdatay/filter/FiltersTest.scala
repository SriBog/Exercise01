package com.bigdatay.filter

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{BeforeAndAfter, FunSuite, Matchers}

import scala.collection.mutable.ArrayBuffer

@RunWith(classOf[JUnitRunner])
class FiltersTest extends FunSuite with BeforeAndAfter with Matchers {

  val offers = new Offers(Map(("Apple" -> 2), ("Orange" -> 3)))


  test("Offers applied on one each product in basket even though we've a few Limes and Melons (noting should be changed)") {

    val order = ArrayBuffer("Apple", "Apple", "Orange", "Orange", "Orange")
    var afterOffer = offers.current(order)
    assert(afterOffer == ArrayBuffer("Apple", "Orange", "Orange"))
  }

  test("Two Apples as buy one get one free (it should remove one Apple from the ArrayBuffer) ") {

    val order = ArrayBuffer("Apple", "Apple")
    var afterOffer = offers.current(order)
    assert(afterOffer == ArrayBuffer("Apple"))
  }

  test("Three Oranges for price of two (final ArrayBuffer should have two Oranges)") {

    val order = ArrayBuffer("Orange", "Orange", "Orange")
    var afterOffer = offers.current(order)
    assert(afterOffer == ArrayBuffer("Orange", "Orange"))
  }

  test("Two Apples as buy one get one free and three Oranges for price of two (final ArrayBuffer should have one Apple and one Orange)") {

    val order = ArrayBuffer("Orange", "Orange", "Orange", "Apple", "Apple" )
    var afterOffer = offers.current(order)
    assert(afterOffer == ArrayBuffer("Orange", "Orange", "Apple"))
  }

  test("Testing empty ArrayBuffer ") {
    assert(offers.current(ArrayBuffer()) == ArrayBuffer())
  }

}
