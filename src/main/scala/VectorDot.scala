package cla

import Chisel._

class VectorDot(val vecLength : Int, val bitWidth : Int, val hasStage : Boolean) extends Module {
    val lnOf2 = scala.math.log(2) // natural log of 2
    def log2(x: Double): Double = scala.math.log(x) / lnOf2 
    
    val io = new Bundle {
        val a = Vec.fill(vecLength){UInt(INPUT, bitWidth)}
        val b = Vec.fill(vecLength){UInt(INPUT, bitWidth)}
        val res = UInt(OUTPUT, bitWidth)
    }
    // Use VectorMul for the Initial Multiplication
    val vecMul = Module(new VectorMul(vecLength, bitWidth, hasStage)).io
    var mulRes = Vec.fill(vecLength){UInt(0, bitWidth)}
    vecMul.a := io.a
    vecMul.b := io.b
    mulRes := vecMul.res

    var inA = Vec.fill(vecLength){UInt(0, bitWidth)}
    var inB = Vec.fill(vecLength){UInt(0, bitWidth)}
  
    for (i <- 0 until (vecLength/2).toInt) {
    inA(i) := mulRes(i*2)
    inB(i) := mulRes(i*2 + 1)
  }
  for (i <- (vecLength/2).toInt until vecLength) {
    inA(i) := UInt(0, bitWidth)
    inB(i) := UInt(0, bitWidth)
  }

  val adderTree = Vec.fill(log2(vecLength).toInt - 1){Module(new AdderTree(vecLength, bitWidth, hasStage)).io}
  adderTree(0).a := inA
  adderTree(0).b := inB

  for (i <- 1 until (log2(vecLength).toInt - 1)) {
    adderTree(i).a := adderTree(i-1).res1
    adderTree(i).b := adderTree(i-1).res2
  }
  io.res := adderTree(log2(vecLength).toInt - 1).res1(0) + adderTree(log2(vecLength).toInt - 1).res2(0)
}

class AdderTree(val vecLength : Int, val bitWidth : Int, val hasStage : Boolean) extends Module {
  val io = new Bundle {
    val a = Vec.fill(vecLength){UInt(INPUT, bitWidth)}
    val b = Vec.fill(vecLength){UInt(INPUT, bitWidth)}
    val res1 = Vec.fill(vecLength){UInt(OUTPUT, bitWidth)}
    val res2 = Vec.fill(vecLength){UInt(OUTPUT, bitWidth)}
  }
  val vecAdd = Module(new VectorAdd(vecLength, bitWidth, hasStage)).io
  val vecRes = Vec.fill(vecLength){UInt(0, bitWidth)}

  vecAdd.a := io.a
  vecAdd.b := io.b
  vecRes := vecAdd.res

  for (i <- 0 until (vecLength/2).toInt) {
    io.res1(i) := vecRes(i*2)
    io.res2(i) := vecRes(i*2 + 1)
  }
  for (i <- (vecLength/2).toInt until vecLength) {
    io.res1(i) := UInt(0, bitWidth)
    io.res2(i) := UInt(0, bitWidth)
  }
}

class VectorDotTests(c: VectorDot) extends Tester(c) {
    val r = scala.util.Random

    for (i <- 0 to 10) {
        // Generate Inputs and Outputs
        val inA = Array.fill(c.vecLength){r.nextInt(scala.math.pow(2, c.bitWidth/2).toInt)}
        val inB = Array.fill(c.vecLength){r.nextInt(scala.math.pow(2, c.bitWidth/2).toInt)}
        var outRes = 0;
        for (i <- 0 until c.vecLength) {
            outRes += (inA(i) * inB(i))
        }

        // Test Module
        for (i <- 0 until c.vecLength) {
            poke(c.io.a(i), inA(i))
            poke(c.io.b(i), inB(i))
        }
        if (c.hasStage)
            step(1)
        expect(c.io.res, outRes);
    }
}
