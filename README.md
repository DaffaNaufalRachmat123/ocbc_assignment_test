
# OCBC Assignment Test

This project was made by me to complete assignment test from OCBC Bank Singapore




## Architecture

- MVVM
- Kotlin Coroutines
- RxJava
- Modularization

## Unit Testing

I've been write some Unit Test in this application with Mockito , Koin and ViewModel

- Register Test 
- Login Test
- Get Balance Test
- Get Payees Test
- Get Transactions Test
- Make Transfer to spesific payee

## Modularization

To keep the code in this application and easily to read for other people and maintainable.
I separate the features with different modules

- app - contains all of the config and all of modules
- core - contains the core of this app like Networking and User Settings
- common - contains custom widget and custom value or custom type
- features - contains the feature like login , register , home ,etc.
- Navigation - contains string that navigate between other activities
- UiResources - contains resources of this application like drawable , colors , string ,etc.
