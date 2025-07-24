package io.github.curioustools.tvstore.base

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class BaseUseCase<out Result, in Params> {
    abstract suspend fun execute(params: Params): Result
    suspend operator fun invoke(params: Params) : Result{
        return withContext(Dispatchers.IO) {
            execute(params)
        }
    }
}