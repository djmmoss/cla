package cla

import Chisel._

class VectorScalarMul(val vecLength : Int, val bitWidth : Int, val hasStage : Boolean) extends Module {
    val io = new Bundle {
        val a = Vec.fill(vecLength){UInt(INPUT, bitWidth)}
        val b = UInt(INPUT, bitWidth)
        val res = Vec.fill(vecLength){UInt(OUTPUT, bitWidth)}
    }
    val VecMuls = Vec.fill(vecLength){Module(new Mul(bitWidth, hasStage)).io}
    for (i <- 0 until vecLength) {
        VecMuls(i).a := io.a(i)
        VecMuls(i).b := io.b
        io.res(i) := VecMuls(i).res
    }
}

class VectorScalarMulTests(c: VectorScalarMul) extends Tester(c) {
    val r = scala.util.Random

    for (i <- 0 to 10) {
        val inA = Array.fill(c.vecLength){r.nextInt(scala.math.pow(2, c.bitWidth/2).toInt)}
        val inB = r.nextInt(scala.math.pow(2, c.bitWidth/2).toInt)
        for (i <- 0 until c.vecLength) {
            poke(c.io.a(i), inA(i))
        }
        poke(c.io.b, inB)
        if (c.hasStage)
            step(1)
        for (i <- 0 until c.vecLength) {
            expect(c.io.res(i), inA(i) * inB)
        }
    }
}
