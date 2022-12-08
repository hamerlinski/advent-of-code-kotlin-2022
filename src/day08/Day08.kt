package day08

import readInput

fun main() {
    val input = readInput("Day08", "day08")
    val forest = Forest(input)
    forest.plant()
    println(forest.numOfVisibleTrees())
    println(forest.highestScenicScore())
}

@Suppress("DuplicatedCode")
class Forest(private val input: List<String>) {
    private val trees: MutableList<MutableList<Tree>> = mutableListOf()
    fun plant() {
        input.listIterator().forEach { row ->
            val treesRow: MutableList<Tree> = mutableListOf()
            row.iterator().forEach { treeInfo ->
                val tree = Tree(treeInfo.toString().toInt())
                treesRow.add(tree)
            }
            trees.add(treesRow)
        }
    }

    fun numOfVisibleTrees(): Int {
        val forestWidth = trees[0].size
        val forestHeight = trees.size
        var visibleTrees = forestWidth * 2 + forestHeight * 2 - 4
        for (i in 1 until trees.size - 1) {
            for (j in 1 until trees[i].size - 1) {
                var westernTreeIndex = j - 1
                var northernTreeIndex = i - 1
                var easternTreeIndex = j + 1
                var southernTreeIndex = i + 1
                var checkedAndVisible = false
                while (westernTreeIndex >= 0) {
                    if (westernTreeIndex == 0 && trees[i][j].height() > trees[i][westernTreeIndex].height()) {
                        visibleTrees += 1
                        checkedAndVisible = true
                        break
                    } else if (trees[i][j].height() > trees[i][westernTreeIndex].height()) {
                        westernTreeIndex -= 1
                    } else break
                }
                while ((northernTreeIndex >= 0) && !checkedAndVisible) {
                    if (northernTreeIndex == 0 && trees[i][j].height() > trees[northernTreeIndex][j].height()) {
                        visibleTrees += 1
                        checkedAndVisible = true
                        break
                    } else if (trees[i][j].height() > trees[northernTreeIndex][j].height()) {
                        northernTreeIndex -= 1
                    } else break
                }
                while ((easternTreeIndex <= forestWidth - 1) && !checkedAndVisible) {
                    if (easternTreeIndex == (forestWidth - 1) && trees[i][j].height() > trees[i][forestWidth - 1].height()) {
                        visibleTrees += 1
                        checkedAndVisible = true
                        break
                    } else if (trees[i][j].height() > trees[i][easternTreeIndex].height()) {
                        easternTreeIndex += 1
                    } else break
                }
                while ((southernTreeIndex <= forestHeight - 1) && !checkedAndVisible) {
                    if (southernTreeIndex == (forestHeight - 1) && trees[i][j].height() > trees[forestHeight - 1][j].height()) {
                        visibleTrees += 1
                        break
                    } else if (trees[i][j].height() > trees[southernTreeIndex][j].height()) {
                        southernTreeIndex += 1
                    } else break
                }
            }
        }
        return visibleTrees

    }

    fun highestScenicScore(): Int {
        val forestWidth = trees[0].size
        val forestHeight = trees.size
        var score = 0
        for (i in 1 until trees.size - 1) {
            for (j in 1 until trees[i].size - 1) {
                var westernTreeIndex = j - 1
                var northernTreeIndex = i - 1
                var easternTreeIndex = j + 1
                var southernTreeIndex = i + 1
                var westViewScore = 0
                var northViewScore = 0
                var eastViewScore = 0
                var southViewScore = 0
                while (westernTreeIndex >= 0) {
                    if (trees[i][j].height() > trees[i][westernTreeIndex].height()) {
                        westViewScore += 1
                        westernTreeIndex -= 1
                    } else if (trees[i][j].height() <= trees[i][westernTreeIndex].height()) {
                        westViewScore += 1
                        westernTreeIndex -= 1
                        break
                    } else break
                }
                while (northernTreeIndex >= 0) {
                    if (trees[i][j].height() > trees[northernTreeIndex][j].height()) {
                        northViewScore += 1
                        northernTreeIndex -= 1
                    } else if (trees[i][j].height() <= trees[northernTreeIndex][j].height()) {
                        northViewScore += 1
                        northernTreeIndex -= 1
                        break
                    } else break
                }
                while (easternTreeIndex <= forestWidth - 1) {
                    if (trees[i][j].height() > trees[i][easternTreeIndex].height()) {
                        eastViewScore += 1
                        easternTreeIndex += 1
                    } else if (trees[i][j].height() <= trees[i][easternTreeIndex].height()) {
                        eastViewScore += 1
                        easternTreeIndex += 1
                        break
                    } else break
                }
                while (southernTreeIndex <= forestHeight - 1) {
                    if (trees[i][j].height() > trees[southernTreeIndex][j].height()) {
                        southViewScore += 1
                        southernTreeIndex += 1
                    } else if (trees[i][j].height() <= trees[southernTreeIndex][j].height()) {
                        southViewScore += 1
                        southernTreeIndex += 1
                        break
                    } else break
                }
                val potentialScore = westViewScore * northViewScore * eastViewScore * southViewScore
                if (potentialScore > score) {
                    score = potentialScore
                }
            }
        }
        return score
    }
}

class Tree(private val height: Int) {
    fun height(): Int {
        return this.height
    }
}
