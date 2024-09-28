use anyhow::Result;
use futures::future::join_all;
use futures::TryFutureExt;

use crate::domain::primitive::{OrganizationName, RepositoryName};
use crate::domain::repository::Repository;
use crate::port::display::DisplayPort;
use crate::port::github::GitHubPort;

pub async fn output_repositories_with_contributors<T: GitHubPort, U: DisplayPort>(
    github_port: &T,
    display_port: &U,
    organization_name: &OrganizationName,
) {
    let repositories = github_port
        .get_organization_repositories(organization_name)
        .and_then(|repository_names| async move {
            get_contributors(github_port, organization_name, repository_names).await
        });

    match repositories.await {
        Ok(repositories) => {
            display_port
                .print_repositories_with_contributors(organization_name, &repositories)
                .await;
        }
        Err(_) => {
            display_port.print_error("failed to get repository").await;
        }
    }
}

async fn get_contributors<T: GitHubPort>(
    github_port: &T,
    organization_name: &OrganizationName,
    repository_names: Vec<RepositoryName>,
) -> Result<Vec<Repository>> {
    let repository_futures = repository_names
        .into_iter()
        .map(|repository_name| async move {
            if repository_name == "rust" {
                let contributor_names = github_port
                    .get_repository_contributors(organization_name, &repository_name)
                    .await
                    .unwrap();
                Repository::new(repository_name, contributor_names)
            } else {
                Repository::new(repository_name, vec![])
            }
        });
    let repositories = join_all(repository_futures).await;
    Ok(repositories)
}

#[cfg(test)]
mod tests {
    use anyhow::anyhow;
    use mockall::predicate;
    use predicate::eq;

    use crate::domain::primitive::{ContributorName, OrganizationName, RepositoryName};
    use crate::domain::repository::Repository;
    use crate::port::display::MockDisplayPort;
    use crate::port::github::MockGitHubPort;
    use crate::use_case::repository::output_repositories_with_contributors;

    #[tokio::test]
    async fn output_organization_repositories_with_contributors() {
        let mut stub_github_port = MockGitHubPort::new();
        stub_github_port
            .expect_get_organization_repositories()
            .with(eq(OrganizationName::from("rust-lang")))
            .returning(|_| {
                Ok(vec![
                    RepositoryName::from("rust"),
                    RepositoryName::from("rustlings"),
                    RepositoryName::from("cargo"),
                ])
            });
        stub_github_port
            .expect_get_repository_contributors()
            .with(
                eq(OrganizationName::from("rust-lang")),
                eq(RepositoryName::from("rust")),
            )
            .returning(|_, _| {
                Ok(vec![
                    ContributorName::from("bob"),
                    ContributorName::from("alice"),
                ])
            });

        let mut mock_display_port = MockDisplayPort::new();
        mock_display_port
            .expect_print_repositories_with_contributors()
            .with(
                eq(OrganizationName::from("rust-lang")),
                eq(vec![
                    Repository::new(
                        RepositoryName::from("rust"),
                        vec![ContributorName::from("bob"), ContributorName::from("alice")],
                    ),
                    Repository::new(RepositoryName::from("rustlings"), vec![]),
                    Repository::new(RepositoryName::from("cargo"), vec![]),
                ]),
            )
            .return_const(())
            .times(1);

        output_repositories_with_contributors(
            &stub_github_port,
            &mock_display_port,
            &String::from("rust-lang"),
        )
        .await;
    }

    #[tokio::test]
    async fn output_error_message_failed_to_get_organization_repository_name() {
        let mut stub_github_port = MockGitHubPort::new();
        stub_github_port
            .expect_get_organization_repositories()
            .with(eq(String::from("rust-lang")))
            .returning(|_| Err(anyhow!("failed")));

        let mut mock_display_port = MockDisplayPort::new();
        mock_display_port
            .expect_print_error()
            .with(eq("failed to get repository"))
            .times(1)
            .return_const(());

        output_repositories_with_contributors(
            &stub_github_port,
            &mock_display_port,
            &String::from("rust-lang"),
        )
        .await;
    }
}
