use futures::executor::block_on;

fn main() {
    block_on(async_main());
}

async fn async_main() {
    let f1 = learn_and_sing();
    let f2 = dance();
    futures::join!(f1, f2);
}

type Song = String;

async fn learn_and_sing() {
    let song = learn_song().await;
    sing_song(song).await;
}

async fn learn_song() -> Song {
    println!("learn song: SONG");
    String::from("SONG")
}

async fn sing_song(song: Song) {
    println!("sing song: {song}");
}

async fn dance() {
    println!("dance");
}
