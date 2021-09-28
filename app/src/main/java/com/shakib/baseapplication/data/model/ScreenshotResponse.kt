package com.shakib.baseapplication.data.model

data class ScreenshotResponse(
        val count: Int,
        val next: Any,
        val previous: Any,
        val results: MutableList<ScreenShot>
)