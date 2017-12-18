package com.vdshb.tests.lift_emulator

import java.text.ParseException

object Interface {
    fun askingCommands(lift: Lift) {
        println("""
            To push hall lift button enter letter H with floor number after it (Example: H23 - emulates push to hall button at 23 floor)
            To push cabin lift button enter letter C with floor number after it (Example: C1 - emulates push to button "1" inside the lift)
        """.trimIndent())
        while (true) {
            try {
                val command = UserCommand(readLine()?.toUpperCase())
                if (command.command == UserCommandType.HALL) lift.pressOuterLiftButton(command.floor)
                if (command.command == UserCommandType.CABIN) lift.pressInnerLiftButton(command.floor)
                if (command.command == UserCommandType.EXIT) {
                    println("Stopping lift simulator...")
                    lift.stop()
                    break
                }
            } catch (e: ParseException) {
                println("Unacceptable command")
            }
        }
    }
}

class UserCommand(commandString: String?) {

    val command: UserCommandType
    val floor: Int

    init {
        if (commandString == null) {
            throw ParseException("Wrong command", 0)
        }
        command =
                if (commandString == "E") {
                    UserCommandType.EXIT
                } else
                if (commandString == "") {
                    throw ParseException("Empty command is unacceptable", 0)
                } else
                    when (commandString.substring(0, 1)) {
                        "H" -> UserCommandType.HALL
                        "C" -> UserCommandType.CABIN
                        else -> throw ParseException("Wrong command", 0)
                    }
        floor = if (commandString == "E") {
            0
        } else {
            try {
                commandString.substring(1).toInt()
            } catch (e: NumberFormatException) {
                throw ParseException("Wrong floor number", 0)
            }
        }
    }
}


enum class UserCommandType {
    HALL, CABIN, EXIT
}