package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.model.ProjectCategory
import com.example.data.model.ProjectStatus
import com.example.ui.components.UserLabRole
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Indus Nexus", appName)
  }

  @Test
  fun `verify domain categories and project status enums`() {
    val categories = ProjectCategory.values()
    assertTrue(categories.contains(ProjectCategory.HARDWARE))
    assertTrue(categories.contains(ProjectCategory.SOFTWARE))
    assertTrue(categories.contains(ProjectCategory.HYBRID_IOT))

    val statuses = ProjectStatus.values()
    assertTrue(statuses.contains(ProjectStatus.READY_FOR_DEFENSE))
    assertTrue(statuses.contains(ProjectStatus.BENCH_TESTING))
    assertTrue(statuses.contains(ProjectStatus.IN_PROGRESS))

    val roles = UserLabRole.values()
    assertEquals(3, roles.size)
  }
}



