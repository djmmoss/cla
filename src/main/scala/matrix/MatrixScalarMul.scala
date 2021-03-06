package cla.matrix

import cla.vector._
import Chisel._

class MatrixScalarMul(val rowLength : Int, val colLength : Int, val bitWidth : Int, val hasStage : Boolean) extends Module {
    val io = new Bundle {
        val a = Vec.fill(colLength){Vec.fill(rowLength){UInt(INPUT, bitWidth)}}
        val b = UInt(INPUT, bitWidth)
        val res = Vec.fill(colLength){Vec.fill(rowLength){UInt(OUTPUT, bitWidth)}}
    }
    val MatMuls = Vec.fill(colLength){Module(new VectorMul(rowLength, bitWidth, hasStage)).io}
    val inB = Vec.fill(rowLength){io.b}
    for (i <- 0 until colLength) {
        MatMuls(i).a := io.a(i)
        MatMuls(i).b := inB
        io.res(i) := MatMuls(i).res
    }
}

class MatrixScalarMulTests(c: MatrixScalarMul) extends Tester(c) {
    val r = scala.util.Random

    for (i <- 0 to 10) {
        val inA = Array.fill(c.colLength){Array.fill(c.rowLength){r.nextInt(scala.math.pow(2, c.bitWidth/2).toInt)}}
        val inB = r.nextInt(scala.math.pow(2, c.bitWidth/2).toInt)
        for (i <- 0 until c.colLength) {
            for (j <- 0 until c.rowLength) {
                poke(c.io.a(i)(j), inA(i)(j))
            }
        }
        poke(c.io.b, inB)
        if (c.hasStage)
            step(1)
        for (i <- 0 until c.colLength) {
            for (j <- 0 until c.rowLength) {
                expect(c.io.res(i)(j), inA(i)(j) * inB)
            }
        }
    }
}
