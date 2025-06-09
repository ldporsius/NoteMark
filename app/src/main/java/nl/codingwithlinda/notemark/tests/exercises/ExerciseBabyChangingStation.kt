package nl.codingwithlinda.notemark.tests.exercises

enum class Child{
    BOY, GIRL
}
interface Diaper{
    val fitFor: List<Child>
}

class BoyDiaper: Diaper {
    override val fitFor = listOf(Child.BOY)
}
class GirlDiaper: Diaper {
    override val fitFor = listOf(Child.GIRL)
}
class UnisexDiper: Diaper {
    override val fitFor = listOf(Child.BOY, Child.GIRL)
}

interface BabyChangingStation{
    fun changeDiper(child: Child): Boolean
}

class HiltonBabyChangingStation: BabyChangingStation {
    val diaperSupply: MutableList<Diaper> = mutableListOf()
    fun addSupplies() {
        repeat(5) {
            diaperSupply.add(BoyDiaper())
            diaperSupply.add(GirlDiaper())
        }
    }
    fun addUnisexDiaper(){
        diaperSupply.add(UnisexDiper())
    }
    inline fun <reified D: Diaper>getSpecificDiaper(): Diaper {
        val diaper = diaperSupply.firstOrNull() {
            it is D
        }
        diaper?.run {
            diaperSupply.remove(this)
        }

        return diaper ?: throw RuntimeException("No diaper of type found.")
    }

    init {
        addSupplies()
    }
    override fun changeDiper(child: Child): Boolean {

        fun getDiaper(get: () -> Diaper){
            try {
                val diaper = get.invoke()
                println("Changed diaper ${diaper.javaClass.simpleName} for $child at the Hilton baby changing station")

            }catch (e: Exception) {
                println("CRISIS AT HILTON: ${e.message}")
            }
        }
        try {
            val diaper = when (child) {
                Child.BOY -> getSpecificDiaper<BoyDiaper>()
                Child.GIRL -> getSpecificDiaper<GirlDiaper>()
            }
            println("Changed diaper successfully! ${diaper.javaClass.simpleName} for $child at the Hilton baby changing station")
            return true
        }catch (e: Exception) {
            println("No more diapers: ${e.message}")
            println("HILTON TRIES TO PROVIDES UNISEX DIAPER")
            getDiaper {
                getSpecificDiaper<UnisexDiper>()
            }

        }

        return false
    }

}
class AirportBabyChangingStation: BabyChangingStation {
    val diaperSupply: MutableList<Diaper> = mutableListOf()
    fun addSupplies() {
        repeat(10) {
            diaperSupply.add(UnisexDiper())
        }
    }
    fun supplyBoyDiapers(){
        repeat(10) {
            diaperSupply.add(BoyDiaper())
        }
    }
    private fun getAnyDiaper(): Diaper {
        val firstAvailableDiaper = diaperSupply.firstOrNull() ?: throw RuntimeException("Out of supply")
        diaperSupply.remove(firstAvailableDiaper)
        return firstAvailableDiaper
    }
    private fun getUnisexDiaper(): UnisexDiper {
        val diaper = diaperSupply.firstOrNull {
            it is UnisexDiper
        }?: throw RuntimeException("No unisex diapers")
        diaper.let {
            diaperSupply.remove(it)
            return it as UnisexDiper
        }
    }
    private fun <D: Diaper>getSpecificDiaper(child: Child): D {
        println("CHILD IN QUEUE: $childInQueue is a $child")
        val firstAvailableDiaper = diaperSupply.firstOrNull{
            it.fitFor.all {
                it == child
            }
        }
        firstAvailableDiaper ?: throw RuntimeException("No diaper for this specific child: $child")
        diaperSupply.remove(firstAvailableDiaper)
        return firstAvailableDiaper as D
    }
    init {
        addSupplies()
    }

    var childInQueue = 0
    override fun changeDiper(child: Child): Boolean {

        fun getDiaper(get: () -> Diaper, onFailure: (() -> Diaper)? = null): Boolean {
            try {
                val diaper = get()
                println("Diaper changed success! ${diaper.javaClass.simpleName}")
                println("Make room for next babe: ${childInQueue ++}")
                return true
            }catch (e: Exception){
                println("${e.message}")
                onFailure?.run {
                    return getDiaper(onFailure , null)

                }

                println("Diaper was not changed. Sorry.")
                return false
            }

        }

        when (child) {
            Child.BOY -> {
                println("Changing diaper for $child at the Airport baby changing station")
                return getDiaper(
                    get = { getSpecificDiaper<BoyDiaper>(child) },
                    onFailure = {
                        getUnisexDiaper()
                    }
                )
            }
            Child.GIRL -> {
                println("Changing diaper for $child at the Airport baby changing station")
                return getDiaper(
                    get = { getSpecificDiaper<GirlDiaper>(child) },
                    onFailure = {  getUnisexDiaper() }
                )
            }
        }
    }
}