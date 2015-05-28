package cla.types

import Chisel._
import Node._
import ChiselError._

object Fixed {

    def apply(x : Int) : UInt = apply(BigInt(x))
    def apply(x : Int, width : Int) : UInt = apply(BigInt(x), width)
    def apply(x : BigInt) : UInt = Lit(checkSign(x)){UInt()};
    def apply(x : BigInt, width : Int) : UInt = Lit(checkSign(x), width){UInt()};
    def apply(x : String) : UInt = Lit(x, -1){UInt()};
    def apply(x : String, width : Int) : UInt = Lit(x, width){UInt()};
    def apply(x: String, base: Char): UInt = Lit(x, base, -1){UInt()};
    def apply(x: String, base: Char, width: Int): UInt = Lit(x, base, width){UInt()};
    def apply(x: Node): UInt = UInt(x, -1)
    //def apply(x: Node, width: Int): UInt = UInt(width = width).asTypeFor(x)

    def apply(dir : IODirection = null, width : Int = -1) : UInt = {
        val res = new UInt();
        res.create(dir, width)
        res
    }
    def DC(width: Int): UInt = Lit("b" + "?"*width, width){UInt()}
    
    private def checkSign(x: BigInt) = {
        if (x < 0)
            ChiselError.error("UInt can't represent negative literal " + x)
        x
    }
}

class Fixed extends UInt{}
