# SpringSecurityにおけるAuthenticationとは

ここ何日かSpringSecurityで遊んでいるのですが、Authenticationについて腑に落ちたのでメモを残します。

## Authentication is 何

Authenticationとは名前の通り認証を表現するものです。認証処理の過程でその実装クラスに応じ処理を切り分ける仕組みとなっています（AuthenticationManager、AuthenticationProvider）。

一般的なユーザー名/パスワードのログインケースで考えてみましょう。まず認証処理を開始するためにAuthenticationを作成します。この時、Authenticationの状態は「未認証」です。ユーザが送ってきたユーザ名/パスワードの検証に成功すれば、Authenticationの状態が「認証済み」に変わります。基本的にこの状態を変更する仕事を担うのがAuthenticationProviderです。

※ 未認証のユーザを表す[AnonimousAuthenticationToken](https://spring.pleiades.io/spring-security/reference/servlet/authentication/anonymous.html)というものもあるのですが、あまり意識する必要がないのと、話が不用意に複雑になると思うのでここでは掘り下げません。

Authenticationはclassではなくinterfaceとして定義されています。なので、何かしら実装が必要になるわけですが、これはSpringSecurityが備えているクラスを使うこともできますし独自に定義することもできます。

Authenticationの定義は次のとおりです。

``` java
public interface Authentication extends Principal, Serializable {
	Collection<? extends GrantedAuthority> getAuthorities();
	Object getCredentials();
	Object getDetails();
	Object getPrincipal();
	boolean isAuthenticated();
	void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException;
}
```

https://github.com/spring-projects/spring-security/blob/040b87911946b40d290141b93bdee20a54391c34/core/src/main/java/org/springframework/security/core/Authentication.java

ユーザの認証状態は `isAuthenticated()` メソッドで確認する決まりです。

## Authentication の具体的な実装を読んでみよう

ユーザ名/パスワードで認証をする時によく使われるクラスが `UsernamePasswordAuthenticationToken` です。このクラスは `AbstractAuthenticationToken` クラスを継承し、それ経由で `Authentication` を実装しています。

https://github.com/spring-projects/spring-security/blob/040b87911946b40d290141b93bdee20a54391c34/core/src/main/java/org/springframework/security/authentication/UsernamePasswordAuthenticationToken.java