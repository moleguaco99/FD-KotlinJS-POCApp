@file:JsModule("react-youtube-lite")
@file:JsNonModule

package ReactUtils

import react.*

@JsName("ReactYouTubeLite")
external val reactPlayer: RClass<ReactYouTubeProps>

external interface ReactYouTubeProps: RProps {
    var url: String
}