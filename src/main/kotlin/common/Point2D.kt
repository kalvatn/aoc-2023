package common

data class Point2D(val y: Int, val x: Int) {

  operator fun plus(other: Point2D): Point2D =
    Point2D(this.y + other.y, this.x + other.x)

  fun adj(dir:Dir) =
    this + dir.diff

  fun adj4(yBound: IntRange, xBound: IntRange) = listOf(Dir.N, Dir.E, Dir.S, Dir.W).map {
    this + it.diff
  }.filter { it.y in yBound && it.x in xBound }

  fun adj8(yBound: IntRange, xBound: IntRange) = Dir.entries.map {
    this + it.diff
  }.filter { it.y in yBound && it.x in xBound }
}
