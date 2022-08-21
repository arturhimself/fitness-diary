package exceptions

import ru.artursitnikov.fitness.common.models.CoachCommand

class UnknownCoachCommand(command: CoachCommand) : Throwable("Wrong command $command at mapping toTransport stage")