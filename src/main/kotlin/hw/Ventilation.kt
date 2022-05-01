package hw

import com.fazecast.jSerialComm.SerialPort
import com.fazecast.jSerialComm.SerialPort.LISTENING_EVENT_DATA_RECEIVED
import com.fazecast.jSerialComm.SerialPortEvent
import com.fazecast.jSerialComm.SerialPortMessageListener
import ui.widgets.ventilation.VentilationMode

const val MESSAGE_DELIMITER = "\r\n"

const val DEVICE = "/dev/ttyUSB0"

enum class MessageVerb(val verb: String) {
    INFO(" I"),
    WRITE(" "),
    REQUEST("RQ"),
    RESPONSE("RP")
}

@JvmInline
value class OptionalNumber(private val value: UByte?) {
    companion object {
        fun fromString(str: String): OptionalNumber {
            return if (str == "---") {
                OptionalNumber(null);
            } else {
                OptionalNumber(Integer.parseInt(str).toUByte())
            }
        }
    }

    override fun toString(): String {
        return value?.toString(10)?.padStart(3, '0') ?: "---"
    }
}

typealias RSSI = OptionalNumber
typealias Sequence = OptionalNumber

//sealed class DeviceID {
//    private val type: Int?
//    private val id: String?
//    private var empty: Boolean = false;
//
//    class Empty() : DeviceID(false, null, null)
//
//    constructor(type: Int, id: String) {
//        this.type = type;
//        this.id = id;
//    }
//
//    private constructor(empty: Boolean, type: Int?, id: String?) {
//        this.empty = empty;
//        this.type = type;
//        this.id = id;
//    }
//
//    override fun toString(): String {
//        return "${type ?: "--"}:${id ?: "------"}"
//    }
//
//    companion object {
//        fun fromString(str: String): DeviceID {
//            return DeviceID(false, 2, "")
//        }
//    }
//}
//
//@ExperimentalUnsignedTypes
//data class Message(
//    val rssi: RSSI,
//    val verb: MessageVerb,
//    val sequence: Sequence,
//    val device1: DeviceID?,
//    val device2: DeviceID?,
//    val device3: DeviceID?,
//    val code: Int,
//    val payload: UByteArray,
//) {
//
//    companion object {
//        fun fromString(str: String): Message {
//            val parts = str.split(" ");
//
//            return Message(
//                RSSI.fromString(parts[0]),
//                MessageVerb.valueOf(parts[1]),
//                Sequence.fromString(parts[2]),
//                DeviceID.fromString(parts[3]),
//                DeviceID.fromString(parts[4]),
//                DeviceID.fromString(parts[5]),
//                Integer.parseInt(parts[6]),
//            )
//        }
//    }
//
//    private val payloadHex: String
//        get() = payload.joinToString("") {
//            it.toString(16).padStart(2, '0')
//        }
//
//    override fun toString(): String {
//        return "$rssi $verb $sequence $device1 $device2 $device3 ${"%03d".format(code)} ${"%03d".format(payload.size)} $payloadHex"
//    }
//}
//
//final class MessageListener : SerialPortMessageListener {
//    override fun getListeningEvents() = LISTENING_EVENT_DATA_RECEIVED;
//    override fun getMessageDelimiter() = MESSAGE_DELIMITER.toByteArray()
//    override fun delimiterIndicatesEndOfMessage() = true
//
//    override fun serialEvent(event: SerialPortEvent?) {
//        if (event != null && event.eventType == LISTENING_EVENT_DATA_RECEIVED) {
//            var msg = event.receivedData
//
//            if (msg[0] == 0x11.toByte()) {
//                msg = msg.drop(1).toByteArray()
//            }
//            println(String(msg))
//        }
//    }
//}
//
//object Ventilation {
//    fun startDebugMonitor() {
//        val uart = SerialPort.getCommPort(DEVICE)
//        uart.baudRate = 115200
//        println(uart.openPort())
//        uart.addDataListener(MessageListener())
//    }
//
//    var mode: VentilationMode = VentilationMode.LOW
//        set(new: VentilationMode) {
//            println(new)
//            field = new;
//        }
//}