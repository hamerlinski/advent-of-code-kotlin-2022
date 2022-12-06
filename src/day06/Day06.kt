package day06

import readInput

fun main() {
    val input = readInput("Day06", "day06")
    val communication = Communication(input[0])
    println(communication.markerLength())
    println(communication.messageMarkerLength())
}


class Communication(private val incomingDataStream: String) {
    fun markerLength(): Int {
        var markerLen = 4
        for (i in 1..incomingDataStream.length - 3) {
            val sequence = Sequence(incomingDataStream.subSequence(i - 1, i + 3))
            if (sequence.indicative()) {
                break
            }
            markerLen += 1
        }
        return markerLen
    }

    fun messageMarkerLength(): Int {
        var markerLen = 14
        for (i in 1..incomingDataStream.length - 13) {
            val sequence = Sequence(incomingDataStream.subSequence(i - 1, i + 13))
            if (sequence.indicative()) {
                break
            }
            markerLen += 1
        }
        return markerLen
    }
}

class Sequence(private val input: CharSequence) {
    fun indicative(): Boolean = input.all(hashSetOf<Char>()::add)
}