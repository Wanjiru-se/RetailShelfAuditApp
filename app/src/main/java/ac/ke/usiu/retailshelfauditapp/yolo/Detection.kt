package ac.ke.usiu.retailshelfauditapp.yolo

data class Detection(
    val className: String,
    val confidence: Float,
    val left: Float,
    val top: Float,
    val right: Float,
    val bottom: Float
)