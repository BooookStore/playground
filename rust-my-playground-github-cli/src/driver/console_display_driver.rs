use async_trait::async_trait;

use crate::domain::primitive::{OrganizationName, RepositoryName};
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
        organization_name: &OrganizationName,
        repository_names: &[RepositoryName],
    ) {
        println!("{organization_name}");
        for repository_name in repository_names {
            println!("{repository_name}");
        }
    }

    async fn print_error(&self, message: &str) {
        println!("Error: {message}");
    }
}
