import tools.getResourceAsText
import tools.timer

fun main() = timer {

  val testInput = """
    Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
    Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
    Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
    Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
    Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
  """.trimIndent()

  val (RED, GREEN, BLUE) = listOf("red", "green", "blue")

  var part1 = 0

  getResourceAsText("02.txt").lines()
//  testInput.lines()
    .map { line ->
      val (game, sets) = line.split(":")
      val gameNumber = game.split(" ").last().toInt()
      var possible = true
      sets.split(";").forEach { set ->
        val counts = mutableMapOf(
          RED to 0,
          GREEN to 0,
          BLUE to 0,
        )
        val cubes = set.split(",").map { it.trim() }
        cubes.forEach { cube ->
          val (num, color) = cube.split(" ").map { it.trim() }
          counts[color.trim()] = counts[color.trim()]!! + num.toInt()
        }

        if (
          counts[RED]!! > 12 ||
          counts[GREEN]!! > 13 ||
          counts[BLUE]!! > 14
        ) {
          possible = false
        }
      }
      if (possible) {
        part1 += gameNumber
      }
    }
  println(part1)

  var part2 = 0
  getResourceAsText("02.txt").lines()
//  testInput.lines()
    .map { line ->
      val (game, sets) = line.split(":")
      val gameNumber = game.split(" ").last().toInt()
      val counts = mutableMapOf<String, MutableList<Int>>(
        RED to mutableListOf(),
        GREEN to mutableListOf(),
        BLUE to mutableListOf(),
      )
      sets.split(";").forEach { set ->
        val cubes = set.split(",").map { it.trim() }
        cubes.forEach { cube ->
          val (num, color) = cube.split(" ").map { it.trim() }
          counts[color.trim()]!!.add(num.toInt())
        }
      }
      part2 += counts.values.map { it.max() }.reduce { acc, value ->
        acc * value
      }
    }
  println(part2)
}
