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

async fn foo_ref(x: &u8) -> u8 {
    *x
}

fn foo_ref_expanded<'a>(x: &'a u8) -> impl Future<Output = u8> + 'a {
    async move { *x }
}

fn main() {
    env_logger::init();

    let result = block_on(bar());
    info!("bar() result: {result}");
}
