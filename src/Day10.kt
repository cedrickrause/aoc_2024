fun main() {
    fun part1(input: List<String>): Int {
        val grid = getGrid(input)

        var ans = 0

        for (i in grid.indices) {
            for (j in grid.first().indices) {
                val pos = Pair(i, j)
                if (grid[pos.first][pos.second] == 0) {
                    ans += calculateTrailheadScore(pos, 0, grid).size
                }
            }
        }

        return ans
    }

    fun part2(input: List<String>): Int {
        val grid = getGrid(input)

        var ans = 0

        for (i in grid.indices) {
            for (j in grid.first().indices) {
                val pos = Pair(i, j)
                if (grid[pos.first][pos.second] == 0) {
                    ans += calculateTrailheadRating(pos, 0, grid)
                }
            }
        }

        return ans
    }

    val testInput = readInput("Day10_test")
    check(part1(testInput) == 36)
    check(part2(testInput) == 81)

    val input = readInput("Day10")
    part1(input).println()
    part2(input).println()
}
private fun getGrid(input: List<String>) =
    input.map { line -> line.split("").filter { it.isNotEmpty() }.map { it.toInt() } }


private fun calculateTrailheadScore(position: Pair<Int, Int>, value: Int, grid: List<List<Int>>): MutableSet<Pair<Int,Int>> {
    if (value == 9) return mutableSetOf(position)
    val goals = mutableSetOf<Pair<Int,Int>>()

    if (position.first - 1 >= 0 && grid[position.first - 1][position.second] == value+1)
        goals.addAll(calculateTrailheadScore(Pair(position.first-1, position.second), value+1, grid))
    if (position.first + 1 < grid.size && grid[position.first + 1][position.second] == value+1)
        goals.addAll(calculateTrailheadScore(Pair(position.first+1, position.second), value+1, grid))
    if (position.second - 1 >= 0 && grid[position.first][position.second - 1] == value+1)
        goals.addAll(calculateTrailheadScore(Pair(position.first, position.second-1), value+1, grid))
    if (position.second + 1 < grid.first().size && grid[position.first][position.second + 1] == value+1)
        goals.addAll(calculateTrailheadScore(Pair(position.first, position.second+1), value+1, grid))
    return goals
}


private fun calculateTrailheadRating(position: Pair<Int, Int>, value: Int, grid: List<List<Int>>): Int {
    if (value == 9) return 1
    var count = 0

    if (position.first - 1 >= 0 && grid[position.first - 1][position.second] == value+1)
        count += calculateTrailheadRating(Pair(position.first-1, position.second), value+1, grid)
    if (position.first + 1 < grid.size && grid[position.first + 1][position.second] == value+1)
        count += calculateTrailheadRating(Pair(position.first+1, position.second), value+1, grid)
    if (position.second - 1 >= 0 && grid[position.first][position.second - 1] == value+1)
        count += calculateTrailheadRating(Pair(position.first, position.second-1), value+1, grid)
    if (position.second + 1 < grid.first().size && grid[position.first][position.second + 1] == value+1)
        count += calculateTrailheadRating(Pair(position.first, position.second+1), value+1, grid)
    return count
}