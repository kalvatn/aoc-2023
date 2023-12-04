import common.Dir
import common.Point2D
import tools.getResourceAsText
import tools.timer
import java.lang.IllegalStateException

fun main() = timer {

  val testInput = """
    467..114..
    ...*......
    ..35..633.
    ......#...
    617*......
    .....+.58.
    ..592.....
    ......755.
    ...${'$'}.*....
    .664.598..
  """.trimIndent().lines()

//  val grid = testInput
  val grid = getResourceAsText("03.txt").lines()
  val length = grid[0].length

  val partCoords = mutableListOf<Point2D>()
  val yBound = grid.indices
  val xBound = 0 until length
  for (y in yBound) {
    for (x in xBound) {
      assert(grid[y][x] != ' ')

      if (!grid[y][x].isDigit() && grid[y][x] != '.') {
        partCoords.add(Point2D(y, x))
      }
    }
  }
  val adjToPart = mutableListOf<Point2D>()
  val adjToGear = mutableMapOf<Point2D, List<Point2D>>()
  partCoords.forEach { p2d ->
    val (y,x)= p2d
    println("($y, $x) == ${grid[y][x]}")

    adjToPart += p2d.adj8(yBound, xBound).filter {
      grid[it.y][it.x].isDigit()
    }

    if (grid[y][x] == '*') {
      adjToGear[p2d] = p2d.adj8(yBound, xBound).filter {
        grid[it.y][it.x].isDigit()
      }
    }
  }

  val seen = mutableListOf<Point2D>()
  val iter = adjToPart.listIterator()
  val numbers = mutableListOf<Int>()
  while(iter.hasNext()) {
    val p2d = iter.next()
    if (!seen.contains(p2d)) {
      seen.add(p2d)
      val line = grid[p2d.y]
      val start = findStart(line, p2d.x)
      val end = findEnd(line, p2d.x)
      numbers.add(line.substring(start, end.inc()).toInt())
      seen.addAll((start .. end).map {
        Point2D(p2d.y, it)
      })
    }
  }
  println(numbers.sum())
  var part2 = 0
  adjToGear.forEach { (gearPoint, adj) ->
    val seen2 = mutableListOf<Point2D>()
    val iter2 = adj.listIterator()
    val numbers2 = mutableListOf<Int>()
    println(grid[gearPoint.y][gearPoint.x])
    while(iter2.hasNext()) {
      val p2d = iter2.next()
      if (!seen2.contains(p2d)) {
        seen2.add(p2d)
        val line = grid[p2d.y]
        val start = findStart(line, p2d.x)
        val end = findEnd(line, p2d.x)
        numbers2.add(line.substring(start, end.inc()).toInt())
        seen2.addAll((start .. end).map {
          Point2D(p2d.y, it)
        })
      }
    }
    if (numbers2.size == 2) {
      val ratio = numbers2[0] * numbers2[1]
      println("$numbers2 $ratio")
      part2 += ratio
    }
  }

  println(part2)

}

fun findStart(line:String, index:Int):Int {
  for (i in index downTo 0) {
    if (!line[i].isDigit()) {
      return i+1
    }
  }
  return 0
}
fun findEnd(line:String, index:Int):Int {
  for (i in index until line.length) {
    if (!line[i].isDigit()) {
      return i-1
    }
  }
  return line.length.dec()
}
