package day04

import readInput

fun main() {
    val input = readInput("Day04", "day04")
    val inputIterator = input.iterator()
    var solution1 = 0
    var solution2 = 0
    fun part1and2(input: Iterator<String>) {
        input.forEach {
            val elvesAssignmentRanges = it.split(",")
            val elf0 = Elf(elvesAssignmentRanges[0])
            val elf1 = Elf(elvesAssignmentRanges[1])
            val pair = Pair(elf0, elf1)
            if (pair.overlappedCompletely()) {
                solution1 += 1
            }
            if (pair.overlappedPartially()) {
                solution2 += 1
            }
        }
        println("solution1: $solution1")
        println("solution2: $solution2")
    }

    part1and2(inputIterator)
}

class Elf(private val assignmentRange: String) {
    fun assignment(): MutableList<Int> {
        val assignmentList: MutableList<Int> = mutableListOf()
        val first: Int = assignmentRange.split("-")[0].toInt()
        val second: Int = assignmentRange.split("-")[1].toInt()
        for (i in first..second) {
            assignmentList.add(i)
        }
        return assignmentList
    }
}

class Pair(private val elf0: Elf, private val elf1: Elf) {
    fun overlappedCompletely(): Boolean {
        return (elf0.assignment().contains(elf1.assignment()[0]) && elf0.assignment()
            .contains(elf1.assignment()[elf1.assignment().lastIndex])
                || elf1.assignment().contains(elf0.assignment()[0]) && elf1.assignment()
            .contains(elf0.assignment()[elf0.assignment().lastIndex]))
    }

    fun overlappedPartially(): Boolean {
        return (elf0.assignment().contains(elf1.assignment()[0]) || elf0.assignment()
            .contains(elf1.assignment()[elf1.assignment().lastIndex])
                || elf1.assignment().contains(elf0.assignment()[0]) || elf1.assignment()
            .contains(elf0.assignment()[elf0.assignment().lastIndex]))

    }
}