package cla.matrix

import cla.vector._
import Chisel._

class MatrixDiv(val rowLength : Int, val colLength : Int, val bitWidth : Int, val hasStage : Boolean) extends Module {
    val io = new Bundle {
        val a = Vec.fill(colLength){Vec.fill(rowLength){UInt(INPUT, bitWidth)}}
        val b = Vec.fill(colLength){Vec.fill(rowLength){UInt(INPUT, bitWidth)}}
        val res = Vec.fill(colLength){Vec.fill(rowLength){UInt(OUTPUT, bitWidth)}}
    }
    val MatDivs = Vec.fill(colLength){Module(new VectorDiv(rowLength, bitWidth, hasStage)).io}
    for (i <- 0 until colLength) {
        for (j <- 0 until rowLength) {
            MatDivs(i).a := io.a(i)
            MatDivs(i).b := io.b(i)
            io.res(i) := MatDivs(i).res
        }
    }
}

class MatrixDivTests(c: MatrixDiv) extends Tester(c) {
    val r = scala.util.Random

    for (i <- 0 to 10) {
        val inA = Array.fill(c.colLength){Array.fill(c.rowLength){r.nextInt(scala.math.pow(2, c.bitWidth/2).toInt)}}
        val inB = Array.fill(c.colLength){Array.fill(c.rowLength){r.nextInt(scala.math.pow(2, c.bitWidth/2).toInt) + 1}}
        for (i <- 0 until c.colLength) {
            for (j <- 0 until c.rowLength) {
                poke(c.io.a(i)(j), inA(i)(j))
                poke(c.io.b(i)(j), inB(i)(j))
            }
        }
        if (c.hasStage)
            step(1)
        for (i <- 0 until c.colLength) {
            for (j <- 0 until c.rowLength) {
                expect(c.io.res(i)(j), inA(i)(j) / inB(i)(j))
            }
        }
    }
}
