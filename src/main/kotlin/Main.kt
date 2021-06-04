import kotlinx.browser.document
import react.child
import react.dom.*

fun main() {
    render(document.getElementById("root")) {
        child(App) {}
    }
}