fun main() {
    fun part1(input: List<String>): Int {
        var dir = Pair(-1, 0)
        val grid = input.map { line -> line.split("").filter { it.isNotEmpty() }.toMutableList() }.toMutableList()

        var pos = getStartingPosition(grid)

        while(true) {
            grid[pos.first][pos.second] = "1"
            when (nextStepValue(grid, pos, dir)) {
                "#" -> dir = changeDir(dir)
                null -> break
                else -> {
                    pos = move(pos, dir)
                }
            }
        }

        val gridOfNumberOfVisits = grid.map { row -> row.mapNotNull { col -> runCatching { col.toInt() }.getOrNull() } }.filter { it.isNotEmpty() }

        return gridOfNumberOfVisits.fold(0) { rowAcc, row -> rowAcc + row.reduce { colAcc, col -> colAcc + col } }
    }

    fun part2(input: List<String>): Int {
        val startingDir = Pair(-1, 0)
        val grid = input.map { line -> line.split("").filter { it.isNotEmpty() }.toMutableList() }.toMutableList()
        val startingPos = getStartingPosition(grid)

        var ans = 0

        for (i in grid.indices) {
            for (j in grid.first().indices) {
                val history = mutableSetOf(Pair(startingPos, startingDir))
                val adjustedGrid = grid.map { it.toMutableList() }.toMutableList()
                if (i != history.first().first.first || j != history.first().first.second) {
                    adjustedGrid[i][j] = "#"

                    while(true) {
                        val setSize = history.size
                        val pos = history.last().first
                        val dir = history.last().second
                        when (nextStepValue(adjustedGrid, pos, dir)) {
                            "#" -> history.add(Pair(pos, changeDir(dir)))
                            null -> break
                            else -> {
                                history.add(Pair(move(pos, dir), dir))
                            }
                        }
                        if (setSize == history.size) {
                            ans++
                            break
                        }
                    }
                }
            }
        }

        return ans
    }

    val testInput = readInput("Day06_test")
    check(part1(testInput) == 41)
    check(part2(testInput) == 6)

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}

private fun changeDir(currentDir: Pair<Int, Int>): Pair<Int, Int> {
     return when (currentDir) {
         Pair(-1, 0) -> Pair(0, 1)
         Pair(0, 1) -> Pair(1, 0)
         Pair(1, 0) -> Pair(0, -1)
         Pair(0, -1) -> Pair(-1, 0)
         else -> throw RuntimeException("Invalid currentDir")
     }
}

private fun nextStepValue(grid: List<List<String>>, currentPos: Pair<Int, Int>, dir: Pair<Int, Int>): String? {
    if (currentPos.first + dir.first < 0 || currentPos.first + dir.first >= grid.first().size || currentPos.second + dir.second < 0 || currentPos.second + dir.second >= grid.size) return null
    return grid[currentPos.first + dir.first][currentPos.second + dir.second]
}

private fun move(currentPos: Pair<Int, Int>, currentDir: Pair<Int, Int>): Pair<Int, Int> {
    return Pair(currentPos.first + currentDir.first, currentPos.second + currentDir.second)
}

private fun getStartingPosition(grid: MutableList<MutableList<String>>): Pair<Int, Int> {
    for (i in grid.indices) {
        for (j in grid.first().indices) {
            if (grid[i][j] == "^") {
                return Pair(i, j)
            }
        }
    }
    return Pair(-1, -1)
}