package features.profile.domain.repositories

import core.data.utils.DataResult
import domain.requests.SignInRequest
import domain.requests.SignUpRequest
import features.profile.domain.models.User

interface AuthRepository {

    suspend fun signIn(signInRequest: SignInRequest): DataResult<User>

    suspend fun signUp(signUpRequest: SignUpRequest): DataResult<User>

    suspend fun signOut()

}