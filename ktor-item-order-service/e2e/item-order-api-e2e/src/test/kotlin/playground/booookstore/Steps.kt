package playground.booookstore

import com.thoughtworks.gauge.Step

class Steps {

    @Step("オーダーIDが<orderId>であるオーダーを取得する")
    fun getOrderByOrderID(orderId: String) {
        println(orderId)
    }

}