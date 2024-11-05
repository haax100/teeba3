// AppModule.kt
package com.hax.teeba3

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // توفير SharedPreferences للتطبيق
    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("cart_preferences", Context.MODE_PRIVATE)
    }

    // توفير مكتبة Gson لتحويل البيانات إلى JSON والعكس
    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

    // توفير مستودع السلة باستخدام CartRepositoryImpl
    @Provides
    @Singleton
    fun provideCartRepository(
        sharedPreferences: SharedPreferences,
        gson: Gson
    ): CartRepository {
        return CartRepositoryImpl(sharedPreferences, gson)
    }
}
