use async_trait::async_trait;
use mockall::automock;

#[automock]
#[async_trait]
pub trait GitHubPort {
    async fn get_one_organization_repository(&self, organization_name: &str) -> String;
}
