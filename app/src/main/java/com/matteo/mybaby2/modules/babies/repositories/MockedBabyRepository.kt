package com.matteo.mybaby2.modules.babies.repositories

import com.matteo.mybaby2.common.schemas.Age
import com.matteo.mybaby2.modules.babies.schemas.BabyRead
import com.matteo.mybaby2.modules.babies.schemas.BabyUpsert

class MockedBabyRepository: IBabyRepository {
    private val exampleBaby = BabyRead(id = 1, name= "Alessandro", age = Age(1,1,1))
    override fun getAll(): List<BabyRead> {
        print("ciao")
        return listOf(exampleBaby, exampleBaby)
    }

    override fun getById(id: Int): BabyRead {
        return exampleBaby
    }

    override fun create(dto: BabyUpsert) {
        TODO("Not yet implemented")
    }

    override fun update(
        id: String,
        dto: BabyUpsert
    ) {
        TODO("Not yet implemented")
    }

    override fun delete(id: String) {
        TODO("Not yet implemented")
    }
}