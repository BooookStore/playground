use anyhow::Result;
use async_trait::async_trait;
use mockall::automock;

use crate::domain::primitive::OrganizationName;

#[automock]
#[async_trait]
pub trait GitHubPort {
    async fn get_one_organization_repository(
        &self,
        organization_name: &OrganizationName,
    ) -> Result<String>;
}
