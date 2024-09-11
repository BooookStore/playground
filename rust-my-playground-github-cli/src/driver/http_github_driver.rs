use anyhow::{anyhow, Result};
use async_trait::async_trait;
use log::{debug, error};
use reqwest::{Client, StatusCode};
use serde::Deserialize;

use crate::domain::primitive::{ContributorName, OrganizationName, RepositoryName};
use crate::port::github::GitHubPort;

#[cfg(feature = "production")]
const GITHUB_URL: &str = "https://api.github.com";

#[cfg(not(feature = "production"))]
const GITHUB_URL: &str = "http://localhost:8080";

pub struct HttpGithubDriver {
    bearer_token: String,
    user_agent: String,
    client: Client,
}

impl HttpGithubDriver {
    pub fn new(auth_token: String, user_name: String) -> HttpGithubDriver {
        HttpGithubDriver {
            bearer_token: format!("Bearer {}", auth_token),
            user_agent: user_name,
            client: Client::new(),
        }
    }
}

#[async_trait]
impl GitHubPort for HttpGithubDriver {
    async fn get_organization_repositories(
        &self,
        organization_name: &OrganizationName,
    ) -> Result<Vec<RepositoryName>> {
        #[derive(Deserialize, Debug)]
        struct ApiResponse {
            name: String,
        }

        impl ApiResponse {
            fn repository_name(self) -> RepositoryName {
                self.name
            }
        }

        let url = format!("{}/orgs/{}/repos", GITHUB_URL, organization_name);
        debug!("http request to url: {}", url);

        let res = self
            .client
            .get(&url)
            .header("User-Agent", &self.user_agent)
            .header("Accept", "application/vnd.github+json")
            .header("Authorization", &self.bearer_token)
            .header("X-Github-Api-Version", "2022-11-28")
            .send()
            .await?;

        if !res.status().eq(&StatusCode::OK) {
            error!("http status code unexpected: {}", res.status());
            return Err(anyhow!("Unexpected status code returned"));
        }
        debug!("http status is ok");

        let api_responses = res
            .json::<Vec<ApiResponse>>()
            .await
            .inspect_err(|_| error!("unexpected json structure returned"))?;

        Ok(api_responses
            .into_iter()
            .map(ApiResponse::repository_name)
            .collect())
    }

    async fn get_repository_contributors(
        &self,
        _organization_name: &OrganizationName,
        _repository_name: &RepositoryName,
    ) -> Result<Vec<ContributorName>> {
        todo!()
    }
}
