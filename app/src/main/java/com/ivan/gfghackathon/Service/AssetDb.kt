package com.ivan.gfghackathon.Service

import com.ivan.gfghackathon.Model.PlansCategory

object AssetDb {
    val categories = listOf(
        PlansCategory("Loose Weight","calories_loss"),
        PlansCategory("Gain Weight","gain_weight"),
        PlansCategory("Healthy Diet","healthy")
    )
}