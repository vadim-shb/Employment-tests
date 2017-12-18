package com.vdshb.tests.lift_emulator

import java.math.BigDecimal

class LiftDriver(private val liftConfiguration: LiftConfiguration) {

    private val oneFloorTime= (BigDecimal(liftConfiguration.floorHeight) / liftConfiguration.liftSpeed).toLong()

    fun moveOneFloorUp() {
        Thread.sleep(oneFloorTime)
    }

    fun moveOneFloorDown() {
        Thread.sleep(oneFloorTime)
    }

    fun openDoor() {
        Thread.sleep(liftConfiguration.liftDoorTime)
    }

}