# FD-KotlinJS-POCApp

_Serban Dragos-Cornel, Software Enginnering 1<sup>st</sup> year_

On this final assignment I have opted for designing a POC frontend web application using the Kotlin/JS extension of the
Kotlin programming language.

The app will be similar to an IMDB for the anime genre in which a user may select an anime, view its description, number of episodes, its trailer, and then they may mark it as already watched, or share the trailer link to other persons.

## React applications using Kotlin/JS

Regarding the number of applications for this Kotlin/JS extension, we may as well talk about writing React applications using the kotlin-wrappers provided by JetBrains, "_which provide convenient abstractions and deep integrations for one of the most popular JavaScript frameworks_". Kotlin-wrappers also provide support for other adjacent technologies such as react-redux, react-router, or styled-components.

This feature facilitates the writing of frontend web applications using Kotlin, as we are able to apply the same component reusability principles as in React, and also make use of its vast "ecosystem" of dependencies. What is expected of a developer who wants to implement such an application, is a basic knowledge of Kotlin, and little knowledge of HTML and CSS. "_Prior knowledge of the basic concepts behind React may be helpful in understanding some of the sample code, but is not strictly required._"

## Set up project

As a prerequisite for setting up a project, is to have the Intellij IDEA installed, along with the Kotlin plugin (version 14.3.0 or above).

The specific project type that needs to be selected is the following:

![image](https://user-images.githubusercontent.com/49645354/120811783-07484180-c555-11eb-9d60-085c0d938e2d.png)

After its creation, we may as well edit the gradle configuration, by adding the required dependencies. As such, for developing an application in React, we would need the following:

```java
implementation("org.jetbrains:kotlin-react:17.0.1-pre.148-kotlin-1.4.21")
implementation("org.jetbrains:kotlin-react-dom:17.0.1-pre.148-kotlin-1.4.21")

implementation(npm("react", "17.0.1"))
implementation(npm("react-dom", "17.0.1"))
```
The first two dependencies are the aforementioned JetBrains wrappers for react and react-dom, whereas the last two are the npm dependencies of the same modules. We will approach the npm imports in a future section.

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

Therefore, in the main() function of our app, we would have to integrate all the children (or wrappers of those), into the root div of our HTML page.

```kotlin
fun main() {
    render(document.getElementById("root")) {
        child(App) {}
    }
}
```
In this example, we are adding as a child only one component, namely App, that will comprise all the other React components and layouts we have introduced in this app.

### Writing components 

The React components defined with Kotlin/JS, may be defined as follows:

```kotlin
import react.*

@JsExport
class App : RComponent<RProps, RState>() {
  override fun RBuilder.render() {
        // typesafe HTML goes here!
    }
}
```
There is a class defined with the name App that will extend the RComponent class of our JetBrains react wrapper. This will have to override the render() function of the RBuilder. Inside that function, we may add any other children that the App component should contain.

#### Typesafe HTML

### Transition to functional components
### _useState_() and _useEffect_() hooks

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

package ReactUtils

import react.*

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

package UIUtils

import react.RClass
import react.RProps

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

### References
