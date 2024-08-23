use async_trait::async_trait;
use mockall::automock;

#[automock]
#[async_trait]
pub trait Display {
    async fn show_organization_repository(organization_name: &str, repository_name: &str);
}