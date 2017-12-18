package com.vdshb.tests.lift_emulator

object App {

    @JvmStatic
    fun main(args: Array<String>) {
        val liftConfiguration = try {
            getLiftConfiguration(args)
        } catch (e: Exception) {
            printConfigurationHelp()
            return
        }
        val lift = Lift(liftConfiguration, 1)
        lift.start()
        Interface.askingCommands(lift)
    }

    private fun printConfigurationHelp() {
        println(
                """Lack of app configuration. Please, enter the following parameters:
           --floors-quantity : Quantity of floors
           --floor-height : Height of floor (mm, integer)
           --lift-speed : Lift speed (m/s, float)
           --doors-open-close-time : Time to open, wait and close lift doors (ms, integer)

           example: --floors-quantity=10 --floor-height=2750 --lift-speed=1.3 --doors-open-close-time=2000"""
                        .trimIndent()
        )
    }

    private fun getLiftConfiguration(args: Array<String>): LiftConfiguration {
        return LiftConfiguration(
                floorsQuantity = getParam(args, "--floors-quantity").toInt(),
                floorHeight = getParam(args, "--floor-height").toInt(),
                liftSpeed = getParam(args, "--lift-speed").toBigDecimal(),
                liftDoorTime = getParam(args, "--doors-open-close-time").toLong()
        )
    }

    private fun getParam(args: Array<String>, paramName: String): String {
        return args
                .filter { it.length > paramName.length }
                .filter { it.substring(0, paramName.length) == paramName }
                .map { it.substring(it.indexOf('=') + 1) }
                .get(0)
    }
}