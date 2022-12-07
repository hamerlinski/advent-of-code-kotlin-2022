package day05

import readInput

fun main() {
    fun part1(stacks: SupplyStacks) {
        stacks.executeInstructions()
        println("solution1:")
        stacks.printAllLastCrates()
    }

    fun part2(stacks: SupplyStacks) {
        stacks.executeInstructionsOnCrateMover9001()
        println("solution2:")
        stacks.printAllLastCrates()
    }

    val input = readInput("Day05", "day05")
    val parsedInput = ParsedInput(input)
    val supplyStacks = SupplyStacks(parsedInput.stacks(), parsedInput.instructions())
    val supplyStacksCopy = SupplyStacks(parsedInput.stacks(), parsedInput.instructions())

    part1(supplyStacks)
    part2(supplyStacksCopy)
}


class ParsedInput(private val input: List<String>) {
    private val splitLine: Int = emptyLineIndex()
    private val regex = Regex("^' '{4}")
    private fun emptyLineIndex(): Int {
        var index = -1
        for (line in input) {
            index += 1
            if (line == "") break
        }
        return index
    }

    fun stacks(): MutableList<Stack> {
        val iterator = input.listIterator(splitLine)
        val numberOfStacks = iterator.previous().split("   ").size
        val containers: MutableList<Stack> = mutableListOf()
        for (i in 1..numberOfStacks) {
            val tempCrateList: MutableList<Crate> = mutableListOf()
            val tempStack = Stack(tempCrateList)
            containers.add(tempStack)
        }
        while (iterator.hasPrevious()) {
            val cratesRow = iterator.previous()
                .replace(regex, "[0] ")
                .replace("    ", " [0]")
                .replace("[", "")
                .replace("]", "")
                .split(" ")
            val cratesRowIterator = cratesRow.listIterator()
            var rowIndex = 0
            while (cratesRowIterator.hasNext()) {
                val tempCrateValue = cratesRowIterator.next()
                if (tempCrateValue != "0") {
                    val tempCrate = Crate(tempCrateValue)
                    containers[rowIndex].addCrate(tempCrate)
                }
                rowIndex += 1
            }
        }
        return containers
    }

    fun instructions(): MutableList<Instruction> {
        val iterator = input.listIterator()
        for (i in 0..splitLine) { // set iterator to first lane with crates
            iterator.next()
        }
        val instructions: MutableList<Instruction> = mutableListOf()
        while (iterator.hasNext()) {
            val tempInstruction = Instruction(iterator.next())
            instructions.add(tempInstruction)
        }

        return instructions
    }


}

class SupplyStacks(
    private val stacks: MutableList<Stack>,
    private val instructions: MutableList<Instruction>
) {

    fun printAllLastCrates() {
        stacks.iterator().forEach {
            it.printLastCrate()
        }
        println()
    }

    fun executeInstructions() {
        instructions.listIterator().forEach {
            for (i in 1..it.amountOfCratesToMove()) {
                stacks[it.fromStack() - 1].moveOneCrate(stacks[it.toStack() - 1])
            }
        }
    }

    fun executeInstructionsOnCrateMover9001() {
        instructions.listIterator().forEach {
            stacks[it.fromStack() - 1].moveMultipleCrates(
                it.amountOfCratesToMove(),
                stacks[it.toStack() - 1]
            )
        }
    }
}

class Stack(private val crates: MutableList<Crate>) {
    fun moveOneCrate(toStack: Stack) {
        val crateToMove = crates[crates.lastIndex]
        toStack.crates.add(crateToMove)
        crates.removeAt(crates.lastIndex)
    }

    fun moveMultipleCrates(amount: Int, toStack: Stack) {
        for (i in amount downTo 1 step 1) {
            val crateToMove = crates[crates.lastIndex - i + 1]
            toStack.crates.add(crateToMove)
            crates.removeAt(crates.lastIndex - i + 1)
        }
    }

    fun printLastCrate() {
        print(crates[crates.lastIndex].value())
    }

    fun addCrate(crate: Crate) {
        crates.add(crate)
    }

}

class Crate(private val name: String) {
    fun value(): String {
        return name
    }
}

class Instruction(input: String) {
    private val inputList: List<String> = input.split(" ")
    fun amountOfCratesToMove(): Int {
        return inputList[1].toInt()
    }

    fun fromStack(): Int {
        return inputList[3].toInt()
    }

    fun toStack(): Int {
        return inputList[5].toInt()
    }
}
