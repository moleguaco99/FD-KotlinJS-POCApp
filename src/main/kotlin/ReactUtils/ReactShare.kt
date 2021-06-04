@file:JsModule("react-share")
@file:JsNonModule

package ReactUtils

import react.RClass
import react.RProps

@JsName("EmailIcon")
external val emailIcon: RClass<IconProps>

@JsName("EmailShareButton")
external val emailShareButton: RClass<ShareButtonProps>

@JsName("FacebookIcon")
external val facebookIcon: RClass<IconProps>

@JsName("FacebookShareButton")
external val facebookButton: RClass<ShareButtonProps>

@JsName("WhatsappIcon")
external val whatsappIcon: RClass<IconProps>

@JsName("WhatsappShareButton")
external val whatsappButton: RClass<ShareButtonProps>

@JsName("FacebookMessengerIcon")
external val messengerIcon: RClass<IconProps>

@JsName("FacebookMessengerShareButton")
external val messengerButton: RClass<ShareButtonProps>


external interface ShareButtonProps : RProps {
    var url: String
}

external interface IconProps : RProps {
    var size: Int
    var round: Boolean

}
