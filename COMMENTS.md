# Comments on the Classical Music News code

Ktor was chosen as HTTP client because I am used to it, I like its APIs and it's apparently newer and more powerful, but Retrofit, which is more mature, could also have been used. That's what nowinandroid uses.

I use a DataStore for storing preferences, which is provides easy-to-use APIs for preferences on Android.

I use Room for the local database, which is a great library for making storage and retrieval of entities easy to implement, easy to migrate, fun and good in all sorts of ways.

The architecture is MVVM and Clean Architecture.

Most of the code should be self-explanatory and quite standard.

The special feature I added to the project was an "ask Mozart's opinion" feature. It's accessible via the Mozart button in the top-right corner on the Details screen. When you click it, we make a call to a simple Node/Typescript/Express backend that's hosted on Replit. Let's call it CMN Backend. CMN Backend fetches some of the article text based on the link query parameter and passes it along to a GPT 3.5 turbo model via the Open AI API. The AI model is instructed to be Mozart resurrected and ready to give his opinions on classical music news articles.
