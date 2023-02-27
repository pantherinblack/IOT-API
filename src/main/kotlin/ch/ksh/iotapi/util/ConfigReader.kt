package ch.ksh.iotapi.util

import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException
import java.util.*

class ConfigReader {
    companion object {
        fun readConfig(attribute: String): String {
            try {
                val reader = BufferedReader(FileReader("PATH")) //TODO
                while (reader.ready()) {
                    val line = reader.readLine()
                    if (line.split("=".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0] == attribute) {
                        var out = Optional.ofNullable(line.split("=".toRegex()).dropLastWhile { it.isEmpty() }
                            .toTypedArray()[1]).map { str: String? -> StringBuilder(str)
                        }.orElse(null)
                        for (i in 2 until line.split("=".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray().size) {
                            out = (out ?: java.lang.StringBuilder("null")).append("=")
                                .append(line.split("=".toRegex()).dropLastWhile { it.isEmpty() }
                                    .toTypedArray()[i])
                        }
                        return out.toString()
                    }
                }
            } catch (e: IOException) {
                throw RuntimeException(e)
            }
            return File("").absolutePath
        }
    }
}