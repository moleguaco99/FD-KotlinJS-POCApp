import UIUtils.Appbar
import UIUtils.Toolbar
import UIUtils.Typography
import kotlinx.browser.window
import kotlinx.coroutines.*
import kotlinx.css.*
import react.RProps
import react.dom.div
import react.functionalComponent
import react.useEffect
import react.useState
import styled.css
import styled.styledDiv
import styled.styledH3

val App = functionalComponent<RProps>  {
    val (currentAnime, setCurrentAnime) = useState<Anime?>(null)
    val (unwatchedAnimes, setUnwatchedAnimes) = useState<List<Anime>>(listOf())
    val (watchedAnimes, setWatchedAnimes) = useState<List<Anime>>(listOf())

    useEffect(emptyList()) {
        MainScope().launch {
            val animes = fetchAnimes()
            setUnwatchedAnimes(animes)
        }
    }

    div {
        Appbar {
            Toolbar {
                Typography {
                    attrs.asDynamic().variant = "h6"
                    +"Welcome to our page!"
                }
            }
        }

        styledDiv {
            css{
                display = Display.flex
                marginTop = 100.px
                marginLeft = 50.px
            }
            div {
                styledH3 {
                    css {
                        color = Color.darkSalmon
                        fontFamily = "Arial"
                        fontWeight = FontWeight.inherit
                    }
                    +"Please try some of our best!"
                }

                videoList {
                    animes = unwatchedAnimes
                    selectedAnime = currentAnime
                    onSelectAnime = { anime ->
                        setCurrentAnime(anime)
                    }
                }
            }
            div {
                styledH3 {
                    css {
                        color = Color.blueViolet
                        fontFamily = "Arial"
                        fontWeight = FontWeight.inherit
                    }
                    +"Watched anime"
                }
                videoList {
                    animes = watchedAnimes
                    selectedAnime = currentAnime
                    onSelectAnime = { anime ->
                        setCurrentAnime(anime)
                    }
                }
            }
        }
        currentAnime?.let { currentAnime ->
            videoPlayer {
                anime = currentAnime
                unwatchedAnime = currentAnime in unwatchedAnimes
                onWatchedButtonPressed = {
                    if(anime in unwatchedAnimes){
                        setUnwatchedAnimes(unwatchedAnimes - anime)
                        setWatchedAnimes(watchedAnimes + anime)
                        }
                    }
                }
            }
        }
}


suspend fun fetchAnimes(): List<Anime> = coroutineScope {
    (1..5).map { id ->
        async {
            fetchAnime(id)
        }
    }.awaitAll()
}

suspend fun fetchAnime(id: Int): Anime {
    val response = window.fetch("http://192.168.0.101:5000/comics/$id").await().json().await()
    console.log(response)
    return response as Anime
}