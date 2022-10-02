package itmo.mpi.service

import itmo.mpi.model.CrewMemberResponse
import itmo.mpi.model.NewFsbAgentRequest

interface FsbAgentService {
    fun registerNewAgent(newFsbAgentRequest: NewFsbAgentRequest)
    fun getCrews(): List<CrewMemberResponse>
    fun markAsPirate(userUid: Long)
    fun markAsGoodPerson(userUid: Long)
    fun markIslandAsDangerous(islandUid: Int)
    fun markIslandAsSafe(islandUid: Int)
}