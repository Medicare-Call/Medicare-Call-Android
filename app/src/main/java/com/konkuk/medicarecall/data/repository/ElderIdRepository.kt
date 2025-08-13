package com.konkuk.medicarecall.data.repository

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ElderIdRepository @Inject constructor() {
    private var elderIds: MutableList<Int> = mutableListOf()
    fun addElderId(id: Int) {
        this.elderIds.add(id)
    }

    fun getElderIds(): List<Int> {
        return elderIds
    }
}