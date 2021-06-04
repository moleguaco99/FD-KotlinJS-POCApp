import ReactUtils.*
import kotlinx.css.*
import kotlinx.html.js.*
import react.*
import react.dom.*
import styled.*

external interface VideoPlayerProps : RProps {
    var anime: Anime
    var onWatchedButtonPressed: (Anime) -> Unit
    var unwatchedAnime: Boolean
}

val VideoPlayer = functionalComponent<VideoPlayerProps> { props ->
    styledDiv {
        css{
            position = Position.relative
            left = 150.px
            width = 100.em
        }
        h3 {
            +"${props.anime.title}"
        }
        styledDiv {
            css {
                display = Display.flex
                marginBottom = 10.px
            }
            styledButton {
                css {
                    marginRight = 10.px
                    backgroundColor = if (props.unwatchedAnime) Color.lawnGreen else Color.pink
                }
                attrs {
                    onClickFunction = {
                        props.onWatchedButtonPressed(props.anime)
                    }
                }
                if (props.unwatchedAnime) {
                    +"Mark as watched"
                } else {
                    +"You have already watched this anime"
                }
            }
            styledDiv {
                css{
                    marginRight = 5.px
                }
                facebookButton {
                    attrs.url = props.anime.videoUrl
                    facebookIcon {
                        attrs.size = 32
                        attrs.round = true
                    }
                }
            }
            styledDiv {
                css{
                    marginRight = 5.px
                }
                messengerButton {
                    attrs.url = props.anime.videoUrl
                    messengerIcon {
                        attrs.size = 32
                        attrs.round = true
                    }
                }
            }
            styledDiv {
                css{
                    marginRight = 5.px
                }
                whatsappButton {
                    attrs.url = props.anime.videoUrl
                    whatsappIcon {
                        attrs.size = 32
                        attrs.round = true
                    }
                }
            }
            styledDiv {
                css{
                    marginRight = 5.px
                }
                emailShareButton {
                    attrs.url = props.anime.videoUrl
                    emailIcon {
                        attrs.size = 32
                        attrs.round = true
                    }
                }
            }
        }
        reactPlayer {
            attrs.url = props.anime.videoUrl
        }
        p{
            h3 {
                +"Description:"
            }
            +"${props.anime.description}"
        }
        p{
            h3 {
                +"Number of episodes:"
            }
            +"${props.anime.numberOfEpisodes}"
        }

    }
}

fun RBuilder.videoPlayer(handler: VideoPlayerProps.() -> Unit): ReactElement {
    return child(VideoPlayer) {
        this.attrs(handler)
    }
}