fun main() {
    fun part1(input: List<String>): Int {
        val pattern = Regex("mul\\([0-9]{1,3},[0-9]{1,3}\\)")
        val matches = input.map { pattern.findAll(it) }
            .flatMap { matchResults -> matchResults.map { it.value } }

        val products = matches.map { it.split("(", ",", ")") }.map { it[1].toInt() * it[2].toInt() }
        return products.reduce { acc, next -> acc + next }
    }

    fun part2(input: List<String>): Int {
        val sections = input.joinToString("")
            .split("do()")
            .map { it.split("don't()").first() }

        return part1(sections)
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 161)
    check(part2(testInput) == 48)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
