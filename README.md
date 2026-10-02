# OrderPricingSample
ECサイトを想定した簡易注文管理API。 顧客ランク、商品ごとのセール割引、注文単位の会員割引を組み合わせて注文金額を計算する。

Java 21 / Spring Boot 3.x / Maven / Spring Data JPA / H2

## 実行・テスト

```bash
mvn test
mvn spring-boot:run
```

## 業務ルール

### 顧客ランク
- `REGULAR`: 会員割引なし
- `GOLD`: 商品数量の合計が2個以上の場合、注文金額に10%の会員割引を適用

### 割引の適用順序(正)

1. 商品ごとのセール割引を適用する
2. セール適用後の商品金額を合計する
3. その合計金額(セール適用後)に対してGOLD会員割引を適用する

例: GOLD会員が Standard Keyboard (10,000円) x1 と Premium Mouse (5,000円・20%セール) x1 を購入する場合
- セール適用後: 10,000 + 4,000 = 14,000円
- 会員割引(10%): 1,400円
- 支払金額: 12,600円

## 初期データ

| Customer | Rank |
|----------|------|
| C001 | REGULAR |
| C002 | GOLD |

| Product | Name | Price | Sale |
|---------|------|------:|------|
| P001 | Standard Keyboard | 10000 | なし |
| P002 | Premium Mouse | 5000 | 20% |
| P003 | USB-C Dock | 15000 | なし |

## API

### POST /api/orders

Request:

```json
{
  "customerId": "C002",
  "items": [
    { "productId": "P001", "quantity": 2 }
  ]
}
```

Response (201 Created):

```json
{
  "orderId": 1,
  "customerId": "C002",
  "customerRank": "GOLD",
  "items": [
    { "productId": "P001", "productName": "Standard Keyboard", "unitPrice": 10000,
      "saleRatePercent": 0, "quantity": 2, "lineTotal": 20000 }
  ],
  "originalSubtotal": 20000,
  "saleDiscountTotal": 0,
  "subtotalAfterSale": 20000,
  "membershipDiscount": 2000,
  "totalAmount": 18000
}
```

エラー: 顧客・商品が存在しない場合は 404、入力不正(items空、quantity<1など)は 400。
