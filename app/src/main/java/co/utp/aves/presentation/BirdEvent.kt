package co.utp.aves.presentation

import co.utp.aves.data.remote.model.BirdResponse
import co.utp.aves.presentation.model.AboutUs
import co.utp.aves.presentation.model.AboutUsDependencies
import co.utp.aves.presentation.model.Ave

sealed class BirdEvent{
    class ListBird(val listBird: List<Ave>): BirdEvent()
    class FindBird(val bird: Ave): BirdEvent()
    class ListAboutUs(val listAboutUs: List<AboutUs>):BirdEvent()
    class ListAboutUsDependencies(val listAboutUsDependencies: List<AboutUsDependencies>):BirdEvent()
    class IdBird(val id: BirdResponse): BirdEvent()
}