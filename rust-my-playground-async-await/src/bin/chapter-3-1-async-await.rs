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

async fn blocks() {
    let my_string = "foo".to_string();

    let future_one = async {
        info!("future_one: {my_string}");
    };

    let future_two = async {
        info!("future_two: {my_string}");
    };

    let ((), ()) = futures::join!(future_one, future_two);
}

fn move_block() -> impl Future<Output = ()> {
    let my_string = "foo".to_string();
    async move {
        info!("move_block: {my_string}");
    }
}

fn main() {
    env_logger::init();

    let result = block_on(bar());
    info!("bar() result: {result}");

    block_on(blocks());
    block_on(async { move_block().await });
}
