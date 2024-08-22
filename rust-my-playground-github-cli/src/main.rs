mod command;

use clap::Parser;

use crate::command::Cli;

fn main() {
    let cli = Cli::parse();
}
