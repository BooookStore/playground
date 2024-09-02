use async_trait::async_trait;
use mockall::automock;

#[automock]
#[async_trait]
pub trait DisplayPort {
    async fn print_repository_with_organization(&self, organization_name: &str, repository_name: &str);
    async fn print_error(&self, message: &str);
}
