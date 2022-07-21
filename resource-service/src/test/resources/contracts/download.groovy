package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should return binary by id"
    request {
        url "/resources/1"
        method GET()
    }
    response {
        status OK()
        headers {
            contentType applicationJson()
        }
        body(fileAsBytes('valid.mp3'))
    }
}
