package ru.artursitnikov.fitness.repo.postgres.exception

class DbConfigParamException(param: String) : RuntimeException("Param $param in database config is undefined")