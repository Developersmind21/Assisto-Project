package com.example.assisto.di

import com.example.assisto.data.mock.MockProviderRepository
import com.example.assisto.data.mock.MockRequestRepository
import com.example.assisto.data.mock.MockUserRepository
import com.example.assisto.data.repository.ProviderRepository
import com.example.assisto.data.repository.RequestRepository
import com.example.assisto.data.repository.UserRepository

class AppContainer {
    val userRepository: UserRepository = MockUserRepository()
    val requestRepository: RequestRepository = MockRequestRepository()
    val providerRepository: ProviderRepository = MockProviderRepository()
    val mockUserRepository: MockUserRepository get() = userRepository as MockUserRepository
    val mockRequestRepository: MockRequestRepository get() = requestRepository as MockRequestRepository
}
