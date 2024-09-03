use async_trait::async_trait;

use crate::port::display::DisplayPort;

pub struct ConsoleDisplayDriver;

impl ConsoleDisplayDriver {
    pub fn new() -> Self {
        Self {}
    }
}

#[async_trait]
impl DisplayPort for ConsoleDisplayDriver {
    async fn print_repository_with_organization(
        &self,
        organization_name: &str,
        repository_name: &str,
    ) {
        println!("{organization_name}");
        println!("{repository_name}");
    }

    async fn print_error(&self, message: &str) {
        println!("Error: {message}");
    }
}
