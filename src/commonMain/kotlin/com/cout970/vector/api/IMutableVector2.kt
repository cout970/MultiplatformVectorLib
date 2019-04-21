package com.cout970.vector.api

/**
 * Created by cout970 on 17/08/2016.
 */
interface IMutableVector2 : IVector2 {

    override var x: Number
    override var y: Number

    //@formatter:off
    override var xi: Int get() = x.toInt(); set(v) { x = v }
    override var yi: Int get() = y.toInt(); set(v) { y = v }

    override var xf: Float get() = x.toFloat(); set(v) { x = v }
    override var yf: Float get() = y.toFloat(); set(v) { y = v }

    override var xd: Double get() = x.toDouble(); set(v) { x = v }
    override var yd: Double get() = y.toDouble(); set(v) { y = v }
    //@formatter:on
}