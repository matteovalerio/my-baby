package com.matteo.mybaby2.modules.activities.repositories

import com.matteo.mybaby2.modules.activities.repositories.MockedActivity.exampleActivity
import com.matteo.mybaby2.modules.activities.schemas.ActivityRead
import com.matteo.mybaby2.modules.activities.schemas.BreastfeedingActivitiesRead
import com.matteo.mybaby2.modules.activities.schemas.BreastfeedingActivity
import com.matteo.mybaby2.modules.babies.repositories.MockedBaby
import com.matteo.mybaby2.modules.babies.schemas.BabyRead
import java.time.Instant
import kotlin.time.Duration.Companion.minutes

object MockedActivity {
    val exampleActivity = ActivityRead(
        id = 1,
        name = "Feeding",
        categoryId = 1,
        baby = MockedBaby.exampleBaby, // Assuming you have MockedBaby object
        breastfeedingActivities = BreastfeedingActivitiesRead(
            leftBreast = BreastfeedingActivity(duration = 10.minutes),
            rightBreast = BreastfeedingActivity(duration = 15.minutes)
        ),
        duration = 25.minutes,
        createdAt = Instant.now()
    )
}

class MockedActivityRepository: IActivityRepository {

    override suspend fun getAllByBabyId(babyId: Int): List<ActivityRead> {
        return listOf(exampleActivity, exampleActivity, exampleActivity)
    }

    override suspend fun getById(id: Int): ActivityRead {
        return exampleActivity
    }
}