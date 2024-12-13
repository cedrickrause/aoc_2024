fun main() {
    fun part1(input: List<String>): Int {
        val grid = input.map { line -> line.split("").filter { it.isNotEmpty() }.toMutableList() }.toMutableList()

        var ans = 0

        grid.indices.flatMap { lineIndex ->
            grid.first().indices.map { colIndex ->
                if (grid[lineIndex][colIndex] != ".") {
                    val (area, perimeter) = handleRegion(lineIndex, colIndex, grid)
                    ans += area * perimeter
                }
            }
        }

        return ans
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("Day12_test")
    check(part1(testInput) == 1930)
//    check(part2(testInput) == 1206)

    val input = readInput("Day12")
    part1(input).println()
    part2(input).println()
}

private fun handleRegion(row: Int, col: Int, grid: MutableList<MutableList<String>>): Pair<Int, Int> {
    var area = 1
    var perimeter = getAddedPerimeter(row, col, grid)
    val plotName = grid[row][col]

    grid[row][col] = "current"

    if (row - 1 >= 0 && grid[row-1][col] == plotName) {
        val (newArea, newPerimeter) = handleRegion(row-1, col, grid)
        area += newArea
        perimeter += newPerimeter
    }
    if (row + 1 < grid.size && grid[row+1][col] == plotName) {
        val (newArea, newPerimeter) = handleRegion(row+1, col, grid)
        area += newArea
        perimeter += newPerimeter
    }
    if (col - 1 >= 0 && grid[row][col-1] == plotName) {
        val (newArea, newPerimeter) = handleRegion(row, col-1, grid)
        area += newArea
        perimeter += newPerimeter
    }
    if (col + 1 < grid.size && grid[row][col+1] == plotName) {
        val (newArea, newPerimeter) = handleRegion(row, col+1, grid)
        area += newArea
        perimeter += newPerimeter
    }

    grid[row][col] = "."

    return Pair(area, perimeter)
}

private fun getAddedPerimeter(row: Int, col: Int, grid: List<List<String>>): Int {
    val plotName = grid[row][col]
    var addedPerimeter = 0
    if (row - 1 < 0 || grid[row-1][col] !in listOf(plotName, "current")) addedPerimeter += 1
    if (row + 1 >= grid.size || grid[row+1][col] !in listOf(plotName, "current")) addedPerimeter += 1
    if (col - 1 < 0 || grid[row][col-1] !in listOf(plotName, "current")) addedPerimeter += 1
    if (col + 1 >= grid.size || grid[row][col+1] !in listOf(plotName, "current")) addedPerimeter += 1
    return addedPerimeter
}
