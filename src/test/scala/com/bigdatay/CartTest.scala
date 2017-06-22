package com.bigdatay

import com.bigdatay.filter.Offers
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{BeforeAndAfter, FunSuite, Matchers}

import scala.collection.concurrent.TrieMap
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

@RunWith(classOf[JUnitRunner])
class CartTest extends FunSuite with BeforeAndAfter with Matchers {

  private val inventoryList = TrieMap("Apple" -> BigDecimal(0.60), "Orange" -> BigDecimal(0.25))

  private val orderList = new ArrayBuffer[String] with mutable.SynchronizedBuffer[String]
  orderList +=("Apple", "Orange")

  val cart = new Cart(inventoryList)

  test("Test the price per ordered item") {

    val list = cart.getOrderPricing(orderList).asInstanceOf[ArrayBuffer[BigDecimal]].toList
    assert(list == List(0.6, 0.25))
  }

  test("Test total cost") {

    assert(cart.getTotal(orderList) == 0.85)
  }

  test("Test total cost after discounts applied") {

    val offers = new Offers(Map(("Apple" -> 2), ("Orange" -> 3)))
    assert(cart.getTotal(offers.current(orderList)) == 0.85)
  }
}
