package com.example.android.guesstheword.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import timber.log.Timber

// View Model for the GameFragment
class GameViewModel : ViewModel() {

    companion object {
        // These represent different important times
        // This is when the game is over
        const val DONE = 0L

        // This is the number of milliseconds in a second
        const val ONE_SECOND = 1000L

        // This is the total time of the game
        const val COUNTDOWN_TIME = 10000L
    }

    // The current word live data
    private val _word = MutableLiveData<String>()
    val word: LiveData<String>
        get() = _word

    // The current score live data
    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score

    // The current time live data
    private val _currentTime = MutableLiveData<Long>()
    val currentTime: LiveData<Long>
        get() = _currentTime

    // The current time live data formatted to a string
    val currentTimeString = Transformations.map(_currentTime) { time ->
        DateUtils.formatElapsedTime(time)
    }

    // The status of the eventGameFinish
    private val _eventGameFinish = MutableLiveData<Boolean>()
    val eventGameFinish: LiveData<Boolean>
        get() = _eventGameFinish

    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>

    // Countdown timer
    private val timer: CountDownTimer

    init {
        Timber.i("GameViewModel was created!")

        // Initialize a timer which triggers the end of the game when it finishes
        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {

            // For each tick of the timer, update the currentTime
            override fun onTick(millisUntilFinished: Long) {
                _currentTime.value = millisUntilFinished / ONE_SECOND
            }

            // Set the currentTime to DONE, then trigger the game to end
            override fun onFinish() {
                _currentTime.value = DONE
                _eventGameFinish.value = true
            }
        }

        // Start the timer
        timer.start()

        // Initialize score to be 0 when the GameViewModel is created
        _score.value = 0

        // Set the state of eventGameFinish to false when GameViewModel is created
        _eventGameFinish.value = false

        // Shuffle the list of words and show the next word when when the
        // the GameViewModel is created
        resetList()
        nextWord()
    }

    /**
     * Resets the list of words and randomizes the order
     */
    private fun resetList() {
        wordList = mutableListOf(
                "queen",
                "hospital",
                "basketball",
                "cat",
                "change",
                "snail",
                "soup",
                "calendar",
                "sad",
                "desk",
                "guitar",
                "home",
                "railway",
                "zebra",
                "jelly",
                "car",
                "crow",
                "trade",
                "bag",
                "roll",
                "bubble"
        )
        wordList.shuffle()
    }

    /**
     * Moves to the next word in the list
     */
    private fun nextWord() {
        // If the list of words runs out, reset the list
        if (wordList.isEmpty()) {
            resetList()
        }
        // Always update the next word
        _word.value = wordList.removeAt(0)
    }

    // Decrement score and update the next word
    fun onSkip() {
        _score.value = (_score.value)?.minus(1)
        nextWord()
    }

    // Increment score and update the next word
    fun onCorrect() {
        _score.value = (_score.value)?.plus(1)
        nextWord()
    }

    override fun onCleared() {
        super.onCleared()

        // Cancel the timer when we no longer need it to avoid memory leaks
        timer.cancel()
    }

    // Will set the state of eventGameFinish to false when called by the GameFragment
    fun onGameFinishComplete() {
        _eventGameFinish.value = false
    }
}