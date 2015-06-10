package cla.types

import Chisel._
import cla.types._

class FixedMultiLine(val bitWidth : Int, val fracWidth : Int) extends Module {
    val io = new Bundle {
        val a = Fixed(INPUT, bitWidth, fracWidth)
        val b = Fixed(INPUT, bitWidth, fracWidth)
        val out = Fixed(OUTPUT, bitWidth, fracWidth)
    }
    val temp1 = io.a + io.b
    val temp2 = temp1 - io.a
    val temp3 = temp2 - io.b
    io.out := temp3 
}

class FixedMultiLineTests(c : FixedMultiLine) extends Tester(c) {
    def toFixed(x : Int, fracWidth : Int) : BigInt = BigInt(scala.math.round(x*scala.math.pow(2, fracWidth)))
    val r = scala.util.Random

    for (i <- 0 to 10) {
        val inA = toFixed(r.nextInt(scala.math.pow(2, c.bitWidth-c.fracWidth).toInt), c.fracWidth)
        val inB = toFixed(r.nextInt(scala.math.pow(2, c.bitWidth-c.fracWidth).toInt), c.fracWidth)
        poke(c.io.a, inA)
        poke(c.io.b, inB)
        expect(c.io.out, inA + inB - inA - inB)
    }
}
