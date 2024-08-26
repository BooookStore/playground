use crate::port::github::GitHubPort;

pub async fn output_one_organization_repository<T: GitHubPort>(
    github_port: T,
    organization_name: &str,
) {
    github_port
        .get_one_organization_repository(organization_name)
        .await;
}

#[cfg(test)]
mod tests {
    use mockall::predicate;

    use crate::port::github::MockGitHubPort;
    use crate::use_case::repository::output_one_organization_repository;

    #[tokio::test]
    async fn give_exist_organization_then_output_one_repository() {
        let mut mock_github_port = MockGitHubPort::new();
        mock_github_port
            .expect_get_one_organization_repository()
            .with(predicate::eq("rust-lang"))
            .returning(|_| vec!["cargo".to_string()]);

        output_one_organization_repository(mock_github_port, "rust-lang").await;
    }
}
