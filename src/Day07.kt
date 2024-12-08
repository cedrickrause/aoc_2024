fun main() {
    fun part1(input: List<String>): Long {
        val solutionToNumbers = getSolutionsToNumbers(input)

        val correctCalibrationsSolutions = solutionToNumbers.findSolutionsBasedOn(
            { a, b -> a + b },
            { a, b -> a * b },
        ).keys

        return correctCalibrationsSolutions.reduce { acc, next -> acc + next }
    }

    fun part2(input: List<String>): Long {
        val solutionToNumbers = getSolutionsToNumbers(input)

        val correctCalibrationsSolutions = solutionToNumbers.findSolutionsBasedOn(
            { a, b -> a + b },
            { a, b -> a * b },
            { a, b -> (a.toString() + b.toString()).toLong() }
        ).keys

        return correctCalibrationsSolutions.reduce { acc, next -> acc + next }
    }

    val testInput = readInput("Day07_test")
    check(part1(testInput) == 3749L)
    check(part2(testInput) == 11387L)

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}

private fun getSolutionsToNumbers(input: List<String>): Map<Long, List<Long>> {
    val split = input.map { line -> line.split(": ") }
    val rawSolutions = split.map { it.first().toLong() }
    val rawNumbers = split.map { line -> line.last().split(" ").map { it.toLong() } }
    val solutionToNumbers = rawSolutions.zip(rawNumbers).toMap()
    return solutionToNumbers
}

private fun Map<Long, List<Long>>.findSolutionsBasedOn(vararg operations: (Long, Long) -> Long) =
    this.filter { (solution, numbers) ->
        var results = listOf<Long>()
        numbers.forEach { number ->
            results =
                if (results.isEmpty()) listOf(number)
                else results.map { prev -> operations.map { it(prev, number) } }.flatten()
        }
        solution in results
    }
