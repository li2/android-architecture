package arch

import java.io.IOException

/**
 * Creating a custom Exception class allows us to catch it by Exception class type for error handling.
 *
 * Created by weiyi on 18/2/18.
 * https://github.com/li2
 */
class NoNetworkException : IOException() {
    override val message = "No network connectivity exception"
}
