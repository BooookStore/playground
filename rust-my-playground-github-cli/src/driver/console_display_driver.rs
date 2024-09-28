use async_trait::async_trait;

use crate::domain::primitive::OrganizationName;
use crate::domain::repository::Repository;
use crate::port::display::DisplayPort;

pub struct ConsoleDisplayDriver;

impl ConsoleDisplayDriver {
    pub fn new() -> Self {
        Self {}
    }
}

#[async_trait]
impl DisplayPort for ConsoleDisplayDriver {
    async fn print_repositories_with_contributors(
        &self,
        _organization_name: &OrganizationName,
        _repositories: &[Repository],
    ) {
        todo!()
    }

    async fn print_error(&self, message: &str) {
        println!("Error: {message}");
    }
}
