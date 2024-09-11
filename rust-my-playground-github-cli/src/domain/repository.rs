use crate::domain::primitive::{ContributorName, RepositoryName};

#[derive(PartialEq, Debug)]
pub struct Repository {
    pub name: RepositoryName,
    pub contributors: Vec<ContributorName>,
}

impl Repository {
    pub fn new(name: RepositoryName, contributors: Vec<ContributorName>) -> Self {
        Repository { name, contributors }
    }
}
