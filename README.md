# Cantegories Mobgen
Sample Android project of a [CategoriesMobgen](http://android-test.mobgen.com/api1/) client.

The client will load automatically the  popular items(books, houses , characaters) from Cantegories API, which can be found here: http://android-test.mobgen.com/api1/

![Demo](https://github.com/gabrielpozo/Categories/raw/master/gifCategories.gif)

Technical features
============
The code demostrates how to use together multiple design patterns, libraries.

The code implements pagination when scrolling down through the category item view(books, characters or houses) querying the APi calls
for some resource(10 items by default) instead of delivering all of the results.


Libraries
-------
- Retrofit, OkHttp
- Gson
- SQL Lite
- AppCompat
- Material Design
- Picasso
- Glide
- Mockito
- Hamcrest



Patterns
-------
- Model View View-Model
- Singleton
- Continuos integration


Running the project and the tests
=============
Application's apk can be directly [downloaded from here]

Open the project in Android Studio and select the gradle task '**installDebug**' or simply press the Run button.


_Note: Make sure to connect a phone to the computer or start an emulator before running the tests._

Continuous integration environment
============
Builds are triggered and automatically built on every commit to git repository, executing all unit tests.
Build history and reports are on the development branch of the project: https://bitbucket.org/mobgen/interview-android-test2-gabriel/commits/branch/development

