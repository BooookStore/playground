use crate::port::github::GitHubPort;

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
    use mockall::predicate;

    use crate::port::github::MockGitHubPort;
    use crate::use_case::list::list_repository_by_organization;

    #[tokio::test]
    async fn give_exist_organization_then_output_repositories() {
        let mut mock_github_port = MockGitHubPort::new();
        mock_github_port
            .expect_get_organization_repositories()
            .with(predicate::eq("rust-lang"))
            .returning(|_| "cargo".to_string());

        list_repository_by_organization(mock_github_port, "rust-lang").await;
    }
}
