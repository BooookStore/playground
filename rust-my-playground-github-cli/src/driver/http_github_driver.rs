use async_trait::async_trait;
use reqwest::Client;
use serde::Deserialize;

use crate::port::github::GitHubPort;

pub struct HttpGithubDriver {
    _auth_token: String,
    client: Client,
}

impl HttpGithubDriver {
    pub fn new(auth_token: String) -> HttpGithubDriver {
        HttpGithubDriver {
            _auth_token: auth_token,
            client: Client::new(),
        }
    }
}

#[async_trait]
impl GitHubPort for HttpGithubDriver {
    async fn get_one_organization_repository(&self, _organization_name: &str) -> String {
        #[derive(Deserialize, Debug)]
        struct ApiResponse {
            name: String,
        }

        let res = self
            .client
            .get("http://localhost:8080/orgs/rust-lang/repos")
            .header("Accept", "application/vnd.github+json")
            .header("Authorization", "Bearer fake-token")
            .header("X-Github-Api-Version", "2022-11-28")
            .send()
            .await
            .expect("Failed http get request to orgs/rust-lang/repos");

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
