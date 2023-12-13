import tools.getResourceAsText
import tools.timer

fun main() = timer {

  val input = getResourceAsText("05.txt")
  val testInput = """
seeds: 79 14 55 13

seed-to-soil map:
50 98 2
52 50 48

soil-to-fertilizer map:
0 15 37
37 52 2
39 0 15

fertilizer-to-water map:
49 53 8
0 11 42
42 0 7
57 7 4

water-to-light map:
88 18 7
18 25 70

light-to-temperature map:
45 77 23
81 45 19
68 64 13

temperature-to-humidity map:
0 69 1
1 0 69

humidity-to-location map:
60 56 37
56 93 4
  """.trimIndent()

//  val chunks = testInput.split("\n\n")
  val chunks = input.split("\n\n")

  val seeds =
    chunks.first().split(":").last().split(" ").filterNot { it.isBlank() }.map { it.toLong() }
  val mappings = chunks.drop(1).map { chunk ->
    chunk.lines().drop(1)
      .map { mapping -> mapping.split(" ").filterNot { it.isBlank() }.map { it.toLong() } }
  }.toList()

  val maps = Map.values().associateWith { mutableMapOf<LongRange, LongRange>() }

  mappings.mapIndexed { index, ranges ->
    println("$index $ranges")
    ranges.forEach {
      val (source, dest, range) = it
      maps[Map.fromIndex(index)]!![LongRange(dest, dest + range)] = LongRange(source, source + range)
    }
  }

  val seedToLocation = seeds.associateWith { seed ->
    getLocation(seed, maps)
  }.toMutableMap()

  val part1 = seedToLocation.values.min()
  println(part1)
//  assert(part1 == 910845529L)

  val seeds2 = seeds.chunked(2).map { (start, range) ->
    LongRange(start, start+range)
  }

  seedToLocation.clear()

  seeds2.forEach { seedRange ->
    seedRange.forEach {
      if (!seedToLocation.contains(it)) {
        seedToLocation[it] = getLocation(it, maps)
      }
    }
  }
  val part2 = seedToLocation.values.min()
  println(part2)
}

private fun getLocation(
  seed: Long,
  maps: kotlin.collections.Map<Map, MutableMap<LongRange, LongRange>>
): Long {
  var lookup = seed
  Map.values().map { map ->
    val range = maps[map]!!.keys.firstOrNull { it.contains(lookup) }

    lookup = if (range != null) {
      val idx = range.indexOf(lookup)
      lookup = maps[map]!![range]!!.first + idx
      lookup
    } else {
      lookup
    }
    lookup
  }
  return lookup
}

enum class Map(val index: Int) {
  SEED_TO_SOIL(0),
  SOIL_TO_FERTILIZER(1),
  FERTILIZER_TO_WATER(2),
  WATER_TO_LIGHT(3),
  LIGHT_TO_TEMPERATURE(4),
  TEMPERATURE_TO_HUMIDITY(5),
  HUMIDITY_TO_LOCATION(6)
  ;

  companion object {
    fun fromIndex(index: Int) = Map.values().first { it.index == index }
  }
}
