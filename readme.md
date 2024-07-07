<h1 align="center">Compose Searchable Dropdown</h1><br>

<div align="center">
<a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
<a href="https://android-arsenal.com/api?level=21" rel="nofollow"><img alt="API" src="https://camo.githubusercontent.com/0eda703da08220e08354f624a3fc0023f10416a302565c69c3759bf6e0800d40/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f4150492d32312532422d627269676874677265656e2e7376673f7374796c653d666c6174" data-canonical-src="https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat" style="max-width: 100%;"></a>
  <img src="https://img.shields.io/badge/Platform-Android-brightgreen.svg?logo=android" alt="Badge Android" />
		<img src="https://img.shields.io/badge/Platform-iOS%20%2F%20macOS-lightgrey.svg?logo=apple" alt="Badge iOS" />
		<img src="https://img.shields.io/badge/Platform-JVM-8A2BE2.svg?logo=openjdk" alt="Badge JVM" />
    <img src="https://img.shields.io/badge/Platform-WASM%20%2F%20JS-yellow.svg?logo=javascript" alt="Badge JS" />
<a href="https://github.com/the-best-is-best/"><img alt="Profile" src="https://img.shields.io/badge/github-%23181717.svg?&style=for-the-badge&logo=github&logoColor=white" height="20"/></a>
<a href="https://central.sonatype.com/search?q=io.github.the-best-is-best&smo=true"><img alt="Maven Central" src="https://img.shields.io/maven-central/v/io.github.the-best-is-best/ComposeSearchableDropdown"/></a>
</div>

Compose Searchable Dropdown is a Jetpack Compose library for Android that provides a dropdown with a search bar.

## Download

[![Maven Central](https://img.shields.io/maven-central/v/io.github.the-best-is-best/ComposeSearchableDropdown)](https://central.sonatype.com/artifact/io.github.the-best-is-best/ComposeSearchableDropdown)

Compose Searchable Dropdown is available on `mavenCentral()`.

```kotlin
implementation("io.github.the-best-is-best:ComposeSearchableDropdown:2.0.2")
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