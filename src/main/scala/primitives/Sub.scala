package cla.primitives

import cla.utils._
import Chisel._

class Sub(bitWidth : Int, hasStage : Boolean) extends Module {
    val io = new Bundle {
        val a = UInt(INPUT, bitWidth)
        val b = UInt(INPUT, bitWidth)
        val res = UInt(OUTPUT, bitWidth)
    }
    if (hasStage) {
        val pipeReg = Module(new PipeReg(bitWidth, hasStage))
        pipeReg.io.in := io.a - io.b
        io.res := pipeReg.io.out
    } else {
        io.res := io.a - io.b
    }
}

class SubTests(c: Sub, bitWidth : Int, hasStage : Boolean) extends Tester(c) {
    val r = scala.util.Random

    for (i <- 0 to 10) {
        val inA = r.nextInt(scala.math.pow(2, bitWidth).toInt)
        val inB = r.nextInt(scala.math.pow(2, bitWidth).toInt)
        val outRes = inA - inB
        poke(c.io.a, inA)
        poke(c.io.b, inB)
        if (hasStage)
            step(1)
        expect(c.io.res, outRes)
    }
}

