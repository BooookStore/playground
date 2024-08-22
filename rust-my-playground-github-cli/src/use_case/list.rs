use crate::port::GitHubPort;

pub async fn list_repository_by_organization<T: GitHubPort>(github_port: T, organization_name: &str) {
    github_port.get_organization_repositories(organization_name).await;
}
