# FD-KotlinJS-POCApp

_Serban Dragos-Cornel, Software Enginnering 1<sup>st</sup> year_

On this final assignment I have opted for designing a POC frontend web application using the Kotlin/JS extension of the
Kotlin programming language.

The app will be similar to an IMDB for the anime genre in which a user may select an anime, view its description, number of episodes, its trailer, and then they may mark it as already watched, or share the trailer link to other persons.

## React applications using Kotlin/JS

Regarding the number of applications for this Kotlin/JS extension, we may as well talk about writing React applications using the kotlin-wrappers provided by JetBrains, "_which provide convenient abstractions and deep integrations for one of the most popular JavaScript frameworks_". Kotlin-wrappers also provide support for other adjacent technologies such as react-redux, react-router, or styled-components.

This feature facilitates the writing of frontend web applications using Kotlin, as we are able to apply the same component reusability principles as in React, and also make use of its vast "ecosystem" of dependencies. What is expected of a developer who wants to implement such an application, is a basic knowledge of Kotlin, and little knowledge of HTML and CSS. "_Prior knowledge of the basic concepts behind React may be helpful in understanding some of the sample code, but is not strictly required._"

## Usage

As a prerequisite for accessing the project, we need to have the Intellij IDEA installed, along with the Kotlin plugin (version 14.3.0 or above).

### Run the development server

In order to start the development browser server, the following gradle task can be run.

![image](https://user-images.githubusercontent.com/49645354/120811832-1929e480-c555-11eb-8edb-c80b1fd0302c.png)

The _gradlew run_ command in the terminal will also do. Still, there is room for improvement.

### Continuous run

So we wouldn't have to restart the application every time a modification is made, we also have the hot reload/continuous run feature. In order to enable the hot reload for this web app, we can either use the gradle configuration for "BrowserDevelopmentRun in continuous mode":

![image](https://user-images.githubusercontent.com/49645354/120806371-7327ab80-c54f-11eb-8037-7b48f092a0cd.png)

Or we can simply write in the terminal the following command: _gradlew run --continuous_. With the application starting this way, everytime a new change will be saved in the project, a new build will be triggered, and the page will be refreshed accordingly.

## React environment

Firstly, we would need a HTML page in which we would embed the JavaScript file that is generated with our code (FD-KotlinJS-POCApp.js). This is represented by the index.html file in the main/resources package.

```HTML
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>JS Client</title>
</head>
<body>
<div id="root"></div>
<script src="FD-KotlinJS-POCApp.js"></script>
</body>
</html>
```

Therefore, inside the main() function of our app, we would have to integrate all the children (or wrappers of those), into the root div of our HTML page.

```kotlin
fun main() {
    render(document.getElementById("root")) {
        child(App) {}
    }
}
```
In this example, we are adding as a child only one component, namely App, that will comprise all the other React components and layouts we have introduced in this app.

### Writing components 

The React components written with Kotlin/JS, may be defined as follows:

```kotlin
import react.*

@JsExport
class App : RComponent<RProps, RState>() {
  override fun RBuilder.render() {
        // typesafe HTML goes here!
    }
}
```
The App component is a class extending the RComponent class of our JetBrains react wrapper. App will have to override the render() function of the RBuilder. Inside that function, we may add any other children that the App component should contain, be it HTML or other React components.

#### Typesafe HTML

_"Kotlin's support for Domain Specific Languages (DSLs), a feature provided to us through kotlin-react, allows us to describe a markup language like HTML using a syntax that is easy to read (and hopefully also to write) for those familiar with HTML."_

Differently from the HTML tags, the HTML syntax that may be used in KotlinJS, looks as the following:

```kotlin
div {
    h3 {
        +"Example header"
    }
    ...
} 
```

#### Typesafe CSS

There is also a wrapper for the styled-components of React, namely, kotlin-styled, which allows users to also stylize their HTML components.
Differently from writing the stylesheets for every HTML component, the CSS syntax will now be:

```kotlin
styledDiv {
    css {
        position = Position.absolute
        top = 15.px
        right = 15.px
    }
    h3 {
        +"Example header"
    }
}
```
### Props and State

Similarly to the React class components, they allow the passing of props, and keeping an internal state. These may be simply reffered to as RProps and RState, when creating the component class, or we may create interfaces for these, as to know exactly what props we are expecting and what state variables we'd be using in these components. 

```kotlin
external interface AppProps : RProps {
    ...
}
external interface AppState: RState {
    ...
}

class App  : RComponent<AppProps, AppState> {
    override fun RBuilder.render(){
        ...
    }
}
```

### Transition to functional components

Still, for this project, I have opted for the usage of functional components, which has been recently introduced in Kotlin/JS. This feature allows the developers to discard the class components, which are a lot more complicated and harder to maintain than the functional ones.

The definition of a functional component is less complicated than the one of a class, it will only receive the props as parameter, and then the components' children may be added for rendering (we add them to the component's builder). The component's properties, may be received as well under the form of RProps (accept any props), or under the form of an interface.

```kotlin

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
    ...
```

In the event of needing an equivalent for a class' state, there is an option for this, under the form of the _useState_() hook.

### _useState_() and _useEffect_() hooks

As it can be seen in the previous section, the _useState_ hook is used the same as the one in React, its call will return a pair consisting of a reference to a state variable, and a function that is used to set the state. Differently from the state of a React class component, the update of those variables won't necessitate the call of the _setState_ lambda function.

In the upper example the declared state variables are currentAnime, watchedAnimes and unwatchedAnimes. The first one defines the anime that is currently selected by the user for info view, whereas the latest are the animes that have been marked as watched by the user, apart from those that haven't been used yet.

Additionally, there is one more hook we can put to use, when we want to provide some data and cause side-effects to the respective component. This would be the _useEffect_() hook. This hook receives a list of variables as parameter, and whenever one of these variables is updated, the _useEffect_ will also be triggered. 

Its usage in this application is at the App component's rendering due to the fact that it has an empty list as a parameter, meaning it will be called only once. It will set the list of unwatched anime, with the anime list that will be retrieved from the API (details in the last section).

## Import npm dependencies

One of the most useful features of Kotlin/JS is related to the possibility of making use of the vast number of npm modules designed for React development.

npm dependencies can be added to the gradle build file via the npm function. The yarn installation managed by the Gradle plugin will take care of downloading and installing those dependencies.

```kotlin
implementation(npm("react-youtube-lite", "1.0.1"))
implementation(npm("react-share", "~4.2.1"))
```

As we want to use this module inside the Kotlin project, we need to create a separate file for it, add the proper annotations for importing the js module, and make the component available through a wrapper. 

```kotlin
@file:JsModule("react-youtube-lite")
@file:JsNonModule

...
@JsName("ReactYouTubeLite")
external val reactPlayer: RClass<ReactYouTubeProps>

external interface ReactYouTubeProps: RProps {
    var url: String
}
```
Similarly to normal components, we can create an interface extending RProps that will define the properties that the reactPlayer component allows.

### Material-UI inclusion

In order to make the application a lot more appealing, we may as well use this feature to import the @material-ui/core dependency into the project. As we want to use some of this framework's modules, we will have to follow the previous example, and create a wrapper for each desired component. 

![image](https://user-images.githubusercontent.com/49645354/120801794-4f159b80-c54a-11eb-9c90-8aa7b9eabd49.png)


```kotlin
@file:JsModule("@material-ui/core/Card")																																
@file:JsNonModule

...
@JsName("default")
external val AnimeCard : RClass<RProps>
```
We may as well notice the usage of the "default" js name, due to the Card component being exported as default.

Opposed to the first example, now we won't define the props interface for these components, but we can let the programmer set those props dynamically at the component's instantiation.

```kotlin
AnimeCard {
   CardAction {
      attrs.asDynamic().onClick = {
         props.onSelectAnime(anime)
      }
 ...
```

## Fetch API

Nevertheless, we have the option to request data from a REST API, using the fetch function. As a data provider, I am using a local .NET Core API that returns an object having the same fields as our Anime interface through the <code>comics</code> endpoint.

An anime can be fetched from the API through the following function:

```kotlin
suspend fun fetchAnime(id: Int): Anime {
    val response = window.fetch("http://192.168.0.101:5000/comics/$id").await().json().await()
    return response as Anime
}
```
As we are fetching the anime, based on an id, we are waiting for the response to be available and then turning it into a JSON. As the latter operation ends, we are returning a cast of the response to the Anime interface.
We have a total of 5 anime, and we'd want to get those at once, as such, we will use the async functionality of the coroutines.

```kotlin
suspend fun fetchAnimes(): List<Anime> = coroutineScope {
    (1..5).map { id ->
        async {
            fetchAnime(id)
        }
    }.awaitAll()
}
```

Finally, this fetchAnimes() function will be called in the App component's mount (usage of _useEffect_() hook).

```kotlin
    useEffect(emptyList()) {
        MainScope().launch {
            val animes = fetchAnimes()
            setUnwatchedAnimes(animes)
        }
    }

```

## Features

- See list of anime

![Screenshot (579)](https://user-images.githubusercontent.com/49645354/120834213-64e78880-c56b-11eb-8edd-82e5dfbc3516.png)

- Video player accessible when selecting anime card

![Screenshot (580)](https://user-images.githubusercontent.com/49645354/120834230-6a44d300-c56b-11eb-8b42-08c6f25cc2a8.png)

- Sharing the video to social media

![Screenshot (581)](https://user-images.githubusercontent.com/49645354/120834320-86e10b00-c56b-11eb-91bb-55cce87c21ca.png)

- Mark anime as seen

![Screenshot (583)](https://user-images.githubusercontent.com/49645354/120834380-9bbd9e80-c56b-11eb-9991-e268573e34a9.png)


### References

- https://kotlinlang.org/docs/js-get-started.html
- https://kotlinlang.org/docs/js-hands-ons.html
