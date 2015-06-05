package cla.types

import Chisel._
import Node._
import ChiselError._

object Fixed {

    def apply(x : Int, width : Int, fracWidth : Int) : Fixed = apply(BigInt(x), width, fracWidth)
    def apply(x : BigInt, width : Int, fracWidth : Int) : Fixed = Lit(x, width){Fixed()};

    def apply(dir : IODirection = null, width : Int = -1, fracWidth : Int = -1) : Fixed = {
        val res = new Fixed(fracWidth);
        res.create(dir, width)
        res
    }

}

class Fixed(val fractionalWidth : Int = 0) extends Bits with Num[Fixed] {
    type T = Fixed

    /* Fixed Factory Method */
    override def fromNode(n : Node): this.type = {
        val res = Fixed(OUTPUT).asTypeFor(n).asInstanceOf[this.type]
        res
    }

    override def fromInt(x : Int) : this.type = {
        Fixed(x, this.needWidth(), this.fractionalWidth).asInstanceOf[this.type]
    }

    def checkAligned(b : Fixed) = if(this.fractionalWidth != b.fractionalWidth) ChiselError.error("Fractional Bits do not match")
    
    def fromSInt(s : SInt) : Fixed = chiselCast(s){Fixed()}

    // Order Operators
    def > (b : Fixed) : Bool = this.toSInt > b.toSInt
    def < (b : Fixed) : Bool = this.toSInt < b.toSInt
    def >= (b : Fixed) : Bool = this.toSInt >= b.toSInt
    def <= (b : Fixed) : Bool = this.toSInt <= b.toSInt

    // Arithmetic Operators
    def unary_-() : Fixed = Fixed(0, this.needWidth(), this.fractionalWidth) - this
    def + (b : Fixed) : Fixed = {
        checkAligned(b)
        fromSInt(this.toSInt + b.toSInt)
    }
    def * (b : Fixed) : Fixed = fromSInt(this.toSInt * b.toSInt)
    def / (b : Fixed) : Fixed = fromSInt(this.toSInt / b.toSInt)
    def % (b : Fixed) : Fixed = fromSInt(this.toSInt % b.toSInt)
    def - (b : Fixed) : Fixed = fromSInt(this.toSInt - b.toSInt)

}
