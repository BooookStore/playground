use anyhow::Result;
use async_trait::async_trait;
use reqwest::Client;
use serde::Deserialize;

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
    async fn get_one_organization_repository(&self, organization_name: &str) -> Result<String> {
        #[derive(Deserialize, Debug)]
        struct ApiResponse {
            name: String,
        }

        let url = format!("{}/orgs/{}/repos", GITHUB_URL, organization_name);
        let res = self
            .client
            .get(&url)
            .header("User-Agent", &self.user_agent)
            .header("Accept", "application/vnd.github+json")
            .header("Authorization", &self.bearer_token)
            .header("X-Github-Api-Version", "2022-11-28")
            .send()
            .await
            .unwrap_or_else(|_| panic!("Failed http get request to {}", &url));

        let json = res
            .json::<Vec<ApiResponse>>()
            .await
            .expect("Failed to deserialize http response jseon");

        Ok(json
            .first()
            .expect("Unexpected repository size is 0")
            .name
            .clone())
    }
}
