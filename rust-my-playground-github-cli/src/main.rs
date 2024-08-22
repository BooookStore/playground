mod config;
mod use_case;

use std::env;

use clap::Parser;

use crate::config::{Cli, Commands::List};
use crate::use_case::list;

fn main() {
    let cli = Cli::parse();
    let token = env::var("TOKEN").expect("Require auth token in env with key is TOKEN");

    match cli.command {
        List { org } => {
            list::list_repository_by_organization(&token, &org);
        }
    }
}
