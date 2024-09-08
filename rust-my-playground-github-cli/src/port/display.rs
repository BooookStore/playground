use async_trait::async_trait;
use mockall::automock;

use crate::domain::primitive::{OrganizationName, RepositoryName};

#[automock]
#[async_trait]
pub trait DisplayPort {
    async fn print_repository_with_organization(
        &self,
        organization_name: &OrganizationName,
        repository_name: &[RepositoryName],
    );

    async fn print_error(&self, message: &str);
}
