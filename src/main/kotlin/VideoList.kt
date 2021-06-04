
import UIUtils.AnimeCard
import UIUtils.AnimeCardContent
import UIUtils.CardAction
import UIUtils.Typography
import kotlinx.css.*
import react.*
import react.dom.*
import styled.css
import styled.styledDiv
import styled.styledImg

external interface VideoListProps : RProps {
    var animes: List<Anime>
    var selectedAnime: Anime?
    var onSelectAnime: (Anime) -> Unit
}

val VideoList = functionalComponent<VideoListProps> { props ->
    styledDiv {
        css{
            display = Display.flex
        }
        for (anime in props.animes) {
            styledDiv {
                css {
                    width = 235.px
                    marginBottom = 20.px
                    marginRight = 20.px
                }
                AnimeCard {
                    CardAction {
                        attrs.asDynamic().onClick = {
                                props.onSelectAnime(anime)
                        }
                        AnimeCardContent {
                            styledImg {
                                css {
                                    height = 300.px
                                    width = 200.px
                                    overflow = Overflow.hidden
                                }
                                attrs {
                                    src =
                                        "${anime.imageUrl}"
                                }
                            }
                            Typography {
                                attrs.asDynamic().variant = "subtitle1"
                                +anime.title
                            }
                        }
                    }
                }
            }
        }
    }
}

fun RBuilder.videoList(handler: VideoListProps.() -> Unit): ReactElement {
    return child(VideoList) {
        this.attrs(handler)
    }
}
