import kotlin.math.min

fun main() {
    fun part1(input: List<String>): Long {
        val split = input.first().split("").filter { it.isNotEmpty() }.map { it.toInt() }

        val files = split
            .filterIndexed { index, _ -> index % 2 == 0 }
            .flatMapIndexed { index, value -> (0..<value).map { index } }
            .toMutableList()

        val ans = split.flatMapIndexed { index, count ->
            if (index % 2 == 0) {
                (0..<min(count, files.size)).map { files.removeFirst() }
            } else {
                (0..<min(count, files.size)).map { files.removeLast() }
            }
        }

        return ans.foldIndexed(0) { index, acc, next -> acc + next * index }
    }

    fun part2(input: List<String>): Long {
        val split = input.first().split("").filter { it.isNotEmpty() }.map { it.toInt() }

        val files = split
            .filterIndexed { index, _ -> index % 2 == 0 }
            .mapIndexed { index, value -> (0..<value).map { index } }
            .toMutableList()

        val ans = split
            .mapIndexed { index, value ->
                if (index % 2 == 0) (0..<value).map { index / 2 }
                else (0..<value).map { 0 }
            }
            .map { it.toMutableList() }
            .toMutableList()

        files.reversed().forEach { block ->
//            println("----------------")
            val indexOfFit = ans.drop(1).indexOfFirst { it.size >= block.size && it.all { number -> number == 0 } } + 1
//            println(indexOfFit)
//            println(block)

            if (indexOfFit != 0) {
                ans.add(indexOfFit, block.toMutableList())
                ans[indexOfFit + 1] = ans[indexOfFit + 1].drop(block.size).toMutableList()
                ans[ans.lastIndexOf(block)] = MutableList(block.size) { 0 }
            }

//            println(ans)
        }

        return ans.flatten().foldIndexed(0) { index, acc, next -> acc + next * index }
    }

    val testInput = readInput("Day09_test")
    check(part1(testInput) == 1928L)
    check(part2(testInput) == 2858L)

    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}

private fun MutableList<Int>.replace(fromIndex: Int, replacement: List<Int>) {
    replacement.forEachIndexed { index, number ->
        this[fromIndex + index] = number
    }
}