package itmo.mpi.entity;

/**
 * PENDING - created and waiting for approval by crew leader and ship captain
 * CANCELLED - cancelled by organizer
 * REJECTED - rejected by crew leader or ship captain
 * APPROVED - approved by all parties
 * COMPLETE - completed
 * ENDED - ended by date
 */
public enum TripRequestStatus {
    PENDING,
    CANCELLED,
    REJECTED,
    APPROVED_BY_CREW,
    APPROVED_BY_SHIP,
    APPROVED_BY_BOTH,
    COMPLETE,
    ENDED
}
