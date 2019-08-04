package alytvyniuk.com.data.repository.di

import alytvyniuk.com.data.repository.imageLoader.ImageLoaderImpl
import alytvyniuk.com.model.ImageLoader
import dagger.Module
import dagger.Provides

@Module
class ImageLoaderModule {

    @Provides
    fun provideImageLoader() : ImageLoader = ImageLoaderImpl()
}
