package cla

import Chisel._

class VectorPipe(val vecLength : Int, val bitWidth : Int, val hasStage : Boolean) extends Module {
    val io = new Bundle {
        val in = Vec.fill(vecLength){UInt(INPUT, bitWidth)}
        val out = Vec.fill(vecLength){UInt(OUTPUT, bitWidth)}
    }
    val VecPipe = Vec.fill(vecLength){Module(new PipeReg(bitWidth, hasStage)).io}
    for (i <- 0 until vecLength) {
        VecPipe(i).in := io.in(i)
        io.out(i) := VecPipe(i).out
    }
}

class VectorPipeTests(c: VectorPipe) extends Tester(c) {
    val r = scala.util.Random

    for (i <- 0 to 10) {
        val in = Array.fill(c.vecLength){r.nextInt(scala.math.pow(2, c.bitWidth).toInt)}
        for (i <- 0 until c.vecLength) {
            poke(c.io.in(i), in(i))
        }
        if (c.hasStage)
            step(1)
        for (i <- 0 until c.vecLength) {
            expect(c.io.out(i), in(i))
        }
    }
}
