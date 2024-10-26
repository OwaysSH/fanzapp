package com.fanz.oways.model

enum class PositionType(val positionName: String){
    GK("GOALKEEPER"),
    DEF("DEFENDER"),
    MID("MIDFIELDER"),
    FWD("FORWARD");
}
data class PlayerSelectedItem(
    var positionCode: PositionType,
    var player: Player? = null
)
