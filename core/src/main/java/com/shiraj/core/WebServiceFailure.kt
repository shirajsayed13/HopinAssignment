package com.shiraj.core

import com.shiraj.base.Failure


class WebServiceFailure {
    class NoNetworkFailure(
        msg: String = "Network not available!"
    ) : Failure.DataFailure(msg)

    class NetworkTimeOutFailure(
        msg: String = "Network timeout!"
    ) : Failure.DataFailure(msg)

    class NetworkDataFailure(
        msg: String = "Error parsing data!"
    ) : Failure.DataFailure(msg)

    class UnknownNetworkFailure(
        msg: String = "Unknown network error!"
    ) : Failure.DataFailure(msg)
}