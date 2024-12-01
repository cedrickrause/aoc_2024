import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val (leftNumbers, rightNumbers) = getLists(input)

        val differences = leftNumbers.sorted()
            .zip(rightNumbers.sorted())
            .map { abs(it.first - it.second) }

        return differences.reduce { acc, next -> acc + next }
    }

    fun part2(input: List<String>): Int {
        var similarityScore = 0
        val (leftNumbers, rightNumbers) = getLists(input)

        leftNumbers.forEach { left ->
            rightNumbers.forEach { right ->
                if (left == right) similarityScore += left
            }
        }

        return similarityScore
    }

    fun part2_2(input: List<String>): Int {
        val (leftNumbers, rightNumbers) = getLists(input)

        return leftNumbers.fold(0) { acc, next ->
            acc + rightNumbers.fold(0) { innerAcc, innerNext -> if (innerNext == next) innerAcc + innerNext else innerAcc }
        }
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 11)
    check(part2(testInput) == 31)
    check(part2_2(testInput) == 31)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
    part2_2(input).println()
}

private fun getLists(input: List<String>): Pair<List<Int>, List<Int>> {
    val rawPairs = input.map { line -> line.split(" ").filter { it.isNotBlank() }.map { it.trim().toInt() } }
    return Pair(rawPairs.map { it.first() }, rawPairs.map { it.last() })
}
