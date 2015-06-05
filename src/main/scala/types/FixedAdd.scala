package cla.types

import Chisel._
import cla.types._

class FixedAdd(val bitWidth : Int, val fracWidth : Int) extends Module {
    val io = new Bundle {
        val a = Fixed(INPUT, bitWidth, fracWidth)
        val b = Fixed(INPUT, bitWidth, fracWidth)
        val out = Fixed(OUTPUT, bitWidth, fracWidth)
    }
    io.out := io.a + io.b
}

class FixedAddTests(c : FixedAdd) extends Tester(c) {
    val r = scala.util.Random

    for (i <- 0 to 10) {
        val inA = r.nextInt(scala.math.pow(2, c.bitWidth).toInt)
        val inB = r.nextInt(scala.math.pow(2, c.bitWidth).toInt)
        poke(c.io.a, inA)
        poke(c.io.b, inB)
        expect(c.io.out, inA + inB)
    }
}
