package ru.artursitnikov.fitness.common

import kotlinx.datetime.Instant
import ru.artursitnikov.fitness.common.models.*
import ru.artursitnikov.fitness.common.repo.ProgramRepository
import ru.artursitnikov.fitness.common.stubs.ProgramStubs

data class ProgramContext(
    var settings: Settings = Settings(),

    var requestId: RequestId = RequestId.NONE,
    var timeStart: Instant = Instant.NONE,
    var command: ProgramCommand = ProgramCommand.NONE,
    var state: ContextState = ContextState.NONE,
    val errors: MutableList<GeneralError> = mutableListOf(),

    var workMode: WorkMode = WorkMode.PROD,
    var stubCase: ProgramStubs = ProgramStubs.NONE,

    var programValidating: Program = Program(),
    var programValidated: Program = Program(),

    var repo: ProgramRepository = ProgramRepository.NONE,
    var repoRead: Program = Program(),
    var repoPrepare: Program = Program(),
    var repoDone: Program = Program(),
    var listRepoDone: List<Program> = mutableListOf(),

    var programRequest: Program = Program(),
    var programResponse: Program = Program(),
    var programListResponse: List<Program> = mutableListOf()
)