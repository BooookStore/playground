mod config;

use clap::Parser;

use crate::config::{Cli, Commands::List};

fn main() {
    let cli = Cli::parse();

    match cli.command {
        List { org } => {
            if org.eq("rust-lang") {
                println!("cargo");
            }
        }
    }
}
