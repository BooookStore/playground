use crate::domain::primitive::OrganizationName;
use crate::port::display::DisplayPort;
use crate::port::github::GitHubPort;

pub async fn output_one_organization_repository<T: GitHubPort, U: DisplayPort>(
    github_port: T,
    display_port: U,
    organization_name: &OrganizationName,
) {
    let repository_name = github_port
        .get_one_organization_repository(organization_name)
        .await;

    match repository_name {
        Ok(repository_name) => {
            display_port
                .print_repository_with_organization(organization_name, &repository_name)
                .await;
        }
        Err(_) => {
            display_port.print_error("failed to get repository").await;
        }
    }
}

#[cfg(test)]
mod tests {
    use anyhow::anyhow;
    use mockall::predicate;
    use predicate::eq;

    use crate::port::display::MockDisplayPort;
    use crate::port::github::MockGitHubPort;
    use crate::use_case::repository::output_one_organization_repository;

    #[tokio::test]
    async fn output_one_organization_repository_name() {
        let mut mock_github_port = MockGitHubPort::new();
        mock_github_port
            .expect_get_one_organization_repository()
            .with(eq("rust-lang".to_string()))
            .times(1)
            .returning(|_| Ok("cargo".to_string()));

        let mut mock_display_port = MockDisplayPort::new();
        mock_display_port
            .expect_print_repository_with_organization()
            .with(eq(String::from("rust-lang")), eq(String::from("cargo")))
            .times(1)
            .return_const(());

        output_one_organization_repository(
            mock_github_port,
            mock_display_port,
            &String::from("rust-lang")
        )
        .await;
    }

    #[tokio::test]
    async fn output_error_message_failed_to_get_organization_repository_name() {
        let mut mock_github_port = MockGitHubPort::new();
        mock_github_port
            .expect_get_one_organization_repository()
            .with(eq("rust-lang".to_string()))
            .returning(|_| Err(anyhow!("failed")));

        let mut mock_display_port = MockDisplayPort::new();
        mock_display_port
            .expect_print_error()
            .with(eq("failed to get repository"))
            .times(1)
            .return_const(());

        output_one_organization_repository(
            mock_github_port,
            mock_display_port,
            &String::from("rust-lang"),
        )
        .await;
    }
}
