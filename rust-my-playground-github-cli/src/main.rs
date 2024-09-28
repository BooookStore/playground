use std::env;

use clap::Parser;

use driver::http_github_driver::HttpGithubDriver;

use crate::driver::console_display_driver::ConsoleDisplayDriver;
use crate::{config::Cli, config::Commands::Repository, use_case::repository};

mod config;
mod domain;
mod driver;
mod port;
mod use_case;

#[tokio::main]
async fn main() {
    env_logger::init();

    let cli = Cli::parse();
    let token = env::var("TOKEN").expect("Require auth token in env with key is TOKEN");
    let user_name = env::var("USER_NAME").expect("Require user name in env with key is USER_NAME");

    match cli.command {
        Repository { org } => {
            let http_github_driver = HttpGithubDriver::new(token, user_name);
            let console_display_driver = ConsoleDisplayDriver::new();

            repository::output_repositories_with_contributors(
                &http_github_driver,
                &console_display_driver,
                &org,
            )
            .await;
        }
    }
}
