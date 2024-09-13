use anyhow::Result;

fn main() {
    println!("Hello, world!");
}

fn find_customer<A: ServerAPort, B: ServerBPort>(
    server_a_port: A,
    server_b_port: B,
    purchase_number: i32,
) -> Result<String> {
    // 初めにServerAに問い合わせる
    let server_a_response: Result<String> =
        server_a_port.find_customer_by_purchase_number(purchase_number);

    // 顧客情報が見つかったら呼び出し元に返し処理を終える
    if server_a_response.is_ok() {
        return Ok(server_a_response.unwrap());
    }

    // 顧客情報が見つからなかった場合ServerBに問い合わせ、
    // 結果を呼び出し元に返し処理を終える
    server_b_port.find_customer_by_purchase_number(purchase_number)
}

// traitはJavaで言うInterfaceのようなもの
trait ServerAPort {
    fn find_customer_by_purchase_number(&self, number: i32) -> Result<String>;
}

struct ServerAGateway {
    host: String,
    port: i32,
}

// traitで宣言されたメソッドを実装
impl ServerAPort for ServerAGateway {
    fn find_customer_by_purchase_number(&self, _number: i32) -> Result<String> {
        // サーバーにリクエストを送る処理...
        todo!()
    }
}

trait ServerBPort {
    fn find_customer_by_purchase_number(&self, number: i32) -> Result<String>;
}

struct ServerBGateway {
    host: String,
    port: i32,
}

impl ServerBPort for ServerBGateway {
    fn find_customer_by_purchase_number(&self, _number: i32) -> Result<String> {
        // サーバーにリクエストを送る処理...
        todo!()
    }
}
