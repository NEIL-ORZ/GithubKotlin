package com.neil.common.ext

/**
 * @USER NEIL.Z
 * @TIME 2020/6/9 0009 10:45
 * @DESC
 */
//子类有限的类
sealed class BooleanExt<out T>

//单例类
object Otherwise : BooleanExt<Nothing>()

class WithData<T>(val data: T) : BooleanExt<T>()

//创建Boolean扩展函数，传入lambda，使用inline节省lambda多创建的类
inline fun <T> Boolean.yes(block: () -> T) =
    //返回类型是BooleanExt
    when {
        this -> {//判断Boolean的值是true，执行lambda并返回子类
            WithData(block())
        }
        else -> Otherwise //判断Boolean的值是false，返回子类
    }

inline fun <T> Boolean.no(block: () -> T) = when {
    this -> Otherwise
    else -> {
        WithData(block())
    }
}

//创建BooleanExt扩展函数
inline fun <T> BooleanExt<T>.otherwise(block: () -> T): T =
    //判断BooleanExt是哪个子类
    when (this) {
        is Otherwise -> block() //判断BooleanExt的子类是Otherwise，执行lambda
        is WithData -> this.data //判断Boolean的值是WidthData，已处理
    }

fun main() {
    //Boolean扩展函数使用
    val result1 = true.yes {
        "true"
    }.otherwise {
        "false"
    }

    val result2 = false.yes {
        "true"
    }.otherwise {
        "false"
    }

    println("result1 = $result1 , result2 = $result2")
}