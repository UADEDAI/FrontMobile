package com.uade.daitp.client.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uade.daitp.client.core.actions.CreateComment
import com.uade.daitp.client.core.actions.GetComments
import com.uade.daitp.client.core.model.Comment
import com.uade.daitp.client.core.model.CommentIntent
import com.uade.daitp.owner.home.core.actions.GetMovie
import com.uade.daitp.owner.home.core.models.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ClientMovieDetailViewModel(
    private val getMovie: GetMovie,
    private val getComments: GetComments,
    private val createComment: CreateComment
) : ViewModel() {

    private val _error: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val error: LiveData<String> get() = _error

    private val _movie: MutableLiveData<Movie> by lazy { MutableLiveData<Movie>() }
    val movie: LiveData<Movie> get() = _movie

    private val _comments: MutableLiveData<List<Comment>> by lazy { MutableLiveData<List<Comment>>() }
    val comments: LiveData<List<Comment>> get() = _comments

    fun getMovieBy(movieId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val movie = getMovie(movieId)
                _movie.postValue(movie)
            } catch (e: Exception) {
                _error.postValue(e.message)
            }
        }
    }

    fun refreshComments(movieId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val comments = getComments(movieId)
                _comments.postValue(comments)
            } catch (e: Exception) {
                _error.postValue(e.message)
            }
        }
    }

    fun createCommentOf(title: String, body: String, score: Double){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val commentIntent = CommentIntent(-1, _movie.value!!.id, title, body, score)
                createComment(commentIntent)
                refreshComments(_movie.value!!.id)
            } catch (e: Exception) {
                _error.postValue(e.message)
            }
        }
    }
}