package com.example.assisto.data.repository

import com.example.assisto.data.model.Provider
import com.example.assisto.data.model.Review
import com.example.assisto.data.model.ServiceCategory
import com.example.assisto.data.model.SortOption
import kotlinx.coroutines.flow.Flow

interface ProviderRepository {
    fun getFeaturedProviders(): Flow<List<Provider>>
    fun getProvidersNearLocation(
        category: ServiceCategory?,
        sortBy: SortOption,
        radiusMiles: Float,
    ): Flow<List<Provider>>
    fun getProviderById(id: String): Flow<Provider?>
    fun getProviderReviews(providerId: String): Flow<List<Review>>
}
