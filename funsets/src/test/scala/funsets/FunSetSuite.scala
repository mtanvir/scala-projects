package funsets

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * This class is a test suite for the methods in object FunSets. To run
 * the test suite, you can either:
 *  - run the "test" command in the SBT console
 *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
 */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {

  /**
   * Link to the scaladoc - very clear and detailed tutorial of FunSuite
   *
   * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
   *
   * Operators
   *  - test
   *  - ignore
   *  - pending
   */

  /**
   * Tests are written using the "test" operator and the "assert" method.
   */
  ignore("string take") {
    val message = "hello, world"
    assert(message.take(5) == "hello")
  }

  /**
   * For ScalaTest tests, there exists a special equality operator "===" that
   * can be used inside "assert". If the assertion fails, the two values will
   * be printed in the error message. Otherwise, when using "==", the test
   * error message will only say "assertion failed", without showing the values.
   *
   * Try it out! Change the values so that the assertion fails, and look at the
   * error message.
   */
  ignore("adding ints") {
    assert(1 + 2 === 3)
  }

  import FunSets._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   *   val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)    
  }

  /**
   * Tests for singletonSet function.
   */
  test("singletonSet(1) contains 1") {

    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3".
     */
    new TestSets {
      assert(contains(s1, 1), "Singleton failed. Missing value 1")
    }
  }

  /**
   * Tests for union function.
   */
  test("union contains all elements") {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union failed. Missing value 1")
      assert(contains(s, 2), "Union failed. Missing value 2")
      assert(!contains(s, 3), "Union failed. Unexpected value 3")
    }
  }

  /**
   * Tests for intersect function.
   */
  test("intersect set contains only common elements") {
    new TestSets {
      val s = intersect(s1, union(s1, s2))
      assert(contains(s, 1), "Intersect failed. Missing value 1")
      assert(!contains(s, 2), "Intersect failed. Unexpected value 2")
    }
  }

  /**
   * Tests for diff function.
   */
  test("diff set contains only non-common elements") {
    new TestSets {
      val s = diff(union(s1, s2), s1)
      assert(contains(s, 2), "diff failed. Missing value 2")
      assert(!contains(s, 1), "diff failed. Unexpected value 1")
    }
  }
  
  /**
   * Tests for filter function
   */
  
  test("filtered set") {
    new TestSets {
      val s = union(union(s1, s2), s3)
      val fs = filter(s, (y: Int) => y < 3)
      assert(contains(fs, 1), "Filter failed. Missing value 1")
      assert(contains(fs, 2), "Filter failed. Missing value 2")
      assert(!contains(fs, 3), "Filter failed. Unexpected value 3")
    }
  }
  
  /**
   * Tests for forall function
   */
  test("forall") {
    new TestSets {
      val s = union(union(s1, s2), s3)
      assert(!forall(s, (y: Int) => y > 2), "Forall failed. > 2")
      assert(forall(s, (y: Int) => y > 0), "Forall failed. > 0")
      assert(!forall(s, (y: Int) => y < 3), "Forall failed. < 3")
    }
  }
  
  /**
   * Tests for exists function
   */
  test("exists") {
    new TestSets {
      val s = union(union(s1, s2), s3)
      assert(exists(s, (y: Int) => y > 2), "Exists failed. > 2")
      assert(!exists(s, (y: Int) => y > 3), "Exists failed. > 3")
      assert(exists(s, (y: Int) => y == 1), "Exists failed. == 1")
    }
  }
  
  /**
   * Tests for map function
   */
  test("map") {
    new TestSets {
      val s = map(union(union(s1, s2), s3), x => 2 * x)
      assert(contains(s, 4), "Map failed. Missing value 4")
      assert(!contains(s, 1), "Map failed. Unexpected value 1")
      assert(!contains(s, 3), "Map failed. Unexpected value 3")
      assert(forall(s, (y: Int) => y >= 2), "Map failed. Unexpected value(s)")
    }
  }  
}
