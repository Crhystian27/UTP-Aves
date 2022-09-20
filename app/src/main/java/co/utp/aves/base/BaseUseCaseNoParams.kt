package co.utp.aves.base

abstract class BaseUseCaseNoParams<T> {
    abstract suspend fun execute(): T
}