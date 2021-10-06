package hw


data class BrushInfo(
    val protocolVersion: Int,
    val typeId: Int,
    val firmwareVersion: Int,
    val state: BrushState,
    val pressureDetected: Boolean,
    val hasReducedMotorSpeed: Boolean,
    val hasProfessionalTimer: Boolean,
    val timer: Int,
    val mode: BrushMode,
    val quadrant: Int,
)

enum class BrushMode(val value: Int) {
    ModeOff(0x00),
    ModeDailyClean(0x01),
    ModeSensitive(0x02),
    ModeMassage(0x03),
    ModeWhitening(0x04),
    ModeDeepClean(0x05),
    ModeTongueCleaning(0x06),
    ModeTurbo(0x07),
    ModeUnknown(0xff);

    companion object {
        fun valueOf(value: Int) = BrushMode.values().find { it.value == value }
    }
}

enum class BrushState(val value: Int) {
    UNKNOWN(0x00),
    IDLE(0x02),
    RUN(0x03),
    CHARGE(0x04);

    companion object {
        fun valueOf(value: Int) = BrushState.values().find { it.value == value }
    }
}