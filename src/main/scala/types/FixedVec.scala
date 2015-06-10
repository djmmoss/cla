package cla.types

import Chisel._

class FixedVec(val vecLength : Int, val bitWidth : Int, val fracWidth : Int) extends Module {
    val io = new Bundle {
        val a = Vec.fill(vecLength){Fixed(INPUT, bitWidth, fracWidth)}
        val b = Vec.fill(vecLength){Fixed(INPUT, bitWidth, fracWidth)}
        val res = Vec.fill(vecLength){Fixed(OUTPUT, bitWidth, fracWidth)}
    }
    for (i <- 0 until vecLength) {
        io.res(i) := io.a(i) + io.b(i)
    }
}

class FixedVecTests(c: FixedVec) extends Tester(c) {
    def toFixed(x : Int, fracWidth : Int) : BigInt = BigInt(scala.math.round(x*scala.math.pow(2, fracWidth)))
    val r = scala.util.Random

    for (i <- 0 to 10) {
        val inA = Array.fill(c.vecLength){toFixed(r.nextInt(scala.math.pow(2, c.bitWidth).toInt), c.fracWidth)}
        val inB = Array.fill(c.vecLength){toFixed(r.nextInt(scala.math.pow(2, c.bitWidth).toInt), c.fracWidth)}
        for (i <- 0 until c.vecLength) {
            poke(c.io.a(i), inA(i))
            poke(c.io.b(i), inB(i))
        }
        for (i <- 0 until c.vecLength) {
            expect(c.io.res(i), inA(i) + inB(i))
        }
    }
}
