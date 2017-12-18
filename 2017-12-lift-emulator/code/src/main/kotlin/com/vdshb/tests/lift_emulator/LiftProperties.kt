package com.vdshb.tests.lift_emulator

import java.math.BigDecimal
import java.util.concurrent.atomic.AtomicBoolean

data class LiftConfiguration(
        val floorsQuantity: Int,
        val floorHeight: Int,           // height in mm
        val liftSpeed: BigDecimal,      // speed in mm/ms = m/s
        val liftDoorTime: Long          // time in ms
)

data class Floor(val number: Int, val isInRoute: AtomicBoolean)

enum class Direction {
    UP, DOWN
}

enum class Action {
    STAY, MOVE, OPEN
}