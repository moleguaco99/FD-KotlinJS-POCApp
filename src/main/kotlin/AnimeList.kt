
import UIUtils.AnimeCard
import UIUtils.AnimeCardContent
import UIUtils.CardAction
import UIUtils.Typography
import kotlinx.css.*
import react.*
import styled.css
import styled.styledDiv
import styled.styledImg

external interface AnimeListProps : RProps {
    var animes: List<Anime>
    var selectedAnime: Anime?
    var onSelectAnime: (Anime) -> Unit
}

val AnimeList = functionalComponent<AnimeListProps> { props ->
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
                                    src = anime.imageUrl
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

fun RBuilder.animeList(handler: AnimeListProps.() -> Unit): ReactElement {
    return child(AnimeList) {
        this.attrs(handler)
    }
}
