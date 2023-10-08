# GET /order/{id}
* item-shop-apiのwiremockを設定する
* URL"/v1/order/ad86ffdd-d891-4261-b29b-76ee631c28fa"にGETリクエストを送る
* レスポンスステータスコードが"200"である

## オーダーIDを指定しオーダーを取得できる
* レスポンスのJSONの"$.id"が"ad86ffdd-d891-4261-b29b-76ee631c28fa"である
* レスポンスのJSONの"$.orderDateTime"が"2023-09-23T22:18:11"である

## ショップ情報を取得できる
* レスポンスのJSONの"$.shop.id"が"9870"である
* レスポンスのJSONの"$.shop.name"が"さいたま川越一号店"である
