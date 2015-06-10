package cla.types

import Chisel._
import cla.types._

class FixedCompare(val bitWidth : Int, val fracWidth : Int) extends Module {
    val io = new Bundle {
        val a = Fixed(INPUT, bitWidth, fracWidth)
        val b = Fixed(INPUT, bitWidth, fracWidth)
        val gt = Bool(OUTPUT)
        val lt = Bool(OUTPUT)
        val gte = Bool(OUTPUT)
        val lte = Bool(OUTPUT)
    }
    io.gt := io.a > io.b
    io.lt := io.a < io.b
    io.gte := io.a >= io.b
    io.lte := io.a <= io.b
}

class FixedCompareTests(c : FixedCompare) extends Tester(c) {
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
        expect(c.io.gt, Bool(inA > inB).litValue())
        expect(c.io.lt, Bool(inA < inB).litValue())
        expect(c.io.gte, Bool(inA >= inB).litValue())
        expect(c.io.lte, Bool(inA <= inB).litValue()) 
    }
}
