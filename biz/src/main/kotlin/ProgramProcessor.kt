package ru.artursitnikov.fitness.biz

import ru.artursitnikov.fitness.biz.general.initRepo
import ru.artursitnikov.fitness.biz.general.operation
import ru.artursitnikov.fitness.biz.general.prepareResult
import ru.artursitnikov.fitness.biz.repo.*
import ru.artursitnikov.fitness.biz.stubs.*
import ru.artursitnikov.fitness.biz.validation.*
import ru.artursitnikov.fitness.common.ProgramContext
import ru.artursitnikov.fitness.common.models.ContextState
import ru.artursitnikov.fitness.common.models.ProgramCommand
import ru.artursitnikov.fitness.common.models.Settings
import ru.artursitnikov.fitness.lib.cor.chain
import ru.artursitnikov.fitness.lib.cor.rootChain
import ru.artursitnikov.fitness.lib.cor.worker

class ProgramProcessor(
    private val settings: Settings = Settings()
) {
    suspend fun exec(context: ProgramContext) = RootChain.exec(context.apply { settings = this@ProgramProcessor.settings })

    companion object {
        private val RootChain = rootChain {
            initStatus("Инициализация статуса")
            initRepo("Инициализация репозитория")

            operation("Создание программы", ProgramCommand.CREATE) {
                stubs("Обработка стабов") {
                    stubCreateSuccess("Имитация успешной обработки")
                    stubValidationBadTitle("Имитация ошибки валидации заголовка")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в programValidating") { programValidating = programRequest.deepCopy() }
                    worker("Очистка заголовка") { programValidating.title = programValidating.title.trim() }
                    validateTitleNotEmpty("Проверка, что заголовок не пуст")
                    validateTitleHasContent("Проверка символов")
                    finishProgramValidation("Завершение проверок")
                }
                chain {
                    title = "Логика сохранения"
                    repoPrepareCreate("Подготовка объекта для сохранения")
                    repoCreate("Создание программы в БД")
                }
                prepareResult("Подготовка ответа")
            }

            operation("Получить программу", ProgramCommand.READ) {
                stubs("Обработка стабов") {
                    stubReadSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в programValidating") { programValidating = programRequest.deepCopy() }
                    validateIdNotEmpty("Проверка на непустой id")
                    finishProgramValidation("Успешное завершение процедуры валидации")
                }
                chain {
                    title = "Логика чтения"
                    repoRead("Чтение программы из БД")
                    worker {
                        title = "Подготовка ответа для Read"
                        on { state == ContextState.RUNNING }
                        handle { repoDone = repoRead }
                    }
                }
                prepareResult("Подготовка ответа")
            }

            operation("Получить список всех программ", ProgramCommand.LIST) {
                stubs("Обработка стабов") {
                    stubListSuccess("Имитация успешной обработки")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                chain {
                    title = "Логика чтения"
                    repoList("Чтение программ из БД")
                }
                prepareResult("Подготовка ответа")
            }

            operation("Изменить программу", ProgramCommand.UPDATE) {
                stubs("Обработка стабов") {
                    stubUpdateSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubValidationBadTitle("Имитация ошибки валидации заголовка")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в programValidating") { programValidating = programRequest.deepCopy() }
                    worker("Очистка заголовка") { programValidating.title = programValidating.title.trim() }
                    validateIdNotEmpty("Проверка на непустой id")
                    validateTitleNotEmpty("Проверка на непустой заголовок")
                    validateTitleHasContent("Проверка на наличие содержания в заголовке")
                    finishProgramValidation("Успешное завершение процедуры валидации")
                }
                chain {
                    title = "Логика сохранения"
                    repoRead("Чтение программы из БД")
                    repoPrepareUpdate("Подготовка программы для обновления")
                    repoUpdate("Обновление программы в БД")
                }
                prepareResult("Подготовка ответа")
            }

            operation("Удалить программу", ProgramCommand.DELETE) {
                stubs("Обработка стабов") {
                    stubDeleteSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в programValidating") {
                        programValidating = programRequest.deepCopy() }
                    validateIdNotEmpty("Проверка на непустой id")
                    finishProgramValidation("Успешное завершение процедуры валидации")
                }
                chain {
                    title = "Логика удаления"
                    repoRead("Чтение программы из БД")
                    repoPrepareDelete("Подготовка программы для удаления")
                    repoDelete("Удаление программы из БД")
                }
                prepareResult("Подготовка ответа")
            }
        }.build()
    }
}