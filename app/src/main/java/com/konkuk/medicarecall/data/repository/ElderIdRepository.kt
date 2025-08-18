package com.konkuk.medicarecall.data.repository

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ElderIdRepository @Inject constructor() {

    private var elderIds: MutableList<Map<String, Int>> = mutableListOf()
    fun addElderId(name:String, id: Int) {
        this.elderIds.add(mapOf(name to id))
    }

    fun clearElderId() {
        elderIds.clear()
    }

    fun getElderIds(): List<Map<String, Int>> {
        return elderIds
    }


}