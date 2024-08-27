package core.domain.exceptions

class BadRequestException(private val msg: String? = null): Exception(msg)