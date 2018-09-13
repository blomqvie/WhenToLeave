package fi.blomqvist.whentoleave

data class TrainInfo(val routeId: String, val arrivalTime: Long, val departureTime: Long, val delay: Long, val arrivalInMinutes: Int, val arrivalText: String)