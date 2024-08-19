use std::future::Future;

use futures::executor::block_on;
use log::info;

async fn foo() -> u8 {
    5
}

fn bar() -> impl Future<Output = u8> {
    async {
        let x: u8 = foo().await;
        x + 5
    }
}

fn main() {
    env_logger::init();

    let result = block_on(bar());
    info!("bar() result: {result}");
}
