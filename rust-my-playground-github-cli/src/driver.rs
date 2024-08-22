use crate::port::GitHubPort;

pub struct HttpGithubDriver {
    auth_token: String,
}

impl HttpGithubDriver {
    pub fn new(auth_token: String) -> HttpGithubDriver {
        HttpGithubDriver { auth_token }
    }
}

impl GitHubPort for HttpGithubDriver {
    fn get_organization_repositories(&self, organization_name: &str) {
        println!("cargo");
    }
}
