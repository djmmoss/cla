package cla

import cla.primitives._
import cla.utils._
import cla.vector._
import cla.matrix._
import cla.types._

import Chisel._


object Top {
    def main(args: Array[String]): Unit = {
        val bitWidth = 8
        val fracWidth = 2
        val vecLength = 2
        val rowLength = 2
        val colLength = 2
        val theArgs = args.slice(1, args.length)
        args(0) match {

            // Scale Operations
            case "Add" =>
                chiselMainTest(theArgs, () => Module(new Add(bitWidth, true))) {
                    c => new AddTests(c, bitWidth, true)}
                chiselMainTest(theArgs, () => Module(new Add(bitWidth, false))) {
                    c => new AddTests(c, bitWidth, false)}
            case "PipeReg" =>
                chiselMainTest(theArgs, () => Module(new PipeReg(bitWidth, true))) {
                    c => new PipeRegTests(c, bitWidth, true)}
                chiselMainTest(theArgs, () => Module(new PipeReg(bitWidth, false))) {
                    c => new PipeRegTests(c, bitWidth, false)}
            case "Sub" =>
                chiselMainTest(theArgs, () => Module(new Sub(bitWidth, true))) {
                    c => new SubTests(c, bitWidth, true)}
                chiselMainTest(theArgs, () => Module(new Sub(bitWidth, false))) {
                    c => new SubTests(c, bitWidth, false)}
            case "Mul" =>
                chiselMainTest(theArgs, () => Module(new Mul(bitWidth, true))) {
                    c => new MulTests(c, bitWidth, true)}
                chiselMainTest(theArgs, () => Module(new Mul(bitWidth, false))) {
                    c => new MulTests(c, bitWidth, false)}
            case "Div" =>
                chiselMainTest(theArgs, () => Module(new Div(bitWidth, true))) {
                    c => new DivTests(c, bitWidth, true)}
                chiselMainTest(theArgs, () => Module(new Div(bitWidth, false))) {
                    c => new DivTests(c, bitWidth, false)}

            // Vector Operations
            case "VectorAdd" =>
                chiselMainTest(theArgs, () => Module(new VectorAdd(vecLength, bitWidth, true))) {
                    c => new VectorAddTests(c)}
                chiselMainTest(theArgs, () => Module(new VectorAdd(vecLength, bitWidth, false))) {
                    c => new VectorAddTests(c)}
            case "VectorSub" =>
                chiselMainTest(theArgs, () => Module(new VectorSub(vecLength, bitWidth, true))) {
                    c => new VectorSubTests(c)}
                chiselMainTest(theArgs, () => Module(new VectorSub(vecLength, bitWidth, false))) {
                    c => new VectorSubTests(c)}
            case "VectorMul" =>
                chiselMainTest(theArgs, () => Module(new VectorMul(vecLength, bitWidth, true))) {
                    c => new VectorMulTests(c)}
                chiselMainTest(theArgs, () => Module(new VectorMul(vecLength, bitWidth, false))) {
                    c => new VectorMulTests(c)}
            case "VectorDiv" =>
                chiselMainTest(theArgs, () => Module(new VectorDiv(vecLength, bitWidth, true))) {
                    c => new VectorDivTests(c)}
                chiselMainTest(theArgs, () => Module(new VectorDiv(vecLength, bitWidth, false))) {
                    c => new VectorDivTests(c)}
            case "VectorDot" =>
                chiselMainTest(theArgs, () => Module(new VectorDot(vecLength, bitWidth, false))) {
                    c => new VectorDotTests(c)}
                chiselMainTest(theArgs, () => Module(new VectorDot(vecLength, bitWidth, true))) {
                    c => new VectorDotTests(c)}
            case "VectorScalarMul" =>
                chiselMainTest(theArgs, () => Module(new VectorScalarMul(vecLength, bitWidth, true))) {
                    c => new VectorScalarMulTests(c)}
                chiselMainTest(theArgs, () => Module(new VectorScalarMul(vecLength, bitWidth, false))) {
                    c => new VectorScalarMulTests(c)}
            case "VectorScalarDiv" =>
                chiselMainTest(theArgs, () => Module(new VectorScalarDiv(vecLength, bitWidth, true))) {
                    c => new VectorScalarDivTests(c)}
                chiselMainTest(theArgs, () => Module(new VectorScalarDiv(vecLength, bitWidth, false))) {
                    c => new VectorScalarDivTests(c)}
            case "VectorPipe" =>
                chiselMainTest(theArgs, () => Module(new VectorPipe(vecLength, bitWidth, true))) {
                    c => new VectorPipeTests(c)}
                chiselMainTest(theArgs, () => Module(new VectorPipe(vecLength, bitWidth, false))) {
                    c => new VectorPipeTests(c)}
            
            // Matrix Operations
            case "MatrixAdd" =>
                chiselMainTest(theArgs, () => Module(new MatrixAdd(rowLength, colLength, bitWidth, true))) {
                    c => new MatrixAddTests(c)}
                chiselMainTest(theArgs, () => Module(new MatrixAdd(rowLength, colLength, bitWidth, false))) {
                    c => new MatrixAddTests(c)}
            case "MatrixSub" =>
                chiselMainTest(theArgs, () => Module(new MatrixSub(rowLength, colLength, bitWidth, true))) {
                    c => new MatrixSubTests(c)}
                chiselMainTest(theArgs, () => Module(new MatrixSub(rowLength, colLength, bitWidth, false))) {
                    c => new MatrixSubTests(c)}
            case "MatrixMul" =>
                chiselMainTest(theArgs, () => Module(new MatrixMul(rowLength, colLength, bitWidth, true))) {
                    c => new MatrixMulTests(c)}
                chiselMainTest(theArgs, () => Module(new MatrixMul(rowLength, colLength, bitWidth, false))) {
                    c => new MatrixMulTests(c)}
            case "MatrixDiv" =>
                chiselMainTest(theArgs, () => Module(new MatrixDiv(rowLength, colLength, bitWidth, true))) {
                    c => new MatrixDivTests(c)}
                chiselMainTest(theArgs, () => Module(new MatrixDiv(rowLength, colLength, bitWidth, false))) {
                    c => new MatrixDivTests(c)}
            case "MatrixScalarMul" =>
                chiselMainTest(theArgs, () => Module(new MatrixScalarMul(rowLength, colLength, bitWidth, true))) {
                    c => new MatrixScalarMulTests(c)}
                chiselMainTest(theArgs, () => Module(new MatrixScalarMul(rowLength, colLength, bitWidth, false))) {
                    c => new MatrixScalarMulTests(c)}
            case "MatrixScalarDiv" =>
                chiselMainTest(theArgs, () => Module(new MatrixScalarDiv(rowLength, colLength, bitWidth, true))) {
                    c => new MatrixScalarDivTests(c)}
                chiselMainTest(theArgs, () => Module(new MatrixScalarDiv(rowLength, colLength, bitWidth, false))) {
                    c => new MatrixScalarDivTests(c)}
            case "MatrixVectorMul" =>
                chiselMainTest(theArgs, () => Module(new MatrixVectorMul(rowLength, colLength, bitWidth, true))) {
                    c => new MatrixVectorMulTests(c)}
                chiselMainTest(theArgs, () => Module(new MatrixVectorMul(rowLength, colLength, bitWidth, false))) {
                    c => new MatrixVectorMulTests(c)}
            case "MatrixPipe" =>
                chiselMainTest(theArgs, () => Module(new MatrixPipe(rowLength, colLength, bitWidth, true))) {
                    c => new MatrixPipeTests(c)}
                chiselMainTest(theArgs, () => Module(new MatrixPipe(rowLength, colLength, bitWidth, false))) {
                    c => new MatrixPipeTests(c)}

            // Fixed Point Tests
            case "FixedAdd" =>
                chiselMainTest(theArgs, () => Module(new FixedAdd(bitWidth, fracWidth))) {
                    c => new FixedAddTests(c)}
            case "FixedSub" =>
                chiselMainTest(theArgs, () => Module(new FixedSub(bitWidth, fracWidth))) {
                    c => new FixedSubTests(c)}
            case "FixedNew" =>
                chiselMainTest(theArgs, () => Module(new FixedNew(bitWidth, fracWidth))) {
                    c => new FixedNewTests(c)}
            case "FixedMultiLine" =>
                chiselMainTest(theArgs, () => Module(new FixedMultiLine(bitWidth, fracWidth))) {
                    c => new FixedMultiLineTests(c)}
            case "FixedMult" =>
                chiselMainTest(theArgs, () => Module(new FixedMult(bitWidth, fracWidth))) {
                    c => new FixedMultTests(c)}
        }
    }
}
