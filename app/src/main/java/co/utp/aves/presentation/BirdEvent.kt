package co.utp.aves.presentation

import co.utp.aves.presentation.model.Ave

sealed class BirdEvent{
    class ListBird(val result: List<Ave>): BirdEvent()
    class FindBird(val result: Ave): BirdEvent()
}