use async_trait::async_trait;
use reqwest::Client;
use serde::Deserialize;

use crate::port::github::GitHubPort;

pub struct HttpGithubDriver {
    auth_token: String,
    client: Client,
}

impl HttpGithubDriver {
    pub fn new(auth_token: String) -> HttpGithubDriver {
        HttpGithubDriver {
            auth_token,
            client: Client::new(),
        }
    }
}

#[async_trait]
impl GitHubPort for HttpGithubDriver {
    async fn get_one_organization_repository(&self, organization_name: &str) -> String {
        #[derive(Deserialize, Debug)]
        struct ApiResponse {
            name: String,
        }

        let url = format!("http://localhost:8080/orgs/{}/repos", organization_name);
        let res = self
            .client
            .get(&url)
            .header("Accept", "application/vnd.github+json")
            .header("Authorization", &format!("Bearer {}", &self.auth_token))
            .header("X-Github-Api-Version", "2022-11-28")
            .send()
            .await
            .unwrap_or_else(|_| panic!("Failed http get request to {}", &url));

        let json = res
            .json::<Vec<ApiResponse>>()
            .await
            .expect("Failed to deserialize http response jseon");

        json.first()
            .expect("Unexpected repository size is 0")
            .name
            .clone()
    }
}
