use async_trait::async_trait;

use crate::port::display::DisplayPort;

pub struct ConsoleDisplayDriver;

impl ConsoleDisplayDriver {
    pub fn new() -> Self {
        Self {}
    }
}

#[async_trait]
impl DisplayPort for ConsoleDisplayDriver {
    async fn print_repository_with_organization(
        &self,
        _organization_name: &str,
        _repository_name: &str,
    ) {
        todo!()
    }
}
