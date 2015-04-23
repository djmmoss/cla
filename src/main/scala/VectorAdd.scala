package cla

import Chisel._

class VectorAdd(val vecLength : Int, val bitWidth : Int, val hasStage : Boolean) extends Module {
    val io = new Bundle {
        val a = Vec.fill(vecLength){UInt(INPUT, bitWidth)}
        val b = Vec.fill(vecLength){UInt(INPUT, bitWidth)}
        val res = Vec.fill(vecLength){UInt(OUTPUT, bitWidth)}
    }
    val VecAdds = Vec.fill(vecLength){Module(new Add(bitWidth, hasStage)).io}
    for (i <- 0 until vecLength) {
        VecAdds(i).a := io.a(i)
        VecAdds(i).b := io.b(i)
        io.res(i) := VecAdds(i).res
    }
}

class VectorAddTests(c: VectorAdd) extends Tester(c) {
    val r = scala.util.Random

    for (i <- 0 to 10) {
        val inA = Array.fill(c.vecLength){r.nextInt(scala.math.pow(2, c.bitWidth).toInt)}
        val inB = Array.fill(c.vecLength){r.nextInt(scala.math.pow(2, c.bitWidth).toInt)}
        for (i <- 0 until c.vecLength) {
            poke(c.io.a(i), inA(i))
            poke(c.io.b(i), inB(i))
        }
        if (c.hasStage)
            step(1)
        for (i <- 0 until c.vecLength) {
            expect(c.io.res(i), inA(i) + inB(i))
        }
    }
}
