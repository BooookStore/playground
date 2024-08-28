use core::panic;
use std::fmt::format;
use std::{
    env, fs,
    process::{Command, Output},
};

use cucumber::{gherkin::Step, given, then, when, World};

#[derive(World, Default, Debug)]
struct GithubCliWorld {
    args: Vec<String>,
    output: Option<Output>,
}

impl GithubCliWorld {
    fn add_arg(&mut self, arg: String) {
        self.args.push(arg);
    }
}

#[given(expr = "set environment variable {word} is {word}")]
async fn given_set_environment_variable(_world: &mut GithubCliWorld, key: String, value: String) {
    env::set_var(key, value);
}

#[given(expr = "set arg {word}")]
async fn given_set_arg(world: &mut GithubCliWorld, arg: String) {
    world.add_arg(arg);
}

#[given(expr = "set arg {word} is {word}")]
async fn given_set_arg_pair(world: &mut GithubCliWorld, key: String, value: String) {
    world.add_arg(key);
    world.add_arg(value);
}

#[given(expr = "github wiremock mapping {word}")]
async fn given_github_wiremock_mapping(_world: &mut GithubCliWorld, file_path: String) {
    let file_path = format!("fixture/github_wiremock{}", file_path);
    let mapping = fs::read_to_string(file_path).unwrap();
}

#[when("run application")]
async fn when_run_application(world: &mut GithubCliWorld) {
    let args = world.args.clone();
    let output = Command::new("./rust-my-playground-github-cli")
        .args(args)
        .output()
        .expect("Failed run application");
    world.output = Some(output);
}

#[then("exit status is success")]
async fn then_exit_status_is_success(world: &mut GithubCliWorld) {
    match &world.output {
        Some(output) => {
            if !output.status.success() {
                panic!("exit status is failed")
            }
        }
        None => panic!("not run application"),
    }
}

#[then("stdout contains")]
async fn then_stdout_contains(world: &mut GithubCliWorld, step: &Step) {
    let output = &world.output.as_ref().expect("not run application");
    let actual = String::from_utf8_lossy(&output.stdout);
    let expected = docstring_to_str(step);
    assert!(actual.contains(expected), "nactual: {}", actual);
}

#[tokio::main]
async fn main() {
    build_and_copy();
    GithubCliWorld::cucumber()
        .after(|_, _, _, _, _| Box::pin(async { clean() }))
        .run_and_exit("tests/features")
        .await;
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

fn docstring_to_str(step: &Step) -> &str {
    step.docstring
        .as_deref()
        .and_then(|s| s.strip_prefix('\n'))
        .expect("not found docstring")
}
