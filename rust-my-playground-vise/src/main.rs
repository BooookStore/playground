use anyhow::Result;
use mockall::automock;

fn main() {
    println!("Hello, world!");
}

fn find_customer<A: ServerAPort, B: ServerBPort>(
    server_a_port: A,
    server_b_port: B,
    purchase_number: i32, // 検索元の注文番号
) -> Result<String> {
    // 初めにServerAに問い合わせる
    let server_a_response: Result<String> =
        server_a_port.search_customer_by_purchase_number(purchase_number);

    // 顧客情報が見つかったら呼び出し元に返し処理を終える
    if server_a_response.is_ok() {
        return Ok(server_a_response.unwrap());
    }

    // 顧客情報が見つからなかった場合ServerBに問い合わせ、
    // 結果を呼び出し元に返し処理を終える
    server_b_port.find_customer_by_purchase_number(purchase_number)
}

// traitはJavaで言うInterfaceのようなもの
#[automock]
trait ServerAPort {
    fn search_customer_by_purchase_number(&self, number: i32) -> Result<String>;
}

#[automock]
trait ServerBPort {
    fn find_customer_by_purchase_number(&self, number: i32) -> Result<String>;
}

#[cfg(test)]
mod tests {
    use mockall::predicate::eq;

    use crate::{find_customer, MockServerAPort, MockServerBPort};

    #[test]
    fn get_customer_from_server_a() {
        // ServerAにアクセスする処理のテストダブル
        // 以下の設定は、12345の購入番号で検索するとbobという顧客が見つかる様にしており、
        // ServerAへ一度アクセスされることを検証する
        let mut mock_server_a_port = MockServerAPort::new();
        mock_server_a_port
            .expect_search_customer_by_purchase_number()
            .with(eq(12345))
            .returning(|_| Ok(String::from("bob")))
            .times(1);

        // ServerBにアクセスする処理のテストダブル
        // 以下の設定では、ServerBへのアクセスが発生しないことを検証する
        let mut mock_server_b_port = MockServerBPort::new();
        mock_server_b_port
            .expect_find_customer_by_purchase_number()
            .times(0);

        // テスト対象を呼び出し、顧客情報を得る
        let customer = find_customer(mock_server_a_port, mock_server_b_port, 12345);

        // 顧客が見つかることを検証する
        assert!(customer.is_ok());
        // 顧客がbobであることを検証する
        assert_eq!(String::from("bob"), customer.unwrap());
    }
}