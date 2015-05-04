package cla

import Chisel._

class MatrixPipe(val rowLength : Int, val colLength : Int, val bitWidth : Int, val hasStage : Boolean) extends Module {
    val io = new Bundle {
        val in = Vec.fill(colLength){Vec.fill(rowLength){UInt(INPUT, bitWidth)}}
        val out = Vec.fill(colLength){Vec.fill(rowLength){UInt(OUTPUT, bitWidth)}}
    }
    val MatPipe = Vec.fill(colLength){Module(new VectorPipe(rowLength, bitWidth, hasStage)).io}
    for (i <- 0 until colLength) {
        for (j <- 0 until rowLength) {
            MatPipe(i).in := io.in(i)
            io.out(i) := MatPipe(i).out
        }
    }
}

class MatrixPipeTests(c: MatrixPipe) extends Tester(c) {
    val r = scala.util.Random

    for (i <- 0 to 10) {
        val in = Array.fill(c.colLength){Array.fill(c.rowLength){r.nextInt(scala.math.pow(2, c.bitWidth).toInt)}}
        for (i <- 0 until c.colLength) {
            for (j <- 0 until c.rowLength) {
                poke(c.io.in(i)(j), in(i)(j))
            }
        }
        if (c.hasStage)
            step(1)
        for (i <- 0 until c.colLength) {
            for (j <- 0 until c.rowLength) {
                expect(c.io.out(i)(j), in(i)(j))
            }
        }
    }
}
