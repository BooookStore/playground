use clap::{command, Parser, Subcommand};

#[derive(Parser)]
pub struct Cli {
    #[command(subcommand)]
    command: Commands,
}

#[derive(Subcommand)]
enum Commands {
    /// list repository
    List {
        /// specify organization
        #[arg(short, long)]
        org: String,
    },
}
