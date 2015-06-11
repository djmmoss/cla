package cla.types

import Chisel._
import cla.types._

class FixedMux(val bitWidth : Int, val fracWidth : Int) extends Module {
    val io = new Bundle {
        val a = Fixed(INPUT, bitWidth, fracWidth)
        val b = Fixed(INPUT, bitWidth, fracWidth)
        val c = Fixed(INPUT, bitWidth, fracWidth)
        val sel = Bool(INPUT)
        val out = Fixed(OUTPUT, bitWidth, fracWidth)
    }
    val muxSel = Mux(io.sel, io.a + io.c, io.b + io.c)
    io.out := muxSel
}

class FixedMuxTests(c : FixedMux) extends Tester(c) {
    def toFixed(x : Double, fracWidth : Int) : BigInt = BigInt(scala.math.round(x*scala.math.pow(2, fracWidth)))
    def toFixed(x : Float, fracWidth : Int) : BigInt = BigInt(scala.math.round(x*scala.math.pow(2, fracWidth)))
    def toFixed(x : Int, fracWidth : Int) : BigInt = BigInt(scala.math.round(x*scala.math.pow(2, fracWidth)))
    val r = scala.util.Random

    for (i <- 0 to 10) {
        val inA = r.nextInt(scala.math.pow(2, (c.bitWidth - c.fracWidth)/2).toInt) * r.nextFloat()
        val fixedA = toFixed(inA, c.fracWidth)
        val inB = r.nextInt(scala.math.pow(2, (c.bitWidth - c.fracWidth)/2).toInt) * r.nextFloat()
        val fixedB = toFixed(inB, c.fracWidth)
        val inC = r.nextInt(scala.math.pow(2, (c.bitWidth - c.fracWidth)/2).toInt) * r.nextFloat()
        val fixedC = toFixed(inB, c.fracWidth)
        poke(c.io.a, fixedA)
        poke(c.io.b, fixedB)
        poke(c.io.c, fixedC)
        val inSel = r.nextInt(2)
        poke(c.io.sel, inSel)
        val res = if(inSel == 1) fixedA + fixedC else fixedB + fixedC 
        expect(c.io.out, res)
    }
}
