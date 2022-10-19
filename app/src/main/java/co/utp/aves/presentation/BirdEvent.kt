package co.utp.aves.presentation

import co.utp.aves.presentation.model.AboutUs
import co.utp.aves.presentation.model.Ave

sealed class BirdEvent{
    class ListBird(val listBird: List<Ave>): BirdEvent()
    class FindBird(val bird: Ave): BirdEvent()
    class ListAboutUs(val listAboutUs: List<AboutUs>):BirdEvent()
}