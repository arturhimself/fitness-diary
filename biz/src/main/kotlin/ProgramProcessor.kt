package ru.artursitnikov.fitness.biz

import ru.artursitnikov.fitness.biz.general.operation
import ru.artursitnikov.fitness.biz.stubs.*
import ru.artursitnikov.fitness.biz.validation.*
import ru.artursitnikov.fitness.common.ProgramContext
import ru.artursitnikov.fitness.common.models.ProgramCommand
import ru.artursitnikov.fitness.lib.cor.rootChain
import ru.artursitnikov.fitness.lib.cor.worker

class ProgramProcessor {
    suspend fun exec(context: ProgramContext) = RootChain.exec(context)

    companion object {
        private val RootChain = rootChain {
            initStatus("Инициализация статуса")

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
            }

            operation("Получить программу", ProgramCommand.LIST) {
                stubs("Обработка стабов") {
                    stubListSuccess("Имитация успешной обработки")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
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
            }
        }.build()
    }
}