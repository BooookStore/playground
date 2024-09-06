use anyhow::{anyhow, Result};
use async_trait::async_trait;
use log::{debug, error};
use reqwest::{Client, StatusCode};
use serde::Deserialize;

use crate::domain::primitive::OrganizationName;
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
    async fn get_one_organization_repository(
        &self,
        organization_name: &OrganizationName,
    ) -> Result<String> {
        #[derive(Deserialize, Debug)]
        struct ApiResponse {
            name: String,
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

        let name = res
            .json::<Vec<ApiResponse>>()
            .await?
            .first()
            .ok_or(anyhow!("Unexpected json structure"))?
            .name
            .clone();

        Ok(name)
    }
}
