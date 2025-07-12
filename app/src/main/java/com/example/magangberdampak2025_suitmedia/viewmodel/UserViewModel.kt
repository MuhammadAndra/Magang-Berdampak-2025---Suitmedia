package com.example.magangberdampak2025_suitmedia.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.magangberdampak2025_suitmedia.model.User
import com.example.magangberdampak2025_suitmedia.model.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    private val repository = UserRepository()

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users


    private val _isInitialLoading = MutableStateFlow(false)
    val isInitialLoading: StateFlow<Boolean> = _isInitialLoading.asStateFlow()

    private val _isPagingLoading = MutableStateFlow(false)
    val isPagingLoading: StateFlow<Boolean> = _isPagingLoading.asStateFlow()

    private val _isError = MutableStateFlow<String?>(null)
    val isError: StateFlow<String?> = _isError.asStateFlow()

    private var currentPage = 1
    private val perPage = 10
    private var isLastPage = false


    fun fetchUsers() {
        if (_isInitialLoading.value || isLastPage) return

        val isFirstPage = currentPage == 1


        viewModelScope.launch(Dispatchers.IO) {
            if (isFirstPage) _isInitialLoading.value = true else _isPagingLoading.value = true
            _isError.value = null
            try {
                val result = UserRepository().fetchUsers(currentPage, perPage)
//                _users.value = result
                if (result.isEmpty()) {
                    isLastPage = true
                } else {
                    _users.value += result
                    currentPage++
                }
            } catch (e: Exception) {
                _isError.value = e.message ?: "Unknown error"
            } finally {
                if (isFirstPage) _isInitialLoading.value = false else _isPagingLoading.value = false
            }
        }
    }
    fun reset() {
        currentPage = 1
        isLastPage = false
        _users.value = emptyList()
    }

}