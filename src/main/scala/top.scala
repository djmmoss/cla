package cla

import Chisel._


object Top {
    def main(args: Array[String]): Unit = {
        val bitWidth = 8
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
        }
    }
}
