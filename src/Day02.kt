fun main() {
    fun part1(input: List<String>): Int {
        val reports = getReports(input)
        return reports.filter { report -> isReportSafe(report) }.size
    }

    fun part2(input: List<String>): Int {
        val reports = getReports(input)
        return reports.filter { report ->
            val candidates = listOf(report, *List(report.size) { index -> report.copyWithoutIndex(index) }.toTypedArray())

            candidates.any { isReportSafe(it) }
        }.size
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 5)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}

private fun getReports(input: List<String>): List<List<Int>> {
    return input.map { line -> line.split(" ").map { number -> number.toInt() } }
}

private fun isReportSafe(report: List<Int>): Boolean {
    val ascending = report.first() < report[1]

    report.forEachIndexed { index, level ->
        if (index == 0) return@forEachIndexed

        if (ascending) {
            if (!IntRange(report[index - 1] + 1, report[index - 1] + 3).contains(level)) return false
        } else {
            if (!IntRange(report[index - 1] - 3, report[index - 1] - 1).contains(level)) return false
        }
    }
    return true
}

private fun List<Int>.copyWithoutIndex(indexToDrop: Int): List<Int> {
    return this.toList().filterIndexed { index, _ -> index != indexToDrop}
}