package saarland.cispa.subjects

import com.alibaba.fastjson.JSON

object Driver : SubjectExecutor() {
    override val packagePrefix = "com.alibaba.fastjson"

    @JvmStatic
    fun main(args: Array<String>) = processArgs(args)

    override fun processInput(text: String) {
        val o = JSON.parseObject(text, Object::class.java)
        JSON.toJSONString(o)
    }
}
