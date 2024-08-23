use crate::port::GitHubPort;

pub async fn list_repository_by_organization<T: GitHubPort>(
    github_port: T,
    organization_name: &str,
) {
    github_port
        .get_organization_repositories(organization_name)
        .await;
}

#[cfg(test)]
mod tests {
    use crate::port::MockGitHubPort;
    use mockall::predicate;

    #[tokio::test]
    async fn give_exist_organization_then_output_repositories() {
        let mut mock_github_port = MockGitHubPort::new();
        mock_github_port
            .expect_get_organization_repositories()
            .with(predicate::eq("rust"))
            .returning(|_| "cargo".to_string());
    }
}
