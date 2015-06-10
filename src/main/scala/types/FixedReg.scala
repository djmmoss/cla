package cla.types

import Chisel._
import cla.types._

class FixedReg(val bitWidth : Int, val fracWidth : Int) extends Module {
    val io = new Bundle {
        val a = Fixed(INPUT, bitWidth, fracWidth)
        val out = Fixed(OUTPUT, bitWidth, fracWidth)
    }
    val myReg = Reg(init=Fixed(0.0, bitWidth, fracWidth))
    myReg := io.a
    io.out := myReg
}

class FixedRegTests(c : FixedReg) extends Tester(c) {
    def toFixed(x : Double, fracWidth : Int) : BigInt = BigInt(scala.math.round(x*scala.math.pow(2, fracWidth)))
    def toFixed(x : Float, fracWidth : Int) : BigInt = BigInt(scala.math.round(x*scala.math.pow(2, fracWidth)))
    def toFixed(x : Int, fracWidth : Int) : BigInt = BigInt(scala.math.round(x*scala.math.pow(2, fracWidth)))
    val r = scala.util.Random

    for (i <- 0 to 10) {
        val in = r.nextInt(scala.math.pow(2, (c.bitWidth - c.fracWidth)/2).toInt) * r.nextFloat()
        val fixed = toFixed(in, c.fracWidth)
        poke(c.io.a, fixed)
        step(1)
        expect(c.io.out, fixed)
    }
}
