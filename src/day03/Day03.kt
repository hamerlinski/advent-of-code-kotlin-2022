package day03

import readInput

fun main() {
    val input = readInput("Day03", "day03")
    val inputIterator = input.iterator()

    fun part1(inputIterator: Iterator<String>) {
        var part1Solution = 0
        inputIterator.forEach {
            val rucksack = Rucksack(it.toList())
            val priority = Priority(rucksack.errorsItem().filter { true }[0])
            part1Solution += priority.value()
        }
        println(part1Solution)

    }

    fun part2() {
        var part2solution = 0
        for (i in 0..input.lastIndex step 3) {
            val firstRucksack = Rucksack(input[i].toList())
            val secondRucksack = Rucksack(input[i + 1].toList())
            val thirdRucksack = Rucksack(input[i + 2].toList())
            val group = Group(firstRucksack, secondRucksack, thirdRucksack)
            val priority = Priority(group.badge())
            part2solution += priority.value()
        }
        println(part2solution)
    }
    part1(inputIterator)
    part2()
}

class Priority(private val item: Char) {
    fun value(): Int {
        return if (item.isLowerCase())
            item.code.minus(96)
        else item.code.minus(38)
    }
}

class Rucksack(items: List<Char>) {
    private val firstCompartment: List<Char> = items.subList(0, items.size.div(2))
    private val secondCompartment: List<Char> = items.subList(items.size.div(2), items.size)
    fun errorsItem(): Set<Char> {
        return firstCompartment.intersect(secondCompartment.toSet())
    }

    val allItemsSet: Set<Char> = items.toSet()
}

class Group(private val elf1: Rucksack, private val elf2: Rucksack, private val elf3: Rucksack) {
    fun badge(): Char {
        val elf1AndElf2: Set<Char> = elf1.allItemsSet.intersect(elf2.allItemsSet)
        val elf1AndElf3: Set<Char> = elf1.allItemsSet.intersect(elf3.allItemsSet)
        val elf2AndElf3: Set<Char> = elf2.allItemsSet.intersect(elf3.allItemsSet)
        val elf1elf2AndElf1Elf3: Set<Char> = elf1AndElf2.intersect(elf1AndElf3)
        return elf2AndElf3.intersect(elf1elf2AndElf1Elf3).filter { true }[0]
    }
}