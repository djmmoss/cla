package cla

import Chisel._


object Top {
    def main(args: Array[String]): Unit = {
        val bitWidth = 8
        val vecLength = 10
        val rowLength = 5
        val colLength = 5
        val theArgs = args.slice(1, args.length)
        args(0) match {
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
            case "VectorDot" =>
                chiselMainTest(theArgs, () => Module(new VectorDot(vecLength, bitWidth, true))) {
                    c => new VectorDotTests(c)}
                chiselMainTest(theArgs, () => Module(new VectorDot(vecLength, bitWidth, false))) {
                    c => new VectorDotTests(c)}
            case "VectorPipe" =>
                chiselMainTest(theArgs, () => Module(new VectorPipe(vecLength, bitWidth, true))) {
                    c => new VectorPipeTests(c)}
                chiselMainTest(theArgs, () => Module(new VectorPipe(vecLength, bitWidth, false))) {
                    c => new VectorPipeTests(c)}
        }
    }
}
