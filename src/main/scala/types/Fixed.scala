package cla.types

import Chisel._
import Node._
import ChiselError._

object Fixed {
    def toFixed(x : Double, fracWidth : Int) : BigInt = BigInt(scala.math.round(x*scala.math.pow(2, fracWidth)))
    def toFixed(x : Float, fracWidth : Int) : BigInt = BigInt(scala.math.round(x*scala.math.pow(2, fracWidth)))
    def toFixed(x : Int, fracWidth : Int) : BigInt = BigInt(scala.math.round(x*scala.math.pow(2, fracWidth)))

    def apply(x : Int, width : Int, fracWidth : Int) : Fixed = apply(toFixed(x, fracWidth), width, fracWidth)
    def apply(x : Float, width : Int, fracWidth : Int) : Fixed = apply(toFixed(x, fracWidth), width, fracWidth)
    def apply(x : Double, width : Int, fracWidth : Int) : Fixed = apply(toFixed(x, fracWidth), width, fracWidth)
    def apply(x : BigInt, width : Int, fracWidth : Int) : Fixed =  { 
      val res = Lit(x, width){Fixed()}
      res.fractionalWidth = fracWidth
      res
    }

    def apply(dir : IODirection = null, width : Int = -1, fracWidth : Int = -1) : Fixed = {
        val res = new Fixed(fracWidth);
        res.create(dir, width)
        res
    }
}

class Fixed(var fractionalWidth : Int = 0) extends Bits with Num[Fixed] {
    type T = Fixed

    /* Fixed Factory Method */
    override def fromNode(n : Node): this.type = {
        val res = Fixed(OUTPUT).asTypeFor(n).asInstanceOf[this.type]
        res.fractionalWidth = this.fractionalWidth
        res
    }

    override def fromInt(x : Int) : this.type = {
        Fixed(x, this.needWidth(), this.fractionalWidth).asInstanceOf[this.type]
    }

  override def clone: this.type = {
    val res = Fixed(this.dir, this.needWidth(), this.fractionalWidth).asInstanceOf[this.type];
    res
  }

    def checkAligned(b : Fixed) = if(this.fractionalWidth != b.fractionalWidth) ChiselError.error(this.fractionalWidth + " Fractional Bits does not match " + b.fractionalWidth)

    def fromSInt(s : SInt) : Fixed = {
        val res = chiselCast(s){Fixed()}
        res.fractionalWidth = fractionalWidth
        res
    }

    // Order Operators
    def > (b : Fixed) : Bool = {
        checkAligned(b)
        this.toSInt > b.toSInt
    }

    def < (b : Fixed) : Bool = {
        checkAligned(b)
        this.toSInt < b.toSInt
    }

    def >= (b : Fixed) : Bool = {
        checkAligned(b)
        this.toSInt >= b.toSInt
    }

    def <= (b : Fixed) : Bool = {
        checkAligned(b)
        this.toSInt <= b.toSInt
    }

    // Arithmetic Operators
    def unary_-() : Fixed = Fixed(0, this.needWidth(), this.fractionalWidth) - this

    def + (b : Fixed) : Fixed = {
        checkAligned(b)
        fromSInt(this.toSInt + b.toSInt)
    }

    def - (b : Fixed) : Fixed = {
        checkAligned(b)
        fromSInt(this.toSInt - b.toSInt)
    }

    def * (b : Fixed) : Fixed ={
        checkAligned(b)
        val temp = this.toSInt * b.toSInt
        val res = temp + ((temp & UInt(1)<<UInt(this.fractionalWidth-1))<<UInt(1))
        fromSInt(res >> UInt(this.fractionalWidth))
    }

    def / (b : Fixed) : Fixed = {
        checkAligned(b)
        fromSInt((this.toSInt << UInt(this.fractionalWidth)) / b.toSInt)
    }

    def % (b : Fixed) : Fixed = {
        checkAligned(b)
        fromSInt(this.toSInt % b.toSInt)
    }
}
