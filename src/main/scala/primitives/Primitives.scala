package cla.primitives

import Chisel._
import cla.types._

trait Primitives {
    // Pipeline Function
    def pipeline(wire : UInt, hasStage : Boolean) : UInt    = if (hasStage) Reg(init=UInt(0, wire.getWidth()), next=wire) else wire
    def pipeline(wire : SInt, hasStage : Boolean) : SInt    = if (hasStage) Reg(init=SInt(0, wire.getWidth()), next=wire) else wire
    def pipeline(wire : Fixed, hasStage : Boolean) : Fixed  = if (hasStage) Reg(init=Fixed(0, wire.getWidth(), wire.fractionalWidth), next=wire) else wire

}
