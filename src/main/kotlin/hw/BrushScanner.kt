package hw

import com.welie.blessed.BluetoothCentralManager
import com.welie.blessed.BluetoothCentralManagerCallback
import com.welie.blessed.BluetoothPeripheral
import com.welie.blessed.ScanResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

const val pgCompanyId = 0xDC

const val BRUSH_DEVICE = "Oral-B Toothbrush"

@ExperimentalCoroutinesApi
object BrushScanner : BluetoothCentralManagerCallback() {

    private val callbacks: MutableMap<Int, (info: BrushInfo) -> Unit> = mutableMapOf()
    private var cbCounter = 0

    override fun onDiscoveredPeripheral(peripheral: BluetoothPeripheral, scanResult: ScanResult) {
        val mfData = scanResult.manufacturerData[pgCompanyId] ?: return

        for (callback in callbacks.values) {
            val info = parseManufacturerData(mfData)
            callback(info)
        }
    }

    private fun subscribe(cb: (info: BrushInfo) -> Unit): Int {
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
            this.central.stopScan()
        }
    }

    private val central: BluetoothCentralManager = BluetoothCentralManager(this)

    fun onBrushUpdate(): Flow<BrushInfo> = callbackFlow {
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