package nl.codingwithlinda.notemark.tests.exercises

import org.junit.Before
import org.junit.Test

class BabyChangingStationTest {
    val airportBabyChangingStation = AirportBabyChangingStation()
    val hiltonBabyChangingStation = HiltonBabyChangingStation()

    var happyBabesAtAirport =  0
    var happyBabesAtHilton = 0

    val waitingQueue = 6
    @Before
    fun setup(){
        airportBabyChangingStation.diaperSupply.clear()
        airportBabyChangingStation.addSupplies()
        hiltonBabyChangingStation.diaperSupply.clear()
        hiltonBabyChangingStation.addSupplies()
    }
    @Test
    fun `changing diaper at airport test`(){

        //Let's change some diapers at an airport. Your plain is about to leave, but ... shit happens.
        repeat(waitingQueue) {i->
            try {
                println(" $i, THIS BOY NEEDS A CLEAN DIAPER")
                airportBabyChangingStation.changeDiper(Child.BOY).let {happy ->
                    happyBabesAtAirport += if (happy) 1 else 0
                }

                println(" $i, THIS GIRL NEEDS A CLEAN DIAPER")
                airportBabyChangingStation.changeDiper(Child.GIRL).let {happy ->
                    happyBabesAtAirport += if (happy) 1 else 0
                }
            }catch (e: Exception) {
                println("AIRPORT GOT THE MESSAGE: ${e.message} ")
            }
        }
        //Let's add boys diapers to our supply!
        airportBabyChangingStation.supplyBoyDiapers()
        println("THIS BOY NOW GET'S A BOY'S DIAPER")
        airportBabyChangingStation.changeDiper(Child.BOY).let {happy ->
            happyBabesAtAirport += if (happy) 1 else 0
        }

        println("HAPPY BABES AT AIRPORT: $happyBabesAtAirport")
        println("**********************************************************")
    }

    @Test
    fun `changing diaper at the Hilton test`(){

        //Let's change some diapers in a luxury hotel
        repeat(waitingQueue) {i->
            try {
                println(" $i, THIS BOY NEEDS A CLEAN DIAPER")
                hiltonBabyChangingStation.changeDiper(Child.BOY).let {happy ->
                    happyBabesAtHilton += if (happy) 1 else 0
                }

                println(" $i, THIS GIRL NEEDS A CLEAN DIAPER")
                hiltonBabyChangingStation.changeDiper(Child.GIRL).let {happy ->
                    happyBabesAtHilton += if (happy) 1 else 0
                }
            }catch (e: Exception) {
                println(e.message)
            }
        }
        //Let's add unisex diapers to our supply!
        println("THE HILTON DECIDES TO HAVE A UNISEX DIAPER")
        hiltonBabyChangingStation.addUnisexDiaper()
        println("THE NEXT BOY GET'S A UNISEX DIAPER")
        hiltonBabyChangingStation.changeDiper(Child.BOY).let {happy ->
            happyBabesAtHilton += if (happy) 1 else 0
        }
        println("HAPPY BABES AT HILTON: $happyBabesAtHilton")

    }

}