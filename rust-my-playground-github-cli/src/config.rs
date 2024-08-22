use clap::{command, Parser, Subcommand};

#[derive(Parser)]
pub struct Cli {
    #[command(subcommand)]
    pub command: Commands,
}

#[derive(Subcommand)]
pub enum Commands {
    /// list repository
    List {
        /// specify organization
        #[arg(short, long)]
        org: String,
    },
}
