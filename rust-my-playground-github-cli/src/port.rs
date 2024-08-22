use async_trait::async_trait;

#[async_trait]
pub trait GitHubPort {
    async fn get_organization_repositories(&self, organization_name: &str);
}
