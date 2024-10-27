<h1 align="center">Compose Searchable Dropdown</h1><br>

<div align="center">
<a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
<a href="https://android-arsenal.com/api?level=21" rel="nofollow">
    <img alt="API" src="https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat" style="max-width: 100%;">
</a>
<img src="https://img.shields.io/badge/Platform-Android-brightgreen.svg?logo=android" alt="Badge Android" />
		<img src="https://img.shields.io/badge/Platform-iOS%20%2F%20macOS-lightgrey.svg?logo=apple" alt="Badge iOS" />
		<img src="https://img.shields.io/badge/Platform-JVM-8A2BE2.svg?logo=openjdk" alt="Badge JVM" />
    <img src="https://img.shields.io/badge/Platform-WASM%20%2F%20JS-yellow.svg?logo=javascript" alt="Badge JS" />
<a href="https://github.com/the-best-is-best/"><img alt="Profile" src="https://img.shields.io/badge/github-%23181717.svg?&style=for-the-badge&logo=github&logoColor=white" height="20"/></a>

</div>

Compose Searchable Dropdown is a Jetpack Compose library for Android that provides a dropdown with a search bar.

## Download

[![Maven Central](https://img.shields.io/maven-central/v/io.github.the-best-is-best/ComposeSearchableDropdown)](https://central.sonatype.com/artifact/io.github.the-best-is-best/ComposeSearchableDropdown)

Compose Searchable Dropdown is available on `mavenCentral()`.

```kotlin
implementation("io.github.the-best-is-best:ComposeSearchableDropdown:2.1.1")
```

## v 2.0.5

- fix jvm not implementation and need change gradle.properties check if add this

```groovy
kotlin.native.cacheKind = none
compose.kotlin.native.manageCacheKind = false
```

## v 2.0.2 

- support compose and kotlin multiplatform

## v 2.0.1

- The `searchIn` parameter can now be null (if null the search bar will be hidden)

## how to use v 2

```kotlin
data class ExampleData(
    val id: Int,
    val name: String
)

private val data = listOf(
    ExampleData(1, "First"),
    ExampleData(2, "Second"),
    ExampleData(3, "Third"),
    ExampleData(4, "Fourth"),
    ExampleData(5, "Fifth"),
)

val selectedItemsState = rememberDropdownStates<ExampleData>(value = data[2])

Column{

    SearchableDropDown(
        listOfItems = data,
        state = selectedItemsState,
        placeholder = {
            Text(text = "اختار")
        },
        searchPlaceHolder = {
            Text(text = "بحث")
        },
        onDropDownItemSelected = {
            Log.d("get v", it.name)
        },
        dropdownItem = {
            Text("${it.id} - ${it.name}", fontSize = 20.sp)
        },
        selectedOptionTextDisplay = {it.name},
        searchIn = {
            it.name
        }
    )

}
```
- Can clear state

```kotlin
    selectedItemsState.clear()


```
## How to use v < 2  

```kotlin
data class ExampleData(
    val id: Int,
    val name: String
)

private val data = listOf(
    ExampleData(1, "First"),
    ExampleData(2, "Second"),
    ExampleData(3, "Third"),
    ExampleData(4, "Fourth"),
    ExampleData(5, "Fifth"),
)



SearchableDropDown(
    listOfItems = data,
    placeholder = {
        Text(text = "Select an item")
    },
    searchPlaceHolder = {
        Text(text = "Search an item")
    },

    defaultItem = data[2],
    onDropDownItemSelected = {
        Log.d("get v", it.name)
    },
    dropdownItem = {
        Text("${it.id} - ${it.name}")
    },
    selectedOptionTextDisplay = {it.name},
    searchIn = {it.name} // or it.id
)
```

<br></br>

#### `listOfItems` add your list data
#### `placeholder` add your placeholder for text field
#### `searchPlaceHolder` add your placeholder for search field
#### `defaultItem` add your default item
#### `onDropDownItemSelected` add your callback when item selected and receive item selected
#### `dropdownItem` add your item view like it.name or any thing will display in search dropdown
#### `selectedOptionTextDisplay` add your item view like it.name or any thing will display in text field
#### `searchIn` add your item like it.name or any thing will search in it