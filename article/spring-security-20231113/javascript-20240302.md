関数宣言とArrow Functionのthisの違いについてのメモです。

以下のサイトに詳しく書いてあります。

- https://jsprimer.net
- https://jsprimer.net/basic/function-this/#function-without-arrow-function-this

関数宣言の場合、thisは動的に決まるとのこと。

ベースのオブジェクトがあればそのオブジェクトをthisとし、なければundefinedとなる、と。

ここで言うベースのオブジェクトとはブラケット演算子、ドット演算子の左側にあるオブジェクトのことです。

では、実験してみましょう。

環境はこちら。

```
❯ node --version
v20.11.1
```

REPLを使っていきます。ちょっと試したいときはREPLが使いやすいと個人的には思います。

次のようにthisをコンソールに出力してみます。

関数宣言の場合、ただ呼び出すとundefinedになります。これはベースとなるオブジェクトがないからですね。

``` javascript
> function hoge() {
... console.log(this);
... }
> hoge();
undefined
```

このhoge関数をhogeObjオブジェクトにセットしてみます。

そうすると、thisはhogeObjになりました。thisが実行時のコンテキストで動的に変わっているのがわかりました。

``` javascript
> const hogeObj = {
... hoge
... }
> hogeObj.hoge();
{ hoge: [Function: hoge] }
```

callを使えばベースとなるオブジェクトを指定しつつ呼び出すことができます。

``` javascript
> hoge.call({});
{}
```

同じようにArrow Functionでも試してみましょう。

``` javascript
> const fuga = () => {
... console.log(this);
... }
> fuga();
<ref *1> Object [global] {
  global: [Circular *1],
  clearImmediate: [Function: clearImmediate],
  setImmediate: [Function: setImmediate] {
    [Symbol(nodejs.util.promisify.custom)]: [Getter]
  },
  clearInterval: [Function: clearInterval],
  clearTimeout: [Function: clearTimeout],
  setInterval: [Function: setInterval],
  setTimeout: [Function: setTimeout] {
    [Symbol(nodejs.util.promisify.custom)]: [Getter]
  },
  queueMicrotask: [Function: queueMicrotask],
  structuredClone: [Function: structuredClone],
  atob: [Getter/Setter],
  btoa: [Getter/Setter],
  performance: [Getter/Setter],
  fetch: [Function: fetch],
  crypto: [Getter],
  hoge: [Function: hoge]
}
```

Arrow Functionの場合はthisを外側のスコープに探しに行きます。

node.jsの場合はglobalオブジェクトがグローバルスコープのthisのようです。

https://nodejs.org/dist/latest-v20.x/docs/api/globals.html#global-objects

そういえばdenoはどうなんだろう？

```
❯ deno --version
deno 1.41.0 (release, x86_64-unknown-linux-gnu)
v8 12.1.285.27
typescript 5.3.3
```

``` javascript
> this
Window {}
```

Windowオブジェクトが取れました。実行環境で違いますね。

話を戻してArrow Functionのベースオブジェクトを変更してみます。

``` javascript
> const fugaObj = {
... fuga
... }
> fugaObj.fuga();
<ref *1> Object [global] {
  global: [Circular *1],
  clearImmediate: [Function: clearImmediate],
  setImmediate: [Function: setImmediate] {
    [Symbol(nodejs.util.promisify.custom)]: [Getter]
  },
  clearInterval: [Function: clearInterval],
  clearTimeout: [Function: clearTimeout],
  setInterval: [Function: setInterval],
  setTimeout: [Function: setTimeout] {
    [Symbol(nodejs.util.promisify.custom)]: [Getter]
  },
  queueMicrotask: [Function: queueMicrotask],
  structuredClone: [Function: structuredClone],
  atob: [Getter/Setter],
  btoa: [Getter/Setter],
  performance: [Getter/Setter],
  fetch: [Function: fetch],
  crypto: [Getter],
  hoge: [Function: hoge]
}

> fuga.call({});
<ref *1> Object [global] {
  global: [Circular *1],
  clearImmediate: [Function: clearImmediate],
  setImmediate: [Function: setImmediate] {
    [Symbol(nodejs.util.promisify.custom)]: [Getter]
  },
  clearInterval: [Function: clearInterval],
  clearTimeout: [Function: clearTimeout],
  setInterval: [Function: setInterval],
  setTimeout: [Function: setTimeout] {
    [Symbol(nodejs.util.promisify.custom)]: [Getter]
  },
  queueMicrotask: [Function: queueMicrotask],
  structuredClone: [Function: structuredClone],
  atob: [Getter/Setter],
  btoa: [Getter/Setter],
  performance: [Getter/Setter],
  fetch: [Function: fetch],
  crypto: [Getter],
  hoge: [Function: hoge]
}
```

オブジェクトにセットしても、callをつかっても、結果が変わらないことがわかります。

というわけで、Arrow Functionの場合はthisが静的に決定されあとから変更することができないことがわかりました。

一方で、Arrow Function自体を動的に生成する場合、外側のthisが動的に変わるのであればもちろんArrow Functionが参照するthisも生成されるごとにことなるthisを参照することになります。

例えば次のようなケースです。

``` javascript
> const hoge = {
... fuga() {
...   return () => { console.log(this) };
... }
... }
> hoge.fuga()();
{ fuga: [Function: fuga] }
> hoge.fuga.call({})();
{}
```

hogeオブジェクトのfugaメソッドはArrow Functionを作成して返します。

Arrow Functionはthisを外側のスコープに探しに行きます。このときの外側のスコープにはfugaメソッドであり、fugaメソッドのthisはhogeオブジェクトです。

なので単純に `hoge.fuga()()` と呼び出すとArrow Functionのthisはhogeオブジェクトになりました。

ただし、fugaメソッドの呼び出しをcallで行いベースオブジェクトを変更するとArrow Functionのthisも変化します。

これはArrow Functionが作成されるとき、外側のスコープを探しに行くためです。

fugaメソッドのthisが変更されたのであれば変更されたthisをArrow Functionも探しに行くことになる、ということですね。

