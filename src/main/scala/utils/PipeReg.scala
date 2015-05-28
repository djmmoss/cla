package cla.utils

import Chisel._

class PipeReg(bitWidth : Int, isStage : Boolean) extends Module {
    val io = new Bundle {
        val in = UInt(INPUT, bitWidth)
        val out = UInt(OUTPUT, bitWidth)
    }
    val r = Reg(UInt(0, bitWidth))
    if (isStage == true) {
        r := io.in
        io.out := r
    } else {
        io.out := io.in
    }
}

class PipeRegTests(c: PipeReg, bitWidth : Int, isStage : Boolean) extends Tester(c) {
    val r = scala.util.Random

    for (i <- 0 to 10) {
        val inVal = r.nextInt(scala.math.pow(2, bitWidth).toInt)
        poke(c.io.in, inVal)
        if (isStage)
            step(1)
        expect(c.io.out, inVal)
    }
}
