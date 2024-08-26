use std::env;

use clap::Parser;

use driver::http_github_driver::HttpGithubDriver;

use crate::{
    config::Cli,
    config::Commands::Repository,
    use_case::list
};

mod config;
mod driver;
mod port;
mod use_case;

#[tokio::main]
async fn main() {
    let cli = Cli::parse();
    let token = env::var("TOKEN").expect("Require auth token in env with key is TOKEN");

    match cli.command {
        Repository { org } => {
            list::list_repository_by_organization(HttpGithubDriver::new(token), &org).await;
        }
    }
}
