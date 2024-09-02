use core::panic;
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

    let client = reqwest::Client::new();
    let res = client
        .post("http://localhost:8080/__admin/mappings")
        .body(mapping)
        .send()
        .await
        .unwrap();

    assert_eq!(reqwest::StatusCode::CREATED, res.status());
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

#[then(expr = "exit status is {word}")]
async fn then_exit_status_is_success(world: &mut GithubCliWorld, expected: String) {
    match &world.output {
        Some(output) => {
            if expected.eq("success") {
                assert!(
                    output.status.success(),
                    "expected success but actual failure"
                )
            } else if expected.eq("failure") {
                assert!(
                    !output.status.success(),
                    "expected failure but actual success"
                )
            } else {
                panic!("unrecognized expected status: {expected}")
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
    GithubCliWorld::cucumber()
        .before(|_, _, _, _| {
            Box::pin(async {
                clean_wiremock_mappings().await;
                build_and_copy_sut();
            })
        })
        .after(|_, _, _, _, _| Box::pin(async { delete_sut() }))
        .run_and_exit("tests/features")
        .await;
}

async fn clean_wiremock_mappings() {
    let client = reqwest::Client::new();
    let res = client
        .delete("http://localhost:8080/__admin/mappings")
        .send()
        .await
        .unwrap();

    assert_eq!(reqwest::StatusCode::OK, res.status());
}

fn build_and_copy_sut() {
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

fn delete_sut() {
    fs::remove_file("./rust-my-playground-github-cli").expect("Failed delete file");
}

fn docstring_to_str(step: &Step) -> &str {
    step.docstring
        .as_deref()
        .and_then(|s| s.strip_prefix('\n'))
        .expect("not found docstring")
}
