package co.utp.aves.presentation.bird

import co.utp.aves.presentation.bird.model.Ave

sealed class BirdEvent{
    class ListBird(val result: List<Ave>): BirdEvent()
    class FindBird(val result: Ave): BirdEvent()
}