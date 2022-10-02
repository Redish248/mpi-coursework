package itmo.mpi.model

data class NewFsbAgentRequest(
    val name: String,
    val surname: String,
    val nick: String,
    val password: String
)