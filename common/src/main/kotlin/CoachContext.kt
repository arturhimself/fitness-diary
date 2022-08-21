import models.*
import stubs.CoachStubs
import kotlinx.datetime.Instant

data class CoachContext(
    var command: CoachCommand = CoachCommand.NONE,
    var state: ContextState = ContextState.NONE,
    val errors: MutableList<GeneralError> = mutableListOf(),

    var workMode: WorkMode = WorkMode.PROD,
    var stubCase: CoachStubs = CoachStubs.NONE,

    var requestId: RequestId = RequestId.NONE,
    var timeStart: Instant = Instant.NONE,
    var coachRequest: Coach = Coach(),
    var coachResponse: Coach = Coach(),
    var coachListResponse: MutableList<Coach> = mutableListOf()
)