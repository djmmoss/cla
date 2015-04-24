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
    var prevRes = Vec.fill(vecLength){UInt(0, bitWidth)}
    vecMul.a := io.a
    vecMul.b := io.b
    prevRes := vecMul.res


    var currentLength = vecLength
    var vecAdd = Module(new VectorAdd(currentLength, bitWidth, hasStage)).io
    var addA = Vec.fill(currentLength){UInt(0, bitWidth)}
    var addB = Vec.fill(currentLength){UInt(0, bitWidth)}
    var currRes = Vec.fill(currentLength){UInt(0, bitWidth)}
    // Now We create the Adder Tree
    // The Adder Tree Depth is log2(vecLength)
    for (i <- 0 until (log2(vecLength).toInt)) {
        // At each level we want to perform a vector addition the elements of the previous addition
        vecAdd = Module(new VectorAdd(currentLength, bitWidth, hasStage)).io
        addA = Vec.fill(currentLength){UInt(0, bitWidth)}
        addB = Vec.fill(currentLength){UInt(0, bitWidth)}
        for (i <- 0 until (currentLength/2)) {
            addA := prevRes(i*2)
            addB := prevRes(i*2 + 1)
        }
        val currRes = Vec.fill(currentLength){UInt(0, bitWidth)}
        vecAdd.a := addA
        vecAdd.b := addB
        currRes := vecAdd.res
        currentLength = currentLength/2
        prevRes = currRes
    }
    io.res := currRes(0)
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
