package com.example.android.guesstheword.screens.score

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber

/**
 * ViewModel for the final  screen showing the score
 */
class ScoreViewModel(finalScore: Int) : ViewModel() {

    // Score LiveData
    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score

    // EventPlayAgain LiveData
    private val _eventPlayAgain = MutableLiveData<Boolean>()
    val eventPlayAgain: LiveData<Boolean>
        get() = _eventPlayAgain

    init {
        Timber.i("Final Score is: $finalScore")

        // Initialize the score LiveData when this viewModel is created
        _score.value = finalScore

        // Initialize the EventPlayAgain trigger to false on creation
        _eventPlayAgain.value = false
    }

    fun onPlayAgain(){
        _eventPlayAgain.value = true
    }

    fun onPlayAgainComplete(){
        _eventPlayAgain.value = false
    }
}