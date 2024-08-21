use core::panic;
use std::{fs, process::Command};

use cucumber::World;

#[derive(World, Default, Debug)]
struct GithubCliWorld;

#[tokio::main]
async fn main() {
    build_and_copy();
    GithubCliWorld::cucumber()
        .run_and_exit("tests/features")
        .await;
    clean();
}

fn build_and_copy() {
    let output = Command::new("cargo")
        .arg("build")
        .current_dir("../rust-my-playground-github-cli")
        .output()
        .expect("Error on cargo build");

    if !output.status.success() {
        panic!("Failed on cargo build");
    }

    fs::copy(
        "../rust-my-playground-github-cli/target/debug/rust-my-playground-github-cli",
        "./rust-my-playground-github-cli",
    )
    .expect("Failed copy");
}

fn clean() {
    fs::remove_file("./rust-my-playground-github-cli").expect("Failed remove file");
}
