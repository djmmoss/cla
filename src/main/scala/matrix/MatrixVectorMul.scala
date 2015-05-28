package cla.matrix

import cla.vector._
import Chisel._
import breeze.linalg._
import breeze.numerics._

class MatrixVectorMul(val rowLength : Int, val colLength : Int, val bitWidth : Int, val hasStage : Boolean) extends Module {
    val io = new Bundle {
        val a = Vec.fill(colLength){Vec.fill(rowLength){UInt(INPUT, bitWidth)}}
        val b = Vec.fill(colLength){UInt(INPUT, bitWidth)}
        val res = Vec.fill(rowLength){UInt(OUTPUT, bitWidth)}
    }
    val VecDots = Vec.fill(rowLength){Module(new VectorDot(colLength, bitWidth, hasStage)).io}
    for (i <- 0 until rowLength) {
        for (j <- 0 until colLength) {
            VecDots(i).a(j) := io.a(j)(i)
            VecDots(i).b(j) := io.b(j)
        }
        io.res(i) := VecDots(i).res
    }
}

class MatrixVectorMulTests(c: MatrixVectorMul) extends Tester(c) {
    val r = scala.util.Random
    val lnOf2 = scala.math.log(2) // natural log of 2
    def log2(x: Double): Double = scala.math.log(x) / lnOf2 

    for (i <- 0 to 10) {
        val inA = DenseMatrix.fill(c.colLength, c.rowLength){r.nextInt(scala.math.pow(2, c.bitWidth/2).toInt)}
        val inB = DenseVector.fill(c.colLength){r.nextInt(scala.math.pow(2, c.bitWidth/2).toInt)}
        val outR = inA.t * inB
        for (i <- 0 until c.colLength) {
            for (j <- 0 until c.rowLength) {
                poke(c.io.a(i)(j), inA(i, j))
            }
            poke(c.io.b(i), inB(i))
        }
        if (c.hasStage)
            step(1 + log2(c.colLength).toInt)

        // Try Now
        for (i <- 0 until c.rowLength) {
            expect(c.io.res(i), outR(i))
        }
    }
}
