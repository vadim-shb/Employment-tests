package com.vdshb.tests.lift_emulator

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.concurrent.thread
import kotlin.math.abs

class Lift(liftConfiguration: LiftConfiguration, startFloor: Int) {

    private var currentFloor = startFloor
    private var currentDirection = Direction.UP
    private var currentAction = Action.STAY

    private val floors = ConcurrentHashMap<Int, Floor>()
    private val isThreadStarted = AtomicBoolean(false)
    private var workerThread: Thread? = null
    private val liftDriver = LiftDriver(liftConfiguration)

    init {
        for (floor in 1..liftConfiguration.floorsQuantity) {
            floors.put(floor, Floor(floor, AtomicBoolean(false)))
        }
    }

    fun start() {
        workerThread = thread {
            while (isThreadStarted.get()) {
                act()
            }
            println("Lift has stopped working")
        }
        isThreadStarted.set(true)
        println("Lift has started working on the $currentFloor floor")
    }

    fun stop() {
        isThreadStarted.set(false)
    }

    fun pressOuterLiftButton(floorNumber: Int) {
        // We can not turn off pressed button
        addFloorToRoute(floorNumber)
    }

    fun pressInnerLiftButton(floorNumber: Int) {
        // We can not turn off pressed button
        addFloorToRoute(floorNumber)
    }

    private fun addFloorToRoute(floorNumber: Int) {
        val floor = floors[floorNumber]
        if (floor == null) {
            println("Wrong floor number")
        } else {
            floor.isInRoute.set(true)
        }
    }

    private fun act() {
        when (currentAction) {
            Action.STAY -> {
                Thread.sleep(10) // don't eat the processor while staying
                checkToStartMoving()
            }
            Action.MOVE -> {
                if (floorsInRoute().isEmpty()) {
                    currentAction = Action.STAY
                } else {
                    changeDirectionOnEdge()
                    moveToNextFloor()
                    checkToOpenDoor()
                }
            }
            Action.OPEN -> {
                openDoor()
            }
        }
    }

    private fun checkToStartMoving() {
        val destinationFloors = floorsInRoute()
        if (destinationFloors.isNotEmpty()) {
            val directionFloor = destinationFloors.reduce(::closestFloor)          // Let's choose the closest floor as destination
            currentAction = Action.MOVE
            currentDirection = if (directionFloor.number > currentFloor) Direction.UP else Direction.DOWN
        }
    }

    private fun closestFloor(floor1: Floor, floor2: Floor): Floor {
        val destination1 = abs(floor1.number - currentFloor)
        val destination2 = abs(floor2.number - currentFloor)
        return if (destination1 <= destination2) floor1 else floor2
    }

    private fun floorsInRoute() = floors.values.filter { it.isInRoute.get() }

    private fun changeDirectionOnEdge() {
        if (currentDirection == Direction.UP && floorsInRoute().none { it.number > currentFloor }) {
            currentDirection = Direction.DOWN
            return
        }
        if (currentDirection == Direction.DOWN && floorsInRoute().none { it.number < currentFloor }) {
            currentDirection = Direction.UP
        }
    }

    private fun moveToNextFloor() {
        when (currentDirection) {
            Direction.UP -> {
                liftDriver.moveOneFloorUp()
                currentFloor++
            }
            Direction.DOWN -> {
                liftDriver.moveOneFloorDown()
                currentFloor--
            }
        }
        println("Lift is on the $currentFloor floor")
    }

    private fun checkToOpenDoor() {
        if (floors[currentFloor]!!.isInRoute.get()) {
            currentAction = Action.OPEN
        }
    }

    private fun openDoor() {
        println("Lift is opening...")
        liftDriver.openDoor()
        println("Lift is closed")
        currentAction = Action.MOVE
        floors[currentFloor]!!.isInRoute.set(false)
    }
}
