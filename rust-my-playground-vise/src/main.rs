use anyhow::Result;

fn main() {
    println!("Hello, world!");
}

trait ServerAPort {
    fn find_customer_by_purchase_number(number: i32) -> Result<String>;
}

struct ServerAGateway {
    host: String,
    port: i32,
}

impl ServerAPort for ServerAGateway {
    fn find_customer_by_purchase_number(number: i32) -> Result<String> {
        todo!()
    }
}

trait ServerBPort {
    fn find_customer_by_purchase_number(number: i32) -> Result<String>;
}

struct ServerBGateway {
    host: String,
    port: i32,
}

impl ServerBPort for ServerBGateway {
    fn find_customer_by_purchase_number(number: i32) -> Result<String> {
        todo!()
    }
}
