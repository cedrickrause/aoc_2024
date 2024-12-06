import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val rows = input.map { line -> line.split("").filter { char -> char.isNotBlank() } }
        val cols = (0..<rows.first().size).map { col -> rows.map { it[col] } }

        val posToValMap = rows.indices.map { row ->
            rows.first().indices.map { col ->
                Pair(row, col) to rows[row][col]
            }
        }
            .flatten()
            .toMap()

        val diagonalsDown = (-(rows.size-1)..<rows.first().size).map { index -> posToValMap.filterKeys { key -> key.first == key.second - index }.values.toList() }
        val diagonalsUp = (0..<rows.size + rows.first().size).map { index -> posToValMap.filterKeys { key -> key.first + key.second == abs(index) }.values.toList() }
        val diagonals = (diagonalsUp + diagonalsDown).filter { it.size >= 4 }

        return countXmas(rows) + countXmas(rows.map { it.reversed() }) + countXmas(cols) + countXmas(cols.map { it.reversed() }) + countXmas(diagonals) + countXmas(diagonals.map { it.reversed() })
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 18)
    check(part2(testInput) == 9)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}

private fun countXmas(rows: List<List<String>>): Int {
    val pattern = "XMAS"
    return rows.map { it.joinToString("").split(pattern).size - 1 }.reduce { acc, next -> acc + next }
}