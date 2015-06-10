package cla.types

import Chisel._
import cla.types._

class FixedNew(val bitWidth : Int, val fracWidth : Int) extends Module {
    val io = new Bundle {
        val a = Fixed(INPUT, bitWidth, fracWidth)
        val out = Fixed(OUTPUT, bitWidth, fracWidth)
    }
    io.out := Fixed(1, bitWidth, fracWidth) + io.a
}

class FixedNewTests(c : FixedNew) extends Tester(c) {
    def toFixed(x : Int, fracWidth : Int) : BigInt = BigInt(scala.math.round(x*scala.math.pow(2, fracWidth)))
    val r = scala.util.Random

    for (i <- 0 to 10) {
        val inA = toFixed(r.nextInt(scala.math.pow(2, c.bitWidth/2).toInt), c.fracWidth)
        poke(c.io.a, inA)
        expect(c.io.out, toFixed(1, c.fracWidth) + inA)
    }
}
