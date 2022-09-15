package exceptions

import ru.artursitnikov.fitness.common.models.ProgramCommand

class UnknownProgramCommand(command: ProgramCommand) : Throwable("Wrong command $command at mapping toTransport stage")