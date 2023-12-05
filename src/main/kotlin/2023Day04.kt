import tools.getResourceAsText
import tools.timer
import java.util.Stack
import kotlin.math.max
import kotlin.math.min

fun main() = timer {

  val input = getResourceAsText("04.txt")
  val testInput = """
    Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
    Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
    Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
    Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
    Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
    Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11
  """.trimIndent()

//  val cards = testInput.lines()
  val cards = input.lines()

  val re = Regex("""Card (.*): (.*) \| (.*)$""")
  var part1 = 0
  val memoCard = mutableMapOf<Int, Int>()
  cards.map { line ->
    println(line)
    val (number, winningRaw, ownedRaw) = re.matchEntire(line)!!.destructured.toList().map { it.trim() }

    val winning = winningRaw.split(" ").filterNot { it.isBlank() }.map { it.toInt() }
    val owned = ownedRaw.split(" ").filterNot { it.isBlank() }.map { it.toInt() }

    val matches = winning.filter { owned.contains(it) }
    val pointList = mutableListOf<Int>()
    var points = 1
    repeat(matches.size) {
      pointList.add(points)
      points *= 2
    }
    memoCard[number.toInt()] = pointList.size
    part1 += pointList.lastOrNull() ?: 0

  }

  val queue = cards.mapIndexed { idx, card ->
    1
  }.toMutableList()
  println(queue)
  queue.mapIndexed { idx, copiesOfCard ->
    repeat(copiesOfCard) {
      val line = cards[idx]
      val (numberRaw, winningRaw, ownedRaw) = re.matchEntire(line)!!.destructured.toList().map { it.trim() }
      val number = numberRaw.toInt()

      val copies = memoCard[number]!!
      val rangeMax = min(number + copies, cards.size.dec())
      (number.inc()..rangeMax).map {
        queue[it.dec()] = queue[it.dec()].inc()
      }

    }
    println()

  }

  println(queue.mapIndexed { idx, copies ->
    copies
  }.sum())
}

