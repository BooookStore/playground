use async_trait::async_trait;
use reqwest::{Client, StatusCode};

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
        let res = self
            .client
            .get("http://localhost:8080/orgs/rust-lang/repos")
            .header("Accept", "application/vnd.github+json")
            .header("Authorization", "Bearer fake-token")
            .header("X-Github-Api-Version", "2022-11-28")
            .send()
            .await;

        assert_eq!(res.unwrap().status(), StatusCode::OK);

        "cargo".to_string()
    }
}
