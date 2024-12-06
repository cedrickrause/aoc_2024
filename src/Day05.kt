fun main() {
    fun part1(input: List<String>): Int {
        val (rules, updates) = getRulesAndUpdates(input)

        val correctUpdates = getUpdatesWhereCorrectnessIs(true, updates, rules)

        return sumCenterValues(correctUpdates)
    }

    fun part2(input: List<String>): Int {
        val (rules, updates) = getRulesAndUpdates(input)

        val incorrectUpdates = getUpdatesWhereCorrectnessIs(false, updates, rules)

        incorrectUpdates.forEach { update ->
            for (i in 0..<update.size) {
                for (j in 0..<i) {
                    rules.forEach { rule ->
                        if (update[i] == rule.first && update[j] == rule.second) {
                            val temp = update[i]
                            update[i] = update[j]
                            update[j] = temp
                        }
                    }
                }
            }
        }

        return sumCenterValues(incorrectUpdates)
    }

    val testInput = readInput("Day05_test")
    check(part1(testInput) == 143)

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}

private fun getRulesAndUpdates(input: List<String>): Pair<List<Pair<Int, Int>>, List<MutableList<Int>>> {
    val (rawRules, rawUpdates) = input.partition { it.contains("|") }
    val rules = rawRules
        .map { it.split("|") }
        .map { Pair(it.first().toInt(), it.last().toInt()) }

    val updates = rawUpdates.filter { it.isNotEmpty() }
        .map { line -> line.split(",").map { it.toInt() }.toMutableList() }
    return Pair(rules, updates)
}

private fun sumCenterValues(updates: List<MutableList<Int>>) =
    updates.map { it[(it.size - 1) / 2] }.reduce { acc, next -> acc + next }


private fun getUpdatesWhereCorrectnessIs(
    correctness: Boolean,
    updates: List<MutableList<Int>>,
    rules: List<Pair<Int, Int>>
): List<MutableList<Int>> {
   return updates.filter { update ->
        val disallowed = mutableSetOf<Int>()
        update.forEach { number ->
            if (number in disallowed) return@filter !correctness
            rules.forEach { rule ->
                if (rule.second == number) {
                    disallowed.add(rule.first)
                }
            }
        }
        return@filter correctness
    }
}
