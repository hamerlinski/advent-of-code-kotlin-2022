package day02

import readInput
import java.lang.Exception

fun main() {
    fun part1() {
        val input = readInput("Day02", "day02")
        val inputIterator = input.iterator()
        var solution = 0
        inputIterator.forEach {
            val myTacticWeapon = Tactic(it[2].toString())
            val match = Match(it[0].toString(), myTacticWeapon.decision)
            val points = ScoreCalculation(myTacticWeapon.decision, match.result())
            solution += points.value
        }
        println(solution)
    }

    fun part2() {
        val input = readInput("Day02", "day02")
        val inputIterator = input.iterator()
        var solution = 0
        inputIterator.forEach {
            val tactic = TrueTactic(it[2].toString())
            val myWeapon = MyWeapon(it[0].toString(), tactic.decision)
            val points = ScoreCalculation(myWeapon.result(), tactic.decision)
            solution += points.value
        }
        println(solution)
    }
    part1()
    part2()
}

class ScoreCalculation(shape: String, outcome: String) {
    private val weaponPoints: Int = Awarding(shape).points
    private val resultsPoints: Int = Awarding(outcome).points
    val value: Int = weaponPoints.plus(resultsPoints)
}

class Awarding(value: String) {
    private val pointsSystem: Map<String, Int> = mapOf(
        "A" to 1, // rock > scissors    A > C
        "B" to 2, // paper > rock       B > A
        "C" to 3, // scissors > paper   C > B
        "lose" to 0,
        "draw" to 3,
        "win" to 6
    )
    val points = pointsSystem[value] ?: 0
}

class Tactic(value: String) {
    private val approach: Map<String, String> = mapOf(
        "X" to "A",
        "Y" to "B",
        "Z" to "C"
    )
    val decision: String = approach[value] ?: "A"
}

class TrueTactic(value: String) {
    private val approach: Map<String, String> = mapOf(
        "X" to "lose",
        "Y" to "draw",
        "Z" to "win"
    )
    val decision: String = approach[value] ?: "lose"
}

class Match(private val opponentWeapon: String, private val myWeapon: String) {
    fun result(): String {
        if (opponentWeapon == "A" && myWeapon == "A" || opponentWeapon == "B" && myWeapon == "B" || opponentWeapon == "C" && myWeapon == "C")
            return "draw"
        return if (opponentWeapon == "A" && myWeapon == "C" || opponentWeapon == "B" && myWeapon == "A" || opponentWeapon == "C" && myWeapon == "B")
            "lose"
        else "win"
    }
}

class MyWeapon(private val opponentWeapon: String, private val desiredOutcome: String) {
    fun result(): String {
        if (opponentWeapon == "A" && desiredOutcome == "draw" || opponentWeapon == "B" && desiredOutcome == "lose" || opponentWeapon == "C" && desiredOutcome == "win")
            return "A"
        if (opponentWeapon == "B" && desiredOutcome == "draw" || opponentWeapon == "C" && desiredOutcome == "lose" || opponentWeapon == "A" && desiredOutcome == "win")
            return "B"
        if (opponentWeapon == "C" && desiredOutcome == "draw" || opponentWeapon == "A" && desiredOutcome == "lose" || opponentWeapon == "B" && desiredOutcome == "win")
            return "C"
        else {
            throw Exception()
        }
    }
}