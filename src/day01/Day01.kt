package day01

import readInput

fun main() {
    fun prepareElves(input: List<String>): Elves {
        val inputIterator = input.iterator();
        val elvesList = mutableListOf<Elf>()
        val temporaryList = mutableListOf<Int>()
        inputIterator.forEach {
            if (it == "") {
                val tempElf = Elf(temporaryList)
                elvesList.add(tempElf)
                temporaryList.clear()

            } else temporaryList.add(it.toInt())
        }
        return Elves(elvesList)
    }

    fun part1(elves: Elves): Int {
        return elves.maxCalories
    }

    fun part2(elves: Elves): Int {
        return elves.top3ElvesMaxCalories
    }

    val day01input = readInput("Day01", "day01")
    val allElves = prepareElves(day01input)
    println(part1(allElves))
    println(part2(allElves))
}

class Elf(caloriesList: List<Int>) {
    val sumOfCalories = caloriesList.sum()
}

class Elves(elves: List<Elf>) {
    val maxCalories = elves.maxOf { it.sumOfCalories }
    val top3ElvesMaxCalories =
        elves.sortedByDescending { it.sumOfCalories }.subList(0, 3).sumOf { it.sumOfCalories }
}