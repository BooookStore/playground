use crate::domain::primitive::{ContributorName, RepositoryName};

pub struct Repository {
    pub name: RepositoryName,
    pub contributors: Vec<ContributorName>,
}
