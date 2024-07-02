package core.domain.exceptions





class CustomHttpException(val statusCode: Int, val errorBody: String) : Exception(
    "HTTP Error $statusCode: $errorBody"
)