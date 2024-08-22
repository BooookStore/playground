pub trait GitHubPort {
    fn get_organization_repositories(&self, organization_name: &str);
}
