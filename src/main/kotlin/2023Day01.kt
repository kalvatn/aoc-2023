import tools.getResourceAsText
import tools.timer

fun main() = timer {
  val stringToNum = mapOf(
    "one" to "o1e",
    "two" to "t2o",
    "three" to "t3e",
    "four" to "4",
    "five" to "f5e",
    "six" to "s6x",
    "seven" to "s7n",
    "eight" to "e8t",
    "nine" to "n9e",
  )
  val lines = getResourceAsText("01.txt").lines()

  timer {
    val part1 = lines.map { line ->
      line.find { it.isDigit() }!!.digitToIntOrNull() to line.findLast { it.isDigit() }!!
        .digitToIntOrNull()
    }.sumOf { (first, last) ->
      "$first$last".toInt()
    }
    assert(part1 == 54708)
    println(part1)
  }
  timer {
    val part2 = lines.map { line ->
      var new = line
      stringToNum.entries.forEach { (sVal, repl) ->
        new = new.replace(sVal, repl)
      }
      new
    }.map { line ->
      line.find { it.isDigit() }!!.digitToIntOrNull() to line.findLast { it.isDigit() }!!
        .digitToIntOrNull()
    }.sumOf { (first, last) ->
      "$first$last".toInt()
    }
    assert(part2 == 54087)
    println(part2)
  }
}
