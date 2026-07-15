package ac.ke.usiu.retailshelfauditapp.model

data class Report(
    val id: Int,
    val reportId: String,
    val date: String,
    val cocaColaCount: Int,
    val fantaCount: Int,
    val spriteCount: Int,
    val emptySpaces: Int
)