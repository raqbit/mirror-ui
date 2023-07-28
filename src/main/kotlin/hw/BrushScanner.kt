package hw

import com.welie.blessed.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.util.*

const val pgCompanyId = 0xDC

const val BRUSH_DEVICE = "Oral-B Toothbrush"

@ExperimentalCoroutinesApi
object BrushScanner : BluetoothCentralManagerCallback() {
    private val BRUSH_SERVICE = UUID.fromString("")
    private val TIMER_CHAR = UUID.fromString("a0f0ff24-5047-4d53-8208-4f72616c2d42")

    private val callbacks: MutableMap<Int, (time: Int) -> Unit> = mutableMapOf()
    private var cbCounter = 0

    private val peripheralCallback: BluetoothPeripheralCallback = object : BluetoothPeripheralCallback() {
        override fun onServicesDiscovered(
            peripheral: BluetoothPeripheral,
            services: MutableList<BluetoothGattService>
        ) {
            super.onServicesDiscovered(peripheral, services)

            peripheral.setNotify(BRUSH_SERVICE, TIMER_CHAR, true)
        }

        override fun onCharacteristicUpdate(
            peripheral: BluetoothPeripheral,
            value: ByteArray,
            characteristic: BluetoothGattCharacteristic,
            status: BluetoothCommandStatus
        ) {
            super.onCharacteristicUpdate(peripheral, value, characteristic, status)

            for ((_, cb) in callbacks) {
                cb(byteToInt(value))
            }
        }
    }

    override fun onDiscoveredPeripheral(peripheral: BluetoothPeripheral, scanResult: ScanResult) {
        // Sanity check that this is a Proctor & Gamble product
        if (!scanResult.manufacturerData.containsKey(pgCompanyId))
            return

        central.connectPeripheral(peripheral, this.peripheralCallback)
    }

    override fun onConnectedPeripheral(peripheral: BluetoothPeripheral) {

    }

    private fun subscribe(cb: (time: Int) -> Unit): Int {
        val id = cbCounter++
        callbacks[id] = cb

        if (callbacks.size == 1) {
            println("Starting scan")
            this.central.scanForPeripheralsWithNames(arrayOf(BRUSH_DEVICE))
        }

        return id
    }

    private fun unsubscribe(id: Int) {
        callbacks.remove(id)

        if (callbacks.isEmpty()) {
            println("Stopping scan")
        }
    }

    private val central: BluetoothCentralManager = BluetoothCentralManager(this)

    fun onBrushUpdate(): Flow<Int> = callbackFlow {
        val id = subscribe {
            trySendBlocking(it)
        }
        awaitClose {
            unsubscribe(id)
        }
    }

    private fun parseManufacturerData(data: ByteArray): BrushInfo {
        return BrushInfo(
            data[0].toInt(),
            data[1].toInt(),
            data[2].toInt(),
            BrushState.valueOf(data[3].toInt())!!,
            data[4].toInt() and 0x80 != 0,
            data[4].toInt() and 0x40 != 0,
            data[4].toInt() and 0x1 != 0,
            data[5] * 60 + data[6],
            BrushMode.valueOf(data[7].toInt())!!,
            data[8].toInt()
        )
    }
}

private fun byteToInt(bytes: ByteArray): Int {
    var result = 0
    var shift = 0
    for (byte in bytes) {
        result = result or (byte.toInt() shl shift)
        shift += 8
    }
    return result
}
