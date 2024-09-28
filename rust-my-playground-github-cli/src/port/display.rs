use async_trait::async_trait;
use mockall::automock;

use crate::domain::primitive::OrganizationName;
use crate::domain::repository::Repository;

#[automock]
#[async_trait]
pub trait DisplayPort {
    async fn print_repositories_with_contributors(
        &self,
        organization_name: &OrganizationName,
        repositories: &[Repository],
    );

    async fn print_error(&self, message: &str);
}
