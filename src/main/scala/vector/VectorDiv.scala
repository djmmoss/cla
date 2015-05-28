package cla.vector

import cla.primitives._
import Chisel._

class VectorDiv(val vecLength : Int, val bitWidth : Int, val hasStage : Boolean) extends Module {
    val io = new Bundle {
        val a = Vec.fill(vecLength){UInt(INPUT, bitWidth)}
        val b = Vec.fill(vecLength){UInt(INPUT, bitWidth)}
        val res = Vec.fill(vecLength){UInt(OUTPUT, bitWidth)}
    }
    val VecDivs = Vec.fill(vecLength){Module(new Div(bitWidth, hasStage)).io}
    for (i <- 0 until vecLength) {
        VecDivs(i).a := io.a(i)
        VecDivs(i).b := io.b(i)
        io.res(i) := VecDivs(i).res
    }
}

class VectorDivTests(c: VectorDiv) extends Tester(c) {
    val r = scala.util.Random

    for (i <- 0 to 10) {
        val inA = Array.fill(c.vecLength){r.nextInt(scala.math.pow(2, c.bitWidth/2).toInt)}
        val inB = Array.fill(c.vecLength){r.nextInt(scala.math.pow(2, c.bitWidth/2).toInt) + 1}
        for (i <- 0 until c.vecLength) {
            poke(c.io.a(i), inA(i))
            poke(c.io.b(i), inB(i))
        }
        if (c.hasStage)
            step(1)
        for (i <- 0 until c.vecLength) {
            expect(c.io.res(i), inA(i) / inB(i))
        }
    }
}
