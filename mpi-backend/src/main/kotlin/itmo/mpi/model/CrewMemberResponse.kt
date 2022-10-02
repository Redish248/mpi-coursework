package itmo.mpi.model

data class CrewMemberResponse(
    val id: Int,
    val fullName: String,
    val teamName: String,
    val isPirate: Boolean
)