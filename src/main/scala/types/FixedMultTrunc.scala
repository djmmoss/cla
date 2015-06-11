package cla.types

import Chisel._
import cla.types._

class FixedMultTrunc(val bitWidth : Int, val fracWidth : Int) extends Module {
    val io = new Bundle {
        val a = Fixed(INPUT, bitWidth, fracWidth)
        val b = Fixed(INPUT, bitWidth, fracWidth)
        val out = Fixed(OUTPUT, bitWidth, fracWidth)
    }
    io.out := io.a * io.b
}

class FixedMultTruncTests(c : FixedMultTrunc) extends Tester(c) {
    def toFixed(x : Double, fracWidth : Int) : BigInt = BigInt(scala.math.round(x*scala.math.pow(2, fracWidth)))
    def toFixed(x : Float, fracWidth : Int) : BigInt = BigInt(scala.math.round(x*scala.math.pow(2, fracWidth)))
    def toFixed(x : Int, fracWidth : Int) : BigInt = BigInt(scala.math.round(x*scala.math.pow(2, fracWidth)))
    val r = scala.util.Random

    for (i <- 0 to 10) {
        val inA = r.nextInt(scala.math.pow(2, (c.bitWidth - c.fracWidth)/2).toInt) * r.nextFloat()
        val inB = r.nextInt(scala.math.pow(2, (c.bitWidth - c.fracWidth)/2).toInt) * r.nextFloat()
        val fixedA = toFixed(inA, c.fracWidth)
        val fixedB = toFixed(inB, c.fracWidth)
        poke(c.io.a, fixedA)
        poke(c.io.b, fixedB)
        expect(c.io.out, toFixed(inA * inB, c.fracWidth))
    }
}
