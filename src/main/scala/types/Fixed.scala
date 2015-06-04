package cla.types

import Chisel._
import Node._
import ChiselError._

object Fixed {

    def apply(x : Int) : Fixed = apply(BigInt(x))
    def apply(x : Int, width : Int) : Fixed = apply(BigInt(x), width)
    def apply(x : BigInt) : Fixed = Lit(x){Fixed()};
    def apply(x : BigInt, width : Int) : Fixed = Lit(x, width){Fixed()};

    def apply(dir : IODirection = null, width : Int = -1) : Fixed = {
        val res = new Fixed();
        res.create(dir, width)
        res
    }
}

class Fixed extends Bits with Num[Fixed] {
    type T = Fixed

    /* Fixed Factory Method */
    override def fromNode(n : Node): this.type = {
        val res = Fixed(OUTPUT).asTypeFor(n).asInstanceOf[this.type]
        res
    }



}
