import java.util.concurrent.ConcurrentHashMap

import com.bigdatay.Cart
import com.bigdatay.filter.Offers
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FeatureSpec, GivenWhenThen, Matchers}

import scala.collection.JavaConverters._
import scala.collection.concurrent.TrieMap
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

@RunWith(classOf[JUnitRunner])
class CartFeaturesTest extends FeatureSpec with GivenWhenThen with Matchers {

  private val inventoryList = TrieMap("Apple" -> BigDecimal(0.60),"Orange" -> BigDecimal(0.25))

  private val orderList = new ArrayBuffer[String] with mutable.SynchronizedBuffer[String]
  orderList +=("Apple", "Orange", "Orange","Orange","Apple")

  val offers = new Offers(Map(("Apple" -> 2), ("Orange" -> 3)))

  val cart = new Cart(inventoryList)

  feature("ShoppingCart object") {

    scenario("Create the cart object with loaded items") {

      Given("ShoppingCart object is created and price list has retrieved")

      Then("the itemized list is created")

      val priceList = cart.getOrderPricing(ArrayBuffer("Apple", "Orange"))
      priceList should be(ArrayBuffer(0.60,0.25))

      Then("the total amount")

      cart.getTotal(ArrayBuffer("Apple", "Orange")) should be(0.85)

      Then("the extra item should be set")

      val newList = ArrayBuffer("Apple", "Apple", "Orange", "Apple")
      cart.getOrderPricing(newList) should be(ArrayBuffer(0.60, 0.60, 0.25, 0.60))

      Then("the last order total amount")

      cart.getTotal(newList) should be(2.05)

      Then("buy one get one free & three for the price two")

      cart.getTotal(offers.current(ArrayBuffer("Apple", "Apple", "Orange", "Orange", "Orange"))) should be(1.10)

    }
  }
}