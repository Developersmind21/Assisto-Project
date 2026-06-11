package com.example.assisto.data.mock

import com.example.assisto.data.model.Provider
import com.example.assisto.data.model.Review
import com.example.assisto.data.model.ServiceCategory
import com.example.assisto.data.model.SortOption
import com.example.assisto.data.repository.ProviderRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
class MockProviderRepository : ProviderRepository {

    override fun getFeaturedProviders(): Flow<List<Provider>> = flow {
        delay(400)
        emit(MockData.providers.filter { it.isAvailable }.take(4))
    }

    override fun getProvidersNearLocation(
        category: ServiceCategory?,
        sortBy: SortOption,
        radiusMiles: Float,
    ): Flow<List<Provider>> = flow {
        delay(800)
        var list = MockData.providers.filter { it.distanceMiles <= radiusMiles && it.isAvailable }
        if (category != null) {
            list = list.filter { category in it.categories }
        }
        list = when (sortBy) {
            SortOption.DISTANCE -> list.sortedBy { it.distanceMiles }
            SortOption.RATING -> list.sortedByDescending { it.rating }
            SortOption.PRICE -> list.sortedBy { it.priceRange }
            SortOption.AVAILABILITY -> list.sortedBy { it.etaMinutes }
        }
        emit(list)
    }

    override fun getProviderById(id: String): Flow<Provider?> = flow {
        delay(300)
        emit(MockData.providers.find { it.id == id })
    }

    override fun getProviderReviews(providerId: String): Flow<List<Review>> = flow {
        delay(300)
        emit(MockData.reviews[providerId] ?: emptyList())
    }
}
