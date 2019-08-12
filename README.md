### About

Application is designed to display data about SpaceX program launches
It has 3 screens:
- **List of lounches in historical order**
List supports pagination. It loads several items, and while scolling it loads more from server until it retrieves all the data.
- **Graph which represents how many lounches were performed on each month**
It also supports pagination. Graph may scale itself while loading new data, as incoming values can be bigger than starting.
- **Launch Details**
Displays detailed description about the launch alongside with its photos if they are availabe. It is possible to download photos by clicking on them.

### Implementaion Details.

- Application follows CLEAN architecture. There are 3 modules: *app*, *data* and *domain* and *app* knows only about interfaces and models of *domain*, and there implementaion is hidden in *data* module.
- Application is implemented by MVVM pattern, and ViewModel is tested by unittests
- Pagination. SpaceX api allows to get information about all launches at once or get number of launches, but current app implementation doesn't rely on that. It just gets data by small portions, which are requested by UI.
- Retrofit is used as networking library
- JavaRx
- Dagger2 as a DI
- Picasso as image loader
- File download is performed by DownloadManager
