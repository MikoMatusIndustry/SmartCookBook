package com.smartcookbook.viewmodel

import app.cash.turbine.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CookingTimerViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: CookingTimerViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = CookingTimerViewModel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is correct`() = runTest {
        assertEquals(15, viewModel.selectedMinutes.value)
        assertEquals(15 * 60, viewModel.timeLeftSeconds.value)
        assertFalse(viewModel.isRunning.value)
        assertFalse(viewModel.isFinished.value)
    }

    @Test
    fun `setMinutes updates values correctly`() = runTest {
        viewModel.setMinutes(10)
        assertEquals(10, viewModel.selectedMinutes.value)
        assertEquals(10 * 60, viewModel.timeLeftSeconds.value)
    }

    @Test
    fun `setMinutes clamps values between 1 and 99`() = runTest {
        viewModel.setMinutes(0)
        assertEquals(1, viewModel.selectedMinutes.value)
        
        viewModel.setMinutes(100)
        assertEquals(99, viewModel.selectedMinutes.value)
    }

    @Test
    fun `toggleTimer starts and stops the timer`() = runTest {
        viewModel.toggleTimer()
        assertTrue(viewModel.isRunning.value)

        viewModel.toggleTimer()
        assertFalse(viewModel.isRunning.value)
    }

    @Test
    fun `timer counts down correctly`() = runTest {
        viewModel.setMinutes(1) // 60 seconds
        viewModel.toggleTimer()
        
        // Advance time by 5 seconds
        advanceTimeBy(5000L)
        // Need to run current to let the job process the delays
        runCurrent()
        
        assertEquals(55, viewModel.timeLeftSeconds.value)
    }

    @Test
    fun `timer finishes correctly`() = runTest {
        viewModel.setMinutes(1)
        viewModel.toggleTimer()
        
        // Advance time by 60 seconds
        advanceTimeBy(60000L)
        runCurrent()
        
        assertEquals(0, viewModel.timeLeftSeconds.value)
        assertFalse(viewModel.isRunning.value)
        assertTrue(viewModel.isFinished.value)
    }

    @Test
    fun `reset returns to initial state`() = runTest {
        viewModel.setMinutes(10)
        viewModel.toggleTimer()
        advanceTimeBy(5000L)
        runCurrent()
        
        viewModel.reset()
        
        assertEquals(10, viewModel.selectedMinutes.value)
        assertEquals(10 * 60, viewModel.timeLeftSeconds.value)
        assertFalse(viewModel.isRunning.value)
        assertFalse(viewModel.isFinished.value)
    }
}
