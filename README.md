# HingeProject

Written in MVI style utilizing the following libraries

- Jetpack compose
    - Chosen primarily for learnings as well as ease of implementation for server side rendering.
- AAC Components (Viewmodel, LiveData)
    - Allows data and models to retain outside of view scope (properly renders UI on configuration change, handles process death gracefully).
- Kotlin Coroutines
    - Simple asynchronous API. More complicated use cases can utilize Flow.
- Hilt
    - Dagger injection without most of the boilerplate.
- Mockk
    - Easily allows testing of coroutines. Also provides better aliases in kotlin (every vs when)
- Retrofit
    - Easy to use network library to call RESTful APIs
