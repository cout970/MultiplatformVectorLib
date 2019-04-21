package com.cout970.vector.extensions

import com.cout970.vector.api.IQuaternion
import com.cout970.vector.api.IVector2
import com.cout970.vector.api.IVector3
import com.cout970.vector.api.IVector4
import kotlin.math.*

/**
 * Created by cout970 on 17/08/2016.
 */

fun IVector2.lengthSq() = xd * xd + yd * yd

fun IVector3.lengthSq() = xd * xd + yd * yd + zd * zd
fun IVector4.lengthSq() = xd * xd + yd * yd + zd * zd + wd * wd

fun IVector2.length() = sqrt(lengthSq())
fun IVector3.length() = sqrt(lengthSq())
fun IVector4.length() = sqrt(lengthSq())

infix fun IVector2.distance(other: IVector2) = (other - this).length()
infix fun IVector3.distance(other: IVector3) = (other - this).length()
infix fun IVector4.distance(other: IVector4) = (other - this).length()

infix fun IVector2.distanceSq(other: IVector2) = (other - this).lengthSq()
infix fun IVector3.distanceSq(other: IVector3) = (other - this).lengthSq()
infix fun IVector4.distanceSq(other: IVector4) = (other - this).lengthSq()

inline fun IVector2.transform(f: (Double) -> Double) = vec2Of(f(xd), f(yd))
inline fun IVector3.transform(f: (Double) -> Double) = vec3Of(f(xd), f(yd), f(zd))
inline fun IVector4.transform(f: (Double) -> Double) = vec4Of(f(xd), f(yd), f(zd), f(wd))

fun IVector2.ceil() = transform { ceil(it) }
fun IVector3.ceil() = transform { ceil(it) }
fun IVector4.ceil() = transform { ceil(it) }

fun IVector2.floor() = transform { floor(it) }
fun IVector3.floor() = transform { floor(it) }
fun IVector4.floor() = transform { floor(it) }

fun IVector2.round() = transform { round(it).toDouble() }
fun IVector3.round() = transform { round(it).toDouble() }
fun IVector4.round() = transform { round(it).toDouble() }

fun IVector2.normalize() = this / length()
fun IVector3.normalize() = this / length()
fun IVector4.normalize() = this / length()

operator fun IVector2.unaryMinus() = vec2Of(-xd, -yd)
operator fun IVector3.unaryMinus() = vec3Of(-xd, -yd, -zd)
operator fun IVector4.unaryMinus() = vec4Of(-xd, -yd, -zd, -wd)

infix fun IVector2.dot(other: IVector2) = xd * other.xd + yd * other.yd
infix fun IVector3.dot(other: IVector3) = xd * other.xd + yd * other.yd + zd * other.zd
infix fun IVector4.dot(other: IVector4) = xd * other.xd + yd * other.yd + zd * other.zd + wd * other.wd

infix fun IVector3.cross(other: IVector3) =
        vec3Of(yd * other.zd - zd * other.yd, zd * other.xd - xd * other.zd, xd * other.yd - yd * other.xd)

infix fun IVector2.reflect(normal: IVector2): IVector2 {
    val dot2 = dot(normal) * 2
    return vec2Of(xd - dot2 * normal.xd, yd - dot2 * normal.yd)
}

infix fun IVector3.reflect(normal: IVector3): IVector3 {
    val dot2 = dot(normal) * 2
    return vec3Of(xd - dot2 * normal.xd, yd - dot2 * normal.yd, zd - dot2 * normal.zd)
}

infix fun IVector2.angle(vec: IVector2) = acos(angleCos(vec))
infix fun IVector3.angle(vec: IVector3) = acos(angleCos(vec))

infix fun IVector2.angleCos(vec: IVector2): Double {
    var cos = dot(vec) / sqrt(lengthSq() * vec.lengthSq())
    //fixing rounding errors
    cos = if (cos < 1) cos else 1.0
    cos = if (cos > -1) cos else -1.0
    return cos
}

infix fun IVector3.angleCos(vec: IVector3): Double {
    var cos = dot(vec) / sqrt(lengthSq() * vec.lengthSq())
    //fixing rounding errors
    cos = if (cos < 1) cos else 1.0
    cos = if (cos > -1) cos else -1.0
    return cos
}

fun IVector2.interpolate(other: IVector2, point: Double) =
        vec2Of(xd * (1 - point) + other.xd * point, yd * (1 - point) + other.yd * point)

fun IVector3.interpolate(other: IVector3, point: Double) =
        vec3Of(xd * (1 - point) + other.xd * point, yd * (1 - point) + other.yd * point, zd * (1 - point) + other.zd * point)

fun IVector4.interpolate(other: IVector4, point: Double) =
        vec4Of(xd * (1 - point) + other.xd * point, yd * (1 - point) + other.yd * point, zd * (1 - point) + other.zd * point, wd * (1 - point) + other.wd * point)

infix fun IVector2.middle(other: IVector2) = interpolate(other, 0.5)
infix fun IVector3.middle(other: IVector3) = interpolate(other, 0.5)
infix fun IVector4.middle(other: IVector4) = interpolate(other, 0.5)

fun IVector2.toRadians() = transform { it.radians }
fun IVector3.toRadians() = transform { it.radians }
fun IVector4.toRadians() = transform { it.radians }

fun IVector2.toDegrees() = transform { it.degrees }
fun IVector3.toDegrees() = transform { it.degrees }
fun IVector4.toDegrees() = transform { it.degrees }

fun IVector2.rotateCW(angle: Double): IVector2 {
    val sin = sin(-angle)
    val cos = cos(-angle)
    return vec2Of(xd * cos - yd * sin, xd * sin + yd * cos)
}

fun IVector2.rotateCCW(angle: Double): IVector2 {
    val sin = sin(angle)
    val cos = cos(angle)
    return vec2Of(xd * cos - yd * sin, xd * sin + yd * cos)
}

fun IVector3.rotateX(angle: Double): IVector3 {
    val sin = sin(angle)
    val cos = cos(angle)
    val m0 = Vector3.X_AXIS
    val m1 = vec3Of(0.0, cos, -sin)
    val m2 = vec3Of(0.0, sin, cos)

    return vec3Of(dot(m0), dot(m1), dot(m2))
}

fun IVector3.rotateY(angle: Double): IVector3 {
    val sin = sin(angle)
    val cos = cos(angle)
    val m0 = vec3Of(cos, 0.0, sin)
    val m1 = Vector3.Y_AXIS
    val m2 = vec3Of(-sin, 0.0, cos)

    return vec3Of(dot(m0), dot(m1), dot(m2))
}

fun IVector3.rotateZ(angle: Double): IVector3 {
    val sin = sin(angle)
    val cos = cos(angle)
    val m0 = vec3Of(cos, -sin, 0.0)
    val m1 = vec3Of(sin, cos, 0.0)
    val m2 = Vector3.Z_AXIS
    return vec3Of(dot(m0), dot(m1), dot(m2))
}

fun IVector3.rotate(angle: Double, axis: IVector3): IVector3 {
    val sin = sin(angle)
    val cos = cos(angle)
    val ncos = (1 - cos)
    val m0 = vec3Of(cos + axis.xd * axis.xd * ncos, axis.yd * axis.xd * ncos - axis.zd * sin, axis.zd * axis.xd * ncos + axis.yd * sin)
    val m1 = vec3Of(axis.xd * axis.yd * ncos + axis.zd * sin, cos + axis.yd * axis.yd * ncos, axis.zd * axis.yd * ncos - axis.zd * sin)
    val m2 = vec3Of(axis.xd * axis.zd * ncos - axis.yd * sin, axis.yd * axis.zd * ncos + axis.xd * sin, cos + axis.zd * axis.zd * ncos)
    return vec3Of(dot(m0), dot(m1), dot(m2))
}

fun IVector3.rotate(quat: IQuaternion): IVector3 = quat.rotate(this)

fun IQuaternion.rotate(vec: IVector3): IVector3 {
    val x2 = xd + xd
    val y2 = yd + yd
    val z2 = zd + zd
    val x2sq = xd * x2
    val y2sq = yd * y2
    val z2sq = zd * z2
    val xy2 = xd * y2
    val xz2 = xd * z2
    val yz2 = yd * z2
    val wx2 = wd * x2
    val wy2 = wd * y2
    val wz2 = wd * z2
    return vec3Of(
            (1.0 - (y2sq + z2sq)) * vec.xd + (xy2 - wz2) * vec.yd + (xz2 + wy2) * vec.zd,
            (xy2 + wz2) * vec.xd + (1.0 - (x2sq + z2sq)) * vec.yd + (yz2 - wx2) * vec.zd,
            (xz2 - wy2) * vec.xd + (yz2 + wx2) * vec.yd + (1.0 - (x2sq + y2sq)) * vec.zd)
}

fun IVector4.fromAxisAngToQuat(): IQuaternion = quatOfAxisAngledDeg(wd, xd, yd, zd)

infix fun IVector2.min(other: IVector2) = vec2Of(min(xd, other.xd),min(yd, other.yd))
infix fun IVector3.min(other: IVector3) = vec3Of(min(xd, other.xd),min(yd, other.yd), min(zd, other.zd))
infix fun IVector4.min(other: IVector4) = vec4Of(min(xd, other.xd),min(yd, other.yd), min(zd, other.zd), min(wd, other.wd))

infix fun IVector2.max(other: IVector2) = vec2Of(max(xd, other.xd),max(yd, other.yd))
infix fun IVector3.max(other: IVector3) = vec3Of(max(xd, other.xd),max(yd, other.yd), max(zd, other.zd))
infix fun IVector4.max(other: IVector4) = vec4Of(max(xd, other.xd),max(yd, other.yd), max(zd, other.zd), max(wd, other.wd))

fun IVector2.project(other: IVector2) = (this dot other) / this.length()
fun IVector3.project(other: IVector3) = (this dot other) / this.length()
fun IVector4.project(other: IVector4) = (this dot other) / this.length()
