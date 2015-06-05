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

  override def fromInt(x : Int) : this.type = {
    Fixed(x).asInstanceOf[this.type]
  }

  def toSInt(f : Fixed) : SInt = SInt(0)
  def fromSInt(s : SInt) : Fixed = Fixed(0)

  // Arithmetic Operators
  def unary_-() : Fixed = Fixed(0) - this
  def + (b : Fixed) : Fixed = fromSInt(toSInt(this) + toSInt(b))
  def * (b : Fixed) : Fixed = fromSInt(toSInt(this) * toSInt(b))
  def / (b : Fixed) : Fixed = fromSInt(toSInt(this) / toSInt(b))

}
