fun main() {
    fun part1(input: List<String>): Int {
        var stones = getStones(input)

        for (i in 0..24) {
            stones = blink(stones)
        }
        return stones.size
    }

    fun part2(input: List<String>): Int {
        var stones = getStones(input)

        for (i in 0..74) {
            stones = fakeBlink(stones)
        }
        return stones.size
    }

    val testInput = readInput("Day11_test")
    check(part1(testInput) == 55312)

    val input = readInput("Day11")
    part1(input).println()
    part2(input).println()
}
fun getStones(input: List<String>): List<Long> {
    return input.map { line -> line.split(" ") }.first()
        .filter { it.isNotEmpty() }.map { it.toLong() }
}

private fun blink(stones: List<Long>): List<Long> {
    return stones.flatMap { stone ->
        if (stone == 0L) return@flatMap listOf(1L)
        if (stone.toString().length % 2 == 0) {
            return@flatMap listOf(
                stone.toString().substring(0, stone.toString().length / 2).toLong(),
                stone.toString().substring(stone.toString().length / 2).toLong(),
            )
        }
        else return@flatMap listOf(stone * 2024)
    }
}

private fun fakeBlink(stones: List<Long>): List<Long> {
    return stones.flatMap { stone ->
        if (stone == 0L) return@flatMap listOf(1L)
        if (stone.toString().length % 2 == 0) {
            return@flatMap listOf(
                stone.toString().substring(0, stone.toString().length / 2).toLong(),
                stone.toString().substring(stone.toString().length / 2).toLong(),
            )
        }
        else return@flatMap listOf(stone * 2024)
    }
}