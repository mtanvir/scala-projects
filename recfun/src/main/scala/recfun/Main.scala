package recfun
import common._

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int = {

    if (c == 0 || r == c) {
      1
    } else {
      val x = pascal(c - 1, r - 1)
      val y = pascal(c, r - 1)
      x + y
    }
  }

  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean = {

    def balanceWithCount(text: List[Char], count: Int): Boolean = {
      var c = 0
      if (text.isEmpty) {
        count == 0
      } else {
        val ch = text.head
        if (ch == '(') {
          c = count + 1
        } else if (ch == ')') {
          c = count - 1
        } else {
          c = count
        }
        //println(text.tail)       
        //println(c)
        if (c >= 0) {
          balanceWithCount(text.tail, c)
        } else {
          false
        }
      }
    }

    balanceWithCount(chars, 0)
  }

  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int = {
    if (money == 0) {
      1
    } else if (money < 0 || coins.isEmpty) {
      0
    } else {
      val x = countChange(money - coins.head, coins)
      val y = countChange(money, coins.tail)
      x + y
    }
  }
}
