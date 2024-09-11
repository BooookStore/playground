use anyhow::Result;
use async_trait::async_trait;
use mockall::automock;

use crate::domain::primitive::{ContributorName, OrganizationName, RepositoryName};

#[automock]
#[async_trait]
pub trait GitHubPort {
    async fn get_organization_repositories(
        &self,
        organization_name: &OrganizationName,
    ) -> Result<Vec<RepositoryName>>;

    async fn get_repository_contributors(
        &self,
        organization_name: &OrganizationName,
        repository_name: &RepositoryName,
    ) -> Result<Vec<ContributorName>>;
}
