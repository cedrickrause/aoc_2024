fun main() {
    fun part1(input: List<String>): Int {
        val grid = input.map { line -> line.split("").filter { it.isNotEmpty() } }
        val nameToPositions = getNameToPositions(grid)
        val boundaries = (grid.indices to grid.first().indices)

        return nameToPositions.flatMap { entry ->
            entry.value.flatMap { location ->
                entry.value.filter { location != it }.map { otherLocation ->
                    val difference = (location.first - otherLocation.first) to (location.second - otherLocation.second)
                    (location.first + difference.first) to (location.second + difference.second)
                }
            }
        }
            .filter { it.within(boundaries) }
            .toSet()
            .size
    }

    fun part2(input: List<String>): Int {
        val grid = input.map { line -> line.split("").filter { it.isNotEmpty() } }
        val nameToPositions = getNameToPositions(grid)
        val boundaries = (grid.indices to grid.first().indices)

        return nameToPositions.flatMap { entry ->
            entry.value.flatMap { location ->
                entry.value.filter { location != it }.flatMap { otherLocation ->
                    val difference = (location.first - otherLocation.first) to (location.second - otherLocation.second)
                    (grid.indices).map { i -> (location.first + i*difference.first) to (location.second + i*difference.second) }
                }
            }
        }
            .filter { it.within(boundaries) }
            .toSet()
            .size
    }

    val testInput = readInput("Day08_test")
    check(part1(testInput) == 14)
    check(part2(testInput) == 34)

    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}

private fun Pair<Int, Int>.within(boundaries: Pair<IntRange, IntRange>) =
    this.first in boundaries.first && this.second in boundaries.second

private fun getNameToPositions(grid: List<List<String>>): Map<String, List<Pair<Int, Int>>> {
    return grid
        .mapIndexed { rowIndex, row -> row.mapIndexed { colIndex, col -> col to Pair(rowIndex, colIndex) } }
        .flatten()
        .groupBy({ it.first }, { it.second })
        .filter { it.key != "." }
}
